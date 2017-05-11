package cc.oit.bsmes.pla.action;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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












import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.pla.model.MaterialCheckReport;
import cc.oit.bsmes.pla.service.MaterialCheckReportService;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSONObject;



/**
 * 物料盘点
 * 
 * @Description
 *
 * @author yezhiqiang
 * @CreateDate 2015年9月7日
 */
@Controller
@RequestMapping("/pla/materialCheck")
public class MaterialCheckController {
	
	@Resource
    private MaterialCheckReportService materialCheckReportService;
	
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "pla");
        model.addAttribute("submoduleName", "materialCheck");
        return "pla.materialCheck";
    }
	
	@ResponseBody
	@RequestMapping
	public TableView indexList(HttpServletRequest request,
			@RequestParam String yearMonth,@RequestParam String warehouseNo, 
			@RequestParam String matName,@RequestParam String sectionName,
			@RequestParam String sort,
			@RequestParam int page, @RequestParam int start,
			@RequestParam int limit) {
		Map<String, String> param = new HashMap<String, String>();
		// 查询
		if (!yearMonth.isEmpty()) {
			param.put("yearMonth", yearMonth);
		}
		if (!warehouseNo.isEmpty()) {
			param.put("warehouseNo", warehouseNo);
		}
		if (!matName.isEmpty()) {
			param.put("matName", matName);
		}
		if (!sectionName.isEmpty()) {
			param.put("sectionName", sectionName);
		}
		List<Map> list = materialCheckReportService.getMaterialStockByMonth(param,start, limit, null);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(materialCheckReportService.countMaterialStockByMonth(param));
		return tableView;
	}
	
	@RequestMapping(value = "/importMaterialCheckReport", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importMaterialCheckReport(HttpServletRequest request, @RequestParam MultipartFile importFile)
            throws Exception {
        org.apache.poi.ss.usermodel.Workbook workbook = null;

        try {
            workbook = new XSSFWorkbook(importFile.getInputStream());
        } catch (Exception e) {
            workbook = new HSSFWorkbook(importFile.getInputStream());
        }
        String fileName = importFile.getOriginalFilename();
        fileName = fileName.substring(0, 7);
        if(fileName.indexOf('月')>0){
        	fileName = fileName.replace("月","");
		}
        Date date = DateUtils.convert(fileName, "yyyy-MM");
        List<String> list = materialCheckReportService.getCheckSectionByMonth(DateUtils.convert(date, "yyyy-MM"));
        JSONObject result = new JSONObject();
        if(list.size() == 5){
        	result.put("message", fileName+"月份库存已导入");
        	result.put("success", false);
        	return result;
        }
    	Sheet sheet = workbook.getSheet("绝缘");
		Sheet sheetHT = workbook.getSheet("护套");
		Sheet sheetCL = workbook.getSheet("成缆");
		Sheet sheetGXJ = workbook.getSheet("硅橡胶");
		Sheet sheetGW = workbook.getSheet("高温");
		boolean a = materialCheckReportService.verdictFormat(sheet,
				materialCheckReportService.getSheetHeaders(sheet), result);
		if (materialCheckReportService.verdictFormat(sheet,
				materialCheckReportService.getSheetHeaders(sheet), result)
				&& materialCheckReportService.verdictFormat(sheetHT,
						materialCheckReportService.getSheetHeaders(sheetHT),
						result)
				&& materialCheckReportService.verdictFormat(sheetCL,
						materialCheckReportService.getSheetHeaders(sheetCL),
						result)
				&& materialCheckReportService.verdictFormat(sheetGXJ,
						materialCheckReportService.getSheetHeaders(sheetGXJ),
						result)
				&& materialCheckReportService.verdictFormat(sheetGW,
						materialCheckReportService.getSheetHeaders(sheetGW),
						result)) {
			if(list.contains("绝缘")){
				if(result.containsKey("message")){
					result.put("message", result.getString("message")+"</br>"+fileName+"月份绝缘库存已导入");
				}else{
					result.put("message", fileName+"月份绝缘库存已导入");
				}
			}else{
				 if(null != sheet){
					 materialCheckReportService.importMaterialCheckReport(sheet , result,date);
				 }
			}
			if (list.contains("护套")) {
				if(result.containsKey("message")){
					result.put("message", result.getString("message")+"</br>"+fileName+"月份护套库存已导入");
				}else{
					result.put("message", fileName+"月份护套库存已导入");
				}
			} else {
				if(null != sheetHT){
					 materialCheckReportService.importMaterialCheckReport(sheetHT , result,date);
				 }
			}
			if (list.contains("硅橡胶")) {
				if(result.containsKey("message")){
					result.put("message", result.getString("message")+"</br>"+fileName+"月份硅橡胶库存已导入");
				}else{
					result.put("message", fileName+"月份硅橡胶库存已导入");
				}
			} else {
				if(null != sheetGXJ){
					 materialCheckReportService.importMaterialCheckReportGXJ(sheetGXJ , result,date);
				 }
			}
			if (list.contains("高温")) {
				if(result.containsKey("message")){
					result.put("message", result.getString("message")+"</br>"+fileName+"月份高温库存已导入");
				}else{
					result.put("message", fileName+"月份高温库存已导入");
				}
			} else {
				if(null != sheetGW){
					 materialCheckReportService.importMaterialCheckReportGXJ(sheetGW , result,date);
				 }
			}
			if (list.contains("成缆")) {
				if(result.containsKey("message")){
					result.put("message", result.getString("message")+"</br>"+fileName+"月份成缆库存已导入");
				}else{
					result.put("message", fileName+"月份成缆库存已导入");
				}
			} else {
				if(null != sheetCL){
					 materialCheckReportService.importMaterialCheckReportCL(sheetCL , result,date);
				 }
			}
		}
		if(!result.containsKey("success")){
			result.put("message", "文件中无库存页或文件中的库存已导入,请确认");
			result.put("success", false);
		}
		return result;
    }
	
	@RequestMapping(value = "/getCheckDays", method = RequestMethod.GET)
    @ResponseBody
	public List<MaterialCheckReport> getCheckDays(@RequestParam String yearMonth){
		return materialCheckReportService.getCheckDays(yearMonth);
	}
	@RequestMapping(value = "/getCheckMonth", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getCheckMonth(){
		return materialCheckReportService.getCheckMonth();
	}
}
