package cc.oit.bsmes.wip.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.StoreManage;

public interface StoreManageService extends BaseService<StoreManage>{
	
	public List<StoreManage> getUserInfo();
	
	public void insertNewData(Map<String, Object> param);
	
	public void updateData(Map<String, Object> param);
	
	public void deleteData(Map<String, Object> param);
	
	public List<StoreManage> findResult(Map<String, Object> param,int start,int limit, List<Sort> sortList);

	public void importScrapSub(Sheet sheet, JSONObject result, String year);

	public int countStoreMange(Map<String, Object> params);

}
