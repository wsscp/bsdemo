package cc.oit.bsmes.bas.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cc.oit.bsmes.bas.model.Amoeba;
import cc.oit.bsmes.bas.service.AmoebaService;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 绝缘 阿米巴
 * @author 王国华
 * @date 2017-01-09 11:21:06
 */
@Controller
@RequestMapping("/bas/amoeba")
public class AmoebaController {
	

	@Resource
	private AmoebaService amoebaService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "amoeba");
        return "bas.amoeba";
    }

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@SuppressWarnings({ "rawtypes" })
	public TableView list(HttpServletRequest request, Amoeba findParams, @RequestParam int start, @RequestParam int limit) {

		List list = amoebaService.find(findParams, start, limit, null);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(amoebaService.count(findParams));
		return tableView;
	}
	

	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		Amoeba amoeba =  JSON.parseObject(jsonText, Amoeba.class);
		amoebaService.insert(amoeba);
		amoeba = amoebaService.getById(amoeba.getId());
		updateResult.addResult(amoeba);
		return updateResult;
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		Amoeba amoeba =  JSON.parseObject(jsonText, Amoeba.class);
		amoebaService.update(amoeba);
		amoeba = amoebaService.getById(amoeba.getId());
		updateResult.addResult(amoeba);
		return updateResult;
	}

	/**
	 * 导入阿米巴数据
	 */
	@RequestMapping(value="/importAmoeba",method=RequestMethod.POST)
	@ResponseBody
	 public JSONObject importAmoeba(HttpServletRequest request, @RequestParam MultipartFile importFile)
	            throws Exception {
		 org.apache.poi.ss.usermodel.Workbook workbook = null;
		 try {
	            workbook = new XSSFWorkbook(importFile.getInputStream());
	        } catch (Exception e) {
	            workbook = new HSSFWorkbook(importFile.getInputStream());
	        }
	        Sheet sheet = workbook.getSheetAt(0);
	        JSONObject result = new JSONObject();
	        if (sheet == null) {
	            result.put("message", "文件格式错误'");
	        } else {
	        	amoebaService.importAmoebaData(sheet,result);
	        }
	        return result;
	}
}
