package cc.oit.bsmes.pla.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.OrderProcessPR;

import java.util.List;

/**
 * Created by JIN on 2015/6/30.
 */
public interface OrderProcessPRService extends BaseService<OrderProcessPR> {

	/**
	 * @Title:       insertProp
	 * @Description: TODO(特殊工艺新增数据)
	 * @param:       salesOrderItemId 订单明细id
	 * @param:       processId 工序id
	 * @param:       type 类型
	 * @param:       code 
	 * @param:       name
	 * @param:       targetValue
	 * @param:       matCode
	 * @param:       matName
	 * @param:       inOrOut   
	 * @return:      void   
	 * @throws
	 */
	public void insertSpencial(String salesOrderItemId, String processId, String type, String code, String name, String targetValue, String matCode,
			String matName, String inOrOut);
	
}
