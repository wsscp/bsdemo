package cc.oit.bsmes.pla.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.AttachFile;

public interface AttachFileDAO extends BaseDAO<AttachFile> {

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
	public List<AttachFile> getByContractNo(Map<String, List<String>> param);
	
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
