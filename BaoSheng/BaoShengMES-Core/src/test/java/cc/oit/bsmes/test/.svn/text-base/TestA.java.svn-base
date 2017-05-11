package cc.oit.bsmes.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;

public class TestA {

	private static String[] codeArray = { "CZ50200750033-4*2.5", "CZ50200750205-19*2.5", "CZ50200750035-4*4",
			"CZ50200750038-4*6", "CZ50200420114-10*4", "CZ50200750114-10*4", "CZ50200420035-4*4",
			"CZ50200750111-10*2.5", "CZ50100100040-10*2.5", "CZ50100100042-10*6", "CZ50100100041-10*4",
			"CZ50200750163-14*4", "CZ50200090114-10*4", "CZ50100100003-4*4", "CZ50100100087-19*2.5",
			"CZ50100100002-4*2.5", "CZ50200420205-19*2.5", "CZ50200750115-10*6", "CZ50200090112-10*2.5",
			"CZ50200750072-7*2.5", "CZ50200420112-10*2.5", "CZ50200420034-4*2.5", "CZ50200420163-14*4",
			"CZ50200750161-14*2.5", "CZ50200090034-4*2.5", "CZ50200420115-10*6", "CZ50100100004-4*6" };

	private static String[] codeArray1 = { "CZ50200750033-4*2.5", "CZ50200750035-4*4", "CZ50200420114-10*4",
			"CZ50200420035-4*4", "CZ50100100040-10*2.5", "CZ50100100042-10*6", "CZ50100100041-10*4",
			"CZ50200750163-14*4", "CZ50200090114-10*4", "CZ50100100003-4*4", "CZ50100100087-19*2.5",
			"CZ50100100002-4*2.5", "CZ50200420205-19*2.5", "CZ50200090112-10*2.5", "CZ50200420112-10*2.5",
			"CZ50200420034-4*2.5", "CZ50200420163-14*4", "CZ50200090034-4*2.5", "CZ50200420115-10*6",
			"CZ50100100004-4*6" }; // "CZ20103190203-4*2.5",

	/**
	 * @param args
	 * @throws ParseException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void main(String[] args) throws SQLException, ParseException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 需要反射调用的方法
		Map<String, String> reflectMethodMap = new HashMap<String, String>();
		reflectMethodMap.put("guidePly", "指导厚度");
		reflectMethodMap.put("standardPly", "标准厚度");
		reflectMethodMap.put("standardMaxPly", "标称厚度最大值");
		reflectMethodMap.put("standardMinPly", "标称厚度最小值");
		reflectMethodMap.put("outsideValue", "标准外径");
		reflectMethodMap.put("outsideMaxValue", "最大外径");
		reflectMethodMap.put("outsideMinValue", "最小外径");
		reflectMethodMap.put("moldCoreSleeve", "模芯模套");
		reflectMethodMap.put("wiresStructure", "线芯结构");

		CustomerOrderItemProDec pd = new CustomerOrderItemProDec();
		JSONObject json = new JSONObject();
		Class clazz = pd.getClass();
		Method method = null;
		pd.setGuidePly("0.22");
		pd.setStandardPly("0.25");
		pd.setStandardMaxPly("0.8");
		pd.setStandardMinPly("0.19");
		pd.setOutsideValue("18mm");
		pd.setOutsideMaxValue("23mm");
		pd.setOutsideMinValue("15mm");
		pd.setMoldCoreSleeve("212/321");
		pd.setWiresStructure("0.176");

		Iterator<String> keys = reflectMethodMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = reflectMethodMap.get(key);
			method = clazz.getDeclaredMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
			json.put(key, (String) method.invoke(pd));
		}
		
		

		System.out.println(json.toJSONString());
		// SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		// Date d = f.parse("2015-07-01");
		// List<String> all = Arrays.asList(codeArray);
		// List<String> in = Arrays.asList(codeArray1);
		// for (String code : codeArray) {
		// }
		// System.out.println(moldCoreSleeve("B", 1.29, 1.0));
	}

	/**
	 * 
	 * <p>
	 * 排生产单 : 根据工序、客户订单ID获取工序列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processCode 工序
	 * @param orderItemIdArray 客户订单ID数组
	 * 
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> getOrderProcessByCode(String processCode, String[] orderItemIdArray) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("processCode", processCode);
		param.put("orderItemIdArray", orderItemIdArray);
		List<CustomerOrderItemProDec> list = null;
		// customerOrderItemProDecDAO.getOrderProcessByCode(param);
		for (CustomerOrderItemProDec prodec : list) {
			if (prodec.getInOrOut() == InOrOut.OUT) {
				// moldCoreSleeve
			}
		}
		return list;
	}

	/**
	 * 计算模芯/模套 方法 模芯 = 单丝直径 + (范围值) 模套 = 单丝直径 + 厚度*2
	 * 
	 * @param wiresStructure 线芯结构
	 * @param wireDia 单丝直径
	 * @param thickNess 标称厚度
	 * */
	private static String moldCoreSleeve(String wiresStructure, Double wireDia, Double thickNess) {
		Double moldCore = 0.0; // 模芯
		Double moldSleeve = 0.0; // 模套

		if (SalesOrderItem.ORDER_ITEM_TYPE_A.equalsIgnoreCase(wiresStructure)) {
			moldCore = wireDia + 0.1;
			moldSleeve = wireDia + thickNess * 2;
		} else if (SalesOrderItem.ORDER_ITEM_TYPE_B.equalsIgnoreCase(wiresStructure)) {
			moldCore = wireDia + 0.15;
			moldSleeve = wireDia + thickNess * 2;
		} else if (SalesOrderItem.ORDER_ITEM_TYPE_C.equalsIgnoreCase(wiresStructure)) {
			moldCore = wireDia + 0.2;
			moldSleeve = wireDia + thickNess * 2;
		}
		return moldCore + "/" + moldSleeve;
	}

