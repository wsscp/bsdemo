package cc.oit.bsmes.pla.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.pla.dao.MaterialCheckReportDAO;
import cc.oit.bsmes.pla.dao.MaterialCheckReportDetailsDAO;
import cc.oit.bsmes.pla.model.MaterialCheckReport;
import cc.oit.bsmes.pla.model.MaterialCheckReportDetails;
import cc.oit.bsmes.pla.service.MaterialCheckReportService;

@Service
public class MaterialCheckReportServiceImpl extends BaseServiceImpl<MaterialCheckReport> implements MaterialCheckReportService{

	@Resource private MaterialCheckReportDAO materialCheckReportDAO;
	@Resource private MaterialCheckReportDetailsDAO materialCheckReportDetailsDAO;
	
	
	@Override
	public void importMaterialCheckReport(Sheet sheet,
			JSONObject result,Date date) {
		// TODO Auto-generated method stub
		MaterialCheckReport material = null;
		int maxRow = sheet.getLastRowNum()+1;
		for(int i=2;i<maxRow;i++){
			Row row = sheet.getRow(i);
			material = new MaterialCheckReport();
			if(row == null || (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(3).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(4).getCellType() == Cell.CELL_TYPE_BLANK)){
				continue;
			}
			if(null != row.getCell(0)){
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				String m = row.getCell(0).getStringCellValue();
				if(m.equals("过期料")){
					if(result.containsKey("message")){
						result.put("message", result.getString("message")+"</br>"+sheet.getSheetName()+"库存导入成功");
					}else{
						result.put("message", sheet.getSheetName()+"库存导入成功");
					}
					result.put("success", true);
					return;
				}
				m = m.replace(".", "-");
				Date d = DateUtils.convert(m,"yyyy-MM-dd");
				material.setManuDate(d);
			}
			if(null != row.getCell(1) || null != row.getCell(2)){
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				if(null != row.getCell(1) && null != row.getCell(2)){
					if(sheet.getSheetName().equals("绝缘")){
						material.setWarehouseNo(row.getCell(1).getStringCellValue());
						material.setManuName(row.getCell(2).getStringCellValue());
					}else{
						material.setManuName(row.getCell(1).getStringCellValue());
						material.setWarehouseNo(row.getCell(2).getStringCellValue());
					}
				}else if(null == row.getCell(1)){
					if(sheet.getSheetName().equals("绝缘")){
						material.setManuName(row.getCell(2).getStringCellValue());
					}else{
						material.setWarehouseNo(row.getCell(2).getStringCellValue());
					}
				}else{
					if(sheet.getSheetName().equals("绝缘")){
						material.setWarehouseNo(row.getCell(1).getStringCellValue());
					}else{
						material.setManuName(row.getCell(1).getStringCellValue());
					}
				}
			}
			if(null != row.getCell(3)){
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				material.setMatName(row.getCell(3).getStringCellValue());
			}
			if(null != row.getCell(4) && row.getCell(4).getCellType() != Cell.CELL_TYPE_BLANK){
				row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				material.setMatColor(row.getCell(4).getStringCellValue());
			}else{
				material.setMatColor("未标注颜色");
			}
			if(null != row.getCell(5) && row.getCell(5).getCellType() != Cell.CELL_TYPE_BLANK){
				row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
				material.setStockComment(row.getCell(5).getStringCellValue());
			}else{
				material.setStockComment("无备注");
			}
			material.setSectionName(sheet.getSheetName());
			material.setCheckMonth(date);
			materialCheckReportDAO.insert(material);
			MaterialCheckReport m = materialCheckReportDAO.getOne(material);
			int start = 6;
			List<Date> list = getCheckDates(sheet,date,start);
			insertMaterialCheckReportDetail(m,row,list,start);
		}
		if(result.containsKey("message")){
			result.put("message", result.getString("message")+"</br>"+sheet.getSheetName()+"库存导入成功");
		}else{
			result.put("message", sheet.getSheetName()+"库存导入成功");
		}
		result.put("success", true);
	}
	
