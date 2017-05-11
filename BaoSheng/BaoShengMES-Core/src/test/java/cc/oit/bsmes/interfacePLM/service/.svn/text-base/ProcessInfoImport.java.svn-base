package cc.oit.bsmes.interfacePLM.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.oit.bsmes.interfacePLM.model.Process;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import cc.oit.bsmes.interfacePLM.dao.ProcessDAO;
import cc.oit.bsmes.junit.BaseTest;

/**
 * Created by Jinhy on 2015/3/30. 工序扩展属性导入
 */
public class ProcessInfoImport extends BaseTest {

	@Resource
	private ProcessDAO processDAO;

	@Resource
	private ClassPathResource plmProcessDetail;

	@Test
	public void processInfoImport() throws IOException {
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(plmProcessDetail.getInputStream());
		} catch (Exception e) {
			workbook = new HSSFWorkbook(plmProcessDetail.getInputStream());
		}
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			analySheet(sheet);
		}
	}

	private void analySheet(Sheet sheet) {
		int maxRow = sheet.getLastRowNum() + 1;
		Row row = sheet.getRow(1); // 23
		String productNo = row.getCell(0).getStringCellValue();
		String prcvNo = row.getCell(1).getStringCellValue();

		Map<String, String> findParams = new HashMap<String, String>();
		findParams.put("productNo", productNo);
		findParams.put("prcvNo", prcvNo);
		findParams.put("proobjgno", String.valueOf(row.getCell(2).getNumericCellValue()));
		Process process = null;
		List<Process> processArray = processDAO.getByProductAndPrcv(findParams);
		if (processArray == null || processArray.size() == 0) {
			return;
		} else {
			process = processArray.get(0);
		}

		// 把已经存在的属性放入map集合中缓存
		String scxkOldCsvalue = process.getCsvalue1() + process.getCsvalue2() + process.getCsvalue3();
		String[] valueArray = scxkOldCsvalue.split("\\^");
		Map<String, String> map = new HashMap<String, String>();
		for (String pro : valueArray) {
			String[] subArray = pro.split("=");
			if (subArray.length == 2) {
				map.put(subArray[0], subArray[1]);
			} else {
				map.put(subArray[0], null);
			}
		}

		// 遍历row 把每行数据添加到map中
		StringBuffer buffer = new StringBuffer();
		for (int j = 3; j < maxRow; j++) {
			row = sheet.getRow(j);
			String proCode = row.getCell(0).getStringCellValue();
			if (proCode.endsWith("-000")) {
				continue;
			}
			String value = getRowValue(row.getCell(2));
			map.put(proCode, value);
		}

		// 遍历map 拼接字符串
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			buffer.append(key + "=" + map.get(key) + "^");
		}

		// 把数据库按照字段长度限制保存到数据库中
		String csvalue = buffer.toString();
		csvalue = csvalue.substring(0, csvalue.length() - 1);
		if (csvalue.length() > 2000) {
			process.setCsvalue1(csvalue.substring(0, 2000));
			if (csvalue.length() > 4000) {
				process.setCsvalue2(csvalue.substring(2000, 4000));
				process.setCsvalue3(csvalue.substring(4000, csvalue.length()));
			} else {
				process.setCsvalue2(csvalue.substring(2000, csvalue.length()));
			}
		} else {
			process.setCsvalue1(csvalue);
		}
		processDAO.updateCsValue1(process);
	}

	private String getRowValue(Cell cell) {
		Object result = null;
		int cellType = cell.getCellType();
		switch (cellType) {
		case Cell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			result = cell.getNumericCellValue();
			break;
		default:
			result = "";
		}
		return String.valueOf(result);
	}
}
