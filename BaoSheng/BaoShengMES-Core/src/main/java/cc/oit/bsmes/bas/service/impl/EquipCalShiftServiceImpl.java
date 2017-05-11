package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.EquipCalShiftDAO;
import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.bas.service.EquipCalShiftService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by chanedi on 14-4-2.
 */
@Service
public class EquipCalShiftServiceImpl extends BaseServiceImpl<EquipCalShift>
        implements EquipCalShiftService {
	
	@Resource
	private EquipCalShiftDAO equipCalShiftDAO;

	@Override
	public List<EquipCalShift> findByRequestMap(Map<String, Object> requestMap,
                                                int start, int limit, List<Sort> sorts) {
		SqlInterceptor.setRowBounds(new RowBounds(start,limit));
		return equipCalShiftDAO.findByRequestMap(requestMap);
	}

	@Override
	public Integer countByRequestMap(Map<String, Object> requestMap) {
		return equipCalShiftDAO.countByRequestMap(requestMap);
	}

	@Override
	public EquipCalShift getByEquipCalendarId(String equipCalendarId) {
		return equipCalShiftDAO.getByEquipCalendarId(equipCalendarId);
	}

	@Override
	public void deleteByEquipCalendarId(String equipCalendarId) {
		// TODO Auto-generated method stub
		equipCalShiftDAO.deleteByEquipCalendarId(equipCalendarId);
	}
}
