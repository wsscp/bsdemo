package cc.oit.bsmes.wip.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.RealCost;

import java.util.List;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-2-21 下午5:04:26
 * @since
 * @version
 */
public interface RealCostService extends BaseService<RealCost> {

	/**
	 * <p>根据工单号查询实际物料投放情况</p> 
	 * @author QiuYangjun
	 * @date 2014-4-29 下午3:21:37
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<RealCost> getByWorkOrderNO(String workOrderNo);

    /**
     * 判断是否已完成投料
     * @param workOrderNo
     * @return
     */
    public boolean isFeedCompleted(String workOrderNo);

    List<RealCost> checkProductPutIn(String workOrderNo,String batchNo);

    int deleteByBarCode(String barCode);

    public void cancelPutMat(String  barCode);
}
