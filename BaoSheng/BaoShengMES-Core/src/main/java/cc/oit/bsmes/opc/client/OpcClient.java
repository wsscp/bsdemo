package cc.oit.bsmes.opc.client;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafish.clients.opc.JOpc;
import javafish.clients.opc.component.OpcGroup;
import javafish.clients.opc.component.OpcItem;
import javafish.clients.opc.exception.SynchReadException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.da.OPCSERVERSTATUS;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AutoReconnectController;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cc.oit.bsmes.common.exception.ErrorBasConfigException;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.opc.client.aop.JOpcConnect;
import cc.oit.bsmes.opc.client.exception.OPCDataException;
import cc.oit.bsmes.wip.model.Receipt;

@Service
public class OpcClient  {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private static Map<String, Receipt> valueCache = new HashMap<String, Receipt>();
	private long waitTime = 500;
	private long MAX_LOOP = 10;
	private final static String WW_OPC_SERVER_HOST_KEY = "ww.opc.server.host";
	private final static String WW_OPC_SERVER_NAME_KEY = "ww.opc.server.name";
	private final static String WW_OPC_LOGIN_USER_KEY = "ww.opc.login.user";
	private final static String WW_OPC_LOGIN_PASSWORD_KEY = "ww.opc.login.password";
	private final static String WW_OPC_GROUP_NAME_KEY = "ww.opc.group.name";
	private final static String WW_OPC_DOMAIN_NAME_KEY = "ww.opc.domain.name";
	
	private final static String WW_OPC_PMJ_SERVER_HOST_KEY = "ww.opc.pmj.server.host";
	private final static String WW_OPC_PMJ_SERVER_NAME_KEY = "ww.opc.pmj.server.name";
	private final static String WW_OPC_PMJ_LOGIN_USER_KEY = "ww.opc.pmj.login.user";
	private final static String WW_OPC_PMJ_LOGIN_PASSWORD_KEY = "ww.opc.pmj.login.password";
	private final static String WW_OPC_PMJ_GROUP_NAME_KEY = "ww.opc.pmj.group.name";
	
	
	private static Object objectRead = new Object();
	private static Object objectWait = new Object(); 
	
	private static ConnectionInformation con;
	
	private static AutoReconnectController autos;
	public static Server server;
	private static  Group group;
	
	private static Map<String, Long> timeFlag=new HashMap<String, Long>();
	
	 
	
