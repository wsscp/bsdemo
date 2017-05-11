package cc.oit.bsmes.common.util;

import jxl.Cell;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * Created by 羽霓 on 2014/5/9.
 */
public class JxlUtils {

    public static String getRealContents(Cell cell) {
        String contents = cell.getContents();
        return contents == null ? "" : contents.trim();
    }

    public static Object getRealContents(Row row,int num) {
        org.apache.poi.ss.usermodel.Cell poiCell = row.getCell(num);
        int cellType = poiCell.getCellType();
        switch(cellType) {
            case XSSFCell.CELL_TYPE_BLANK:
                return "";
            case XSSFCell.CELL_TYPE_ERROR:
                return "";
            case XSSFCell.CELL_TYPE_NUMERIC:
                return poiCell.getNumericCellValue();
            case  XSSFCell.CELL_TYPE_STRING:
                return poiCell.getStringCellValue();
            case XSSFCell.CELL_TYPE_BOOLEAN:
                return poiCell.getBooleanCellValue();
            case XSSFCell.CELL_TYPE_FORMULA:
                return poiCell.getArrayFormulaRange();
            default:
                return "";
        }
    }
}
