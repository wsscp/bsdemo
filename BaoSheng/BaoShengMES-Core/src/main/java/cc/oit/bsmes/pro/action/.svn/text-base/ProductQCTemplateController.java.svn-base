package cc.oit.bsmes.pro.action;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.oit.bsmes.common.mybatis.Sort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cc.oit.bsmes.common.constants.ProcessType;
import cc.oit.bsmes.common.constants.ProductQCStatus;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pro.model.ProductQCTemplate;
import cc.oit.bsmes.pro.service.ProductQCTemplateService;

/**
 * 
 * 产品QC检验内容模板
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-12 下午2:45:17
 * @since
 * @version
 */
@Controller
@RequestMapping("/pro/productQCTemplate")
public class ProductQCTemplateController {
	
	@Resource
	private ProductQCTemplateService productQCTemplateService;
	
	@RequestMapping(produces="text/html")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("moduleName", "pro");
        model.addAttribute("submoduleName", "productQCTemplate");
		return "pro.productQCTemplate";
	}
	
	@RequestMapping
    @ResponseBody
    public TableView list(HttpServletRequest request,@ModelAttribute ProductQCTemplate findParams,@RequestParam int page, 
				    		@RequestParam int start,@RequestParam int limit, @RequestParam(required = false) String sort)  {
    	TableView tableView = new TableView();
    	if(StringUtils.equals("-1", findParams.getProductCode())){
    		findParams.setProductCode(null);
    	}
    	findParams.setOrgCode(SessionUtils.getUser().getOrgCode());
    	List<ProductQCTemplate> rows = productQCTemplateService.find(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
    	int total = productQCTemplateService.count(findParams);
    	tableView.setRows(rows);
    	tableView.setTotal(total);
    	return tableView;
    }
	@ResponseBody
	@RequestMapping(value="update",method = RequestMethod.GET)
	public String upddate(@RequestParam String jsonText){
		ProductQCTemplate productQCTemplate=JSON.parseObject(jsonText, ProductQCTemplate.class);
		ProductQCTemplate result=productQCTemplateService.getByNameAndProductCode(productQCTemplate.getName(),productQCTemplate.getProductCode());
		if(result!=null && StringUtils.isBlank(productQCTemplate.getId())){
			return "invalid";
		}else{
			if(StringUtils.isNotBlank(productQCTemplate.getId())){
				productQCTemplate.setModifyUserCode(SessionUtils.getUser().getUserCode());
				productQCTemplateService.update(productQCTemplate);
				return "update";
			}else{
				productQCTemplate.setStatus(ProductQCStatus.VALID);
				productQCTemplate.setOrgCode(SessionUtils.getUser().getOrgCode());
				productQCTemplateService.insert(productQCTemplate);
				return "insert";
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="delete",method = RequestMethod.GET)
	public void delete(@RequestParam String id){
		ProductQCTemplate productQCTemplate=new ProductQCTemplate();
		productQCTemplate.setId(id);
		productQCTemplate.setStatus(ProductQCStatus.INVALID);
		productQCTemplateService.update(productQCTemplate);
	}
	
	@RequestMapping(value="product/{type}",method=RequestMethod.GET)	
	@ResponseBody
	public JSONArray product(@PathVariable String type){
		Map<String, Product> productMap = StaticDataCache.getProductMap();
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.equals("0", type)){
			map.put("-1","------所有------");
		}
		for(String key:productMap.keySet()){
			map.put(key, key);
		}
		return JSONArrayUtils.mapToJSON(map);
	}
}
