package cc.oit.bsmes.pro.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.pro.model.EqipListBz;
import cc.oit.bsmes.pro.service.EqipListBzService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @author 陈翔
 */
@Controller
@RequestMapping("/pro/process/eqipListBz")
public class EqipListBzController {
	
	@Resource
	private EqipListBzService eqipListBzService;
	
	
    @ResponseBody
    @RequestMapping(value="queryProcessEquipListBz",method=RequestMethod.GET)
    public TableView queryProcessEquipListBz(@RequestParam String processId,
    		@RequestParam int page, @RequestParam(required = false) String sort,
    		@RequestParam int start,
    		@RequestParam int limit){
    	TableView tableView = new TableView();
    	EqipListBz findParams = new EqipListBz();
    	findParams.setProcessId(processId);
    	List<EqipListBz> rows = eqipListBzService.getByProcessId(processId, start, limit, JSONArray.parseArray(sort, Sort.class));;
    	int total = eqipListBzService.count(findParams);
    	tableView.setRows(rows);
    	tableView.setTotal(total);
    	return tableView;
    }
    @ResponseBody
    @RequestMapping(value="updateProcessEquipListBz/{id}",method=RequestMethod.PUT)
    public UpdateResult updateProcessEquipListBz(@PathVariable String id,@RequestBody String jsonText){
    	UpdateResult updateResult = new UpdateResult();
    	EqipListBz equipListBz= JSON.parseObject(jsonText, EqipListBz.class);
    	eqipListBzService.update(equipListBz);
    	updateResult.addResult(equipListBz);
    	return updateResult;
    }
}