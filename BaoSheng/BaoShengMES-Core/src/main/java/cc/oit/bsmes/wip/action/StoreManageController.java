package cc.oit.bsmes.wip.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.wip.model.StoreManage;
import cc.oit.bsmes.wip.service.StoreManageService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/wip/storeManage")
public class StoreManageController {
	
	@Resource
	private StoreManageService storeManageService;

	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "storeManage");
        return "wip.storeManage";
    }
	
	@RequestMapping
	@ResponseBody
	public TableView list(@RequestParam Integer start,@RequestParam Integer limit,@RequestParam String sort,
			@RequestParam(required = false) String nowDate,
			@RequestParam(required = false) String nowUserCode){
		Map<String, Object> params = new HashMap<String, Object>();
		if (nowDate != null && !nowDate.isEmpty()) {
			params.put("nowDate",nowDate);
		}
        if (nowUserCode != null && !nowUserCode.isEmpty()) {
        	params.put("nowUserCode", new ArrayList<String>(Arrays.asList(nowUserCode.split(","))));
		}
		List<StoreManage> materialList = storeManageService.findResult(params,start, limit, JSONArray.parseArray(sort, Sort.class));
		TableView tableView = new TableView();
		tableView.setRows(materialList);
		tableView.setTotal(storeManageService.countStoreMange(params));
		return tableView;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public List<StoreManage> getUserInfo(HttpServletRequest request){
		List<StoreManage> list = storeManageService.getUserInfo();
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value = "/insertNewData", method = RequestMethod.POST)
	public void insertNewData(HttpServletRequest request,
			@RequestParam String userCode, @RequestParam String statDate,
			@RequestParam String feiTong, @RequestParam String feiXian,
			@RequestParam String jiaoLian, @RequestParam String zaLiao,
			@RequestParam String fsLiao, @RequestParam String wuLu,
			@RequestParam String theorySlopLine,
			@RequestParam String jiechaoSlopLine,
			@RequestParam String slopLineDeductions,
			@RequestParam String realityMaterialPro,
			@RequestParam String theoryWaste,
			@RequestParam String realityWaste,
			@RequestParam String wasteDeductions,
			@RequestParam String rewardPunish) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userCode", userCode);
		param.put("statDate", statDate);
		param.put("feiTong", feiTong);
		param.put("feiXian", feiXian);
		param.put("jiaoLian", jiaoLian);
		param.put("zaLiao", zaLiao);
		param.put("fsLiao", fsLiao);
		param.put("wuLu", wuLu);
		param.put("theorySlopLine", theorySlopLine);
		param.put("jiechaoSlopLine", jiechaoSlopLine);
		param.put("slopLineDeductions", slopLineDeductions);
		param.put("realityMaterialPro", realityMaterialPro);
		param.put("theoryWaste", theoryWaste);
		param.put("realityWaste", realityWaste);
		param.put("wasteDeductions", wasteDeductions);
		param.put("rewardPunish", rewardPunish);
		param.put("operator", SessionUtils.getUser().getUserCode());

		storeManageService.insertNewData(param);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	public void updateData(HttpServletRequest request, @RequestParam String id,
			@RequestParam String userCode, @RequestParam String statDate,
			@RequestParam String feiTong, @RequestParam String feiXian,
			@RequestParam String jiaoLian, @RequestParam String zaLiao,
			@RequestParam String fsLiao, @RequestParam String wuLu,
			@RequestParam String theorySlopLine,
			@RequestParam String jiechaoSlopLine,
			@RequestParam String slopLineDeductions,
			@RequestParam String realityMaterialPro,
			@RequestParam String theoryWaste,
			@RequestParam String realityWaste,
			@RequestParam String wasteDeductions,
			@RequestParam String rewardPunish) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userCode", userCode);
		param.put("statDate", statDate);
		param.put("feiTong", feiTong);
		param.put("feiXian", feiXian);
		param.put("jiaoLian", jiaoLian);
		param.put("zaLiao", zaLiao);
		param.put("fsLiao", fsLiao);
		param.put("wuLu", wuLu);
		param.put("id", id);
		param.put("theorySlopLine", theorySlopLine);
		param.put("jiechaoSlopLine", jiechaoSlopLine);
		param.put("slopLineDeductions", slopLineDeductions);
		param.put("realityMaterialPro", realityMaterialPro);
		param.put("theoryWaste", theoryWaste);
		param.put("realityWaste", realityWaste);
		param.put("wasteDeductions", wasteDeductions);
		param.put("rewardPunish", rewardPunish);
		param.put("operator", SessionUtils.getUser().getUserCode());

		storeManageService.updateData(param);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
	public void deleteData(HttpServletRequest request, @RequestParam String [] ids) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		storeManageService.deleteData(param);
	}
	
	@RequestMapping(value = "/importScrapSub", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importScrapSub(HttpServletRequest request, @RequestParam MultipartFile importFile)
            throws Exception {
        org.apache.poi.ss.usermodel.Workbook workbook = null;

        JSONObject result = new JSONObject();
        String fileName = importFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.indexOf(".")+1);
        if(!"xls".equals(suffix.trim()) && !"xlsx".equals(suffix.trim())){
        	result.put("success", false);
        	result.put("message", "文件格式不对");
        	return result;
        }
        try {
            workbook = new XSSFWorkbook(importFile.getInputStream());
        } catch (Exception e) {
            workbook = new HSSFWorkbook(importFile.getInputStream());
        }
        String year = fileName.substring(0,4);
        int num = workbook.getNumberOfSheets();
        for(int i=1;i<num;i++){
        	Sheet sheet = workbook.getSheet(i+"");
        	if(sheet !=null){
        		storeManageService.importScrapSub(sheet,result,year);
        		if(!result.getBooleanValue("success")){
        			return result;
        		}
        	}
        }
        return result;
	}
}
