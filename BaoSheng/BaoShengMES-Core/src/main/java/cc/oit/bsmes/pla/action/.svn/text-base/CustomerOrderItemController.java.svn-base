package cc.oit.bsmes.pla.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cc.oit.bsmes.common.constants.CustomerOrderStatus;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.inv.model.InventoryDetail;
import cc.oit.bsmes.inv.service.InventoryDetailService;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.ImportProduct;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.HighPriorityOrderItemService;
import cc.oit.bsmes.pla.service.ImportProductService;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessQcService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/pla/customerOrderItem")
public class CustomerOrderItemController {

    @Resource
    private CustomerOrderItemService customerOrderItemService;

    @Resource
    private CustomerOrderItemDecService customerOrderItemDecService;

    @Resource
    private CustomerOrderService customerOrderService;

    @Resource
    private ProcessQcService processQcService;
    @Resource
    private ProcessInOutService processInOutService;

    @Resource
    private HighPriorityOrderItemService highPriorityOrderItemService;

    @Resource
    private InventoryDetailService inventoryDetailService;

    @Resource
    private EquipListService equipListService;

    @Resource
    private SalesOrderService salesOrderService;
    
    @Resource
    private ImportProductService importProductService;

    @RequestMapping(produces = "text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "pla");
        model.addAttribute("submoduleName", "customerOrderItem");
        model.addAttribute("cusOrderStatus", JSONArrayUtils.enumToJSONForExt(CustomerOrderStatus.class));
        return "pla.customerOrderItem";
    }

    @ResponseBody
    @RequestMapping
    public TableView list(HttpServletRequest request, @RequestParam(required = false) String sort,
                          @RequestParam int page, @RequestParam int start, @RequestParam int limit) {
        SimpleDateFormat f = new SimpleDateFormat(DateUtils.DATE_FORMAT);

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> findParams = new HashMap<String, Object>();
        Iterator<String> it = parameterMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (parameterMap.get(key) != null && StringUtils.isNotBlank(parameterMap.get(key)[0])) {
                if (StringUtils.containsIgnoreCase(key, "exceedOa")) {
                    if (parameterMap.get(key).length == 1) {
                        findParams.put(key, parameterMap.get(key)[0]);
                    }
                } else if (StringUtils.containsIgnoreCase(key, "orderStatus")) {
                    findParams.put(key, parameterMap.get(key));
                } else if (StringUtils.containsIgnoreCase(key, "startDate")) {
                    try {
                        findParams.put(key, f.parse(parameterMap.get(key)[0]));
                    } catch (ParseException e) {
                    }
                } else if (StringUtils.containsIgnoreCase(key, "endDate")) {
                    try {
                        findParams.put(key, f.parse(parameterMap.get(key)[0]));
                    } catch (ParseException e) {
                    }
                } else if (StringUtils.containsIgnoreCase(key, "section")) {
                    findParams.put(key, parameterMap.get(key)[0]);
                } else {
                    findParams.put(key, "%" + parameterMap.get(key)[0] + "%");
                }
            }
        }

        if (findParams.get("orderStatus") == null) {
            findParams.put("orderStatus", new String[]{"TO_DO", "IN_PROGRESS", "CANCELED", "FINISHED"});
        }

