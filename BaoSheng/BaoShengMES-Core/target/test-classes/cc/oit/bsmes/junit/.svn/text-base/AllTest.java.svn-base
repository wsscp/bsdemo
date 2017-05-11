package cc.oit.bsmes.junit;

import cc.oit.bsmes.bas.service.MonthCalendarService;
import cc.oit.bsmes.interfaceWWIs.dao.EquipIssueParamDAO;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.PlanScheduleTask;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

public class AllTest extends BaseTest {

	@Resource
	private SalesOrderService salesOrderService;
	@Resource
	private CustomerOrderItemService customerOrderService;
	@Resource
	private PlanScheduleTask planScheduleTask;
    @Resource
    private EquipIssueParamDAO equipIssueParamDAO;
    @Resource
    private MonthCalendarService monthCalendarService;

	@Test
    @Rollback(false)
	public void testAll() throws Exception {
		String orgCode = "1";
		JobParams params = new JobParams();
		params.setOrgCode(orgCode);

        planScheduleTask.process(params);
    }

}
