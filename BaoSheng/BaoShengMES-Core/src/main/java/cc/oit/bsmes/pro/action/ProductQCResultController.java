package cc.oit.bsmes.pro.action;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cc.oit.bsmes.common.mybatis.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pro.model.ProductQCResult;
import cc.oit.bsmes.pro.service.ProductQCResultService;

/**
 * 
 *产品QC检验结果
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-13 下午5:40:18
 * @since
 * @version
 */
@Controller
@RequestMapping("/pro/productQCResult")
public class ProductQCResultController {
	@Resource private ProductQCResultService productQCResultService;
	@Resource private EventInformationService eventInformationService;
	@Resource private ProductService productService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "pro");
        model.addAttribute("submoduleName", "productQCResult");
        return "pro.productQCResult"; 
    }
	
	@RequestMapping
    @ResponseBody
    public TableView list(HttpServletRequest request,@ModelAttribute ProductQCResult findParams,@RequestParam int page, 
				    		@RequestParam int start,@RequestParam int limit, @RequestParam(required = false) String sort)  {
    	TableView tableView = new TableView();
    	List<ProductQCResult> rows = productQCResultService.find(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
    	int total = productQCResultService.count(findParams);
    	tableView.setRows(rows);
    	tableView.setTotal(total);
    	return tableView;
    }
	
	@ResponseBody
	@RequestMapping(value="update",method = RequestMethod.GET)
	public void update(@RequestParam String jsonText){
		ProductQCResult productQCDetail=JSON.parseObject(jsonText, ProductQCResult.class);
		productQCResultService.update(productQCDetail);
	}
	
	@ResponseBody
	@RequestMapping(value="delete",method = RequestMethod.GET)
	public void delete(@RequestParam String id){
		productQCResultService.deleteById(id);
	}
	
	@ResponseBody
	@RequestMapping(value="generation",method = RequestMethod.GET)
	public void generation(@RequestParam String jsonText,@RequestParam(required=false) Boolean checked){
		EventInformation eventInfo=new EventInformation();
		eventInfo.setId(UUID.randomUUID().toString());
		eventInfo.setEventTitle("QC警报生成");
		eventInfo.setEventContent("QC警报生成,QC检验不合格");
		eventInfo.setEventStatus(EventStatus.UNCOMPLETED);
		eventInfo.setProcessTriggerTime(new Date());
		eventInfo.setBatchNo(jsonText);
		eventInfo.setCode(EventTypeContent.QC.name());
		eventInfo.setPendingProcessing(checked);
		eventInfo.setProductCode(jsonText);
		eventInformationService.insertInfo(eventInfo);
	}
	
	@ResponseBody
    @RequestMapping(value = "product", method = RequestMethod.GET)
    public List<Product> product() {
		List<Product> products=productService.getAll();
		Product pro=new Product();
		pro.setProductName("全部");
		products.add(0, pro);
		return products;
    }
}
