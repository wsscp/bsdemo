package cc.oit.bsmes.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class ImportExcelEmployee {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {

		List<String> sqlStrArray = readExcel("C:\\Users\\dingxt\\Desktop\\B029 SQL.xlsx");
		executeSqlStrArray(sqlStrArray);
	}

	/**
	 * 执行sql代码
	 * */
	private static void executeSqlStrArray(List<String> sqlStrArray) throws SQLException {

		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String username = "sa";
		String password = "root";

		Connection conn = DriverManager.getConnection(url, username, password);
		Statement stmt = conn.createStatement();
		for (String sqlStr : sqlStrArray) {
			stmt.addBatch(sqlStr);
		}
		stmt.executeBatch();
		conn.commit();
		stmt.close();
		conn.close();
	}

	private static List<String> readExcel(String filePath) {
		List<String> sqlStrArray = new ArrayList<String>();
		// 判断excel版本
		boolean isExcel2003 = true;
		if (isExcel2007(filePath)) {
			isExcel2003 = false;
		}

		// 读取excel文件
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

		// 获取其中的sheet页，此处为获取第一个sheet
		for(int i = 0; i<wb.getNumberOfSheets();i ++){
			if("角色设备".equals(wb.getSheetName(i))){
				Sheet sheet = wb.getSheetAt(i);
				
				// 循环每行
				for (int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) { // 获取总行数
					Row row = sheet.getRow(r); // 获取行
					if (row == null) {
						continue; // 空行跳过
					}

					for(int j = 1; j<row.getPhysicalNumberOfCells();j ++){
						Cell cell = row.getCell(j);
						if (cell == null) {
							continue; // 空行跳过
						}
						if(j==1){
							String role =	ConvertCellStr(cell, "");
						}else{
							String equip =	ConvertCellStr(cell, "");
						}
						
						
						
					}
					// 获取列
					
					
				
			}
		}
		
		
		}
		return sqlStrArray;
	}

	private static String ConvertCellStr(Cell cell, String cellStr) {
		if (null == cell) {
			return cellStr;
		}
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