	private List<Date> getCheckDates(Sheet sheet, Date date, int start) {
		// TODO Auto-generated method stub
		Row row = sheet.getRow(1);
		int maxCell = row.getLastCellNum();
		List<Date> list = new ArrayList<Date>();
		for(int i=start;i<maxCell;i++){
			Cell cell = row.getCell(i);
			if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
				continue;
			}
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String d = row.getCell(i).getStringCellValue();
			d = d.replace("月", "-");
			d = d.replace("日", "-");
			d = d.replace("号", "");
			String year = DateUtils.convert(date, "yyyy");
			d = year + "-" + d;
			Date checkDay = DateUtils.convert(d, "yyyy-MM-dd");
			list.add(checkDay);
		}
		return list;
	}

	private void insertMaterialCheckReportDetail(MaterialCheckReport m,
			Row row, List<Date> list, int start) {
		// TODO Auto-generated method stub
		int maxCell = row.getLastCellNum()+1;
		int seq = 0;
		MaterialCheckReportDetails detail = null;
		if(m==null){
			return;
		}
		for(int i=start;i<maxCell;i++){
			detail = new MaterialCheckReportDetails();
			if(row.getCell(i) == null || row.getCell(i).getCellType() == Cell.CELL_TYPE_BLANK){
				seq++;
				continue;
			}
			row.getCell(i).setCellType(Cell.CELL_TYPE_NUMERIC);
			detail.setCheckDate(list.get(seq));
			detail.setDailyCheckQuantity((int) row.getCell(i).getNumericCellValue());
			detail.setSeq(seq);
			detail.setStockCheckId(m.getId());
			materialCheckReportDetailsDAO.insert(detail);
			seq++;
		}
		
	}

	@Override
	public List<MaterialCheckReport> getCheckDays(String yearMonth) {
		// TODO Auto-generated method stub
		List<MaterialCheckReport> m = materialCheckReportDAO.getCheckDays(yearMonth);
		for(int i=0;i<m.size();i++){
			m.get(i).setSeq(i);
		}
		return m;
	}

	@Override
	public List<Map> getMaterialStockByMonth(Map<String, String> param,Integer start, Integer limit, List<Sort> sortList) {
		// TODO Auto-generated method stub
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		List<MaterialCheckReport> materials = materialCheckReportDAO.getByCheckMonth(param);
		List<Map> mapArray = new ArrayList<Map>();
		for(MaterialCheckReport m: materials){
			Map<String,String> map = new HashMap<String,String>();
			map.put("manuName", m.getManuName());
			map.put("matName", m.getMatName());
			map.put("warehouseNo", m.getWarehouseNo());
			map.put("spec", m.getSpec());
			map.put("stockComment", m.getStockComment());
			map.put("manuDate",DateUtils.convert(m.getManuDate(), "yyyy-MM-dd"));
			List<MaterialCheckReportDetails> details = materialCheckReportDetailsDAO.getByStockCheckId(m.getId());
			for(MaterialCheckReportDetails d : details){
				map.put(d.getSeq()+"", d.getDailyCheckQuantity()+"");
			}
			mapArray.add(map);
		}
		return mapArray;
	}

	@Override
	public void importMaterialCheckReportGXJ(Sheet sheet, JSONObject result,
			Date date) {
		// TODO Auto-generated method stub
		MaterialCheckReport material = null;
		int maxRow = sheet.getLastRowNum()+1;
		for(int i=2;i<maxRow;i++){
			Row row = sheet.getRow(i);
			material = new MaterialCheckReport();
			System.out.println(row);
			if(null == row || (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(3).getCellType() == Cell.CELL_TYPE_BLANK)){
				continue;
			}
			if(null != row.getCell(0)){
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				System.out.println("1");
				material.setMatName(row.getCell(0).getStringCellValue());
			}
			if(null != row.getCell(1)){
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				material.setManuName(row.getCell(1).getStringCellValue());
			}
			if(null != row.getCell(2) && row.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK){
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				material.setStockComment(row.getCell(2).getStringCellValue());
			}else{
				material.setStockComment("无备注");
			}
			if(null != row.getCell(3)){
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				material.setWarehouseNo(row.getCell(3).getStringCellValue());
			}
			material.setMatColor(sheet.getSheetName()+"没有颜色");
			material.setSectionName(sheet.getSheetName());
			material.setCheckMonth(date);
			materialCheckReportDAO.insert(material);
			MaterialCheckReport m = materialCheckReportDAO.getOne(material);
			int start = 4;
			List<Date> list = getCheckDates(sheet,date,start);
			insertMaterialCheckReportDetail(m,row,list,start);
		}
		if(result.containsKey("message")){
			result.put("message", result.getString("message")+"</br>"+sheet.getSheetName()+"库存导入成功");
		}else{
			result.put("message", sheet.getSheetName()+"库存导入成功");
		}
		result.put("success", true);
	}

	@Override
	public void importMaterialCheckReportCL(Sheet sheet, JSONObject result,
			Date date) {
		// TODO Auto-generated method stub
		MaterialCheckReport material = null;
		int maxRow = sheet.getLastRowNum()+1;
		for(int i=2;i<maxRow;i++){
			Row row = sheet.getRow(i);
			material = new MaterialCheckReport();
			if(row == null || (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(3).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(4).getCellType() == Cell.CELL_TYPE_BLANK)){
				continue;
			}
			if(null != row.getCell(0)){
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				material.setMatName(row.getCell(0).getStringCellValue());
			}
			if(null != row.getCell(1)){
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				material.setSpec(row.getCell(1).getStringCellValue());
			}
			if(null != row.getCell(2)){
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				material.setManuName(row.getCell(2).getStringCellValue());
			}
			material.setMatColor(sheet.getSheetName()+"没有颜色");
			material.setStockComment(sheet.getSheetName()+"没有备注");
			material.setSectionName(sheet.getSheetName());
			material.setCheckMonth(date);
			materialCheckReportDAO.insert(material);
			MaterialCheckReport m = materialCheckReportDAO.getOne(material);
			int start = 3;
			List<Date> list = getCheckDates(sheet,date,start);
			insertMaterialCheckReportDetail(m,row,list,start);
		}
		if(result.containsKey("message")){
			result.put("message", result.getString("message")+"</br>"+sheet.getSheetName()+"库存导入成功");
		}else{
			result.put("message", sheet.getSheetName()+"库存导入成功");
		}
		result.put("success", true);
	}

	@Override
	public List<Map> getCheckMonth() {
		// TODO Auto-generated method stub
		List<String> month = materialCheckReportDAO.getCheckMonth();
		List<Map> mapArray = new ArrayList<Map>();
		for(String m : month){
			Map<String,String> map = new HashMap<String,String>();
			map.put("yearMonth", m);
			mapArray.add(map);
		}
		return mapArray;
	}
	
	public boolean verdictFormat(Sheet sheet,List<String> list,JSONObject result){
		if(null == sheet){
			return true;
		}
		System.out.println(sheet.getSheetName());
		Row row = sheet.getRow(1);
		if(row.getLastCellNum()<=1){
			result.put("message", "Excel表格格式不对");
			result.put("success", false);
			return false;
		}
		for(int i=0;i<list.size();i++){
			if(null == row.getCell(i) || row.getCell(i).getCellType() == Cell.CELL_TYPE_BLANK){
				result.put("message", "Excel表格格式不对");
				result.put("success", false);
				return false;
			}else{
				row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
				System.out.println(row.getCell(i).getStringCellValue());
				if(!row.getCell(i).getStringCellValue().equals(list.get(i))){
					result.put("message", "库存表格格式不对，请检查确认后重新导入");
					result.put("success", false);
					return false;
				}
			}
		}
		return true;
	}
	
	public List<String> getSheetHeaders(Sheet sheet){
		List<String> header = new ArrayList<String>();
		if(sheet == null){
			return null;
		}
		if("绝缘".equals(sheet.getSheetName())){
			header.add("生产日期");
			header.add("库号");
			header.add("厂家");
			header.add("品名");
			header.add("颜色"); 
			header.add("备注");
		}
		if("护套".equals(sheet.getSheetName())){
			header.add("生产日期");
			header.add("厂家");
			header.add("库号");
			header.add("品种");
			header.add("颜色"); 
			header.add("备注");
		}
		if("硅橡胶".equals(sheet.getSheetName()) || "高温".equals(sheet.getSheetName())){
			header.add("品名");
			header.add("厂家");
			header.add("备注");
			header.add("库号");
		}
		if("成缆".equals(sheet.getSheetName())){
			header.add("品种");
			header.add("型号");
			header.add("厂家");
		}
		return header;
	}

	@Override
	public List<String> getCheckSectionByMonth(String yearMonth) {
		// TODO Auto-generated method stub
		return materialCheckReportDAO.getCheckSectionByMonth(yearMonth);
	}

	@Override
	public Integer countMaterialStockByMonth(Map<String, String> param) {
		// TODO Auto-generated method stub
		return materialCheckReportDAO.countMaterialStockByMonth(param);
	}

}