	// 初始化opc server 链接
	public synchronized static void initOpcServerConnection(){
		//
		timeFlag.put("timeFlag", System.currentTimeMillis());
		con = new ConnectionInformation();
	    con.setHost(WebContextUtils.getPropValue(WW_OPC_SERVER_HOST_KEY));
	    con.setProgId(WebContextUtils.getPropValue(WW_OPC_SERVER_NAME_KEY));	
	    
	    try{
	    	con.setDomain(WebContextUtils.getPropValue(WW_OPC_DOMAIN_NAME_KEY));
	    }
	    catch(ErrorBasConfigException e){
        }
	    con.setUser(WebContextUtils.getPropValue(WW_OPC_LOGIN_USER_KEY));
	    con.setPassword(WebContextUtils.getPropValue(WW_OPC_LOGIN_PASSWORD_KEY));
	    server = new Server(con,null);//Executors.newSingleThreadScheduledExecutor()	 
	    autos= new AutoReconnectController(server);
	    autos.connect();
	    
	   
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//	    try {
//			server.connect();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JIException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (AlreadyConnectedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    try {
	    	OPCSERVERSTATUS object=null ;
		    try {
		    	object= server.getServerState(5000);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         if(object!=null){
			   group = server.addGroup(WebContextUtils.getPropValue(WW_OPC_GROUP_NAME_KEY));
	         }
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateGroupException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	}
	
	public synchronized static void initOpcPMJServerConnection(){
		//
		timeFlag.put("timeFlag", System.currentTimeMillis());
		con = new ConnectionInformation();
	    con.setHost(WebContextUtils.getPropValue(WW_OPC_PMJ_SERVER_HOST_KEY));
	    con.setProgId(WebContextUtils.getPropValue(WW_OPC_PMJ_SERVER_NAME_KEY));	
	    
	    try{
	    	con.setDomain(WebContextUtils.getPropValue(WW_OPC_DOMAIN_NAME_KEY));
	    }
	    catch(ErrorBasConfigException e){
        }
	    con.setUser(WebContextUtils.getPropValue(WW_OPC_PMJ_LOGIN_USER_KEY));
	    con.setPassword(WebContextUtils.getPropValue(WW_OPC_PMJ_LOGIN_PASSWORD_KEY));
	    server = new Server(con,null);//Executors.newSingleThreadScheduledExecutor()	 
	    autos= new AutoReconnectController(server);
	    autos.connect();
	    
	   
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//	    try {
//			server.connect();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JIException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (AlreadyConnectedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    try {
	    	OPCSERVERSTATUS object=null ;
		    try {
		    	object= server.getServerState(5000);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         if(object!=null){
			   group = server.addGroup(WebContextUtils.getPropValue(WW_OPC_PMJ_GROUP_NAME_KEY));
	         }
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateGroupException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	}

	public void queryReceiptByOPCServer(Map<String, Receipt> receipts) {
		if (receipts == null || receipts.isEmpty()) {
			return;
		}
		Map<String, Receipt> needManuMap = new HashMap<String, Receipt>();

		Iterator<String> it = receipts.keySet().iterator();
		while (it.hasNext()) {
			String receiptKey = it.next();
			// 从缓存中读取
			Receipt receiptCatchValue = valueCache.get(receiptKey);
			if (receiptCatchValue == null) {
				// 为空，需要手工采集
				needManuMap.put(receiptKey, receipts.get(receiptKey));
			} else {
				receipts.put(receiptKey, receiptCatchValue);
			}

		}
		if (needManuMap != null && !needManuMap.isEmpty()) {
			manualCollectData(needManuMap);
			receipts.putAll(needManuMap);
		}
	}

	@JOpcConnect
	public void autoCollectDatas() throws OPCDataException {
		JOpc jopc = new JOpc(WebContextUtils.getPropValue(WW_OPC_SERVER_HOST_KEY),
                WebContextUtils.getPropValue(WW_OPC_SERVER_NAME_KEY),
                WebContextUtils.getPropValue(WW_OPC_LOGIN_USER_KEY));
		OpcGroup group = null;
		try {
			jopc.connect();
			while (true) {
				try {
					group = new OpcGroup(WebContextUtils.getPropValue(WW_OPC_GROUP_NAME_KEY),
							true, 500, 0.0f);
					jopc.addGroup(group);
					 synchronized(objectWait) {
					  objectWait.wait(waitTime);
					  }  
					Map<String, Receipt> needManuMap = new HashMap<String, Receipt>();
					needManuMap.putAll(valueCache);
					collectData(needManuMap, jopc, group, false);

				} catch (InterruptedException ex) {
					log.debug("autoCollectDatas, wait interrupted");
				} catch(Exception e)
				{
					log.error(e.getMessage(), e);
				 
				}
				finally {
					jopc.unregisterGroup(group);
					jopc.removeGroup(group);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OPCDataException(e.getMessage());
		}

	}
 
	private void manualCollectData(Map<String, Receipt> receiptMap)
			throws OPCDataException {
		JOpc jopc = new JOpc(WebContextUtils.getPropValue(WW_OPC_SERVER_HOST_KEY),
				WebContextUtils.getPropValue(WW_OPC_SERVER_NAME_KEY),
				WebContextUtils.getPropValue(WW_OPC_LOGIN_USER_KEY));
		OpcGroup group = null;
		valueCache.putAll(receiptMap);
		try {
			jopc.connect();
			try {

				group = new OpcGroup(WebContextUtils.getPropValue(WW_OPC_GROUP_NAME_KEY), true,
						500, 0.0f);
				jopc.addGroup(group);
				collectData(receiptMap, jopc, group, true);

			} finally {
				jopc.unregisterGroup(group);
				jopc.removeGroup(group);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OPCDataException(e.getMessage());
		}
	}

	private void collectData(Map<String, Receipt> receiptMap, JOpc jopc,
			OpcGroup group, boolean isManual) throws OPCDataException {

		synchronized (objectRead) {

			try {

				Iterator<String> it = receiptMap.keySet().iterator(); 
				Map<String, OpcItem> itemMap = new HashMap<String, OpcItem>();
				while (it.hasNext()) {
					String receiptKey = it.next();
					// 从缓存中读取
					Receipt data = receiptMap.get(receiptKey);
					OpcItem item = new OpcItem(data.getReceiptCode(), true, "");
					group.addItem(item);
					itemMap.put(receiptKey, item);
				}

				jopc.registerGroups();

				it = receiptMap.keySet().iterator();
				while (it.hasNext()) {
					String receiptKey = it.next();
					// 从缓存中读取
					Receipt data = receiptMap.get(receiptKey);
					OpcItem item = itemMap.get(receiptKey);
					// jopc.registerItem(group, item);
					if (isManual) {
						saveTraceAttrValue(data, jopc, group, item);
					} else {
						Date lastDate = data.getLastDate();
						long interval = 0;
						if (lastDate != null) {
							long diff = new Date().getTime()
									- data.getLastDate().getTime();
							interval = diff; 
						} 
						if (lastDate == null
								|| interval >= data.getFrequence() * 1000) {
							  System.err.println("here"+System.currentTimeMillis());
							saveTraceAttrValue(data, jopc, group, item);
							 System.err.println(data.getDaValue());
						}
					} 
				} 

			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			}  
		}
	}

	private void saveTraceAttrValue(Receipt data, JOpc jopc, OpcGroup group,
			OpcItem item) throws SynchReadException {
		int loop_count = 0;
		while (item.getValue() == null
				|| StringUtils.isEmpty(item.getValue().toString())) {
			item = jopc.synchReadItem(group, item);
			loop_count++;
			if (loop_count > this.MAX_LOOP) {
				// 超过最大读取次数，跳出
				break;
			}
		}
		data.setDaValue(item.getValue().toString());
		data.setLastDate(new Date());
	}
	
	public void writeOpcValue(List<OpcParmVO> parmvoList)
	{
		if(CollectionUtils.isEmpty(parmvoList))
		{
			return;
		}
		    try{
		    	  if(con == null || server == null||group==null){ 
		    		  initOpcServerConnection(); 
		    	  }
		    	  
		    	 long oldtime= timeFlag.get("timeFlag");
		    	 if((System.currentTimeMillis()-oldtime)>1000*60*60)  // 一个小时后，重新连接 。可以逐步加长重连时间
		    	 {
		    		 if(autos!=null)
		    		 {
		    			try{
		    			   autos.disconnect();
		    			 }catch(Exception e)
		    			 {
		    				 log.error(e.toString(),e);
		    			 }
		    		 } 
		    		 if(server!=null)
		    		 {
		    		   try{
		    			  server.disconnect();
		    			 }catch(Exception e)
		    			 {
		    				 log.error(e.toString(),e);
		    			 }
		    		 } 
		    		 log.debug("连接超时，主动自动连接");
		    		 initOpcServerConnection(); 
		    	 }
		    	  
		    	 
		    	  if(group != null){
		    		  for(int j=0;j<parmvoList.size();j++){
		    			  OpcParmVO vo = parmvoList.get(j);
		    			  group.setActive(true);
		    			  Item item = group.addItem(WebContextUtils.getPropValue(WW_OPC_GROUP_NAME_KEY)+ "."+vo.getParmCode());// (WebContextUtils.getPropValue(WW_OPC_GROUP_NAME_KEY)+ "Bucket Brigade.Int1" vo.getParmCode()
		    			  final JIVariant value = new JIVariant(vo.getParmValue());	               
		    			  item.write(value);
		    		  }		          
		    	  }
		        }
		        catch(final JIException  e)	
		        {
	        	   log.error(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())), e);
	        	   con=null;
				  // throw new OPCDataException(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
					
	            }catch(final Exception  e)
		        {
	            	log.error(e.getMessage(), e);
	            	  con=null;
	            	 OPCSERVERSTATUS state = server.getServerState();
	            	 if(state!=null)
	            	 {
	            		 log.error("zdp server OPCSERVERSTATUS  is :"+state.toString(),e); 
		            	 log.error("zdp server ServerState is :"+state.getServerState().name(),e); 
	            	 }
	            	 
	            	 try {
						log.error("zdp group status is :"+group,e);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	  
			    	 
					//throw new OPCDataException(e.getMessage());	  
		        }/*
		        finally{
		        	try{
		        	   server.disconnect();
		        	}catch(Exception e)
		        	{
		        	  log.error(e.getMessage(),e);
		        	}
		        }*/
		 
		
		/*
		JOpc jopc = new JOpc(WebContextUtils.getPropValue(WW_OPC_SERVER_HOST_KEY),
				WebContextUtils.getPropValue(WW_OPC_SERVER_NAME_KEY),
				WebContextUtils.getPropValue(WW_OPC_CLIENT_USER_KEY)); 
		 
			try {
				
				jopc.connect();
			} catch (ConnectivityException e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			 
			}
			OpcGroup group = new OpcGroup(WebContextUtils.getPropValue(WW_OPC_GROUP_NAME_KEY), true,
					500, 0.0f);
			OpcItem item = new OpcItem(parmCode, true, "");
		 
			group.addItem(item);
			jopc.addGroup(group);
			try {
				jopc.registerGroups();
			} catch (UnableAddGroupException e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			} catch (UnableAddItemException e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			}
			
			item.setValue(new Variant(parmValue));
			try {
				jopc.synchWriteItem(group, item);
			} catch (ComponentNotFoundException e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			} catch (SynchWriteException e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			}
			finally{
				//最后，该释放的全释放掉
				try {
					jopc.unregisterGroup(group);
				} catch (ComponentNotFoundException e) {
					log.error(e.getMessage(), e);
					throw new OPCDataException(e.getMessage());
				} catch (UnableRemoveGroupException e) {
					log.error(e.getMessage(), e);
					throw new OPCDataException(e.getMessage());
				}
			}
			*/
	}
	
	public void writeOpcPMJValue(List<OpcParmVO> parmvoList)
	{
		if(CollectionUtils.isEmpty(parmvoList))
		{
			return;
		}
		    try{
		    	  if(con == null || server == null||group==null){ 
		    		  initOpcPMJServerConnection(); 
		    	  }
		    	  
		    	 long oldtime= timeFlag.get("timeFlag");
		    	 if((System.currentTimeMillis()-oldtime)>1000*60*60)  // 一个小时后，重新连接 。可以逐步加长重连时间
		    	 {
		    		 if(autos!=null)
		    		 {
		    			try{
		    			   autos.disconnect();
		    			 }catch(Exception e)
		    			 {
		    				 log.error(e.toString(),e);
		    			 }
		    		 } 
		    		 if(server!=null)
		    		 {
		    		   try{
		    			  server.disconnect();
		    			 }catch(Exception e)
		    			 {
		    				 log.error(e.toString(),e);
		    			 }
		    		 } 
		    		 log.debug("连接超时，主动自动连接");
		    		 initOpcServerConnection(); 
		    	 }
		    	  
		    	 
		    	  if(group != null){
		    		  for(int j=0;j<parmvoList.size();j++){
		    			  OpcParmVO vo = parmvoList.get(j);
		    			  group.setActive(true);
		    			  Item item = group.addItem(WebContextUtils.getPropValue(WW_OPC_PMJ_GROUP_NAME_KEY)+ "."+vo.getParmCode());// (WebContextUtils.getPropValue(WW_OPC_GROUP_NAME_KEY)+ "Bucket Brigade.Int1" vo.getParmCode()
		    			  final JIVariant value = new JIVariant(vo.getParmValue());	               
		    			  item.write(value);
		    		  }		          
		    	  }
		        }
		        catch(final JIException  e)	
		        {
	        	   log.error(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())), e);
	        	   con=null;
				  // throw new OPCDataException(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
					
	            }catch(final Exception  e)
		        {
	            	log.error(e.getMessage(), e);
	            	  con=null;
	            	 OPCSERVERSTATUS state = server.getServerState();
	            	 if(state!=null)
	            	 {
	            		 log.error("zdp server OPCSERVERSTATUS  is :"+state.toString(),e); 
		            	 log.error("zdp server ServerState is :"+state.getServerState().name(),e); 
	            	 }
	            	 
	            	 try {
						log.error("zdp group status is :"+group,e);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	  
			    	 
					//throw new OPCDataException(e.getMessage());	  
		        }/*
		        finally{
		        	try{
		        	   server.disconnect();
		        	}catch(Exception e)
		        	{
		        	  log.error(e.getMessage(),e);
		        	}
		        }*/
		 
		
		/*
		JOpc jopc = new JOpc(WebContextUtils.getPropValue(WW_OPC_SERVER_HOST_KEY),
				WebContextUtils.getPropValue(WW_OPC_SERVER_NAME_KEY),
				WebContextUtils.getPropValue(WW_OPC_CLIENT_USER_KEY)); 
		 
			try {
				
				jopc.connect();
			} catch (ConnectivityException e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			 
			}
			OpcGroup group = new OpcGroup(WebContextUtils.getPropValue(WW_OPC_GROUP_NAME_KEY), true,
					500, 0.0f);
			OpcItem item = new OpcItem(parmCode, true, "");
		 
			group.addItem(item);
			jopc.addGroup(group);
			try {
				jopc.registerGroups();
			} catch (UnableAddGroupException e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			} catch (UnableAddItemException e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			}
			
			item.setValue(new Variant(parmValue));
			try {
				jopc.synchWriteItem(group, item);
			} catch (ComponentNotFoundException e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			} catch (SynchWriteException e) {
				log.error(e.getMessage(), e);
				throw new OPCDataException(e.getMessage());
			}
			finally{
				//最后，该释放的全释放掉
				try {
					jopc.unregisterGroup(group);
				} catch (ComponentNotFoundException e) {
					log.error(e.getMessage(), e);
					throw new OPCDataException(e.getMessage());
				} catch (UnableRemoveGroupException e) {
					log.error(e.getMessage(), e);
					throw new OPCDataException(e.getMessage());
				}
			}
			*/
	}

	public static void main(String arg[]) {
		javafish.clients.opc.JOpc.coInitialize();
		for (int i = 1; i < 2; i++) {
			OpcClient o = new OpcClient();
			o.run();
			//Thread t = new Thread(o);
			//t.start();
		}
	}

	public void run() {
		Receipt value = new Receipt();
		value.setReceiptCode("Bucket Brigade.Int1");
		value.setFrequence(0.001);
		Map<String, String> propMap = new HashMap<String, String>();
		propMap.put(WW_OPC_SERVER_HOST_KEY, "127.0.0.1");
		// propMap.put(WW_OPC_SERVER_HOST_KEY, "192.168.1.71");
		propMap.put(WW_OPC_SERVER_NAME_KEY, "Matrikon.OPC.Simulation.1");
		propMap.put(WW_OPC_LOGIN_USER_KEY, "Administrator");
		propMap.put(WW_OPC_GROUP_NAME_KEY, "group1");
		Map<String, Receipt> receiptMap = new HashMap<String, Receipt>();
		receiptMap.put(value.getReceiptCode(), value);
		JOpc jopc = new JOpc(WebContextUtils.getPropValue(WW_OPC_SERVER_HOST_KEY),
				WebContextUtils.getPropValue(WW_OPC_SERVER_NAME_KEY),
				WebContextUtils.getPropValue(WW_OPC_LOGIN_USER_KEY));
		OpcGroup group = null;// new
								// OpcGroup(WebContextUtils.getPropValue(WW_OPC_GROUP_NAME_KEY),
								// true, 500, 0.0f);

		try {
			jopc.connect();
			// jopc.registerGroups();
			for (int i = 0; i < 100; i++) {
				try {
					group = new OpcGroup(WebContextUtils.getPropValue(WW_OPC_GROUP_NAME_KEY),
							true, 500, 0.0f);
					jopc.addGroup(group);
					// System.out.println(i);
					//long now = System.currentTimeMillis();
					collectData(receiptMap, jopc, group, false);
					// System.out.println(System.currentTimeMillis()-now);
					// System.out.println(value.getParmValue());
					// jopc.unregisterGroup(group);
					// jopc.removeGroup(group);
				} finally {
					jopc.unregisterGroup(group);
					jopc.removeGroup(group);
				}

			}

		}

		catch (Exception e) {
			log.error(e.getMessage(), e);
			// throw new OPCDataException(e.getMessage());
		}

	}
}
