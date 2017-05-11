package cc.oit.bsmes.job.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfaceWWAc.dao.EquipParamAcquisitionDAO;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamAcquisition;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;

@Service
public class EquipStatusProcessTask extends BaseSimpleTask {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private EquipInfoService equipInfoService;

	@Resource
	private DataAcquisitionService dataAcquisitionService;

	@Resource
	private EquipParamAcquisitionDAO equipParamAcquisitionDAO;

	@Resource
	private EquipMESWWMappingService equipMESWWMappingService;

	// 上次采集的对象：存了值和时间
	public static Map<String, EquipParamAcquisition> lastVliveCache = new HashMap<String, EquipParamAcquisition>();
	public static Map<String, Long> lastTimeCache = new HashMap<String, Long>();

	/**
	 * <p>
	 * 设备状态处理:优先使用计米器来判断设备状态，如果计算结果为空闲或者关机，使用设备特定的属性来判断设备状态
	 * </p>
	 * ①、查询AlarmHistory列表遍历； ②、根据TagName获取MES映射对象EquipMESWWMapping； ③、
	 */
	@Override
	//@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void process(JobParams parms) {
		// 1、获取设备列表
		EquipInfo findParams = new EquipInfo();
		findParams.setOrgCode(parms.getOrgCode());
		//findParams.setCode("445-227");
		findParams.setType(EquipType.PRODUCT_LINE);
		List<EquipInfo> lineList = equipInfoService.findByObj(findParams);
		for (EquipInfo equip : lineList) {
			this.processOne(equip);
		}
	}
	
	/**
	 * @Title:       processOne
	 * @Description: TODO(单独处理一个设备，保持每个设备独立处理，相互不影响)
	 * @param:       equip 设备信息
	 * @return:      void   
	 * @throws
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	private void processOne(EquipInfo equip){
		try {
			// 1、设备状态故障或者维修或者调试直接返回
            if(equip.getStatus() == EquipStatus.ERROR || equip.getStatus() == EquipStatus.IN_MAINTAIN
                    || equip.getStatus() == EquipStatus.IN_DEBUG){
                return;
            }
			// 2、获取设备状态采集的参考属性的tagName
			List<String> tagNameArray = this.getTagNameArray(equip);
			if (null == tagNameArray || tagNameArray.size() == 0) {
				return;
			}
			
			// 3、获取数采实时库中的实时值，并遍历判断状态
			List<EquipParamAcquisition> liveParamValues = equipParamAcquisitionDAO.findLiveValue(tagNameArray,
					null, null);
			if (null == liveParamValues || liveParamValues.size() == 0) {
				return;
			}
			
			// 4、比较采集值的大小判断状态
			Integer process = 0, idle = 0, close = 0;
			EquipStatus equipStatus = equip.getStatus();
			boolean needAlarm = false;
			for (EquipParamAcquisition param : liveParamValues) {
				Double targetValue = param.getValue(); // 这次采集到的值
				// 上次采集的对象
				EquipParamAcquisition lastParam = lastVliveCache.get(equip.getCode() + param.getTagname()); 
				Long lastTime = lastTimeCache.get(equip.getCode() + param.getTagname()); 
				if(null == lastParam){ // 上次没有跳过，至少要取到才能比较
					lastVliveCache.put(equip.getCode() + param.getTagname(), param);
					lastTimeCache.put(equip.getCode() + param.getTagname(), new Date().getTime());
					continue;
				}
				if (null == targetValue) { // 这次是空:妥妥的关机
					close++;
				} else if("Braiding".equals(equip.getProcessCode()) && 
						"R_GreenL".equals(param.getTagname().substring(param.getTagname().indexOf(".")+1))){
					// 4.1、编织机直接根据R_GreenL来判断：0空闲;1加工
					if(targetValue == 0){
						idle++;
					}else if(targetValue == 1){
						process++;
					}
				}else if(targetValue <= 0 || Math.abs(targetValue) < 0.5){ // 负数，值很小的情况也归类为空闲：因为值一直在变，很小，所以加此判断
					idle++;
				}else{
					// ①上次值为空，这次不为空：加工中；
					// ②上次值不为空:比较两次的:a、值不等；b、时间不等都说明在加工中；
					if (null == lastParam.getValue() || Math.abs(lastParam.getValue() - targetValue) > 0 || 
								lastParam.getDatetime().getTime() !=  param.getDatetime().getTime()){
						process++;
					} else if(Math.abs(lastParam.getValue() - targetValue) == 0 ){
						// 值不变的情况下：时间间距小，保持原来状态；时间间距大，空闲
						if(Math.abs(new Date().getTime() - lastTime) < 2*60*1000){ 
						
						}else{
							idle++;
						}
					}else {
						idle++;
					}
				}
				lastVliveCache.put(equip.getCode() + param.getTagname(), param);
				// 上次变化的时间
				if(null != targetValue && (Math.abs(lastParam.getValue() - targetValue) > 0 || lastParam.getDatetime().getTime() !=  param.getDatetime().getTime()))
					lastTimeCache.put(equip.getCode() + param.getTagname(), new Date().getTime());
			}

			// 5、定义的标签都大于才算开机状态，否则有一个大于或者能查到数据就算待机，否则为关机
			// if (process == tagNameArray.size()) { // 都大于才算开机状态
			if (process > 0) { // @edit:2016-06-13 只要一个大于零就算加工，大爷的
				equipStatus = EquipStatus.IN_PROGRESS;
				needAlarm = true;
			// } else if (idle > 0 || process > 0) { // 有一个大于或者能查到数据就算待机
			} else if (idle > 0) { // 有一个大于或者能查到数据就算待机
				equipStatus = EquipStatus.IDLE;
				needAlarm = true;
			} else if(close > 0){
				equipStatus = EquipStatus.CLOSED;
				needAlarm = false;
			}
			// 6、状态发生变化了才更新
			if (equipStatus != equip.getStatus()) { // 3、计算状态发生变化，更新
				equipInfoService.changeEquipStatus(equip.getCode(), equipStatus, "job", needAlarm);
			}
		} catch (Exception e) {
			logger.error("设备["+equip.getCode()+"]更新异常", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @Title: getTagNameArray
	 * @Description: TODO(获取设备的状态采集标签)
	 * @param: equip 设备
	 * @return: List<String>
	 * @throws
	 */
	private List<String> getTagNameArray(EquipInfo equip) {
		// 1、获取设备的状态采集参考属性：基本属性+计米属性
		String statusBWw = equip.getStatusBasisWw()==null?"":equip.getStatusBasisWw();
		List<String> arrayList = Arrays.asList(statusBWw.split(","));
		List<String> statusBasisWws = new ArrayList<String>(arrayList);
		// statusBasisWws.add(StandardParamCode.R_Length.name());
		// 2、从采集实时表获取值
		EquipInfo mainEquip = StaticDataCache.getMainEquipInfo(equip.getCode());
		Map<String, String> paramsMap = equipMESWWMappingService.getTagNameByEquipCodeParams(mainEquip.getCode(),
				statusBasisWws);
		if (null != paramsMap) {
			// 2.2、查询标签对应的实时参数
			// 定义的标签都大于才算开机状态，否则有一个大于或者能查到数据就算待机，否则为关机
			List<String> tagNameArray = new ArrayList<String>(Arrays.asList(paramsMap.get("TAGNAME").split(",")));
			return tagNameArray;
		}
		return null;
	}

}
