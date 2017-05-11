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
package cc.oit.bsmes.pro.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.pla.service.OrderProcessPRService;
import cc.oit.bsmes.pro.model.ProductProcessWip;
import cc.oit.bsmes.pro.service.ProductProcessWipService;

/**
 * @ClassName:   ProductProcessWipController
 * @Description: TODO(订单工艺工序)
 * @author:      DingXintao
 * @date:        2016年5月20日 上午10:28:32
 */
@Controller
@RequestMapping("/pro/processWip")
public class ProductProcessWipController {
	@Resource
	private ProductProcessWipService productProcessWipService;
	@Resource
	private OrderProcessPRService orderProcessPRService;

	/**
	 * 工艺ID获取工序明细
	 * 
	 * @author DingXintao
	 * @param craftsId 工艺ID
	 * @return
	 * */
	@RequestMapping
	@ResponseBody
	public List<ProductProcessWip> process(@RequestParam String craftsId) {
		List<ProductProcessWip> list = productProcessWipService.getByProductCraftsIdAsc(craftsId);
		return list;
	}
}
