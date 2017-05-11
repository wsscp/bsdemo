package cc.oit.bsmes.wip.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.CamelCaseUtils;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.inv.model.Location;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.inv.service.LocationService;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.dao.ProductProcessWipDAO;
import cc.oit.bsmes.pro.service.ProcessQcValueService;
import cc.oit.bsmes.pro.service.ProcessQcWipService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.dao.ReportDAO;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;
import cc.oit.bsmes.wip.model.OnoffRecord;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.ReportTask;
import cc.oit.bsmes.wip.model.ReportUser;
import cc.oit.bsmes.wip.model.Section;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.OnoffRecordService;
import cc.oit.bsmes.wip.service.ReportService;
import cc.oit.bsmes.wip.service.ReportTaskService;
import cc.oit.bsmes.wip.service.ReportUserService;
import cc.oit.bsmes.wip.service.SectionService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 处理报工
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-2-20 下午3:44:31
 */
@Service
public class ReportServiceImpl extends BaseServiceImpl<Report> implements ReportService {

	@Resource
	private ReportDAO reportDAO;
	@Resource
	private ReportTaskService reportTaskService;
	@Resource
	private ProductProcessWipDAO productProcessWipDAO;
	@Resource
	private SectionService sectionService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private ProcessQcWipService processQcWipService;
	@Resource
	private LocationService locationService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ReportUserService reportUserService;
	@Resource
	private OnoffRecordService onoffRecordService;
	@Resource
	private ProcessQcValueService processQcValueService;

	private static String getSerialNum(String workOrderNo, int coilNum) {
		if (coilNum < 10) {
			return workOrderNo + "000" + coilNum;
		} else if (coilNum < 100) {
			return workOrderNo + "00" + coilNum;
		} else if (coilNum < 1000) {
			return workOrderNo + "0" + coilNum;
		} else {
			return workOrderNo + coilNum;
		}
	}

	/**
	 * <p>
	 * 员工姓名,工号查询报工记录
	 * </p>
	 * 
	 * @param findParam
	 * @param sortList
	 * @return
	 * @author QiuYangjun
	 * @date 2014-2-26 下午4:12:03
	 */
	@Override
	public List<Map<String, Object>> findForUserProcessTrace(Map<String, Object> findParam, int start, int limit,
			List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return reportDAO.findForUserProcessTrace(findParam);
	}

	@Override
	public void report(String equipCode, List<Section> sectionsToReport) {
		Report report = reportDAO.getCurrent(equipCode);
		report.setReportTime(new Date());
		report.setReportUserCode(SessionUtils.getUser().getUserCode());
		reportDAO.update(report);

		for (Section section : sectionsToReport) {
			if (section.getId() == null) {
				sectionService.insertCurrentSection(equipCode, report, section);
			} else {
				sectionService.update(section);
			}
		}

		insert(report.getWorkOrderNo());
	}

	@Override
	public void deleteLast(String equipCode) {
		Report report = reportDAO.getCurrent(equipCode);
		Section findParams = new Section();
		findParams.setReportId(report.getId());
		Section section = sectionService.getOne(findParams);
		if (section != null) {
			sectionService.delete(section);
		}
		reportDAO.delete(report.getId());
	}

	@Override
	public void insert(String workOrderNo) {
		Report report = new Report();
		User user = SessionUtils.getUser();
		report.setReportUserCode(user.getUserCode());
		report.setReportTime(new Date());
		report.setWorkOrderNo(workOrderNo);
		report.setOrgCode(user.getOrgCode());
		insert(report);
	}

	@Override
	public int countByWoNo(String workOrderNo) {
		Report findParams = new Report();
		findParams.setWorkOrderNo(workOrderNo);
		return reportDAO.count(findParams);
	}

	/**
	 * <p>
	 * 员工姓名,工号统计报工记录
	 * </p>
	 * 
	 * @param findParam
	 * @return
	 * @author QiuYangjun
	 * @date 2014-2-26 下午4:12:03
	 * @see cc.oit.bsmes.wip.service.ReportService#countForUserProcessTrace(java.util.Map)
	 */
	@Override
	public int countForUserProcessTrace(Map<String, Object> findParam) {
		findParam.put("orgCode", getOrgCode());
		return reportDAO.countForUserProcessTrace(findParam);
	}

	@Override
	public List<Report> getByWorkOrderNo(String workOrderNo) {
		Report findParams = new Report();
		findParams.setWorkOrderNo(workOrderNo);
		return reportDAO.get(findParams);
	}

