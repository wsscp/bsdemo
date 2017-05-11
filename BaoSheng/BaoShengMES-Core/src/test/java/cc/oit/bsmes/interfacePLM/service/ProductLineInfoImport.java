package cc.oit.bsmes.interfacePLM.service;

import cc.oit.bsmes.interfacePLM.dao.ScxkDAO;
import cc.oit.bsmes.interfacePLM.model.Scxk;
import cc.oit.bsmes.junit.BaseTest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Jinhy on 2015/3/30.
 * 导入生产线扩展属性
 */
public class ProductLineInfoImport extends BaseTest{

    @Resource
    private ScxkDAO scxkDAO;

    @Resource
    private ClassPathResource plmProcessDetail;

    @Test
    public void importScxkCsvalue() throws IOException {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(plmProcessDetail.getInputStream());
        } catch (Exception e) {
            workbook = new HSSFWorkbook(plmProcessDetail.getInputStream());
        }
        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);

            int maxRow = sheet.getLastRowNum() + 1;
            Row row = sheet.getRow(1);
            String no = row.getCell(0).getStringCellValue();
            Scxk scxk = scxkDAO.getByProductLineNo(no);
            if (scxk == null) {
                continue;
            }

            String scxkOldCsvalue = scxk.getCsvalue1() + scxk.getCsvalue2() + scxk.getCsvalue3();
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

            for (int j = 3; j < maxRow; j++) {
                row = sheet.getRow(j);
                String proCode = row.getCell(0).getStringCellValue();
                if (proCode.endsWith("-000")) {
                    continue;
                }
                String value = getRowValue(row.getCell(2));
                map.put(proCode, value);
            }
            StringBuffer buffer = new StringBuffer();
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                buffer.append(key + "=" + map.get(key) + "^");
            }
            String csvalue = buffer.toString();
            csvalue = csvalue.substring(0, csvalue.length() - 1);
            if (csvalue.length() > 2000) {
                scxk.setCsvalue1(csvalue.substring(0, 2000));
                if (csvalue.length() > 4000) {
                    scxk.setCsvalue2(csvalue.substring(2000, 4000));
                    scxk.setCsvalue3(csvalue.substring(4000, csvalue.length()));
                } else {
                    scxk.setCsvalue2(csvalue.substring(2000, csvalue.length()));
                }
            } else {
                scxk.setCsvalue1(csvalue);
            }
            scxkDAO.updateCsvalue(scxk);
        }
    }

    private String getRowValue(Cell cell) {
        Object result = null;
        int cellType = cell.getCellType();
        switch (cellType){
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