        List<CustomerOrder> rows = customerOrderService.findByOrderInfo(findParams, start, limit,
                JSONArray.parseArray(sort, Sort.class));
        int total = customerOrderService.countByOrderInfo(findParams);
        TableView tableView = new TableView();
        tableView.setRows(rows);
        tableView.setTotal(total);
        return tableView;
    }

    @ResponseBody
    @RequestMapping(value = "findOrderItemInfo/{orderId}", method = RequestMethod.GET)
    public TableView findOrderItemInfo(HttpServletRequest request, @PathVariable String orderId) {
        String productSpec = request.getParameter("productSpec");
        String productType = request.getParameter("productType");
        String section = request.getParameter("section");
        String wiresStructure = request.getParameter("wiresStructure");
        CustomerOrderItem findParams = new CustomerOrderItem();
        SalesOrderItem salesOrderItem = new SalesOrderItem();
        if (StringUtils.isNotBlank(productSpec)) {
            salesOrderItem.setProductSpec("%" + productSpec + "%");
        }
        if (StringUtils.isNotBlank(productType)) {
            salesOrderItem.setProductType("%" + productType + "%");
        }
        if (StringUtils.isNotBlank(section)) {
            salesOrderItem.setSection(section);
        }
        if (StringUtils.isNotBlank(wiresStructure)) {
            salesOrderItem.setWiresStructure("%" + wiresStructure + "%");
        }
        findParams.setCustomerOrderId(orderId);
        findParams.setSalesOrderItem(salesOrderItem);
        List<CustomerOrderItem> rows = customerOrderItemService.findByOrderIdAndSalesOrderItemInfo(findParams);
        TableView tableView = new TableView();
        tableView.setRows(rows);
        return tableView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
        UpdateResult updateResult = new UpdateResult();
        CustomerOrder customerOrder = JSON.parseObject(jsonText, CustomerOrder.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(customerOrder.getCustomerOaDate());
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        customerOrderService.setCustomerOaDate(customerOrder, calendar.getTime());
        updateResult.addResult(customerOrder);
        return updateResult;
    }

    @ResponseBody
    @RequestMapping(value = "queryProcessReceipt", method = RequestMethod.GET)
    public List<ProcessReceipt> queryProcessReceipt(@RequestParam String processId) {
        List<EquipList> list = equipListService.getByProcessId(processId);
        List<ProcessReceipt> result = new ArrayList<ProcessReceipt>();
        for (EquipList equip : list) {
            result.addAll(equip.getProcessReceiptList());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "queryProcessQc", method = RequestMethod.GET)
    public List<ProcessQc> queryProcessQc(@RequestParam String processId) {
        List<ProcessQc> result = processQcService.getByProcessId(processId);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "queryProcessInOut", method = RequestMethod.GET)
    public List<ProcessInOut> queryProcessInOut(@RequestParam String processId) {
        List<ProcessInOut> result = processInOutService.getByProcessId(processId);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "findCraftProcess", method = RequestMethod.GET)
    public JSONArray findCraftProcess(@RequestParam String productCode) throws IllegalAccessException,
            InvocationTargetException {
        return customerOrderItemService.craftProcessHandle(productCode);
    }

    @ResponseBody
    @RequestMapping(value = "listCustomerOrder", method = RequestMethod.GET)
    public TableView listCustomerOrder(@RequestParam(required = false) String contractNo,
                                       @RequestParam(required = false) String productType, @RequestParam(required = false) String operator,
                                       @RequestParam String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) {
        TableView tableView = new TableView();
        CustomerOrder findParams = new CustomerOrder();
        if (StringUtils.isNotBlank(contractNo)) {
            findParams.setContractNo("%" + contractNo + "%");
        }
        if (StringUtils.isNotBlank(productType)) {
            findParams.setProductType("%" + productType + "%");
        }
        if (StringUtils.isNotBlank(operator)) {
            findParams.setOperator("%" + operator + "%");
        }
        List<CustomerOrder> rows = customerOrderService.findForSetPriority(findParams, start, limit,
                JSONArray.parseArray(sort, Sort.class));
        int total = customerOrderService.countForSetPriority(findParams);
        tableView.setRows(rows);
        tableView.setTotal(total);
        return tableView;
    }

    @ResponseBody
    @RequestMapping(value = "listCustomerOrder/{seq}", method = RequestMethod.GET)
    public TableView listCustomerOrder(@PathVariable Integer seq) {
        TableView tableView = new TableView();
        CustomerOrder findParams = new CustomerOrder();
        findParams.setSeq(seq);

        List<CustomerOrder> rows = customerOrderService.findForSetPriority(findParams, 0, 0, null);
        tableView.setRows(rows);
        return tableView;
    }

    @ResponseBody
    @RequestMapping(value = "updateSeq", method = RequestMethod.POST)
    public void updateSeq(@RequestParam String rightJsonText, @RequestParam String leftJsonText) {
        highPriorityOrderItemService.updateSeq(rightJsonText, leftJsonText);
    }

    @ResponseBody
    @RequestMapping(value = "findOrderItemDec/{orderItemId}", method = RequestMethod.GET)
    public List<CustomerOrderItemDec> findOrderItemDec(@PathVariable String orderItemId) {
        List<CustomerOrderItemDec> result = customerOrderItemDecService.getByOrderItemId(orderItemId);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "queryInventorys", method = RequestMethod.GET)
    public List<InventoryDetail> queryInventorys(@RequestParam Double orderLength, @RequestParam String productCode,
                                                 @RequestParam Double idealMinLength, @RequestParam String itemId) {
        List<InventoryDetail> result = inventoryDetailService.findByProductCodeAndOrderLength(productCode, orderLength,
                idealMinLength, itemId);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "saveCustomerOrderItem", method = RequestMethod.POST)
    public void saveCustomerOrderItem(@RequestParam(required = false) String deleteJsonData,
                                      @RequestParam(required = false) String updateJsonData,
                                      @RequestParam(required = false) String offsetJsonData, @RequestParam String orderItemId,
                                      @RequestParam(required = false) String offsetItemDecId, @RequestParam(required = false) Double offsetLength) {
        customerOrderItemDecService.splitCustomerOrderItem(deleteJsonData, updateJsonData, offsetJsonData, orderItemId,
                offsetItemDecId, offsetLength);
    }

    /**
     * 按钮确认交货期
     *
     * @param customerOrderId
     * @author DingXintao
     * @date 2014-10-27 17:50:58
     */
    @ResponseBody
    @RequestMapping(value = "confirmOrderDeliver/{customerOrderId}", method = RequestMethod.POST)
    public void confirmOrderDeliver(@PathVariable String customerOrderId) {
        customerOrderService.confirmOrderDeliver(customerOrderId);
    }

    @ResponseBody
    @RequestMapping(value = "cancelOrderItem/{orderItemId}", method = RequestMethod.POST)
    public void cancelOrderItem(@PathVariable String orderItemId) {
        customerOrderItemService.cancel(orderItemId);
    }

    @ResponseBody
    @RequestMapping(value = "updateItemOaDate", method = RequestMethod.POST)
    public void updateItemOaDate(@RequestParam String id, @RequestParam String subOaDate) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        CustomerOrderItem item = new CustomerOrderItem();
        item.setId(id);
        try {
            item.setSubOaDate(f.parse(subOaDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        customerOrderItemService.update(item);
    }

    @ResponseBody
    @RequestMapping(value = "addOrderItem", method = RequestMethod.POST)
    public void addOrderItem(@RequestParam String subOrderLengths, @RequestParam String id) {
        customerOrderItemService.itemSplit(id, subOrderLengths);
    }

    @ResponseBody
    @RequestMapping(value = "getItemOffsetDetail/{itemId}", method = RequestMethod.GET)
    public List<InventoryDetail> getItemOffsetDetail(@PathVariable String itemId) {
        List<InventoryDetail> list = inventoryDetailService.getByOrderItemId(itemId);
        return list;
    }

    @RequestMapping(value = "/export/{fileName}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject export(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName,
                             @RequestParam String params, @RequestParam(required = false) String queryParams) throws IOException,
            WriteException, InvocationTargetException, IllegalAccessException, ClassNotFoundException,
            NoSuchMethodException {
        JSONObject queryFilter = JSONObject.parseObject(queryParams);
        int countExportRows = customerOrderService.countForExport(queryFilter);
        Integer maxExportLine = Integer.parseInt(WebContextUtils.getPropValue(WebConstants.MAX_EXPORT_LINE));
        if (countExportRows > maxExportLine) {
            JSONObject result = new JSONObject();
            result.put("msg", "export rows is than maxExportLine");
            return result;
        }
        JSONArray columns = JSONArray.parseArray(params);
        String sheetName = fileName;
        fileName = URLEncoder.encode(fileName, "UTF8") + ".xls";
        String userAgent = request.getHeader("User-Agent").toLowerCase(); // 获取用户代理
        if (userAgent.indexOf("msie") != -1) { // IE浏览器
            fileName = "filename=\"" + fileName + "\"";
        } else if (userAgent.indexOf("mozilla") != -1) { // firefox浏览器
            fileName = "filename*=UTF-8''" + fileName;
        }
        response.reset();
        response.setContentType("application/ms-excel");
        response.setHeader("Content-Disposition", "attachment;" + fileName);
        OutputStream os = response.getOutputStream();
        customerOrderService.export(os, sheetName, columns, queryFilter);
        os.close();
        return null;
    }

    @RequestMapping(value = "/importOrders", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importOrders(HttpServletRequest request, @RequestParam MultipartFile importFile) throws Exception {
        org.apache.poi.ss.usermodel.Workbook workbook = null;

        try {
            workbook = new XSSFWorkbook(importFile.getInputStream());
        } catch (Exception e) {
            workbook = new HSSFWorkbook(importFile.getInputStream());
        }

        Sheet sheet = workbook.getSheetAt(0);
        JSONObject result = new JSONObject();
        if (sheet == null) {
            result.put("message", "导入文件sheet，请命名为'生产计划'");
            result.put("success", false);
        } else {
            salesOrderService.importProPlan(sheet, SessionUtils.getUser().getOrgCode(), result);
        }
        return result;
    }

    /**
     * 新的导入方法： 不作分卷，分卷表只有一条数据，不拆分
     */
    @RequestMapping(value = "/importOrdersToItemDec", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importOrdersToItemDec(HttpServletRequest request, @RequestParam MultipartFile importFile)
            throws Exception {
        org.apache.poi.ss.usermodel.Workbook workbook = null;

        try {
            workbook = new XSSFWorkbook(importFile.getInputStream());
        } catch (Exception e) {
            workbook = new HSSFWorkbook(importFile.getInputStream());
        }

        Sheet sheet = workbook.getSheetAt(0);
        JSONObject result = new JSONObject();
        if (sheet == null) {
            result.put("message", "导入文件sheet，请命名为'生产计划'");
            result.put("success", false);
        } else {
            salesOrderService.importProPlanToItemDec(sheet, SessionUtils.getUser().getOrgCode(), result);
        }
        return result;
    }

    @RequestMapping(value = "/seriesName", method = RequestMethod.GET)
    @ResponseBody
    public List<ImportProduct> seriesName(@RequestParam(required = false) String sort){
    	List<Sort> sortArray=JSONArray.parseArray(sort, Sort.class);
    	List<ImportProduct> result=importProductService.getSeriesNameAndUserName(sortArray);
    	return result;   	
    }
    
    
    @RequestMapping(value = "/userName", method = RequestMethod.GET)
    @ResponseBody
    public List<ImportProduct> userName(@RequestParam(required = false) String sort){
    	List<Sort> sortArray=JSONArray.parseArray(sort, Sort.class);
    	List<ImportProduct> result=importProductService.getUserName(sortArray);
    	return result;   	
    }
    
    
    @RequestMapping(value = "/importPrcvXml", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importPrcvXml() throws IOException{
    	JSONObject result=new JSONObject();
    	StringBuffer log=new StringBuffer();
    	importProductService.importPrcvXml(log);
    	String location="\\\\192.167.29.93\\问题清单\\工艺文件问题清单";   	
    	importProductService.createFileForPrcvXml(log,location);
    	result.put("success", true);
    	return result;   	
    }
    
    @RequestMapping(value = "/importProductsToItemDec", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importProductsToItemDec(HttpServletRequest request, 
                                              @RequestParam(required = false) MultipartFile plmProductDetail,
                                              @RequestParam(required = false) MultipartFile plmMpartDetail,
                                              @RequestParam(required = false) MultipartFile insertMpartObj,
                                              @RequestParam(required = false) MultipartFile plmProcessDetail,
                                              @RequestParam(required = false) MultipartFile insertScx)
            throws Exception {
        StringBuffer log=new StringBuffer();
        String seriesName=request.getParameter("seriesName").toUpperCase();
        String userName=SessionUtils.getUser().getName();
        JSONObject result = new JSONObject();       
        String importType="";
        String location="\\\\192.167.29.93\\plm录入数据excel\\问题清单\\"+userName+"\\"+seriesName;
        String status="";
        String otherStatus="2";
        Map<String,String> param=new HashMap<String,String>();
		param.put("plmProductDetail", "setProductStatus");
		param.put("plmMpartDetail", "setMpartStatus");
		param.put("insertMpartObj", "setInsertMpartStatus");
		param.put("plmProcessDetail", "setProcessStatus");
		param.put("insertScx", "setScxStatus");
        if(!plmProductDetail.isEmpty() ){
        	importType="plmProductDetail";       	
        	importProductService.importProductDetail(plmProductDetail, log);
            if(null!=log && log.length()!=0){
            	status="0";
            	importProductService.createFile(log,userName,location,importType);
            	if(importProductService.checkExistsByName(seriesName).size()==0){
            		importProductService.insertNewRecord(param,seriesName,userName,location,status,importType
            		,otherStatus);
            	}
            	
            	else{
            		importProductService.updateRecordBySeriesName(param,seriesName,userName,status,importType);
            	}
            }
            else{
            	status="1";
            	importProductService.deleteFile(location,importType);
            	if(importProductService.checkExistsByName(seriesName).size()==0){
            		importProductService.insertNewRecord(param,seriesName,userName,location,status,importType
            		,otherStatus);
            	}
            	else{
            		importProductService.updateRecordBySeriesName(param,seriesName,userName,status,importType);
            	}
            }
        }   
        
        if(!plmMpartDetail.isEmpty() ){       	
        	importType="plmMpartDetail";
        	importProductService.importMpartDetail(plmMpartDetail, log);
        	if(null!=log && log.length()!=0){
        		status="0";
        		importProductService.createFile(log,userName,location,importType);
        		if(importProductService.checkExistsByName(seriesName).size()==0){
            		importProductService.insertNewRecord(param,seriesName,userName,location,status,importType
                    		,otherStatus);
            	}
        		else{
        			importProductService.updateRecordBySeriesName(param,seriesName,userName,status,importType);
        		}
        	}
        	else{
        		status="1";
        		importProductService.deleteFile(location,importType);
        		if(importProductService.checkExistsByName(seriesName).size()==0){
            		importProductService.insertNewRecord(param,seriesName,userName,location,status,importType
            		,otherStatus);
            	}
            	else{
            		importProductService.updateRecordBySeriesName(param,seriesName,userName,status,importType);
            	}
        	}        	
        }
        if(!insertMpartObj.isEmpty()){
        	importType="insertMpartObj";
        	importProductService.importInsertMpartObj(insertMpartObj, log);
        	if(null!=log && log.length()!=0){
        		status="0";
        		importProductService.createFile(log,userName,location,importType);
        		if(importProductService.checkExistsByName(seriesName).size()==0){
            		importProductService.insertNewRecord(param,seriesName,userName,location,status,importType
                    		,otherStatus);
            	}
        		else{
        			importProductService.updateRecordBySeriesName(param,seriesName,userName,status,importType);
        		}
        	}
        	else{
        		status="1";
        		importProductService.deleteFile(location,importType);
        		if(importProductService.checkExistsByName(seriesName).size()==0){
            		importProductService.insertNewRecord(param,seriesName,userName,location,status,importType
            		,otherStatus);
            	}
            	else{
            		importProductService.updateRecordBySeriesName(param,seriesName,userName,status,importType);
            	}
        	}        
        	
        }
        if(!plmProcessDetail.isEmpty()){
        	importType="plmProcessDetail";
        	importProductService.importProcessDetail(plmProcessDetail, log);
        	if(null!=log && log.length()!=0){
        		status="0";
        		importProductService.createFile(log,userName,location,importType);
        		if(importProductService.checkExistsByName(seriesName).size()==0){
            		importProductService.insertNewRecord(param,seriesName,userName,location,status,importType
                    		,otherStatus);
            	}
        		else{
        			importProductService.updateRecordBySeriesName(param,seriesName,userName,status,importType);
        		}
        	}
        	else{
        		status="1";
        		importProductService.deleteFile(location,importType);
        		if(importProductService.checkExistsByName(seriesName).size()==0){
            		importProductService.insertNewRecord(param,seriesName,userName,location,status,importType
            		,otherStatus);
            	}
            	else{
            		importProductService.updateRecordBySeriesName(param,seriesName,userName,status,importType);
            	}
        	}   
        }
        
        if(!insertScx.isEmpty()){
        	importType="insertScx";
        	importProductService.importInsertScx(insertScx, log);
        	if(null!=log && log.length()!=0){
        		status="0";
        		importProductService.createFile(log,userName,location,importType);
        		if(importProductService.checkExistsByName(seriesName).size()==0){
            		importProductService.insertNewRecord(param,seriesName,userName,location,status,importType
                    		,otherStatus);
            	}
        		else{
        			importProductService.updateRecordBySeriesName(param,seriesName,userName,status,importType);
        		}
        	}
        	else{
        		status="1";
        		importProductService.deleteFile(location,importType);
        		if(importProductService.checkExistsByName(seriesName).size()==0){
            		importProductService.insertNewRecord(param,seriesName,userName,location,status,importType
            		,otherStatus);
            	}
            	else{
            		importProductService.updateRecordBySeriesName(param,seriesName,userName,status,importType);
            	}
        	}  
        }
        File file=new File(location);
        if(file.listFiles().length==0){
        	file.delete();
        }
        result.put("success", true);
        result.put("message", "导入完成,请进入详情查看导入结果");
        return result;
	}
        
        @RequestMapping(value = "/importProDe", method = RequestMethod.GET)
    	@ResponseBody
    	public TableView importProDe(HttpServletRequest request,
    			@RequestParam(required = false) Integer start,
    			@RequestParam(required = false) Integer limit,
    			@RequestParam(required = false) String sort, @RequestParam int page)
    			throws Exception {
        	List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
        	Map<String, Object> param = new HashMap<String, Object>();
        	String seriesName=request.getParameter("seriesName");
        	String createUserCode=request.getParameter("createUserCode");
        	String createTime=request.getParameter("createTime");
        	if(seriesName!=null&&StringUtils.isNotBlank(seriesName)){
						param.put("seriesName", seriesName);      			
        		}
        	if(createUserCode!=null&&StringUtils.isNotBlank(createUserCode)){
				param.put("createUserCode", createUserCode);      			
		}
        	if(createTime!=null&&StringUtils.isNotBlank(createTime)){
				param.put("createTime", createTime);      			
		}
        	param.put("start", start);
    		param.put("end", (start + limit));
    		List<ImportProduct> lists=customerOrderService.getImportProduct(param, start, limit, sortArray);
    		TableView tableView = new TableView();
    		tableView.setRows(lists);
    		tableView.setTotal(customerOrderService.countImportProduct(param));
			return tableView;
        }
		
/*
        Sheet sheet = workbook.getSheetAt(0);
		
		if (sheet == null) {
			result.put("message", "导入文件sheet，请命名为'生产计划'");
			result.put("success", false);
		} else {
			salesOrderService.importProPlanToItemDec(sheet, SessionUtils.getUser().getOrgCode(), result);
		}*/


    @RequestMapping(value = "/downImportTemplate", method = RequestMethod.POST)
    @ResponseBody
    public void downImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        String downLoadPath = this.getClass().getResource("/").getPath() + "exportfile/importOrdorTemplate.xls";
        String fileName = "生产计划导入模板";
        fileName = URLEncoder.encode(fileName, "UTF8") + ".xls";
        String userAgent = request.getHeader("User-Agent").toLowerCase(); // 获取用户代理
        if (userAgent.indexOf("msie") != -1) { // IE浏览器
            fileName = "filename=\"" + fileName + "\"";
        } else if (userAgent.indexOf("mozilla") != -1) { // firefox浏览器
            fileName = "filename*=UTF-8''" + fileName;
        }
        response.reset();
        response.setContentType("application/ms-excel");
        response.setHeader("Content-Disposition", "attachment;" + fileName);

        bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }
}
