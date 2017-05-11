package cc.oit.bsmes.pla.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.dao.MesClientDAO;
import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.ProcessSection;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.PropKeyConstants;
import cc.oit.bsmes.common.constants.WorkOrderOperateType;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.GlobalVariableClass;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.pla.dao.CustomerOrderItemProDecDAO;
import cc.oit.bsmes.pla.dao.FinishedProductDAO;
import cc.oit.bsmes.pla.dao.FinishedProductLogDAO;
import cc.oit.bsmes.pla.dao.OrderOADAO;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.FinishedProduct;
import cc.oit.bsmes.pla.model.FinishedProductLog;
import cc.oit.bsmes.pla.model.HighPriorityOrderItem;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderPlanService;
import cc.oit.bsmes.pla.service.HandScheduleService;
import cc.oit.bsmes.pla.service.HighPriorityOrderItemProDecService;
import cc.oit.bsmes.pla.service.HighPriorityOrderItemService;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pla.service.OrderProcessPRService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.model.ProductCraftsBz;
import cc.oit.bsmes.pro.model.ProductProcessBz;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProductCraftsBzService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.wip.dao.ReportDAO;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.dto.MethodReturnDto;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.WorkCusorderRelation;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.model.WorkOrderEquipRelation;
import cc.oit.bsmes.wip.service.SemiFinishedProductsService;
import cc.oit.bsmes.wip.service.WorkCusorderRelationService;
import cc.oit.bsmes.wip.service.WorkOrderEquipRelationService;
import cc.oit.bsmes.wip.service.WorkOrderOperateLogService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class HandScheduleServiceImpl implements HandScheduleService {

	@Resource
	private EquipListService equipListService;
	@Resource
	private ProductCraftsBzService productCraftsBzService;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private HighPriorityOrderItemService highPriorityOrderItemService;
	@Resource
	private HighPriorityOrderItemProDecService highPriorityOrderItemProDecService;
	@Resource
	private CustomerOrderPlanService customerOrderPlanService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private ResourceCache resourceCache;
	@Resource
	private TaskExecutor taskExecutor;// 线程池
	@Resource
	private WorkOrderEquipRelationService workOrderEquipRelationService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;
	@Resource
	private OrderOADAO orderDAO;
	@Resource
	private WorkOrderDAO workOrderDAO;
	@Resource
	private MesClientDAO mesClientDAO;
	@Resource
	private ReportDAO reportDAO;
	@Resource
	private CustomerOrderItemProDecDAO customerOrderItemProDecDAO;
	@Resource
	private WorkOrderOperateLogService workOrderOperateLogService;
	@Resource
	private SemiFinishedProductsService semiFinishedProductsService;
	@Resource
	private WorkCusorderRelationService workCusorderRelationService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private OrderProcessPRService orderProcessPRService;
	@Resource
	private FinishedProductDAO finishedProductDAO;
	@Resource
	private FinishedProductLogDAO finishedProductLogDAO;

	private static final int BUF_SIZE = 8192;

	/**
	 * @Title: onExportOutPlan
	 * @Description: TODO(导出外计划)
	 * @param: dataArray 订单数据
	 * @return: String 文件路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws WriteException
	 * @throws RowsExceededException
	 * @throws BiffException
	 */
	@Override
	public String putOutPlan2Excel(String columnArray, String dataIndexArray, String dataArray)
			throws FileNotFoundException, IOException, RowsExceededException, WriteException, BiffException,
			BiffException {
		// 1、获取excel文件
		String attachmentPath = null;
		try {
			attachmentPath = WebContextUtils.getPropValue(PropKeyConstants.ATTACHMENT_PATH);
		} catch (Exception e) {
			attachmentPath = "D:/";
		}
		attachmentPath = attachmentPath + "外计划.xls";
		File file = new File(attachmentPath);
		int rows = 0; // 写入行
		boolean exists = file.exists(); // 文件是否存在

		// 2、转化列名
		List<String> columns = new ArrayList<String>();
		for (Object object : JSON.parseArray(columnArray)) {
			columns.add((String) object);
		}

		// 3、转化列的index
		List<String> dataIndex = new ArrayList<String>();
		for (Object object : JSON.parseArray(dataIndexArray)) {
			dataIndex.add((String) object);
		}

		WritableWorkbook wwb = null;
		try {
			WritableSheet sheet = null;
			// 4、判断是否存在：创建/追加
			if (!exists) {
				wwb = Workbook.createWorkbook(new FileOutputStream(file));
				sheet = wwb.createSheet("订单列表", 0);
				// 4.1、创建添加列名
				for (int i = 0; i < columns.size(); i++) {
					sheet.addCell(new Label(i, rows, columns.get(i)));
				}
				rows++;
			} else { // 存在追加
				Workbook rwb = Workbook.getWorkbook(file);
				wwb = Workbook.createWorkbook(new File(attachmentPath), rwb);
				sheet = wwb.getSheet(0);
				rows = sheet.getRows();
			}

			// 5、数据为零直接返回
			JSONArray jsonArray = JSON.parseArray(dataArray);
			if (jsonArray.size() == 0) {
				wwb.write();
				wwb.close();
				return null;
			}

			// 6、循环添加每个cell
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jObject = (JSONObject) jsonArray.get(i);
				for (int j = 0; j < dataIndex.size(); j++) {
					sheet.addCell(new Label(j, i + rows, jObject.getString(dataIndex.get(j))));
				}
			}
			wwb.write();
		} finally {
			if (null != wwb) {
				wwb.close();
			}
		}
		return file.getAbsolutePath();
	}

	/**
	 * @Title: onExportOutPlan
	 * @Description: TODO(导出外计划)
	 * @param: outputStream 响应流
	 * @param: dataArray 导出数据列表
	 * @return: void
	 * @throws IOException
	 */
	@Override
	public void onExportOutPlan(OutputStream outputStream, File file) throws IOException {
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			bufferedOutputStream = new BufferedOutputStream(outputStream);
			final byte temp[] = new byte[BUF_SIZE];
			int readBytes = 0;
			while ((readBytes = bufferedInputStream.read(temp)) != -1) {
				bufferedOutputStream.write(temp, 0, readBytes);
			}
			bufferedOutputStream.flush();
		} finally {
			if (bufferedOutputStream != null) {
				bufferedOutputStream.close();
			}
			if (bufferedInputStream != null) {
				bufferedInputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	/**
	 * 保存排序并计算排程
	 * 
	 * @author DingXintao
	 * @param jsonText 编辑的排序对象
	 * @return MethodReturnDto
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Override
	public MethodReturnDto updateSeqAndCalculate(String jsonText) throws Exception {
		if (GlobalVariableClass.getInstance().isScheduleJobIsRun()) {
			return new MethodReturnDto(false, "计算任务正真进行中，请稍候再试...");
		}
		JSONArray jsonArray = JSON.parseArray(jsonText);
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			HighPriorityOrderItem orderItem = new HighPriorityOrderItem();
			orderItem.setId(jsonObject.getString("id"));
			Integer seq = jsonObject.getInteger("seq");
			orderItem.setSeq(seq == null ? null : seq + 1);
			if (StringUtils.isBlank(jsonObject.getString("highPriorityId"))) {
				highPriorityOrderItemService.insert(orderItem);
			} else {
				highPriorityOrderItemService.update(orderItem);
			}
		}

		taskExecutor.execute(new CalculateThread(SessionUtils.getUser(), resourceCache, false));

		GlobalVariableClass.getInstance().setScheduleJobIsRun(true);

		return new MethodReturnDto(true);
	}

	/**
	 * 重新计算排程
	 * 
	 * @author DingXintao
	 * @param jsonText 编辑的排序对象
	 * @return MethodReturnDto
	 */
	@Override
	public MethodReturnDto calculateAgain() throws Exception {
		if (GlobalVariableClass.getInstance().isScheduleJobIsRun()) {
			return new MethodReturnDto(false, "计算任务正真进行中，请稍候再试...");
		}

		// 提高性能，将分解提出来，未分解的分解，已分解的不操作

		taskExecutor.execute(new CalculateThread(SessionUtils.getUser(), resourceCache, true));

		GlobalVariableClass.getInstance().setScheduleJobIsRun(true);

		return new MethodReturnDto(true);
	}

	/**
	 * 内部类：
	 * */
	class CalculateThread extends Thread {
		private User user; //
		private ResourceCache resourceCache; //
		private boolean isCalculateAgain = false; // 重新计算

		public CalculateThread(User user, ResourceCache resourceCache, boolean isCalculateAgain) {
			this.user = user;
			this.resourceCache = resourceCache;
			this.isCalculateAgain = isCalculateAgain;
		}

		public void run() {
			try {
				SessionUtils.setUser(user); // 多线程复制sessionUser
				equipInfoService.initEquipLoad(user.getOrgCode());
				if (!isCalculateAgain) {
					customerOrderPlanService.calculatorOA(resourceCache, user.getOrgCode());
				} else {
					orderTaskService.generate(this.resourceCache, user.getOrgCode());
				}
				GlobalVariableClass.getInstance().setScheduleJobIsRun(false);
			} catch (Exception e) {
				GlobalVariableClass.getInstance().setScheduleJobIsRun(false);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查看生产单 - 调整生产单加工顺序 - 保存
	 * 
	 * @author DingXintao
	 * @param equipCode 设备编码
	 * @param updateSeq 生产单排序JSON:[WORKORDERID, WORKORDERID]
	 * @return MethodReturnDto
	 */
	public MethodReturnDto updateWOrkOrderSeq(String equipCode, String updateSeq) throws Exception {
		// List<WorkOrder> workOrderList = JSON.parseArray(updateSeq,
		// WorkOrder.class);
		// if (workOrderList != null && !workOrderList.isEmpty()) {
		// highPriorityOrderItemProDecService.deleteByEquipCode(workOrderList.get(0).getEquipCode());
		// updateSeq(workOrderList);
		// }
		JSONArray workIdArray = JSON.parseArray(updateSeq);
		for (int i = 0; i < workIdArray.size(); i++) {
			WorkOrderEquipRelation workOrderEquipRelation = new WorkOrderEquipRelation();
			workOrderEquipRelation.setWorkOrderId(workIdArray.getString(i));
			workOrderEquipRelation.setEquipCode(equipCode);
			List<WorkOrderEquipRelation> relationArray = workOrderEquipRelationService
					.findByObj(workOrderEquipRelation);
			if (!CollectionUtils.isEmpty(relationArray)) {
				workOrderEquipRelation = relationArray.get(0);
				workOrderEquipRelation.setSeq(new BigDecimal(i));
				workOrderEquipRelationService.update(workOrderEquipRelation);
			}
		}
		return new MethodReturnDto(true);
	}

	@Transactional(readOnly = false)
	public void updateSeq(List<WorkOrder> workOrderList) {
		if (workOrderList != null && !workOrderList.isEmpty()) {
			String equipCode = "";
			for (WorkOrder workOrder : workOrderList) {
				if (StringUtils.isBlank(equipCode)) {
					equipCode = workOrder.getEquipCode();
				}
				highPriorityOrderItemProDecService.insertSeqByWorkOrderNo(workOrder.getWorkOrderNo(), equipCode,
						workOrder.getSeq());
				customerOrderItemProDecService.updateFixEquipForWorkOrderSeq(equipCode, workOrder.getWorkOrderNo());
			}
		}
	}

	/**
	 * 设备调整：修改生产单的使用设备<br/>
	 * 1、更新生产单信息<br/>
	 * 2、删除生产单设备关联记录<br/>
	 * 3、添加新记录<br/>
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单编号
	 * @param equipNameArrayStr 设备名称集
	 * @param equipCodeArrayStr 设备编码集
	 * @return MethodReturnDto
	 * */
	@Override
	@Transactional(readOnly = false)
	public MethodReturnDto changeWorkEquipSub(String workOrderNo, String equipNameArrayStr, String equipCodeArrayStr) {
		String userCode = SessionUtils.getUser().getUserCode();
		WorkOrder workOrder = new WorkOrder();
		workOrder.setWorkOrderNo(workOrderNo);
		List<WorkOrder> workOrderArray = workOrderService.findByObj(workOrder);
		if (!CollectionUtils.isEmpty(workOrderArray)) {
			workOrder = workOrderArray.get(0);
			workOrder.setEquipCode(equipCodeArrayStr);
			workOrder.setEquipName(equipNameArrayStr);
			workOrderService.update(workOrder); // 更新生产单信息

			// 删除生产单关联设备信息
			workOrderEquipRelationService.deleteByWorkOrderNo(workOrderNo);

			// 添加新的生产单设备关系
			String[] equipCodeArray = equipCodeArrayStr.split(",");
			for (String equipCode : equipCodeArray) {
				WorkOrderEquipRelation workOrderEquipRelation = new WorkOrderEquipRelation();
				workOrderEquipRelation.setEquipCode(equipCode);
				workOrderEquipRelation.setWorkOrderId(workOrder.getId());
				workOrderEquipRelation.setCreateUserCode(userCode);
				workOrderEquipRelation.setModifyUserCode(userCode);
				workOrderEquipRelationService.insert(workOrderEquipRelation);
			}
		}
		return new MethodReturnDto(true);
	}

	/**
	 * 获取查询下拉框：合同号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<OrderOA> getContractNo(Map<String, Object> param) {
		return orderDAO.getContractNo(param);
	}

	/**
	 * 获取查询下拉框：单位
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<OrderOA> getCustomerCompany(Map<String, Object> param) {
		return orderDAO.getCustomerCompany(param);
	}

	/**
	 * 获取查询下拉框：经办人
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<OrderOA> getOperator(Map<String, Object> param) {
		return orderDAO.getOperator(param);
	}

	/**
	 * 获取查询下拉框：客户型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<OrderOA> getCustproductType(Map<String, Object> param) {
		return orderDAO.getCustproductType(param);
	}

	/**
	 * 获取查询下拉框：产品型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<OrderOA> getProductType(Map<String, Object> param) {
		return orderDAO.getProductType(param);
	}

	/**
	 * 获取查询下拉框：规格
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<OrderOA> getProductSpec(Map<String, Object> param) {
		return orderDAO.getProductSpec(param);
	}

	/**
	 * 获取查询下拉框：线芯结构
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<OrderOA> getWiresStructure(Map<String, Object> param) {
		return orderDAO.getWiresStructure(param);
	}

	/**
	 * 获取查询下拉框：线芯数
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<OrderOA> getnumberOfWires(Map<String, Object> param) {
		return orderDAO.getnumberOfWires(param);
	}

	/**
	 * 获取查询下拉框：截面
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<OrderOA> getSection(Map<String, Object> param) {
		return orderDAO.getSection(param);
	}

	/**
	 * 获取查询下拉框：状态
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<OrderOA> getstatus(Map<String, Object> param) {
		return orderDAO.getstatus(param);
	}

	/**
	 * [生产单]获取查询下拉框：合同号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<WorkOrder> getWorkOrderContractNo(Map<String, Object> param) {
		return workOrderDAO.getContractNo(param);
	}

	/**
	 * [生产单]获取查询下拉框：单位
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<WorkOrder> getWorkOrderCustomerCompany(Map<String, Object> param) {
		return workOrderDAO.getCustomerCompany(param);
	}

	/**
	 * [生产单]获取查询下拉框：经办人
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<WorkOrder> getWorkOrderOperator(Map<String, Object> param) {
		return workOrderDAO.getOperator(param);
	}

	/**
	 * [生产单]获取查询下拉框：客户型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<WorkOrder> getWorkOrderCustproductType(Map<String, Object> param) {
		return workOrderDAO.getCustproductType(param);
	}

	/**
	 * [生产单]获取查询下拉框：产品型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<WorkOrder> getWorkOrderProductType(Map<String, Object> param) {
		return workOrderDAO.getProductType(param);
	}

	/**
	 * [生产单]获取查询下拉框：产品规格
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<WorkOrder> getWorkOrderProductSpec(Map<String, Object> param) {
		return workOrderDAO.getProductSpec(param);
	}

	@Override
	public List<WorkOrder> getWorkOrderProcessName() {
		return workOrderDAO.getWorkOrderProcessName();
	}

	/**
	 * [生产单]获取查询下拉框：线芯结构
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	@Override
	public List<WorkOrder> getWorkOrderWiresStructure(Map<String, Object> param) {
		return workOrderDAO.getWiresStructure(param);
	}

	public List<MesClient> getProcessCode(Map<String, Object> param) {
		return mesClientDAO.getProcessCode(param);
	}

	public List<MesClient> getEquipInfo(Map<String, Object> param) {
		return mesClientDAO.getEquipInfo(param);
	}

	public List<MesClient> getTaskStatueInEquip(Map<String, Object> param) {
		return mesClientDAO.getTaskStatueInEquip(param);
	}

	/**
	 * 查询接收人信息
	 * 
	 * @author DingXintao
	 * @param section 工段
	 * */
	public List<WorkOrder> getReceiver(String section) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("Section", section);
		return workOrderDAO.getReceiver(param);
	}

	public List<CustomerOrderItemProDec> getSwitchHistory(Map<String, Object> param) {
		return customerOrderItemProDecDAO.getSwitchHistory(param);
	}

	public List<Report> getWorkOrderReportHistory(Map<String, Object> param) {
		return reportDAO.getWorkOrderReportHistory(param);
	}

	/**
	 * @Title mergeCustomerOrderItem
	 * @Description TODO(保存合并订单侯的生产单)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年4月6日 下午4:18:26
	 * @param docMakerUserCode 制单人
	 * @param receiverUserCode 接受人
	 * @param requireFinishDate 完成日期
	 * @param equipCodes 设备编码数组
	 * @param equipName 设备名称
	 * @param processName 工序名称
	 * @param processCode 工序编码
	 * @param userComment 备注
	 * @param specialReqSplit
	 * @param processJsonData 
	 *            工序Json对象，投入产出：转换对象CustomerOrderItemProDec、MaterialRequirementPlan保存
	 * @param orderTaskLengthJsonData 该订单产品该生产单所下发的长度
	 * @param preWorkOrderNo 上一道生产单的生产单号
	 * @param processesMergedArray 成缆工段生产单保存当前生产单所用工序的Json字符串
	 * @param workOrderSection 生产单所属工段 1:绝缘工段 2:成缆工段 3:护套工段
	 * @param nextSection 生产单下一个加工工段 1:绝缘工段 2:成缆工段 3:护套工段
	 * @param cusOrderItemIds 当前生产单中下发的所有客户生产订单明细IDs
	 * @param ids4FinishedJY 绝缘工段：已经完全下发所有工序的订单ID
	 * @param completeCusOrderItemIds 成缆工段：已经完全下完成缆工序的订单ID
	 * @param isDispatch 是否急件
	 * @param isHaved 是否陈线
	 * @param isAbroad 是否出口
	 * @param @return
	 * @return MethodReturnDto
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly = false)
	public MethodReturnDto mergeCustomerOrderItem(String docMakerUserCode, String receiverUserCode,
			String requireFinishDate, String equipCodes, String equipName, String processName, String processCode,
			String userComment, String specialReqSplit, String processJsonData, String orderTaskLengthJsonData,
			String preWorkOrderNo, String processesMergedArray, String workOrderSection, String nextSection,
			String cusOrderItemIds, String ids4FinishedJY, String completeCusOrderItemIds, String isDispatch,
			String isHaved, String isAbroad) {
		JSONArray inOutJsonArray = JSON.parseArray(processJsonData); // 投入产出JSON数组
		JSONArray orderTaskLengthJsonArray = JSON.parseArray(orderTaskLengthJsonData); // 投入产出JSON数组
		// JSONArray orderArray = JSON.parseArray(yuliangJson); // 订单余量更新对象
		String workOrderId = UUID.randomUUID().toString(); //
		String workOrderNo = DateUtils.convert(new Date(), DateUtils.DATE_TIMESTAMP_LONG_FORMAT);
		String userCode = SessionUtils.getUser().getUserCode();
		String orgCode = SessionUtils.getUser().getOrgCode();

		// 1、保存一条工单信息: 此处先封装一下
		WorkOrder workOrder = this.setParams2WorkOrder(workOrderId, workOrderNo, docMakerUserCode, receiverUserCode,
				requireFinishDate, equipCodes, equipName, processName, processCode, orgCode, userCode, userComment,
				specialReqSplit, preWorkOrderNo, processesMergedArray, workOrderSection, nextSection, cusOrderItemIds,
				completeCusOrderItemIds, isHaved, isDispatch, isAbroad);

		// 2、解析投入产出JSON数据，封装返回
		Map<String, List> mapCache = this.changeJson2UpdateObject(inOutJsonArray, workOrder, orderTaskLengthJsonArray);
		List<String> proDecIdList = mapCache.get("proDecIdList"); // 1、产出
		List<MaterialRequirementPlan> matList = mapCache.get("matList"); // 2、投入
		List<WorkCusorderRelation> relationList = mapCache.get("relationList"); // 3、生产单与订单关系
		List<WorkOrderEquipRelation> equipList = mapCache.get("equipList"); // 4、生产单与设备关系

		// 3、更新数据，新增或者修改，先后顺序不能倒，部分有主外键约束
		workOrderService.insert(workOrder); // 1、生产单
		workOrderOperateLogService.changeWorkOrderStatus(workOrderNo, "", workOrder.getStatus(), WorkOrderOperateType.INSERT, WorkOrderOperateType.INSERT.toString(), userCode); // 1、生产单状态变更记录
		materialRequirementPlanService.insert(matList); // 2、投入
		workCusorderRelationService.insert(relationList); // 3、生产单与订单关系
		orderTaskService.insertOrderTask(userCode, workOrderNo, proDecIdList);// 1.1、T_PLA_ORDER_TASK，关联PRO_DEC和WORK_ORDER
		workOrderEquipRelationService.insert(equipList); // 4、生产单与设备关系
		this.updatePreWorkOrder(workOrder, preWorkOrderNo); // 5、更新上一道生产单的信息：包括已经下发的客户订单id[auditedCusOrderItemIds]、下一道工段[nextSection]
		this.updateCustomerOrderItem4JY(ids4FinishedJY);
		semiFinishedProductsService.calculateWorkOrderCost(workOrderNo);
		return new MethodReturnDto(true, "成功！", "{workOrderNo: \"" + workOrderNo + "\"}");
	}

	/**
	 * @Title changeJson2UpdateObject
	 * @Description TODO(将投入产出的JSON字符串转变成封装对象)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月23日 下午3:41:28
	 * @param @param inOutJsonArray 投入产出的JSON数组对象
	 * @param @param workOrder 生产单对象
	 * @param @param orderTaskLengthJsonArray 生产单ID:长度
	 * @return Map<String,List>
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, List> changeJson2UpdateObject(JSONArray inOutJsonArray, WorkOrder workOrder,
			JSONArray orderTaskLengthJsonArray) {
		Map<String, List> mapCache = new HashMap<String, List>();
		Double orderLength = 0.0; // 总长度
		String processId = ""; // 随机取一个工序id，用来终端显示工艺质量信息
		String matCode = ""; // 随机取一个无聊编码
		List<String> proDecIdList = new ArrayList<String>(); // 1、产出信息
		List<MaterialRequirementPlan> matList = new ArrayList<MaterialRequirementPlan>(); // 2、投入物料信息
		List<WorkCusorderRelation> relationList = new ArrayList<WorkCusorderRelation>(); // 3、生产单与订单关系表
		List<WorkOrderEquipRelation> equipList = new ArrayList<WorkOrderEquipRelation>(); // 4、生产单与设备关系表
		Map<String, BigDecimal> materialAmount = new HashMap<String, BigDecimal>(); // 5、原材料使用量：更新库存

		for (Object object : inOutJsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			String inOrOut = jsonObject.getString("inOrOut"); // 判断投入还是产出
			Double yuliang = jsonObject.getDouble("yuliang"); // 放置的余量
			if (InOrOut.OUT.name().equalsIgnoreCase(inOrOut)) {
				// 1、产出
				CustomerOrderItemProDec customerOrderItemProDec = JSONObject.toJavaObject(jsonObject,
						CustomerOrderItemProDec.class);
				customerOrderItemProDec.setOutAttrDesc(this.getAttrDesc(jsonObject, InOrOut.OUT));
				customerOrderItemProDec.setOrgCode(workOrder.getOrgCode());
				customerOrderItemProDec.setWorkOrderNo(workOrder.getWorkOrderNo());
				customerOrderItemProDec.setStatus(ProductOrderStatus.TO_DO);
				if (null != yuliang) {
					customerOrderItemProDec
							.setUnFinishedLength(customerOrderItemProDec.getUnFinishedLength() + yuliang);
				}
				customerOrderItemProDecService.insert(customerOrderItemProDec);
				proDecIdList.add(customerOrderItemProDec.getId());

				orderLength += customerOrderItemProDec.getUnFinishedLength();
				processId = customerOrderItemProDec.getProcessId();
				matCode = customerOrderItemProDec.getHalfProductCode();
			} else {
				// 2、投入
				MaterialRequirementPlan materialRequirementPlan = JSONObject.toJavaObject(jsonObject,
						MaterialRequirementPlan.class);
				materialRequirementPlan.setWorkOrderId(workOrder.getId());
				materialRequirementPlan.setPlanDate(new Date());
				materialRequirementPlan.setStatus(MaterialStatus.MAT_UN_DOWN);
				materialRequirementPlan.setInAttrDesc(this.getAttrDesc(jsonObject, InOrOut.IN));
				if (null != yuliang) {
					materialRequirementPlan.setUnFinishedLength(String.valueOf(Double.valueOf(materialRequirementPlan
							.getUnFinishedLength()) + yuliang));
				}
				matList.add(materialRequirementPlan);

				// 5、原材料使用量：更新库存
				if (materialRequirementPlan.getMatType() == MatType.MATERIALS) { // 如果是原材料，更新原HH材料库存的对象
					BigDecimal quantityTotal = materialAmount.get(materialRequirementPlan.getMatCode());
					BigDecimal quantity = BigDecimal.valueOf(materialRequirementPlan.getQuantity()).multiply(
							BigDecimal.valueOf(Double.parseDouble(materialRequirementPlan.getUnFinishedLength())));
					if (null == quantityTotal) {
						materialAmount.put(materialRequirementPlan.getMatCode(), quantity);
					} else {
						materialAmount.put(materialRequirementPlan.getMatCode(), quantityTotal.add(quantity));
					}
				}
			}
		}

		// 3、生产单与订单的关系
		for (Object object : orderTaskLengthJsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			WorkCusorderRelation workCusorderRelation = new WorkCusorderRelation();
			workCusorderRelation.setWorkOrderId(workOrder.getId());
			workCusorderRelation.setWorkOrderNo(workOrder.getWorkOrderNo());
			workCusorderRelation.setProcessCode(workOrder.getProcessCode());
			workCusorderRelation.setCusOrderItemId(jsonObject.getString("cusOrderItemId"));
			workCusorderRelation.setOrderTaskLength(jsonObject.getString("orderTaskLength"));
			workCusorderRelation.setSplitLengthRole(jsonObject.getString("splitLengthRole"));
			relationList.add(workCusorderRelation);
		}

		// 4、生产单与设备的关系
		for (String equipCode : workOrder.getEquipCode().split(",")) {
			WorkOrderEquipRelation workOrderEquipRelation = new WorkOrderEquipRelation();
			workOrderEquipRelation.setEquipCode(equipCode);
			workOrderEquipRelation.setWorkOrderId(workOrder.getId());
			workOrderEquipRelation.setCreateUserCode(workOrder.getCreateUserCode());
			workOrderEquipRelation.setModifyUserCode(workOrder.getModifyUserCode());
			equipList.add(workOrderEquipRelation);
		}

		// 5、原材料使用量：更新库存
		Iterator<String> matCodes = materialAmount.keySet().iterator();
		while (matCodes.hasNext()) {
			String materialCode = matCodes.next();
			BigDecimal quantity = materialAmount.get(materialCode);

			List<Inventory> inventoryArray = inventoryService.findByMatCode(materialCode);
			for (Inventory inventory : inventoryArray) {
				if (BigDecimal.valueOf(inventory.getQuantity()).compareTo(quantity) >= 0) {
					inventory.setQuantity(BigDecimal.valueOf(inventory.getQuantity()).subtract(quantity).doubleValue());
					inventory.setLockedQuantity(BigDecimal.valueOf(inventory.getLockedQuantity()).add(quantity)
							.doubleValue());
					inventoryService.update(inventory);
					quantity = BigDecimal.ZERO;
					break;
				} else {
					inventory.setLockedQuantity(inventory.getLockedQuantity() + inventory.getQuantity());
					inventory.setQuantity(0d);
					inventoryService.update(inventory);
					quantity = quantity.subtract(BigDecimal.valueOf(inventory.getQuantity()));
				}
			}

			if (quantity.compareTo(BigDecimal.ZERO) > 0) {
				System.out.println(materialCode + "--库存不足！！！");
			}
		}

		workOrder.setOrderLength(orderLength);
		workOrder.setProcessId(processId);
		workOrder.setHalfProductCode(matCode);
		
		mapCache.put("proDecIdList", proDecIdList);
		mapCache.put("matList", matList);
		mapCache.put("relationList", relationList);
		mapCache.put("equipList", equipList);
		return mapCache;
	}

	/**
	 * 前台属性放入workOrder对象，直接set不用反射提高速度
	 * 
	 * @author DingXintao
	 * @param workOrder 生产单对象
	 * @param workOrderId 主键id
	 * @param workOrderNo 生产单号
	 * @param docMakerUserCode 制单人
	 * @param receiverUserCode 接受人
	 * @param requireFinishDate 要求完成日期
	 * @param equipCodes 设备编码
	 * @param equipName 设备名称
	 * @param processName 工序名称
	 * @param processCode 工序编码
	 * @param orgCode 组织code
	 * @param userCode 用户code
	 * @param userComment 备注
	 * @param specialReqSplit 特殊分盘要求
	 * @param preWorkOrderNo 上一道生产单号
	 * @param processesMergedArray 下单的工序JSON描述
	 * @param workOrderSection 生产单所属工段
	 * @param nextSection 下一道工段
	 * @param cusOrderItemIds 关联的订单产品id
	 * @param completeCusOrderItemIds 下单完成的产品id：成缆特殊情况使用
	 * @param isHaved 是否陈线
	 * @param isDispatch 是否急件
	 * @param isAbroad 是否出口
	 * @return WorkOrder
	 * */
	private WorkOrder setParams2WorkOrder(String workOrderId, String workOrderNo, String docMakerUserCode,
			String receiverUserCode, String requireFinishDate, String equipCodes, String equipName, String processName,
			String processCode, String orgCode, String userCode, String userComment, String specialReqSplit,
			String preWorkOrderNo, String processesMergedArray, String workOrderSection, String nextSection,
			String cusOrderItemIds, String completeCusOrderItemIds, String isHaved, String isDispatch, String isAbroad) {
		WorkOrder workOrder = new WorkOrder();
		workOrder.setId(workOrderId);
		workOrder.setWorkOrderNo(workOrderNo);
		workOrder.setDocMakerUserCode(docMakerUserCode);
		workOrder.setReceiverUserCode(receiverUserCode);
		workOrder.setReleaseDate(new Date());
		if (StringUtils.isNotEmpty(requireFinishDate)) {
			workOrder.setRequireFinishDate(DateUtils.strToDate((requireFinishDate)));
		}
		workOrder.setEquipName(equipName);
		workOrder.setEquipCode(equipCodes);

		workOrder.setProcessName(processName);
		workOrder.setProcessCode(processCode);
		workOrder.setOrgCode(orgCode);
		workOrder.setCreateUserCode(userCode);
		workOrder.setModifyUserCode(userCode);
		workOrder.setPreStartTime(new Date());
		workOrder.setPreEndTime(new Date());
		workOrder.setUserComment(userComment);
		workOrder.setSpecialReqSplit(specialReqSplit);
		workOrder.setProcessGroupRespool(preWorkOrderNo);
		workOrder.setProcessesMerged(processesMergedArray);
		workOrder.setWorkOrderSection(workOrderSection);
		workOrder.setNextSection(nextSection);
		workOrder.setCusOrderItemIds(cusOrderItemIds);
		if(workOrderSection.equals(ProcessSection.HT.getOrder())){ // 护套工段，已经完成的当已经下发处理，因为整个流程结束了！方便前台人性化显示。
			workOrder.setAuditedCusOrderItemIds(completeCusOrderItemIds); 
		}else{ // 否则(基本就是成缆)保存到完成字段
			workOrder.setCompleteCusOrderItemIds(completeCusOrderItemIds);
		}
		workOrder.setStatus(WorkOrderStatus.TO_AUDIT);
		workOrder.setIsOldLine("true".equals(isHaved));
		workOrder.setPercent("true".equals(isHaved)?1d:0d); // 陈线直接完成百分百
		workOrder.setIsDispatch("true".equals(isDispatch));
		workOrder.setIsAbroad("true".equals(isAbroad));
		workOrder.setMatStatus(MaterialStatus.MAT_UN_DOWN);
		return workOrder;
	}

	/**
	 * @Title updatePreWorkOrder
	 * @Description 
	 *              TODO(更新上一道生产单的信息：包括已经下发的客户订单id[auditedCusOrderItemIds]、下一道工段[
	 *              nextSection])
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年4月6日 下午4:32:51
	 * @param workOrder 此次下发的生产单对象
	 * @param preWorkOrderNo 上一道生产单生产单号
	 * @return void
	 * @throws
	 */
	private void updatePreWorkOrder(WorkOrder workOrder, String preWorkOrderNo) {
		WorkOrder preworkOrder = workOrderService.getByWorkOrderNO(preWorkOrderNo);
		if (null != preworkOrder) {
			String cusOrderItemIds = workOrder.getCusOrderItemIds();
			String auditedCusOrderItemIds = preworkOrder.getAuditedCusOrderItemIds();
			// 更新auditedCusOrderItemIds
			preworkOrder.setAuditedCusOrderItemIds(StringUtils.isEmpty(auditedCusOrderItemIds) ? cusOrderItemIds
					: (auditedCusOrderItemIds + "," + cusOrderItemIds));

			String nextSection = this.getPreNextSection(preworkOrder);
			// 更新nextSection
			preworkOrder.setNextSection(nextSection);
			workOrderService.update(preworkOrder);
		}
	}

	/**
	 * @Title updateCustomerOrderItem4JY
	 * @Description TODO(绝缘完全下发了工序，更新已经完全下发状态为true)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年4月6日 下午4:26:39
	 * @param ids4FinishedJY 绝缘工段：已经完全下发所有工序的订单ID
	 * @return void
	 * @throws
	 */
	private void updateCustomerOrderItem4JY(String ids4FinishedJY) {
		if (StringUtils.isNotEmpty(ids4FinishedJY)) {
			for (String id : ids4FinishedJY.split(",")) {
				CustomerOrderItem customerOrderItem = customerOrderItemService.getById(id);
				customerOrderItem.setFinishJy(true);
				customerOrderItemService.update(customerOrderItem);
			}
		}
	}

	/**
	 * @Title getPreNextSection
	 * @Description TODO(nextSection 计算上一道生产单的下一个工段)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年4月6日 下午4:33:20
	 * @param preworkOrder 上一道生产单：用来查询直线下级生产单
	 * @return String
	 * @throws
	 */
	private String getPreNextSection(WorkOrder preworkOrder) {
		String nextSection = preworkOrder.getNextSection(); // 原来的
		Set<String> all = this.string2Set(preworkOrder.getCusOrderItemIds()); // 所有订单ID
		Set<String> audited = this.string2Set(preworkOrder.getAuditedCusOrderItemIds()); // 已经下发饿订单ID
		Set<String> completed = this.string2Set(preworkOrder.getCompleteCusOrderItemIds()); // 已经完成工段的订单ID
		// 此处逻辑稍微绕
		// 1、if(全部id长度[all] ＝ 下发完的id长度[audited] || 下发和完成的是一样的)，不用考虑，直接为: 0(完成)；
		// 2、else if(nextSection 不含有,号),直接返回本身
		// 2、else if(成缆完成的[completed] 全部in [audited])，说明在下一道(护套)里的下完了，把它还到成缆，即nextSection＝workOrderSection；
		// 3、else if(去除了[all]里的[completed]，全部in [audited])，说明成缆本工段下完了，更新为护套所在的，即nextSection＝(nextSection+',').replace(workOrderSection+',', '')
		// 4、否则本身
		if (all.size() == audited.size() || this.sameSet(all, audited)) {
			nextSection = "0";
		}else if(nextSection.indexOf(",") == -1){
			// 本身
		}else if(this.containSet(audited, completed)){
			nextSection = preworkOrder.getWorkOrderSection();
		}else if(this.containSet(audited, this.removeSet(all, completed))){
			nextSection = (nextSection+",").replace(preworkOrder.getWorkOrderSection()+",", "");
			if(",".equals(nextSection.substring(nextSection.length()-1))){ // 最后一个是不是逗号，逗号去除: 肯定有逗号，因为上一行加了；
				nextSection = nextSection.substring(0, nextSection.length()-1);
			}
		}
		return nextSection;
	}

	/**
	 * 将数组过滤剔除一部分获取剩下的
	 * */
	private Set<String> string2Set(String str) {
		Set<String> set = new HashSet<String>();
		if (StringUtils.isNotEmpty(str)) {
			for (String s : str.split(",")) {
				set.add(s);
			}
		}
		return set;
	}

	/**
	 * 将数组过滤剔除一部分获取剩下的
	 * */
	private Set<String> removeSet(Set<String> setParent, Set<String> setDelete) {
		if (null == setParent) {
			return new HashSet<String>();
		}
		if (null == setDelete) {
			return setParent;
		}
		Set<String> left = new HashSet<String>();
		for (String s : setParent) {
			if (!setDelete.contains(s)) {
				left.add(s);
			}
		}
		return left;
	}

	/**
	 * 判断set数组是包含另一个数组
	 * */
	private boolean containSet(Set<String> setParent, Set<String> setSon) {
		if (null == setParent || null == setSon) {
			return false;
		}
		for (String s : setSon) {
			if (!setParent.contains(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断set数组是否完全一样:长度，内容
	 * */
	private boolean sameSet(Set<String> setA, Set<String> setB) {
		if (null == setA || null == setB) {
			return false;
		}
		if (setA.size() != setB.size()) {
			return false;
		}
		for (String s : setA) {
			if (!setB.contains(s)) {
				return false;
			}
		}
		return true;
	}

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
		outAttrMap.put("wireCoil", "收线盘具");
		outAttrMap.put("splitLengthRoleWithYuliang", "分盘要求带余量");
		outAttrMap.put("splitLengthRole", "分盘要求");
		outAttrMap.put("color", "颜色");

		inAttrMap.put("kuandu", "宽度");
		inAttrMap.put("houdu", "厚度");
		inAttrMap.put("caizhi", "材质");
		inAttrMap.put("chicun", "尺寸");
		inAttrMap.put("guige", "规格");
		inAttrMap.put("dansizhijing", "单丝直径");
		inAttrMap.put("disk", "库位");
		inAttrMap.put("wireCoil", "盘具");
		inAttrMap.put("color", "颜色");
	}

	/**
	 * 获取产出的
	 * 
	 * */
	private String getAttrDesc(JSONObject jsonObject, InOrOut inout) {
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
				tmp = (String) jsonObject.getString(key);
				if (StringUtils.isNotEmpty(tmp)) {
					json.put(key, tmp);
				}
			}
		}
		// if(json.isEmpty()){
		// return "";
		// }
		return json.toJSONString();
	}

	/**
	 * 内部类：实例化工艺的线程
	 * */
	class InstanceCraftsThread extends Thread {
		private User user; //
		private String craftsId; // 工艺ID
		private String craftsCname; // 工艺别名
		private ProductCraftsBz productCraftsBz; // 标准工艺对象
		private List<ProductProcessBz> editProcessBzArray; // 编辑的工序列表
		@Resource
		private ProductCraftsService productCraftsService;

		public InstanceCraftsThread(User user, String craftsId, String craftsCname, ProductCraftsBz productCraftsBz,
				List<ProductProcessBz> editProcessBzArray) {
			this.user = user;
			this.craftsId = craftsId;
			this.craftsCname = craftsCname;
			this.productCraftsBz = productCraftsBz;
			this.editProcessBzArray = editProcessBzArray;

		}

		public void run() {
			try {
				if (this.productCraftsService == null) {
					this.productCraftsService = (ProductCraftsService) ContextUtils.getBean(ProductCraftsService.class);
				}

				SessionUtils.setUser(user); // 多线程复制sessionUser
				this.productCraftsService.instanceCrafts(craftsId, craftsCname, productCraftsBz, editProcessBzArray,
						false); // 实例化工艺

				StaticDataCache.init(); // 缓存重新初始化
				GlobalVariableClass.getInstance().setInstanceJobIsRun(false);
			} catch (Exception e) {
				GlobalVariableClass.getInstance().setInstanceJobIsRun(false);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 排程前校验：产品是否已经下发
	 * 
	 * @param orderItemIdArray 校验的订单ID
	 * @param isyunmu 是否是云母工序
	 * */
	public MethodReturnDto hasAuditOrder(String orderItemIdArrayStr, boolean isyunmu) {
		String[] orderItemIdArray = orderItemIdArrayStr.split(",");
		StringBuffer message = new StringBuffer();
		Map<String, Object> reMap = new HashMap<String, Object>();
		for (String orderItemId : orderItemIdArray) {
			
			
			Map<String, Object> result = customerOrderItemService.validateHasAuditOrder(orderItemId);
			Double contractlength = ((BigDecimal) result.get("CONTRACTLENGTH")).doubleValue();

			Double hasyunmu = ((BigDecimal) result.get("HASYUNMU")).doubleValue();
			Double haszhx = ((BigDecimal) result.get("HASZHX")).doubleValue();

			Double ymCount = ((BigDecimal) result.get("YMCOUNT")).doubleValue();
			Double jcCount = ((BigDecimal) result.get("JCCOUNT")).doubleValue();
			Double hhCount = ((BigDecimal) result.get("HHCOUNT")).doubleValue();
			Double zhxCount = ((BigDecimal) result.get("ZHXCOUNT")).doubleValue();

			if (StringUtils.isNotEmpty((String) result.get("HASCRAFTS"))) {
				this.addFrontMessage(message, result);
				message.append((String) result.get("HASCRAFTS") + "<br/>");
			}
			if (isyunmu) {
				if (hasyunmu == 0) {
					this.addFrontMessage(message, result);
					message.append("没有云母绕包工序<br/>");
				} else if (ymCount >= contractlength) {
					this.addFrontMessage(message, result);
					message.append("云母绕包工序已经下发<br/>");
				}
			} else {
				if (jcCount >= contractlength && hhCount >= contractlength
						&& (haszhx == 0 || (haszhx > 0 && zhxCount >= contractlength))) {
					this.addFrontMessage(message, result);
					message.append("已全部下发<br/>");
				} else if (hasyunmu > 0 && ymCount == 0) {
					this.addFrontMessage(message, result);
					message.append("云母绕包工序没有下发<br/>");
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("has_ymd_process", hasyunmu);
			map.put("has_zhx_process", haszhx);
			map.put("wrapping_ymd_length", ymCount);
			map.put("Extrusion-Single_length", jcCount);
			map.put("Respool_length", hhCount);
			map.put("Steam-Line_length", zhxCount);
			map.put("wrapping_ymd", (String) result.get("YMWONO"));
			map.put("Extrusion-Single", (String) result.get("JCWONO"));
			map.put("Respool", (String) result.get("HHWONO"));
			reMap.put(orderItemId, map);
		}
		if (StringUtils.isNotEmpty(message.toString())) {
			return new MethodReturnDto(false, message.toString());
		}
		return new MethodReturnDto(true, "", reMap);
	}

	/**
	 * 添加提示消息内容
	 * 
	 * @param message 提示消息对象
	 * @param result 查询数据
	 * */
	private void addFrontMessage(StringBuffer message, Map<String, Object> result) {
		String contract = ((String) result.get("CONTRACTNO")).replaceAll("[a-zA-Z]+$", "");
		contract = contract.length() > 5 ? contract.substring(contract.length() - 5) : contract;
		message.append("[").append(contract);
		if (StringUtils.isNotEmpty((String) result.get("OPERATOR"))) {
			message.append("(").append((String) result.get("OPERATOR")).append(")");
		}
		message.append("-").append(result.get("CUSTPRODUCTTYPE")).append(" ").append(result.get("CUSTPRODUCTSPEC"))
				.append("-").append(result.get("CONTRACTLENGTH")).append("]");
	}

	public void insertNewColorData(Map<String, Object> param) {
		workOrderDAO.insertNewColorData(param);
	}

	/**
	 * @Title: updateIsReport
	 * @Description: TODO(更新订单的特殊状态: 0:厂外计划;1:计划已报;2:手工单;3:库存生产)
	 * @author: DinXintao
	 * @version V1.0
	 * @date: 2016年3月17日 上午9:44:43
	 * @param: ids 订单ID(T_PLA_CUSTOMER_ORDER_ITEM)
	 * @param: specialFlag 0:厂外计划;1:计划已报;2:手工单;3:库存生产
	 * @param: finished 是否完成订单状态
	 * @return: MethodReturnDto
	 * @throws
	 */
	@Override
	@Transactional(readOnly = false)
	public MethodReturnDto updateSpecialFlag(String ids, String specialFlag, Boolean finished) {
		User user = SessionUtils.getUser();
		for (String id : ids.split(",")) {
			// 1、更新订单
			CustomerOrderItem customerOrderItem = customerOrderItemService.getById(id);
			customerOrderItem.setSpecialFlag(specialFlag);
			customerOrderItem.setSpecialOperateTime(new Date());
//			customerOrderItem.setFinishedProduct(useLength);
			customerOrderItemService.update(customerOrderItem);
			// 2、更新生产单完成状态
			if(null != finished && finished){
				customerOrderItemService.finishedOrderItem(id, user.getUserCode());
			}
		}
		return new MethodReturnDto(true);
	}
	/**
	 * 导入成品现货
	 */
	@Override
	public void importFinishedProduct(Sheet sheet, JSONObject result) {
		Row row1 = sheet.getRow(0);
		//获取最大行
		int maxRow = sheet.getLastRowNum()+1;
		//去除读取excel中大数字时，科学计数法
		DecimalFormat df = new DecimalFormat("0");
		if(row1.getCell(0).getStringCellValue().equals("编号")){
			for(int i=1;i<maxRow;i++){
				FinishedProduct finishedProduct = new FinishedProduct();
				Row row = sheet.getRow(i);
				//型号，规格，长度不能为空
				if(null == row.getCell(1) || null == row.getCell(2) || null == row.getCell(3)){
					continue;
				}
				finishedProduct.setModel(row.getCell(1).getStringCellValue());
				if(row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC){
					finishedProduct.setSpec(df.format(row.getCell(2).getNumericCellValue()));
				}else{
					finishedProduct.setSpec(row.getCell(2).getStringCellValue());
				}
				//看看是否有型号规格相同的就覆盖掉
				List<FinishedProduct> list = finishedProductDAO.get(finishedProduct);
				if(list != null){
					for(FinishedProduct fp: list){
						fp.setStatus("0");
						finishedProductDAO.update(fp);
					}
				}
				//编号
				if(null != row.getCell(0)){
					if(row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC){
						finishedProduct.setNum(df.format(row.getCell(0).getNumericCellValue()));
					}else{
						finishedProduct.setNum(row.getCell(0).getStringCellValue()+"");
					}
				}
				if(row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC){
					finishedProduct.setLength(row.getCell(3).getNumericCellValue());
				}else{
					finishedProduct.setLength(Double.parseDouble(row.getCell(3).getStringCellValue()));
				}
				if(null != row.getCell(4)){
					if(row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC){
						finishedProduct.setDish(df.format(row.getCell(4).getNumericCellValue()));
					}else{
						finishedProduct.setDish(row.getCell(4).getStringCellValue());
					}
				}
				if(null != row.getCell(5)){
					if(row.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC){
						finishedProduct.setQualifying(df.format(row.getCell(5).getNumericCellValue()));
					}else{
						finishedProduct.setQualifying(row.getCell(5).getStringCellValue());
					}
				}
				finishedProduct.setStatus("2");
				finishedProductDAO.insert(finishedProduct);
			}
		}else{
			for(int i=1;i<maxRow;i++){
				FinishedProduct finishedProduct = new FinishedProduct();
				Row row = sheet.getRow(i);
				//型号，规格，长度不能为空
				if(null == row.getCell(2) || null == row.getCell(3) || null == row.getCell(4)){
					continue;
				}
				//型号
				if(row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC){
					finishedProduct.setModel(df.format(row.getCell(2).getNumericCellValue()));
				}else{
					finishedProduct.setModel(row.getCell(2).getStringCellValue());
				}
				//规格
				if(row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC){
					finishedProduct.setSpec(df.format(row.getCell(3).getNumericCellValue()));
				}else{
					finishedProduct.setSpec(row.getCell(3).getStringCellValue());
				}
				//看看是否有型号规格相同的就覆盖掉
				List<FinishedProduct> list = finishedProductDAO.get(finishedProduct);
				if(list != null){
					for(FinishedProduct fp: list){
						fp.setStatus("0");
						finishedProductDAO.update(fp);
					}
				}
				//序号
				if(null != row.getCell(0)){
					if(row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC){
						finishedProduct.setSerialNum(df.format(row.getCell(0).getNumericCellValue()));
					}else{
						finishedProduct.setSerialNum(row.getCell(0).getStringCellValue());
					}
				}
				//编号
				if(null != row.getCell(1)){
					if(row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC){
						finishedProduct.setNum(df.format(row.getCell(1).getNumericCellValue()));
					}else{
						finishedProduct.setNum(row.getCell(1).getStringCellValue());
					}
				}
				//长度
				if(row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC){
					finishedProduct.setLength(row.getCell(4).getNumericCellValue());
				}else{
					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					String str = row.getCell(4).getStringCellValue();
					//1,约200
					if(str.contains("约")){
						finishedProduct.setLength(Double.parseDouble(str.substring(1)));
					}
					//2,230+89
					if(str.contains("+")){
						String[] s =  str.split("\\+");
						finishedProduct.setLength(Double.parseDouble(s[0])+Double.parseDouble(s[1]));
					}
					//3,539FT
					if(str.contains("FT")){
						finishedProduct.setLength(Double.parseDouble(str.substring(0,str.length()-2)));
					}
				}
				//盘具
				if(null != row.getCell(5)){
					if(row.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC){
						finishedProduct.setDish(df.format(row.getCell(5).getNumericCellValue()));
					}else{
						finishedProduct.setDish(row.getCell(5).getStringCellValue());
					}
				}
				//区域
				if(null != row.getCell(6)){
					if(row.getCell(6).getCellType() == Cell.CELL_TYPE_NUMERIC){
						finishedProduct.setRegion(df.format(row.getCell(6).getNumericCellValue()));
					}else{
						finishedProduct.setRegion(row.getCell(6).getStringCellValue());
					}
				}
				//备注
				if(null != row.getCell(7)){
					finishedProduct.setRemarks(row.getCell(7).getStringCellValue());
				}
				if(null != row.getCell(8)){
					if(finishedProduct.getRemarks() != null){
						finishedProduct.setRemarks(row.getCell(7).getStringCellValue()+","+row.getCell(8).getStringCellValue());
					}else{
						finishedProduct.setRemarks(row.getCell(8).getStringCellValue());
					}
				}
				finishedProduct.setStatus("2");
				finishedProductDAO.insert(finishedProduct);
			}
		}
		result.put("success", true);
		result.put("message", "导入成品现货成功!");
	}

	@Override
	public List<FinishedProduct> listFinishedProduct(Integer start,
			Integer limit,FinishedProduct findParams) {
		// TODO Auto-generated method stub
		if (start != null && limit != null) {
		    SqlInterceptor.setRowBounds(new RowBounds(start, limit));
        }
		return finishedProductDAO.listFinishedProduct(findParams);
	}

	@Override
	public Integer countFinishedProduct(FinishedProduct findParams) {
		// TODO Auto-generated method stub
		return finishedProductDAO.countFinishedProduct(findParams);
	}

	@Override
	public List<FinishedProduct> getAllModelORSpec(String type,String query) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		if(query == "" || query == null){
			map.put("query", "");
		}else{
			map.put("query", "%"+query.toUpperCase()+"%");
		}
		if("model".equals(type)){
			return finishedProductDAO.getAllModel(map);
		}else{
			return finishedProductDAO.getAllSpec(map);
		}
	}

	@Override
	public void updateFinishedProduct(String id,String uselength, String salesOrderItemId,
			JSONObject result) {
		FinishedProduct finishedProduct = finishedProductDAO.getById(id);
		FinishedProductLog finishedProductLog = new FinishedProductLog();
		double usedLength = 0;
		if(finishedProduct.getUsedLength() != null){
			usedLength = finishedProduct.getUsedLength();
		}
		finishedProduct.setUsedLength(Double.parseDouble(uselength)+usedLength);
		if(finishedProduct.getLength()-Double.parseDouble(uselength)>0){
			finishedProduct.setLength(finishedProduct.getLength()-Double.parseDouble(uselength));
		}else{
			finishedProduct.setLength(0.0);
			finishedProduct.setStatus("1");
		}
		finishedProductDAO.update(finishedProduct);
		finishedProductLog.setOrderItemId(salesOrderItemId);
		finishedProductLog.setFinishedProductId(id);
		finishedProductLog.setLength(Double.parseDouble(uselength));
		finishedProductLogDAO.insert(finishedProductLog); 
		result.put("success", true);
	}

	@Override
	public List<FinishedProduct> getFinishedProductById(String salesOrderItemId) {
		// TODO Auto-generated method stub
		return finishedProductDAO.getFinishedProductById(salesOrderItemId);
	}

}