package cc.oit.bsmes.wip.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.Debug;
import cc.oit.bsmes.wip.model.Receipt;

import java.util.List;
import java.util.Map;

public interface DebugService extends BaseService<Debug> {

	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author QiuYangjun
	 * @date 2014-2-26 下午3:45:09
	 * @param debug
	 * @param receiptArray
	 * @see
	 */
	public void saveDebugInfo(Debug debug,List<Receipt> receiptArray,String operator);

	/**
	 * <p>根据工单号查询最后一次未结束的调试信息</p> 
	 * @author QiuYangjun
	 * @date 2014-3-5 上午11:49:12
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public Debug getByWorkOrderNoAndNullEndTime(String workOrderNo);

	public void issueParam(Map<String, String> params);
	
}
