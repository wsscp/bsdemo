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
package cc.oit.bsmes.pla.service.impl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cc.oit.bsmes.pla.dao.OrderStatisticsDAO;
import cc.oit.bsmes.pla.model.OrderStatistics;
import cc.oit.bsmes.pla.service.OrderStatisticsService;

/**
 * 
 * @author 吕桐生
 * 生产统计
 * 2017.1.3
 *
 */

@Service
public class OrderStatisticsServiceImpl implements OrderStatisticsService {
	@Resource
	private OrderStatisticsDAO orderStatisticsDAO;
	@Override
	public List<OrderStatistics> getOrderStatisticsList(OrderStatistics param) {
		return orderStatisticsDAO.getOrderStatisticsList(param);
	}
   //获取工段
	@Override
	public List<OrderStatistics> getSection() {
		return orderStatisticsDAO.getSection();
	}
	
	//获取工序
	@Override
	public List<OrderStatistics> getName(){
		return orderStatisticsDAO.getName();
	}	
}
