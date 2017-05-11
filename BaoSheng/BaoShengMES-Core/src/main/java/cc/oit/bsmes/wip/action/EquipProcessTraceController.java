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
package cc.oit.bsmes.wip.action;

import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.CamelCaseUtils;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.service.WorkOrderService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.ValueFilter;
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
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-2-24 下午5:48:15
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/equipProcessTrace")
public class EquipProcessTraceController {
	
	@Resource
	private WorkOrderService workOrderService;

	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "equipProcessTrace");
        return "wip.equipProcessTrace"; 
    }
	
	
	@RequestMapping
    @ResponseBody
    @SuppressWarnings({ "rawtypes" })
    public TableView list(@RequestParam String equipCode,@RequestParam String contractNo,@RequestParam String batchNum, @RequestParam String realStartTime,@RequestParam String realEndTime,
    		@RequestParam String processCode,@RequestParam String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit)  {

    	// 设置findParams属性
		Map<String,Object> findParams = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(equipCode)){
			findParams.put("equipCode", "%"+ equipCode+"%");
		}
		if(StringUtils.isNotBlank(contractNo)){
			findParams.put("contractNo", contractNo);
		}
		if(StringUtils.isNotBlank(batchNum)){
			findParams.put("batchNum", "%"+ batchNum+"%");
		}
		if(StringUtils.isNotBlank(processCode)){
			findParams.put("processCode",processCode);
		}
		if(StringUtils.isNotBlank(realStartTime)){
			findParams.put("realStartTime", DateUtils.convert(realStartTime));
		}
		if(StringUtils.isNotBlank(realEndTime)){
			// TODO 查询条件结束时间做处理，结束时间加一天
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.convert(realEndTime));
			calendar.add(Calendar.DATE, 1);
			findParams.put("realEndTime", calendar.getTime());
		}

		// TODO 添加查询条件
		// 设备加工追溯报表：还没有加工的数据不用显示，只显示正在加工或者加工完成的数据
		WorkOrderStatus[] statuss = { WorkOrderStatus.IN_PROGRESS,
				WorkOrderStatus.FINISHED };
		findParams.put("statuss", statuss);

    	// 查询
    	List list = workOrderService.findForEquipProcessTrace(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
    	
    	NameFilter filter = new NameFilter(){
			@Override
			public String process(Object object, String name, Object value) {
				return CamelCaseUtils.toCamelCase(name);
			}
    	} ;  
    	ValueFilter dateFilter = new ValueFilter(){
			@Override
			public Object process(Object object, String name, Object value) {
				if(value instanceof oracle.sql.TIMESTAMP){
					
					try {
						return DateUtils.convert(((oracle.sql.TIMESTAMP)value).dateValue(),DateUtils.DATE_TIME_FORMAT);
					} catch (SQLException e) {
					
					}
				}
				return value;
			}
    		
    	} ;
    	
    	SerializeWriter out = new SerializeWriter(); 
    	JSONSerializer serializer = new JSONSerializer(out);  
    	serializer.getNameFilters().add(filter);  
    	serializer.getValueFilters().add(dateFilter);  
    	serializer.write(list);  
    	String text = out.toString();
    	
    	List result = JSONObject.parseArray(text) ;
    	TableView tableView = new TableView();
    	tableView.setRows(result);
    	tableView.setTotal(workOrderService.countForEquipProcessTrace(findParams));
    	return tableView;
    }
	
	@RequestMapping(value="/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable String fileName,
                       @RequestParam String params,
                       @RequestParam(required = false) String queryParams) throws IOException, WriteException, InvocationTargetException, IllegalAccessException {
        JSONArray columns = JSONArray.parseArray(params);
        JSONObject param=JSONObject.parseObject(queryParams);
    	Map<String,Object> findParams = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(param.getString("equipCode"))){
			findParams.put("equipCode", "%"+param.getString("equipCode") +"%");
		}
		if(StringUtils.isNotBlank(param.getString("contractNo"))){
			findParams.put("contractNo", "%"+ param.getString("contractNo")+"%");
		}
		if(StringUtils.isNotBlank(param.getString("realStartTime"))){
			findParams.put("realStartTime",DateUtils.convert(param.getString("realStartTime")));
		}
		if(StringUtils.isNotBlank(param.getString("realEndTime"))){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.convert(param.getString("realEndTime")));
			calendar.add(Calendar.DATE, 1);
			findParams.put("realEndTime", calendar.getTime());
		}
		WorkOrderStatus[] statuss = { WorkOrderStatus.IN_PROGRESS,
				WorkOrderStatus.FINISHED };
		findParams.put("statuss", statuss);
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
        workOrderService.export(os, sheetName, columns,findParams);
        os.close();
	}
}
