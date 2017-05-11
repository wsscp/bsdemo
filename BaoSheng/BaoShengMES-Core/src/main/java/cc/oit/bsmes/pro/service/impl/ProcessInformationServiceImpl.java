package cc.oit.bsmes.pro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.ProcessSection;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pro.dao.ProcessInformationDAO;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import cc.oit.bsmes.pro.service.ProductProcessService;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-3-26 下午4:22:12
 * @since
 * @version
 */
@Service
public class ProcessInformationServiceImpl extends BaseServiceImpl<ProcessInformation> implements
		ProcessInformationService {
	public static final Pattern cablingPattern = Pattern.compile("成缆([\\u4e00-\\u9fa5]+?)层");
	public static final Pattern wrappPattern = Pattern.compile("绕包([\\u4e00-\\u9fa5]+?)层");

	@Resource
	private ProcessInformationDAO processInformationDAO;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;;

	@Resource
	private ProductProcessService productProcessService;

	@Override
	public List<ProcessInformation> getSection() {
		Map<String, Object> param = new HashMap<String, Object>();
		User user = SessionUtils.getUser();
		param.put("orgCode", user.getOrgCode());
		return processInformationDAO.getSection(param);
	}

	@Override
	public List<ProcessInformation> getBySection(String section) {
		Map<String, Object> param = new HashMap<String, Object>();
		User user = SessionUtils.getUser();
		param.put("orgCode", user.getOrgCode());
		param.put("section", section);
		return processInformationDAO.getBySection(param);
	}

	/**
	 * <p>
	 * 查询条件->工艺信息下拉框：支持模糊查询
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<ProcessInformation>
	 * @see
	 */
	@Override
	public List<ProcessInformation> findByCodeOrName(String query) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(query)){
			param.put("codeOrName", "%" + query + "%");
		}
		return processInformationDAO.findByCodeOrName(param);
	}

	@Override
	public ProcessInformation getByCode(String code) {
		return processInformationDAO.getByCode(code);
	}

	/**
	 * 
	 * <p>
	 * 排生产单 : 根据工段、客户订单ID获取工序列表
	 * 1、绝缘只返回工序分组后的结果[工序1,工序2,工序3...]；
	 * 2、护套成缆返回每个型号规格的工序{订单id-1:工序组1,订单id-2:工序组2,...}；
	 * 3、成缆工序查询后要拆分工序组。
	 * </p>
	 * 
	 * @author DingXintao
	 * @param map 保存数据的map
	 * @param section 工段
	 * @param orderItemIdArray 客户订单ID数组
	 * 
	 * @return 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getOrderProcessBySection(Map map, ProcessSection section, String[] orderItemIdArray){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("section", section.getOrder());
		// 1、绝缘只返回工序分组后的结果[工序1,工序2,工序3...]；
		if(ProcessSection.JY == section){
			param.put("orderItemIdArray", orderItemIdArray);
			map.put("processList", processInformationDAO.getOrderProcessBySection(param));
		}else{
			// 2、护套成缆返回每个型号规格的工序{订单id-1:工序组1,订单id-2:工序组2,...}；
			Map<String, List<Map<String, String>>> returnMap = new HashMap<String, List<Map<String, String>>>();
			for (String orderItemId : orderItemIdArray) {
				param.put("orderItemIdArray", new String[] { orderItemId });
				List<Map<String, String>> queryResult = processInformationDAO.getOrderProcessBySection(param);
				// 3、成缆工序查询后要拆分工序组。
				if(ProcessSection.CL == section){
					queryResult = this.getSplitWrappingProcess(queryResult);
				}
				returnMap.put(orderItemId, queryResult);
			}
			map.put("processList", returnMap);
		}
	}

	/**
	 * 根据订单明细id获得成缆工段的所有工序
	 * 
	 * @param orderItemId 订单ID：用于查询绞向
	 * @param processInformationArray 成缆工段查询出来的工序
	 * @return
	 */
	public List<Map<String, String>> getSplitWrappingProcess(List<Map<String, String>> processInformationArray) {
		List<Map<String, String>> processInformationArrayReturn = new ArrayList<Map<String, String>>();
		for (Map<String, String> processInformation : processInformationArray) {
			int times = 0;
			Matcher matcher = cablingPattern.matcher(processInformation.get("NAME"));
			if (matcher.find()) {
				if (matcher.groupCount() >= 1) {
					times = getRepeatTimes(matcher.group(1));
				}
			} else {
				matcher = wrappPattern.matcher(processInformation.get("NAME"));
				if (matcher.find()) {
					if (matcher.groupCount() >= 1) {
						times = getRepeatTimes(matcher.group(1));
					}
				}
			}

			// 根据匹配的分解工序次数将工序拆分放入返回的list对象中
			this.putRepeatProcessToList(times, processInformation, processInformationArrayReturn);
		}
		return processInformationArrayReturn;
	}

	/**
	 * 工序分解私有方法<br/>
	 * 根据匹配的分解工序次数将工序拆分放入返回的list对象中
	 * 
	 * @param times 分解次数
	 * @param processInformation 工序Map对象
	 * @param processInformationArrayReturn 工序返回list集合
	 * @return
	 * */
	private void putRepeatProcessToList(int times, Map<String, String> processInformation,
			List<Map<String, String>> processInformationArrayReturn) {
		/**
		 * 绞向另外处理，因为要区分层一绞向，层二绞向； 根据工序名字绞向对应的进行区分
		 * */
		List<Map<String, String>> cableFaceArray = null; // 绞向
		if (processInformation.get("CODE").equals("Cabling")) { // 成缆工序查询绞向/线芯排列
			cableFaceArray = processInformationDAO.getCableFace(processInformation.get("ID"));
		}

		if (times > 1) {
			for (int j = 1; j <= times; j++) {
				Map<String, String> tmp = new HashMap<String, String>(processInformation);
				if (j == 1) { // 　这些信息就放一次，否则会重复显示的
					this.initSpecialAttr(tmp, true, cableFaceArray);
				}

				if (tmp.get("CODE").equals("Cabling") && !CollectionUtils.isEmpty(cableFaceArray)) { // 绞向不为空并且匹配上了就赋值
					for (Map<String, String> cableFace : cableFaceArray) {
						if (cableFace.get("NAME").indexOf(j + "") >= 0) {
							tmp.put("cableFaceName", cableFace.get("NAME"));
							tmp.put("cableFaceValue", cableFace.get("VALUE"));
							break;
						}
					}
				}
				tmp.put("NAME", tmp.get("NAME") + "_" + j);
				processInformationArrayReturn.add(tmp);
			}
		} else {
			this.initSpecialAttr(processInformation, false, cableFaceArray);
			processInformationArrayReturn.add(processInformation);
		}
	}

	/**
	 * 工序分解私有方法<br/>
	 * 对返回工序添加特殊属性：绞向、线芯排列、节距、搭盖率、编织密度
	 * 
	 * @param processInformation 工序Map对象
	 * @param needRepeat 是否需要拆分工序
	 * @param cableFaceArray 绞向数组对象
	 * @return
	 * 
	 * */
	// 特殊说明一下：绞向另外处理，因为要区分层一绞向，层二绞向； 根据工序名字绞向对应的进行区分
	private void initSpecialAttr(Map<String, String> processInformation, boolean needRepeat,
			List<Map<String, String>> cableFaceArray) {
		String processId = processInformation.get("ID");
		List<Map<String, String>> cableOrderArray = null; // 线芯排列
		List<Map<String, String>> wringDistArray = null; // 节距
		List<Map<String, String>> coverRateArray = null; // 搭盖率
		List<Map<String, String>> braidDensityArray = null; // 编织密度

		if (processInformation.get("CODE").equals("Cabling")) { // 成缆工序查询绞向/线芯排列
			cableOrderArray = processInformationDAO.getCableOrder(processId);
			if (!needRepeat && !CollectionUtils.isEmpty(cableFaceArray)) { // 需要分解的绞向另外处理，因为要区分层一绞向，层二绞向；
				processInformation.put("cableFaceName", cableFaceArray.get(0).get("NAME"));
				processInformation.put("cableFaceValue", cableFaceArray.get(0).get("VALUE"));
			}
			if (!CollectionUtils.isEmpty(cableOrderArray)) { // 线芯排列不为空并且匹配上了就赋值
				processInformation.put("cableOrderName", cableOrderArray.get(0).get("NAME"));
				processInformation.put("cableOrderValue", cableOrderArray.get(0).get("VALUE"));
			}
		} else if (processInformation.get("CODE").equals("Twisting")) { // 节距
			wringDistArray = processInformationDAO.getWringDist(processId);
			if (!CollectionUtils.isEmpty(wringDistArray)) { // 节距不为空并且匹配上了就赋值
				processInformation.put("wringDistName", wringDistArray.get(0).get("NAME"));
				processInformation.put("wringDistValue", wringDistArray.get(0).get("VALUE"));
			}
		} else if (processInformation.get("CODE").equals("wrapping_ymd")
				|| processInformation.get("CODE").equals("wrapping")) { // 搭盖率
			coverRateArray = processInformationDAO.getCoverRate(processId);
			if (!CollectionUtils.isEmpty(coverRateArray)) { // 搭盖率不为空并且匹配上了就赋值
				processInformation.put("coverRateName", coverRateArray.get(0).get("NAME"));
				processInformation.put("coverRateValue", coverRateArray.get(0).get("VALUE"));
			}
		} else if (processInformation.get("CODE").equals("Braiding")) { // 编织密度
			braidDensityArray = processInformationDAO.getBraidDensity(processId);
			if (!CollectionUtils.isEmpty(braidDensityArray)) { // 编织密度不为空并且匹配上了就赋值
				processInformation.put("braidDensityName", braidDensityArray.get(0).get("NAME"));
				if(braidDensityArray.get(0).get("VALUE").contains("%")){
					processInformation.put("braidDensityValue", braidDensityArray.get(0).get("VALUE"));
				}else{
					//将编织密度统一成80%
					double value = Double.parseDouble(braidDensityArray.get(0).get("VALUE"));
					if(value < 1){
						value = value*100;
					}
					processInformation.put("braidDensityValue", (int)value+"%");
				}
			}
		}
	}

	/**
	 * 工序分解私有方法<br/>
	 * 根据正则匹配到的次数判断工序分解几次
	 * 
	 * @param chineseRepeatTimes 中文成缆\绕包次数
	 * @return int 返回次数
	 * */
	private int getRepeatTimes(String chineseRepeatTimes) {
		int count = 0;
		if ("一".equals(chineseRepeatTimes)) {
			count = 1;
		} else if ("二".equals(chineseRepeatTimes)) {
			count = 2;
		} else if ("三".equals(chineseRepeatTimes)) {
			count = 3;
		} else if ("四".equals(chineseRepeatTimes)) {
			count = 4;
		} else if ("五".equals(chineseRepeatTimes)) {
			count = 5;
		} else if ("六".equals(chineseRepeatTimes)) {
			count = 6;
		} else if ("七".equals(chineseRepeatTimes)) {
			count = 7;
		} else if ("八".equals(chineseRepeatTimes)) {
			count = 8;
		} else if ("九".equals(chineseRepeatTimes)) {
			count = 9;
		}
		return count;
	}
}
