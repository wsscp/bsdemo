package cc.oit.bsmes.ord.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.constants.SalesOrderStatus;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.ord.dao.SalesOrderDAO;
import cc.oit.bsmes.ord.dao.SalesOrderItemDAO;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.ProductService;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@Service
public class SalesOrderServiceImpl extends BaseServiceImpl<SalesOrder> implements SalesOrderService {

	@Resource
	private SalesOrderDAO salesOrderDAO;
	@Resource
	private SalesOrderItemDAO salesOrderItemDAO;
	@Resource
	private CustomerOrderService customerOrderService;
	@Resource
	private ProductService productService;

	@Override
	public List<SalesOrder> getCustomerCompany(String contractNo, String orgcode) {
		return salesOrderDAO.getCustomerCompany(contractNo, orgcode);
	}

	@Override
	public SalesOrder getByContractNo(String contractNo, String orgCode) {
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setContractNo(contractNo);
		salesOrder.setOrgCode(orgCode);
		return salesOrderDAO.getSalesOrderByContractNo(salesOrder);
	}

	@Override
	@Transactional(readOnly = false)
	public void importProPlan(Sheet sheet, String orgCode, JSONObject result) {
		List<SalesOrder> salesOrders = new ArrayList<SalesOrder>();
		SalesOrder salesOrder = null;
		int maxRow = sheet.getLastRowNum() + 1;
		String oldContractNo = "";
		int importNum = 0;

		for (int i = 1; i < maxRow; i++) {
			Row row = sheet.getRow(i); // 23
			// 单位、合同号、产品规格、计划长度、产品编码不能为空
			if (null == row.getCell(0) || null == row.getCell(1) || null == row.getCell(3) || null == row.getCell(7)
					|| null == row.getCell(9) || null == row.getCell(23) || null == row.getCell(24)) {
				continue;
			}
			if (row.getCell(0).getStringCellValue().equals("客户名称")) {
				continue;
			}
			if (row.getCell(0).getStringCellValue().contains("合计")) {
				continue;
			}

			String contractNo = row.getCell(1).getStringCellValue();
			String productCode = row.getCell(23).getStringCellValue() + "-" + row.getCell(7).getStringCellValue();

			if (StringUtils.isBlank(contractNo)) {
				result.put("success", false);
				result.put("message", "第" + (i + 1) + "行合同号不能为空");
				return;
			}

//			Product product = productService.getByProductCode(productCode);
//			if (product == null) {
//				result.put("success", false);
//				result.put("message", "第" + (i + 1) + "行产品代码在系统中不存在!");
//				return;
//			}

			Cell orderLengthCell = row.getCell(9);
			if (orderLengthCell == null || orderLengthCell.getNumericCellValue() <= 0) {
				result.put("success", false);
				result.put("message", "第" + (i + 1) + "行计划长度为空或数据有误!");
				return;
			}

			salesOrder = getByContractNo(contractNo, orgCode);
			if (StringUtils.isBlank(oldContractNo) || !(oldContractNo.equals(contractNo))) {
				if (salesOrder == null) {
					salesOrder = new SalesOrder();
				}
				salesOrders.add(salesOrder);
				salesOrder.setOrgCode(orgCode);
				salesOrder.setStatus(SalesOrderStatus.TO_DO);
				// TODO
				salesOrder.setContractNo(String.valueOf(contractNo));
				salesOrder.setSalesOrderNo("BS14031" + (10380 + i));
				salesOrder.setCustomerCompany(row.getCell(0).getStringCellValue());
				salesOrder.setOperator(row.getCell(2).getStringCellValue());

				Date confirmDate = row.getCell(3).getDateCellValue();
				salesOrder.setConfirmDate(confirmDate);
				if (StringUtils.isBlank(salesOrder.getId())) {
					salesOrderDAO.insert(salesOrder);
				} else {
					salesOrderDAO.update(salesOrder);
				}

			}
			SalesOrderItem salesOrderItem = new SalesOrderItem();
			salesOrderItem.setSalesOrderId(salesOrder.getId());
			salesOrderItem.setOrgCode(orgCode);
			salesOrderItem.setStatus(SalesOrderStatus.TO_DO);
			salesOrderItem.setProductCode(productCode);
			setOrderItemProperty(row, salesOrderItem);
			// salesOrderItem.setSaleorderLength(redundantAmount(salesOrderItem.getWiresStructure(),
			// salesOrderItem.getContractLength())); // 订单长度
			salesOrderItem.setSaleorderLength(salesOrderItem.getContractLength()); // 订单长度
																					// //
																					// 余量不在此处放了

			Cell cell = row.getCell(24);
			double num = 1;
			if (cell != null) {
				num = cell.getNumericCellValue();
				if (num == 0.0) {
					num = 1;
				}
			}
			salesOrderItem.setStandardLength(salesOrderItem.getSaleorderLength() / num);

			salesOrderItem.setLengthConstraints(",100:0;");
//
//			if (salesOrderItem.getStandardLength() == null) {
//				salesOrderItem.setStandardLength(Double.parseDouble(product.getStandardLength()));
//			}
			
			salesOrderItemDAO.insert(salesOrderItem);
			importNum++;
		}

		for (SalesOrder order : salesOrders) {
			try {
				CustomerOrder customerOrder = customerOrderService.insert(order);
				customerOrderService.setCustomerOaDate(customerOrder, order.getConfirmDate());
			} catch (Exception e) {
				result.put("success", false);
				result.put("message", "订单" + order.getContractNo() + "导入分解出现异常，请查看相关工艺是否正确");
				e.printStackTrace();
				return;
			}

		}
		result.put("success", true);
		result.put("message", "成功导入" + importNum + "个产品!");
	}

