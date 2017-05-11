package cc.oit.bsmes.interfaceWWAc.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamAcquisition;

import java.util.Date;
import java.util.List;

/**
 * Created by chanedi on 14-3-13.
 */
public interface EquipParamAcquisitionDAO extends BaseDAO<EquipParamAcquisition> {
	
	/**
	 * 根据标签获取实时数据
	 * */ 
	public List<EquipParamAcquisition> findLiveValue(List<String> tagName, Date startTime, Date endTime);
	
	/**
	 * 根据标签获取实时数据：目的，更新设备状态
	 * 
	 * @param tagName 标签名称
	 * @param equipCode 设备编码
	 * */ 
}
