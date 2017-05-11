package cc.oit.bsmes.redis;

import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.service.ProcessQcService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JIN on 2015/5/26.
 */
public class RedisTest extends BaseTest{

    @Resource
    private EquipInfoService equipInfoService;

    @Resource
    private RedisService redisService;

    @Resource
    private ProcessQcService processQcService;

    private static final Gson gson = new GsonBuilder().create();

    @Test
    public void testQcInit(){
        List<EquipInfo> equipInfos = equipInfoService.getAllProductLine();
        for (EquipInfo equipInfo : equipInfos) {
            System.out.println(gson.toJson(equipInfo,EquipInfo.class));

        }
    }

    @Test
    public void find(){
        RedisCache.init();

    /*    List<EquipInfo> list = redisService.lRange("446-095",EquipInfo.class);
        for (EquipInfo equipInfo : list) {
            logger.info("{},{}",equipInfo.getCode(),equipInfo.getType().toString());
        }*/
    }


    @Test
    public void getTest(){
        String processId = "669ad898-cafe-4745-843c-3d62ab4a41ed";
        List<ProcessQc> list = processQcService.getByProcessId(processId);
        long start = System.currentTimeMillis();
        for (ProcessQc processQc : list) {
            redisService.addList(processId, processQc);
        }
        logger.info("use time:{}", System.currentTimeMillis() - start);

        redisService.remove(processId);

        start = System.currentTimeMillis();
        redisService.addList(processId,list);
        logger.info("use time:{}",System.currentTimeMillis()-start);
        redisService.remove(processId);
    }
}
