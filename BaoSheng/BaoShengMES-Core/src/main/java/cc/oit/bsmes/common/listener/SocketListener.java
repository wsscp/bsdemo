package cc.oit.bsmes.common.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.interfaceChinaNet.SocketClient;

public class SocketListener implements ServletContextListener{
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		new Thread(new SocketThread("current",30000)).start();
		new Thread(new SocketThread("order",30000)).start();
		new Thread(new SocketThread("history",150000)).start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

}
class SocketThread implements Runnable{
	
	private String type;
	private long interval;
	private Timer timer = new Timer();
	public SocketThread(String type,long interval){
		this.type = type;
		this.interval = interval;
	}

	@Override
	public void run() {
		TimerTask task = new TimerTask(){
			SocketClient socketClient = (SocketClient) ContextUtils.getBean(SocketClient.class);
			@Override
			public void run() {
				socketClient.sendMessage(type);
			}
		};
		timer.schedule(task, 0, interval);
	}
	
}
