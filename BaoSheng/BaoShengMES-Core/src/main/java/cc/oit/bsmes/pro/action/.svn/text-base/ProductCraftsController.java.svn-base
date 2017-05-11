/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.pro.action;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.ReflectUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jxl.write.WriteException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 产品工艺
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-3-11 下午5:03:51
 * @since
 * @version
 */
@Controller
@RequestMapping("/pro/craftsSL")
public class ProductCraftsController {
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private ProcessInformationService processInformationService;
	
	
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "pro");
        model.addAttribute("submoduleName", "craftsSL");
        return "pro.craftsSL"; 
    }
	
	@RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public TableView list(HttpServletRequest request, ProductCrafts findParams, 
    		@RequestParam String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) 
    				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException  {
		List<ProductCrafts> result = productCraftsService.find(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(result);
    	tableView.setTotal(productCraftsService.count(findParams));
    	return tableView;
    }
	
	/**
     * <p>查询条件->工艺信息下拉框：支持模糊查询</p> 
     * @author DingXintao
     * @date 2014-6-30 9:56:48
     * @return List<ProcessInformation>
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/craftsComboSL/{query}")
    public List<ProductCrafts> craftsCombo(HttpServletRequest request, @PathVariable String query)  {
    	String codeOrName = "";
    	if(!StringUtils.equalsIgnoreCase(WebConstants.ROOT_ID, query)){
    		codeOrName=query;
    	}
    	List<ProductCrafts> matList = productCraftsService.craftsCombo(codeOrName);
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
        productCraftsService.export(os, sheetName, columns,queryFilter);
        os.close();
    }
    
}
