package cc.oit.bsmes.pla.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.ToolsRequirementPlan;
/**
 * 
 * 辅助工具需求清单
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-1-22 下午6:04:13
 * @since
 * @version
 */
public interface ToolsRequirementPlanService extends BaseService<ToolsRequirementPlan> {
	/**
	 * 根据机构代码获取辅助工具需求清单
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author leiwei
	 * @date 2014-1-22 下午6:04:32
	 * @param orgCode 机构代码
	 * @return
	 * @see
	 */
	List<ToolsRequirementPlan> findByOrgCode(String orgCode);

	/**
	 * <p>根据生产单号查询工装夹具</p> 
	 * @author QiuYangjun
	 * @date 2014-2-10 下午3:18:04
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<ToolsRequirementPlan> getByWorkOrderNo(String workOrderNo);

}
