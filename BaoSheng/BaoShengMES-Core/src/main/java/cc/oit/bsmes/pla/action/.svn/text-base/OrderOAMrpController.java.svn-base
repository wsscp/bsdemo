package cc.oit.bsmes.pla.action;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.pla.model.OaMrp;
import cc.oit.bsmes.pla.service.OaMrpService;

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

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * 订单OA物料需求结果查看
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-3-25 下午3:51:32
 * @since
 * @version
 */
@Controller
@RequestMapping("/pla/orderOAMrp")
public class OrderOAMrpController {

	@Resource
	private OaMrpService oaMrpService;

	@RequestMapping(produces = "text/html")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("moduleName", "pla");
		model.addAttribute("submoduleName", "orderOAMrp");
		return "pla.orderOAMrp";
	}

	@RequestMapping
	@ResponseBody
	public TableView list(HttpServletRequest request, @RequestParam int start, @RequestParam int limit,
			@RequestParam(required = false) String sort) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryParam2Map(request, findParams, null, null);
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		this.changeSortProperty2Column(sortArray);
		findParams.put("start", start); // 顺序不能变，必须要覆盖
		findParams.put("end", (start + limit));
		findParams.put("sort", sortArray);

		List<OaMrp> list = oaMrpService.list(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(oaMrpService.count(findParams));
		return tableView;
	}

	/**
	 * <p>
	 * 查询条件->物料信息下拉框：支持模糊查询
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<Mat>
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "/matsCombo/{query}")
	public List<OaMrp> matsCombo(HttpServletRequest request, @PathVariable String query) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryParam2Map(request, findParams, query, "matCode");
		List<OaMrp> matList = oaMrpService.matsCombo(findParams);
		return matList;
	}

	/**
	 * <p>
	 * 查询条件->生产线信息下拉框：支持模糊查询
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<Mat>
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "/equipCombo/{query}")
	public List<OaMrp> equipCombo(HttpServletRequest request, @PathVariable String query) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryParam2Map(request, findParams, query, "equipCode");
		List<OaMrp> matList = oaMrpService.equipCombo(findParams);
		return matList;
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
	private void putQueryParam2Map(HttpServletRequest request, Map<String, Object> findParams, String query,
			String queryType) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Iterator<String> paramKeys = parameterMap.keySet().iterator();
		while (paramKeys.hasNext()) {
			String paramKey = paramKeys.next();
			String[] param = parameterMap.get(paramKey);
			if (null != param && param.length > 0 && StringUtils.isNotBlank(param[0])) {
				if ("planDate".equals(paramKey)) {
					findParams.put(paramKey, param[0]);
				} else {
					findParams.put(paramKey, new ArrayList<String>(Arrays.asList(param)));
				}
			}
		}
		if (StringUtils.isNotEmpty(query)) {
			if (!WebConstants.ROOT_ID.equals(query)) {
				findParams.put(queryType, "%" + query + "%");
			} else {
				findParams.put(queryType, null); // 不可省，清空功能
			}
		}
		findParams.put("orgCode", oaMrpService.getOrgCode());
	}

	/**
	 * <p>
	 * 导出功能
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-7-18 16:56:48
	 * @return
	 * @see
	 */
	@RequestMapping(value = "/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName,
			@RequestParam String params, @RequestParam(required = false) String queryParams) throws IOException,
			WriteException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		JSONObject queryFilter = JSONObject.parseObject(queryParams);
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
		oaMrpService.export(os, sheetName, columns, queryFilter);
		os.close();
	}

	/**
	 * 将排序的字段自动转换为数据库字段 适用于自定义分页
	 * */
	private void changeSortProperty2Column(List<Sort> sortArray) {
		if (null != sortArray) {
			for (Sort sort : sortArray) {
				sort.setProperty(sort.getProperty().replaceAll("([A-Z])", "_$0").toUpperCase());
			}
		}
	}

}
