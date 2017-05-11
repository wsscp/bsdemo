package cc.oit.bsmes.pla.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pla.dao.OrderProcessPRDAO;
import cc.oit.bsmes.pla.model.OrderProcessPR;
import cc.oit.bsmes.pla.service.OrderProcessPRService;

/**
 * Created by JIN on 2015/6/30.
 */
@Service
public class OrderProcessPRServiceImpl extends BaseServiceImpl<OrderProcessPR> implements OrderProcessPRService {

	@Resource
	private OrderProcessPRDAO orderProcessPRDAO;

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
	@Override
	@Transactional(readOnly = false)
	public void insertSpencial(String salesOrderItemId, String processId, String type, String code, String name, String targetValue, String matCode,
			String matName, String inOrOut) {
		OrderProcessPR pr = new OrderProcessPR();
		pr.setSalesOrderItemId(salesOrderItemId);
		pr.setProcessId(processId);
		pr.setType(type);
		pr.setCode(code);
		pr.setName(name);
		pr.setTargetValue(targetValue);
		pr.setMatCode(matCode);
		pr.setMatName(matName);
		pr.setInOrOut(inOrOut);
		orderProcessPRDAO.insert(pr);
	}
}
