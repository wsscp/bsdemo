package cc.oit.bsmes.common.log;

import cc.oit.bsmes.common.log.dao.BizInfoDAO;
import cc.oit.bsmes.common.log.dao.EntityInfoDAO;
import cc.oit.bsmes.common.log.model.BizInfo;
import cc.oit.bsmes.common.log.model.EntityInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
 


/**
 * 获取日志所需的基础数据
 * @author zhangdongping
 * $Id: LogMetaData.java 31169 2014-03-11 01:35:33Z zhangdongping $
 */
@Service
public class LogMetaData {
	@Resource
	private EntityInfoDAO entityInfoDAO;
	@Resource
	private BizInfoDAO bizInfoDAO;
	
	private static HashMap<String, EntityInfo> entityInfoMap;
	private static HashMap<String, BizInfo> bizInfoMap;	
 
	public HashMap<String, EntityInfo> getEntityInfoMap() {
		if (bizInfoMap == null || entityInfoMap == null) {
			init();
		} 
		return entityInfoMap;
	}
	
	/**
	 * 初始化操作
	 * @return
	 */
	public HashMap<String, BizInfo> getBizInfoMap() {
		if (bizInfoMap == null || entityInfoMap == null) {
			init();
		} 
		return bizInfoMap;
	}
	
	private void init() {
		if (entityInfoMap == null) {
			List<EntityInfo> list = entityInfoDAO.getAll();
			entityInfoMap = new HashMap<String, EntityInfo>(list == null ? 0 : list.size());
			for (EntityInfo entityInfo : list) {
				entityInfoMap.put(entityInfo.getId(), entityInfo);
			}
		}
		
		if (bizInfoMap == null) {
			BizInfo findParams=new BizInfo();
			findParams.setStatus(Boolean.TRUE);
			List<BizInfo> list = bizInfoDAO.get(findParams);
			bizInfoMap = new HashMap<String, BizInfo>(list == null ? 0 : list.size());
			for (BizInfo bizInfo : list) {
				bizInfoMap.put(bizInfo.getBizClass().concat(".").concat(bizInfo.getBizMethod()), bizInfo);
			}
		}
	}
 

}
