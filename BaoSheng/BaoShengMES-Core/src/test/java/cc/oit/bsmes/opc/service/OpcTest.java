package cc.oit.bsmes.opc.service;

import cc.oit.bsmes.bas.dao.ParmsMappingDAO;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.interfaceWWAc.service.EquipParamAcquisitionClient;
import cc.oit.bsmes.interfaceWWIs.service.DataIssuedService;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.opc.client.OpcClient;
import cc.oit.bsmes.opc.client.OpcParmVO;
import cc.oit.bsmes.wip.model.Receipt;
import org.apache.commons.lang3.time.DateUtils;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.junit.Test;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.Server;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class OpcTest extends BaseTest {
	
	@Resource
    private DataAcquisitionService dataAcquisitionService;
	@Resource
	ParmsMappingDAO parmsMappingDAO;
	@Resource
	OpcClient opcClient;
	
	private Object o =new Object();
	   @Resource
	   DataIssuedService dataIssuedService ;
	   @Resource
	   EquipParamAcquisitionClient equipParamAcquisitionClient;
	   

	@Test
 
	public void testGetAll() throws Exception{
		  System.out.println(Thread.currentThread());
		  for(int i=0;i<2;i++)
		  {
			  List<OpcParmVO> parmvoList =new ArrayList<OpcParmVO>();  
			  
			  if(i%2==0){
				  OpcParmVO vo=new OpcParmVO("Boolean","0");
				  parmvoList.add(vo);			 
				 opcClient.writeOpcValue(parmvoList);
			  
			  }else
			  {
				  OpcParmVO vo=new OpcParmVO("Boolean","1");
				  parmvoList.add(vo);			 
				 opcClient.writeOpcValue(parmvoList);
			  }
			  if(1==1)
			  continue;
 		 final ConnectionInformation ci = new ConnectionInformation();
		    ci.setHost("192.168.1.71");
	        ci.setDomain("ame.org.cn");
	        ci.setUser("administrator");
	        ci.setPassword("cimscims");
	        ci.setProgId("ArchestrA.FSGateway.3");
	        final String itemId = "MK.TEST.Boolean";
	        final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());
	        /*
	        try {
	            // connect to server
	            server.connect();
	            // add sync access, poll every 500 ms
	            final AccessBase access = new SyncAccess(server, 500);
	            access.addItem(itemId, new DataCallback() {
	                @Override
	                public void changed(Item item, ItemState state) {
	                    System.out.println(state);
	                }
	            });
	            // start reading
	            access.bind();
	            // wait a little bit
	            Thread.sleep(10 * 1000);
	            // stop reading
	            access.unbind();
	        } catch (final JIException e) {
	            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
	        }
	        */
	         
	        try{
	        server.connect();
	          Group group = server.addGroup("MK.TEST.");
            // Add a new item to the group
              Item item = group.addItem(itemId);
              
               JIVariant  value = new JIVariant(true);              
               if(i%2==0){
            	  value = new JIVariant(false);
               }
              
               
              item.write(value);
	        }catch(final JIException  e)
	        {
	        	 System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
	        }finally{
	        	
	        	server.disconnect();
	        }
              
		  }
 
		 final ConnectionInformation ci = new ConnectionInformation();
		    ci.setHost("192.168.1.129");
	        ci.setDomain("ame.org.cn");
	        ci.setUser("admin");
	        ci.setPassword("root");
	        ci.setProgId("ArchestrA.FSGateway.3");
	        final String itemId = "TEST.G.Bucket Brigade.Boolean";
	        final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());
	        /*
	        try {
	            // connect to server
	            server.connect();
	            // add sync access, poll every 500 ms
	            final AccessBase access = new SyncAccess(server, 500);
	            access.addItem(itemId, new DataCallback() {
	                @Override
	                public void changed(Item item, ItemState state) {
	                    System.out.println(state);
	                }
	            });
	            // start reading
	            access.bind();
	            // wait a little bit
	            Thread.sleep(10 * 1000);
	            // stop reading
	            access.unbind();
	        } catch (final JIException e) {
	            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
	        }
	        */
	         
	        try{
	        server.connect();
	          Group group = server.addGroup("test");
            // Add a new item to the group
              Item item = group.addItem(itemId);
              final JIVariant value = new JIVariant(true);
               
              item.write(value);
	        }catch(final JIException  e)
	        {
	        	 System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
	        }finally{
	        	
	        	//server.disconnect();
	        }
              
	        
 
		//SysPerfClassicManualStorageHandleCount 12
		//SysPerfAvailableBytes  63
		
		//SysPulse  10080
		
		//SysString 168
	      if(1==1)
		return;
		Integer cycleCount=200;
		String parmCode="SysPulse";
		String equipCode="test";
		Date endDate=new Date();
		Date stratDate=DateUtils.addDays(new Date(), -90);
		long now=System.currentTimeMillis();
	 
		
		List<Receipt> receipts = new ArrayList<Receipt>();
		Receipt o1=new Receipt();
		o1.setReceiptCode("Bucket Brigade.Int1");
		o1.setEquipCode("LINE-JX-0001");
		Receipt o2=new Receipt();
		o2.setReceiptCode("Random.Boolean");
		o2.setEquipCode("LINE-JX-0001");
		Receipt o3=new Receipt();
		o3.setReceiptCode("Bucket Brigade.Int2");
		o3.setEquipCode("LINE-JX-0002");
		Receipt o4=new Receipt();
		o4.setReceiptCode("Random.Boolean2");
		o4.setEquipCode("LINE-JX-0002");
		receipts.add(o1);
		receipts.add(o2);
		receipts.add(o3);
		receipts.add(o4);
		//List<List<Receipt>> alist = splitList(receipts,3);
		
		  Mythread2 t2 = new Mythread2();
	 		 Thread t = new Thread(t2);
	 		  t.start();
	 		 while(true)
				{
				   try {
					   cycleCount++;
					   now=System.currentTimeMillis();
					   if(cycleCount>10000)
					   {
		                List< Receipt> ob = dataAcquisitionService.queryLiveReceiptByCodes(receipts) ;
		             // System.err.println(System.currentTimeMillis()-now);
		              cycleCount=0;
					   }
				   }
				   catch (Exception e) {
						 
						e.printStackTrace();
					}
				}
		// List< Receipt> ob2 = dataAcquisitionService.queryLiveReceiptByCodes(receipts) ;
		//System.out.print(System.currentTimeMillis()-now);
		 