	/**
	 * 0 单位 1 合同号 2 经办人 3 副本交货期 4 产品型号 5 产品规格 6 客户产品型号 7 客户产品规格 8 芯数 9 计划长度 10
	 * 截面 11 线芯结构 12 备注说明 13 投产长度 14 项目组 15 线芯长度 16 副本说明 17 铜重量 18 合同金额 19 下达日起
	 * 20 空 21 制造部门 22 状态 23 产品编码
	 * */
	private void setOrderItemProperty(Row row, SalesOrderItem salesOrderItem) {
		for (int j = 0; j < row.getLastCellNum(); j++) {
			Cell cell = row.getCell(j);
			if (null == cell) {
				continue;
			}
			switch (j) {
			case 4:
				salesOrderItem.setProductType(cell.getStringCellValue()); // 产品型号
				break;
			case 5:
				String productSpec = cell.getStringCellValue();
				productSpec = productSpec.replaceAll("[([\u4E00-\u9FA5]++)]", "");
				productSpec = productSpec.replaceAll("(\\(+\\))", "");
				salesOrderItem.setProductSpec(productSpec); // 产品规格
				//通过excel中绝缘交货期列计算芯数
				if(!"".equals(cell.getStringCellValue()) && cell.getStringCellValue() != null){
					String[] a = productSpec.split("\\+");
					if(a.length >  1 && a[0] != null && !"".equals(a[0]) && a[1] != null && !"".equals(a[1])){
						BigDecimal cd = new BigDecimal(0);
						List<String> cs = new ArrayList<String>() ;
						for(String obj : a){
							String[] c = obj.split("\\*");
							if(c.length > 1){
								if(c.length == 2 && c[0] != null && !"".equals(c[0])){
									cd = cd.add(new BigDecimal(c[0]));
									cs.add(c[1]);
								}
							}else{
								cd = cd.add(new BigDecimal(1));
								cs.add(obj.replaceAll("[^\\d.]+",""));
							}
						}
						  HashSet<String> h  =  new HashSet<String>(cs); 
						  cs.clear(); 
						  cs.addAll(h); 
						salesOrderItem.setNumberOfWires(cd.intValue());
						salesOrderItem.setSection(cs.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
//						String[] c = a[0].split("\\*");
//						String[] d = a[1].split("\\*");
//						BigDecimal cd = null ,dd = null ; 
//						String cs = null ,ds = null ;
//						if(c.length == 2 && c[0] != null && !"".equals(c[0])){
//							 cd = new BigDecimal(c[0]);
//							 cs = c[1];
//						}
//						if(d.length == 2 && d[0] != null && !"".equals(d[0])){
//							 dd = new BigDecimal(d[0]);
//							 ds = d[1];
//							 
//						}
//						if(cd != null && dd != null ){
//							salesOrderItem.setNumberOfWires(cd.add(dd).intValue());
//							salesOrderItem.setSection(cs +"," + ds);
//						}
					}else{
						String s = cell.getStringCellValue().replaceAll("\\(", "").replaceAll("\\)", "");
						String[] b = s.split("\\*");
						if(b.length == 2 && b[0] != null && !"".equals(b[0])){
							salesOrderItem.setNumberOfWires(new BigDecimal(b[0]).intValue());
							salesOrderItem.setSection(b[1].replaceAll("([^0-9.]+)","($1)"));
						}else if(b.length == 3 && b[0] != null && !"".equals(b[0]) && b[1] != null && !"".equals(b[1]) ){
							salesOrderItem.setNumberOfWires(new BigDecimal(b[0]).multiply(new BigDecimal(b[1])).intValue());
							salesOrderItem.setSection(b[2].replaceAll("([^0-9.]+)","($1)"));
						}
					}
				}
				break;
			case 6:
				salesOrderItem.setCustProductType(cell.getStringCellValue()); // 客户产品型号
				break;
			case 7:
				salesOrderItem.setCustProductSpec(cell.getStringCellValue()); // 客户产品规格
				break;
			case 8:
//				Double d = cell.getNumericCellValue();
//				salesOrderItem.setNumberOfWires(d.intValue()); // 芯数
				break;
			case 9:
				salesOrderItem.setContractLength(cell.getNumericCellValue()); // 计划长度/合同长度
				break;
			case 10:
//				salesOrderItem.setSection(cell.getNumericCellValue()); // 截面
				break;
			case 11:
				String wiresStructure = customerOrderService.getWiresStructure(salesOrderItem.getProductCode()); // 空默认A
				salesOrderItem.setWiresStructure(StringUtils.isEmpty(wiresStructure)? "A" : wiresStructure); // 线芯结构
				break;
			case 12:
				salesOrderItem.setRemarks(cell.getStringCellValue()); // 备注说明
				break;
			case 15:
				// salesOrderItem.setWiresLength(); // 线芯长度
				break;
			case 18:
				salesOrderItem.setContractAmount(cell.getNumericCellValue()); // 合同金额
				break;
			default:
				break;
			}
		}
	}

	// TODO 为ERP自动导入订单订单使用，根据产品的分类（如A,B,C类）的不同分别计算余量,单位为km
	private Double redundantAmount(String orderItemType, Double contractLength) {
		Double salesOrderLength = 0.0;
		if (SalesOrderItem.ORDER_ITEM_TYPE_A.equalsIgnoreCase(orderItemType)) {
			if (contractLength >= 1.0)
				salesOrderLength = contractLength * (1 + 0.01);
			else
				salesOrderLength = contractLength + 0.001;
		} else if (SalesOrderItem.ORDER_ITEM_TYPE_B.equalsIgnoreCase(orderItemType)) {
			if (contractLength >= 0.6)
				salesOrderLength = contractLength * (1 + 0.015);
			else
				salesOrderLength = contractLength + 0.01;
		} else if (SalesOrderItem.ORDER_ITEM_TYPE_C.equalsIgnoreCase(orderItemType)) {
			if (contractLength >= 0.5)
				salesOrderLength = contractLength * (1 + 0.02);
			else
				salesOrderLength = contractLength + 0.01;
		} else {
			salesOrderLength = contractLength;
		}
		return salesOrderLength;
	}

	/**
	 * 新的导入方法： 不作分卷，分卷表只有一条数据，不拆分
	 * 
	 * 0 单位 1 合同号 2 经办人 3 副本交货期 4 产品型号 5 产品规格 6 客户产品型号 7 客户产品规格 8 芯数 9 计划长度 10
	 * 截面 11 线芯结构 12 备注说明 13 投产长度 14 项目组 15 线芯长度 16 副本说明 17 铜重量 18 合同金额 19 下达日起
	 * 20 空 21 制造部门 22 状态 23 产品编码 24 卷数
	 * 
	 * */
	@Override
	@Transactional(readOnly = false)
	public void importProPlanToItemDec(Sheet sheet, String orgCode, JSONObject result) {
		List<SalesOrder> salesOrders = new ArrayList<SalesOrder>();
		Multimap<String, SalesOrderItem> orderItemCache = ArrayListMultimap.create(); // 存放salesOrderItem的map对象，用于解决性能问题，减少数据库查询
		Map<String, String> productCraftsCache = new HashMap<String, String>(); // 存放产品编码对应工艺ID的缓存，用于解决性能问题，减少数据库查询
		SalesOrder salesOrder = null;
		int maxRow = sheet.getLastRowNum() + 1;
		String oldContractNo = "";
		int importNum = 0;

		Date a = new Date();

		for (int i = 1; i < maxRow; i++) {
			Row row = sheet.getRow(i); // 23
			// 单位、合同号、产品规格、计划长度、产品编码不能为空
			if (null == row || null == row.getCell(0) || null == row.getCell(1) || null == row.getCell(3)
					|| null == row.getCell(7) || null == row.getCell(9) || null == row.getCell(23)
					|| null == row.getCell(24)) {
				continue;
			}
			if (row.getCell(0).getStringCellValue().equals("客户名称")) {
				continue;
			}
			if (row.getCell(0).getStringCellValue().contains("合计")) {
				continue;
			}

			String contractNo = row.getCell(1).getStringCellValue();
			// 2015.6.10 注释掉 ，因产品规格中可能会包含如3*16+E16格式的产品规格，实际上就是4*16
			String productCode = row.getCell(23).getStringCellValue() + "-" + row.getCell(7).getStringCellValue();
//			String productCode = row.getCell(23).getStringCellValue() + "-" + row.getCell(5).getStringCellValue();

			if (StringUtils.isBlank(contractNo)) {
				result.put("success", false);
				result.put("message", "第" + (i + 1) + "行合同号不能为空");
				continue;
				// return;
			}

			// 导入订单
			Product product = productService.getByProductCode(productCode);
			/* if (product == null) {
				result.put("success", false);
				result.put("message", "第" + (i + 1) + "行产品代码在系统中不存在!");
				continue;
				// return;
			}*/

			Cell orderLengthCell = row.getCell(9);
			if (orderLengthCell == null || orderLengthCell.getNumericCellValue() <= 0) {
				result.put("success", false);
				result.put("message", "第" + (i + 1) + "行计划长度为空或数据有误!");
				continue;
				// return;
			}

			salesOrder = getByContractNo(contractNo, orgCode);
			if (StringUtils.isBlank(oldContractNo) || !(oldContractNo.equals(contractNo))) {
				if (salesOrder == null) {
					salesOrder = new SalesOrder();
				}
				salesOrders.add(salesOrder);
				salesOrder.setOrgCode(orgCode);
				salesOrder.setStatus(SalesOrderStatus.TO_DO);
				// TODO
				salesOrder.setContractNo(String.valueOf(contractNo));
				salesOrder.setSalesOrderNo("BS14031" + (10380 + i));
				salesOrder.setCustomerCompany(row.getCell(0).getStringCellValue());
				salesOrder.setOperator(row.getCell(2).getStringCellValue());

				Date confirmDate = row.getCell(3).getDateCellValue();
				salesOrder.setConfirmDate(confirmDate);
				if (StringUtils.isBlank(salesOrder.getId())) {
					salesOrderDAO.insert(salesOrder);
				} else {
					salesOrderDAO.update(salesOrder);
				}

			}
			SalesOrderItem salesOrderItem = new SalesOrderItem();
			salesOrderItem.setSalesOrderId(salesOrder.getId());
			salesOrderItem.setOrgCode(orgCode);
			salesOrderItem.setStatus(SalesOrderStatus.TO_DO);
			salesOrderItem.setProductCode(productCode);
//			if(StringUtils.isNotBlank(productCode)){
//				salesOrderItem.setWiresStructure(customerOrderService.getWiresStructure(productCode));
//			}
			setOrderItemProperty(row, salesOrderItem);
			// salesOrderItem.setSaleorderLength(redundantAmount(salesOrderItem.getWiresStructure(),
			// salesOrderItem.getContractLength())); // 订单长度
			salesOrderItem.setSaleorderLength(salesOrderItem.getContractLength()); // 订单长度
																					// //
																					// 余量不在此处放了

			Cell cell = row.getCell(24);
			double num = 1;
			if (cell != null) {
				num = cell.getNumericCellValue();
				if (num == 0.0) {
					num = 1;
				}
			}
			salesOrderItem.setStandardLength(salesOrderItem.getSaleorderLength() / num);

			salesOrderItem.setLengthConstraints(",100:0;");

			if (product != null && salesOrderItem.getStandardLength() == null) {
				salesOrderItem.setStandardLength(Double.parseDouble(product.getStandardLength()));
			}
			salesOrderItemDAO.insert(salesOrderItem);

			orderItemCache.put(salesOrder.getId(), salesOrderItem); // 存放salesOrderItem的map对象
			importNum++;
		}

		Date b1 = new Date();
		System.out.println("salesOrder表总耗时：" + (b1.getTime() - a.getTime()) / 1000 + "秒");

		for (SalesOrder order : salesOrders) {
			Date aa = new Date();
			try {
				List<SalesOrderItem> orderItemArray = (List<SalesOrderItem>) orderItemCache.get(order.getId());
				customerOrderService.insertToItemDec(order, orderItemArray, productCraftsCache);
				// customerOrderService.setCustomerOaDate(customerOrder,
				// order.getConfirmDate());
			} catch (Exception e) {
				result.put("success", false);
				result.put("message", "订单" + order.getContractNo() + "导入分解出现异常，请查看相关工艺是否正确");
				e.printStackTrace();
				return;
			}

			Date bb = new Date();
			System.out.println("customerOrder/Item[" + order.getId() + "]获取工艺ID耗时：" + (bb.getTime() - aa.getTime())
					+ "豪秒");

		}

		Date b2 = new Date();
		System.out.println("customerOrder表总耗时：" + (b2.getTime() - b1.getTime()) / 1000 + "秒");

		Date b = new Date();
		System.out.println("总耗时：" + (b.getTime() - a.getTime()) / 1000 + "秒");

		result.put("success", true);
		result.put("message", "成功导入" + importNum + "个产品!");
	}

	@Override
	public SalesOrder getByContract(String contractNo, String orgCode) {
		return salesOrderDAO.getByContract(contractNo, orgCode);
	}

	@Override
	public List<SalesOrder> getSalesOrderByGW() {
		// TODO Auto-generated method stub
		return salesOrderDAO.getSalesOrderByGW();
	}
}
