package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;
/**
 * 
 * 设备班次
 * <p style="display:none">modifyRecord</p>
 * @author Administrator
 * @date 2014-1-13 下午1:01:51
 * @since
 * @version
 */
public interface EquipCalShiftDAO extends BaseDAO<EquipCalShift> {
	
	public List<EquipCalShift> findByRequestMap(Map<String, Object> requestMap);
    
    public Integer countByRequestMap(Map<String, Object> requestMap);
    
    public EquipCalShift getByEquipCalendarId(String equipCalendarId);
    
    public void deleteByEquipCalendarId(String equipCalendarId);

}
