package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.model.WorkShift;
import cc.oit.bsmes.bas.service.EquipCalShiftService;
import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.bas.service.WorkShiftService;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.Range;
import cc.oit.bsmes.common.util.RangeList;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 设备日历班次管理
 * <p style="display:none">modifyRecord</p>
 * @author ChenXiang
 * @date 2014-4-17 下午16:00:04
 * @since
 * @version
 */
@Controller
@RequestMapping("/bas/eqipCalendarShift")
public class EqipCalendarShiftController {
	
	@Resource
	private EquipCalShiftService equipCalShiftService;
	@Resource
	private WorkShiftService workShiftService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private EquipCalendarService equipCalendarService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "eqipCalendarShift");
        model.addAttribute("workShifts",getWorkShiftJsonArray());
        return "bas.eqipCalendarShift";
    }
	
	//将班次信息连同ID组装成JSON数组对象
	private JSONArray getWorkShiftJsonArray(){
		List<WorkShift> result = workShiftService.getWorkShiftInfo();
        JSONArray array = new JSONArray();
        for(WorkShift workShift : result){
        	JSONObject obj = new JSONObject();
        	obj.put("id", workShift.getId());
        	obj.put("shiftName", workShift.getShiftName());
        	array.add(obj);
        }
        return array;
	}
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public TableView list(@RequestParam String sort,
				    		@RequestParam int page, 
				    		@RequestParam int start, 
				    		@RequestParam int limit,
				    		@RequestParam(required = false) String equipCode){
		
    	// 设置findParams属性
		Map<String,Object> requestMap = new HashMap<String, Object>();
		
		//不同组织对应不同数据
		User user = SessionUtils.getUser();
		requestMap.put("orgCode", user.getOrgCode());
		
		if(StringUtils.isNotBlank(equipCode)){
			requestMap.put("equipCode","%"+equipCode+"%");
		}

    	//查询
    	List<EquipCalShift> list = equipCalShiftService.findByRequestMap(requestMap, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(equipCalShiftService.countByRequestMap(requestMap));
    	return tableView;
    }
	
	/**
	 * 设备编码下拉款选项
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getEquipCode")		    
	public List<EquipInfo> getEquipCode(){
		User user = SessionUtils.getUser();
	    List<EquipInfo> result = equipInfoService.getByOrgCode(user.getOrgCode(),EquipType.PRODUCT_LINE);
	    return result;
	}
	/**
	 * 设备编码下拉框联动，选择设备编码，设备名称自动加载。
	 * @param eqipCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getEqipName/{eqipCode}")		    
	public String getEqipName(@PathVariable String eqipCode){
	    EquipInfo result = equipInfoService.getByCode(eqipCode,SessionUtils.getUser().getOrgCode());
	    return result.getName();
	}
	
	/**
	 * 保存一条设备班次信息
	 * @param equipCode
	 * @param workShiftId
	 * @param dateOfWork
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value="saveForm/{equipCode}/{workShiftId}/{dateOfWork}")		    
	public void saveForm(@PathVariable String equipCode,@PathVariable String workShiftId,@PathVariable String dateOfWork) throws UnsupportedEncodingException{
		dateOfWork = dateOfWork.replace("-","");
		User user = SessionUtils.getUser();
		
		EquipCalendar equipCalendar = new EquipCalendar();
		equipCalendar.setEquipCode(equipCode);
		equipCalendar.setDateOfWork(dateOfWork);
		equipCalendar.setOrgCode(user.getOrgCode());
		equipCalendarService.insert(equipCalendar);
		
		String[] workShiftIdArray = workShiftId.split(",");
		for(int i=0;i<workShiftIdArray.length;i++){
			EquipCalShift equipCalShift = new EquipCalShift();
			equipCalShift.setEquipCalendarId(equipCalendar.getId());
			equipCalShift.setWorkShiftId(workShiftIdArray[i]);
			equipCalShiftService.insert(equipCalShift);
		}
	}
	
	/**
	 * 先删除原数据库中班次记录，再根据用户选择重新插入数据
	 * @param equipCode 设备编码
	 * @param workShiftId 工作日班次ID
	 * @param dateOfWork 日期
	 * @param id 设备日历ID
	 * @return
	 * @throws ClassNotFoundException
	 */
	@RequestMapping(value="updateForm/{equipCode}/{workShiftId}/{dateOfWork}/{id}")
	@ResponseBody
	public void updateForm(@PathVariable String equipCode,@PathVariable String workShiftId,@PathVariable String dateOfWork,@PathVariable String id) throws ClassNotFoundException {
		dateOfWork = dateOfWork.replace("-","");
		EquipCalendar equipCalendar = new EquipCalendar();
		equipCalendar.setId(id);
		equipCalendar.setEquipCode(equipCode);
		equipCalendar.setDateOfWork(dateOfWork);
		equipCalendarService.update(equipCalendar);
		
		String[] workShiftIdArray = workShiftId.split(",");
		equipCalShiftService.deleteByEquipCalendarId(id);
		
		for(int i=0;i<workShiftIdArray.length;i++){
			EquipCalShift equipCalShift = new EquipCalShift();
			equipCalShift.setWorkShiftId(workShiftIdArray[i]);
			equipCalShift.setEquipCalendarId(equipCalendar.getId());
			equipCalShiftService.insert(equipCalShift);
		}
	}

	/**
	 * 添加数据，唯一性验证
	 * @param equipCode 设备编码
	 * @param dateOfWork 日期
	 * @return
	 * @throws ClassNotFoundException
	 */
	@RequestMapping(value="checkUnique/{equipCode}/{dateOfWork}",method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUnique(@PathVariable String equipCode,@PathVariable String dateOfWork) throws ClassNotFoundException {
		boolean result = false;
		dateOfWork = dateOfWork.replace("-","");
		EquipCalendar equipCalendar = new EquipCalendar();
		equipCalendar.setDateOfWork(dateOfWork);
		equipCalendar.setEquipCode(equipCode);
		equipCalendar.setOrgCode(equipCalendarService.getOrgCode());
		EquipCalendar equipCalendarValid = equipCalendarService.getByEquipCodeAndDateOfWork(equipCalendar);
		
		if(equipCalendarValid != null){
			result = true;
		}
		return result;
	}
	
	/**
	 * 删除选中行数据 
	 * @param eqipCalendarId
	 */
	@RequestMapping(value="deleteRow/{eqipCalendarId}",method = RequestMethod.GET)
	@ResponseBody
	public void deleteRow(@PathVariable String eqipCalendarId){
		equipCalShiftService.deleteByEquipCalendarId(eqipCalendarId);
		equipCalendarService.deleteById(eqipCalendarId);
	}
	
	/**
	 * 验证设备是否存在
	 * @param eqipCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="checkExist/{eqipCode}")		    
	public boolean checkExist(@PathVariable String eqipCode){
	    EquipInfo result = equipInfoService.getByCode(eqipCode,SessionUtils.getUser().getOrgCode());
	    return result==null;
	}
	/**
	 * 时间范围验证
	 * @param workShiftId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="workShiftValid/{workShiftId}")		    
	public boolean workShiftValid(@PathVariable String workShiftId){
		String[] workShiftIdArray = workShiftId.split(",");
		RangeList rangeList = new RangeList();
		for (String workShiftIdStr : workShiftIdArray) {
			WorkShift workShift = workShiftService.getById(workShiftIdStr);
			Long startTime = Long.valueOf(workShift.getShiftStartTime());
			Long endTime = Long.valueOf(workShift.getShiftEndTime());
			endTime = endTime < startTime ? endTime + 2400 : endTime;
			rangeList.add(new Range(startTime, endTime), false);
			rangeList.add(new Range(startTime + 2400, endTime + 2400), false);
		}
		
	    return rangeList.size() < workShiftIdArray.length * 2;
	}

}
