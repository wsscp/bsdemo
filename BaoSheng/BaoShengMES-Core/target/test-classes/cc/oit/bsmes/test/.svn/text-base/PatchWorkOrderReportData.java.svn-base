package cc.oit.bsmes.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.wip.service.WorkOrderReportService;

/**
 * 补充生产单数据 T_WIP_WORK_ORDER_REPORT T_WIP_USER_WORK_HOURS
 */
public class PatchWorkOrderReportData extends BaseTest {
	@Resource
	private WorkOrderReportService workOrderReportService;

	/**
	 * bean初始化后执行的方法
	 * */
	@PostConstruct
	public void initMethod() {

	}

	public String startDate = "2015-07-01";
	public String endDate = "2015-09-01";

	/**
	 * 执行函数
	 * @throws ParseException 
	 * */
	@Test
	@Rollback(false)
	public void process() throws BiffException, IOException, ParseException {
			List<String> queryDateArray = getPatchDateArray();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			for (String queryDate : queryDateArray) {
				System.out.println(queryDate);
				paramMap.put("vo_reporttype", null);
				paramMap.put("vo_querydate", queryDate);
				workOrderReportService.callProcedure(paramMap);
			}
	}

	private List<String> getPatchDateArray() throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date start = df.parse(startDate);
		Date end = df.parse(endDate);

		List<String> queryDateArray = new ArrayList<String>();
		queryDateArray.add(startDate);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);

		boolean addEnd = true; // 结束时间是否超过当前时间
		for (int i = 0; i < 100; i++) {
			calendar.add(Calendar.DATE, 1);
			Date tmp = calendar.getTime();
			if (tmp.after(new Date())) {
				addEnd = false;
			}
			if (tmp.before(end) && tmp.before(new Date())) {
				queryDateArray.add(df.format(tmp));
			} else {
				break;
			}
		}
		if (addEnd) {
			queryDateArray.add(endDate);
		}
		return queryDateArray;
	}

}
