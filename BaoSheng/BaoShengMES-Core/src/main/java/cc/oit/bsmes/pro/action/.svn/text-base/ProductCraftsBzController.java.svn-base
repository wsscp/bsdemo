package cc.oit.bsmes.pro.action;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.model.ProductCraftsBz;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import cc.oit.bsmes.pro.service.ProductCraftsBzService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 陈翔
 */
@Controller
@RequestMapping("/pro/craftsBz")
public class ProductCraftsBzController {

	@Resource
	private ProductCraftsBzService productCraftsBzService;
	@Resource
	private ProcessInformationService processInformationService;
	@Resource
	private ProductService productService;
	
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "pro");
        model.addAttribute("submoduleName", "crafts");
        return "pro.crafts"; 
    }
	
	@RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public TableView list(HttpServletRequest request, ProductCraftsBz findParams, 
    		@RequestParam String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) 
    				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException  {
		List<ProductCraftsBz> result = productCraftsBzService.find(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(result);
    	tableView.setTotal(productCraftsBzService.count(findParams));
    	return tableView;
    }
	
    @ResponseBody
    @RequestMapping(value = "/productsCombo",method=RequestMethod.GET)
    public List<Product> productsCombo(HttpServletRequest request,@RequestParam String query)  {
    	String codeOrName = "";
    	if(!StringUtils.equalsIgnoreCase(WebConstants.ROOT_ID, query)){
    		codeOrName=query;
    	}
    	List<Product> matList = productService.productsCombo(codeOrName);
    	return matList;
    }
	
	/**
     * <p>查询条件->工艺信息下拉框：支持模糊查询</p> 
     * @author DingXintao
     * @date 2014-6-30 9:56:48
     * @return List<ProcessInformation>
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/craftsCombo", method = RequestMethod.GET)
    public List<ProductCraftsBz> craftsCombo(HttpServletRequest request,@RequestParam String query)  {
    	String codeOrName = "";
    	if(!StringUtils.equalsIgnoreCase(WebConstants.ROOT_ID, query)){
    		codeOrName=query;
    	}
    	List<ProductCraftsBz> matList = productCraftsBzService.craftsCombo(codeOrName);
    	return matList;
    }
    
    @RequestMapping(value="/export/{fileName}", method = RequestMethod.POST)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable String fileName,
                       @RequestParam String params,
                       @RequestParam(required = false) String queryParams) throws IOException, WriteException, InvocationTargetException, IllegalAccessException,ClassNotFoundException,NoSuchMethodException {
        JSONObject queryFilter  = JSONObject.parseObject(queryParams);
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
        productCraftsBzService.export(os, sheetName, columns,queryFilter);
        os.close();
    }
    

}