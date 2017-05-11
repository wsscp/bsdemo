package cc.oit.bsmes.fac.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.fac.model.EquipEnergyMonitor1;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.EquipRepairRecord;
import cc.oit.bsmes.fac.model.MaintainTemplate;
import cc.oit.bsmes.fac.model.SparePart;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 设备信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2013-12-24 下午2:46:31
 * @since
 * @version
 */
public interface EquipInfoService extends BaseService<EquipInfo> {

	/**
	 * 通过设备编码获取设备信息
	 * 
	 * @author leiwei
	 * @date 2013年12月31日 下午16:00:21
	 * @param code 设备编码
	 * @param orgCode
	 * @return
	 * @see
	 */
	public EquipInfo getByCode(String code, String orgCode);

	/**
	 * 通过工序id获取工序的设备信息，开始时间大于当前时间，按照任务负载由大到小排列
	 * 
	 * @author leiwei
	 * @date 2014-1-6 下午3:47:42
	 * @param processId 工序id
	 * @return
	 * @see
	 */
	public List<EquipInfo> getByWipProcessId(String processId);

	/**
	 * 获取组织下的所有设备。
	 * 
	 * @author chanedi
	 * @date 2014年1月8日 下午7:09:03
	 * @param orgCode
	 * @return
	 * @see
	 */
	public List<EquipInfo> getByOrgCode(String orgCode, EquipType type);

	/**
	 * 获取组织下的所有设备。
	 * 
	 * @author chanedi
	 * @date 2014年1月8日 下午7:09:03
	 * @param orgCode
	 * @return
	 * @see
	 */
	public List<EquipInfo> getByProcessSection(String orgCode, EquipType type, String processCode);

	/**
	 * 初始化设备负载，计算锁定订单
	 * 
	 * @author chanedi
	 * @date 2014年2月17日 下午3:48:43
	 * @param orgCode
	 * @see
	 */
	public void initEquipLoad(String orgCode);

	/**
	 * 计算OA前调用存储过程：删除组织下设备产能
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param orgCode 组织编码
	 */
	public void callInitOrderTask(String orgCode);

	/**
	 * <p>
	 * 更改设备状态
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-26 上午11:45:04
	 * @param equipLineCode 生产线CODE
	 * @param status
	 * @param statusStartTime
	 * @see
	 */
	public void changeEquipStatus(String equipLineCode, EquipStatus status, String operator, boolean needAlarm);

	/**
	 * <p>
	 * 更改设备状态
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-26 上午11:45:04
	 * @param equipCode 生产线代码
	 * @param statusStartTime
	 * @see
	 */
	public void changeEquipStatusBetweenDebugAndInProgress(String equipCode, String operator);

	/**
	 * 
	 * <p>
	 * 接受任务
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-4 上午10:38:51
	 * @param equipCode
	 * @see
	 */
	public JSONObject mesClientAcceptTask(String equipCode, String woNo, String operator);

	/**
	 * 
	 * <p>
	 * 根据工序获取设备
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-3-24 下午2:48:07
	 * @param processId
	 * @return
	 * @see
	 */
	public List<EquipInfo> findByProcessId(String processId);

	public List<EquipInfo> findByName(String name);

	/**
	 * <p>
	 * 查询全部生产线信息
	 * </p>
	 * 
	 * @author zhangdongping
	 * @date 2014-6-26 下午12:00:42
	 * @return
	 * @see
	 */
	public List<EquipInfo> getAllProductLine();

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author 根据工序id和订单明细工序用时分解id获取设备计划加工任务负载name
	 * @date 2014-3-25 下午1:41:44
	 * @param processId
	 * @param id
	 * @return
	 * @see
	 */
	public String getEquipNameByProcessIdAndOrderItemProDecId(String processId, String id);

	/**
	 * 根据生产线查询主设备
	 * 
	 * @author Jinhy
	 * @param equipLineCode
	 * @return
	 */
	public EquipInfo getMainEquipByEquipLine(String equipLineCode);

