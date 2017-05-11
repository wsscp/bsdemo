package cc.oit.bsmes.interfacePLM.service;

import cc.oit.bsmes.interfacePLM.dao.ProcessDAO;
import cc.oit.bsmes.interfacePLM.dao.ScxkDAO;
import cc.oit.bsmes.interfacePLM.model.MpartInOut;
import cc.oit.bsmes.interfacePLM.model.Process;
import cc.oit.bsmes.interfacePLM.model.Scxk;
import cc.oit.bsmes.junit.BaseTest;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Jinhy on 2015/3/9.
 */
public class MpartDAOTest extends BaseTest {

    @Resource
    private MpartInOutService mpartInOutService;

    @Resource
    private ProcessDAO processDAO;

    @Resource
    private ClassPathResource plmProcessDetail;

    @Resource
    private ScxkDAO scxkDAO;

    @Test
    public void scxkDaoTest(){
        List<Scxk> list = scxkDAO.lastUpdateData(null);
        logger.info(list.size()+"------------------");
        for (Scxk scxk : list) {
            logger.info(scxk.getName()+scxk.getNo());

        }
    }

    public static void main(String[] args) {
        System.out.println(5/2);
    }

    @Test
    @Rollback(false)
    public void updateCsType() {
        List<MpartInOut> list = mpartInOutService.getAll();
        logger.info("list num:" + list.size());
        for (MpartInOut mpart : list) {
            String csValue1 = mpart.getCsvalue1();
            if (StringUtils.isBlank(csValue1)) {
                continue;
            }

            if (csValue1.contains("JY-001-004")) {
                String[] pros = StringUtils.split(csValue1, "^");
                for (String pro : pros) {
                    if (pro.contains("JY-001-004")) {
                        String color = pro.substring(11, pro.length());
                        if (StringUtils.isNotBlank(color)) {
                            mpart.setYanse(color);
                            logger.info("update row id :" + mpart.getId());
                            mpartInOutService.update(mpart);
                        }
                    }
                }
            }
        }
    }


    @Test
    @Rollback
    public void getProcessTest() throws IOException {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(plmProcessDetail.getInputStream());
        } catch (Exception e) {
            workbook = new HSSFWorkbook(plmProcessDetail.getInputStream());
        }
        int sheets = workbook.getNumberOfSheets();
        for(int i= 0;i<sheets;i++){
            Sheet sheet = workbook.getSheetAt(i);
            analySheet(sheet);
        }
    }

    private void analySheet(Sheet sheet) {
        int maxRow = sheet.getLastRowNum()+1;
        Row row = sheet.getRow(1); //23
        String productNo = row.getCell(0).getStringCellValue();
        String prcvNo = row.getCell(1).getStringCellValue();

        Map<String, String> map = new HashMap<String, String>();
        map.put("productNo", productNo);
        map.put("prcvNo", prcvNo);
        map.put("proobjgno", String.valueOf(row.getCell(2).getNumericCellValue()));
        Process process = null;
        List<Process> processArray = processDAO.getByProductAndPrcv(map);
        if(processArray == null || processArray.size() ==0){
            return;
        }else{
        	process = processArray.get(0);
        }
        StringBuffer buffer = new StringBuffer();
        for(int j = 3;j<maxRow;j++){
            row = sheet.getRow(j);
            String proCode=row.getCell(0).getStringCellValue();
            if(proCode.endsWith("-000")){
                continue;
            }
            String value = getRowValue(row.getCell(2));
            buffer.append(proCode+"="+value+"^");
        }
        String csvalue = buffer.toString();
        csvalue =  csvalue.substring(0,csvalue.length() -1);
        if(csvalue.length() > 2000){
            process.setCsvalue1(csvalue.substring(0, 2000));
            if(csvalue.length() > 4000){
                process.setCsvalue2(csvalue.substring(2000,4000));
                process.setCsvalue3(csvalue.substring(4000,csvalue.length()));
            }else{
                process.setCsvalue2(csvalue.substring(2000,csvalue.length()));
            }
        }else{
            process.setCsvalue1(csvalue);
        }
        processDAO.updateCsValue1(process);
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

