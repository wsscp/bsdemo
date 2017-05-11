package cc.oit.bsmes.interfaceWWAc.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamHistoryAcquisition;

import java.util.List;

 
/**
 * 历史数据查询
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2014-3-27 上午11:35:39
 * @since
 * @version
 */

public interface EquipParamHistoryAcquisitionDAO extends BaseDAO<EquipParamHistoryAcquisition> {
	
	List<EquipParamHistoryAcquisition> findParamHistory(EquipParamHistoryAcquisition parm);
	List<EquipParamHistoryAcquisition> findLengthLiveData(EquipParamHistoryAcquisition parm);
	List<EquipParamHistoryAcquisition> findHistoryData(EquipParamHistoryAcquisition parm);
	
	
	
}
