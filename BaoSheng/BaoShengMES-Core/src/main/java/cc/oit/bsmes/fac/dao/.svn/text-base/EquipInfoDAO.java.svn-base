package cc.oit.bsmes.fac.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.bas.model.EquipEnergyMonitor;
import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.fac.model.EquipEnergyMonitor1;
import cc.oit.bsmes.fac.model.EquipInfo;

/**
 * EquipInfoDAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2013年12月31日 下午3:43:43
 * @since
 * @version
 */
public interface EquipInfoDAO extends BaseDAO<EquipInfo> {

	public List<EquipInfo> getEquipInfosByWipProcessId(String processId);

	public void initEquipLoad(String orgCode);

	public List<EquipInfo> findByName(String name);

	public List<EquipInfo> findByProcessId(String processId);

	public String getEquipNameByProcessIdAndOrderItemProDecId(Map<String, Object> map);

	/**
	 * 根据生产线查询主设备
	 * 
	 * @author Jinhy
	 * @param equipLineCode
	 * @return
	 */
	public EquipInfo getMainEquipByEquipLine(String equipLineCode);

	public List<EquipInfo> getEquipLine(Map<String, Object> map);

	@SuppressWarnings("rawtypes")
	public List<EquipInfo> getTotalByStatus(Map map);

	/**
	 * 根据设备编码和组织机构获取生产线
	 * 
	 * @param map equipCode 生产线编码/orgCode 组织机构
	 * */
	@SuppressWarnings("rawtypes")
	public EquipInfo getEquipLineByEquip(Map map);

	/**
	 * 根据工序查询 设备 用于 质量参数导入
	 * 
	 * @param processId
	 * @return
	 */
	public List<EquipInfo> getEquipInfoByProcessId(String processId);

	public List<EquipInfo> getByNameOrCode(Map<String, Object> map);

	public List<EquipInfo> getByRoleId(EquipInfo equipInfo);

	public int countByRoleId(EquipInfo equipInfo);

	public EquipInfo findForDataUpdate(String code, String type);

	public List<EquipInfo> findForVF(Map<String, Object> map);

	public List<EquipInfo> findEquipByUser(String userCode);

	public List<EquipInfo> getMergeEquipByProcessCode(String processCode, String orgCode);

	public List<EquipInfo> getEquipBySection(String processCode, String orgCode);

	/**
	 * 
	 * 获取工段可选设备 : 排生产单(包含工序)
	 * 
	 * @author DingXintao
	 * @param map 参数：processSection/orgCode
	 * 
	 * @return List<EquipInfo>
	 */
	public List<EquipInfo> getEquipByProcessSection(Map<String, Object> param);
	
	/**
	 * 
	 * 获取工段可选设备 : 排生产单(不包含工序)
	 * 
	 * @author DingXintao
	 * @param map 参数：processSection/orgCode
	 * 
	 * @return List<EquipInfo>
	 */
	public List<EquipInfo> getEquipByProcessSectionN(Map<String, Object> param);

	/**
	 * 
	 * 获取工段可选设备 : 生产单切换设备
	 * 
	 * @author DingXintao
	 * @param map 参数：orgCode/workOrderNo
	 * 
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getEquipForChangeOrderEquip(Map<String, Object> param);

	void updateEquipStatus(String status, String equipCode, String operator);

	public List<EquipInfo> findInfo(String section);

	public int countInfo(String section);
	
	public List<EquipInfo> getSpecificEquip(Map<String, Object> params);

	public void insertEquipEnergyMonitorInfo(Map<String, Object> findParams);

	public List<EquipEnergyMonitor1> getEquipEnergyMonitor(Map<String, Object> findParams);

	public int countEquipEnergyMonitor(String name);
	public List<Map<String,String>> getByWorkOrder(Map<String, Object> param1);

	public String getEquipParamByCode(String code);

	public List<Map<String, String>> getEquipParams();

//	public List<EquipEnergyMonitor2> findEnergyHistory(EquipEnergyMonitor2 findparams);
}
