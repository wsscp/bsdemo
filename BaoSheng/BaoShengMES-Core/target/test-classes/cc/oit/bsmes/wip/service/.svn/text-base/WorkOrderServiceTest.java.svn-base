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
package cc.oit.bsmes.wip.service;

import cc.oit.bsmes.bas.service.OrgService;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.model.WorkOrder;

import org.apache.poi.ss.usermodel.DateUtil;
import org.junit.Test;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author Administrator
 * @date 2014-1-20 下午4:43:54
 * @since
 * @version
 */
public class WorkOrderServiceTest extends BaseTest {
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private OrgService orgService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private WorkOrderDAO workOrderDao;
	
	@Test
	public void insertTest() {
		Calendar c = Calendar.getInstance();
		Random random = new Random();
		for(int i=0;i<10;i++){
			WorkOrder wo = new WorkOrder();
			wo.setOrgCode(orgService.getAll().get(random.nextInt(3)).getOrgCode());
			wo.setEquipCode(equipInfoService.getAll().get(random.nextInt(1)).getCode());
			wo.setStatus(WorkOrderStatus.TO_DO);
			wo.setOrderLength(random.nextDouble()*100);
			wo.setPreStartTime(c.getTime());
			wo.setWorkOrderNo(c.getTime().getTime()+"");
			c.add(Calendar.DATE, random.nextInt(3));
			wo.setPreEndTime(c.getTime());
			workOrderService.insert(wo); 
		}
	}
	@Test
	public void test() {
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		System.out.println(df2.format(c.getTime()));
		System.out.println(new BigDecimal(123.111).negate());
		
	}
	@Test
	public void getProductsCoordinateTest(){
		WorkOrder workOrder = new WorkOrder();
//		Date realStartTime = new Date("2015-01-09 20:01:15");
//		Date realEndTime = new Date("2015-01-10 20:01:15");
		workOrder.setRealStartTime(DateUtils.convert("2015-01-09 20:01:15", DateUtils.DATE_TIME_FORMAT));
		workOrder.setRealEndTime(DateUtils.convert("2015-01-20 20:01:15", DateUtils.DATE_TIME_FORMAT));
		workOrder.setProcessCode("ss");
		workOrder.setProductCode("sb");
		workOrder.setEquipCode("sc");
		workOrderDao.getFinishedWorkOrder(workOrder);
		workOrderService.getProductsCoordinate(workOrder);
	}
}