	// 1.44/3.29

	private static List<Object[]> con() throws SQLException {
		List<Object[]> list = new ArrayList<Object[]>();

		String url = "jdbc:jtds:sqlserver://172.168.0.84:1433/interface";
		String username = "sa";
		String password = "root";

		Connection conn = DriverManager.getConnection(url, username, password);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from T_INT_EQIP_ISSUE_PARMS where EQUIP_CODE='B093'");
		while (rs.next()) {
			Object note[] = new Object[3];
			for (int i = 0; i < note.length; i++) {
				note[i] = rs.getObject(i + 1);
			}
			list.add(note);
		}
		stmt.close();
		conn.close();
		return list;
	}

	private static List<String> readExcel(String filePath) {
		List<String> dataList = new ArrayList<String>();
		boolean isExcel2003 = true;
		if (isExcel2007(filePath)) {
			isExcel2003 = false;
		}
		File file = new File(filePath);
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		Workbook wb = null;
		try {
			wb = isExcel2003 ? new HSSFWorkbook(is) : new XSSFWorkbook(is);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		Sheet sheet = wb.getSheetAt(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		int totalCells = 0;
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		StringBuffer sb;
		for (int r = 0; r < totalRows; r++) {

			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			sb = new StringBuffer();

			for (int i = 0; i < totalCells; i++) {
				Cell cell = row.getCell(i);
				if (cell == null) {
					continue;
				}
				sb.append(ConvertCellStr(cell, "")).append(",");
			}

			System.out.println(sb.toString());

		}
		return dataList;
	}

	private static String ConvertCellStr(Cell cell, String cellStr) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			// 读取String
			cellStr = cell.getStringCellValue().toString();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			// 得到Boolean对象的方法
			cellStr = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			// 先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {
				// 读取日期格式
				cellStr = formatTime(cell.getDateCellValue().toString());
			} else {
				// 读取数字
				cellStr = String.valueOf(cell.getNumericCellValue());

				BigDecimal bd = new BigDecimal(Double.parseDouble(cellStr));
				bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				cellStr = bd.intValue() + "";
			}
			break;
		case Cell.CELL_TYPE_FORMULA:
			// 读取公式
			cellStr = cell.getCellFormula().toString();
			break;
		}
		return cellStr;
	}

	private static boolean isExcel2007(String fileName) {
		return fileName.matches("^.+\\.(?i)(xlsx)$");
	}

	private static String formatTime(String s) {
		SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
		Date date = null;
		try {
			date = sf.parse(s);
		} catch (ParseException ex) {
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = sdf.format(date);
		return result;
	}

}
