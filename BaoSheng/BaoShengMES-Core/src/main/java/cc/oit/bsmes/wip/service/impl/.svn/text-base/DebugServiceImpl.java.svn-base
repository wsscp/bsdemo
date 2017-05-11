/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.wip.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfaceWWIs.service.DataIssuedService;
import cc.oit.bsmes.opc.client.OpcClient;
import cc.oit.bsmes.opc.client.OpcParmVO;
import cc.oit.bsmes.wip.dao.DebugDAO;
import cc.oit.bsmes.wip.model.Debug;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.service.DebugService;
import cc.oit.bsmes.wip.service.ReceiptService;
import cc.oit.bsmes.wip.service.WorkOrderService;

/**
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-2-20 下午3:44:31
 * @since
 * @version
 */
@Service
public class DebugServiceImpl extends BaseServiceImpl<Debug> implements DebugService {

	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private ReceiptService receiptService;
	@Resource
	private DebugDAO debugDAO;
	@Resource
	private OpcClient opcClient;

    @Resource
    private DataIssuedService dataIssuedService;
	
	@Override
	@Transactional(readOnly=false)
	public void saveDebugInfo(Debug debug,List<Receipt> receiptArray,String operator){
		if(StringUtils.isEmpty(debug.getWorkOrderNo())){
			throw new MESException();
		}
		insert(debug);
        receiptService.issuedReceipt(receiptArray, debug.getWorkOrderNo(),true,operator);
//		WorkOrder wo = workOrderService.getByWorkOrderNO(debug.getWorkOrderNo());
//		equipInfoService.changeEquipStatusBetweenDebugAndInProgress(wo.getEquipCode(), EquipStatus.IN_DEBUG, debug.getStartTime());
	}
	
	@Override
	public Debug getByWorkOrderNoAndNullEndTime(String workOrderNo){
		return debugDAO.getByWorkOrderNoAndNullEndTime(workOrderNo);
		
	}

	@Override
	public void issueParam(Map<String, String> params) {
		String factoryName="宝胜科技创新股份有限公司";
		String productType=params.get("productType");
		String productSpec=params.get("productSpec");
		String length=params.get("length");
		String str1=factoryName;
		String str2=productType+productSpec;
		String str3=length;
		List<OpcParmVO> parmvoList=new ArrayList<OpcParmVO>();
		OpcParmVO o1=new OpcParmVO("PMJ_test.String1", str1);
		OpcParmVO o2=new OpcParmVO("PMJ_test.String2", str2);
		OpcParmVO o3=new OpcParmVO("PMJ_test.String3", str3);
		parmvoList.add(o1);
		parmvoList.add(o2);
		parmvoList.add(o3);
		opcClient.writeOpcValue(parmvoList);	
	}
}