//		//parmsMappingDAO.getAll();
//		//Mythread1 o1 = new Mythread1();
//		 // Thread t = new Thread(o1);
 	
//		  Thread t2 = new Thread(o2);
//		 // t.start();
 
//		int count=0;
//			while(true)
//			{
//			   try {
//				   cycleCount++;
//				  ;
//				   System.err.println("count++" +count++);
//				     now=System.currentTimeMillis();
//				   List<EquipParamHistoryAcquisition> date = dataAcquisitionService.findParamHistory(equipCode, parmCode, stratDate, endDate, cycleCount);
//				   System.err.println(date.size());
//					System.err.println(System.currentTimeMillis()-now);
//					if(cycleCount>1000)
//					{
//						cycleCount=200;
//					}
//			   }
//		     catch (Exception e) {
//					 
//					e.printStackTrace();
//				}
//		     
//			}
		  
	}
	@Test
	public void main()
	{
		for(int j=0;j<100;j++)
		{
			  Mythread2 t2 = new Mythread2();
		 		 Thread t = new Thread(t2);
		 		  t.start();
		}
		  
	 		  
	 		  Mythread1 t1 = new Mythread1();
		 		 Thread t11 = new Thread(t1);
		 		t11.start();
		 	while(true)
		 	{
		 		
		 	}
	 		  
	 		  
	}
	 
	public void myTest() {
		String codes[]=new String[2];
		codes[0]="Bucket Brigade.Int1";
		codes[1]="Random.Boolean";
				
		Boolean isDebug=false;
		List<Receipt> receipts=new ArrayList<Receipt>();
		Receipt o1=new Receipt();
		o1.setEquipCode("Equip1");
		o1.setNeedAlarm(false);		 
		o1.setReceiptCode("Int1");
		o1.setReceiptMaxValue("1234567");
		o1.setReceiptMinValue("12345");
		o1.setReceiptTargetValue("123456");
		o1.setWorkOrderId("workOrderId");
		
		
		Receipt o2=new Receipt();
		o2.setEquipCode("Equip2");
		o2.setNeedAlarm(true);		 
		o2.setReceiptCode("Int2");
		o2.setReceiptMaxValue("44");
		o2.setReceiptMinValue("33");
		o2.setReceiptTargetValue("38");
		o2.setWorkOrderId("workOrderId");
		
		receipts.add(o1);
		receipts.add(o2); 
		
		dataIssuedService.IssuedParms(receipts, isDebug);
		
		/*
		Map<String, Receipt> data = dataAcquisitionService.queryReceiptByCodes(codes);
		//System.out.print("here"+data.size());
		int i=0;
		
		while(true)
		{
			i++;
		   try {
			   synchronized( o) {
				   o.wait(500);
				  } 
			   
			 
		} catch (InterruptedException e) {
			 
			e.printStackTrace();
		} 	
		 //data = dataAcquisitionService.queryReceiptByCodes(codes);
		//   dataAcquisitionService.wrieval("Random.Boolean", i+"i");
		  //System.out.println("data size"+data.size());
		}
		*/
		 
	}
	
	public class Mythread1  implements Runnable  
	{
		public void run() {
			try {
				testGetAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } 
	}
	public class Mythread2  implements Runnable  
	{
		public void run() {
			try {
				testGetAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } 
	}
	
	public static <T> List<List<T>> splitList(List<T> values, Integer size) {
		if (values == null || values.size() == 0)
			return null;

		if (size == null || size <= 0)
			size = 1000;
		
		List<List<T>> results = new ArrayList<List<T>>();
		
		for (int i = 0; i < values.size(); ++i) {
			if (i % size == 0) {
				List<T> result = new ArrayList<T>();
				results.add(result);
			}
			
			results.get(i / size).add(values.get(i));
		}

		return results;
	}
}
