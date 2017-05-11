package cc.oit.bsmes.interfaceWWIs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.dao.ParmsMappingDAO;
import cc.oit.bsmes.bas.model.ParmsMapping;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.StandardParamCode;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfaceWWIs.dao.EquipIssueParamDAO;
import cc.oit.bsmes.interfaceWWIs.model.EquipIssueParam;
import cc.oit.bsmes.opc.client.OpcClient;
import cc.oit.bsmes.opc.client.OpcParmVO;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.model.Receipt;

/**
 * 数据下发接口实现类
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2014-3-24 下午5:34:09
 * @since
 * @version
 */
@Service
public class DataIssuedServiceImpl    implements DataIssuedService {
	@Resource
	EquipIssueParamDAO equipIssueParamDAO;
	@Resource
	OpcClient opcClient;
	
	@Resource
	WorkOrderDAO workOrderDAO;
	
	private static String ISSED_DATA="1";
	private static String NEED_ALARM="1";//
	private static String NO_ALARM="0";//不报警
	private static String ALARM_FLG_PREFIX="A";
	@Resource
	ParmsMappingDAO parmsMappingDAO;
	@Resource
	EquipInfoService equipInfoService;
	
     
	@Override
	@Transactional(readOnly = false)
	public void IssuedParms(List<Receipt> receipts, Boolean isDebug) {	 
		if(receipts==null || receipts.size() == 0)
		{
			return ;
		}
        //String workOrderId=receipts.get(0).getWorkOrderId();
        //WorkOrder workOrder = workOrderDAO.getById(workOrderId) ;
		Date operateDate=new Date(); 
		User user = SessionUtils.getUser();
		String  operater=user.getUserCode(); 
		Map<String ,String> equipAcCodeMap=new HashMap<String ,String>();
		List<EquipIssueParam> needInsertList=new ArrayList<EquipIssueParam>();
		
		
		 //prepare data
		for(int i=0;i<receipts.size();i++)
		{			
			Receipt receipt = receipts.get(i);
			  
			ParmsMapping findParams = new ParmsMapping();
			findParams.setEquipCode(receipt.getEquipCode());
			findParams.setParmCode(receipt.getReceiptCode());
			ParmsMapping mappngobject = parmsMappingDAO.getOne(findParams);
			if(mappngobject==null)
			{
				throw new RuntimeException("设备："+receipt.getEquipCode()+"的参数："+receipt.getReceiptCode()+"没有在映射表T_INT_EQUIP_MES_WW_MAPPING中配置映射信息！");				
			} 
			 
			    equipAcCodeMap.put(mappngobject.getAcEquipCode(), mappngobject.getAcEquipCode());
				EquipIssueParam issedParm=new EquipIssueParam();  
				issedParm.setParmCode(mappngobject.getTagName().substring(mappngobject.getAcEquipCode().length()+1));
				if(StringUtils.isBlank(receipt.getReceiptTargetValue())&&issedParm.getParmCode().startsWith("W_"))
				{
					continue;
				}
				if(StringUtils.isBlank(receipt.getReceiptMaxValue())&&StringUtils.isBlank(receipt.getReceiptMinValue())&&issedParm.getParmCode().startsWith("R_"))
				{
					continue;
				}
				issedParm.setTargetValue(receipt.getReceiptTargetValue()); 
				issedParm.setUpValue(receipt.getReceiptMaxValue());
				issedParm.setDownValue(receipt.getReceiptMinValue());
				issedParm.setEquipCode(mappngobject.getAcEquipCode()); 
				issedParm.setWorkOrderId(receipt.getWorkOrderId());
				
				if(isDebug)
				{
					issedParm.setNeedAlarm(Boolean.FALSE);
				}else
				{
					issedParm.setNeedAlarm(receipt.getNeedAlarm());
				}				
				issedParm.setCreateTime(operateDate);
				issedParm.setCreateUserCode(operater);
				issedParm.setModifyTime(operateDate);
				issedParm.setModifyUserCode(operater);		
				
				/*
				//计米器设定 计米器设定  W_OrderL
				if("W_OrderL".equalsIgnoreCase(issedParm.getParmCode()))
				{
					issedParm.setTargetValue(workOrder.getOrderLength().toString()); 
					issedParm.setUpValue("");
					issedParm.setDownValue("");
					issedParm.setNeedAlarm(false);
				}
				//订单编号 M_OrderN  
				if("M_OrderN".equalsIgnoreCase(issedParm.getParmCode()))
				{  
					issedParm.setUpValue("");
				    issedParm.setDownValue("");
					issedParm.setTargetValue(workOrder.getWorkOrderNo()); 
					issedParm.setNeedAlarm(false);
				}
				
				String halfProductCdoe=workOrder.getHalfProductCode();
				if("M_OrderM".equalsIgnoreCase(issedParm.getParmCode()))
				{  
					//半成品 产品型号 产品型号 M_OrderM  无型号，直接写入半成品代码				
					issedParm.setUpValue("");
				    issedParm.setDownValue("");
					issedParm.setTargetValue(halfProductCdoe); 
					issedParm.setNeedAlarm(false);
				}
				if("M_OrderS".equalsIgnoreCase(issedParm.getParmCode()))
				{  
					//半成品 产品规格 产品规格 M_OrderS  
					Map<String, Mat> matMap = StaticDataCache.getMatMap();
					Mat matInfo = matMap.get(halfProductCdoe);
					issedParm.setUpValue("");
				    issedParm.setDownValue("");
					if(matInfo!=null)
					{
						issedParm.setTargetValue(matInfo.getMatSpec()); 
					}else
					{
						issedParm.setTargetValue(""); 
					}		 
					issedParm.setNeedAlarm(false);
				}
				
				if("M_Worker".equalsIgnoreCase(issedParm.getParmCode()))
				{  
					//半成品 产品型号 产品型号 M_OrderM  无型号，直接写入半成品代码				
					issedParm.setUpValue("");
				    issedParm.setDownValue("");
					issedParm.setTargetValue(workOrder.getAuditUserCode()); 
					issedParm.setNeedAlarm(false);
				} 				
				*/
				needInsertList.add(issedParm);
							
			 		
			
		}
		//delete old data
		Iterator<String> itdelete = equipAcCodeMap.keySet().iterator();
		while(itdelete.hasNext())
		{
			String value = itdelete.next();
			EquipIssueParam parm = new EquipIssueParam();
			//得到设备的在WW系统中定义的代码，然后当做条件查询
			parm.setEquipCode(value); 
			parm.setNeedAlarm(null);
			List<EquipIssueParam> oldParmList=equipIssueParamDAO.get(parm);			 
			for(int j=0;j<oldParmList.size();j++)
			{
				EquipIssueParam parmtemp = oldParmList.get(j);
				equipIssueParamDAO.delete(parmtemp.getId());
			 
			}			
		}
		//EquipIssueParam issedParmTemplete =needInsertList.get(0);
		
		//insert new data
		
		for(int i=0;i<needInsertList.size();i++)
		{
			equipIssueParamDAO.insert(needInsertList.get(i));
		}
		
		//计米器设定 计米器设定  W_OrderL
//		issedParmTemplete.setId(null);   
//		issedParmTemplete.setParmCode("W_OrderL");
//		issedParmTemplete.setTargetValue(workOrder.getOrderLength().toString()); 
//		issedParmTemplete.setUpValue("");
//		issedParmTemplete.setDownValue("");
//		issedParmTemplete.setNeedAlarm(false);
//		equipIssueParamDAO.insert(issedParmTemplete);
		
		//订单编号 M_OrderN  
//		issedParmTemplete.setId(null);   
//		issedParmTemplete.setParmCode("M_OrderN");
//		issedParmTemplete.setTargetValue(workOrder.getWorkOrderNo()); 
//		issedParmTemplete.setNeedAlarm(false);
//		equipIssueParamDAO.insert(issedParmTemplete);
		
	
//		String halfProductCdoe=workOrder.getHalfProductCode();
//		//半成品 产品型号 产品型号 M_OrderM  无型号，直接写入半成品代码
//		issedParmTemplete.setId(null);   
//		issedParmTemplete.setParmCode("M_OrderM");
//		issedParmTemplete.setTargetValue(halfProductCdoe); 
//		issedParmTemplete.setNeedAlarm(false);
//		equipIssueParamDAO.insert(issedParmTemplete);
		
//		Map<String, Mat> matMap = StaticDataCache.getMatMap();
//		Mat matInfo = matMap.get(halfProductCdoe);
//		//半成品 产品规格 产品规格 M_OrderS  
//		issedParmTemplete.setId(null);   
//		issedParmTemplete.setParmCode("M_OrderS");
//		if(matInfo!=null)
//		{
//			issedParmTemplete.setTargetValue(matInfo.getMatSpec()); 
//		}else
//		{
//			issedParmTemplete.setTargetValue(""); 
//		}		
//		issedParmTemplete.setNeedAlarm(false);
//		equipIssueParamDAO.insert(issedParmTemplete);

		//制单人员 制单人员 M_Worker  
		
//		issedParmTemplete.setId(null);   
//		issedParmTemplete.setParmCode("M_Worker");
//		issedParmTemplete.setTargetValue(workOrder.getAuditUserCode()); 
//		issedParmTemplete.setNeedAlarm(false);
//		equipIssueParamDAO.insert(issedParmTemplete);
		
		
		//send message to opc server 		
		
		Iterator<String> it = equipAcCodeMap.keySet().iterator();
		while(it.hasNext())
		{			
		 String equipCode = it.next();
		 String alarmEquipCode=ALARM_FLG_PREFIX+equipCode.substring(1, equipCode.length());
		 if(isDebug)
			{
			 //不需要报警 TODO			 
			 List<OpcParmVO> parmvoList =new ArrayList<OpcParmVO>();  
			 OpcParmVO vo1=new OpcParmVO(alarmEquipCode,NO_ALARM);
			 parmvoList.add(vo1);	
			 OpcParmVO vo2=new OpcParmVO(equipCode,ISSED_DATA);
			 parmvoList.add(vo2);	
		 
			 opcClient.writeOpcValue( parmvoList);
			}else
			{
			//开始报警 TODO
			 List<OpcParmVO> parmvoList =new ArrayList<OpcParmVO>();  
			 OpcParmVO vo1=new OpcParmVO(alarmEquipCode,NEED_ALARM);
			 parmvoList.add(vo1);
			 opcClient.writeOpcValue(parmvoList);			
			}
		
		}
	} 
	
	
	public void UpdateEquipAlarmState(String equipCode ,EquipStatus et)
	{
		
//		EquipInfo mainequip1 = equipInfoService.getMainEquipByEquipLine(productLineCode);
//		if(mainequip==null)
//		{
//			return;
//		}
		ParmsMapping findParams = new ParmsMapping();
		findParams.setEquipCode(equipCode);
		findParams.setParmCode(StandardParamCode.R_Length.name());		
		ParmsMapping mappngobject = parmsMappingDAO.getOne(findParams);
		if(mappngobject==null)
		{
			return;				
		} 
		String acEquipCode=mappngobject.getAcEquipCode();
		 String alarmEquipCode=ALARM_FLG_PREFIX+acEquipCode.substring(1, acEquipCode.length());
		 //alarmEquipCode="TEST.G.Bucket Brigade.Boolean";
		 List<OpcParmVO> parmvoList =new ArrayList<OpcParmVO>();  
		 if(EquipStatus.IN_PROGRESS.name().equalsIgnoreCase(et.name()))
		 {
			 OpcParmVO vo=new OpcParmVO(alarmEquipCode,NEED_ALARM);
			 parmvoList.add(vo);			 
			 opcClient.writeOpcValue(parmvoList);
		 }else
		 { 
			 OpcParmVO vo=new OpcParmVO(alarmEquipCode,NO_ALARM);
		     parmvoList.add(vo);		
			 opcClient.writeOpcValue(parmvoList);
		 }
		 
		
	}
	

	
	
}
