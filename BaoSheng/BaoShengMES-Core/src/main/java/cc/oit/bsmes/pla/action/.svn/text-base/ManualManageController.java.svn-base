package cc.oit.bsmes.pla.action;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.pla.model.ManualManage;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.pla.service.ManualManageService;
import cc.oit.bsmes.pla.service.OrderOAService;



@Controller
@RequestMapping("/pla/manualManage")
public class ManualManageController {
	
	@Resource
	private ManualManageService manualManageService;
	
	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "pla");
		model.addAttribute("submoduleName", "manualManage");
		return "pla.manualManage";
		
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
				findParams.put(paramKey, param[0]);
			}
		}
		
		findParams.put("start", start); // 顺序不能变，必须要覆盖
		findParams.put("end", end);
		findParams.put("sort", sortArray);
	}
	
	@ResponseBody
	@RequestMapping
	public TableView list(HttpServletRequest request,
			@RequestParam int page, @RequestParam int start,
			@RequestParam int limit,@RequestParam(required = false) String sort) {
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryParam2Map(request, findParams, null, null, start,
				(start + limit), sortArray);
		List<ManualManage> list = manualManageService.getInfo(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(manualManageService.countContractNo(findParams));
		return tableView;
	}
	
	

	@ResponseBody
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	public void updateData(HttpServletRequest request,
			@RequestParam String id,
			@RequestParam String ptFinishTime, @RequestParam String clFinishTime,
			@RequestParam String bzFinishTime, @RequestParam String htFinishTime,
			@RequestParam String coordinateTime,@RequestParam String remarks,
			@RequestParam String infoSources){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("ptFinishTime", ptFinishTime);
		param.put("clFinishTime", clFinishTime);
		param.put("bzFinishTime", bzFinishTime);
		param.put("htFinishTime", htFinishTime);
		param.put("coordinateTime", coordinateTime);
		param.put("remarks", remarks);
		param.put("infoSources", infoSources);
		manualManageService.updateData(param);
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
	private void putQueryQueryParam2Map(HttpServletRequest request, Map<String, Object> findParams, String query,
			String queryType) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Iterator<String> paramKeys = parameterMap.keySet().iterator();
		while (paramKeys.hasNext()) {
			String paramKey = paramKeys.next();
			String[] param = parameterMap.get(paramKey);
			if (null != param && param.length > 0 && StringUtils.isNotBlank(param[0])) {
				if (param.length > 1) {
					findParams.put(paramKey, new ArrayList<String>(Arrays.asList(param)));
				} else {
					findParams.put(paramKey, param[0]);
				}
			}
		}
		if (!WebConstants.ROOT_ID.equals(query)) {
			findParams.put(queryType, "%" + query + "%");
		} else {
			findParams.put(queryType, null); // 不可省，清空功能
		}
	}
	
	/**
	 * 获取查询下拉框：合同号
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getcontractNo/{query}", method = RequestMethod.GET)
	public List<ManualManage> findContractNo(HttpServletRequest request, @PathVariable String query) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, query, "contractNo");
		List<ManualManage> list = manualManageService.getContractNo(findParams);
		return list;
	}
	
	/**
	 * 获取查询下拉框：合同号
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getinfosources/{query}", method = RequestMethod.GET)
	public List<ManualManage> findInfoSources(HttpServletRequest request, @PathVariable String query) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		List<ManualManage> list = manualManageService.getInfoSources(findParams);
		return list;
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
        manualManageService.export(os, sheetName, columns,queryFilter);
        os.close();
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/updateInformation", method = RequestMethod.POST)
	public JSON updateInformation(HttpServletRequest request,
			@RequestParam String id,
			@RequestParam String ptFinishTime, @RequestParam String clFinishTime,
			@RequestParam String bzFinishTime, @RequestParam String htFinishTime,
			@RequestParam String remarks,@RequestParam String coordinateTime,@RequestParam String infoSources) throws ParseException{
		String[] ids = id.split(",");
		for(String id1 : ids){
			if("".equals(id1) || null == id1){
				continue;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id1);
			param.put("ptFinishTime", ptFinishTime);
			param.put("clFinishTime", clFinishTime);
			param.put("bzFinishTime", bzFinishTime);
			param.put("htFinishTime", htFinishTime);
			param.put("remarks", remarks);
			param.put("coordinateTime", coordinateTime);
			param.put("infoSources", infoSources);
			manualManageService.updateData1(param);
		}
		return JSONArrayUtils.ajaxJsonResponse(true, "更新成功");
	}
}
