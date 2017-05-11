package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Cell;

public interface EquipMESWWMappingService extends BaseService<EquipMESWWMapping> {

	public List<EquipMESWWMapping> findByRequestMap(Map<String, Object> requestMap, int start, int limit,
			List<Sort> sortList);

	public Integer countByRequestMap(Map<String, Object> requestMap);

	public EquipMESWWMapping getByTagName(String tagName);

	public EquipMESWWMapping getByEquipCodeAndParmCode(String equipCode, String parmCode);

	public void deleteAll();

	public void autoGenerate();

	/**
	 * <p>
	 * 导入设备工艺参数映射表: T_INT_EQUIP_MES_WW_MAPPING
	 * </p>
	 * 
	 * @author DingXintao
	 * @param qcList
	 * @param equipCode 设备编码
	 * @param acEquipCode 真实设备编码
	 * @param orgCode
	 * @see
	 */
	public void importEquipMESWWMapping(List<Cell[]> qcList, String equipCode, String acEquipCode);
	
	/**
	 * 根据设备和参数获取标签名
	 * 
	 * @author DingXintao
	 * @param equipCode 设备编码
	 * @param paramCodes 真实设备编码
	 */
	public Map<String, String> getTagNameByEquipCodeParams(String equipCode, List<String> paramCodes);
	
	/**
	 * @Title:       getDataForEvent
	 * @Description: TODO(报警处理后台任务: 获取事件类型不为空的数据)
	 * @return:      List<EquipMESWWMapping>   
	 * @throws
	 */
	public List<EquipMESWWMapping> getDataForEvent();
	
}
