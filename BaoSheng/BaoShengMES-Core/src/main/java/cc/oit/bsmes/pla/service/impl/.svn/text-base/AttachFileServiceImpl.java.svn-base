package cc.oit.bsmes.pla.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pla.dao.AttachFileDAO;
import cc.oit.bsmes.pla.model.AttachFile;
import cc.oit.bsmes.pla.service.AttachFileService;

@Service
public class AttachFileServiceImpl extends BaseServiceImpl<AttachFile> implements AttachFileService{
	@Resource
	private AttachFileDAO attachFileDAO;

	/**
	 * 根据订单ID[T_PLA_CUSTOMER_ORDER_ITEM]获取附件信息
	 * @author DingXintao
	 * @param orderItemId 订单ID
	 * @return List<AttachFile>
	 */
	@Override
	public List<AttachFile> getByOrderItemId(String orderItemId) {
		return attachFileDAO.getByOrderItemId(orderItemId);
	}
	
	/**
	 * 根据合同号[T_ORD_SALES_ORDER.CONTRACT_NO]获取附件信息
	 * @author DingXintao
	 * @param contractNoList 合同号数组
	 * @return List<AttachFile>
	 */
	@Override
	public List<AttachFile> getByContractNo(List<String> contractNoList) {
		Map<String, List<String>> param = new HashMap<String, List<String>>();
		param.put("contractNoList", contractNoList);
		return attachFileDAO.getByContractNo(param);
	}
	
	/**
	 * 根据生产单号[WORK_ORDER_NO]获取附件信息
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * @return List<AttachFile>
	 */
	public List<AttachFile> getByWorkOrderNo(String workOrderNo){
		return attachFileDAO.getByWorkOrderNo(workOrderNo);
	}
	
	@Override
	public List<AttachFile> getBySalesOrderId(String id) {
		// TODO Auto-generated method stub
		return attachFileDAO.getBySalesOrderId(id);
	}

	@Override
	public List<AttachFile> getTechnique(String contractNo) {
		// TODO Auto-generated method stub
		return attachFileDAO.getTechnique(contractNo);
	}

}
