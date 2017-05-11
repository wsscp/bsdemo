package cc.oit.bsmes.job.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.dao.ParmsMappingDAO;
import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfaceWWAc.dao.EquipParamAcquisitionDAO;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamAcquisition;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;

@Service
public class AlarmProcessTask extends BaseSimpleTask {

	@Resource
	private EquipMESWWMappingService equipMESWWMappingService;
	@Resource
	private ParmsMappingDAO parmsMappingDAO;
	@Resource
	private EquipParamAcquisitionDAO equipParamAcquisitionDAO;
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private EquipInfoService equipInfoService;
	
	/**
	 * <p>
	 * 警报处理
	 * </p>
	 * ①、查询AlarmHistory列表遍历； ②、根据TagName获取MES映射对象EquipMESWWMapping； ③、
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void process(JobParams parms) {	
		// 事件列表对象：最后保存警报事件
		List<EventInformation> eventArray = new ArrayList<EventInformation>();
		// 1、MES库映射表获取有事件状态的记录
		List<EquipMESWWMapping> alarms = equipMESWWMappingService.getDataForEvent();
		for(EquipMESWWMapping www : alarms){
//			if(!"R_ColdD".equals(www.getParmCode())){
//				continue;
//			}
			try {
				// 1.1、获取设备信息: 不在加工中的都不产生事件
				EquipInfo equipLine = equipInfoService.getByCode(www.getEquipCode().replace("_EQUIP", ""), parms.getOrgCode());
				if(null == equipLine || equipLine.getStatus() != EquipStatus.IN_PROGRESS){
					 continue;
				}
				// 1.2、事件标题：根据标题判断当前事件是否已经发生，防止重复发生
				String title = "质量采集数据报警[" + www.getParmName() + "]";
				EventInformation param = new EventInformation();
				param.setEventTitle(title);
				Integer has = eventInformationService.hasRecentEvent(param);
				if(has > 0){
					continue;
				}
				
				// 2、根据[设备编码、标签]获取当前的产品的上下限
				Double hi = 0d, lo = 0d;
//				EquipInfo equipLine = new EquipInfo();
//				equipLine.setCode(www.getEquipCode().replace("_EQUIP", ""));
//				equipLine.setEquipAlias(" 戴维斯挤出");
				//				List<EquipInfo> equipLineArray = equipInfoService.findByObj(equipLine);
				String targetValue = parmsMappingDAO.getEquipWWProductValue(www.getParmCode(), equipLine.getCode(),
						parms.getOrgCode());
//				targetValue = "0.5-1.5";
				if (StringUtils.isEmpty(targetValue)) {
					continue;
				}else{
					String[] s = targetValue.split("-");
					if(s.length == 2){
						lo = Double.valueOf(s[0]);
						hi = Double.valueOf(s[1]);
					}else if(s.length == 1){
						hi = Double.valueOf(s[0]);
					}else{
						continue;
					}
				}
				// 3、获取标签的实时值
				EquipParamAcquisition liveData = null;
				Double liveValue = 0d;
				List<String> tagNameArray = new ArrayList<String>();
				tagNameArray.add(www.getTagName());
				List<EquipParamAcquisition> liveParamValues = equipParamAcquisitionDAO.findLiveValue(tagNameArray,
						null, null);
				if(liveParamValues.size() > 0){
					liveData = liveParamValues.get(0);
					liveValue = liveData.getValue();
				}else{
					continue;
				}
				
				// liveValue = 1.7;
				
				// 4、判断标签值是否超出
				if(null == liveValue || liveValue == 0){
					continue;
				}
				String upOrDown = null;
				if(hi != 0 && liveValue > hi){
					upOrDown = "设定上限:" + hi; // 高报
				}else if(lo != 0 && liveValue < lo){
					upOrDown = "设定下限:" + lo; // 低报
				}else{
					continue;
				}
				StringBuffer desc = new StringBuffer();
				desc.append("设备:").append(equipLine.getEquipAlias()).append("[").append(equipLine.getCode()).append("]")
					.append(",参数:").append(www.getParmName()).append("发生报警,").append(",采集值:")
					.append(liveValue).append(",").append(upOrDown).append(",请处理。");
				EventInformation event = this.initEventInfo(title, desc.toString(), www.getEventType().name(),
						new Date(), "", equipLine.getCode(), parms.getOrgCode());
				eventArray.add(event);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
			}
		}
		
		if(!CollectionUtils.isEmpty(eventArray)){
			// 5、保存事件信息
			eventInformationService.insertInfo(eventArray);
		}
		
		
	}

	/**
	 * <p>
	 * 封装事件对象
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-11-10 09:47:16
	 * @param title 事件标题
	 * @param eventContent 事件内容
	 * @param code 类型代码
	 * @param eventStamp 事件升级触发时间
	 * @param processId 工序ID
	 * @param equipCode 设备CODE
	 * @param orgCode 数据所属组织
	 * */
	private EventInformation initEventInfo(String title, String eventContent, String code, Date eventStamp,
			String processId, String equipCode, String orgCode) {
		EventInformation event = new EventInformation();
		event.setEventTitle(title);
		event.setEventContent(eventContent);
		event.setCode(code); // 设备异常
		event.setEventStatus(EventStatus.UNCOMPLETED);
		event.setProcessTriggerTime(eventStamp);
		event.setProcessId(processId);
		event.setEquipCode(equipCode);
		event.setOrgCode(orgCode);
		return event;
	}
}