	public List<EquipInfo> getTotalByStatus(String orgCode, String[] equipStatus);

	/**
	 * 通过设备查询该设备生产线
	 * 
	 * @param equipCode
	 * @return
	 */
	public EquipInfo getEquipLineByEquip(String equipCode);

	/**
	 * 根据工序查询 设备 用于 质量参数导入
	 * 
	 * @param processId
	 * @return
	 */
	public List<EquipInfo> getEquipInfoByProcessId(String processId);

	public void createMaintainEvent(String orgCode);

	public Date fixLastMaintainDate(EquipInfo equip, MaintainTemplate maintainTemplate, Date lastMaintainDate);

	/**
	 * 获取到endDate为止的所有设备维修日
	 * 
	 * @param equipInfo
	 * @param endDate
	 * @return
	 */
	public List<Date> getMaintainDates(EquipInfo equipInfo, Date endDate);

	/**
	 * 获取到之前状态
	 * 
	 * @param equipCode
	 */
	public void reverseEquipStatus(String equipCode);

	public void fixNextMaintainDate(Date now, EquipInfo equipInfo);

	/**
	 * 
	 * <p>
	 * TODO(根据code或name模糊查询)
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-8-26 下午2:55:06
	 * @param name
	 * @return
	 * @see
	 */
	public List<EquipInfo> getByNameOrCode(String name, String orgCode, EquipType type);

	/**
	 * 
	 * <p>
	 * 查询某角色下的生产线
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-9-16 下午2:04:23
	 * @param sortList
	 * @return
	 * @see
	 */
	public List<EquipInfo> getByRoleId(EquipInfo equipInfo, int start, int limit, List<Sort> sortList);

	public int countByRoleId(EquipInfo equipInfo);

	public EquipInfo findForDataUpdate(String code, String type);

	public List<EquipInfo> findForVF(String[] equipCodes, String orgCode);

	public List<JSONObject> findEquipByUser(String userCode);

	/**
	 * 获取生产线信息
	 * */
	public List<EquipInfo> getEquipLine(Map<String, Object> param);

	public List<EquipInfo> getMergeEquipByProcessCode(String processCode, String orgCode);

	/**
	 * 
	 * 获取工段可选设备 : 排生产单（包含工序）
	 * 
	 * @author DingXintao
	 * @param section 工段
	 * 
	 * @return List<EquipInfo>
	 */
	public List<EquipInfo> getEquipByProcessSection(String section);
	
	/**
	 * 
	 * 获取工段可选设备 : 排生产单(不包含工序)
	 * 
	 * @author 宋前克
	 * @param section 工段
	 * 
	 * @return List<EquipInfo>
	 */
	public List<EquipInfo> getEquipByProcessSectionN(String section);

	/**
	 * 
	 * 获取工段可选设备 : 生产单切换设备
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * 
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getEquipForChangeOrderEquip(String workOrderNo);

	public void insetRecord(EquipRepairRecord r);

	public void insetSparePart(SparePart s);

	public List<SparePart> querySpareParts(String eventInfoId);

	public void deleteSparePart(String id);

	public List<EquipRepairRecord> getEventInfo(String id);

	public List<EquipInfo> findInfo(EquipInfo param, Integer start, Integer limit,
			List<Sort> parseArray);

	public int countInfo(EquipInfo param);

	public int getRecordsByEventInfoId(String eventInfoId);
	
	public List<EquipInfo> getSpecificEquip(Map<String,Object> params);

	public List<Map<String,String>> getByWorkOrder(Map<String, Object> param1);

	public String getEquipParamByCode(String code);

	public List<Map<String, String>> getEquipParams();

//	public List<EquipEnergyMonitor1> getEquipEnergyMonitor(Map<String, Object> findParams);

//	public int countEquipEnergyMonitor(String name);

//	public Map<String, Object> energyReceiptChart(String equipName);

}
