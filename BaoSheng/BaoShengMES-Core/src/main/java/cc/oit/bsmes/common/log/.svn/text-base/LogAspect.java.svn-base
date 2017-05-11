package cc.oit.bsmes.common.log;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.LogConstants;
import cc.oit.bsmes.common.log.logback.AsyncLoggerFactory;
import cc.oit.bsmes.common.log.model.BizInfo;
import cc.oit.bsmes.common.log.model.BizLog;
import cc.oit.bsmes.common.log.model.EntityInfo;
import cc.oit.bsmes.common.mybatis.ColumnTarget;
import cc.oit.bsmes.common.mybatis.ModelUtils;
import cc.oit.bsmes.common.mybatis.Property;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SortListInterceptor;
import cc.oit.bsmes.common.util.SessionUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;
 
 
 

/**
 * 业务日志记录实现
 * AOP
 * @author zhangdongping
 *
 */
@Aspect
@Component
public class LogAspect {
	
	private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);
	@Resource
	private LogMetaData logMetaData;
    @Autowired  
    @Lazy
    private  HttpServletRequest request;  
	
	
	public LogAspect() {
	}
	@Around("execution(* *..service.impl.*.*(..))")
	public Object doBiz(ProceedingJoinPoint joinPoint) throws Throwable {
		log.trace("entering doBiz ... ");

        parseSortList(joinPoint);
		
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName(); 
		HashMap<String, BizInfo> bizInfoMap = logMetaData.getBizInfoMap(); 
		HashMap<String, EntityInfo> entityInfoMap =logMetaData.getEntityInfoMap(); 
		BizInfo bizInfo = bizInfoMap.get(className.concat(".").concat(methodName));
		BizLog bizLog = null;
		Object object = null; 
		if (request == null || bizInfo == null) {
			object = joinPoint.proceed();
			return object;
		}
		
		User user = SessionUtils.getUser();
		bizLog = new BizLog();  
		String actionId=null;
		try{
		 actionId = (String) request.getAttribute(LogConstants.LOG_ACTION_ATTRIBUTE_NAME);	
		 request.removeAttribute(LogConstants.LOG_ACTION_ATTRIBUTE_NAME);
		}catch(Exception e)
		{
			//did nothing			
		}
			 
		bizLog.setActionId(actionId);
		bizLog.setBizId(bizInfo.getId());
		bizLog.setBizTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));

		if (user != null) {
			bizLog.setUserCode(user.getUserCode());
			bizLog.setUserName(user.getName());
			bizLog.setOrgCode(user.getOrgCode());
			bizLog.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			bizLog.setModifyTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			bizLog.setCreateUserCode(user.getUserCode());
			bizLog.setModifyUserCode(user.getUserCode()); 
		}		
		String entityId = null;
		EntityInfo entityInfo = entityInfoMap.get(bizInfo.getEntityId());		
		if (entityInfo != null) {
			Object[] args = joinPoint.getArgs();
			// 从第一个参数的getId获取值
			if (args != null && args.length > 0) {
				if(args[0] instanceof String) {
					entityId = (String) args[0];
				} else
				try {
					entityId = BeanUtils.getProperty(args[0], "id");
				} catch (Exception e) {}
			}
		}


		try {
			object = joinPoint.proceed();
			bizLog.setIsException(Boolean.FALSE);
			
			// 新增实体，从返回值中获取id值；返回值可以是id或者是具有id属性的po对象
			if (entityInfo != null && StringUtils.isEmpty(entityId)) {
				if (object == null) {
					// do nothing
				}
				else if (object instanceof String)
					entityId = (String) object;
				else {
					try {
						entityId = BeanUtils.getProperty(object, "id");
					} catch (Exception e) {
						//did nothing
					}
				}
			}
		
			
			bizLog.setEntityId(entityId);
			
		} catch (Exception e) {
			bizLog.setIsException(Boolean.TRUE);
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			AsyncLoggerFactory.instance().saveBizLog(bizLog);
		}

		log.trace("exiting doBiz ... ");
		return object;
	}

    private void parseSortList(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] oriArgs = proceedingJoinPoint.getArgs();
        List<Sort> sortList = null;
        for (Object oriArg : oriArgs) {
            if (!(oriArg instanceof List)) {
                continue;
            }
            List list = (List) oriArg;
            if (list == null || list.size() == 0) {
                continue;
            }
            if (list.get(0) instanceof Sort) {
                sortList = list;
                break;
            }
        }
        if (sortList == null) {
            return;
        }

        // 获取代理目标对象
        Object obj = proceedingJoinPoint.getTarget();

        Class<?> modelClass = null;
        try {
            // 获取model类
            String modelName = obj.getClass().getName().replace(".service.impl.", ".model.").replace("ServiceImpl", "");

            // 将modelClass添加到线程变量
            modelClass = Class.forName(modelName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }
        if (modelClass == null) {
            return;
        }

        Map<String, Property> properties = ModelUtils.getProperties(modelClass, ColumnTarget.ORDER);
        List<Sort> sorts = new ArrayList<Sort>();
        for (Sort sort : sortList) {
            Property property = properties.get(sort.getProperty());
//            if (property == null) {
//                continue;
//            }
//            sort.setColumn(property.getColumnName());
            if (property == null) {
            	//用于排序时需要对排序字段做转换处理
                if(sort.getProperty() != null && sort.getProperty().indexOf("(") != -1 && sort.getProperty().indexOf(")") != -1){
                	String s = sort.getProperty().substring(sort.getProperty().indexOf("(")+1,sort.getProperty().indexOf(")"));
                	property = properties.get(s);
                	if(property == null){
                		 continue;
                	}else{
                		sort.setColumn(sort.getProperty().replaceAll(s, property.getColumnName()));
                	}
                }else{
                	continue;
                }
            }else{
            	 sort.setColumn(property.getColumnName());
            }
            sorts.add(sort);
        }
        SortListInterceptor.setSortList(sorts);
    }

}
