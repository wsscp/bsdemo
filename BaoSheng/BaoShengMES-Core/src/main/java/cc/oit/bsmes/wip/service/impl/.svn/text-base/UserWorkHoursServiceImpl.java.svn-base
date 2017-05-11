package cc.oit.bsmes.wip.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.UserWorkHoursDAO;
import cc.oit.bsmes.wip.model.UserWorkHours;
import cc.oit.bsmes.wip.service.UserWorkHoursService;

@Service
public class UserWorkHoursServiceImpl extends BaseServiceImpl<UserWorkHours>
		implements UserWorkHoursService {
	@Resource
	private UserWorkHoursDAO userWorkHoursDAO;

	/**
	 * 查询一个月的员工工时记录
	 * 
	 * @param yearMonth 年月
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getUsersHoursByMonth(String yearMonth,String userName, String typeName){

		String[] hoursTypeArray = { PRODUCTWORKHOURS }; // 固定列部分

		List<Map> mapArray = new ArrayList<Map>();
		// 1、 获取参数和列名:columnArray,startDate,endDate;
		Map paramsMap = this.initDayOfReportMonth(yearMonth);
		/*List<String> userNames=Arrays.asList(userName.split(","));
		List<String> typeNames=Arrays.asList(typeName.split(","));*/
		paramsMap.put("userName",userName);
		paramsMap.put("typeName",typeName);
		// 2、 获取数据结果并处理
		List<Map<String, Object>> userWorkHoursArray = userWorkHoursDAO.queryUsersHoursForAMonth(paramsMap); // 数据库查询出结果
		Map<String, Map<String, Map<String, Double>>> userHoursMap = this.initQueryData(userWorkHoursArray); // 对数据处理，转换成方便生成指定格式的excel数据
		
		Iterator<String> f_keys = userHoursMap.keySet().iterator();
		while (f_keys.hasNext()) {
			String userCodeName = f_keys.next();
			Map<String, Map<String, Double>> hoursTypeMap = userHoursMap
					.get(userCodeName);
			for (int j = 0; j < hoursTypeArray.length; j++) {
				Map map = new HashMap();
				map.put("USERCODE", userCodeName.substring(0, userCodeName.indexOf("-")));
				map.put("USERNAME", userCodeName.substring(userCodeName.indexOf("-") + 1));
				map.put("HOURSTYPE", hoursTypeArray[j]);
				Map<String,Double> tempMap = hoursTypeMap.get(hoursTypeArray[j]);
				if(tempMap.get(TOTAL_HOURS)!=0.0){
					map.putAll(hoursTypeMap.get(hoursTypeArray[j]));
				}
				mapArray.add(map);
			}
		}
		return mapArray;
	}
	
	
	/**
	 * 查询一个月的员工工时记录，导出成excel格式
	 * 
	 * @param outputStream 输出流
	 * @param yearMonth 年月
	 * @throws IOException
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Override
	public void usersHoursAMonthOutToExcel(OutputStream outputStream, String yearMonth) throws IOException {
		// 1、 获取参数和列名:columnArray,startDate,endDate;
		Map paramsMap = this.initDayOfReportMonth(yearMonth);
		List<String> columnArray = (List<String>) paramsMap.get("columnArray"); // excel列名

		// 2、 获取数据结果并处理
		List<Map<String, Object>> userWorkHoursArray = userWorkHoursDAO.queryUsersHoursForAMonth(paramsMap); // 数据库查询出结果
		Map<String, Map<String, Map<String, Double>>> userHoursMap = this.initQueryData(userWorkHoursArray); // 对数据处理，转换成方便生成指定格式的excel数据

		// 3、新建excel相关对象，数据静态对象
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("工时统计");// 添加sheet
		String[] hoursTypeArray = { PRODUCTWORKHOURS }; // 固定列部分
//		String[] hoursTypeArray = { PRODUCTWORKHOURS, PRODUCTSUPPORTHOURS, OVERTIMEHOURS, SUPPORTHOURS }; // 固定列部分
		
		// 4、设置默认宽度高度
		sheet.setDefaultRowHeight((short) (1 * 256));
		sheet.setDefaultColumnWidth(3);
		sheet.setColumnWidth(1, 8 * 256);  // 第二列特殊设置宽度
		// 5、获取表格需要样式:标题样式、第一列样式、数据样式
		HSSFCellStyle titleStyle = this.getCellStyle(wb, 12, true, false); // 标题样式
		HSSFCellStyle dateStyle = this.getCellStyle(wb, 8, false, false); // 数据样式
		HSSFCellStyle firColumnStyle = this.getCellStyle(wb, 8, false, true); // 第一列样式

		// 6、第一行:标题：创建行、设置高度、新建单元格、set数据、合并单元格、添加边框
		HSSFRow row = sheet.createRow(0); // 创建行
		row.setHeight((short) (15 * 16)); // 设置高度
		HSSFCell cell = row.createCell(0); // 新建单元格
		cell.setCellValue(yearMonth + "月份工时统计表"); // 标题:set数据
		cell.setCellStyle(titleStyle);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (columnArray.size() - 1))); // 标题栏合并单元格
		this.setRegionBorder(1, new CellRangeAddress(0, 0, 0, columnArray.size() - 1), sheet, wb); // 添加边框

		// 7、第二行:列名栏
		row = sheet.createRow(1);
		for (int i = 0; i < columnArray.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(columnArray.get(i));
			cell.setCellStyle(dateStyle);

			if (i >= columnArray.size() - 2) { // 特殊处理最后两列：宽度变宽
				sheet.setColumnWidth(i, 6 * 256);
			}
		}

		// 8、第三-N行:数据
		int rowNum = 2; // 第三行开始是数据
		Iterator<String> f_keys = userHoursMap.keySet().iterator();
		while (f_keys.hasNext()) {
			String userCodeName = f_keys.next();
			Map<String, Map<String, Double>> hoursTypeMap = userHoursMap
					.get(userCodeName);

			int startMerged = rowNum; // 合并单元格起始行
			for (int j = 0; j < hoursTypeArray.length; j++) {
				// 8.1、第一列:姓名-只在第一次set，因为要合并单元格
				row = sheet.createRow(rowNum);
				if (j == 0) {
					cell = row.createCell(0);
					cell.setCellValue(userCodeName.substring(userCodeName
							.indexOf("-") + 1));
					cell.setCellStyle(firColumnStyle);
				}
				// 8.2、第二列:工时类型
				cell = row.createCell(1);
				cell.setCellValue(hoursTypeArray[j]);
				cell.setCellStyle(dateStyle);

				// 8.3、第三-N列:数据-从map中取数据，对应存放
				Map<String, Double> dayHoursMap = hoursTypeMap.get(hoursTypeArray[j]);
				for (int i = 2; i < columnArray.size(); i++) {
					cell = row.createCell(i);
					if (null != dayHoursMap.get(columnArray.get(i))) {
						cell.setCellValue(dayHoursMap.get(columnArray.get(i)));
					}
					cell.setCellStyle(dateStyle);
				}
				// 8.4、对姓名合并单元格：类型的最后一次时合并
				if (j == hoursTypeArray.length - 1) {
					sheet.addMergedRegion(new Region(startMerged, (short) 0, rowNum, (short) 0)); // 合并
					this.setRegionBorder(1, new CellRangeAddress(startMerged, rowNum, 0, 0), sheet, wb); // 添加边框
				}

				rowNum++; // 按人循环过后行+1
			}
		}

//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		FileOutputStream fout = new FileOutputStream("D://"
//				+ df.format(new Date()) + ".xls");
		wb.write(outputStream);
//		fout.close();
	}

	/**
	 * 获取单元格样式
	 * @param wb excel表对象
	 * @param fontSize 字体大小
	 * @param bold 是否加粗
	 * @param wrapText 是否自动换行
	 * */
	private HSSFCellStyle getCellStyle(HSSFWorkbook wb, int fontSize, boolean bold, boolean wrapText){
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) fontSize);// 设置字体大小
		if(bold){
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}
		
		HSSFCellStyle columnStyle = wb.createCellStyle();
		columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		columnStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 指定单元格水平居中对齐
		columnStyle.setFont(font);
		columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		columnStyle.setWrapText(wrapText); // 自动换行
		return columnStyle;
	}
	
	/**
	 * 设置合并单元格后的边框问题
	 * @param border 边框厚度
	 * @param region 合并的区域对象 CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
	 * @param sheet sheet页
	 * @param wb excel表对象
	 * */
	private void setRegionBorder(int border, CellRangeAddress region,
			Sheet sheet, Workbook wb) {
		RegionUtil.setBorderBottom(border, region, sheet, wb);
		RegionUtil.setBorderLeft(border, region, sheet, wb);
		RegionUtil.setBorderRight(border, region, sheet, wb);
		RegionUtil.setBorderTop(border, region, sheet, wb);

	}

	/**
	 * 处理查询出来的参数，方便生成excel数据
	 * @param userWorkHoursArray 数据库里面查询出来的员工工时记录：按天分组
	 * */
	private Map<String, Map<String, Map<String, Double>>> initQueryData(
			List<Map<String, Object>> userWorkHoursArray) {
		String[] hoursTypeArray = { PRODUCTWORKHOURS, PRODUCTSUPPORTHOURS, OVERTIMEHOURS, SUPPORTHOURS };

		Map<String, Map<String, Map<String, Double>>> userHoursMap = new HashMap<String, Map<String, Map<String, Double>>>();
		for (Map<String, Object> userWorkHours : userWorkHoursArray) {
			String day = Integer.parseInt(userWorkHours.get("REPORTDATE").toString().substring(userWorkHours.get("REPORTDATE").toString().lastIndexOf("-") + 1)) + "日";

			Map<String, Map<String, Double>> hoursTypeMap = userHoursMap.get(userWorkHours.get("USERCODE") + "-" + userWorkHours.get("USERNAME"));
			if (null == hoursTypeMap) {
				hoursTypeMap = new HashMap<String, Map<String, Double>>();
				userHoursMap.put(userWorkHours.get("USERCODE") + "-" + userWorkHours.get("USERNAME"), hoursTypeMap);
			}

			for (int i = 0; i < hoursTypeArray.length; i++) {
				Map<String, Double> dayHoursMap = hoursTypeMap.get(hoursTypeArray[i]);
				if (null == dayHoursMap) {
					dayHoursMap = new HashMap<String, Double>();
					hoursTypeMap.put(hoursTypeArray[i], dayHoursMap);
				}

				// 合计、含系数
				Double totalHours = dayHoursMap.get(TOTAL_HOURS) == null ? 0.0 : dayHoursMap.get(TOTAL_HOURS), 
						totalHours2 = dayHoursMap.get(TOTAL_HOURS_2) == null ? 0.0 : dayHoursMap.get(TOTAL_HOURS_2); // 合计工时，合计工时(带系数)
				
				Double workHours = null;
				switch (i) {
				case 0:
					
					workHours = Double.valueOf(userWorkHours.get("PRODUCTWORKHOURS").toString());
					break;
				case 1:
					workHours = Double.valueOf(userWorkHours.get("PRODUCTSUPPORTHOURS").toString());
					break;
				case 2:
					workHours = Double.valueOf(userWorkHours.get("OVERTIMEHOURS").toString());
					break;
				case 3:
					workHours = Double.valueOf(userWorkHours.get("SUPPORTHOURS").toString());
					break;
				default:
					break;
				}
				
				// 添加到map对象：日的、合计、含系数；注：当类型为生产工时(即i==0)，工时含系数取包含系数的工时
				if(null != workHours){
					dayHoursMap.put(day, workHours);
					dayHoursMap.put(TOTAL_HOURS, totalHours + workHours);
					if(i == 0){
						if(userWorkHours.get("PRODUCTWORKHOURS2") != null){
							workHours = Double.valueOf(userWorkHours.get("PRODUCTWORKHOURS2").toString());
						}
					}
					dayHoursMap.put(TOTAL_HOURS_2, totalHours2 + workHours);
				}
			}
		}

		return userHoursMap;
	}

	/**
	 * 根据月获得报表所要查询和显示的天：上月的26-本月25
	 * @param yearMonth 年月
	 * @return Map 返回map，包含：{columnArray:excel列名, startDate:开始日期, endDate:结束时间}
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map initDayOfReportMonth(String yearMonth) {
		int year = Integer.parseInt(yearMonth.substring(0, yearMonth.indexOf("-")));
		int month =  Integer.parseInt(yearMonth.substring(yearMonth.indexOf("-") + 1));
						
		Map map = new HashMap();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<String> columnArray = new ArrayList<String>(); // excel列名
		String startDate, endDate; // 查询的开始和结束时间

		columnArray.add("姓名");
		columnArray.add("类型");

		Calendar calendarStart = Calendar.getInstance();
		calendarStart.set(Calendar.YEAR, year);
		calendarStart.set(Calendar.MONTH, month - 2);
		int start = 26;
		calendarStart.set(Calendar.DATE, start);
		startDate = df.format(calendarStart.getTime());

		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(Calendar.YEAR, year);
		calendarEnd.set(Calendar.MONTH, month - 1);
		calendarEnd.set(Calendar.DATE, 25);
		endDate = df.format(calendarEnd.getTime());

		for (int i = 0; i < 100; i++) {
			int maxDay = calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (start + i > maxDay) {
				i = 0;
				start = 1;
				calendarStart.add(Calendar.MONTH, 1);
//				calendarStart.set(Calendar.MONTH, month-1);
			}
			calendarStart.set(Calendar.DATE, start + i);

			if (calendarStart.after(calendarEnd)) {
				break;
			}
			columnArray.add(calendarStart.get(Calendar.DAY_OF_MONTH) + "日");
		}
		columnArray.add(TOTAL_HOURS);
		columnArray.add(TOTAL_HOURS_2);

		map.put("columnArray", columnArray);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return map;
	}
	
	private String TOTAL_HOURS = "合计";
	private String TOTAL_HOURS_2 = "含系数";
	
	private String PRODUCTWORKHOURS = "生产工时";
	private String PRODUCTSUPPORTHOURS = "生产辅助";
	private String OVERTIMEHOURS = "加班工时";
	private String SUPPORTHOURS = "辅助工时";

	@Override
	public List<Map<String, String>> getUserName() {		
		return userWorkHoursDAO.getUserName();
	}
	
}
