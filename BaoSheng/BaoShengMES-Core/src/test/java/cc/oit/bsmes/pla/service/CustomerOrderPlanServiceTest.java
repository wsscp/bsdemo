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

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.dto.CraftDto;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 *
 * @author Administrator
 * @date 2014-1-2 上午9:45:25
 */
public class CustomerOrderPlanServiceTest extends BaseTest {

    @Resource
    private CustomerOrderPlanService customerOrderPlanService;
    @Resource
    private ProductCraftsService productCraftsService;
    @Resource
    private ProductProcessService productProcessService;

    @Test
    public void calculatorOATest() {
        try {
            customerOrderPlanService.calculatorOA(null, "03");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        BigDecimal b1 = new BigDecimal("13");
        BigDecimal b2 = new BigDecimal("3");
        System.out.println(b1.divide(b2, BigDecimal.ROUND_UP));

    }

    @Test
    public void convertCraftTest() {
        CraftDto craftDto = new CraftDto();
        ProductCrafts productCrafts = productCraftsService.getByProductCode("sss");
//		ProductCrafts productCrafts = new ProductCrafts();
        List<ProductProcess> productProcessList = productProcessService.getByProductCode("sss");
    }
}
