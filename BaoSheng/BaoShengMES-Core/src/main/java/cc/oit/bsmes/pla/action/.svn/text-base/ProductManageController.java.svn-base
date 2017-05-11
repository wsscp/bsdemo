package cc.oit.bsmes.pla.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.OrderPushStatus;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.SalesOrderStatus;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.wip.model.FinishedOrder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


@Controller
@RequestMapping("/pla/productManage")
public class ProductManageController {
	
	
	
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	
	@Resource
	private SalesOrderItemService salesOrderItemService;
	@Resource
	private SalesOrderService salesOrderService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "pla");
		model.addAttribute("submoduleName", "productManage");
		return "pla.productManage";
	}
	
	@RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(HttpServletRequest request, @RequestParam String sort,
                          @RequestParam int page, @RequestParam int start, @RequestParam int limit) {
		Map<String, Object> param = new HashMap<String, Object>();
		this.paramToMap(param,request,start,limit);
        List list = customerOrderItemService.getProductManageList(param);
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(customerOrderItemService.countProductManageList(param));
        return tableView;
    }
	@SuppressWarnings("unchecked")
	private void paramToMap(Map<String, Object> param,
			HttpServletRequest request, int start, int limit) {
		Map<String,String[]> parameterMap = request.getParameterMap();
		Iterator<String> paramKeys = parameterMap.keySet().iterator();
		while (paramKeys.hasNext()) {
			String paramKey = paramKeys.next();
			String[] paramValues = parameterMap.get(paramKey);
			if (null != paramValues && paramValues.length > 0 && StringUtils.isNotBlank(paramValues[0])) {
				param.put(paramKey, new ArrayList<String>(Arrays.asList(paramValues)));
			}
		}
		param.put("start", Integer.valueOf(start));
		param.put("end", Integer.valueOf(start + limit));
	}

	@RequestMapping(value="/updateOrdersStatus",method = RequestMethod.POST)
	@ResponseBody
	@Transactional(readOnly=false)
	public JSON updateOrdersStatus(@RequestParam String jsonData/*,@RequestParam String quality,@RequestParam String message*/){
		List<CustomerOrderItem> list = JSONArray.parseArray(jsonData, CustomerOrderItem.class);
		boolean flag = true;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		for(CustomerOrderItem orderItem: list){
			SalesOrderItem salesOrderItem =  salesOrderItemService.getById(orderItem.getSalesOrderItemId());
			CustomerOrderItem customerOrderItem = customerOrderItemService.getById(orderItem.getId());
			if("erp".equals(orderItem.getCreateUserCode())){
				Map<String,Object> map = salesOrderItemService.getReportLength(orderItem.getId());
				if(map==null){
					flag = false;
					String[] strs = {orderItem.getContractNo(),orderItem.getCustProductType(),orderItem.getCustProductSpec(),orderItem.getContractLength().toString()};
					paramMap.put(orderItem.getId(), strs);
					continue;
				}
				if(map.containsKey("JJR") && map.containsKey("JJRID") && map.containsKey("JJSL")){
					FinishedOrder order = new FinishedOrder();
					String processCode = salesOrderItemService.getLastProcessCode(orderItem.getSalesOrderItemId());
					order.setSalesOrderItemId(orderItem.getSalesOrderItemId());
					order.setPushStatus(OrderPushStatus.TO_PUSH);
					order.setProcessCode(processCode);
					order.setJjsl(Double.parseDouble(map.get("JJSL").toString())/1000);
					order.setJjr(map.get("JJR").toString());
					order.setJjrid(map.get("JJRID").toString());
					order.setJldw("公里");
					salesOrderItemService.insertToFinishedOrder(order);
					salesOrderItem.setStatus(SalesOrderStatus.QUALIFIED);
					customerOrderItem.setStatus(ProductOrderStatus.QUALIFIED);
				}else{
					flag = false;
					String[] strs = {orderItem.getContractNo(),orderItem.getCustProductType(),orderItem.getCustProductSpec(),orderItem.getContractLength().toString()};
					paramMap.put(orderItem.getId(), strs);
					continue;
				}
			}else{//如果不是erp导过来的，直接完成
				salesOrderItem.setStatus(SalesOrderStatus.QUALIFIED);
				customerOrderItem.setStatus(ProductOrderStatus.QUALIFIED);
			}
			salesOrderItemService.update(salesOrderItem);
			customerOrderItemService.update(customerOrderItem);
		}
		if(flag){
			return JSONArrayUtils.ajaxJsonResponse(true, "订单完成，准备向ERP推送信息。。");
		}else{
			return JSONArrayUtils.ajaxJsonResponse(false, "失败",paramMap);
		}
	}

}
