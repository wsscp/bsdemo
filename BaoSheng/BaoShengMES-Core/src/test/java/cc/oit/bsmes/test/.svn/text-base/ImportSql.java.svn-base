package cc.oit.bsmes.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportSql {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		// List<Object[]> a = con();
		// for (Object[] s : a) {
		// System.out.println(s);
		// }

		List<String> sqlList = readExcel("C:\\Users\\dingxt\\Desktop\\B029 SQL.xlsx");
		for (String sql : sqlList) {
			System.out.println(sql);
		}

	}

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
		net.sourceforge.jtds.jdbc.Driver a;
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
		String parmCode = "";
		String targetValue = "";
		for (int r = 0; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			Cell cell = row.getCell(0);
			if (cell == null) {
				continue;
			}
			parmCode = ConvertCellStr(cell, parmCode);

			cell = row.getCell(1);
			if (cell != null) {
				targetValue = ConvertCellStr(cell, targetValue);
			}

			dataList.add("INSERT INTO "
					+ "T_INT_EQIP_ISSUE_PARMS "
					+ "(ID, EQUIP_CODE, PARM_CODE, TARGET_VALUE, UP_VALUE, DOWN_VALUE, NEED_ALARM, WORK_ORDER_ID, CREATE_USER_CODE, CREATE_TIME, MODIFY_USER_CODE, MODIFY_TIME) "
					+ "VALUES " + "(newid(), 'B029', '" + parmCode + "', '" + targetValue
					+ "', '', '', '0', '', 'admin', getdate(), 'admin', getdate()); ");
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
