package cc.oit.bsmes.wip.dao;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.dao.OrderTaskDAO;
import cc.oit.bsmes.pla.model.OrderTask;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Jinhy on 2015/4/29.
 */
public class OrderTaskDAOTest extends BaseTest{

    @Resource
    private OrderTaskDAO orderTaskDAO;


    @Test
    public void findByWoNoAndColorTest(){
        List<OrderTask> list = orderTaskDAO.findByWoNoAndColor("20150422171344122","红");
        for (OrderTask orderTask : list) {
            System.out.println(orderTask.getId());
        }
    }
}
