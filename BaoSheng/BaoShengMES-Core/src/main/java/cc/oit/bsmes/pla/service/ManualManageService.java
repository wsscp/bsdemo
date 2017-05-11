package cc.oit.bsmes.pla.service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.ManualManage;

public interface ManualManageService extends BaseService<ManualManage>{


	public Integer countContractNo(Map<String, Object> param);

	public List<ManualManage> getInfo(Map<String, Object> param);

	public void updateData(Map<String, Object> param);
	
	public void updateData1(Map<String, Object> param);
	
	public List<ManualManage> getContractNo(Map<String, Object> param);

	public List<ManualManage> getptFinishTime(Map<String, Object> param);

	public List<ManualManage> getInfoSources(Map<String, Object> findParams);
	
	public List<ManualManage> findForExport(JSONObject jsonParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
}
