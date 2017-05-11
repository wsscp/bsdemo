package cc.oit.bsmes.pla.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.AttachFile;

public interface AttachFileService extends BaseService<AttachFile>{
	
	/**
	 * 根据订单ID[T_PLA_CUSTOMER_ORDER_ITEM]获取附件信息
	 * @author DingXintao
	 * @param orderItemId 订单ID
	 * @return List<AttachFile>
	 */
	public List<AttachFile> getByOrderItemId(String orderItemId);
	
	/**
	 * 根据合同号[T_ORD_SALES_ORDER.CONTRACT_NO]获取附件信息
	 * @author DingXintao
	 * @param contractNoList 合同号数组
	 * @return List<AttachFile>
	 */
	public List<AttachFile> getByContractNo(List<String> contractNoList);
	
	/**
	 * 根据生产单号[WORK_ORDER_NO]获取附件信息
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * @return List<AttachFile>
	 */
	public List<AttachFile> getByWorkOrderNo(String workOrderNo);
	

	List<AttachFile> getBySalesOrderId(String id);

	public List<AttachFile> getTechnique(String orderItemId);
	
}
