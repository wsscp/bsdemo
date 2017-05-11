package cc.oit.webservice.beans;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.concurrent.RenameThreadPoolExecutor;
import cc.oit.bsmes.common.constants.CustomerOrderStatus;
import cc.oit.bsmes.common.constants.OperateType;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.SalesOrderStatus;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.log.model.SaleLog;
import cc.oit.bsmes.common.log.service.SaleLogService;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.common.util.NoGenerator;
import cc.oit.bsmes.common.util.ParseStringUtils;
import cc.oit.bsmes.ord.dao.SalesOrderDAO;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.dao.CustomerOrderDAO;
import cc.oit.bsmes.pla.dao.ProductDAO;
import cc.oit.bsmes.pla.model.AttachFile;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.ManualManage;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.AttachFileService;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.ManualManageService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.webservice.util.FTPUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component("erpService")
@Transactional(readOnly=false)
public class ERPOrderSyncServiceImpl implements ERPOrderSyncService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());//鏃ュ織璁板綍
	@Resource
	private SalesOrderItemService salesOrderItemService;
	@Resource
	private SalesOrderService salesOrderService;
	@Resource
	private SalesOrderDAO salesOrderDAO;
	@Resource
	private AttachFileService attachFileService;
	@Resource
	private CustomerOrderDAO customerOrderDAO;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private CustomerOrderService customerOrderService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private CustomerOrderItemDecService customerOrderItemDecService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private ProductService productService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private ProductDAO productDAO;
	@Resource
	private SaleLogService saleLogService;
	@Resource
	private ManualManageService manualManageService;
	
	private static final String REGEX_1 = "\\d{1,2}\\*\\d{1,2}\\.?\\d{0,2}\\锛�\\(?[\u4E00-\u9FA5]+\\d{0,9}([\u4E00-\u9FA5]+)?\\锛�\\)?";
	
	private static final String REGEX_2 = "\\d{1,2}\\*\\d{1,2}\\*\\d{1,2}\\.?\\d{0,2}\\(?\\锛�[\u4E00-\u9FA5]+\\)?\\锛�";
	
	private static final String REGEX_3 = "\\d{1,2}\\*\\d{1,2}\\.?\\d{0,2}\\+\\d{1,2}\\*\\d{1,2}\\.?\\d{0,2}\\(?\\锛�[\u4E00-\u9FA5]+\\d*([\u4E00-\u9FA5]+)?\\)?\\锛�";
	
	private static final Executor executor = new RenameThreadPoolExecutor(10,
			50, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	
	private static final String ORG_CODE = "bstl01";
	
	/**
	 * 涓昏鍗曟帹閫�
	 * update_time : 2016-01-25
	 */
	@Override
	@Transactional(readOnly = false)
	public String saveSalesOrder(String jsonText) {
		logger.info(jsonText); // log  瀛樺偍浣嶇疆锛歵omcat鏂囦欢/logs/catalina.txt
		
		JSONArray resultJSONArray = new JSONArray(); // 杩斿洖缁撴灉
		
		JSONArray jsonArray = JSONArray.parseArray(jsonText); // 瑙ｆ瀽erp鎺ㄩ�鏁版嵁
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject resultObject = new JSONObject();
			
			JSONObject object = jsonArray.getJSONObject(i);
			SalesOrder salesOrder = (SalesOrder) JSONUtils.jsonToBean(object,SalesOrder.class);
			salesOrder.setOrgCode(ORG_CODE);
			SalesOrder salesOrderExist = salesOrderDAO.getById(salesOrder.getId());
			
			try {
				String operateType = object.getString("operateType");
				if (OperateType.ADD.toString().equals(operateType)) {
					if (salesOrderExist == null) {
						salesOrder.setStatus(SalesOrderStatus.TO_DO);
						salesOrder.setCreateUserCode("erp");
						salesOrder.setModifyUserCode("erp");
//						salesOrder.setSalesOrderNo("BS14031" + (10380 + i));
						salesOrderDAO.insert(salesOrder);
						
						// 鎻掑叆T_PLA_CUSTOMER_ORDER瀹㈡埛璁㈠崟鏁版嵁
						insertIntoCustomerOrder(salesOrder);
						
						// 闄勪欢涓嬭浇
						String fileJson = object.get("attachfileBeans").toString();
						JSONArray fileArray = JSONArray.parseArray(fileJson);
						
						CountDownLatch countDownLatch = new CountDownLatch(fileArray.size());
						for (int k = 0; k < fileArray.size(); k++) {
							JSONObject fileObject = fileArray.getJSONObject(k);
							AttachFile attachFile = JSON.toJavaObject(fileObject, AttachFile.class);
							
							attachFile.setSalesOrderId(salesOrder.getId());
							attachFile.setCreateUserCode("erp");
							attachFile.setCreateTime(new Date());
							attachFile.setModifyUserCode("erp");
							attachFile.setModifyTime(new Date());
							
							// 鏂板缓鏈湴鏂囦欢澶�瀛樻斁闄勪欢鏂囨。
							File file = new File("E:/attachfile/" + attachFile.getUploadTime());
							if (!file.exists()) {
								file.mkdirs();
							}
							// 寮�涓嬭浇
							executor.execute(new UploadFileThread(attachFile,file,countDownLatch,resultObject));
						}
						countDownLatch.await(1L, TimeUnit.HOURS);
						
						resultObject.put("result", "success");
						resultObject.put("id", salesOrder.getId());
					} else {
						if(salesOrderExist.getStatus() == SalesOrderStatus.CANCELED){
							salesOrder.setStatus(SalesOrderStatus.TO_DO);
							salesOrderService.update(salesOrder);
							
							// 鏇存柊T_PLA_CUSTOMER_ORDER瀹㈡埛璁㈠崟鏁版嵁
							CustomerOrder customerOrder = customerOrderService.getBySalesOrderId(salesOrder.getId()).get(0);
							customerOrder.setRemarks(salesOrder.getRemarks());
							customerOrder.setStatus(CustomerOrderStatus.TO_DO);
							customerOrderDAO.update(customerOrder);
							
							resultObject.put("result", "success");
							resultObject.put("id", salesOrder.getId());
						}else{
							resultObject.put("result", "refuse");
							resultObject.put("id", salesOrder.getId());
							resultObject.put("message","璁㈠崟鍙�" + salesOrder.getSalesOrderNo() + "宸茬粡瀛樺湪,璇峰嬁閲嶅娣诲姞.");
						}
					}
				} else if (OperateType.MODIFY.toString().equals(operateType)) {
					if (salesOrderExist == null) {
						resultObject.put("result", "refuse");
						resultObject.put("id", salesOrder.getId());
						resultObject.put("message","璁㈠崟鍙�" + salesOrder.getSalesOrderNo() + "涓嶅瓨鍦�");
					} else {
						salesOrder.setReleaseDate(salesOrderExist.getReleaseDate());
						salesOrderService.update(salesOrder);
						
						// 闄勪欢鏇存柊
						List<AttachFile> attachFiles = attachFileService.getBySalesOrderId(salesOrder.getId());
						
						String fileJson = object.get("attachfileBeans").toString();
						JSONArray fileArray = JSONArray.parseArray(fileJson);
						CountDownLatch countDownLatch = new CountDownLatch(fileArray.size());
						for (int k = 0; k < fileArray.size(); k++) {
							JSONObject fileObject = fileArray.getJSONObject(k);
							AttachFile attachFile = JSON.toJavaObject(fileObject, AttachFile.class);
							attachFile.setSalesOrderId(salesOrder.getId());
							attachFile.setCreateUserCode("erp");
							attachFile.setCreateTime(new Date());
							attachFile.setModifyUserCode("erp");
							attachFile.setModifyTime(new Date());
							int v=1;
							String temp = attachFile.getRealFileName().substring(0, attachFile.getRealFileName().lastIndexOf("."));
							String suffix = attachFile.getRealFileName().substring(attachFile.getRealFileName().lastIndexOf("."));
							for(AttachFile a : attachFiles){
								String temp1 = a.getRealFileName().substring(0, a.getRealFileName().lastIndexOf("_")>0 ? a.getRealFileName().lastIndexOf("_") : a.getRealFileName().lastIndexOf("."));
								if(temp1.equals(temp)){
									v++;
								}
							}

							attachFile.setRealFileName(temp+"_"+v+suffix);
							File file = new File("E:/attachfile/" + attachFile.getUploadTime());
							if (!file.exists()) {
								file.mkdirs();
							}
							executor.execute(new UploadFileThread(attachFile,file, countDownLatch,resultObject));
						}
						countDownLatch.await(1L, TimeUnit.HOURS);
						
						resultObject.put("result", "success");
						resultObject.put("id", salesOrder.getId());
					}
				} else {
					if (salesOrderExist == null) {
						resultObject.put("result", "refuse");
						resultObject.put("id", salesOrder.getId());
						resultObject.put("message","璁㈠崟鍙�" + salesOrder.getSalesOrderNo() + "涓嶅瓨鍦�");
					} else {
						salesOrder.setStatus(SalesOrderStatus.CANCELED);
						salesOrder.setReleaseDate(salesOrderExist.getReleaseDate());
						salesOrderService.update(salesOrder);
						CustomerOrder customerOrder = customerOrderService.getBySalesOrderId(salesOrder.getId()).get(0);
						customerOrder.setStatus(CustomerOrderStatus.CANCELED);
						customerOrderService.update(customerOrder);
						AttachFile params = new AttachFile();
						params.setSalesOrderId(salesOrder.getId());
						List<AttachFile> attachFileList = attachFileService.getByObj(params);
						if (attachFileList.size() != 0) {
							for (AttachFile attachFile : attachFileList) {
								attachFileService.delete(attachFile);
							}
						}
						resultObject.put("result", "success");
						resultObject.put("id", salesOrder.getId());
					}
				}
			} catch (Exception e) {
				resultObject.put("result", "false");
				resultObject.put("id", salesOrder.getId());
				resultObject.put("message", e.getLocalizedMessage());
			}
			resultJSONArray.add(resultObject);
		}
		return resultJSONArray.toJSONString();
	}

	@Override
	@Transactional(readOnly = false)
	public String saveSalesOrderItem(String jsonText) {
		logger.info(jsonText); // log  瀛樺偍浣嶇疆锛歵omcat鏂囦欢/logs/catalina.txt
		
		JSONArray resultJSONArray = new JSONArray(); //瀛樺偍杩斿洖缁撴灉
		
		JSONArray jsonArray = JSONArray.parseArray(jsonText);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject relustObject = new JSONObject();
			JSONObject object = jsonArray.getJSONObject(i);
			String departId = object.getString("departmentId");
			if(("302").equals(departId)){
				SalesOrderItem salesOrderItem = (SalesOrderItem) JSONUtils.jsonToBean(object, SalesOrderItem.class);
				
				//鎸佷箙鍖栨槑缁嗘棩蹇�
				SaleLog saleLog = new SaleLog();
				saleLog.setItemId(object.getString("id"));
				saleLog.setContractNo(object.getString("contractNo"));
				saleLog.setCustomerOaDate(object.getString("customerOADate"));
				saleLog.setCustProductSpec(object.getString("custProductSpec"));
				saleLog.setCustProductType(object.getString("custProductType"));
				saleLog.setOperateType(object.getString("operateType"));
				saleLog.setOrgCode(object.getString("orgCode"));
				saleLog.setProcessRequire(object.getString("processRequire"));
				saleLog.setProductCode(object.getString("productCode"));
				saleLog.setProductSpec(object.getString("productSpec"));
				saleLog.setSaleorderLength(object.getString("saleorderLength"));
				saleLog.setSalesOrderId(object.getString("salesOrderId"));
				saleLog.setContractAmount(object.getString("contractAmount"));
				saleLogService.insert(saleLog);
				
				String operateType = object.getString("operateType");          // 鎿嶄綔绫诲瀷
				String salesOrderLength = object.getString("saleorderLength"); // 璁㈠崟闀垮害
				String customerOADate = object.getString("customerOADate");    // 璁㈠崟浜よ揣鏈�
				String productSpec = object.getString("productSpec");          // 浜у搧瑙勬牸
				
				salesOrderItem.setProductSpec(productSpec);
				salesOrderItem.setSaleorderLength(Double.valueOf(salesOrderLength)*1000);
				salesOrderItem.setOrgCode(ORG_CODE);
				
				SalesOrderItem salesOrderItemExist = salesOrderItemService.getById(salesOrderItem.getId());
				
				SalesOrder salesOrderExist = salesOrderService.getById(salesOrderItem.getSalesOrderId());
				try {
					//鎵句笉鍒版槑缁嗙殑涓昏〃锛宔rp鏂瑰繀椤昏ˉ鎺ㄤ富琛ㄦ暟鎹紝骞朵簩娆℃帹閫佹槑缁嗘暟鎹�
					if(salesOrderExist == null){
						relustObject.put("result", "refuse");
						relustObject.put("id", salesOrderItem.getId());
						relustObject.put("message","璇ユ槑缁嗙殑涓昏鍗曚笉瀛樺湪");
						resultJSONArray.add(relustObject);
						continue;
					}
					//娣诲姞鏄庣粏鏁版嵁
					String productSpecParse = parseProductSpec(productSpec);
					if (OperateType.ADD.toString().equals(operateType)) {
						if (salesOrderItemExist == null) {
							salesOrderItem.setStandardLength(salesOrderItem.getSaleorderLength());
							salesOrderItem.setProductCode(salesOrderItem.getProductCode()+"-"+productSpecParse);
							salesOrderItem.setNumberOfWires(ParseStringUtils.parseNumber(productSpecParse));      //鑺暟
							salesOrderItem.setSection(ParseStringUtils.parseSection(productSpecParse));           //鎴潰
							salesOrderItem.setLengthConstraints(",100:0;");                                       //闀垮害绾︽潫
							salesOrderItem.setStatus(SalesOrderStatus.TO_DO);                                     //璁㈠崟鍒濆鍖栫姸鎬�
							salesOrderItem.setContractLength(salesOrderItem.getSaleorderLength());                //鍚堝悓闀垮害
							salesOrderItem.setCreateUserCode("erp");
							salesOrderItem.setModifyUserCode("erp");
							salesOrderItemService.insert(salesOrderItem); 
							
							//鍚屾浜よ揣鏈熻鍒掔鐞�
							ManualManage manualManage = new ManualManage();
							manualManage.setSalesOrderItemId(salesOrderItem.getId());
							manualManage.setCoordinateTime(salesOrderItem.getCreateTime());
							manualManageService.insert(manualManage);
							
							//鏇存柊浜よ揣鏃堕棿
							List<CustomerOrder> customerOrderList = customerOrderDAO.getBySalesOrderId(salesOrderItem.getSalesOrderId());
							CustomerOrder customerOrder = customerOrderList.get(0);
							SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
							Date date = formatDate.parse(customerOADate);
							customerOrder.setOaDate(date); // 璁㈠崟浜よ揣鏈�
							customerOrderDAO.update(customerOrder);
							
							SalesOrder salesOrder = salesOrderService.getById(salesOrderItem.getSalesOrderId());
							CustomerOrderItem customerOrderItem = new CustomerOrderItem();
							customerOrderItem.setSalesOrderItemId(salesOrderItem.getId());
							customerOrderItem.setCustomerOrderId(customerOrder.getId());
							customerOrderItem.setProductCode(salesOrderItem.getProductCode());
							customerOrderItem.setOrderLength(salesOrderItem.getSaleorderLength());
							customerOrderItem.setContractLength(salesOrderItem.getContractLength());
							boolean isFirstTime = orderTaskService.checkFirstTime(salesOrderItem.getProductCode(),salesOrder.getContractNo());
							customerOrderItem.setIsFirstTime(isFirstTime);
							//--------------------缁戝畾宸ヨ壓start
							// #1 鎸夌収'erp缂栫爜+浜у搧瑙勬牸'杩涜鍖归厤
							String craftsId = productCraftsService.getCraftIdByProductCode(salesOrderItem.getProductCode());
							if (StringUtils.isEmpty(craftsId)) {
								// #2 鎸夌収'erp缂栫爜+浜у搧瑙勬牸' 涓庝笂涓�釜鐢熶骇杩囩殑璁㈠崟杩涜鍖归厤
								ProductCrafts craft = productCraftsService.getLastOrderUserdCrafts(salesOrderItem.getProductCode());
								if(craft==null){
									// #3 鎸夌収浜у搧鐨勫瀷鍙峰拰瑙勬牸杩涜鍖归厤
									String productType= salesOrderItem.getProductType(); // 浜у搧鍨嬪彿
									productType = productType.replaceAll("[([\u4E00-\u9FA5]++)]", "");
									productType = productType.replaceAll("(\\(+\\))", "");
									productSpecParse = productSpecParse.replaceAll("[([\u4E00-\u9FA5]++)]", "");
									productSpecParse = productSpecParse.replaceAll("(\\(+\\))", "");
									Product product = productService.getByProductTypeAndSpec(productType,productSpecParse);
									if(product != null){
										craftsId = productCraftsService.getCraftIdByProductCode(product.getProductCode());
									}
								}else{
									craftsId = craft.getId();
								}
							}
							String finalCraftsId = salesOrderItemService.dataSeparationFunction(craftsId, salesOrderItem.getId());
							customerOrderItem.setCraftsId(finalCraftsId);
							String wiresStructure = customerOrderService.getWiresStructure(craftsId); // 绌洪粯璁
							salesOrderItem.setWiresStructure(StringUtils.isEmpty(wiresStructure)? "" : wiresStructure); // 绾胯姱缁撴瀯
							//--------------------------缁戝畾宸ヨ壓end
							salesOrderItemService.update(salesOrderItem);
							customerOrderItem.setStatus(ProductOrderStatus.TO_DO);
							customerOrderItem.setCustomerOaDate(date);
							customerOrderItem.setCreateUserCode("erp");
							customerOrderItem.setModifyUserCode("erp");
							customerOrderItemService.insert(customerOrderItem);
							
							double leftLength = salesOrderItem.getSaleorderLength();
							
							CustomerOrderItemDec customerOrderItemDec = new CustomerOrderItemDec();
							customerOrderItemDec.setLength(leftLength);
							customerOrderItemDec.setOrderItemId(customerOrderItem.getId());
							customerOrderItemDec.setStatus(ProductOrderStatus.TO_DO);
							customerOrderItemDec.setCreateUserCode("erp");
							customerOrderItemDec.setModifyUserCode("erp");
							customerOrderItemDecService.insert(customerOrderItemDec);
//						salesOrderItemService.dataSeparationFunction(craftsId, salesOrderItem.getId());
							relustObject.put("result", "success");
							relustObject.put("id", salesOrderItem.getId());
						} else {
							if(salesOrderItemExist.getStatus() == SalesOrderStatus.CANCELED){
								salesOrderItem.setStandardLength(salesOrderItem.getSaleorderLength()); // 鏍囧噯闀垮害
								salesOrderItem.setProductCode(salesOrderItem.getProductCode()+"-"+productSpecParse); // 浜у搧浠ｇ爜
								salesOrderItem.setNumberOfWires(ParseStringUtils.parseNumberOfWires(productSpecParse)); // 鑺暟
								salesOrderItem.setSection(ParseStringUtils.parseNumberOfSection(productSpecParse));
								salesOrderItem.setStatus(SalesOrderStatus.TO_DO); // 璁㈠崟鐘舵�
								salesOrderItem.setContractLength(salesOrderItem.getSaleorderLength()); // 鍚堝悓闀垮害/璁㈠崟闀垮害
								salesOrderItemService.update(salesOrderItem);
								
								//鏇存柊浜よ揣鏃堕棿
								List<CustomerOrder> customerOrderList = customerOrderDAO.getBySalesOrderId(salesOrderItem.getSalesOrderId());
								CustomerOrder customerOrder = customerOrderList.get(0);
								SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
								Date date = formatDate.parse(customerOADate);
								customerOrder.setOaDate(date); // 璁㈠崟浜よ揣鏈�
								customerOrderDAO.update(customerOrder);
								
								SalesOrder salesOrder = salesOrderService.getById(salesOrderItem.getSalesOrderId());
								CustomerOrderItem findPar =  new CustomerOrderItem();
								findPar.setSalesOrderItemId(salesOrderItem.getId());
								CustomerOrderItem customerOrderItem = customerOrderItemService.getByObj(findPar).get(0);
								customerOrderItem.setProductCode(salesOrderItem.getProductCode());
								customerOrderItem.setOrderLength(salesOrderItem.getSaleorderLength());
								customerOrderItem.setContractLength(salesOrderItem.getContractLength());
								boolean isFirstTime = orderTaskService.checkFirstTime(salesOrderItem.getProductCode(),salesOrder.getContractNo());
								customerOrderItem.setIsFirstTime(isFirstTime);
								
								// #1 鎸夌収'erp缂栫爜+浜у搧瑙勬牸'杩涜鍖归厤
								String craftsId = productCraftsService.getCraftIdByProductCode(salesOrderItem.getProductCode());
								if (StringUtils.isEmpty(craftsId)) {
									// #2 鎸夌収'erp缂栫爜+浜у搧瑙勬牸' 涓庝笂涓�釜鐢熶骇杩囩殑璁㈠崟杩涜鍖归厤
									ProductCrafts craft = productCraftsService.getLastOrderUserdCrafts(salesOrderItem.getProductCode());
									if(craft==null){
										// #3 鎸夌収浜у搧鐨勫瀷鍙峰拰瑙勬牸杩涜鍖归厤
										String productType = salesOrderItem.getProductType(); // 浜у搧鍨嬪彿
										productType = productType.replaceAll("[([\u4E00-\u9FA5]++)]", "");
										productType = productType.replaceAll("(\\(+\\))", "");
										productSpecParse = productSpecParse.replaceAll("[([\u4E00-\u9FA5]++)]", "");
										productSpecParse = productSpecParse.replaceAll("(\\(+\\))", "");
										Product product = productService.getByProductTypeAndSpec(productType,productSpecParse);
										if(product != null){
											craftsId = productCraftsService.getCraftIdByProductCode(product.getProductCode());
										}
									}else{
										craftsId = craft.getId();
									}
								}
								String finalCraftsId = salesOrderItemService.dataSeparationFunction(craftsId, salesOrderItem.getId());
								customerOrderItem.setCraftsId(finalCraftsId);
								
								String wiresStructure = customerOrderService.getWiresStructure(craftsId); // 绌洪粯璁
								salesOrderItem.setWiresStructure(StringUtils.isEmpty(wiresStructure)? "" : wiresStructure); // 绾胯姱缁撴瀯
								salesOrderItemService.update(salesOrderItem);
								customerOrderItem.setStatus(ProductOrderStatus.TO_DO);
								customerOrderItem.setCustomerOaDate(date);
								customerOrderItemService.update(customerOrderItem);
								
								double leftLength = salesOrderItem.getSaleorderLength();
								
								CustomerOrderItemDec findParDec =  new CustomerOrderItemDec();
								findParDec.setOrderItemId(customerOrderItem.getId());
								CustomerOrderItemDec customerOrderItemDec = customerOrderItemDecService.getByObj(findParDec).get(0);
								customerOrderItemDec.setLength(leftLength);
								customerOrderItemDec.setOrderItemId(customerOrderItem.getId());
								customerOrderItemDec.setStatus(ProductOrderStatus.TO_DO);
								customerOrderItemDecService.update(customerOrderItemDec);
								
								relustObject.put("result", "success");
								relustObject.put("id", salesOrderItem.getId());
							}else{
								relustObject.put("result", "refuse");
								relustObject.put("id", salesOrderItem.getId());
								relustObject.put("message","浜у搧鍨嬪彿:" + salesOrderItem.getCustProductType()+ "宸茬粡瀛樺湪,璇峰嬁閲嶅娣诲姞.");
							}
						}
					} else if (OperateType.MODIFY.toString().equals(operateType)) {
						if (salesOrderItemExist != null) {
							salesOrderItem.setStandardLength(salesOrderItem.getSaleorderLength()); // 鏍囧噯闀垮害
							salesOrderItem.setProductCode(salesOrderItem.getProductCode()+"-"+productSpecParse);
							salesOrderItem.setNumberOfWires(ParseStringUtils.parseNumber(productSpecParse));
							salesOrderItem.setSection(ParseStringUtils.parseSection(productSpecParse));
							salesOrderItem.setContractLength(salesOrderItem.getSaleorderLength()); // 鍚堝悓闀垮害/璁㈠崟闀垮害
							
							//鏇存柊浜よ揣鏃堕棿
							List<CustomerOrder> customerOrderList = customerOrderDAO.getBySalesOrderId(salesOrderItem.getSalesOrderId());
							CustomerOrder customerOrder = customerOrderList.get(0);
							customerOrder.setOaDate(DateUtils.strToDate(customerOADate)); // 璁㈠崟浜よ揣鏈�
							customerOrderDAO.update(customerOrder);
							
							SalesOrder salesOrder = salesOrderService.getById(salesOrderItem.getSalesOrderId());
							CustomerOrderItem customerOrderItem = customerOrderItemService.getBySalesOrderItemId(salesOrderItem.getId());
//						CustomerOrderItem customerOrderItem = customerOrderItemList.get(0);
							customerOrderItem.setSalesOrderItemId(salesOrderItem.getId());
							customerOrderItem.setProductCode(salesOrderItem.getProductCode());
							customerOrderItem.setOrderLength(salesOrderItem.getSaleorderLength());
							customerOrderItem.setContractLength(salesOrderItem.getSaleorderLength());
							boolean isFirstTime = orderTaskService.checkFirstTime(salesOrderItem.getProductCode(),salesOrder.getContractNo());
							customerOrderItem.setIsFirstTime(isFirstTime);
							
							// #1 鎸夌収'erp缂栫爜+浜у搧瑙勬牸'杩涜鍖归厤
							String craftsId = productCraftsService.getCraftIdByProductCode(salesOrderItem.getProductCode());
							if (StringUtils.isEmpty(craftsId)) {
								// #2 鎸夌収'erp缂栫爜+浜у搧瑙勬牸' 涓庝笂涓�釜鐢熶骇杩囩殑璁㈠崟杩涜鍖归厤
								ProductCrafts craft = productCraftsService.getLastOrderUserdCrafts(salesOrderItem.getProductCode());
								if(craft==null){
									// #3 鎸夌収浜у搧鐨勫瀷鍙峰拰瑙勬牸杩涜鍖归厤
									String productType= salesOrderItem.getProductType(); // 浜у搧鍨嬪彿
									productType = productType.replaceAll("[([\u4E00-\u9FA5]++)]", "");
									productType = productType.replaceAll("(\\(+\\))", "");
									productSpecParse = productSpecParse.replaceAll("[([\u4E00-\u9FA5]++)]", "");
									productSpecParse = productSpecParse.replaceAll("(\\(+\\))", "");
									Product product = productService.getByProductTypeAndSpec(productType,productSpecParse);
									if(product != null){
										craftsId = productCraftsService.getCraftIdByProductCode(product.getProductCode());
									}
								}else{
									craftsId = craft.getId();
								}
							}
							String finalCraftsId = salesOrderItemService.dataSeparationFunction(craftsId, salesOrderItem.getId());
							customerOrderItem.setCraftsId(finalCraftsId);
							String wiresStructure = customerOrderService.getWiresStructure(craftsId); 
							salesOrderItem.setWiresStructure(StringUtils.isEmpty(wiresStructure)? "" : wiresStructure); // 绾胯姱缁撴瀯
							salesOrderItemService.update(salesOrderItem);
							customerOrderItemService.update(customerOrderItem);
							
							ManualManage manualManageParam = new ManualManage();
							manualManageParam.setSalesOrderItemId(salesOrderItem.getId());
							List<ManualManage> manualManageList = manualManageService.getByObj(manualManageParam);
							if(manualManageList.size()!=0){
								ManualManage manualManage = manualManageList.get(0);
								manualManage.setCoordinateTime(salesOrderItem.getModifyTime());
								manualManageService.update(manualManage);
							}
							
							
							double leftLength = salesOrderItem.getSaleorderLength();
							CustomerOrderItemDec params = new CustomerOrderItemDec();
							params.setOrderItemId(customerOrderItem.getId());
							List<CustomerOrderItemDec> customerOrderItemDecList = customerOrderItemDecService.getByObj(params);
							CustomerOrderItemDec customerOrderItemDec = customerOrderItemDecList.get(0);
							customerOrderItemDec.setLength(leftLength);
							customerOrderItemDec.setOrderItemId(customerOrderItem.getId());
							customerOrderItemDec.setStatus(ProductOrderStatus.TO_DO);
							customerOrderItemDecService.update(customerOrderItemDec);
							relustObject.put("result", "success");
							relustObject.put("id", salesOrderItem.getId());
						} else {
							relustObject.put("result", "refuse");
							relustObject.put("id", salesOrderItem.getId());
							relustObject.put("message","浜у搧鍨嬪彿:" + salesOrderItem.getCustProductType()+ "涓嶅瓨鍦�");
						}
					} else {
						if (salesOrderItemExist == null) {
							relustObject.put("result", "refuse");
							relustObject.put("id", salesOrderItem.getId());
							relustObject.put("message","浜у搧鍨嬪彿:" + salesOrderItem.getCustProductType()+ "涓嶅瓨鍦�");
						} else {
							salesOrderItem.setStatus(SalesOrderStatus.CANCELED);
							salesOrderItemService.update(salesOrderItem);
							CustomerOrderItem customerOrderItem = customerOrderItemService.getBySalesOrderItemId(salesOrderItem.getId());
							customerOrderItem.setStatus(ProductOrderStatus.CANCELED);
							customerOrderItemService.update(customerOrderItem);
							CustomerOrderItemDec findParDec =  new CustomerOrderItemDec();
							findParDec.setOrderItemId(customerOrderItem.getId());
							CustomerOrderItemDec customerOrderItemDec = customerOrderItemDecService.getByObj(findParDec).get(0);
							customerOrderItemDec.setStatus(ProductOrderStatus.CANCELED);
							customerOrderItemDecService.update(customerOrderItemDec);
							relustObject.put("result", "success");
							relustObject.put("id", salesOrderItem.getId());
						}
					}
					
				} catch (Exception e) {
					relustObject.put("result", "false");
					relustObject.put("id", salesOrderItem.getId());
					relustObject.put("message", e.getLocalizedMessage());
				}
				resultJSONArray.add(relustObject);
			}
		}
		return resultJSONArray.toJSONString();
	}
	
	private void insertIntoCustomerOrder(SalesOrder salesOrder){
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setSalesOrderId(salesOrder.getId());
		customerOrder.setCustomerOrderNo(NoGenerator.generateNoByDate());
		customerOrder.setRemarks(salesOrder.getRemarks());
		customerOrder.setStatus(CustomerOrderStatus.TO_DO);
		customerOrder.setOrgCode(salesOrder.getOrgCode());
		customerOrder.setFixedOa(false);
		customerOrder.setCreateUserCode("erp");
		customerOrder.setModifyUserCode("erp");
		customerOrderDAO.insert(customerOrder);
	}
	
	private String parseProductSpec(String productSpec){
		String productSpecTemp = "";
		if(productSpec.matches(REGEX_1)){
			productSpecTemp = productSpec.replaceAll("\\锛�\\(?[\u4E00-\u9FA5]+\\d{0,9}([\u4E00-\u9FA5]+)?\\)?\\锛�", "");
		}else if(productSpec.matches(REGEX_2)){
			productSpecTemp = productSpec.replaceAll("\\(?\\锛�[\u4E00-\u9FA5]+\\)?\\锛�", "");
		}else if(productSpec.matches(REGEX_3)){
			productSpecTemp = productSpec.replaceAll("\\(?\\锛�[\u4E00-\u9FA5]+\\d*([\u4E00-\u9FA5]+)?\\)?\\锛�", "");
		}else if(productSpec.contains("+S")){				
			productSpecTemp = productSpec.replaceAll("[+S]{2}","").replaceAll("[()]", "").replaceAll("\\[", "").replaceAll("\\]", "");
		}else if(productSpec.contains("B")&&!productSpec.contains("BS")){
			productSpecTemp = productSpec.replaceAll("[^\u002bB0-9\\*\\.\\[\\]]","");
			int indexB = productSpecTemp.indexOf('B');
			StringBuilder sb = new StringBuilder(productSpecTemp);
			sb.insert(indexB, "(");
			sb.insert(indexB+2, ")");
			productSpecTemp = sb.toString();
		}else if(productSpec.contains("BS")){
			productSpecTemp = productSpec.replaceAll("\\(BS\\d*\\)?","");
		}else if(productSpec.contains("P")&&!productSpec.contains("PE")){
			if(productSpec.contains("P*")){
				productSpecTemp = productSpec.replaceAll("P","*2");
			}else{
				productSpecTemp = productSpec.replaceAll("P","*2*");
			}
		}else if(productSpec.contains("PE")||productSpec.contains("E")){
			String[] productSpecArray = productSpec.split("\\+");
			if(productSpecArray.length>1&&!productSpec.contains("MECH. PROTEC.")){
				String[] productSpecArrayOne = productSpecArray[0].split("\\*");
				Integer number = Integer.valueOf(productSpecArrayOne[0]) + 1;
				productSpecTemp = number + "*" + productSpecArrayOne[1];
			}
		}else if(productSpec.contains("AWG")){
			productSpecTemp = productSpec.replaceAll("\\(\\d*[\u4E00-\u9FA5]+\\)","");
		}else{
			productSpecTemp = productSpec.replaceAll("\\([\u4E00-\u9FA5]+\\)", "");
		}
		return productSpecTemp;
	}
	
