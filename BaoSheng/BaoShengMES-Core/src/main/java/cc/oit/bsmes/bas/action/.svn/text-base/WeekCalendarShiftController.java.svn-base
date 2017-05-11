/*
 * Copyright by Orientech and the original author or authors
 * 
 * This document only allow internal use.Any of your behaviors using the
 * file not internal will pay the legal responsibility.
 * 
 * You can learn more information about Orientech from 
 * 
 *       http://www.orientech.cc/
 */
package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.model.WeekCalendar;
import cc.oit.bsmes.bas.model.WeekCalendarShift;
import cc.oit.bsmes.bas.model.WorkShift;
import cc.oit.bsmes.bas.service.WeekCalendarService;
import cc.oit.bsmes.bas.service.WeekCalendarShiftService;
import cc.oit.bsmes.bas.service.WorkShiftService;
import cc.oit.bsmes.common.constants.StatusDescription;
import cc.oit.bsmes.common.constants.WeekCalendarType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * Week Calendar Shift
 * @author ChenXiang
 * @date 2014-4-10 上午午10:50:15
 * @since
 * @version
 */
@Controller
@RequestMapping("/bas/weekCalendarShift")
public class WeekCalendarShiftController {
	
	@Resource
	private WeekCalendarShiftService weekCalendarShiftService;
	
	@Resource
	private WeekCalendarService weekCalendarService;
	
