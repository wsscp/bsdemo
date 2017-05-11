package cc.oit.bsmes.bas.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Cell;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.dao.EquipMESWWMappingDAO;
import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JxlUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.model.EquipInfo;

@Service
public class EquipMESWWMappingServiceImpl extends BaseServiceImpl<EquipMESWWMapping> implements
		EquipMESWWMappingService {

	@Resource
	private EquipMESWWMappingDAO equipMESWWMappingDAO;

	@Override
	public List<EquipMESWWMapping> findByRequestMap(Map<String, Object> requestMap, int start, int limit,
			List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return equipMESWWMappingDAO.findByRequestMap(requestMap);
	}

	@Override
	public Integer countByRequestMap(Map<String, Object> requestMap) {
		return equipMESWWMappingDAO.countByRequestMap(requestMap);
	}

	@Override
	public EquipMESWWMapping getByTagName(String tagName) {
		return equipMESWWMappingDAO.getByTagName(tagName);
	}

	@Override
	public EquipMESWWMapping getByEquipCodeAndParmCode(String equipCode, String parmCode) {
		return equipMESWWMappingDAO.getByEquipCodeAndParmCode(equipCode, parmCode);
	}

	@Override
	public void deleteAll() {
		equipMESWWMappingDAO.deleteAll();

	}

	@Override
	public void autoGenerate() {
		equipMESWWMappingDAO.autoGenerate();

	}

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
	@Override
	public void importEquipMESWWMapping(List<Cell[]> qcList, String equipCode, String acEquipCode) {
		EquipInfo equipInfo = StaticDataCache.getMainEquipInfo(equipCode); // 获取设备信息
		// 循环excel行封装工艺对象
		if (null != equipInfo) { // 更新设备工艺参数映射关系表
			// 删除此设备对应的映射关系
			equipMESWWMappingDAO.deleteByAcEquipCode(acEquipCode);

			for (int loop = 0; loop < qcList.size(); loop++) {
				Cell[] cells = qcList.get(loop);
				EquipMESWWMapping equipMESWWMapping = new EquipMESWWMapping();
				equipMESWWMapping.setAcEquipCode(acEquipCode);
				equipMESWWMapping.setTagName(acEquipCode + "." + JxlUtils.getRealContents(cells[1]));
				equipMESWWMapping.setEquipCode(equipInfo.getCode());
				equipMESWWMapping.setParmName(JxlUtils.getRealContents(cells[2]));
				equipMESWWMapping.setParmCode(JxlUtils.getRealContents(cells[1]));
				equipMESWWMapping.setNeedDa("是".equals(JxlUtils.getRealContents(cells[6])));
				equipMESWWMapping.setNeedIs("是".equals(JxlUtils.getRealContents(cells[7])));
				String dataType = JxlUtils.getRealContents(cells[8]);
				if ("布尔型".equalsIgnoreCase(dataType)) {
					equipMESWWMapping.setDataType("BOOLEAN");
				} else if ("数字型".equalsIgnoreCase(dataType)) {
					equipMESWWMapping.setDataType("DOUBLE");
				} else {
					equipMESWWMapping.setDataType("STRING");
				}
				equipMESWWMapping.setNeedShow("是".equals(JxlUtils.getRealContents(cells[14])));
				// equipMESWWMapping.setEventType(eventType);
				this.insert(equipMESWWMapping);
			}
		}
	}
	
	
	/**
	 * 根据设备和参数获取标签名
	 * 
	 * @author DingXintao
	 * @param equipCode 设备编码
	 * @param paramCodes 真实设备编码
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, String> getTagNameByEquipCodeParams(String equipCode, List<String> paramCodes){
		Map param = new HashMap();
		param.put("equipCode", equipCode);		
		param.put("paramCodes", paramCodes);
		return equipMESWWMappingDAO.getTagNameByEquipCodeParams(param);
	}
	
	/**
	 * @Title:       getDataForEvent
	 * @Description: TODO(报警处理后台任务: 获取事件类型不为空的数据)
	 * @return:      List<EquipMESWWMapping>   
	 * @throws
	 */
	public List<EquipMESWWMapping> getDataForEvent(){
		return equipMESWWMappingDAO.getDataForEvent();
	}

}
