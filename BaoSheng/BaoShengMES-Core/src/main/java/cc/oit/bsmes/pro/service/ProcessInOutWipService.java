package cc.oit.bsmes.pro.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.pro.model.ProcessInOutWip;

public interface ProcessInOutWipService extends BaseService<ProcessInOutWip> {

	
	/**
	 * @Title:       getByProcessId
	 * @Description: TODO(工序ID获取投入产出)
	 * @param:       processId 工序ID
	 * @return:      List<ProcessInOut>   
	 * @throws
	 */
	public List<ProcessInOutWip> getByProcessId(String processId);
	
	/**
	 * @Title: insert
	 * @Description: TODO(更新投入产出信息)
	 * @param: processInOutWip 投入产出更新参数: salesOrderItemId 客户订单id, oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return: UpdateResult
	 * @throws
	 */
	public UpdateResult insertProcessInOut(ProcessInOutWip processInOutWip);
	
	/**
	 * @Title: update
	 * @Description: TODO(更新投入产出信息)
	 * @param: processInOutWip 投入产出更新参数: salesOrderItemId 客户订单id, oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return: UpdateResult
	 * @throws
	 */
	public UpdateResult updateProcessInOut(ProcessInOutWip processInOutWip);

}