	/**
	 * <p>
	 * Title: report
	 * </p>
	 * <p>
	 * Description: 终端报工提交事件
	 * </p>
	 * 
	 * @param workOrderNo 生产单号
	 * @param reportLength 报工长度
	 * @param operator 操作人
	 * @param locationName 报工库位信息
	 * @param equipCode 设备编码
	 * @param reportUsers 报工归属人
	 * @return
	 * @see cc.oit.bsmes.wip.service.ReportService#report(java.lang.String,
	 *      java.lang.Double, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public JSONObject report(String workOrderNo, Double reportLength, String operator, String locationName,
			String equipCode, String[] reportUsers,String disk, Integer diskNumber)  {
//		int count =  reportDAO.getReportCount1(workOrderNo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", false);
		if(reportLength <= 0){ // 没报工长度直接返回
			jsonObject.put("message", "报工长度必须大于0！");
			return jsonObject;
		}
		// 1、当前生产单
		WorkOrder workOrder = workOrderService.getByWorkOrderNO(workOrderNo);
		if(null == workOrder){
			jsonObject.put("message", "当前生产单不存在！");
			return jsonObject;
		}
		// 2、生产中的任务单
		List<OrderTask> orderTasks = orderTaskService.getInProgressTask(workOrderNo, equipCode);
		if(null == orderTasks){
			jsonObject.put("message", "请双击至少一条生产单任务，使其变为加工中！");
			return jsonObject;
		}
		// 3、封装report对象并新增:T_WIP_REPORT
		Report report = this.getReportModel(workOrderNo, reportLength, operator, equipCode, orderTasks);
		this.insert(report);
		
		
		// 4、新增报工人信息:T_WIP_REPORT_USER
		this.insertReportUsers(report.getId(), report.getReportUserCode(), reportUsers);
		// 5、新增报工明细信息:T_WIP_REPORT_TASK
		boolean endWoOrder = WebConstants.END_WO_ORDER.equals(workOrder.getNextSection()); // 判断是否最后一道工序
		if(StringUtils.equals(disk, "") || diskNumber == null){
			this.insertReportTasks(report.getId(), report.getReportUserCode(), reportLength, orderTasks, endWoOrder,"");
		}else{
			this.insertReportTasks(report.getId(), report.getReportUserCode(), reportLength, orderTasks, endWoOrder,disk+"-"+diskNumber);
		}
		// 6、更新质检信息，报工批次
		processQcValueService.updateDA(report.getSerialNum(), workOrderNo);
		// 7、入库根据工序信息查询空闲的库位
		inventoryService.productInWarehouse(workOrder, report, locationName);
		// 8、获取该生产单的总报工长度
		double sumGoodLength = this.getSumGoodLength(workOrderNo);
		// 9、获取设备检测信息：判断是否下车捡
		MesClientEqipInfo info = new MesClientEqipInfo();
		processQcWipService.getCheckTypeByProcessId(workOrder.getProcessId(), info);
		jsonObject.put("success", true);
		jsonObject.put("sumGoodLength", sumGoodLength);
		jsonObject.put("isOutCheck", WebConstants.YES.equals(info.getNeedOutCheck()));
		
		// 10、更新workOrder表的percent字段: 新完成百分比=(总任务*完成百分比+当前报工长度)/总任务
		Double percent =(((workOrder.getPercent() ==null ? 0d : workOrder.getPercent())*workOrder.getOrderLength())+reportLength)/workOrder.getOrderLength();
		workOrder.setPercent(percent);
		int i = workOrderService.updatePercent(workOrder);
		if(i == 0){
			throw new MESException("报工已报，请勿重复提交！");
		}
		return jsonObject;
	}
	
	/**
	 * @Title:       getReportModel
	 * @Description: TODO(封装report对象)
	 * @param:       workOrderNo 生产单号
	 * @param:       reportLength 报工长度
	 * @param:       operator 报工人
	 * @param:       equipCode 设备编码
	 * @param:       orderTasks 报工选择的任务列表
	 * @return:      Report   
	 * @throws
	 */
	private Report getReportModel(String workOrderNo, Double reportLength, String operator,String equipCode, List<OrderTask> orderTasks){
		Report findParams = new Report();
		findParams.setWorkOrderNo(workOrderNo);
		int reports = reportDAO.count(findParams);
		Report report = new Report();
		report.setWorkOrderNo(workOrderNo);
		report.setReportLength(reportLength);
		report.setGoodLength(reportLength);
		report.setOrgCode(this.getOrgCode());
		report.setReportUserCode(StringUtils.isEmpty(operator) ? SessionUtils.getUser().getUserCode() : operator);
		report.setReportTime(new Date());
		report.setCoilNum(reports + 1);
		report.setSerialNum(getSerialNum(workOrderNo, report.getCoilNum()));
		report.setCreateUserCode(operator);
		report.setModifyUserCode(operator);
		report.setEquipCode(equipCode);
		report.setColor(this.getTaskColor(orderTasks));
		return report;
	}
	
