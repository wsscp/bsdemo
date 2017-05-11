package cc.oit.bsmes.fac.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.fac.model.EquipMaintenance;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author lewei
 * @date 2014-6-23 下午4:48:55
 * @since
 * @version
 */
public interface EquipMaintenanceDAO extends BaseDAO<EquipMaintenance> {

	List<EquipMaintenance> queryEquipEvent(String code);

}
