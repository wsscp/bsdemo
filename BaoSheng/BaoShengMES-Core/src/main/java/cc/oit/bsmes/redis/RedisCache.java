package cc.oit.bsmes.redis;

import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.common.concurrent.RenameThreadPoolExecutor;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.inv.model.Mat;
        import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by JIN on 2015/5/27.
 */
public class RedisCache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private static RedisService redisService;
    private static ProcessQcService processQcService;
    private static ProductCraftsService productCraftsService;
    private static ProductProcessService productProcessService;
    private static EquipMESWWMappingService equipMESWWMappingService;
    private static EquipInfoService equipInfoService;
    private static final Executor executor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    public static void initService(){
        redisService = (RedisService)ContextUtils.getBean(RedisService.class);
        processQcService = (ProcessQcService)ContextUtils.getBean(ProcessQcService.class);
        productCraftsService = (ProductCraftsService)ContextUtils.getBean(ProductCraftsService.class);
        productProcessService = (ProductProcessService)ContextUtils.getBean(ProductProcessService.class);
        equipMESWWMappingService = (EquipMESWWMappingService)ContextUtils.getBean(EquipMESWWMappingService.class);
        equipInfoService = (EquipInfoService)ContextUtils.getBean(EquipInfoService.class);
    }

    public static void init(){
        initService();

//        initQCData();
//
//        initWWMappingData();

        initEquipData();

    }

    /**
     * 添加第二次替换数据
     */
    public static void initWWMappingData(){
        List<EquipMESWWMapping> list = equipMESWWMappingService.getAll();
        for (EquipMESWWMapping equipMESWWMapping : list) {
            redisService.add(equipMESWWMapping.getTagName(),equipMESWWMapping);
        }
    }

    /**
     * add equip_Line
     * add main_equip
     */
    public static void initEquipData(){
        List<EquipInfo> list = equipInfoService.getAllProductLine();
        for (EquipInfo equipInfo : list) {
            redisService.addList(equipInfo.getCode(),equipInfo);
            EquipInfo mainEquip = equipInfoService.getMainEquipByEquipLine(equipInfo.getCode());
            redisService.addList(equipInfo.getCode(), mainEquip);
        }
    }

    /**
     * 初始化QC数据
     */
    public static void initQCData(){
        List<ProductCrafts> list = productCraftsService.getAll();
        int num = list.size()/100;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for(int i = 0;i<num;i++){
            Runnable task = new AddQCDataTask(list.subList(i*100,(i+1)*100), countDownLatch,i);
            executor.execute(task);
            logger.info("启动第{}个线程", i);
        }

        Runnable task = new AddQCDataTask(list.subList(num*100,list.size()), countDownLatch,num);
        executor.execute(task);
        try {
            countDownLatch.await(1L, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static final class AddQCDataTask implements Runnable{
        private List<ProductCrafts> productCraftses;
        private CountDownLatch countDownLatch;
        private Integer taskId;

        private AddQCDataTask(List<ProductCrafts> productCraftses,CountDownLatch countDownLatch,Integer taskId){
            this.countDownLatch = countDownLatch;
            this.productCraftses = productCraftses;
            this.taskId =  taskId;
        }

        @Override
        public void run() {
            for (ProductCrafts productCrafts : productCraftses) {
                List<ProductProcess> processes = productProcessService.getByProductCraftsId(productCrafts.getId());
                for (ProductProcess process : processes) {
                    List<ProcessQc> qcs = processQcService.getByProcessId(process.getId());
                    redisService.addList(process.getId(),qcs);
                }
            }
            logger.info("线程{}结束,剩余{}个线程", taskId, countDownLatch.getCount());
            countDownLatch.countDown();
        }
    }


}
