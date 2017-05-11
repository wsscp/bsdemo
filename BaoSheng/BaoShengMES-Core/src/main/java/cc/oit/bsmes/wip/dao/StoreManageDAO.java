package cc.oit.bsmes.wip.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.StoreManage;

public interface StoreManageDAO extends BaseDAO<StoreManage> {

	public List<StoreManage> getUserInfo();
	
	public void insertNewData(Map<String, Object> param);
	
	public void updateData(Map<String, Object> param);
	
	public void deleteData(Map<String, Object> param);
	
	public List<StoreManage> findResult(Map<String, Object> param);

	public int countStoreMange(Map<String, Object> params);

}
