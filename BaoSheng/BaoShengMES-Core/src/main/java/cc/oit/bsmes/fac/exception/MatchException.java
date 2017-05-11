package cc.oit.bsmes.fac.exception;

import cc.oit.bsmes.common.exception.MESException;
import lombok.Getter;

/**
 * Created by chanedi on 14-3-20.
 * 排程失败
 */
public class MatchException extends MESException {

    @Getter
    private boolean needOA = false;

    public MatchException() {
        super("fac.match");
    }

    public MatchException(boolean needOA) {
        super("fac.match");
        this.needOA = needOA;
    }

}
