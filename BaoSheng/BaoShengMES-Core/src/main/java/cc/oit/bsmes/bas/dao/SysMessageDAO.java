package cc.oit.bsmes.bas.dao;


import cc.oit.bsmes.bas.model.SysMessage;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 系统消息dao类
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2013-12-16 上午9:24:51
 * @since
 * @version
 */
public interface SysMessageDAO extends BaseDAO<SysMessage>{ 
	
	public List<SysMessage> findByRequestMap(Map<String, Object> requestMap);
    
    public Integer countByRequestMap(Map<String, Object> requestMap);

    public void read(String id);

    public void updateNewMessage(String userCode);
}
