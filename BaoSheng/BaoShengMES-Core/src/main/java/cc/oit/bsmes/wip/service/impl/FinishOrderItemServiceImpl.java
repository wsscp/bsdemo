package cc.oit.bsmes.wip.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.FinishOrderItemDAO;
import cc.oit.bsmes.wip.model.FinishOrderItem;
import cc.oit.bsmes.wip.model.FinishOrderItemErpModel;
import cc.oit.bsmes.wip.service.FinishOrderItemService;


@Service
public class FinishOrderItemServiceImpl  extends BaseServiceImpl<FinishOrderItem> implements FinishOrderItemService{

	@Resource
	private FinishOrderItemDAO finishOrderItemDAO;
	/**
	 * 获取要推送的订单信息
	 * @author 前克
	 * @date 2016-05-25
	 * */
	public List<FinishOrderItemErpModel> getNeedPushOrderItems()
	{
		return finishOrderItemDAO.getNeedPushOrderItems();
	}
}
