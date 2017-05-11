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
import cc.oit.bsmes.inv.service.MatPropService;
import cc.oit.bsmes.pla.service.OrderProcessPRService;

@Controller
@RequestMapping("/inv/prop")
public class MatPropController {
	@Resource
	private MatPropService matPropService;
	@Resource
	private OrderProcessPRService orderProcessPRService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<MatProp> list(@RequestParam String matCode) {
		return matPropService.findByMatCode(matCode);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public UpdateResult insert(@RequestBody MatProp matProp) {
		matPropService.insert(matProp);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(matProp);
		return updateResult;
	}

	/**
	 * @Title:       update
	 * @Description: TODO(修改物料扩展属性)
	 * @param:       id 物料扩展属性主键
	 * @param:       matProp 物料扩展属性请求参数
	 * @param:       salesOrderItemId 客户订单明细id
	 * @param:       processId 工序id
	 * @param:       matCode 物料编码
	 * @param:       matName 物料名称
	 * @param:       inOrOut 投入产出
	 * @return:      UpdateResult   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public UpdateResult update(@PathVariable String id, @RequestBody MatProp matProp) {
		UpdateResult updateResult = new UpdateResult();
		matPropService.update(matProp);
		updateResult.addResult(matProp);

		// 如果是特殊工艺的话，将要新增特殊工艺的变更记录
		if (StringUtils.isNotEmpty(matProp.getSalesOrderItemId())) {
			orderProcessPRService.insertSpencial(matProp.getSalesOrderItemId(), matProp.getProcessId(), "物料属性", matProp.getPropCode(), matProp.getPropName(), 
					matProp.getPropTargetValue(), matProp.getMatCode(), matProp.getMatName(), matProp.getInOrOut());
		}
		return updateResult;
	}

}
