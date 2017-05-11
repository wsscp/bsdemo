package cc.oit.bsmes.wip.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.bas.dao.EmployeeDAO;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.wip.model.StoreManage;
import cc.oit.bsmes.wip.service.StoreManageService;
import cc.oit.bsmes.wip.dao.StoreManageDAO;

@Service
public class StoreManageServiceImpl extends BaseServiceImpl<StoreManage> implements StoreManageService{
	
	@Resource
	private StoreManageDAO storeManageDAO;
	@Resource
	private EmployeeDAO employeeDAO;
	
	public List<StoreManage> getUserInfo(){
		return storeManageDAO.getUserInfo();
	}
	
	public void insertNewData(Map<String, Object> param){
		storeManageDAO.insertNewData(param);
	}
	
	public void updateData(Map<String, Object> param){
		storeManageDAO.updateData(param);
	}
	
	public void deleteData(Map<String, Object> param){
		storeManageDAO.deleteData(param);
	}
	
	public List<StoreManage> findResult(Map<String, Object> param,int start,int limit, List<Sort> sortList){
		SqlInterceptor.setRowBounds(new RowBounds(start,limit));
		return storeManageDAO.findResult(param);
	}

	@Override
	public void importScrapSub(Sheet sheet, JSONObject result, String year) {
		// TODO Auto-generated method stub
		Row first_row = sheet.getRow(0);
		String fb = first_row.getCell(0).getStringCellValue();
		fb = fb.substring(fb.indexOf("月")-2);
		fb = fb.replace("月", "-");
		fb = fb.replace("日", "");
		if("12-26".equals(fb.trim())){
			year = Integer.toString(Integer.parseInt(year) - 1);
		}
		Date statDate = DateUtils.convert(year+"-"+fb.trim(), "yyyy-MM-dd");
		
		for(int i=3;i<72;i++){
			Row row = sheet.getRow(i);
//			StoreManage s = new StoreManage();
			if(i==24 || i==62 || i==71 || i==72){
				continue;
			}
//			try{
//				s.setStatDate(statDate);
				if(null != row.getCell(1) && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK){
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					String str = row.getCell(1).getStringCellValue();
					String[] names = str.split(",");
					for(String name : names){
						List<Employee> e = employeeDAO.getByName(name.trim());
						StoreManage s = new StoreManage();
						if(e.isEmpty()){
							continue;
						}
						s.setStatDate(statDate);
						s.setUserCode(e.get(0).getUserCode());
						s.setName(name.trim());
						if(null != row.getCell(2) && row.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(2).getStringCellValue())){
								s.setFeiTong(row.getCell(2).getStringCellValue());
							}else{
								s.setFeiTong(0.0+"");
							}
						}else{
							s.setFeiTong(0.0+"");
						}
						if(null != row.getCell(3) && row.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(3).getStringCellValue())){
								s.setFeiXian(row.getCell(3).getStringCellValue());
							}else{
								s.setFeiXian(0.0+"");
							}
						}else{
							s.setFeiXian(0.0+"");
						}
						if(null != row.getCell(4) && row.getCell(4).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(4).getStringCellValue())){
								s.setJiaoLian(row.getCell(4).getStringCellValue());
							}else{
								s.setJiaoLian(0.0+"");
							}
						}else{
							s.setJiaoLian(0.0+"");
						}
						if(null != row.getCell(5) && row.getCell(5).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(5).getStringCellValue())){
								s.setZaLiao(row.getCell(5).getStringCellValue());
							}else{
								s.setZaLiao(0.0+"");
							}
						}else{
							s.setZaLiao(0.0+"");
						}
						if(null != row.getCell(6) && row.getCell(6).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(6).getStringCellValue())){
								s.setFsLiao(row.getCell(6).getStringCellValue());
							}else{
								s.setFsLiao(0.0+"");
							}
						}else{
							s.setFsLiao(0.0+"");
						}
						if(null != row.getCell(7) && row.getCell(7).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(7).getStringCellValue())){
								s.setWuLu(row.getCell(7).getStringCellValue());
							}else{
								s.setWuLu(0.0+"");
							}
						}else{
							s.setWuLu(0.0+"");
						}
						if(null != row.getCell(8) && row.getCell(8).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(8).getStringCellValue())){
								s.setTheorySlopLine(row.getCell(8).getStringCellValue());
							}else{
								s.setTheorySlopLine(0.0+"");
							}
						}else{
							s.setTheorySlopLine(0.0+"");
						}
						if(null != row.getCell(9) && row.getCell(9).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(9).getStringCellValue())){
								s.setJiechaoSlopLine(row.getCell(9).getStringCellValue());
							}else{
								s.setJiechaoSlopLine(0.0+"");
							}
						}else{
							s.setJiechaoSlopLine(0.0+"");
						}
						if(null != row.getCell(10) && row.getCell(10).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(10).getStringCellValue())){
								s.setSlopLineDeductions(row.getCell(10).getStringCellValue());
							}else{
								s.setSlopLineDeductions(0.0+"");
							}
						}else{
							s.setSlopLineDeductions(0.0+"");
						}
						if(null != row.getCell(11) && row.getCell(11).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(11).getStringCellValue())){
								s.setRealityMaterialPro(row.getCell(11).getStringCellValue());
							}else{
								s.setRealityMaterialPro(0.0+"");
							}
						}else{
							s.setRealityMaterialPro(0.0+"");
						}
						if(null != row.getCell(12) && row.getCell(12).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(12).getStringCellValue())){
								s.setTheoryWaste(row.getCell(12).getStringCellValue());
							}else{
								s.setTheoryWaste(0.0+"");
							}
						}else{
							s.setTheoryWaste(0.0+"");
						}
						if(null != row.getCell(13) && row.getCell(13).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(13).getStringCellValue())){
								s.setRealityWaste(row.getCell(13).getStringCellValue());
							}else{
								s.setRealityWaste(0.0+"");
							}
						}else{
							s.setRealityWaste(0.0+"");
						}
						if(null != row.getCell(14) && row.getCell(14).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(14).getStringCellValue())){
								s.setWasteDeductions(row.getCell(14).getStringCellValue());
							}else{
								s.setWasteDeductions(0.0+"");
							}
						}else{
							s.setWasteDeductions(0.0+"");
						}
						if(null != row.getCell(15) && row.getCell(15).getCellType() != Cell.CELL_TYPE_BLANK){
							row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
							if(isNum(row.getCell(15).getStringCellValue())){
								s.setRewardPunish(row.getCell(15).getStringCellValue());
							}else{
								s.setRewardPunish(0.0+"");
							}
						}else{
							s.setRewardPunish(0.0+"");
						}
						storeManageDAO.insert(s);
					}
				}else{
					continue;
				}
		}
		for(int i=3;i<69;i++){
			Row row = sheet.getRow(i);
			if(i==36){
				continue;
			}
			if(null != row.getCell(17) && row.getCell(17).getCellType() != Cell.CELL_TYPE_BLANK){
				row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
				String str = row.getCell(17).getStringCellValue();
				String[] names = str.split(",");
				for(String name : names){
					List<Employee> e = employeeDAO.getByName(name.trim());
					if(e.isEmpty()){
						continue;
					}
					StoreManage s = new StoreManage();
					s.setStatDate(statDate);
					s.setUserCode(e.get(0).getUserCode());
					s.setName(name.trim());
					if(null != row.getCell(18) && row.getCell(18).getCellType() != Cell.CELL_TYPE_BLANK){
						row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
						if(isNum(row.getCell(18).getStringCellValue())){
							s.setFeiTong(row.getCell(18).getStringCellValue());
						}else{
							s.setFeiTong(0.0+"");
						}
					}else{
						s.setFeiTong(0.0+"");
					}
					if(null != row.getCell(19) && row.getCell(19).getCellType() != Cell.CELL_TYPE_BLANK){
						row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
						if(isNum(row.getCell(19).getStringCellValue())){
							s.setFeiXian(row.getCell(19).getStringCellValue());
						}else{
							s.setFeiXian(0.0+"");
						}
					}else{
						s.setFeiXian(0.0+"");
					}
					storeManageDAO.insert(s);
				}
			}else{
				continue;
			}
			result.put("success", true);
			result.put("message", "导入成功");
		}
		
		
	}
	
	public boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	@Override
	public int countStoreMange(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return storeManageDAO.countStoreMange(params);
	}

}
