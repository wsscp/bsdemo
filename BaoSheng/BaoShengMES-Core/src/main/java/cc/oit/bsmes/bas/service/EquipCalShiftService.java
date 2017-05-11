package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by chanedi on 14-4-2.
 */
public interface EquipCalShiftService extends BaseService<EquipCalShift> {

	public List<EquipCalShift> findByRequestMap(Map<String, Object> requestMap, int start, int limit, List<Sort> sorts);
    
    public Integer countByRequestMap(Map<String, Object> requestMap);
    
    public EquipCalShift getByEquipCalendarId(String equipCalendarId);

    public void deleteByEquipCalendarId(String equipCalendarId);
}
