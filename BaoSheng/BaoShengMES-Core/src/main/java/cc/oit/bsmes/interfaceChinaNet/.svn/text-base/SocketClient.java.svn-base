package cc.oit.bsmes.interfaceChinaNet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.bas.dao.DataDicDAO;
import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.common.constants.SalesOrderStatus;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfaceWWAc.dao.EquipParamAcquisitionDAO;
import cc.oit.bsmes.interfaceWWAc.dao.EquipParamHistoryAcquisitionDAO;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamAcquisition;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamHistoryAcquisition;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderService;

@Service
@Scope("prototype")
@Transactional(readOnly=false)
public class SocketClient {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	private Properties props;
	private String ip;//服务端ip
	private int port; //服务端端口号
	private String username;
	private String pwd;
	private Socket socket;
	private boolean IS_VALIDATE=false;     //是否验证用户名 和密码
	private boolean IS_CONNECTION =	false;  //socket是否连接
	private boolean EQUIP_CACHE=false;     //是否验证用户名 和密码
	private int TIMESTAMP = 0; //时戮
	private OutputStream out = null;
	private InputStream in = null;
	private Map<String,Map<Date,String>> equipInfo = new HashMap<String,Map<Date,String>>();
	//用于存储上一次发送的实时数据和订单数据
	private JSONObject dataJSON = new JSONObject();
	
	@Resource
	private SalesOrderService salesOrderService;
	@Resource
	private SalesOrderItemService salesOrderItemService;
	@Resource
	private CustomerOrderService customerOrderService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private EquipParamAcquisitionDAO equipParamAcquisitionDAO;
	@Resource
	private EquipParamHistoryAcquisitionDAO equipParamHistoryAcquisitionDAO;
	@Resource
	private DataDicDAO dataDicDAO;
	
