package cc.oit.bsmes.common.action;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import cc.oit.bsmes.common.mybatis.complexQuery.WithValueQueryParam;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.common.util.ReflectUtils;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jxl.write.WriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/{moduleName}/{submoduleName}")
public class BaseController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(produces="text/html")
    public String index(@PathVariable String moduleName, @PathVariable String submoduleName, Model model) {
	    model.addAttribute("moduleName", moduleName);
        model.addAttribute("submoduleName", submoduleName);
        return moduleName + "." + submoduleName; 
    }
	
    @RequestMapping
    @ResponseBody
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public TableView list(HttpServletRequest request, @PathVariable String submoduleName, @RequestParam(required = false) String sort,
    		@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer limit)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ParseException, UnsupportedEncodingException {
    	BaseService baseService = (BaseService) ContextUtils.getBean(submoduleName + "ServiceImpl");
    	// 构建findParams
    	String serviceClassName = baseService.toString().split("@")[0];
    	String modelClassName = serviceClassName.replace("service.impl.", "model.").replace("ServiceImpl", "");
    	Class<?> modelClass = Class.forName(modelClassName);

    	// 设置findParams属性
        List<CustomQueryParam> queryParams = new ArrayList<CustomQueryParam>();
		Map<String, String[]> parameterMap = request.getParameterMap();
		PropertyDescriptor[] propDescriptors = ReflectUtils.getPropertyDescriptors(modelClass);
    	for (PropertyDescriptor propDescriptor : propDescriptors) {
            String propName = propDescriptor.getName();
            String[] values = parameterMap.get(propName);
    		if (values == null || values.length == 0) { // 不存在的key
    			continue;
    		}

    		String value = values[0];
    		if (StringUtils.isEmpty(value.toString())) { // 空值
    			continue;
    		}
            queryParams.add(new WithValueQueryParam(propName, "LIKE", "%" + URLDecoder.decode(value, "utf-8") + "%"));
        }

        // 根据filter设置findParams属性
        addFilterQueryParams(request, queryParams);

        // 查询
    	List<?> list = baseService.query(queryParams, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(list);
        tableView.setTotal(baseService.countQuery(queryParams));
    	return tableView;
    }

    public static void addFilterQueryParams(HttpServletRequest request, List<CustomQueryParam> queryParams) throws ParseException {
        for (int i = 0; ; i++) {
            String prefix = "filter[" + i + "]";
            String field = request.getParameter(prefix + "[field]");
            if (field == null) {
                break;
            }
            Object value = request.getParameter(prefix + "[data][value]");
            String type = request.getParameter(prefix + "[data][type]");
            String comparison = request.getParameter(prefix + "[data][comparison]");
            if ("string".equals(type)) {
                queryParams.add(new WithValueQueryParam(field, "LIKE", "%" + value + "%"));
            } else if (comparison != null) {
                if ("date".equals(type)) {
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    value = df.parse(value.toString());
                }
                queryParams.add(new WithValueQueryParam(field, ComparisonTranslator.translate(comparison), value));
            } else {
                queryParams.add(new WithValueQueryParam(field, "=", value));
            }
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@PathVariable String moduleName, @PathVariable String submoduleName, @RequestBody String jsonText) throws ClassNotFoundException {
		BaseService<Base> baseService = getService(submoduleName);
		UpdateResult updateResult = new UpdateResult();

        if (jsonText.startsWith("[")) {
            List<Base> objects = parseModels(jsonText, moduleName, submoduleName);
            baseService.insert(objects);
            updateResult.addResult(objects);
        } else {
            Base object = parseModel(jsonText, moduleName, submoduleName);
            baseService.insert(object);
            updateResult.addResult(object);
        }

		return updateResult;
	}


    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public UpdateResult update(@PathVariable String moduleName,@PathVariable String submoduleName, @RequestBody String jsonText) throws ClassNotFoundException {
		BaseService<Base> baseService = getService(submoduleName);
		UpdateResult updateResult = new UpdateResult();

        if (jsonText.startsWith("[")) {
            List<Base> objects = parseModels(jsonText, moduleName, submoduleName);
            baseService.update(objects);
            updateResult.addResult(objects);
        } else {
            Base object = parseModel(jsonText,moduleName, submoduleName);
            baseService.update(object);
            updateResult.addResult(object);
        }

		return updateResult;
	}

	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public UpdateResult delete(@PathVariable String submoduleName, @PathVariable String id, @RequestBody String jsonText) {
		BaseService<Base> baseService = getService(submoduleName);
		UpdateResult updateResult = new UpdateResult();

		if (jsonText.startsWith("[")) {
			// 删除多条记录
			JSONArray jsonArray = JSON.parseArray(jsonText);
			List<String> list = new ArrayList<String>();
			for (Object object : jsonArray) {
				JSONObject jsonObject = (JSONObject) object;
				list.add((String) jsonObject.get("id"));
			}
			baseService.deleteById(list);
		} else {
			baseService.deleteById(id);
		}

		return updateResult;
	}

	@RequestMapping(value="/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject export(HttpServletRequest request,
                       HttpServletResponse response,
                       @PathVariable String submoduleName,
                       @PathVariable String fileName,
                       @RequestParam String params,
                       @RequestParam(required = false) String queryParams) throws IOException, WriteException, InvocationTargetException, IllegalAccessException,ClassNotFoundException,NoSuchMethodException {
		BaseService<Base> baseService = getService(submoduleName);
        JSONObject queryFilter  = JSONObject.parseObject(queryParams);
        int countExportRows = baseService.countForExport(queryFilter);
        Integer maxExportLine = Integer.parseInt(WebContextUtils.getPropValue(WebConstants.MAX_EXPORT_LINE));
        if(countExportRows > maxExportLine){
            JSONObject result = new JSONObject();
            result.put("msg","export rows is than maxExportLine");
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
        baseService.export(os, sheetName, columns,queryFilter);
        os.close();
        return null;
	}

	@SuppressWarnings("unchecked")
	private BaseService<Base> getService(String submoduleName) {
		return (BaseService<Base>) ContextUtils.getBean(submoduleName + "ServiceImpl");
	}

	private Base parseModel(String jsonText, String moduleName, String submoduleName) throws ClassNotFoundException {
		return (Base) JSON.parseObject(jsonText, reflectModel(moduleName, submoduleName));
	}

    private List<Base> parseModels(String jsonText, String moduleName, String submoduleName) throws ClassNotFoundException {
        return (List<Base>) JSON.parseArray(jsonText, reflectModel(moduleName, submoduleName));
    }

    private Class reflectModel(String moduleName, String submoduleName) throws ClassNotFoundException{
        String modelClassName = "cc.oit.bsmes." + moduleName + ".model." + StringUtils.capitalize(submoduleName);
        return Class.forName(modelClassName);
    }
}
