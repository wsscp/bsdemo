package cc.oit.bsmes.pla.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.pla.model.BorrowMat;
import cc.oit.bsmes.pla.model.SupplementMaterial;
import cc.oit.bsmes.pla.model.MaterialMng;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.service.MaterialMngService;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSONArray;

/**
 * 物料管理
 * 
 * @Description
 *
 * @author 陈翔
 * @CreateDate 2015年7月15日
 */
@Controller
@RequestMapping("/pla/materialMngReport")
public class MaterialMngReportController {
	
	@Resource
	private MaterialMngService materialMngService;
	
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "pla");
        model.addAttribute("submoduleName", "materialMngReport");
        return "pla.materialMngReport";
    }
	
	@RequestMapping
	@ResponseBody
	public TableView list( @RequestParam(required = false) String matCode,@RequestParam(required = false) String demandTime,
						@RequestParam Integer start,@RequestParam Integer limit,@RequestParam String sort){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("matCode", matCode);
		param.put("demandTime", demandTime);
		TableView tableView = new TableView();
		tableView.setRows(materialMngService.getReport(param,start, limit, JSONArray.parseArray(sort, Sort.class)));
		tableView.setTotal(materialMngService.getReportCount(param));
		return tableView;
	}
	
}
