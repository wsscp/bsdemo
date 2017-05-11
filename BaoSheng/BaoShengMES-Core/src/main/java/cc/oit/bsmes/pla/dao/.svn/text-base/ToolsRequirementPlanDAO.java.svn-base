package cc.oit.bsmes.pla.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.ToolsRequirementPlan;
/**
 * 
 * ToolsRequirementPlanDAO
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-1-22 下午6:09:07
 * @since
 * @version
 */
public interface ToolsRequirementPlanDAO extends BaseDAO<ToolsRequirementPlan> {

	List<ToolsRequirementPlan> findByOrgCode(String orgCode);

	/**
	 * <p>根据生产单号查询工装夹具</p> 
	 * @author QiuYangjun
	 * @date 2014-2-10 下午3:17:29
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<ToolsRequirementPlan> getByWorkOrderNo(String workOrderNo);

}
