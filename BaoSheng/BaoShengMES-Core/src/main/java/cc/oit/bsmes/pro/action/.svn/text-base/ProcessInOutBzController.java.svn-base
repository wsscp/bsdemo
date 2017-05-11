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
import cc.oit.bsmes.pro.model.ProcessInOutBz;
import cc.oit.bsmes.pro.service.ProcessInOutBzService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @author 陈翔
 */
@Controller
@RequestMapping("/pro/processBz/inOut")
public class ProcessInOutBzController {
	
	@Resource
	private ProcessInOutBzService processInOutBzService;
	
	
	/**
	 * @Title: list默认查询
	 * @Description: TODO(查询工序下面的投入产出)
	 * @param: processId 工序ID
	 * @return: List<ProcessInOut>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<ProcessInOutBz> list(@RequestParam String processId, @RequestParam int page, @RequestParam(required = false) String sort,
			@RequestParam int start, @RequestParam int limit) {
		return processInOutBzService.getByProcessId(processId);
	}

	/**
	 * @Title: insertProcessInOut
	 * @Description: TODO(更新投入产出信息)
	 * @param: processInOut 投入产出更新参数: oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return: UpdateResult
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public UpdateResult insertProcessInOut(@RequestBody ProcessInOutBz processInOut) {
		processInOutBzService.insert(processInOut);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(processInOut);
		return updateResult;
	}

	/**
	 * @Title: updateProcessInOut
	 * @Description: TODO(更新投入产出信息)
	 * @param: processInOut 投入产出更新参数: oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return: UpdateResult
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public UpdateResult updateProcessInOut(@PathVariable String id, @RequestBody ProcessInOutBz processInOut) {
		processInOutBzService.update(processInOut);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(processInOut);
		return updateResult;
	}
}