	/**
	 * @Title:       insertReportUsers
	 * @Description: TODO(新增报工人员表)
	 * @param:       reportId 报工id
	 * @param:       reportUserCode 报工人工号
	 * @param:       reportUsers 报工信息所属工人工号
	 * @return:      void   
	 * @throws
	 */
	private void insertReportUsers(String reportId, String reportUserCode, String[] reportUsers){
		if (null == reportUsers) {
			return;
		}
		for (String user : reportUsers) {
			OnoffRecord onoffRecord = onoffRecordService.getById(user);
			ReportUser reportUser = new ReportUser();
			reportUser.setReportId(reportId);
			reportUser.setOnoffId(user);
			reportUser.setUserCode(onoffRecord.getUserCode());
			reportUser.setUserName(onoffRecord.getUserName());
			reportUser.setCreateUserCode(reportUserCode);
			reportUser.setModifyUserCode(reportUserCode);
			reportUserService.insert(reportUser);
		}
	}
	
	/**
	 * @param diskNumber 
	 * @Title:       insertReportTasks
	 * @Description: TODO(新增报工明细记录)
	 * @param:       reportId 报工id
	 * @param:       reportUserCode 报工人
	 * @param:       reportLength 报工长度
	 * @param:       orderTasks 任务明细
	 * @param:       isLastWorkOrder 是否最后一道生产单
	 * @return:      String 半成品编码
	 * @throws
	 */
	private void insertReportTasks(String reportId, String reportUserCode,  Double reportLength, List<OrderTask> orderTasks, boolean isLastWorkOrder, String diskNumber){
		// 1、循环任务获取订单分解工序:CustomerOrderItemProDec
		int i = 1; // 用来判断最后一个任务
		for (OrderTask orderTask : orderTasks) {
			// 2、获取订单分解工序:CustomerOrderItemProDec
			CustomerOrderItemProDec proDec = customerOrderItemProDecService.getById(orderTask.getOrderItemProDecId());
			Double unFinishedLength = proDec.getUnFinishedLength(); // 未完成长度
			Double finishedLength = proDec.getFinishedLength(); // 完成长度
			if (unFinishedLength <= 0) {
				continue;
			}
			// 3、封装任务明细model
			ReportTask reportTask = new ReportTask();
			reportTask.setReportId(reportId);
			reportTask.setOrderTaskId(orderTask.getId());
			reportTask.setColor(orderTask.getColor());
			reportTask.setMatCode(orderTask.getHalfProductCode());
			reportTask.setCreateUserCode(reportUserCode);
			reportTask.setModifyUserCode(reportUserCode);
			if(diskNumber.split("-").length > 1){
				reportTask.setDiskNumber(diskNumber);
			}
			// 4、更新任务完成状态：a:正常报工长度大于未完成长度;b:最后一道工序可以加5米余量比较：之所以没有用合同长度比较因为会分盘(相对独立)，一起比较不好比，单独任务比反而比较合理
			if(reportLength >= unFinishedLength || (isLastWorkOrder && (reportLength+5*(proDec.getWireCoilCount() == null ? 1 : proDec.getWireCoilCount())) >= unFinishedLength)){
				orderTask.setStatus(WorkOrderStatus.FINISHED);
				proDec.setStatus(ProductOrderStatus.FINISHED);
				orderTaskService.update(orderTask);
			}
			// 5、如果是最后一个任务/报工长度小于未完成长度：直接更新长度，PS:最后一个任务可以超长报工
			if(orderTasks.size() == i || reportLength < unFinishedLength){
				reportTask.setReportLength(reportLength);
				proDec.setUnFinishedLength(unFinishedLength - reportLength);
				proDec.setFinishedLength(finishedLength + reportLength);
				customerOrderItemProDecService.update(proDec);
				reportTaskService.insert(reportTask);
				break;
			}else{
				// 6、长度很长，且不是最后一个任务，更新报工长度、任务长度、完成长度和未完成长度，继续循环
				reportLength = reportLength - unFinishedLength;
				reportTask.setReportLength(unFinishedLength);
				proDec.setUnFinishedLength(unFinishedLength - unFinishedLength);
				proDec.setFinishedLength(finishedLength + unFinishedLength);
				customerOrderItemProDecService.update(proDec);
				reportTaskService.insert(reportTask);
			}
			i++;
		}
	}
	
