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
@RequestMapping("/pla/materialMng")
public class MaterialMngController {
	
	@Resource
	private MaterialMngService materialMngService;
	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;
	@Resource
	private WorkOrderService workOrderService;
	
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "pla");
        model.addAttribute("submoduleName", "materialMng");
        return "pla.materialMng";
    }
	
	@RequestMapping
	@ResponseBody
	public TableView list( @RequestParam(required = false) String matCode, 
						@RequestParam(required = false) String workOrderNo, 
						@RequestParam(required = false) String status,
						@RequestParam Integer start,@RequestParam Integer limit,@RequestParam String sort){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("matCode", matCode);
		param.put("workOrderNo", workOrderNo);
		int count = 0;
		List<MaterialMng> materialList = new ArrayList<MaterialMng>();
		if("MAT_DOWN".equals(status)){
			param.put("status", status);
			materialList = materialMngService.findMap(param,start, limit, JSONArray.parseArray(sort, Sort.class));
			count = materialMngService.countMap(param);
		}else if("MAT_BORROW".equals(status)){
			param.put("status", status);
			materialList = materialMngService.findBuLiaoMap(param,start, limit, JSONArray.parseArray(sort, Sort.class));
			count = materialMngService.countBuLiaoMap(param);
		}else{
			param.put("status", status);
			materialList = materialMngService.findFaLiaoMap(param,start, limit, JSONArray.parseArray(sort, Sort.class));
			count = materialMngService.countFaLiaoMap(param);
		}
		TableView tableView = new TableView();
		tableView.setRows(materialList);
		tableView.setTotal(count);
		return tableView;
	}
	
	@RequestMapping(value="save/{quantity}/{id}/{status}/{workOrderNo}",method = RequestMethod.GET)
	@ResponseBody
	public void save(@PathVariable String quantity,@PathVariable String id,@PathVariable String status,@PathVariable String workOrderNo){
		if(status.equals("MAT_DOWN")){
			BorrowMat b = materialMngService.getByBorrowMatId(id);
			b.setFaLiaoQuantity(Double.valueOf(quantity));
			b.setStatus(MaterialStatus.MAT_GETED);
			materialMngService.updateBorrowMat(b);
		}else{
			SupplementMaterial s = materialMngService.getSumpById(id);
			s.setFaLiaoQuantity(Double.valueOf(quantity));
			s.setStatus(MaterialStatus.MAT_GETED);
			materialMngService.updateEquipMat(s);
		}
		WorkOrder workOrder = workOrderService.getByWorkOrderNO(workOrderNo);
        workOrder.setMatStatus(MaterialStatus.MAT_GETED);
        workOrderService.update(workOrder);
	}
	
	@RequestMapping(value="saveBorrow/{quantity}/{matCode}/{workOrderNo}",method = RequestMethod.GET)
	@ResponseBody
	public void saveBorrow(@PathVariable String quantity,@PathVariable String matCode,@PathVariable String workOrderNo){
		SupplementMaterial supplementMaterial = new SupplementMaterial();
//		matBorrow.setWorkOrderNo(workOrderNo);
		supplementMaterial.setMatCode(matCode);
//		supplementMaterial.setStatus(MaterialStatus.MAT_GETED);
//		matBorrow.setDemandQuantity(Double.valueOf(quantity));
		materialMngService.updateEquipMat(supplementMaterial);
		
	}

}
