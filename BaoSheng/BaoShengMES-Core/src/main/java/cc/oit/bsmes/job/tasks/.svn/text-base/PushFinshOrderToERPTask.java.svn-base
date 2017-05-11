package cc.oit.bsmes.job.tasks;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.wip.model.FinishOrderItem;
import cc.oit.bsmes.wip.model.FinishOrderItemErpModel;
import cc.oit.bsmes.wip.service.FinishOrderItemService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.constants.OrderPushStatus;
import cc.oit.bsmes.interfaceWebService.InstantiateService;

@Service
public class PushFinshOrderToERPTask extends BaseSimpleTask{

	@Resource
	private FinishOrderItemService finishOrderItemService;
	@Resource
	private SalesOrderService salesOrderService;

	
	/**
	 * <p>
	 * 完成订单信息推动到ERP
	 * </p>
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void process(JobParams parms) {		
		try {
			List<FinishOrderItemErpModel> list =finishOrderItemService.getNeedPushOrderItems();
			if(list!=null&&list.size()>0)
			{
				InstantiateService instantiateService=new InstantiateService();
				String jsonText=JSON.toJSONString(list);
				System.out.println("推送数据："+jsonText);

				String returnJson=instantiateService.saveJJD(jsonText);
				System.out.println("返回数据："+returnJson);
				updateFinishOrderItem(returnJson);
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}
	
	/**
	 * 根据返回的JSON信息更新完成订单信息
	 * @param jsonTex
	 */
	private void updateFinishOrderItem(String jsonText)
	{
		JSONArray jsonArray = JSONArray.parseArray(jsonText);
		
		for (int i = 0; i < jsonArray.size(); i++) {
			// 获取返回信息对象
			JSONObject object = jsonArray.getJSONObject(i);
			
			FinishOrderItem item=finishOrderItemService.getById(object.get("id").toString());
			if(item!=null)
			{
				item.setPushStatus(OrderPushStatus.valueOf(object.get("result").toString().toUpperCase()));
				item.setPushMessage(object.get("message").toString());
				item.setPushNum(item.getPushNum()+1);
				item.setPushTime(new Date());
				finishOrderItemService.update(item);
			}
		}
	}
}
