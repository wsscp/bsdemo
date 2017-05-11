package cc.oit.bsmes.pla.schedule.matcher.candidateHandler;

import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import org.springframework.stereotype.Component;

/**
 * Created by 羽霓 on 2014/4/14.
 */
@Component
public class SpecCandidatesHandler extends DefaultCandidatesHandler {

    @Override
    public String getMapKey() {
        return "spec";
    }

    @Override
    public String getCandidatesKey(CustomerOrderItemProDec order) {
        return order.getProductSpec();
    }

}
