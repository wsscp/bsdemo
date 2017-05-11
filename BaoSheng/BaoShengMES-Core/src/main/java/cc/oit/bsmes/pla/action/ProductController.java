package cc.oit.bsmes.pla.action;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;

import jxl.write.WriteException;

/**
 * 
 * 产品信息
 * @author ChenXiang
 * @data 2014年5月21日 上午10:02:28
 * @since
 * @version
 */

@Controller
@RequestMapping("/pla/product")
public class ProductController {
	
	@Resource
	private ProductService productService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "pla");
        model.addAttribute("submoduleName", "product");
        return "pla.product";
    }

	/**
     * <p>查询条件->产品信息下拉框</p> 
     * @author DingXintao
     * @date 2014-6-30 9:56:48
     * @return List<Product>
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/productsCombo/{query}")
    public List<Product> productsCombo(HttpServletRequest request, @PathVariable String query)  {
    	String codeOrName = "";
    	if(!StringUtils.equalsIgnoreCase(WebConstants.ROOT_ID, query)){
    		codeOrName=query;
    	}
    	List<Product> matList = productService.productsCombo(codeOrName);
    	return matList;
    }
    
    @ResponseBody
    @RequestMapping(value = "/productsTypeCombo/{query}")
    public List<Product> productsTypeCombo(HttpServletRequest request, @PathVariable String query)  {
    	String type = "";
    	if(!StringUtils.equalsIgnoreCase(WebConstants.ROOT_ID, query)){
    		type=query;
    	}
    	List<Product> matList = productService.productsCombo(type);
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
        productService.export(os, sheetName, columns,queryFilter);
        os.close();
    }
}
