package cc.oit.bsmes.inv.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.inv.model.MatProp;
import cc.oit.bsmes.inv.model.MatPropWip;
import cc.oit.bsmes.inv.service.MatPropWipService;
import cc.oit.bsmes.pla.service.OrderProcessPRService;

@Controller
@RequestMapping("/inv/propWip")
public class MatPropWipController {
	@Resource
	private MatPropWipService matPropWipService;
	@Resource
	private OrderProcessPRService orderProcessPRService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<MatProp> list(@RequestParam String inOutId) {
		return matPropWipService.findByMatCode(inOutId);
	}

	/**
	 * @Title:       insert
	 * @Description: TODO(新增物料扩展属性)
	 * @param:       matProp 物料扩展属性请求参数
	 * @return:      UpdateResult   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public UpdateResult insert(@RequestBody MatPropWip matPropWip) {
		matPropWipService.insert(matPropWip);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(matPropWip);
		return updateResult;
	}

	/**
	 * @Title:       update
	 * @Description: TODO(修改物料扩展属性)
	 * @param:       id 物料扩展属性主键
	 * @param:       matProp 物料扩展属性请求参数
	 * @return:      UpdateResult   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public UpdateResult update(@PathVariable String id, @RequestBody MatPropWip matPropWip) {
		UpdateResult updateResult = new UpdateResult();
		matPropWipService.update(matPropWip);
		updateResult.addResult(matPropWip);

		// 如果是特殊工艺的话，将要新增特殊工艺的变更记录
		if (StringUtils.isNotEmpty(matPropWip.getSalesOrderItemId())) {
			orderProcessPRService.insertSpencial(matPropWip.getSalesOrderItemId(),matPropWip.getProcessId(), "物料属性", matPropWip.getPropCode(), matPropWip.getPropName(), 
					matPropWip.getPropTargetValue(), matPropWip.getMatCode(), matPropWip.getMatName(), matPropWip.getInOrOut());
		}
		return updateResult;
	}

}
