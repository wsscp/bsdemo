package cc.oit.bsmes.fac.action;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.StatusHistory;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.StatusHistoryService;

/**
 * 停机原因分析
 *
 */

@Controller
@RequestMapping("/fac/shunDownAnalysis")
public class ShunDownAnalysisController {
	@Resource
    private StatusHistoryService  statusHistoryService;
	@Resource 
	private DataDicService dataDicService;
	@Resource
	private EquipInfoService equipInfoService;
    @RequestMapping(produces = "text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "fac");
        model.addAttribute("submoduleName", "shunDownAnalysis");
        return "fac.shunDownAnalysis";
    }
    
    @RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(@ModelAttribute StatusHistory param, @RequestParam(required=false) Integer start, @RequestParam(required=false) Integer limit, @RequestParam(required = false) String sort) {
        if(StringUtils.isNotBlank(param.getEquipCode())){
        	param.setEquipCode(equipInfoService.getEquipLineByEquip(param.getEquipCode()).getCode());
        }
        if(StringUtils.isBlank(param.getIsCompleted())){
        	param.setIsCompleted("all");
        }
        param.setOrgCode(equipInfoService.getOrgCode());
    	List<StatusHistory> list=statusHistoryService.findShutDownReason(param, start, limit,null,true);
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(statusHistoryService.countShutDownReason(param));
        return tableView;
    }
    
    @ResponseBody
	@RequestMapping(value="equip",method=RequestMethod.GET)		    
	public List<EquipInfo> equip(){
    	List<EquipInfo> result=equipInfoService.getByOrgCode(equipInfoService.getOrgCode(), EquipType.PRODUCT_LINE);
		for(int i=0;i<result.size();i++){
	    	EquipInfo infos=result.get(i);
	    	result.get(i).setName("["+infos.getCode().replace("_EQUIP", "")+"]  "+infos.getName().replace("_设备", ""));
	    }
		return result;
	}
    
    @ResponseBody
    @RequestMapping(value = "statusReason", method = RequestMethod.GET)
    public List<DataDic> statusReason() {
    	List<DataDic> statusReasonList = dataDicService.getCodeByTermsCode(TermsCodeType.SHUT_DOWN_REASON);
    	return statusReasonList;
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public void update(@RequestBody String jsonText) throws ClassNotFoundException {
		StatusHistory statusHistory =  JSON.parseObject(jsonText, StatusHistory.class);
		statusHistory.setStartTime(null);
		statusHistory.setEndTime(null);
		statusHistoryService.update(statusHistory);
	}
    
    @RequestMapping(value="/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable String fileName,
                       @RequestParam String params,
                       @ModelAttribute StatusHistory param/*@RequestParam(required = false) String queryParams*/) throws IOException, WriteException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    	if(StringUtils.isNotBlank(param.getEquipCode())){
        	param.setEquipCode(equipInfoService.getEquipLineByEquip(param.getEquipCode()).getCode());
        }
        if(StringUtils.isBlank(param.getIsCompleted())){
        	param.setIsCompleted("no");
        }
        param.setOrgCode(equipInfoService.getOrgCode());
        
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
        String a = JSONObject.toJSONString(param);
        JSONObject queryFilter  = JSONObject.parseObject(a);
        statusHistoryService.export(os, sheetName, columns,queryFilter);
        os.close();
	}
}
