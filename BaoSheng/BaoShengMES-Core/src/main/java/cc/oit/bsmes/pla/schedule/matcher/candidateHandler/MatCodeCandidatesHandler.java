package cc.oit.bsmes.pla.schedule.matcher.candidateHandler;

import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import com.google.common.collect.Multimap;

import java.util.Collection;

/**
 * Created by 羽霓 on 2014/4/14.
 */
public abstract class MatCodeCandidatesHandler extends DefaultCandidatesHandler {

    public static final String MAP_KEY = "code";

    @Override
    public String getMapKey() {
        return MAP_KEY;
    }

    @Override
    public String getCandidatesKey(CustomerOrderItemProDec order) {
        return order.getProcessOut().getMatCode();
    }

}