	//初始化操作，读取properties文件，创建socket
	public SocketClient(){
		props = new Properties();
		InputStream props_in = SocketClient.class.getResourceAsStream("socket.properties");
		try {
			props.load(props_in);
			this.ip = props.getProperty("ip");
			this.port = Integer.parseInt(props.getProperty("port"));
			this.username = props.getProperty("username");
			this.pwd = props.getProperty("password");
			this.socket = new Socket(ip, port);
			socket.setKeepAlive(true);
			this.out = socket.getOutputStream();
			this.in = socket.getInputStream();
			IS_CONNECTION = true;
		} catch (IOException e) {
			log.info("Socket初始化失败");
			IS_CONNECTION = false;
		}
	}
	private void reconnect(){
		try {
			System.out.println("重新链接:"+DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT));
			socket = new Socket(ip, port);
			socket.setKeepAlive(true);
			out = socket.getOutputStream();
			in = socket.getInputStream();
			TIMESTAMP = 0;
			IS_CONNECTION =  true;
			IS_VALIDATE = false;
			dataJSON.clear();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	/**
	 * 数据格式 m_bUpdate第一次发送为false，以后都为true,
	 * 其中保留“公司ID”是为了程序通用性，你们可以发送任意ID，我们接收的时候会跳过解析”公司ID”
	 * BS16083122571GW-ZR-YJV22-0.6/1kV-4*16：合同号+客户型号规格
	 * 444-69-B036.R_Length：设备以及对应得参数
	 * m_bDelete：订单是否完成，完成则为true
	 *{"Current": {"m_bUpdate": false,"m_dicOrder": {"公司ID": {"BS16083122571GW-ZR-YJV22-0.6/1kV-4*16": {"444-69-B036.R_Length": {"m_bDelete": false}}}}}}
	 */
	
	//获取当前正在生产的数据
	private String readCurrentData(){
		JSONObject currentDate = new JSONObject();
		JSONObject current = new JSONObject();
		JSONObject dicCompany = new JSONObject();
		JSONObject dicItem = new JSONObject();
		List<Map<String,String>> orders = customerOrderItemService.getGWInGrocessOrders();
		//当前没有国网数据
		if(orders == null || orders.size() == 0){
			currentDate.put("Current", current);
			return currentDate.toJSONString();
		}
		JSONObject orderItem = new JSONObject();
		//获取实时订单
		for(Map<String,String> order : orders){
			//获取设备所有的tagName
			String params = this.getParams(order.get("EQUIP_CODE"));
			if(params == null){
				continue;
			}
			String[] statusBasisWw =  params.split(",");
			if(statusBasisWw == null){
				continue;
			}
			List<String> tagName = new ArrayList<String>();
			for(String str : statusBasisWw){
				tagName.add(str);
			}
			List<EquipParamAcquisition> equipParamAcquisitions =  equipParamAcquisitionDAO.findLiveValue(tagName, null, null);
			JSONObject aaa = new JSONObject();
			if(orderItem.containsKey(order.get("SALES_ORDER_ITEM_ID"))){
				aaa = orderItem.getJSONObject(order.get("SALES_ORDER_ITEM_ID"));
				orderItem.remove(order.get("SALES_ORDER_ITEM_ID"));
			}
			for(EquipParamAcquisition temp: equipParamAcquisitions){
				JSONObject bbb = new JSONObject();
				bbb.put("bDelete", false);
				bbb.put("fValue", temp.getVvalue());
				aaa.put(temp.getTagname(), bbb);
			}
			orderItem.put(order.get("SALES_ORDER_ITEM_ID"), aaa);
		}
		if(orderItem.isEmpty() || orderItem.equals(dataJSON)){
			currentDate.put("Current", current);
			return currentDate.toJSONString();
		}
		dataJSON = orderItem;
		dicItem.put("dicItem", orderItem);
		dicCompany.put("宝胜电缆", dicItem);
		if(TIMESTAMP == 0){
			current.put("bUpdate", false);
		}else{
			current.put("bUpdate", true);
		}
		current.put("dicCompany", dicCompany);
		currentDate.put("Current", current);
		return currentDate.toJSONString();
	}
	//获取订单信息
	public String readOrderData(){
		JSONObject orderData = new JSONObject();
		JSONObject dicCompany_ = new JSONObject();
		JSONObject dicCompany = new JSONObject();
		JSONObject dicOrder = new JSONObject();
		JSONObject orders = new JSONObject();
		List<SalesOrder> list = salesOrderService.getSalesOrderByGW();
		//查询订单信息
		for(SalesOrder salesOrder: list){
			CustomerOrder customerOrder = customerOrderService.getBySalesOrderId(salesOrder.getId()).get(0);
			if(customerOrder == null){
				continue;
			}
			JSONObject order = new JSONObject();
			order.put("Project", "");
			order.put("CompanyIssue",salesOrder.getCustomerCompany() == null?"":salesOrder.getCustomerCompany());
			order.put("TimeStart", customerOrder.getPlanStartDate() == null ? "":DateUtils.convert(customerOrder.getPlanStartDate(), DateUtils.DATE_FORMAT));
			order.put("TimeFinish", customerOrder.getPlanFinishDate() ==null ?"":DateUtils.convert(customerOrder.getPlanFinishDate(),DateUtils.DATE_FORMAT));
			JSONObject orderItems = new JSONObject();
			SalesOrderItem item = new SalesOrderItem();
			item.setSalesOrderId(salesOrder.getId());
			item.setStatus(SalesOrderStatus.IN_PROGRESS);
			List<SalesOrderItem> itemList = salesOrderItemService.findByObj(item);
			for(SalesOrderItem salesOrderItem:itemList){
				CustomerOrderItem customerOrderItem = customerOrderItemService.getByOrderItemId(salesOrderItem.getId());
				if(customerOrderItem == null ||salesOrderItem.getContractLength() == null
						|| salesOrderItem.getCustProductType() == null || salesOrderItem.getCustProductSpec() == null){
					continue;
				}
				JSONObject orderItem = new JSONObject();
				orderItem.put("TimeStart", customerOrderItem.getPlanStartDate() == null?"":DateUtils.convert(customerOrderItem.getPlanStartDate(), DateUtils.DATE_FORMAT));
				orderItem.put("TimeFinish", customerOrderItem.getPlanFinishDate() == null?"":DateUtils.convert(customerOrderItem.getPlanFinishDate(), DateUtils.DATE_FORMAT));
				orderItem.put("Length", salesOrderItem.getContractLength());
				orderItem.put("Spec", salesOrderItem.getCustProductType()+" "+salesOrderItem.getCustProductSpec());
				orderItems.put(salesOrderItem.getId(), orderItem);
			}
			if(orderItems.isEmpty()){
				continue;
			}
			order.put("dicItem", orderItems);
			orders.put(salesOrder.getId(), order);
		}
		if(orders.isEmpty() || orders.equals(dataJSON)){
			return null;
		}
		dataJSON = orders;
		dicOrder.put("dicOrder", orders);
		dicCompany.put("宝胜电缆", dicOrder);
		dicCompany_.put("dicCompany", dicCompany);
		orderData.put("Order", dicCompany_);
		return orderData.toJSONString();
	}
	
	private void initEquipInfoMap(){
		if(!equipInfo.isEmpty()){
			return;
		}
		List<Map<String,String>> list = equipInfoService.getEquipParams();
		for(Map<String,String> info : list){
			Map<Date,String> m = new HashMap<Date, String>();
			m.put(DateUtils.convert(info.get("SERCHTIME")), info.get("TAG_NAME"));
			equipInfo.put(info.get("EQUIPCODE"), m);
		}
		this.EQUIP_CACHE = true;
	}
	
	//获取历史数据
	public String readHistoryData(Date startDate,Date endDate){
		try{
		JSONObject history = new JSONObject();
		JSONObject dicCompany = new JSONObject();
		JSONObject dicCompany_ = new JSONObject();
		JSONObject dicItem = new JSONObject();
		JSONObject dicOrders = new JSONObject();
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		//获取历史数据
		List<Map<String,String>> historyData = salesOrderItemService.getFinishedGWData(param);
		if(historyData.isEmpty()){
			return null;
		}
		//组装历史数据
		for(Map<String,String> data:historyData){
			JSONObject order = new JSONObject();
			String[] hisWorkOrders = data.get("WORK_ORDER_NO").split(",");
			String[] processName = data.get("PROCESS_NAME").split(",");
			for(int i=0;i<hisWorkOrders.length;i++){
				Map<String,Object> param1 = new HashMap<String, Object>();
				param1.put("workOrderNo", hisWorkOrders[i]);
				param1.put("startDate", startDate);
				param1.put("endDate", endDate);
				List<Map<String,String>> equipCodes = equipInfoService.getByWorkOrder(param1);
				for(Map<String,String> equipCode:equipCodes){
					JSONObject batch = new JSONObject();
					JSONObject dicBatch = new JSONObject();
					batch.put("Step", processName[i]);
					batch.put("Equipment", equipCode.get("EQUIP_ALIAS"));
					batch.put("StartTime", equipCode.get("PRE_START_TIME"));
					batch.put("FinishTime", equipCode.get("PRE_END_TIME"));
					String code = equipCode.get("EQUIP_CODE");
					//获取设备的所有码点名
					String params = this.getParams(code);
					JSONObject listParamValue = new JSONObject();
					for(String tagName : params.split(",")){
						JSONArray listParamValues = new JSONArray();
						//因为切换生产订单的缘故，这个设备可能有多个生产时间段
						for(String workTime: equipCode.get("WORK_TIME").split(",")){
							String[] times = workTime.split("T");
							Date start_time = null;
							Date end_time = null;
							if(times.length==2){
								start_time =  DateUtils.convert(times[0]);
								end_time =  DateUtils.convert(times[1]);
							}else{
								start_time =  DateUtils.convert(times[0]);
								end_time = endDate;
							}
							System.err.println(DateUtils.convert(startDate, DateUtils.DATE_TIMESTAMP_SHORT_FORMAT));
							System.err.println(DateUtils.convert(endDate, DateUtils.DATE_TIMESTAMP_SHORT_FORMAT));
							System.err.println(DateUtils.convert(start_time, DateUtils.DATE_TIMESTAMP_SHORT_FORMAT));
							System.err.println(DateUtils.convert(end_time, DateUtils.DATE_TIMESTAMP_SHORT_FORMAT));
							//获取码点集
							this.getParamValues(tagName,startDate,endDate,start_time,end_time,listParamValues);
						}
						if(listParamValues.isEmpty()){
							continue;
						}
						listParamValue.put(tagName, listParamValues);
					}
//					batch.put("ListParam", params);
					if(listParamValue.isEmpty()){
						continue;
					}
					batch.put("ListParamValue", listParamValue);
					dicBatch.put(data.get("SALES_ORDER_ITEM_ID") +"."+hisWorkOrders[i]+"."+equipCode.get("EQUIP_CODE"), batch);
					order.put("dicBatch", dicBatch);
				}
			}
			if(order.isEmpty()){
				continue;
			}
			dicOrders.put(data.get("SALES_ORDER_ITEM_ID"), order);
		}
		if(dicOrders.isEmpty()){
			return null;
		}
		dicItem.put("dicItem", dicOrders);
		dicCompany_.put("宝胜电缆", dicItem);
		dicCompany.put("dicCompany", dicCompany_);
		history.put("History", dicCompany);
		return history.toJSONString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	//获取码点名
	private String getParams(String code) {
		if(equipInfo.containsKey(code)){
			Date date = equipInfo.get(code).keySet().iterator().next();
			if(DateUtils.getTimeDiff(date,new Date())>2){
				equipInfo.remove(code);
				Map<Date,String> m = new HashMap<Date, String>();
				String params = equipInfoService.getEquipParamByCode(code+"_EQUIP");
				m.put(new Date(), params);
				equipInfo.put(code, m);
				return params;
			}else{
				return equipInfo.get(code).get(date);
			}
		}else{
			String params = equipInfoService.getEquipParamByCode(code+"_EQUIP");
			Map<Date,String> m = new HashMap<Date, String>();
			m.put(new Date(), params);
			equipInfo.put(code, m);
			return params;
		}
	}
	//获取设备码点集
	private void getParamValues(String tagName, Date startDate, Date endDate,
			Date start_time, Date end_time, JSONArray listParamValues) {
		EquipParamHistoryAcquisition equipParamHistoryAcquisition = new EquipParamHistoryAcquisition();
		// 生产时间在这个时间段之间
		if (DateUtils.getSecondsDiff(startDate, start_time) >= 0
				&& DateUtils.getSecondsDiff(start_time, end_time) >= 0
				&& DateUtils.getSecondsDiff(end_time, endDate) >= 0) {
			equipParamHistoryAcquisition.setTagname(tagName);
			equipParamHistoryAcquisition.setStartTime(start_time);
			equipParamHistoryAcquisition.setEndTime(end_time);
			equipParamHistoryAcquisition.setWwcyclecount(DateUtils
					.getSecondsDiff(start_time, end_time).intValue());
			List<EquipParamHistoryAcquisition> list = equipParamHistoryAcquisitionDAO
					.findParamHistory(equipParamHistoryAcquisition);
			for (EquipParamHistoryAcquisition hisData : list) {
				JSONObject paramValue = new JSONObject();
				paramValue.put("Time", DateUtils.convert(hisData.getDatetime(),
						DateUtils.DATE_TIME_FORMAT));
				JSONObject value = new JSONObject();
				value.put("value_real", hisData.getVvalue());
				paramValue.put("Value", value);
				if(hisData.getVvalue() == null || hisData.getVvalue() == ""){
					paramValue.put("Status", "0");
				}else{
					paramValue.put("Status", "3");
				}
				listParamValues.add(paramValue);
			}
		}
		if (DateUtils.getSecondsDiff(start_time, startDate) >= 0
				&& DateUtils.getSecondsDiff(startDate, end_time) >= 0
				&& DateUtils.getSecondsDiff(end_time, endDate) >= 0) {
			equipParamHistoryAcquisition.setTagname(tagName);
			equipParamHistoryAcquisition.setStartTime(startDate);
			equipParamHistoryAcquisition.setEndTime(end_time);
			equipParamHistoryAcquisition.setWwcyclecount(DateUtils
					.getSecondsDiff(startDate, end_time).intValue());
			List<EquipParamHistoryAcquisition> list = equipParamHistoryAcquisitionDAO
					.findParamHistory(equipParamHistoryAcquisition);
			for (EquipParamHistoryAcquisition hisData : list) {
				JSONObject paramValue = new JSONObject();
				paramValue.put("Time", DateUtils.convert(hisData.getDatetime(),
						DateUtils.DATE_TIME_FORMAT));
				JSONObject value = new JSONObject();
				value.put("value_real", hisData.getVvalue());
				paramValue.put("Value", value);
				if(hisData.getVvalue() == null || hisData.getVvalue() == ""){
					paramValue.put("Status", "0");
				}else{
					paramValue.put("Status", "3");
				}
				listParamValues.add(paramValue);
			}
		}
		if (DateUtils.getSecondsDiff(start_time,startDate) >= 0
				&& DateUtils.getSecondsDiff(startDate,endDate) >= 0
				&& DateUtils.getSecondsDiff(endDate, end_time) >= 0) {
			equipParamHistoryAcquisition.setTagname(tagName);
			equipParamHistoryAcquisition.setStartTime(startDate);
			equipParamHistoryAcquisition.setEndTime(endDate);
			equipParamHistoryAcquisition.setWwcyclecount(DateUtils
					.getSecondsDiff(startDate, endDate).intValue());
			List<EquipParamHistoryAcquisition> list = equipParamHistoryAcquisitionDAO
					.findParamHistory(equipParamHistoryAcquisition);
			for (EquipParamHistoryAcquisition hisData : list) {
				JSONObject paramValue = new JSONObject();
				paramValue.put("Time", DateUtils.convert(hisData.getDatetime(),
						DateUtils.DATE_TIME_FORMAT));
				JSONObject value = new JSONObject();
				value.put("value_real", hisData.getVvalue());
				paramValue.put("Value", value);
				if(hisData.getVvalue() == null || hisData.getVvalue() == ""){
					paramValue.put("Status", "0");
				}else{
					paramValue.put("Status", "3");
				}
				listParamValues.add(paramValue);
			}
		}
		if (DateUtils.getSecondsDiff(startDate ,start_time) >= 0
				&& DateUtils.getSecondsDiff(start_time,endDate) >= 0
				&& DateUtils.getSecondsDiff(endDate, end_time) >= 0) {
			equipParamHistoryAcquisition.setTagname(tagName);
			equipParamHistoryAcquisition.setStartTime(start_time);
			equipParamHistoryAcquisition.setEndTime(endDate);
			equipParamHistoryAcquisition.setWwcyclecount(DateUtils
					.getSecondsDiff(start_time, endDate).intValue());
			List<EquipParamHistoryAcquisition> list = equipParamHistoryAcquisitionDAO
					.findParamHistory(equipParamHistoryAcquisition);
			for (EquipParamHistoryAcquisition hisData : list) {
				JSONObject paramValue = new JSONObject();
				paramValue.put("Time", DateUtils.convert(hisData.getDatetime(),
						DateUtils.DATE_TIME_FORMAT));
				JSONObject value = new JSONObject();
				value.put("value_real", hisData.getVvalue());
				paramValue.put("Value", value);
				if(hisData.getVvalue() == null || hisData.getVvalue() == ""){
					paramValue.put("Status", "0");
				}else{
					paramValue.put("Status", "3");
				}
				listParamValues.add(paramValue);
			}
		}
	}

	//验证用户信息
	private void validateChap(){
		//接收挑战
		CPackageCHAP chap = new CPackageCHAP().receive();
		if(chap == null){
			log.info("挑战发生未知错误..");
			IS_VALIDATE = false;
			return;
		}
		//回应挑战
		try {
			byte[] abtPassword = pwd.getBytes("utf-8");
			abtPassword = MD5(abtPassword);
			byte[] abtUserName = username.getBytes("utf-8");
			byte[] abtAfterMD5 = Bytes2MD5(chap.m_btIdentifier,chap.m_abtValue,abtPassword);
			new CPackageCHAP((byte)2,chap.m_btIdentifier,abtAfterMD5,abtUserName).send();
			CPackageCHAP pkgResult = new CPackageCHAP().receive();
			if (pkgResult == null)
            {
                log.info("AuthenticateToServer(...) 未知错误");
                IS_VALIDATE = false;
                return;
            }
			if (pkgResult.m_btCode != 3)
            {
                log.info("AuthenticateToServer(...) MD5挑战失败");
                IS_VALIDATE = false;
                return;
            }
			//发送随机密码
			/*byte[] uuidEncryptedKey = UUID.randomUUID().toString().getBytes("utf-8");
			byte[] abtEncryptedKey = new byte[16];
			System.arraycopy(uuidEncryptedKey, 0, abtEncryptedKey, 0, 16);*/
			new CPackageCHAP((byte)5,chap.m_btIdentifier,null,abtUserName).send();
			
			//接收哈希后的随机密码
			CPackageCHAP pkgHashKey = new  CPackageCHAP().receive();
			if(pkgHashKey == null){
				log.info("AuthenticateToServer(...) 未知错误");
				IS_VALIDATE = false;
				return;
			}
			if(pkgHashKey.m_abtValue == null){
				IS_VALIDATE = true;
			}else{
				IS_VALIDATE = false;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	private byte[] Bytes2MD5(byte m_btIdentifier, byte[] m_abtValue,
			byte[] abtPassword) {
		byte[] b = null;
		if(abtPassword == null){
			b = new byte[1+m_abtValue.length];
			b[0] = m_btIdentifier;
			System.arraycopy(m_abtValue, 0, b, 1, m_abtValue.length);
			return MD5(b);
		}
		b = new byte[1+m_abtValue.length+abtPassword.length];
		b[0] = m_btIdentifier;
		System.arraycopy(m_abtValue, 0, b, 1, m_abtValue.length);
		System.arraycopy(abtPassword, 0, b, m_abtValue.length+1, abtPassword.length);
		return MD5(b);
	}

	public static byte[] int2Bytes2(int n) {  
	    byte[] b = new byte[4];  
	    for (int i = 0; i < 4; i++) {  
	        b[3-i] = (byte) (n >> (24 - i * 8));  
	    }  
	    return b;  
	}
	public static byte[] short2Bytes(short data)  
    {  
        byte[] bytes = new byte[2];  
        bytes[0] = (byte) (data & 0xff);  
        bytes[1] = (byte) ((data & 0xff00) >> 8);  
        return bytes;  
    }  
	public static byte[] long2Bytes(long num) {  
	    byte[] byteNum = new byte[8];  
	    for (int ix = 0; ix < 8; ++ix) {  
	        int offset = 64 - (ix + 1) * 8;  
	        byteNum[7-ix] = (byte) ((num >> offset) & 0xff);  
	    }  
	    return byteNum;  
	}
	
	//md5 16为加密算法
		private static byte[] MD5(byte[] sourceStr) {
			byte[] buff = new byte[16];
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(sourceStr);
	            buff = md.digest();
	        } catch (NoSuchAlgorithmException e) {
	            System.out.println(e);
	        }
	        return buff;
	    }
		public static int byteArray2Int(byte[] b) {  
		    return   b[0] & 0xFF |  
		            (b[1] & 0xFF) << 8 |  
		            (b[2] & 0xFF) << 16 |  
		            (b[3] & 0xFF) << 24;  
		}
		
		public static short byte2Short(byte[] b){  
			short s = 0; 
	        short s0 = (short) (b[0] & 0xff);// 最低位 
	        short s1 = (short) (b[1] & 0xff); 
	        s1 <<= 8; 
	        s = (short) (s0 | s1); 
	        return s; 
	    }  
	
	public void sendMessage(String type) {
		synchronized (this) {
			while(true){
				if(IS_CONNECTION){
					break;
				}
				try {
					if(socket != null || !socket.isClosed()){
						socket.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.reconnect();
			}

			log.info("链接成功:"+type);
			// 用户名验证，若验证失败就30秒后重新验证
			while (true) {
				if(IS_VALIDATE){
					break;
				}
				//验证
				this.validateChap();
			}
			//初始化设备信息
			while(true){
				if(EQUIP_CACHE){
					break;
				}
				this.initEquipInfoMap();
			}
			String message = null;
			if (type.equals("current")) {
				message = this.readCurrentData();
				this.send(message);
			} else if (type.equals("order")) {
				message = this.readOrderData();
				this.send(message);
			} else {
				List<Map<String,Date>> times = new ArrayList<Map<String,Date>>();
				DataDic dataDic = dataDicDAO.getByDicCode("DATA_PARAM_CONFIG",
						"chinaNet");
				// 获取历史数据发送时间
				Date startDate = DateUtils.convert(dataDic.getMarks(),
						DateUtils.DATE_TIME_FORMAT);
				Date endDate = new Date((new Date().getTime() - 1000l * 60 * 60));
				//若开始时间和结束时间差值超过一小时，就分包发送
				while (DateUtils.getMinuteDiff(startDate, endDate) > 2) {
					Map<String,Date> map = new HashMap<String, Date>();
					map.put("startDate", startDate);
					startDate = DateUtils.addHours(startDate, 1);
					map.put("endDate", startDate);
					times.add(map);
				}
				Map<String,Date> map = new HashMap<String, Date>();
				map.put("startDate", startDate);
				map.put("endDate", endDate);
				times.add(map);
			}
		}
	}
	
	private boolean send(String message) {
		try {
			if (message == null) {
				return false;
			}
			System.err.println(message);
			// 组装发送数据包
			byte[] fasByte = this.assemblyArrays(message);
			// 发送
			out.write(fasByte);
			out.flush();
			int count = 0;
			Date date = new Date();
			// 是否有返回信息
			while (count == 0) {
				count = in.available();
				if (DateUtils.getSecondsDiff(date, new Date()) > 300) {
					break;
				}
			}
			if (count == 0) {
				IS_CONNECTION = false;
				return false;
			}
			byte[] receive1 = new byte[count];
			// 读取返回信息
			in.read(receive1);
			// byte[] errorCode = new byte[4];
			byte[] msg = new byte[count - 22 - 16];
			System.arraycopy(receive1, 22, msg, 0, msg.length);
			// int jsLength = byteArray2Int(errorCode);
			// System.arraycopy(receive1, 22, errorMsg, 0, jsLength);
			log.info("返回信息：" + new String(msg, "utf-8"));
			JSONObject result = (JSONObject) JSONObject.parse(new String(msg,
					"utf-8"));
			if (!result.getString("idError").equals("0")) {
				log.info(DateUtils.convert(new Date(),
						DateUtils.DATE_TIME_FORMAT) + ":" + "返回码不为0!!");
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			IS_CONNECTION = false;
			return false;
		}
	}
	
	//组装发送数据包
	private byte[] assemblyArrays(String message){
		try {
			byte[] btJson = message.getBytes("utf-8");
			// 消息类型
			short i = 1;
			byte[] msgType = short2Bytes(i);
			// 消息长度
			int l = 22 + btJson.length;
			byte[] length = int2Bytes2(l);
			// 发送端id
			long k = 1;
			byte[] id = long2Bytes(k);
			// 时戮
			byte[] time = int2Bytes2(TIMESTAMP++);
			// json长度
			byte[] msgLength = int2Bytes2(btJson.length);
			// 发送信息数据包
			byte[] fasByrte = new byte[22 + btJson.length + 16];
			// md5发送包
			byte[] md5str = new byte[time.length + btJson.length
					+ msgLength.length];
			// 将数据放进fasByrte数组中
			System.arraycopy(time, 0, md5str, 0, time.length);
			System.arraycopy(msgLength, 0, md5str, time.length,
					msgLength.length);
			System.arraycopy(btJson, 0, md5str, time.length
					+ msgLength.length, btJson.length);
			byte[] md5_str = MD5(md5str);
			System.arraycopy(msgType, 0, fasByrte, 0, msgType.length);
			System.arraycopy(length, 0, fasByrte, msgType.length,
					length.length);
			System.arraycopy(id, 0, fasByrte, msgType.length
					+ length.length, id.length);
			System.arraycopy(time, 0, fasByrte, msgType.length
					+ length.length + id.length, time.length);
			System.arraycopy(msgLength, 0, fasByrte, msgType.length
					+ length.length + id.length + time.length,
					msgLength.length);
			System.arraycopy(btJson, 0, fasByrte, msgType.length
					+ length.length + id.length + time.length
					+ msgLength.length, btJson.length);
			System.arraycopy(md5_str, 0, fasByrte, msgType.length
					+ length.length + id.length + time.length
					+ msgLength.length + btJson.length, md5_str.length);
			return fasByrte;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	//挑战握手类
	 class CPackageCHAP{
		public byte m_btCode,m_btIdentifier; //挑战属性，随机id
		public byte[] m_abtValue;      //挑战密码
		public byte[] m_abtName;      //用户
		CPackageCHAP(){}
		CPackageCHAP(Byte btCode, Byte btIdentifier, byte[] abtValue, byte[] szName){
			this.m_btCode = btCode;
			this.m_btIdentifier = btIdentifier;
			this.m_abtValue = abtValue;
			this.m_abtName = szName;
		}
		public CPackageCHAP receive(){
			int count = 0;
			//等待服务端发送挑战数据
			while(count == 0 ){
				try {
					count = in.available();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			byte[] abtHead = new byte[count];
			CPackageCHAP pkg = null;
			try {
				//接收服务端的挑战数据
				in.read(abtHead);
				//给类属性赋值
				pkg = new CPackageCHAP(abtHead[0],abtHead[1],null,null);
				byte[] abtLength = new byte[2];
				//abtHeadde数组第三和第四位为short，表示整个挑战数组长度
				System.arraycopy(abtHead, 2, abtLength, 0, 2);
				short usLength = byte2Short(abtLength);
				//当挑战属性为3和4时，没有挑战码，m_abtValue的长度不放在发送密文里，
				//abtHead[4] == 0表示挑战5，暂时不创建新密码比对，但m_abtValue的 长度为0要放在密文里
				if(pkg.m_btCode == 3 || pkg.m_btCode == 4 || abtHead[4] == 0){
					pkg.m_abtValue = null;
	                pkg.m_abtName = new byte[abtHead[4] == 0 ? usLength - 1-1-2-1 :usLength - 1-1-2];
	                System.arraycopy(abtHead, abtHead[4] == 0 ? 5:4, pkg.m_abtName, 0, pkg.m_abtName.length);
				}else{
					int num = abtHead[4];
					pkg.m_abtValue = new byte[num];
					System.arraycopy(abtHead, 5, pkg.m_abtValue, 0, pkg.m_abtValue.length);
					pkg.m_abtName = new byte[usLength - 5-pkg.m_abtValue.length];
					System.arraycopy(abtHead, pkg.m_abtValue.length+5, pkg.m_abtName, 0, pkg.m_abtName.length);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return pkg;
		}
		//发送验证密文
		void send(){
			byte btSizeLength = 1;
			if (m_btCode == 3 || m_btCode == 4) btSizeLength = 0;
			byte[] abtSend = new byte[1 + 1 + 2 + btSizeLength + (m_abtValue==null?0:m_abtValue.length) + m_abtName.length];
			abtSend[0] = m_btCode;
            abtSend[1] = m_btIdentifier;
            byte[] btLength = short2Bytes((short)abtSend.length);
            System.arraycopy(btLength, 0, abtSend, 2, 2);
            abtSend[4] = (byte)(m_abtValue == null ? 0 :m_abtValue.length);
            if(m_abtValue != null){
            	System.arraycopy(m_abtValue, 0, abtSend, 1 + 1 + 2 + btSizeLength, m_abtValue.length);
            }
            System.arraycopy(m_abtName, 0, abtSend, m_abtValue == null ? (1 + 1 + 2 + btSizeLength) : (1 + 1 + 2 + btSizeLength + m_abtValue.length), m_abtName.length);
            try {
				out.write(abtSend);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	 
}
