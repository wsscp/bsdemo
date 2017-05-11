package cc.oit.bsmes.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.bas.model.MonthCalendar;
import cc.oit.bsmes.bas.service.EquipCalShiftService;
import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.bas.service.MonthCalendarService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.junit.BaseTest;

/**
 * 补充设备日历数据 T_BAS_EQIP_CALENDAR T_BAS_EQIP_CAL_SHIFT
 */
public class PatchEquipCalendar extends BaseTest {
	@Resource
	private EquipCalendarService equipCalendarService;
	@Resource
	private EquipCalShiftService equipCalShiftService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private MonthCalendarService monthCalendarService;

	public static String[] shiftArray = { "1", "2", "3" };

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
		try {
			String orgCode = "bstl01";
			// 清空表
			equipCalShiftService.deleteAll();
			equipCalendarService.deleteAll();

			// 补充设备日历
			this.patchEquipCalendar(orgCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void patchEquipCalendar(String orgCode) {
		// 1、获取设备列表
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orgCode", orgCode);
		List<EquipInfo> equipInfoArray = equipInfoService.getEquipLine(param);
		for (EquipInfo equipInfo : equipInfoArray) {
			logger.debug("========设备{}添加日历中....==========", equipInfo.getCode());
			List<MonthCalendar> monthCalendarArray = monthCalendarService.getAll();
			for (MonthCalendar monthCalendar : monthCalendarArray) {
				List<String> dateOfWorkArray = this.getDateOfWorkArray(monthCalendar);
				for (String dateOfWork : dateOfWorkArray) {
					EquipCalendar equipCalendar = new EquipCalendar();
					equipCalendar.setEquipCode(equipInfo.getCode()); // 设备编码
					equipCalendar.setDateOfWork(dateOfWork); // 日期天
					equipCalendar.setOrgCode(orgCode); // 组织编码
					equipCalendarService.insert(equipCalendar);
					for (String shiftId : shiftArray) {
						EquipCalShift equipCalShift = new EquipCalShift();
						equipCalShift.setEquipCalendarId(equipCalendar.getId());
						equipCalShift.setWorkShiftId(shiftId);
						equipCalShiftService.insert(equipCalShift);
					}
				}
			}
		}
		logger.debug("========全部完成==========");
	}

	private List<String> getDateOfWorkArray(MonthCalendar monthCalendar) {
		List<String> dateOfWorkArray = new ArrayList<String>();
		String workMonth = monthCalendar.getWorkMonth();
		if (StringUtils.isNotEmpty(monthCalendar.getDay1())) {
			dateOfWorkArray.add(workMonth + "01");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay2())) {
			dateOfWorkArray.add(workMonth + "02");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay3())) {
			dateOfWorkArray.add(workMonth + "03");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay4())) {
			dateOfWorkArray.add(workMonth + "04");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay5())) {
			dateOfWorkArray.add(workMonth + "05");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay6())) {
			dateOfWorkArray.add(workMonth + "06");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay7())) {
			dateOfWorkArray.add(workMonth + "07");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay8())) {
			dateOfWorkArray.add(workMonth + "08");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay9())) {
			dateOfWorkArray.add(workMonth + "09");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay10())) {
			dateOfWorkArray.add(workMonth + "10");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay11())) {
			dateOfWorkArray.add(workMonth + "11");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay12())) {
			dateOfWorkArray.add(workMonth + "12");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay13())) {
			dateOfWorkArray.add(workMonth + "13");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay14())) {
			dateOfWorkArray.add(workMonth + "14");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay15())) {
			dateOfWorkArray.add(workMonth + "15");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay16())) {
			dateOfWorkArray.add(workMonth + "16");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay17())) {
			dateOfWorkArray.add(workMonth + "17");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay18())) {
			dateOfWorkArray.add(workMonth + "18");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay19())) {
			dateOfWorkArray.add(workMonth + "19");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay20())) {
			dateOfWorkArray.add(workMonth + "20");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay21())) {
			dateOfWorkArray.add(workMonth + "21");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay22())) {
			dateOfWorkArray.add(workMonth + "22");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay23())) {
			dateOfWorkArray.add(workMonth + "23");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay24())) {
			dateOfWorkArray.add(workMonth + "24");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay25())) {
			dateOfWorkArray.add(workMonth + "25");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay26())) {
			dateOfWorkArray.add(workMonth + "26");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay27())) {
			dateOfWorkArray.add(workMonth + "27");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay28())) {
			dateOfWorkArray.add(workMonth + "28");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay29())) {
			dateOfWorkArray.add(workMonth + "29");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay30())) {
			dateOfWorkArray.add(workMonth + "30");
		}
		if (StringUtils.isNotEmpty(monthCalendar.getDay31())) {
			dateOfWorkArray.add(workMonth + "31");
		}
		return dateOfWorkArray;
	}

}
