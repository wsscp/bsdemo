package cc.oit.webservice.endpoint;

import javax.annotation.Resource;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import cc.oit.webservice.beans.ERPOrderSyncService;
import cc.oit.webservice.beans.SaveSalesOrder;
import cc.oit.webservice.beans.SaveSalesOrderItem;
import cc.oit.webservice.beans.SaveSalesOrderItemResponse;
import cc.oit.webservice.beans.SaveSalesOrderResponse;

/**
 * @author YuYang(zdsoft.yang@foxmail.com)
 *
 * @date 2014年5月2日
 */

@Endpoint
public class ErpServiceEndpoint {
	
	
	//UserService.wsdl声明的命名空间
    public static final String USERVICE_NAMESPACE = "http://www.example.org/erpservice/";

	@Resource(name="erpService")
	private ERPOrderSyncService erpService;
	
	private static Object synObject = new Object();
	
	@PayloadRoot(namespace = USERVICE_NAMESPACE, localPart = "saveSalesOrderItem")
	@ResponsePayload
	public SaveSalesOrderItemResponse HandelGetSaveOrderItem(@RequestPayload SaveSalesOrderItem request) {
		synchronized(synObject) { 
			String info = erpService.saveSalesOrderItem(request.getJsonText());
			SaveSalesOrderItemResponse response = new SaveSalesOrderItemResponse();
			response.setInfo(info);
			return response;
		}
	}
	
	@PayloadRoot(namespace = USERVICE_NAMESPACE, localPart = "saveSalesOrder")
	@ResponsePayload
	public SaveSalesOrderResponse HandelGetSaveOrder(@RequestPayload SaveSalesOrder request) {
		synchronized(synObject) { 
			String info = erpService.saveSalesOrder(request.getJsonText());
			SaveSalesOrderResponse response = new SaveSalesOrderResponse();
			response.setInfo(info);
			return response;
		}
	}
}
