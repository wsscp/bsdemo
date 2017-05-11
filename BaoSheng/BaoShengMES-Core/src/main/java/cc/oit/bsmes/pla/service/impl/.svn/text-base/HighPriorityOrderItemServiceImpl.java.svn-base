package cc.oit.bsmes.pla.service.impl;

import javax.annotation.Resource;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pla.dao.HighPriorityOrderItemDAO;
import cc.oit.bsmes.pla.model.HighPriorityOrderItem;
import cc.oit.bsmes.pla.service.HighPriorityOrderItemService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Calendar;
import java.util.Date;

@Service
@Log
public class HighPriorityOrderItemServiceImpl extends BaseServiceImpl<HighPriorityOrderItem> implements
		HighPriorityOrderItemService {

	@Resource
	private HighPriorityOrderItemDAO  highPriorityOrderItemDAO;

	@Override
	public void updateSeq(String rightJsonText, String leftJsonText) {
        Calendar calendar = Calendar.getInstance();
        log.info("now Time:"+calendar.getTime());

		JSONArray jsonArray = JSON.parseArray(rightJsonText);
		for(Object object : jsonArray){
			JSONObject jsonObject = (JSONObject) object;
			HighPriorityOrderItem orderItem = new HighPriorityOrderItem();
            orderItem.setId(jsonObject.getString("id"));
            Integer seq = jsonObject.getInteger("seq");
			orderItem.setSeq(seq == null?null:seq+1);
			if(StringUtils.isBlank(jsonObject.getString("highPriorityId"))){
                highPriorityOrderItemDAO.insert(orderItem);
            }else{
                highPriorityOrderItemDAO.update(orderItem);
            }
		}
		
		jsonArray = JSON.parseArray(leftJsonText);
		for(Object object : jsonArray){
			JSONObject jsonObject = (JSONObject) object;
			String highPriorityId  =  jsonObject.getString("highPriorityId");
			if(StringUtils.isNotBlank(highPriorityId)){
				highPriorityOrderItemDAO.updateSeq(highPriorityId);
			}
		}
	}
}
