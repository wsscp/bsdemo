package cc.oit.bsmes.common.exception;

import cc.oit.bsmes.common.context.ContextUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MESException extends RuntimeException {

	private static final long serialVersionUID = -3088104317098344394L;
    private Object[] messageArgs;

    public MESException() {
		super("unknown");
	}

	public MESException(String messageKey, Throwable cause, Object... messageArgs) {
		super(messageKey, cause);
        this.messageArgs = messageArgs;
	}

	public MESException(String messageKey, Object... messageArgs) {
		super(messageKey);
        this.messageArgs = messageArgs;
	}

	public MESException(Throwable cause) {
		super("unknown", cause);
	}
	
	public String getLocalizedMessage() {
		String errorMessageKey = "error." +  getMessage();
		MessageSource messageSource = (MessageSource) ContextUtils.getBean("messageSource");
		return messageSource.getMessage(errorMessageKey, messageArgs, "", LocaleContextHolder.getLocale());
    }
	
}
