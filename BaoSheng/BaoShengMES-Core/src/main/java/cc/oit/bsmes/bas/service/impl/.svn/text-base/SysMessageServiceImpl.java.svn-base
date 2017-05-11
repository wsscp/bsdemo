package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.SysMessageDAO;
import cc.oit.bsmes.bas.model.SysMessage;
import cc.oit.bsmes.bas.service.SysMessageService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import cc.oit.bsmes.common.mybatis.complexQuery.WithValueQueryParam;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessage> implements SysMessageService{

	@Resource
	private SysMessageDAO sysMessageDAO;

    @Override
    public List<SysMessage> findByRequestMap(Map<String, Object> requestMap,
                                             int start, int limit, List<Sort> sortList) {
        SqlInterceptor.setRowBounds(new RowBounds(start,limit));
        return sysMessageDAO.findByRequestMap(requestMap);
    }

	@Override
	public Integer countByRequestMap(Map<String, Object> requestMap) {
		return sysMessageDAO.countByRequestMap(requestMap);
	}

    @Override
    public void sendMessage(String receiverCode, String title, String content) {
        SysMessage sysMessage = new SysMessage();
        sysMessage.setMessageReceiver(receiverCode);
        sysMessage.setMessageContent(content);
        sysMessage.setMessageTitle(title);
        sysMessage.setHasread(false);
        sysMessage.setReceiveTime(new Date());
        insert(sysMessage);
    }

    @Override
    public JSONObject getNewMessage(String userCode) {
        List<CustomQueryParam> findParams = new ArrayList<CustomQueryParam>();
        findParams.add(new WithValueQueryParam("isNew", "=", true));
        findParams.add(new WithValueQueryParam("messageReceiver", "=", userCode));
        List<Sort> sortList = new ArrayList<Sort>();
        sortList.add(new Sort("receiveTime", Sort.Direction.DESC));
        List<SysMessage> newMessages = sysMessageDAO.query(findParams, sortList);
        SysMessage findParam = new SysMessage();
        findParam.setHasread(false);
        findParam.setMessageReceiver(userCode);
        List<SysMessage> unreadMessages = sysMessageDAO.find(findParam);

        JSONObject object = new JSONObject();
        object.put("size", newMessages.size());
        object.put("unreadSize", unreadMessages.size());
        if (newMessages.size() == 0) {
            return object;
        }
        SysMessage sysMessage = newMessages.get(0);
        object.put("messageId", sysMessage.getId());
        object.put("message", sysMessage.getMessageContent());
        object.put("title", sysMessage.getMessageTitle());

        sysMessageDAO.updateNewMessage(userCode);

        return object;
    }

    @Override
    public void readMessage(String id) {
        sysMessageDAO.read(id);
    }

}
