package cc.oit.bsmes.interfaceWebClient.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfaceWebClient.model.EquipSummary;
 
/**
 *  
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2015-5-19 下午4:07:41
 * @since
 * @version
 */
public interface EquipSummaryDAO extends BaseDAO<EquipSummary> {

	List<EquipSummary> getEquipSummary(EquipSummary parm);

	List<EquipSummary> getEquipOEE(EquipSummary parm);

	 

}
