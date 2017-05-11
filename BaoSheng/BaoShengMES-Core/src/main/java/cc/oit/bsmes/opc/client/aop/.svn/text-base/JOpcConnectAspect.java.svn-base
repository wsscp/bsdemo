package cc.oit.bsmes.opc.client.aop;

import javafish.clients.opc.JCustomOpc;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JOpcConnectAspect {
	
	@Around("@annotation(cc.oit.bsmes.opc.client.aop.JOpcConnect)")
	public Object invoke(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object value = null;
		try {
			JCustomOpc.coInitialize();			 
			value = proceedingJoinPoint.proceed();
		} finally {
			JCustomOpc.coUninitialize();
		}
		
		return value;
	}

}
