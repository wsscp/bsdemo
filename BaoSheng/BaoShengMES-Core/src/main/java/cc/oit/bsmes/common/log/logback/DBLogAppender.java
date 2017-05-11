package cc.oit.bsmes.common.log.logback;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.log.model.ActionLog;
import cc.oit.bsmes.common.log.model.BizLog;
import cc.oit.bsmes.common.log.service.ActionLogService;
import cc.oit.bsmes.common.log.service.BizLogService;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志记录实现
 * @author zhangdongping
 *
 */

public class DBLogAppender<T> extends AppenderBase<T>   {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static ActionLogService actionLogService ;
	private static BizLogService bizLogService;

	public DBLogAppender() {
		super();
	}
	@Override
	public void append(T event) {
		
		try {
			if(actionLogService==null)
			{
			  actionLogService = (ActionLogService)ContextUtils.getBean("actionLogServiceImpl");  
			}
			if(bizLogService==null)
			{
			 bizLogService = (BizLogService)ContextUtils.getBean("bizLogServiceImpl");  
			}
			 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.debug(ToStringBuilder.reflectionToString(event));
		}
		  LoggingEvent eventObject=null;
		  if(event instanceof LoggingEvent)
		  {
			  eventObject=(LoggingEvent)event;
			  try {
				  Object  message=eventObject.getArgumentArray()[0];
		        	if (message instanceof ActionLog )   
		        	{
		        		actionLogService.saveLog((ActionLog) message);
		        	}
		        	else if(message instanceof BizLog)
		        	{
		        		bizLogService.saveLog((BizLog) message);
		        	}
				} catch (Exception e) {
					logger.error(e.getMessage(), e); 
				}
			  
		  }else
		  {
			 
			logger.debug(ToStringBuilder.reflectionToString(event));
		  }
		  
        
	}

	 
}
