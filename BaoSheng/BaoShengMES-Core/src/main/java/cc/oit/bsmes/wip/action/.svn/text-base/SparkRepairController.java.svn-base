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

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.SparkRepair;
import cc.oit.bsmes.wip.service.SparkRepairService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO(描述类的职责)
 * <p>
 * AlarmHistoryController
 * </p>
 * 
 * @author DingXintao
 * @date 2014-12-12 11:20:48
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/sparkRepair")
public class SparkRepairController {
	
	@Resource
	private SparkRepairService sparkRepairService;
	@Resource
	private DataDicService dataDicService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "sparkRepair");
		return "wip.sparkRepair";
    }
	
	/**
	 * <p>
	 * 查询
	 * </p>
	 * 
	 * @return
	 * @author
	 * @date 2014-6-3 14:12:48
	 * @see
	 */
	@RequestMapping
	@ResponseBody
	public TableView list(HttpServletRequest request, SparkRepair findParams, @RequestParam String sort,
			@RequestParam int page, @RequestParam int start, @RequestParam int limit) {
		List<SparkRepair> list = sparkRepairService.find(findParams, start, limit,
				JSONArray.parseArray(sort, Sort.class));
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(sparkRepairService.countFind(findParams));
		return tableView;
	}

	/**
	 * <p>
	 * 查询
	 * </p>
	 * 
	 * @return
	 * @author
	 * @date 2014-6-3 14:12:48
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "/findList/{equipCode}", method = RequestMethod.GET)
	public TableView findList(SparkRepair sparkRepair,
							  @PathVariable String equipCode,
							  @RequestParam int page,
							  @RequestParam int start,
							  @RequestParam int limit) {

		sparkRepair.setEquipCode(equipCode);
		// 查询
		List<SparkRepair> list = sparkRepairService.find(sparkRepair,start,limit);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(sparkRepairService.countFind(sparkRepair));
		return tableView;
	}
	
	
	/**
     * <p>获取修复方式</p>
     *
     * @return
     * @author 
     * @date 2014-6-3 14:12:48
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/repairType", method = RequestMethod.GET)
    public List<DataDic> repairType() {
        List<DataDic> dataDicList = dataDicService.getCodeByTermsCode(TermsCodeType.SPARK_REPAIR_TYPE);
        return dataDicList;
    }
    
    /**
     * 
     * */
    @ResponseBody
    @RequestMapping(value = "/updateSparkRepair", method = RequestMethod.POST)
    public void updateSparkRepair(SparkRepair sparkRepair){
    	sparkRepair.setStatus("COMPLETED");
    	sparkRepairService.update(sparkRepair);
    }
    
}
