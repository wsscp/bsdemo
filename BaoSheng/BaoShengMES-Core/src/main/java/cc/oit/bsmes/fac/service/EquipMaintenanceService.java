package cc.oit.bsmes.fac.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.fac.model.EquipMaintenance;

/**
 * 
 * 设备维修统计
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-6-23 下午4:46:34
 * @since
 * @version
 */
public interface EquipMaintenanceService extends BaseService<EquipMaintenance> {

	List<EquipMaintenance> queryEquipEvent(String code);

}
