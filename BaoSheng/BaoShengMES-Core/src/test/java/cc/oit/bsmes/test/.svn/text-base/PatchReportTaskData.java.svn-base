package cc.oit.bsmes.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.wip.dao.ReportTaskDAO;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.ReportTask;
import cc.oit.bsmes.wip.service.ReportService;

/**
 * 补充生产单数据 T_WIP_REPORT_TASK 的 报工长度
 */
public class PatchReportTaskData extends BaseTest {
	@Resource
	private ReportTaskDAO reportTaskDAO;
	@Resource
	private ReportService reportService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;

	// 每一个prodec已经报工了的长度
	public static Map<String, Double> prodecReportLengthMap = new HashMap<String, Double>();

	/**
	 * bean初始化后执行的方法
	 * */
	@PostConstruct
	public void initMethod() {
	}

	/**
	 * 执行函数
	 * */
	@Test
	@Rollback(false)
	public void process() throws BiffException, IOException {
		// 获取所有报功记录
		List<Report> reportArray = reportService.getAll();
		if (null == reportArray || reportArray.size() == 0) {
			return;
		}
		for (Report report : reportArray) {
			ReportTask param = new ReportTask();
			param.setReportId(report.getId());
			// 获取报工明细
			List<ReportTask> reportTaskArray = reportTaskDAO.find(param);
			if (null == reportTaskArray || reportTaskArray.size() == 0) {
				continue;
			}

			Double reportTotalLength = report.getReportLength(); // 报工总长度，将这个长度分散到具体的prodec

			for (ReportTask reportTask : reportTaskArray) {
				OrderTask orderTask = orderTaskService.getById(reportTask.getOrderTaskId());
				if (null == orderTask) {
					continue;
				}
				CustomerOrderItemProDec customerOrderItemProDec = customerOrderItemProDecService.getById(orderTask
						.getOrderItemProDecId());
				if (null == customerOrderItemProDec) {
					continue;
				}
				Double finfishLength = customerOrderItemProDec.getFinishedLength();
				Double hasReportLength = prodecReportLengthMap.get(customerOrderItemProDec.getId()); // 已经报工的长度
				if (null != hasReportLength) {
					finfishLength = finfishLength - hasReportLength;
				}

				Double updateReportLength = 0.0;
				if (finfishLength >= reportTotalLength) {
					updateReportLength = reportTotalLength;
				} else {
					updateReportLength = finfishLength;
				}
				prodecReportLengthMap.put(customerOrderItemProDec.getId(), ((hasReportLength == null ? 0.0
						: hasReportLength) + updateReportLength));

				reportTotalLength = reportTotalLength - updateReportLength; // 剩余的长度给下一个reportTask

				reportTask.setReportLength(updateReportLength);
				reportTaskDAO.update(reportTask);
			}
		}
	}
}
