package cc.oit.bsmes.pla.exception;

import cc.oit.bsmes.common.exception.MESException;

/**
 * 没有生产能力
 * @author chanedi
 */
public class UnableToProductException extends MESException {

    public UnableToProductException() {
        super("unableToProduct");
    }

    public UnableToProductException(String messageKey, Throwable cause) {
        super(messageKey, cause);
    }

    public UnableToProductException(String messageKey) {
        super(messageKey);
    }

    public UnableToProductException(Throwable cause) {
        super("unableToProduct", cause);
    }

}