	/**
	 * @Title: getTaskColor
	 * @Description: TODO(获取任务颜色)
	 * @param: orderTasks 报工任务明细
	 * @return: String
	 * @throws
	 */
	private String getTaskColor(List<OrderTask> orderTasks) {
		StringBuilder color = new StringBuilder();
		boolean flag = false;
		for (OrderTask orderTask : orderTasks) {
			if (flag) {
				color.append(",");
			} else {
				flag = true;
			}
			color.append(orderTask.getColor());
		}
		String getColor = null;
		try {
		    getColor = reportDAO.getShortColor(color.toString());
		} catch (Exception e) {
			getColor = color.toString();
		}
		if(getColor.length() > 1000){
			return getColor.substring(0, 998) + "...";
		}
		return getColor;
	}

	@Override
	public JSONObject calculateWasteLength(String woNo) {
		Report findParams = new Report();
		findParams.setWorkOrderNo(woNo);
		List<Report> reports = reportDAO.get(findParams);
		double sumReportLength = 0.0;
		for (Report report : reports) {
			sumReportLength += report.getReportLength();
		}
		JSONObject result = new JSONObject();
		List<Section> notReportSection = sectionService.getByWoNoAndNotReport(woNo, sumReportLength);
		if (notReportSection.size() == 0) {
			result.put("success", true);
			result.put("wasteLength", "0.0");
		} else {
			Map<String, CustomerOrderItemDec> itemDecMap = new HashMap<String, CustomerOrderItemDec>();
			List<CustomerOrderItemProDec> proDecs = customerOrderItemProDecService.getByWoNo(woNo);
			double wasteLength = 0.0;
			for (Section section : notReportSection) {
				wasteLength += sectionService.calculateWasteLength(section, proDecs, itemDecMap);
			}
			result.put("success", true);
			result.put("wasteLength", wasteLength);
		}
		return result;
	}

	@Override
	public List<Report> getReportByEquipCode(Report findParams, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return reportDAO.getByEquipCode(findParams);
	}

	@Override
	public int countByEquipCode(Report findParams) {
		findParams.setOrgCode(getOrgCode());
		return reportDAO.countByEquipCode(findParams);
	}

	@Override
	public double getSumGoodLength(String woNo) {
		return reportDAO.getSumGoodLength(woNo);
	}

	@Override
	public void export(OutputStream os, String sheetName, JSONArray columns, Map<String, Object> findParams)
			throws IOException, WriteException, InvocationTargetException, IllegalAccessException {
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		List<String> columnList = new ArrayList<String>();
		WritableSheet sheet = wwb.createSheet(sheetName, 0);
		for (int i = 0; i < columns.size(); i++) {
			JSONObject jsonObject = (JSONObject) columns.get(i);
			sheet.addCell(new Label(i, 0, jsonObject.getString("text")));
			columnList.add(jsonObject.getString("dataIndex"));
		}

		JSONArray list = getResList(findParams);
		if (list.size() == 0) {
			wwb.write();
			wwb.close();
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			Date endTime = null;
			JSONObject obj = (JSONObject) list.get(i);
			for (int j = 0; j < columnList.size(); j++) {
				String key = columnList.get(j);
				Object val = obj.get(key);
				if (StringUtils.equals("realEndTime", columnList.get(j))) {
					if (val == null) {
						val = DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT);
						endTime = new Date();
					} else {
						endTime = DateUtils.convert(val.toString(), DateUtils.DATE_TIME_FORMAT);
					}
				}
				if (StringUtils.equals("usedTime", columnList.get(j))) {
					Date startTime = DateUtils.convert(obj.get("realStartTime").toString(), DateUtils.DATE_TIME_FORMAT);
					long minutes = (endTime.getTime() - startTime.getTime()) / 60000;
					val = minutes / (60 * 24) + "天" + minutes / 60 % 24 + "时" + minutes % 60 + "分";

				}
				if (val != null) {
					sheet.addCell(new Label(j, i + 1, val.toString()));
				}
			}
		}

