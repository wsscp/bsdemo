package cc.oit.bsmes.common.log.logback;


import cc.oit.bsmes.common.log.model.ActionLog;
import cc.oit.bsmes.common.log.model.BizLog;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

/**
 * 日志工厂
 * @author zhangdongping
 *
 */
public class AsyncLoggerFactory{

	private static AsyncLoggerFactory factory = null;
	
	public static synchronized AsyncLoggerFactory instance() {
		if (factory == null) {
			factory = new AsyncLoggerFactory();
		}
		return factory;
	}
	
	private Logger actionLogger = null;
	
	private AsyncLoggerFactory() {	 	
		actionLogger = LoggerFactory.getLogger("oracleDblogger"); 
	}
	
	public void saveActionLog(ActionLog actionLog) {  
		actionLogger.info(ToStringBuilder.reflectionToString(actionLog), actionLog);
	}
	public void saveBizLog(BizLog bizLog) {
		actionLogger.info(ToStringBuilder.reflectionToString(bizLog), bizLog);
	}
}
