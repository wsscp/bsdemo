package cc.oit.bsmes.pla.dao;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.CustomerOrder;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by joker on 2014/4/1 0001.
 */
public class CustomerOrderDAOTest extends BaseTest{

    @Resource
    private CustomerOrderDAO customerOrderDAO;

    @Test
    public void test(){
        //List<CustomerOrder> list = customerOrderDAO.findByOrderInfo(new CustomerOrder());
        //logger.info("list :"+list.size());

        List<CustomerOrder> list = customerOrderDAO.findForSetPriority(new CustomerOrder());
        logger.info("list :"+list.size());
    }

}