	@Resource
	private WorkShiftService workShiftService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "weekCalendarShift");
        return "bas.weekCalendarShift";
    }
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public TableView list(HttpServletRequest request,
    		@RequestParam String sort,
    		@RequestParam int page, 
    		@RequestParam int start, 
    		@RequestParam int limit) throws UnsupportedEncodingException{
		
    	// 设置findParams属性
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String,Object> requestMap = new HashMap<String, Object>();
		
		//不同组织对应不同数据
		User user = SessionUtils.getUser();
		requestMap.put("orgCode", user.getOrgCode());
		
		for(String key:parameterMap.keySet()){
			if(parameterMap.get(key)!=null&&StringUtils.isNotBlank(parameterMap.get(key)[0])){
				
				if(StringUtils.equalsIgnoreCase(key, "keyV")){
					requestMap.put(key, parameterMap.get(key)[0]);
				}else if(StringUtils.equalsIgnoreCase(key, "valueV")){
					requestMap.put(key, parameterMap.get(key)[0]);
				}else{
					String parameter = URLDecoder.decode(parameterMap.get(key)[0],"UTF-8");
					requestMap.put(key, "%"+parameter+"%");
				}
			}
		}
    	
    	//查询
    	List<WeekCalendarShift> list = weekCalendarShiftService.findByRequestMap(requestMap, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(weekCalendarShiftService.countByRequestMap(requestMap));
    	return tableView;
    }
	
	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		User user = SessionUtils.getUser();
		UpdateResult updateResult = new UpdateResult();
		WeekCalendarShift weekCalendarShift =  JSON.parseObject(jsonText, WeekCalendarShift.class);

        WeekCalendar weekCalendar = getWeekCalendar(user, weekCalendarShift);
		
		//判断新增的班次在 T_BAS_WORK_SHIFT中是否存在,若不存在则新增该班次
		WorkShift workShift = workShiftService.getWorkShiftByShiftName(weekCalendarShift.getWorkShiftId());
		if(workShift==null){
			workShift = new WorkShift();
			workShift.setShiftName(weekCalendarShift.getWorkShiftId());
			String[] arrayStart = weekCalendarShift.getShiftStartTime().split(":");
			String[] arrayEnd = weekCalendarShift.getShiftEndTime().split(":");
			workShift.setShiftStartTime(arrayStart[0]+arrayStart[1]);
			workShift.setShiftEndTime(arrayEnd[0]+arrayEnd[1]);
			workShiftService.insert(workShift);
		}
		
		weekCalendarShift.setId(UUID.randomUUID().toString());
		weekCalendarShift.setCreateUserCode(user.getUserCode());
		weekCalendarShift.setModifyUserCode(user.getUserCode());
		weekCalendarShift.setStatus(StatusDescription.VALID.getOrder());
		weekCalendarShift.setWeekCalendarId(weekCalendar.getId());
		weekCalendarShift.setWorkShiftId(workShift.getId());
		weekCalendarShift.setOrgCode(user.getOrgCode());
		weekCalendarShiftService.insert(weekCalendarShift);
		
		weekCalendarShift = weekCalendarShiftService.getById(weekCalendarShift.getId());
		updateResult.addResult(weekCalendarShift);
		return updateResult;
	}

	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		User user = SessionUtils.getUser();
		UpdateResult updateResult = new UpdateResult();
		WeekCalendarShift weekCalendarShift =  JSON.parseObject(jsonText, WeekCalendarShift.class);

        WeekCalendar weekCalendar = getWeekCalendar(user, weekCalendarShift);
		
		//判断新增的班次在 T_BAS_WORK_SHIFT中是否存在,若不存在则新增该班次
		WorkShift workShift = workShiftService.getWorkShiftByShiftName(weekCalendarShift.getWorkShiftId());
		if(workShift==null){
			workShift = new WorkShift();
			workShift.setShiftName(weekCalendarShift.getWorkShiftId());
			String[] arrayStart = weekCalendarShift.getShiftStartTime().split(":");
			String[] arrayEnd = weekCalendarShift.getShiftEndTime().split(":");
			workShift.setShiftStartTime(arrayStart[0]+arrayStart[1]);
			workShift.setShiftEndTime(arrayEnd[0]+arrayEnd[1]);
			workShiftService.insert(workShift);
		}
		
		weekCalendarShift.setModifyUserCode(user.getUserCode());
		weekCalendarShift.setWeekCalendarId(weekCalendar.getId());
		weekCalendarShift.setWorkShiftId(workShift.getId());
		weekCalendarShiftService.update(weekCalendarShift);
		
		String[] arrayStart = weekCalendarShift.getShiftStartTime().split(":");
		String[] arrayEnd = weekCalendarShift.getShiftEndTime().split(":");
		workShift.setShiftStartTime(arrayStart[0]+arrayStart[1]);
		workShift.setShiftEndTime(arrayEnd[0]+arrayEnd[1]);
		workShiftService.update(workShift);
		
		weekCalendarShift = weekCalendarShiftService.getById(weekCalendarShift.getId());
		updateResult.addResult(weekCalendarShift);
		return updateResult;
	}

    private WeekCalendar getWeekCalendar(User user, WeekCalendarShift weekCalendarShift) {
        WeekCalendar weekCalendar = null;
        if(weekCalendarShift.getWeekCalendarId().equals(WeekCalendarType.MON.toString())){
            //为获得T_BAS_WEEK_CALENDAR对应的ID
            weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.MON.getOrder(), user.getOrgCode());
        }else if(weekCalendarShift.getWeekCalendarId().equals(WeekCalendarType.TUE.toString())){
            weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.TUE.getOrder(), user.getOrgCode());
        }else if(weekCalendarShift.getWeekCalendarId().equals(WeekCalendarType.WED.toString())){
            weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.WED.getOrder(), user.getOrgCode());
        }else if(weekCalendarShift.getWeekCalendarId().equals(WeekCalendarType.THI.toString())){
            weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.THI.getOrder(), user.getOrgCode());
        }else if(weekCalendarShift.getWeekCalendarId().equals(WeekCalendarType.FRI.toString())){
            weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.FRI.getOrder(), user.getOrgCode());
        }else if(weekCalendarShift.getWeekCalendarId().equals(WeekCalendarType.SAT.toString())){
            weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.SAT.getOrder(), user.getOrgCode());
        }else if(weekCalendarShift.getWeekCalendarId().equals(WeekCalendarType.SUN.toString())){
            weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.SUN.getOrder(), user.getOrgCode());
        }
        return weekCalendar;
    }

    @RequestMapping(value="checkUnique/{workShiftId}/{weekCalendarId}",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject checkUnique(@PathVariable String workShiftId,@PathVariable String weekCalendarId) throws UnsupportedEncodingException{
		JSONObject object = new JSONObject();
		User user = SessionUtils.getUser();
		String param = URLDecoder.decode(workShiftId,"UTF-8");
		WorkShift workShift = workShiftService.getWorkShiftByShiftName(param);
		WeekCalendar weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(weekCalendarId, user.getOrgCode());
		
		WeekCalendarShift weekCalendarShift = null;
		if(workShift!=null){
			weekCalendarShift = weekCalendarShiftService.getByWeekCalendarIdAndWorkShiftId(weekCalendar.getId(),workShift.getId(),user.getOrgCode());
		}
		object.put("exists", weekCalendarShift == null?false:true);
		return object;
	}
	
	@RequestMapping(value="timeValid/{shiftStartTime}/{shiftEndTime}/{parameter}/{weekCalendarId}",method = RequestMethod.GET)
	@ResponseBody
	public Boolean timeValid(@PathVariable String shiftStartTime,@PathVariable String  shiftEndTime,@PathVariable String parameter,@PathVariable String weekCalendarId) throws UnsupportedEncodingException{
		User user = SessionUtils.getUser();
		weekCalendarId = URLDecoder.decode(weekCalendarId,"UTF-8");
		WeekCalendar weekCalendar = null;
		if(weekCalendarId.equals(WeekCalendarType.MON.toString())){
			weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.MON.getOrder(), user.getOrgCode());
		}else if(weekCalendarId.equals(WeekCalendarType.TUE.toString())){
			weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.TUE.getOrder(), user.getOrgCode());
		}else if(weekCalendarId.equals(WeekCalendarType.WED.toString())){
			weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.WED.getOrder(), user.getOrgCode());
		}else if(weekCalendarId.equals(WeekCalendarType.THI.toString())){
			weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.THI.getOrder(), user.getOrgCode());
		}else if(weekCalendarId.equals(WeekCalendarType.FRI.toString())){
			weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.FRI.getOrder(), user.getOrgCode());
		}else if(weekCalendarId.equals(WeekCalendarType.SAT.toString())){
			weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.SAT.getOrder(), user.getOrgCode());
		}else if(weekCalendarId.equals(WeekCalendarType.SUN.toString())){
			weekCalendar = weekCalendarService.getWeekCalendarByWeekNo(WeekCalendarType.SUN.getOrder(), user.getOrgCode());
		}
		
		boolean bool = false;
		String shiftName = URLDecoder.decode(parameter,"UTF-8");
		List<WeekCalendarShift> list = weekCalendarShiftService.getByWeekCalendarId(weekCalendar.getId());
		for(WeekCalendarShift weekCalendarShift:list){
			if(shiftName.contains("白") && weekCalendarShift.getShiftName().contains("白")){
				bool = true;
			}else{
				if(StringUtils.equals(shiftStartTime, weekCalendarShift.getShiftStartTime())&& 
						StringUtils.equals(shiftEndTime, weekCalendarShift.getShiftEndTime())){
					bool = true;
				}
			}
//			weekCalendarShift.getShiftStartTime()-shiftEndTime
//			String start=weekCalendarShift.getShiftStartTime();
//			String end=weekCalendarShift.getShiftEndTime();
		}
		
