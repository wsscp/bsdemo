package cc.oit.bsmes.pla.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pro.model.ProductCraftsBz;

import com.alibaba.fastjson.JSONArray;

/**
 * 
 * 物料需求清单
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-1-22 下午6:10:04
 * @since
 * @version
 */
@Controller
@RequestMapping("/pla/materialRequirementPlan")
public class MaterialRequirementPlanController {

	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;
	@Resource
	private MatService matService;
	@Resource
	private EquipInfoService equipInfoService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "pla");
		model.addAttribute("submoduleName", "materialRequirementPlan");
		return "pla.materialRequirementPlan";
	}

	@RequestMapping
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public TableView list(HttpServletRequest request, @ModelAttribute MaterialRequirementPlan param,
			@RequestParam String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) {
		List list = materialRequirementPlanService.find(param, start, limit, JSONArray.parseArray(sort, Sort.class));
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(materialRequirementPlanService.count(param));
		return tableView;
	}

	@ResponseBody
	@RequestMapping(value = "getStatus")
	public List<MaterialRequirementPlan> getStatus(@RequestParam(required = false) Boolean needALL) {
		List<MaterialRequirementPlan> result = new ArrayList<MaterialRequirementPlan>();
		if (needALL != null) {
			MaterialRequirementPlan dataDic = new MaterialRequirementPlan();
			dataDic.setStatusCode("");
			dataDic.setStatusName("所有");
			result.add(dataDic);
		}
		MaterialStatus[] tc = MaterialStatus.values();

		for (MaterialStatus termsCodeType : tc) {
			MaterialRequirementPlan dataDic = new MaterialRequirementPlan();
			dataDic.setStatusCode(termsCodeType.name());
			dataDic.setStatus(termsCodeType);
			result.add(dataDic);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "process", method = RequestMethod.GET)
	public List<MaterialRequirementPlan> getProcess() {
		List<MaterialRequirementPlan> result = materialRequirementPlanService.findByOrgCode(SessionUtils.getUser()
				.getOrgCode());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "workorderNo", method = RequestMethod.GET)
	public List<MaterialRequirementPlan> getworkorderNo(HttpServletRequest request,@RequestParam String query) {
		String workOrderNo = "";
    	if(query != "" && query != null){
    		workOrderNo=query;
    	}
    	List<MaterialRequirementPlan> result = materialRequirementPlanService.getworkorderNo(workOrderNo);
    	return result;
	}

	@ResponseBody
	@RequestMapping(value = "equipment", method = RequestMethod.GET)
	public List<EquipInfo> equipment() {
		List<EquipInfo> result = equipInfoService.getByOrgCode(SessionUtils.getUser().getOrgCode(),
				EquipType.PRODUCT_LINE);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "mat", method = RequestMethod.GET)
	public List<Mat> mat(@RequestParam(required = false) boolean all) {
		Mat mat = new Mat();
//		mat.setMatType(MatType.MATERIALS);
//		List<Mat> list = matService.find(mat, 0, Integer.MAX_VALUE, null);
		List<Mat> list = matService.getMatNames();
		if (all) {
			mat.setMatName("全部");
			list.add(0, mat);
		}
		return list;
	}

	/**
	 * 获取所有物料名称，去重
	 * 
	 * @author DingXintao
	 * */
	@ResponseBody
	@RequestMapping(value = "getAllMatName", method = RequestMethod.GET)
	public List<Map<String, String>> getAllMatName() {
		return matService.getAllMatName();
	}

	/**
	 * 获取该物料名称下的所有描述种类
	 * 
	 * @author DingXintao
	 * @param matName 物料名称
	 * */
	@ResponseBody
	@RequestMapping(value = "getDescByMatName", method = RequestMethod.GET)
	public List<Map<String, String>> getDescByMatName(String matName) {
		if(StringUtils.isEmpty(matName)){
			matName = "";
		}else{
			matName = "%" + matName + "%";
		}
		return matService.getDescByMatName(matName);
	}
	
	/**
	 * 获取某天各个物料数量
	 * 
	 * @author ZhaoXin
	 * @param planDate 查询日期
	 * */
	@ResponseBody
	@RequestMapping(value = "sumPlanData", method = RequestMethod.GET)
	public TableView sumPlanData(@RequestParam(required = false) String planDate) {
		String newPlanDate = planDate.substring(0, 10);
		List<MaterialRequirementPlan> list = materialRequirementPlanService.findSum(newPlanDate);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(materialRequirementPlanService.countSum(newPlanDate));
		return tableView;
	}

}
