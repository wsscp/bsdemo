package cc.oit.bsmes.pla.service;

import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.service.EquipInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by 羽霓 on 2014/5/5.
 */
@Service
@Transactional(rollbackFor = { Exception.class }, readOnly = false)
public class TempService {

    @Resource
    private ResourceCache resourceCache;
    @Resource
    private CustomerOrderItemProDecService customerOrderItemProDecService;
    @Resource
    private EquipInfoService equipInfoService;
    @Resource
    private CustomerOrderPlanService customerOrderPlanService;
    @Resource
    private ProductSOPService productSOPService;
    @Resource
    private OrderTaskService orderTaskService;

    public void oa (String orgCode) throws InvocationTargetException, IllegalAccessException {
        resourceCache.init();
        customerOrderItemProDecService.analysisOrderToProcess(resourceCache, orgCode);
        equipInfoService.initEquipLoad(orgCode);
        customerOrderPlanService.calculatorOA(resourceCache, orgCode);
    }

    public void sop (String orgCode) {
        resourceCache.init();
        productSOPService.calculateSOP(resourceCache, orgCode);
    }

    public void schedule (String orgCode) {
        resourceCache.init();
        equipInfoService.initEquipLoad(orgCode);
        orderTaskService.generate(resourceCache, orgCode);
    }

}
