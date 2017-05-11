package cc.oit.bsmes.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;

import com.alibaba.fastjson.JSONObject;

/**
 * 补充生产单数据 OUT_ATTR_DESC IN_ATTR_DESC
 */
public class DeleteData extends BaseTest {
	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;

	/**
	 * 产出对象需要保存信息的初始化方法
	 * */
	public static Map<String, String> outAttrMap = new HashMap<String, String>();
	public static Map<String, String> inAttrMap = new HashMap<String, String>();

	/**
	 * bean初始化后执行的方法
	 * */
	@PostConstruct
	public void initMethod() {
		outAttrMap.put("guidePly", "指导厚度");
		outAttrMap.put("standardPly", "标准厚度");
		outAttrMap.put("standardMaxPly", "标称厚度最大值");
		outAttrMap.put("standardMinPly", "标称厚度最小值");
		outAttrMap.put("outsideValue", "标准外径");
		outAttrMap.put("outsideMaxValue", "最大外径");
		outAttrMap.put("outsideMinValue", "最小外径");
		outAttrMap.put("moldCoreSleeve", "模芯模套");
		outAttrMap.put("wiresStructure", "线芯结构");
		outAttrMap.put("conductorStruct", "导体结构");
		outAttrMap.put("material", "材料");
		outAttrMap.put("coverRate", "搭盖率");

		inAttrMap.put("kuandu", "宽度");
		inAttrMap.put("houdu", "厚度");
		inAttrMap.put("caizhi", "材质");
		inAttrMap.put("chicun", "尺寸");
		inAttrMap.put("guige", "规格");
		inAttrMap.put("disk", "库位");
		inAttrMap.put("wireCoil", "盘具");
	}

	/**
	 * 执行函数
	 * */
	@Test
	@Rollback(false)
	public void process() throws BiffException, IOException {
		try {
			// 补充投入信息 IN_ATTR_DESC
//			patchInAttrDesc();
			// 补充产出信息 OUT_ATTR_DESC
			patchOutAttrDesc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void patchInAttrDesc() {
		List<MaterialRequirementPlan> inMatArray = materialRequirementPlanService.getAll();
		if (null == inMatArray) {
			return;
		}
		for (MaterialRequirementPlan inMat : inMatArray) {
//			if (StringUtils.isEmpty(inMat.getInAttrDesc())) {
				// 根据物料编码获取物料明细：名字、宽度、厚度、材质、尺寸、规格、库位、盘具
				List<Map<String, String>> attrMapArray = materialRequirementPlanService.getPatchInAttrDesc(
						inMat.getId(), inMat.getMatCode());
				if (null != attrMapArray && attrMapArray.size() > 0) {
					Map<String, String> attrMap = attrMapArray.get(0);
					inMat.setMatName(attrMap.get("MATNAME"));
					String inAttrDesc = getAttrDesc(attrMap, InOrOut.IN);
					inMat.setInAttrDesc(inAttrDesc);
					materialRequirementPlanService.update(inMat);
				}
//			}
		}
	}

	private void patchOutAttrDesc() {
		List<CustomerOrderItemProDec> prodecArray = customerOrderItemProDecService.getAll();
		if (null == prodecArray) {
			return;
		}
		for (CustomerOrderItemProDec prodec : prodecArray) {
//			if (StringUtils.isEmpty(prodec.getOutAttrDesc())) {
				// 根据工序获取质量参数：指导厚度、标准厚度、标称厚度最大值、标称厚度最小值、标准外径、最大外径、最小外径、模芯模套、线芯结构、导体结构、导体结构、搭盖率
				List<Map<String, String>> prodecMapArray = customerOrderItemProDecService.getPatchOutAttrDesc(prodec.getProcessCode(),
						prodec.getId(), prodec.getProcessId());
				if (null != prodecMapArray && prodecMapArray.size() > 0) {
					Map<String, String> attrMap = prodecMapArray.get(0);
					prodec.setWorkOrderNo(attrMap.get("WORKORDERNO"));
					prodec.setProductType(attrMap.get("PRODUCTTYPE"));
					prodec.setCustProductType(attrMap.get("CUSTPRODUCTTYPE"));
					prodec.setOperator(attrMap.get("OPERATOR"));
					String outAttrDesc = getAttrDesc(attrMap, InOrOut.OUT);
					prodec.setOutAttrDesc(outAttrDesc);
					customerOrderItemProDecService.update(prodec);
				}
//			}
		}
	}

	/**
	 * 获取产出的
	 * 
	 * */
	private String getAttrDesc(Map<String, String> attrMap, InOrOut inout) {
		JSONObject json = new JSONObject();
		String tmp = "", key = "";
		Iterator<String> keys = null;
		if (inout == InOrOut.OUT) {
			keys = outAttrMap.keySet().iterator();
		} else if (inout == InOrOut.IN) {
			keys = inAttrMap.keySet().iterator();
		}
		if (null != keys) {
			while (keys.hasNext()) {
				key = (String) keys.next();
				tmp = attrMap.get(key.toUpperCase());
				if (StringUtils.isNotEmpty(tmp)) {
					json.put(key, tmp);
				}
			}
		}
		return json.toJSONString();
	}

}
