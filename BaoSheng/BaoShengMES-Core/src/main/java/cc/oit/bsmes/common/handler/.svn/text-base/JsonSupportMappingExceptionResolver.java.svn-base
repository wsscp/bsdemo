package cc.oit.bsmes.common.handler;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class JsonSupportMappingExceptionResolver extends AbstractHandlerExceptionResolver {
	
	@Resource
	private MessageSource messageSource;
	@Resource
	private ContentNegotiationManager contentNegotiationManager;
	@Resource
	private LocaleResolver localeResolver;
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// 非json不用此异常处理器
		List<MediaType> resolveMediaTypes = null;
		try {
			resolveMediaTypes = contentNegotiationManager.resolveMediaTypes(new ServletWebRequest(request));
		} catch (HttpMediaTypeNotAcceptableException e) {
			logger.debug(e.getMessage(), e);
			return null;
		}
		
		if (resolveMediaTypes == null) {
			return null;
		}
		
		if (!resolveMediaTypes.contains(MediaType.APPLICATION_JSON)) {
			return null;
		}
		
		String message = ex.getLocalizedMessage();
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
			exception.getParameter().getParameterName();
			// 验证错误，已经获得国际化后的消息
		} else {
			// 需国际化异常
			logger.debug(message, ex);
		}
		
		// 要返回的model
		ModelAndView modelAndView = new ModelAndView(new FastJsonJsonView());
		modelAndView.addObject("success", false);
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}

}
