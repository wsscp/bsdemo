package cc.oit.bsmes.common.util;

import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.common.concurrent.RenameRunnable;
import cc.oit.bsmes.common.concurrent.RenameThreadPoolExecutor;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.wip.model.WorkOrder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Chanedi on 2014/9/15.
 */
@ThreadSafe
public class EquipLoadCache {

    private static final Logger logger = LoggerFactory.getLogger(EquipLoadCache.class);
    private static int nThreads = 10; // 线程数量
    private static final int TRY_TIMES = 3; // 重试次数上限

    private static final InitState initState = new InitState();
    private static Map<String, Object> fixedOrderMap; // orderItemProDecId, Object 应为ConcurrentHashMap
    private static final WorkOrder EMPTY = new WorkOrder(); // 空任务，用于表示任务将被删除
    private static final Executor executor = new RenameThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    private static EquipInfoService equipInfoService;
    private static OrderTaskService orderTaskService;
    private static EquipCalendarService equipCalendarService;

    public static boolean isEmptyOrder(Object orderTask) {
        return orderTask == EMPTY;
    }

    public static void putFixedOrderEmpty(String orderItemProDecId) {
        putFixedOrder(orderItemProDecId, EMPTY);
    }

    public static void putFixedOrder(String orderItemProDecId, Object order) {
        logger.info("putFixedOrderTask:{}", orderItemProDecId);
        fixedOrderMap.put(orderItemProDecId, order);
    }

    public static Object getFixedOrder(String orderItemProDecId) throws TimeOutException {
        int i = 0;
        Object order = null;
        do {
            order = fixedOrderMap.get(orderItemProDecId);
            i++;
        } while (order != null && i <= 3);
        if (order == null) {
            try {
//                logger.info("sleep for getFixedOrderTask:{}", orderItemProDecId);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            order = fixedOrderMap.get(orderItemProDecId);
        }
        if (order == null) {
            logger.info("time out for getFixedOrderTask:{}", orderItemProDecId);
            throw new TimeOutException();
        }
        return order;
    }

    public static void init(String orgCode) {
        logger.info("==============================开始调整设备运行状态==============================");
        if (!initState.initIfNot()) {
            // 设置初始化状态，如已初始化中则返回
            return;
        }
        // 以下仅一个线程会执行

        try {
            String poolThreadCount = WebContextUtils.getPropValue("poolThreadCount");
            if(StringUtils.isNotBlank(poolThreadCount)){
                nThreads = Integer.parseInt(poolThreadCount);
            }
        } catch (Exception e) {
            logger.warn("读取poolThreadCount异常，使用默认或上次设置的值", e);
            // 如有异常使用默认
        }
        try {
            if (equipInfoService == null) {
                equipInfoService = (EquipInfoService) ContextUtils.getBean(EquipInfoService.class);
            }
            if (orderTaskService == null) {
                orderTaskService = (OrderTaskService) ContextUtils.getBean(OrderTaskService.class);
            }
            if (equipCalendarService == null) {
                equipCalendarService = (EquipCalendarService) ContextUtils.getBean(EquipCalendarService.class);
            }
            fixedOrderMap = new ConcurrentHashMap<String, Object>();

            List<EquipInfo> equipInfoList = equipInfoService.getByOrgCode(orgCode, EquipType.PRODUCT_LINE);

            CountDownLatch countDownLatch = new CountDownLatch(equipInfoList.size());
            for (final EquipInfo equipInfo : equipInfoList) {
                Runnable task = new EquipLoadTask(equipInfo, countDownLatch);
                executor.execute(task);
            }
            countDownLatch.await(1L, TimeUnit.HOURS);
            if (countDownLatch.getCount() > 0) {
                logger.warn("设备运行状态初始化超时退出！");
            }
            initState.completeInit();
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();// 恢复中断标志
        } finally {
            initState.finishInit();
        }
        logger.info("==============================结束调整设备运行状态==============================");
    }

    private static final class EquipLoadTask implements RenameRunnable {

        private EquipInfo equipInfo;
        private CountDownLatch countDownLatch;
        private Counter counter;

        private EquipLoadTask(EquipInfo equipInfo, CountDownLatch countDownLatch) {
            this.equipInfo = equipInfo;
            this.countDownLatch = countDownLatch;
            this.counter = new Counter();
        }

        @Override
        public void run() {
            try {
                orderTaskService.fixEquipLoad(equipInfo, counter);
            } catch (TimeOutException e) {
                // 超时返回待重试
                executor.execute(this);
                return;
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
                throw new RuntimeException(e);
            }
            equipInfo.setEquipCalendar(equipCalendarService.getByEquipInfo(equipInfo, null, null));
            countDownLatch.countDown();
        }

        @Override
        public String getName() {
            return equipInfo.getName();
        }
    }

    public static class TimeOutException extends Exception {
    }

    @ThreadSafe
    private static final class InitState {
        @GuardedBy("this")
        @Getter
        private volatile boolean initing = false;
        @Getter
        @GuardedBy("this")
        private volatile boolean inited = false;

        /**
         * @return false 如已在初始化中
         */
        public boolean initIfNot() {
            if (initing) { // 事先检查减少加锁
                return false;
            }
            synchronized (this) {
                if (initing) {
                    return false;
                }
                initing = true;
                inited = false;
            }
            return true;
        }

        /**
         * 成功init
         */
        public void completeInit() {
            inited = true;
        }

        /**
         * init结束
         */
        public void finishInit() {
            initing = false;
        }

    }

    public static class Counter {

        @Getter
        private int count = -1;

        public void increase() {
            count++;
        }

    }

}


