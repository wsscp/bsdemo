package cc.oit.bsmes.integration;

import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jinhy on 2015/4/15.
 */
public class CheckCrafts extends BaseTest{
    ;
    @Resource
    private ProductCraftsService productCraftsService;
    @Resource
    private ResourceCache resourceCache;

    private final static String OUT = "OUT";
    private final static String IN = "IN";


    @Test
    public void checkProcessInOut(){
        List<ProductCrafts> list = productCraftsService.getAll();
        for (ProductCrafts crafts : list) {
            Map<String,Map<String,Map<String,ProcessInOut>>> map = new HashMap<String, Map<String, Map<String,ProcessInOut>>>();
            List<ProductProcess> processs = resourceCache.getProductProcessByCraftId(crafts.getId());
            for (ProductProcess process : processs) {
                   Map<String,Map<String,ProcessInOut>> inOutMap = map.get(process.getId());
                   if(inOutMap == null){
                       inOutMap = new HashMap<String, Map<String,ProcessInOut>>();
                   }
                   Map<String,Map<String,ProcessInOut>> nextInoutMap = map.get(process.getNextProcessId());
                   List<ProcessInOut> inOuts = process.getProcessInOutList();
                   for (ProcessInOut inOut : inOuts) {
                        if(inOut.getInOrOut().chackInOrOut()){
                            Map<String,ProcessInOut> inMap =  inOutMap.get(IN);
                            if(inMap == null){
                                inMap = new HashMap<String, ProcessInOut>();
                            }
                            inMap.put(inOut.getMatCode(),inOut);
                            inOutMap.put(IN,inMap);
                           // logger.info("工序：{}，投入{}",process.getId(),inOut.getMatCode());
                        }else{
                            Map<String,ProcessInOut> outMap =   inOutMap.get(OUT);
                            if(outMap == null){
                                outMap = new HashMap<String, ProcessInOut>();
                            }
                            outMap.put(inOut.getMatCode(),inOut);
                            inOutMap.put(OUT,outMap);
                            //logger.info("工序：{}，产出{}",process.getId(),inOut.getMatCode());
                            if(nextInoutMap != null){
                                Map<String,ProcessInOut> nextInMap = nextInoutMap.get(IN);
                                if(nextInMap == null){
                                    logger.info("工序：{} 投入为空",process.getNextProcessId());
                                }else{
                                    ProcessInOut inOut1= nextInMap.get(inOut.getMatCode());
                                    if(inOut1 == null){
                                        logger.info("工序:{} 的产出{}在下道工序{}的投入不存在",process.getId(),inOut.getMatCode(),process.getNextProcessId());
                                    }
                                }
                               // logger.info("工序：{}的产出{}在下一工序的投入存在",process.getId(),inOut.getMatCode());
                            }
                        }
                   }
                   map.put(process.getId(),inOutMap);
            }



        }

    }

}
