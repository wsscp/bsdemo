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
package cc.oit.bsmes.pla.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.pla.dto.CraftDto;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;

/**
 * 订单OA计算服务接口
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2013-12-19 下午1:33:56
 * @since
 * @version
 */
public interface CustomerOrderPlanService {
	
	/**
	 * <p>TODO</p>
	 * @author QiuYangjun
	 * @date 2013-12-24 下午3:17:43
	 * @param resourceCache TODO
	 * @param orgCode TODO
	 * @param 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @see
	 */
	public void calculatorOA(ResourceCache resourceCache, String orgCode) throws IllegalAccessException, InvocationTargetException;

	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2014-1-2 下午4:00:31
	 * @param craftDto
	 * @param productCrafts
	 * @param productProcessList
	 * @param processCustomerOrderItemProDecCache TODO
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @see
	 */
	void convertCraft(CraftDto craftDto, ProductCrafts productCrafts,
			List<ProductProcess> productProcessList, Map<String, List<CustomerOrderItemProDec>> processCustomerOrderItemProDecCache)
			throws IllegalAccessException, InvocationTargetException;
	

}