		wwb.write();
		wwb.close();

	}

	public JSONArray getResList(Map<String, Object> findParams) {
		findParams.put("orgCode", getOrgCode());
		List<Map<String, Object>> list = reportDAO.findForUserProcessTrace(findParams);
		NameFilter filter = new NameFilter() {
			@Override
			public String process(Object object, String name, Object value) {
				return CamelCaseUtils.toCamelCase(name);
			}
		};
		ValueFilter dateFilter = new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				if (value instanceof oracle.sql.TIMESTAMP) {

					try {
						return DateUtils
								.convert(((oracle.sql.TIMESTAMP) value).dateValue(), DateUtils.DATE_TIME_FORMAT);
					} catch (SQLException e) {

					}
				}
				return value;
			}

		};

		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.getNameFilters().add(filter);
		serializer.getValueFilters().add(dateFilter);
		serializer.write(list);
		String text = out.toString();
		JSONArray result = JSON.parseArray(text);
		return result;
	}

	/*
	 * @Override public void print(String reportId) throws PrinterException {
	 * Report report = reportDAO.getPrintInfoById(reportId); if(report == null){
	 * return; }
	 * 
	 * File barCodeDirectory = new
	 * File(WebContextUtils.getPropValue(WebConstants.BARCODE_PND_FILE_PATH));
	 * if(!barCodeDirectory.exists()){ barCodeDirectory.mkdir(); }
	 * 
	 * if(StringUtils.isBlank(report.getSerialNum())){
	 * report.setSerialNum(getSerialNum
	 * (report.getWorkOrderNo(),report.getCoilNum())); }
	 * printBarCode.init(report
	 * .getHalfProductCode(),report.getSerialNum(),report
	 * .getWorkOrderNo(),report.getReportLength());
	 * OneBarcodeUtil.createOneBarCode(report.getSerialNum());
	 * 
	 * Book book = new Book(); PageFormat pageFormat =
	 * printBarCode.createPageFormat(); book.append(printBarCode, pageFormat);
	 * 
	 * //获取打印服务对象 PrinterJob job = PrinterJob.getPrinterJob(); // 设置打印类
	 * job.setPageable(book);
	 * 
	 * job.print();
	 *//*
		 * String file =
		 * WebContextUtils.getPropValue(WebConstants.BARCODE_PND_FILE_PATH
		 * )+report.getSerialNum()+".jpg"; File barcodeFile = new File(file);
		 * barcodeFile.delete();
		 *//*
			 * }
			 */

	@Override
	public List<Report> checkFinishWorkOrder(Map<String, String> paramMap) {
		return reportDAO.checkFinishWorkOrder(paramMap);
	}

	@Override
	public Map<String, Object> createBarCode(String reportId) {
		Report report = reportDAO.getPrintInfoById(reportId);
		if (report == null) {
			return null;
		}
		WorkOrder workOrder = workOrderService.getByWorkOrderNO(report.getWorkOrderNo());
		List<OrderTask> orderTaskList = orderTaskService
				.getFinishedTask(report.getWorkOrderNo(), report.getEquipCode());
		CustomerOrderItemProDec customerOrderItemProDec = customerOrderItemProDecService.getById(orderTaskList.get(0)
				.getOrderItemProDecId());
		String outAttrDesc = customerOrderItemProDec.getOutAttrDesc();
		JSONObject object = JSONObject.parseObject(outAttrDesc);
		String conductorStruct = object.getString("conductorStruct");
		if (conductorStruct.contains("<br/>")) {
			conductorStruct = conductorStruct.substring(0, conductorStruct.indexOf('<'));
		}
		String material = object.getString("material").replaceAll("<br/>", ",");

		if (StringUtils.isBlank(report.getSerialNum())) {
			report.setSerialNum(getSerialNum(report.getWorkOrderNo(), report.getCoilNum()));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("color", report.getProductColor());
		result.put("id", reportId);
		result.put("serialNum", report.getSerialNum());
		result.put("halfProductCode", report.getHalfProductCode());
		result.put("workOrderNo", report.getWorkOrderNo());
		result.put("contractNo", orderTaskService.getContractNo(report.getWorkOrderNo()));
		result.put("reportLength", report.getReportLength());
		result.put("processName", workOrder.getProcessName());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result.put("printTime", df.format(new Date()));
		Location location = locationService.getBySerialNum(report.getSerialNum());
		result.put("locationName", location == null ? "" : location.getLocationName());
		result.put("conductorStruct", conductorStruct);
		result.put("material", material);
		result.put("custType",
				customerOrderItemProDec.getCustProductType() + " " + customerOrderItemProDec.getCustProductSpec());
		result.put("userName", report.getName());
		return result;
	}
	
	/**
	 * 新增方法,创建生产单信息
	 */
	public List<Map<String,Object>> createWoNoInfo(String workOrderNo) {
		List<Map<String,Object>> report = reportDAO.getInfoByWorkOrderNo(workOrderNo);

		if (report == null || "".equals(report) ) {
			return null;
		}
		
		String nextProcessWoNo = reportDAO.hasNextProcess(workOrderNo);
		if(nextProcessWoNo == null || nextProcessWoNo =="" ){
			nextProcessWoNo = "下道工艺尚未下发";
		}
		for(Map<String,Object> re : report){
			re.put("nextProcessWoNo",nextProcessWoNo);
		}
		return report;
	}
	
	@Override
	public Report getByBarCode(String barCode) {
		return reportDAO.getByBarCode(barCode);
	}

	@Override
	public List<Report> getByWorkOrder(String workOrderNo) {
		return reportDAO.getByWorkOrder(workOrderNo);
	}

	@Override
	public List<Report> getPutIn(String workOrderNo, String color, String orderTaskId) {
		List<String> orderDecItems = orderTaskService.getOrderItemDecIds(null, workOrderNo, null);

		OrderTask findParams = new OrderTask();
		findParams.setWorkOrderNo(workOrderNo);
		findParams.setStatus(WorkOrderStatus.IN_PROGRESS);
		List<OrderTask> list = orderTaskService.getByObj(findParams);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemDecIds", orderDecItems);
		Set<String> parentIds = new HashSet<String>();
		for (OrderTask orderTask : list) {
			parentIds.addAll(productProcessService.getParentProcessId(orderTask.getProcessId()));
		}
		params.put("processIds", parentIds);
		List<Report> reports = reportDAO.getPutIn(params);
		if (reports.isEmpty()) {
			if (parentIds.size() > 1) {
				logger.info("进入这里当前工序成缆以后的工序");
			}
			params.remove("processIds");
			reports = getReport(parentIds.iterator().next(), params);
		}
		return reports;
	}

	/**
	 * 递归工序，查询上一工序
	 * 
	 * @param nextProcessId
	 * @param params
	 * @return
	 */
	private List<Report> getReport(String nextProcessId, Map<String, Object> params) {
		List<String> parentIds = productProcessService.getParentProcessId(nextProcessId);
		if (parentIds.size() == 0) {
			return null;
		}
		params.put("processIds", parentIds);
		List<Report> result = reportDAO.getPutIn(params);
		if (result.isEmpty()) {
			if (parentIds.size() > 1) {
				logger.info("上一工序未做完，到这里只能是成缆以后的工序");
			}
			params.remove("processIds");
			result = getReport(parentIds.get(0), params);
		}
		return result;
	}

	@Override
	public Report getByWorkOrderNoAndBarCode(String barCode, String workOrderNo) {
		return reportDAO.getByWorkOrderNoAndBarCode(barCode, workOrderNo);
	}

	@Override
	public int countFind(Report params) {
		return reportDAO.countFind(params);
	}

	@Override
	public List<Report> getGoodLengthByWorkOrder(String workOrderNo) {
		// TODO Auto-generated method stub
		return reportDAO.getGoodLengthByWorkOrder(workOrderNo);
	}

	@Override
	public int count1(String workOrderNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Report> getReportOutput(Report findParams, int start,
			int limit, List<Sort> parseArray) {
		return reportDAO.getReportOutput(findParams);
	}

	@Override
	public List<Report> getEquipEnergyInfo(Report findParams) {
		return reportDAO.getEquipEnergyInfo(findParams);
	}

	@Override
	public String getReMarks(String workOrderNo) {
		return reportDAO.getReMarks(workOrderNo);
	}
	
	@Override
	public void updateUseStatus() {
		
		 reportDAO.updateUseStatus();
	}

	@Override
	public void updateUseStatus2() {
		
		 reportDAO.updateUseStatus2();
	}


}
