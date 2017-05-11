package cc.oit.bsmes.ord.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.inv.dao.MatDAO;
import cc.oit.bsmes.inv.dao.MatPropDAO;
import cc.oit.bsmes.inv.dao.MatPropWipDAO;
import cc.oit.bsmes.inv.dao.MatWipDAO;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.model.MatProp;
import cc.oit.bsmes.inv.model.MatPropWip;
import cc.oit.bsmes.inv.model.MatWip;
import cc.oit.bsmes.ord.dao.SalesOrderItemDAO;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.pla.dao.ProductDAO;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pro.dao.EquipListDAO;
import cc.oit.bsmes.pro.dao.EquipListWipDAO;
import cc.oit.bsmes.pro.dao.ProcessInOutDAO;
import cc.oit.bsmes.pro.dao.ProcessInOutWipDAO;
import cc.oit.bsmes.pro.dao.ProcessQCEqipDAO;
import cc.oit.bsmes.pro.dao.ProcessQCEqipWipDAO;
import cc.oit.bsmes.pro.dao.ProcessQcDAO;
import cc.oit.bsmes.pro.dao.ProcessQcWipDAO;
import cc.oit.bsmes.pro.dao.ProcessReceiptDAO;
import cc.oit.bsmes.pro.dao.ProcessReceiptWipDAO;
import cc.oit.bsmes.pro.dao.ProductCraftsDAO;
import cc.oit.bsmes.pro.dao.ProductCraftsWipDAO;
import cc.oit.bsmes.pro.dao.ProductProcessDAO;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProcessQCEqip;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessQcWip;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.model.ProductProcessWip;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.pro.service.ProductProcessWipService;
import cc.oit.bsmes.wip.dao.FinishedOrderDAO;
import cc.oit.bsmes.wip.model.FinishedOrder;
import cc.oit.bsmes.wip.model.WorkCusorderRelation;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkCusorderRelationService;
import cc.oit.bsmes.wip.service.WorkOrderService;

