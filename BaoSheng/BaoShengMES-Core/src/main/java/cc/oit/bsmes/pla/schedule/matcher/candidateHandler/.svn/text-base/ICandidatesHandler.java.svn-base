package cc.oit.bsmes.pla.schedule.matcher.candidateHandler;

import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.schedule.model.Strategy;
import com.google.common.collect.Multimap;

import java.util.Collection;

/**
 * Created by 羽霓 on 2014/4/14.
 */
public interface ICandidatesHandler {

    public Strategy getStrategy();

    public String getMapKey();

    public String getCandidatesKey(CustomerOrderItemProDec order);

    public Collection<CustomerOrderItemProDec> getCandidates(Multimap<String, CustomerOrderItemProDec> orders, CustomerOrderItemProDec lastOrder);

}
