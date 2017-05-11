package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.bas.model.WorkShift;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class EquipCalendarDAOTest extends BaseTest{
	@Resource
	private EquipCalendarDAO equipCalendarDAO;
	@Resource
	private EquipCalShiftDAO equipCalShiftDAO;
	@Resource
	private WorkShiftDAO workShiftDAO;
	
//设备编码
//	LINE-JC-0002 s
//	LINE-CL-0001 s
//	LINE-CL-0002
//	LINE-JC-0003 s
//	LINE-DY-0001
//	LINE-SJ-0001
//	LINE-RB-0001
	
	@Test
	@Rollback(false)
	public void insert(){
		EquipCalendar equipCalendar=null;
		Date startDate=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		//日期
		cal.add(Calendar.DAY_OF_MONTH,9);
		Date endDate=cal.getTime();
		equipCalendar=new EquipCalendar();
		equipCalendar.setEquipCode("LINE-JC-0002");
		String workDay=DateUtils.getDay(endDate);
		System.out.println(workDay);
				
		//设备日历
		equipCalendar.setDateOfWork(workDay);
		equipCalendar.setCreateUserCode(SessionUtils.getUser().getUserCode());
		equipCalendar.setCreateTime(new Date());
		equipCalendar.setModifyUserCode(SessionUtils.getUser().getUserCode());
		equipCalendar.setModifyTime(new Date());
		equipCalendar.setOrgCode("03");
		equipCalendarDAO.insert(equipCalendar);

		//一个班次
		//班次信息
		
		WorkShift workShift=new WorkShift();
		workShift.setId(UUID.randomUUID().toString());
		workShift.setShiftName("设备排班");
		workShift.setShiftStartTime("0800");
		workShift.setShiftEndTime("1600");
		workShift.setCreateUserCode(SessionUtils.getUser().getUserCode());
		workShift.setCreateTime(new Date());
		workShift.setModifyUserCode(SessionUtils.getUser().getUserCode());
	    workShift.setModifyTime(new Date());
		workShiftDAO.insert(workShift);
				 //设备班次
		EquipCalShift equipCalShift=new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId(workShift.getId());
		equipCalShift.setCreateUserCode(SessionUtils.getUser().getUserCode());
		equipCalShift.setCreateTime(new Date());
		equipCalShift.setModifyUserCode(SessionUtils.getUser().getUserCode());
		equipCalShift.setModifyTime(new Date());
		equipCalShiftDAO.insert(equipCalShift);
				
//		两个班次
//		workShift.setId(UUID.randomUUID().toString());
//		workShift.setShiftStartTime("1600");
//		workShift.setShiftEndTime("0000");
//		workShiftDAO.insert(workShift);
//		equipCalShift.setId(UUID.randomUUID().toString());
//		equipCalShift.setWorkShiftId(workShift.getId());
//		equipCalShiftDAO.insert(equipCalShift);
//////				 三个班次
//		workShift.setId(UUID.randomUUID().toString());
//		workShift.setShiftStartTime("0000");
//		workShift.setShiftEndTime("0800");
//		workShiftDAO.insert(workShift);
//		equipCalShift.setId(UUID.randomUUID().toString());
//		equipCalShift.setWorkShiftId(workShift.getId());
//		equipCalShiftDAO.insert(equipCalShift);
			
		
		
	}
	 
}
