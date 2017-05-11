package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;

public interface EquipMESWWMappingDAO extends BaseDAO<EquipMESWWMapping> {
	
	public List<EquipMESWWMapping> findByRequestMap(Map<String, Object> requestMap);
	
	public Integer countByRequestMap(Map<String, Object> requestMap);
	
	public EquipMESWWMapping getByTagName(String tagName);
	
	public EquipMESWWMapping getByEquipCodeAndParmCode(String equipCode,String parmCode);

	public void  autoGenerate();
	
	/**
	 * 删除设备下面相关的映射关系
	 * @param acEquipCode 数采的设备代号
	 * */
	public void deleteByAcEquipCode(String acEquipCode);
	
	/**
	 * 根据设备和参数获取标签名
	 * 
	 * @author DingXintao
	 * @param equipCode 设备编码
	 * @param paramCodes 真实设备编码
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, String> getTagNameByEquipCodeParams(Map param);
	
	/**
	 * @Title:       getDataForEvent
	 * @Description: TODO(报警处理后台任务: 获取事件类型不为空的数据)
	 * @return:      List<EquipMESWWMapping>   
	 * @throws
	 */
	public List<EquipMESWWMapping> getDataForEvent();
}