@Service
public class SalesOrderItemServiceImpl extends BaseServiceImpl<SalesOrderItem> implements
		SalesOrderItemService {
	
	@Resource
	private SalesOrderItemDAO salesOrderItemDAO;
	@Resource
	private CustomerOrderService customerOrderService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private CustomerOrderItemDecService customerOrderItemDecService;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private ProductService productService;
	@Resource
	private ProductCraftsDAO productCraftsDAO;
	@Resource
	private ProductCraftsWipDAO productCraftsWipDAO;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ProcessInOutWipDAO processInOutWipDAO;
	@Resource
	private ProcessInOutDAO processInOutDAO;
	@Resource
	private MatWipDAO matWipDAO;
	@Resource
	private MatDAO matDAO;
	@Resource
	private MatPropDAO matPropDAO;
	@Resource
	private MatPropWipDAO matPropWipDAO;
	@Resource
	private ProcessQcDAO processQcDAO;
	@Resource
	private ProcessQcWipDAO processQcWipDAO;
	@Resource
	private ProcessQCEqipWipDAO processQCEqipWipDAO;
	@Resource
	private EquipListWipDAO equipListWipDAO;
	@Resource
	private ProcessReceiptWipDAO processReceiptWipDAO;
	@Resource
	private ProductDAO productDAO;
	@Resource
	private WorkCusorderRelationService workCusorderRelationService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;
	@Resource
	private ProductProcessWipService productProcessWipService;
	@Resource
	private FinishedOrderDAO finishedOrderDAO;
	@Resource
	private ProcessQCEqipDAO processQCEqipDAO;
	@Resource
	private ProcessReceiptDAO processReceiptDAO;
	@Resource
	private ProductProcessDAO productProcessDAO;
	@Resource
	private EquipListDAO equipListDAO;

	@Override
	public List<SalesOrderItem> getBySalesOrderId(String salesOrderId) {
		SalesOrderItem salesOrderItem = new SalesOrderItem();
		salesOrderItem.setSalesOrderId(salesOrderId);
		return salesOrderItemDAO.get(salesOrderItem);
	}

	@Override
	public SalesOrderItem getByCustomerOrderItemId(
			String customerOrderItemId) {
		return salesOrderItemDAO.getByCustomerOrderItemId(customerOrderItemId);
	}

	@Override
	public List<SalesOrderItem> getByWorkOrder(String workOrderNo) {
		return salesOrderItemDAO.getByWorkOrder(workOrderNo);
	}

	private Product patternMethod(String productType, String productSpec) {
		Product product=null;
		Map<String,String> param=new HashMap<String,String>();
		param.put("productSpec", productSpec);
		Pattern p1=Pattern.compile(".*\\d{1,3}\\/\\d{1,3}[kv]{1,2}",Pattern.CASE_INSENSITIVE);
		Pattern p2=Pattern.compile(".*\\d{1,3}\\.\\d{1,3}\\/\\d{1,3}[kv]{1,2}",Pattern.CASE_INSENSITIVE);
		Pattern p=Pattern.compile("[kv]{1,2}$",Pattern.CASE_INSENSITIVE);
		Matcher m1=p1.matcher(productType);
		Matcher m2=p2.matcher(productType);
		Matcher m=p.matcher(productType);
		if(m1.matches()||m2.matches()){
			if(m.find()){
				productType=productType.substring(0, m.start());
				param.put("productType", productType);
				product=productDAO.getProBySpecific(param);
				if(product==null && productSpec.endsWith(".0")){
					productSpec=productSpec.substring(0,productSpec.length()-2);
					param.put("productSpec", productSpec);
					product=productDAO.getProBySpecific(param);
				}
			
			}
		}
		return product;
	}
	
	//匹配工艺	
	public String searchCraftsId(String productCode,String productType,String productSpec){
		String craftsId = productCraftsService.getCraftIdByProductCode(productCode);
		if (StringUtils.isEmpty(craftsId)) {
			ProductCrafts craft = productCraftsService.getLastOrderUserdCrafts(productCode);
			if(craft==null){
				productType = productType.replaceAll("[([\u4E00-\u9FA5]++)]", "");
				productType = productType.replaceAll("(\\(+\\))", "");
				productSpec = productSpec.replaceAll("[\u4E00-\u9FA5]+[0-9]{1,6}", "");
				productSpec = productSpec.replaceAll("[([^\u002bA-Z0-9\\*\\.\\[\\]\\s])]", "");
				Product product = productService.getByProductTypeAndSpec(productType,productSpec);
				if(product == null){
					//如果型号后面带KV或V 规格后带.0，将其屏蔽匹配
					 product=this.patternMethod(productType,productSpec);
					 if(product == null){
							craftsId = null;
					 }else{
						 craftsId = productCraftsService.getCraftIdByProductCode(product.getProductCode());
					 }
				}else{
					craftsId = productCraftsService.getCraftIdByProductCode(product.getProductCode());
				}
			}else{
				craftsId = craft.getId();
			}
		}	
		return craftsId;
	}
	
	//临时表数据生成	
	@Override
	@Transactional(readOnly = false)
	public String dataSeparationFunction(String craftsId, String salesOrderItemId) {
		Map<String, Object> existsfindParams = new HashMap<String, Object>();
		existsfindParams.put("craftsId", craftsId);
		existsfindParams.put("salesOrderItemId", salesOrderItemId);
		int validateExists = productCraftsWipDAO.getCountExists(existsfindParams);
		ProductCrafts productCraftsWip = productCraftsDAO.getAllById(craftsId);
		if (productCraftsWip != null) {
			if(validateExists > 0){
				return productCraftsWipDAO.getExistsId(existsfindParams).getId();
			}else{
				String productCraftsWipId = UUID.randomUUID().toString();
				try {
					Date d = new Date();
					List<ProductCrafts> productCraftsWipList = new ArrayList<ProductCrafts>();
					productCraftsWip.setSalesOrderItemId(salesOrderItemId);
					productCraftsWip.setId(productCraftsWipId);
					productCraftsWip.setProductCraftsId(craftsId);
					productCraftsWipList.add(productCraftsWip);
					productCraftsWipDAO.insertNewDate(productCraftsWipList);
					List<String> processIds = new ArrayList<String>();
					List<String> matCodes = new ArrayList<String>();
					List<String> matIds = new ArrayList<String>();
					List<String> qcIds = new ArrayList<String>();
					List<String> equipListIds = new ArrayList<String>();
					List<Mat> matList = new ArrayList<Mat>();
					List<MatProp> matPropList = new ArrayList<MatProp>();
					List<ProcessQc> finalQcList = new ArrayList<ProcessQc>();
					List<ProcessQCEqip> finalQcEqipList = new ArrayList<ProcessQCEqip>();
					List<EquipList> finalEquipList = new ArrayList<EquipList>();
					List<ProcessReceipt> finalReceiptList = new ArrayList<ProcessReceipt>();
					Map<String, Object> findParams = new HashMap<String, Object>();
					Date d1 = new Date();
					List<ProductProcess> productProcessWip = productProcessDAO.getByProductCraftsId(craftsId);
					Date d2 = new Date();
					for(int i = 0;i<productProcessWip.size();i++){
						processIds.add(productProcessWip.get(i).getId());
					}
					findParams.put("processIds", processIds);
					Date d3 = new Date();
					List<ProcessInOut> processInOutList = processInOutDAO.getAllByProductProcessId(findParams);
					Date d4 = new Date();
					List<ProcessQc> processQcWip = processQcDAO.getByProcessId(findParams);
					Date d5 = new Date();
					List<EquipList> equipListWip = equipListDAO.getAllByProcessId(findParams);
					Date d6 = new Date();
					for(int i = 0;i<processInOutList.size();i++){
						matCodes.add(processInOutList.get(i).getMatCode());
					}
					for(int i = 0;i<processQcWip.size();i++){
						qcIds.add(processQcWip.get(i).getId());
					}
					for(int i = 0;i<equipListWip.size();i++){
						equipListIds.add(equipListWip.get(i).getId());
					}
					findParams.put("matCodes", matCodes);
					findParams.put("qcIds", qcIds);
					findParams.put("equipListIds", equipListIds);
					Date d7 = new Date();
					List<ProcessQCEqip> processQCEqipWip = processQCEqipDAO.getAllByOcId(findParams);
					Date d8 = new Date();
					List<ProcessReceipt> processReceiptWip = processReceiptDAO.getByEquipListId(findParams);
					Date d9 = new Date();
					List<Mat> matWip = matDAO.getAllByMatCode(findParams);
					Date d10 = new Date();
					for(int i=0;i<matWip.size();i++){
						matIds.add(matWip.get(i).getId());
					}
					findParams.put("matIds", matIds);
					Date d11 = new Date();
					List<MatProp> matPropWip = matPropDAO.getByMatId(findParams);
					Date d12 = new Date();
					
					//数据封装
					Map<String,List<ProcessQc>> qcMap =  new HashMap<String, List<ProcessQc>>();
					for(int i = 0;i<processQcWip.size();i++){				
						ProcessQc object = processQcWip.get(i);
						String processId=object.getProcessId();
						List<ProcessQc> listObject = qcMap.get(processId);
						if(listObject==null)
						{
							listObject=new ArrayList<ProcessQc>(); 
							qcMap.put(processId, listObject);
						} 
						listObject.add(object);
					}
					
					Map<String,List<ProcessQCEqip>> qcEqipMap =  new HashMap<String, List<ProcessQCEqip>>();
					for(int i = 0;i<processQCEqipWip.size();i++){				
						ProcessQCEqip object = processQCEqipWip.get(i);
						String qcId=object.getQcId();
						List<ProcessQCEqip> listObject = qcEqipMap.get(qcId);
						if(listObject==null)
						{
							listObject=new ArrayList<ProcessQCEqip>(); 
							qcEqipMap.put(qcId, listObject);
						} 
						listObject.add(object);
					}
					
					Map<String,List<EquipList>> equipListMap =  new HashMap<String, List<EquipList>>();
					for(int i = 0;i<equipListWip.size();i++){				
						EquipList object = equipListWip.get(i);
						String processId=object.getProcessId();
						List<EquipList> listObject = equipListMap.get(processId);
						if(listObject==null)
						{
							listObject=new ArrayList<EquipList>(); 
							equipListMap.put(processId, listObject);
						} 
						listObject.add(object);
					}
					
					Map<String,List<ProcessReceipt>> processReceiptMap =  new HashMap<String, List<ProcessReceipt>>();
					for(int i = 0;i<processReceiptWip.size();i++){				
						ProcessReceipt object = processReceiptWip.get(i);
						String eqipListId=object.getEqipListId();
						List<ProcessReceipt> listObject = processReceiptMap.get(eqipListId);
						if(listObject==null)
						{
							listObject=new ArrayList<ProcessReceipt>(); 
							processReceiptMap.put(eqipListId, listObject);
						} 
						listObject.add(object);
					}
					
					int productProcessWipSize = productProcessWip.size();
					int processInOutListSize = processInOutList.size();
					int matWipSize = matWip.size();
					int matPropWipSize = matPropWip.size();
		            
					for(int i = 0;i<productProcessWipSize;i++){
						String productProcessWipId = UUID.randomUUID().toString();
						productProcessWip.get(i).setProductCraftsId(productCraftsWipId);
						//处理投入产出及物料数据
						for(int a = 0;a<processInOutListSize;a++){
							String processInOutWipId = UUID.randomUUID().toString();
							if(processInOutList.get(a).getProductProcessId().equals(productProcessWip.get(i).getId())){	
								for(int b = 0;b<matWipSize;b++){
										if(processInOutList.get(a).getMatCode().equals(matWip.get(b).getMatCode())){
											String matWipId = UUID.randomUUID().toString();
											MatWip matObject = new MatWip();
											BeanUtils.copyProperties(matWip.get(b), matObject);
											matObject.setId(matWipId);
											matObject.setProcessWipId(productProcessWipId);
											matObject.setSalesOrderItemId(salesOrderItemId);
											matObject.setProcessInOutWipId(processInOutWipId);
											matWipDAO.insert(matObject);
//											matList.add(matObject);
											for(int c=0;c<matPropWipSize;c++){
												if(matPropWip.get(c).getMatId().equals(matWip.get(b).getId())){
													String matPropWipId = UUID.randomUUID().toString();
													MatPropWip matPropObject = new MatPropWip();
													BeanUtils.copyProperties(matPropWip.get(c), matPropObject);
													matPropObject.setMatId(matWipId);
													matPropObject.setId(matPropWipId);
													matPropWipDAO.insert(matPropObject);
//													matPropList.add(matPropObject);
												}
											}							
										}
								}
								processInOutList.get(a).setProductProcessId(productProcessWipId);
								processInOutList.get(a).setId(processInOutWipId);
							}
						}
		
						//处理processQC及processQCEqip的数据
						List<ProcessQc> thisQcList  = new ArrayList<ProcessQc>();
						if(qcMap.get(productProcessWip.get(i).getId())!=null){
							thisQcList.addAll(qcMap.get(productProcessWip.get(i).getId()));
						}
						for(int a = 0;a<thisQcList.size();a++){
							String processQcWipId = UUID.randomUUID().toString();
							ProcessQcWip qcObject = new ProcessQcWip();
							BeanUtils.copyProperties(thisQcList.get(a), qcObject);
							List<ProcessQCEqip> thisQcEqipList  = new ArrayList<ProcessQCEqip>();
							if(qcEqipMap.get(thisQcList.get(a).getId())!=null){
								thisQcEqipList.addAll(qcEqipMap.get(thisQcList.get(a).getId()));
							}
							for(int b = 0;b<thisQcEqipList.size();b++){
								String processQCEqipWipId = UUID.randomUUID().toString();
								ProcessQCEqip qcEqipObject = new ProcessQCEqip();
								BeanUtils.copyProperties(thisQcEqipList.get(b), qcEqipObject);
								qcEqipObject.setId(processQCEqipWipId);
								qcEqipObject.setQcId(processQcWipId);
								finalQcEqipList.add(qcEqipObject);
							}
							qcObject.setId(processQcWipId);
							qcObject.setProcessId(productProcessWipId);
							processQcWipDAO.insert(qcObject);
//							finalQcList.add(qcObject);
						}
						
						//处理equipList及receipt的数据
						List<EquipList> thisEquipList  = new ArrayList<EquipList>();
						if(equipListMap.get(productProcessWip.get(i).getId())!=null){
							thisEquipList.addAll(equipListMap.get(productProcessWip.get(i).getId()));
						}
						for(int a = 0;a<thisEquipList.size();a++){
							String equipListWipId = UUID.randomUUID().toString();
							EquipList equipListObject = new EquipList();
							BeanUtils.copyProperties(thisEquipList.get(a), equipListObject);
							List<ProcessReceipt> thisReceiptList  = new ArrayList<ProcessReceipt>();
							if(processReceiptMap.get(thisEquipList.get(a).getId())!=null){
								thisReceiptList.addAll(processReceiptMap.get(thisEquipList.get(a).getId()));
							}
							for(int b = 0;b<thisReceiptList.size();b++){
								String processReceiptWipId = UUID.randomUUID().toString();
								ProcessReceipt processReceiptObject = new ProcessReceipt();
								BeanUtils.copyProperties(thisReceiptList.get(b), processReceiptObject);
								processReceiptObject.setId(processReceiptWipId);
								processReceiptObject.setEqipListId(equipListWipId);
								finalReceiptList.add(processReceiptObject);
							}
							equipListObject.setId(equipListWipId);
							equipListObject.setProcessId(productProcessWipId);
							finalEquipList.add(equipListObject);
						}
		
						productProcessWip.get(i).setOldProcessId(productProcessWip.get(i).getId());
						productProcessWip.get(i).setId(productProcessWipId);
					}
					
					for(int i = 0;i<productProcessWipSize;i++){
						//重置下一道工序ID
						for(int j = 0;j<productProcessWip.size();j++){
							if(productProcessWip.get(j).getOldProcessId().equals(productProcessWip.get(i).getNextProcessId())){
								productProcessWip.get(i).setNextProcessId(productProcessWip.get(j).getId());
							}
						}
						//重置全路径数据
						String [] fullFatn = productProcessWip.get(i).getFullPath().split(";");
						String newfullFatn = ""; 
						for(int index = 0;index<fullFatn.length;index++){
							for(int j = 0;j<productProcessWip.size();j++){
								if(fullFatn[index].equals(productProcessWip.get(j).getOldProcessId())){
									fullFatn[index] = productProcessWip.get(j).getId();
								}
							}
						}
						for(int index = 0;index<fullFatn.length;index++){
							if(newfullFatn.isEmpty()){
								newfullFatn = fullFatn[index];
							}else{
								newfullFatn = newfullFatn + ";" + fullFatn[index];
							}
						}
						productProcessWip.get(i).setFullPath(newfullFatn);
					}
					
		            //插入数据
					Date d13 = new Date();
					if(productProcessWipSize>0){
						productProcessWipService.insertAll(productProcessWip);
					}
					Date d14 = new Date();
					if(processInOutListSize>0){
						processInOutWipDAO.insertAll(processInOutList);
					}
					Date d15 = new Date();
//					if(matList.size()>0){
//						matWipDAO.insertAll(matList);
//					}
					Date d16 = new Date();
//					if(matPropList.size()>0){
//						matPropWipDAO.insertAll(matPropList);
//					}
					Date d17 = new Date();
					Map<String, List<ProcessQc>> qcParams = new HashMap<String, List<ProcessQc>>();
//					if(finalQcList.size()>0){
//						qcParams.put("QCWIPLIST", finalQcList);
//						productCraftsWipDAO.dataSeparationFunctionNew(qcParams);
						//processQcWipDAO.insertAll(finalQcList);
//					}
					Date d18 = new Date();
					if(finalQcEqipList.size()>0){
						processQCEqipWipDAO.insertAll(finalQcEqipList);
					}
					Date d19 = new Date();
					if(finalEquipList.size()>0){
						equipListWipDAO.insertAll(finalEquipList);
					}
					Date d20 = new Date();
					if(finalReceiptList.size()>0){
						processReceiptWipDAO.insertAll(finalReceiptList);
					}
					Date d21 = new Date();
					System.err.println("总耗时：" + (d21.getTime() - d.getTime()) / 1000 + "秒");
					System.err.println("process查询耗时：" + (d2.getTime() - d1.getTime()) / 1000 + "秒");
					System.err.println("inOut查询耗时：" + (d4.getTime() - d3.getTime()) / 1000 + "秒");
					System.err.println("QC查询耗时：" + (d5.getTime() - d4.getTime()) / 1000 + "秒");
					System.err.println("EquipList查询耗时：" + (d6.getTime() - d5.getTime()) / 1000 + "秒");
					System.err.println("QcEquip查询耗时：" + (d8.getTime() - d7.getTime()) / 1000 + "秒");
					System.err.println("receipt查询耗时：" + (d9.getTime() - d8.getTime()) / 1000 + "秒");
					System.err.println("mat查询耗时：" + (d10.getTime() - d9.getTime()) / 1000 + "秒");
					System.err.println("matProp查询耗时：" + (d12.getTime() - d11.getTime()) / 1000 + "秒");
					System.err.println("处理数据循环耗时：" + (d13.getTime() - d12.getTime()) / 1000 + "秒");
					System.err.println("process插入耗时：" + (d14.getTime() - d13.getTime()) / 1000 + "秒");
					System.err.println("inOut插入耗时：" + (d15.getTime() - d14.getTime()) / 1000 + "秒");
					System.err.println("mat插入耗时：" + (d16.getTime() - d15.getTime()) / 1000 + "秒");
					System.err.println("matProp插入耗时：" + (d17.getTime() - d16.getTime()) / 1000 + "秒");
					System.err.println("QC插入耗时：" + (d18.getTime() - d17.getTime()) / 1000 + "秒");
					System.err.println("QcEquip插入耗时：" + (d19.getTime() - d18.getTime()) / 1000 + "秒");
					System.err.println("equipList插入耗时：" + (d20.getTime() - d19.getTime()) / 1000 + "秒");
					System.err.println("receipt插入耗时：" + (d21.getTime() - d20.getTime()) / 1000 + "秒");
				} catch (Exception e) {
					System.err.println(productCraftsWip.getProductCode() + "数据生成过程出现异常:\n" + e.toString());
				}
				return productCraftsWipId;
			
			}
		} else {
			return null;
		}
	}
	
	// 更换工艺：删除旧WIP表工艺数据，添加新WIP表工艺数据
	// @param oldCraftsId 旧工艺ID[WIP]
	// @param newCraftsId 新工艺ID[实例库:不带后缀]
	// @param salesOrderItemId 订单明细id
	@Override
	@Transactional(readOnly = false)
	public String changeCrafts(String oldCraftsId,String newCraftsId,String salesOrderItemId){
		// 1、验证参数合法性
		if(salesOrderItemId.isEmpty() || newCraftsId.isEmpty() || salesOrderItemId.isEmpty()){
			return null;
		}
		
		// 2、删除原工艺数据[WIP][原工艺存在]
		int count = productCraftsWipDAO.getCountById(oldCraftsId); //查找原工艺是否存在，不存在不作删除操作
		if(count>0) {
			this.removeCrafts(oldCraftsId);
		}
		
		// 3、添加新工艺数据
		String craftsId = this.dataSeparationFunction(newCraftsId, salesOrderItemId);
		
		if(craftsId!=null){
		// 4、更新订单、生产单相关的工艺工序ID
			this.updateRelevanceId(oldCraftsId, craftsId);
			return craftsId;
		}else{
			throw new RuntimeException();
		}

	}
	
	
	// 更新生产单和任务相关联的工艺工序ID
	@Transactional(readOnly = false)
	public void updateRelevanceId(String oldCraftsId, String newCraftsId){
		if(StringUtils.isNotEmpty(oldCraftsId)) { // 当工艺oldCraftsId为空会出现更新全部订单工艺的大bug
			return;
		}
		// 先将新的ProcessWip放入Map
		Map<String, ProductProcessWip> codeIdMapping = new HashMap<String, ProductProcessWip>();
		ProductProcessWip processParams = new ProductProcessWip();
		processParams.setProductCraftsId(newCraftsId);
		List<ProductProcessWip> processArray = productProcessWipService.findByObj(processParams);
		for(ProductProcessWip process : processArray){
			codeIdMapping.put(process.getProcessCode(), process);
		}
		ProductProcessWip process = null;
		
		// 1、找到工艺相关联的订单；
		// 2、根据订单找到订单关联的生产单；
		// 3、找到任务明细:PRO_DEC
		// 4、找到物料需求:T_PLA_MRP
		
		// 1
		CustomerOrderItem cusParams = new CustomerOrderItem();
		cusParams.setCraftsId(oldCraftsId);
		List<CustomerOrderItem> cusOrderArray = customerOrderItemService.findByObj(cusParams);
		for(CustomerOrderItem cusOrder : cusOrderArray){
			cusOrder.setCraftsId(newCraftsId);
			customerOrderItemService.update(cusOrder);
			
			// 2
			WorkCusorderRelation relParams = new WorkCusorderRelation();
			relParams.setCusOrderItemId(cusOrder.getId());
			List<WorkCusorderRelation> relArray = workCusorderRelationService.findByObj(relParams);
			for(WorkCusorderRelation rel : relArray){
				WorkOrder workOrder = workOrderService.getById(rel.getCusOrderItemId());
				if(null != workOrder){
					process = codeIdMapping.get(workOrder.getProcessCode());
					if(null == process){
						continue;
					}
					workOrder.setProcessId(process.getId());
					workOrderService.update(workOrder);
					
					// 3
					CustomerOrderItemProDec prodecParams = new CustomerOrderItemProDec();
					prodecParams.setWorkOrderNo(workOrder.getWorkOrderNo());
					List<CustomerOrderItemProDec> prodecArray = customerOrderItemProDecService.findByObj(prodecParams);
					for(CustomerOrderItemProDec prodec : prodecArray){
						process = codeIdMapping.get(prodec.getProcessCode());
						if(null == process){
							continue;
						}
						prodec.setCraftsId(newCraftsId);
						prodec.setProcessId(process.getId());
						prodec.setProcessPath(process.getFullPath());
						customerOrderItemProDecService.update(prodec);
					}
					
					// 4
					MaterialRequirementPlan matPlanParams = new MaterialRequirementPlan();
					matPlanParams.setWorkOrderId(workOrder.getId());
					List<MaterialRequirementPlan> matPlanArray = materialRequirementPlanService.findByObj(matPlanParams);
					for(MaterialRequirementPlan matPlan : matPlanArray){
						process = codeIdMapping.get(matPlan.getProcessCode());
						if(null == process){
							continue;
						}
						matPlan.setProcessId(process.getId());
						materialRequirementPlanService.update(matPlan);
					}
				}
			}
		}
	}
	
	// 删除原工艺数据
	// @param oldCraftsId 工艺ID
	@Override
	@Transactional(readOnly = false)
	public void removeCrafts(String oldCraftsId) {
		//删除原工艺数据	
		processReceiptWipDAO.deleteDate(oldCraftsId);
		processQCEqipWipDAO.deleteDate(oldCraftsId);
		equipListWipDAO.deleteDate(oldCraftsId);
		processQcWipDAO.deleteDate(oldCraftsId);
		matPropWipDAO.deleteDate(oldCraftsId);
		matWipDAO.deleteDate(oldCraftsId);
		processInOutWipDAO.deleteDate(oldCraftsId);
		productProcessWipService.deleteDate(oldCraftsId);
		productCraftsWipDAO.deleteDate(oldCraftsId);
	}

	@Override
	public String getLastProcessCode(String salesOrderItemId) {
		// TODO Auto-generated method stub
		return salesOrderItemDAO.getLastProcessCode(salesOrderItemId);
	}

	@Override
	public Map<String,Object> getReportLength(String id) {
		// TODO Auto-generated method stub
		return salesOrderItemDAO.getJJInfo(id);
	}

	@Override
	public void insertToFinishedOrder(FinishedOrder order) {
		// TODO Auto-generated method stub
		finishedOrderDAO.insert(order);
	}

	@Override
	public List<Map<String, String>> getFinishedGWData(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return salesOrderItemDAO.getFinishedGWData(param);
	}
}
