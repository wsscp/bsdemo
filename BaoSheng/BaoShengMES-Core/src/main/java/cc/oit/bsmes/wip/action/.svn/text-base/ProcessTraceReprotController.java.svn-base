package cc.oit.bsmes.wip.action;

import java.util.Collections;

import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.wip.model.ProductStatus;
import cc.oit.bsmes.wip.service.ProductStatusService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 工艺流程追溯
 * <p style="display:none">modifyRecord</p>
 * @author leiw
 * @date 2014-10-15 下午2:57:13
 * @since
 * @version
 */

@Controller
@RequestMapping("/wip/processTraceReport")
public class ProcessTraceReprotController {

	@Resource private SalesOrderService salesOrderService;
	@Resource private SalesOrderItemService salesOrderItemService; 
	@Resource private EquipInfoService equipInfoService;
	@Resource private ProductStatusService productStatusService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "processTraceReport");
        return "wip.processTraceReport"; 
    }
	
	@RequestMapping(value="/processTrace",method = RequestMethod.GET)
	@ResponseBody
	public TableView processTraceList(@RequestParam String contractNo,@RequestParam String productCode,@RequestParam(required=false) String processCode,@RequestParam(required=false) String equipCode)  {
		ProductStatus productStatus=new ProductStatus();
		productStatus.setContractNo(contractNo);
		productStatus.setProductCode(productCode);
		if(StringUtils.isNotEmpty(processCode)){
			productStatus.setProcessCode(processCode);
		}
		if(StringUtils.isNotBlank(equipCode)){
			productStatus.setEquipCode(equipCode);
		}
		TableView tableView = new TableView();
	    tableView.setRows(productStatusService.getProcessReport(productStatus));
	    tableView.setTotal(productStatusService.countTotalProcessReport(productStatus));
		return tableView;
	}
	
	@ResponseBody
	@RequestMapping(value="getContractNo",method=RequestMethod.GET)		    
	public List<SalesOrder> getContractNo(){
		SalesOrder salesOrder=new SalesOrder();
		salesOrder.setOrgCode(SessionUtils.getUser().getOrgCode());
	    List<SalesOrder> result = salesOrderService.find(salesOrder, 0, Integer.MAX_VALUE,null);
	    return result;
	}
	
	@RequestMapping(value="/product/{id}/{query}",method = RequestMethod.GET)
	@ResponseBody
	public List<SalesOrderItem> product(@PathVariable String id,@PathVariable String query){
		if(StringUtils.isNotBlank(id)){
			return salesOrderItemService.getBySalesOrderId(id);
		}else{
			return null;
		}
	}
	
	
	/**
	 * @Title:       equip
	 * @Description: TODO(根据工段获取设备)
	 * @param:       @param processCode
	 * @param:       @return   
	 * @return:      List<EquipInfo>   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "equip", method = RequestMethod.GET)
	public List<EquipInfo> equip(@RequestParam(required = false) String processCode) {
		List<EquipInfo> result;
		if ("编织".equals(processCode)) {
			List<EquipInfo> list = equipInfoService.getByProcessSection(SessionUtils.getUser().getOrgCode(),
					EquipType.PRODUCT_LINE, "成缆");
			result = new ArrayList<EquipInfo>();
			for (EquipInfo info : list) {
				if ("编织".equals(info.getProcessCode())) {
					result.add(info);
				}
			}

		} else if ("成缆".equals(processCode)) {
			List<EquipInfo> list = equipInfoService.getByProcessSection(SessionUtils.getUser().getOrgCode(),
					EquipType.PRODUCT_LINE, "成缆");
			result = new ArrayList<EquipInfo>();
			for (EquipInfo info : list) {
				if (!"编织".equals(info.getProcessCode())) {
					result.add(info);
				}
			}
		} else {
			result = equipInfoService.getByProcessSection(SessionUtils.getUser().getOrgCode(), EquipType.PRODUCT_LINE,
					processCode);
		}
		Collections.sort(result, new Comparator<EquipInfo>() {
			@Override
			public int compare(EquipInfo o1, EquipInfo o2) {
				return o1.getCode().compareTo(o2.getCode());
			}
		});
		return result;
	}
}
