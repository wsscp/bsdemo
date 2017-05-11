package cc.oit.bsmes.interfacePLM.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.Scx;
import cc.oit.bsmes.interfacePLM.model.Scxk;

/**
 * Created by JinHy on 2014/9/28 0028.
 */
public interface ScxDAO extends BaseDAO<Scx>{
    List<Scx> lastUpdateData(Map<String,Date> findParams);

    /**
	 * 获取PLM更新过的数据
	 * 
	 * @param processId
	 *            工序Id
	 * @return List<Scx>
	 */
	public List<Scx> getAsyncDataList(String processId);
	
	public void insertScx(Scxk param);
	
	public void deleteScxByPrcvNo(String param);

}
