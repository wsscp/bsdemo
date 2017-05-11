package cc.oit.bsmes.wip.action;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.history.pla.model.HisCustomerOrderItem;
import cc.oit.bsmes.history.pla.service.HisCustomerOrderItemService;
import cc.oit.bsmes.history.wip.service.HisProductTraceService;
import cc.oit.bsmes.history.wip.service.HisWorkOrderService;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.wip.model.ProductStatus;
import cc.oit.bsmes.wip.service.ProductTraceService;

import com.alibaba.fastjson.JSONArray;

@Controller
@RequestMapping("/wip/productTrace")
public class ProductTraceController {
	@Resource
	private ProductTraceService productTraceService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private HisCustomerOrderItemService hisCustomerOrderItemService;
	@Resource
	private HisProductTraceService hisProductTraceService;
	@Resource
	private HisWorkOrderService hisWorkOrderService;
	
	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "productTrace");
		return "wip.productTrace";
	}

	@RequestMapping
	@ResponseBody
	public TableView list(HttpServletRequest request, @ModelAttribute ProductStatus param, @RequestParam String sort,
			@RequestParam int page, @RequestParam int start, @RequestParam int limit) {
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		Map<String, Object> findParams = new HashMap<String, Object>();
		// 将查询条件放入查询对象
		this.putQueryParam2Map(request, findParams, null, new String[] { "createDate", "isYunMu", "finishJy" }, start,
				(start + limit), sortArray);
		List<Map<String, String>> list = customerOrderItemService.getHandScheduleOrder(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(customerOrderItemService.countHandScheduleOrder(findParams));
		return tableView;
	}

	/**
	 * @Title: getTraceData
	 * @Description: TODO(生产追溯明细信息获取数据)
	 * @param: request 请求
	 * @param: orderItemId 订单ID
	 * @return: Map<String,List<Map<String,String>>>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "getTraceInitData", method = RequestMethod.GET)
	public Map<String, List<Map<String, String>>> getTraceData(HttpServletRequest request,
			@RequestParam String orderItemId) {
		// orderItemId = "dc74b4e4-08f0-4d7f-89aa-fa3741cefb14";
		// 1、实时库获取订单信息
		CustomerOrderItem customerOrderItem = customerOrderItemService.getById(orderItemId);
		// 2、历史库获取订单信息
		HisCustomerOrderItem hisCustomerOrderItem = hisCustomerOrderItemService.getById(orderItemId);
		// 3、历史库获取订单的生产单数量
	    Integer wonum = hisWorkOrderService.countByOrderItemId(orderItemId);
	    
		// 4、判断从历史库还是实时库获取
	    // 实时库获取条件: 实时库订单状态不是FINISHED || 历史库订单不存在 || 历史库生产单数量为零
	    if((null != customerOrderItem && customerOrderItem.getStatus() != ProductOrderStatus.FINISHED) 
	    		|| null == hisCustomerOrderItem || wonum == 0){
	    	return productTraceService.getLiveTraceData(orderItemId);
	    }else{
	    	return hisProductTraceService.getHisTraceData(orderItemId);
	    }
	}

	/**
	 * @Title: getTraceData
	 * @Description: TODO(根据生产单获取当前设备工艺参数的历史记录)
	 * @param: request 请求
	 * @param: workOrderNo 生产单编码
	 * @param: equipCode 设备编码
	 * @param: receiptCode 工艺参数
	 * @return: Map<String, List<Object[]>>
	 * @throws 
	 */
	@ResponseBody
	@RequestMapping(value = "getDaHistory", method = RequestMethod.GET)
	public Map<String, List<Object[]>> getDaHistory(HttpServletRequest request, @RequestParam String workOrderNo,
			@RequestParam String equipCode, @RequestParam String receiptCode) {
		return productTraceService.getDaHistory(workOrderNo, equipCode, receiptCode);
	}

	/**
	 * 把请求参数放入map对象
	 * 
	 * @param request HTTP请求
	 * @param findParams 放入的map对象
	 * @param query 模糊查询值
	 * @param queryType 模糊查询对象
	 * */
	@SuppressWarnings("unchecked")
	private void putQueryParam2Map(HttpServletRequest request, Map<String, Object> findParams, String[] likeParams,
			String[] equalsParams, Integer start, Integer end, List<Sort> sortArray) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Iterator<String> paramKeys = parameterMap.keySet().iterator();
		paramLoop: while (paramKeys.hasNext()) {
			String paramKey = paramKeys.next();
			String[] param = parameterMap.get(paramKey);
			if (null != param && param.length > 0 && StringUtils.isNotBlank(param[0])) {
				if (null != likeParams) {
					for (String likeParam : likeParams) {
						if (likeParam.equals(paramKey)) {
							findParams.put(paramKey, "%" + param[0] + "%");
							continue paramLoop;
						}
					}
				}
				if (null != equalsParams) {
					for (String equalsParam : equalsParams) {
						if (equalsParam.equals(paramKey)) {
							findParams.put(paramKey, param[0]);
							continue paramLoop;
						}
					}
				}
				findParams.put(paramKey, new ArrayList<String>(Arrays.asList(param)));
			}
		}
		findParams.put("start", start); // 顺序不能变，必须要覆盖
		findParams.put("end", end);
		findParams.put("sort", sortArray);
		findParams.put("orgCode", customerOrderItemService.getOrgCode());
	}

}
