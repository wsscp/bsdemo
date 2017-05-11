package cc.oit.bsmes.wip.action;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SortListInterceptor;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.UserWorkHours;
import cc.oit.bsmes.wip.service.UserWorkHoursService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 工时报表控制类
 * <p>
 * UserWorkHoursController
 * </p>
 * 
 * @author DingXintao
 * @date 2015-08-41 11:20:48
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/userWorkHours")
public class UserWorkHoursController {

	@Resource
	private UserWorkHoursService userWorkHoursService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "userWorkHours");
		return "wip.userWorkHours";
	}

	/**
	 * <p>
	 * 编织屏蔽生产记录 显示列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-8-1 10:22:48
	 * @return
	 * @see
	 */
	@ResponseBody
	@RequestMapping
	public TableView indexList(HttpServletRequest request, @RequestParam String yearMonth, @RequestParam(required = false) String userName,
			@RequestParam(required = false) String typeName, @RequestParam String sort, @RequestParam int page, @RequestParam int start,
			@RequestParam int limit) {
		List<Map> list = userWorkHoursService.getUsersHoursByMonth(yearMonth, userName, typeName);
		TableView tableView = new TableView();
		tableView.setRows(list);
		return tableView;
	}

	/**
	 * @Title: workHoursDetail
	 * @Description: TODO(获取员工报工明细)
	 * @param: userCode 员工号
	 * @param: reportDate 报工时间
	 * @return: List<UserWorkHours>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/workHoursDetail")
	public List<UserWorkHours> workHoursDetail(@RequestParam String sort, @RequestParam String userCode, @RequestParam String reportDate) {
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
//		SortListInterceptor.setSortList(sortArray);
		UserWorkHours userWorkHours = new UserWorkHours();
		userWorkHours.setUserCode(userCode);
		userWorkHours.setReportDate(reportDate);
		List<UserWorkHours> list = userWorkHoursService.findByObj(userWorkHours);
		return list;
	}

	@ResponseBody
	@RequestMapping("/userName")
	public List<Map<String, String>> getUserName() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = userWorkHoursService.getUserName();
		return list;
	}

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject export(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String queryParams)
			throws IOException, WriteException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
		JSONObject queryFilter = JSONObject.parseObject(queryParams);
		String yearMonth = queryFilter.getString("yearMonth");
		String fileName = yearMonth + "月份工时统计表";
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
		OutputStream outputStream = response.getOutputStream();
		userWorkHoursService.usersHoursAMonthOutToExcel(outputStream, yearMonth);
		outputStream.close();
		return null;
	}

}
