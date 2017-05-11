package cc.oit.bsmes.pla.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.ReflectUtils;
import cc.oit.bsmes.pla.dao.ManualManageDAO;
import cc.oit.bsmes.pla.model.ManualManage;
import cc.oit.bsmes.pla.service.ManualManageService;

@Service
public class ManualManageServiceimpl extends BaseServiceImpl<ManualManage>  implements ManualManageService{

	@Resource
	private ManualManageDAO manualManageDAO;

	@Override
	public Integer countContractNo(Map<String, Object> param) {
		return manualManageDAO.countContractNo(param);
	}
	
	@Override
	public void updateData(Map<String, Object> param) {
		manualManageDAO.updateData(param);
	}

	@Override
	public List<ManualManage> getInfo(Map<String, Object> param) {
		return manualManageDAO.getInfo(param);
	}


	@Override
	public List<ManualManage> getContractNo(Map<String, Object> param) {
		return manualManageDAO.getContractNo(param);
	}


	@Override
	public List<ManualManage> getptFinishTime(Map<String, Object> param) {
		return manualManageDAO.getptFinishTime(param);
	}


	@Override
	public List<ManualManage> getInfoSources(Map<String, Object> param) {
		return manualManageDAO.getInfoSources(param);
	}

	@Override

	public void updateData1(Map<String, Object> param) {
		manualManageDAO.updateData1(param);
	}
	
	
	@Override
	public List<ManualManage> findForExport(JSONObject queryParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        return manualManageDAO.getAllInfo();
    }

}
