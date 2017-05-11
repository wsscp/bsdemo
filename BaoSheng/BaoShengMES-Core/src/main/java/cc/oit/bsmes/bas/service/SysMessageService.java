package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.SysMessage;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface SysMessageService extends BaseService<SysMessage>{
	
	public List<SysMessage> findByRequestMap(Map<String, Object> requestMap, int start,int limit, List<Sort> sortList);
    
    public Integer countByRequestMap(Map<String, Object> requestMap);

    public void sendMessage(String receiverCode, String title, String content);

    public JSONObject getNewMessage(String userCode);

    public void readMessage(String id);
}
