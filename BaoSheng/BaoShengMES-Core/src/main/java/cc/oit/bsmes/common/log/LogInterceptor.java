package cc.oit.bsmes.common.log;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.LogConstants;
import cc.oit.bsmes.common.cookie.CookieMachineResolver;
import cc.oit.bsmes.common.log.logback.AsyncLoggerFactory;
import cc.oit.bsmes.common.log.model.ActionLog;
import cc.oit.bsmes.common.util.SessionUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * 日志拦截器
 * @author zhangdongping
 *
 */
public class LogInterceptor  extends HandlerInterceptorAdapter{
	
	private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);
	private String serverHostName;
	private String serverAddress;
	private static ThreadLocal<ActionLog> actionLogThreadLocal = new ThreadLocal<ActionLog>();
	
	public LogInterceptor() {
		try {
			this.serverHostName = InetAddress.getLocalHost().getHostName();
			this.serverAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

     //在执行action方法之前执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		log.trace("entering preHandle Intercept ... ");
 	 	String actionId = java.util.UUID.randomUUID().toString(); 
		request.setAttribute(LogConstants.LOG_ACTION_ATTRIBUTE_NAME, actionId); 
		ActionLog actionLog = new ActionLog();
		actionLog.setRequestTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		actionLog.setId(actionId);
		actionLog.setServerName(this.serverHostName);
		actionLog.setServerAddr(this.serverAddress);
		actionLog.setSessionId(request.getSession().getId()); 
		actionLog.setLocale(request.getLocale().toString()); 
		String clientIp=new CookieMachineResolver().getIp(request, response);
		if(StringUtils.isEmpty(clientIp))
		{
			clientIp=request.getRemoteAddr();
		}
		actionLog.setClientAddr(clientIp);
		actionLog.setAppId(request.getContextPath());
		actionLog.setClientUserAgent(request.getHeader("user-agent"));
		actionLog.setClientMac((new CookieMachineResolver()).getMac(request, response));		
		try {
			String url = request.getRequestURL().toString();
			if (request.getQueryString() != null)  
				url += "?" + request.getQueryString(); 
			actionLog.setUrl(url); 
			actionLog.setMethod(request.getMethod()); 
			String params = JSONObject.toJSONString(request.getParameterMap());
			params = URLDecoder.decode(params, "UTF-8");
			if (params.length() > 2000)
				actionLog.setParams(params.substring(0, 2000));
			else
				actionLog.setParams(params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		User user = SessionUtils.getUser();
		if (user != null) {
			actionLog.setUserCode(user.getUserCode());
			actionLog.setUserName(user.getName());
			actionLog.setOrgCode(user.getOrgCode());
			actionLog.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			actionLog.setModifyTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			actionLog.setCreateUserCode(user.getUserCode());
			actionLog.setModifyUserCode(user.getUserCode());
		}
		actionLog.setIsException(Boolean.TRUE); 			
		actionLogThreadLocal.set(actionLog);

//        SessionUtils.setResourcesMap(request.getSession(), false); // 加载菜单

		log.trace("exiting preHandle Intercept ... ");
		return true;
	}

    //在执行action方法成功之后执行
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		log.trace("entering postHandle Intercept ... ");
		ActionLog actionLog = actionLogThreadLocal.get(); 

		if (actionLog == null) {
			log.trace("exiting postHandle Intercept ... ");
			return;
		}
		//到这里来就没有异常
		actionLog.setIsException(Boolean.FALSE);
		actionLog.setActionResult("SUCCESS");
		log.trace("exiting postHandle Intercept ... ");
	}

	//在所有action方法执行完毕后执行
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.trace("entering afterCompletion Intercept ... ");
		ActionLog actionLog = actionLogThreadLocal.get(); 
		 
		if (actionLog == null) {
			log.trace("exiting afterCompletion Intercept ... ");
			return;
		}
		if (handler instanceof HandlerMethod) {
			try {
				HandlerMethod proxy = (HandlerMethod) handler;
				actionLog.setActionClass(proxy.getBeanType().getName());
				actionLog.setActionMethod(proxy.getMethod().getName());
			} catch (Exception e) {
				log.error(e.getStackTrace().toString());
			}
		}
		actionLog.setResponseTime(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		if(actionLog.getIsException())
		{
			//有异常
			actionLog.setActionResult("FAILURE");	
		}
		if(ex!=null)
		{
			String errorMessage=ex.getStackTrace().toString();
			actionLog.setIsException(Boolean.TRUE); 
			
			if (errorMessage.length() > 2000)
				actionLog.setActionResult(errorMessage.substring(0, 2000));
			else
				actionLog.setActionResult(errorMessage);			
		} 
		AsyncLoggerFactory.instance().saveActionLog(actionLog);
		actionLogThreadLocal.remove(); 
		log.trace("exiting afterCompletion Intercept ... ");

	}

}
