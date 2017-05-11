package cc.oit.bsmes.common.listener;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.interfacemessage.service.MessageAutoSendService;

public class EmailListener implements ServletContextListener {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private Timer timer = new Timer();
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		TimerTask task=new TimerTask(){
			@Override
			public void run() {
				MessageAutoSendService mesAutoSendService = (MessageAutoSendService) ContextUtils
						.getBean(MessageAutoSendService.class);
				try {
					mesAutoSendService.autoSendEmail();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			
		};
		timer.schedule(task, DateUtils.addSeconds(new Date(), 30), 1000);
		
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
		
	}

}
