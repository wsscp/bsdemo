package cc.oit.bsmes.fac.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.fac.dao.EquipMaintenanceDAO;
import cc.oit.bsmes.fac.model.EquipMaintenance;
import cc.oit.bsmes.fac.service.EquipMaintenanceService;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-6-23 下午4:47:43
 * @since
 * @version
 */
@Service
public class EquipMaintenanceServiceImpl extends BaseServiceImpl<EquipMaintenance> implements EquipMaintenanceService {
	
	@Resource private EquipMaintenanceDAO equipMaintenanceDAO;
	@Override
	public List<EquipMaintenance> findForExport(JSONObject queryFilter) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
		EquipMaintenance equipMaintenance=JSON.toJavaObject(queryFilter, EquipMaintenance.class);
		return equipMaintenanceDAO.find(equipMaintenance);
	}
	@Override
	public List<EquipMaintenance> queryEquipEvent(String code) {
		// TODO Auto-generated method stub
		return equipMaintenanceDAO.queryEquipEvent(code);
	}
}