//		if(shiftName.contains("白")){
//			List<WeekCalendarShift> list = weekCalendarShiftService.getByWeekCalendarId(weekCalendar.getId());
//			for(WeekCalendarShift weekCalendarShiftForTime : list){
//				if(weekCalendarShiftForTime.getShiftName().contains("夜")){
//					bool = weekCalendarShiftService.validTimeTop(weekCalendarShiftForTime.getShiftEndTime(),shiftStartTime);
//				}
//			}
//		}else if(shiftName.contains("中")){
//			List<WeekCalendarShift> list = weekCalendarShiftService.getByWeekCalendarId(weekCalendar.getId());
//			for(WeekCalendarShift weekCalendarShiftForTime : list){
//				if(weekCalendarShiftForTime.getShiftName().contains("白")){
//					bool = weekCalendarShiftService.validTimeTop(weekCalendarShiftForTime.getShiftEndTime(),shiftStartTime);
//				}
//			}
//		}else if(shiftName.contains("夜")){
////			List<WeekCalendarShift> list = null;
////			if(weekCalendar.getId().equals("1")){
////				list = weekCalendarShiftService.getByWeekCalendarId("7");
////			}else if(weekCalendar.getId().equals("8")){
////				list = weekCalendarShiftService.getByWeekCalendarId("14");
////			}else {
////				list = weekCalendarShiftService.getByWeekCalendarId(String.valueOf(Integer.valueOf(weekCalendar.getId())-1));
////			}
//			List<WeekCalendarShift> list = weekCalendarShiftService.getByWeekCalendarId(weekCalendar.getId());
//			for(WeekCalendarShift weekCalendarShiftForTime : list){
//				if(weekCalendarShiftForTime.getShiftName().contains("中")){
//					bool = weekCalendarShiftService.validTime(weekCalendarShiftForTime.getShiftEndTime(),shiftStartTime);
//				}
//			}
//		}
		return bool;
	}
	
	@ResponseBody
	@RequestMapping(value="getWorkShiftName")		    
	public List<WorkShift> getWorkShiftName(){
	    List<WorkShift> result = workShiftService.getWorkShiftName();
	    return result;
	}
	
	@ResponseBody
	@RequestMapping(value="getStartTimeAndEndTime/{shiftName}")		    
	public WorkShift getStartTimeAndEndTime(@PathVariable String shiftName) throws UnsupportedEncodingException{
		shiftName = URLDecoder.decode(shiftName,"UTF-8");
	    WorkShift result = workShiftService.getWorkShiftByShiftName(shiftName);
	    return result;
	}
}
