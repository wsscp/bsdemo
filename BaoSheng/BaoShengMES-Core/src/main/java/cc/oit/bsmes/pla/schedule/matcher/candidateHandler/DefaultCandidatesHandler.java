package cc.oit.bsmes.pla.schedule.matcher.candidateHandler;

import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.schedule.model.Strategy;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by 羽霓 on 2014/4/14.
 */
public abstract class DefaultCandidatesHandler implements ICandidatesHandler {

    protected Strategy strategy;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public DefaultCandidatesHandler() {
        strategy = new Strategy();
        strategy.setNotCombine(false);
    }

    @Override
    public Strategy getStrategy() {
        strategy.setLimitEndDate(true);
        return strategy;
    }

    @Override
    public Collection<CustomerOrderItemProDec> getCandidates(Multimap<String, CustomerOrderItemProDec> orders, CustomerOrderItemProDec lastOrder) {
        if (orders == null) {
            return new ArrayList<CustomerOrderItemProDec>();
        }
        return orders.get(getCandidatesKey(lastOrder));
    }

}