//	private Product patternMethod(String productType, String productSpec) {
//		Product product=null;
//		Map<String,String> param=new HashMap<String,String>();
//		param.put("productSpec", productSpec);
//		Pattern p1=Pattern.compile(".*\\d{1,3}\\/\\d{1,3}[kv]{1,2}",Pattern.CASE_INSENSITIVE);
//		Pattern p2=Pattern.compile(".*\\d{1,3}\\.\\d{1,3}\\/\\d{1,3}[kv]{1,2}",Pattern.CASE_INSENSITIVE);
//		Pattern p=Pattern.compile("[kv]{1,2}$",Pattern.CASE_INSENSITIVE);
//		Matcher m1=p1.matcher(productType);
//		Matcher m2=p2.matcher(productType);
//		Matcher m=p.matcher(productType);
//		if(m1.matches()||m2.matches()){
//			if(m.find()){
//				productType=productType.substring(0, m.start());
//				param.put("productType", productType);
//				product=productDAO.getProBySpecific(param);
//				if(product==null && productSpec.endsWith(".0")){
//					productSpec=productSpec.substring(0,productSpec.length()-2);
//					param.put("productSpec", productSpec);
//					product=productDAO.getProBySpecific(param);
//				}
//			
//			}
//		}
//		return product;
//	}

	class UploadFileThread extends Thread {
		private AttachFile attachFile;
		private File file;
		private CountDownLatch countDownLatch;
		private JSONObject resultObject;
		
		@Resource
		private AttachFileService attachFileService;
		private final Logger logger = LoggerFactory.getLogger(UploadFileThread.class);

		public UploadFileThread(AttachFile attachFile, File file,
				CountDownLatch countDownLatch,JSONObject resultObject) {
			this.attachFile = attachFile;
			this.file = file;
			this.countDownLatch = countDownLatch;
			this.resultObject = resultObject;
		}

		public void run() {
			if (this.attachFileService == null) {
				this.attachFileService = (AttachFileService) ContextUtils.getBean(AttachFileService.class);
			}
			try {
				this.attachFileService.insert(attachFile);
				new FTPUtil().downFile("\\attachfile\\" + attachFile.getUploadTime() + File.separator + attachFile.getSysFileName(),"\\attachfile\\" + attachFile.getUploadTime(),file.getPath() + File.separator + attachFile.getRealFileName());
				countDownLatch.countDown();
			} catch (Exception e) {
				resultObject.put("attachFile", attachFile.getSysFileName() + ":false");
				logger.error("salesOrderId:"+attachFile.getSalesOrderId()+";闄勪欢鍚�"+attachFile.getSysFileName()+"涓嬭浇澶辫触");
				e.printStackTrace();
			}
		}
	}
}
