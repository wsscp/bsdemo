package cc.oit.bsmes.pla.schedule;

import cc.oit.bsmes.common.constants.ProcessCode;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.pla.constants.PreciseMatchMode;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.HighPriorityOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.schedule.matcher.PreciseEquipMatcher;
import cc.oit.bsmes.pla.schedule.matcher.candidateHandler.CandidateHandlersGetter;
import cc.oit.bsmes.pla.schedule.matcher.candidateHandler.ICandidatesHandler;
import cc.oit.bsmes.pla.schedule.matcher.candidateHandler.MatCodeCandidatesHandler;
import cc.oit.bsmes.pla.schedule.model.OrderListToSchedule;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.HighPriorityOrderItemProDecService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by 羽霓 on 2014/5/20.
 */
public class Scheduler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ResourceCache resourceCache;
    private String orgCode;
    private Date daysToSchedule;

    private CustomerOrderItemProDecService customerOrderItemProDecService;
    private HighPriorityOrderItemProDecService highPriorityOrderItemProDecService;
    private WorkOrderService workOrderService;
    private OrderTaskService orderTaskService;
    private CandidateHandlersGetter candidateHandlersGetter;

    /*
     * 可合并待生产订单。
     * 包括ordersToSchedule和ordersInProgress；
     */
    private List<CustomerOrderItemProDec> ordersToCombine;
    // 三天内待生产订单(包括ordersHighPriority)
    private List<CustomerOrderItemProDec> ordersToSchedule;
    // 优先顺序订单
    private List<CustomerOrderItemProDec> ordersHighPriority;
    // 三天外生产中订单
    private List<CustomerOrderItemProDec> ordersInProgress;
    private PreciseEquipMatcher preciseEquipMatcher;

    private Map<CustomerOrderItemProDec, OrderListToSchedule> orderListToSchedules;
    private Multimap<String, CustomerOrderItemProDec> orderItemCache; // 输入集合为ordersToSchedule，用于识别相关订单
    private Multimap<String, CustomerOrderItemProDec> processOrdersToSchedule; // 用于求出瓶颈工序，输入集合为ordersToSchedule
    private Map<String, Map<String, Multimap<String, CustomerOrderItemProDec>>> processCandidateMaps;// 按process/handlerkey/进行合并，输入集合为ordersToCombine

    private String bottleProcessCode;

    public Scheduler(ResourceCache resourceCache, Date daysToSchedule, Date allDays, String orgCode) {
        customerOrderItemProDecService = (CustomerOrderItemProDecService) ContextUtils.getBean(CustomerOrderItemProDecService.class);
        highPriorityOrderItemProDecService = (HighPriorityOrderItemProDecService) ContextUtils.getBean(HighPriorityOrderItemProDecService.class);
        workOrderService = (WorkOrderService) ContextUtils.getBean(WorkOrderService.class);
        candidateHandlersGetter = (CandidateHandlersGetter) ContextUtils.getBean(CandidateHandlersGetter.class);
        this.orgCode = orgCode;
        this.daysToSchedule = daysToSchedule;
        this.resourceCache = resourceCache;

        ordersToCombine = customerOrderItemProDecService.getUnaudited(orgCode, allDays);
        preciseEquipMatcher = new PreciseEquipMatcher(resourceCache, orgCode, daysToSchedule, ordersToCombine);

        Map<String, CustomerOrderItemProDec> orderCache = new HashMap<String, CustomerOrderItemProDec>();
        // 初始化orderItemCache和processCandidateMaps
        initCache(orderCache);
        // 保持ordersToCombine、ordersToSchedule、ordersHighPriority、ordersInProgress中order对象的一致
        initOrdersToSchedule(orderCache);
        initOrdersHighPriority(orderCache);
        initOrdersInProgress(orderCache);
        combineOrders();
    }

    private void initCache(Map<String, CustomerOrderItemProDec> orderCache) {
        Multimap<String, CustomerOrderItemProDec> nextOrderCache = ArrayListMultimap.create();
        orderItemCache = ArrayListMultimap.create();
        processCandidateMaps = new HashMap<String, Map<String, Multimap<String, CustomerOrderItemProDec>>>();

        for (CustomerOrderItemProDec order : ordersToCombine) {
            nextOrderCache.put(order.getNextOrderId(), order);
            orderCache.put(order.getId(), order);
            orderItemCache.put(order.getOrderItemDecId(), order);

            String processCode = order.getProcessCode();
            Map<String, Multimap<String, CustomerOrderItemProDec>> candidateMaps = processCandidateMaps.get(processCode);
            if (candidateMaps == null) {
                candidateMaps = new HashMap<String, Multimap<String, CustomerOrderItemProDec>>();
                processCandidateMaps.put(processCode, candidateMaps);
            }
            prepareCandidate(candidateMaps, order);
        }
        for (CustomerOrderItemProDec order : ordersToCombine) {
            order.setLastOrders(nextOrderCache.get(order.getId()));
        }
    }

    private void initOrdersToSchedule(Map<String, CustomerOrderItemProDec> orderCache) {
        ordersToSchedule = new ArrayList<CustomerOrderItemProDec>();
        processOrdersToSchedule = ArrayListMultimap.create();

        List<CustomerOrderItemProDec> orders = customerOrderItemProDecService.getUnaudited(orgCode, daysToSchedule);
        for (CustomerOrderItemProDec order : orders) {
            CustomerOrderItemProDec realOrder = orderCache.get(order.getId());

            ordersToSchedule.add(realOrder);

            String processCode = order.getProcessCode();
            processOrdersToSchedule.put(processCode, realOrder);
        }
    }

    private void initOrdersInProgress(Map<String, CustomerOrderItemProDec> orderCache) {
        ordersInProgress = new ArrayList<CustomerOrderItemProDec>();
        List<CustomerOrderItemProDec> orders = customerOrderItemProDecService.getItemDecInProgress(orgCode, daysToSchedule);
        for (CustomerOrderItemProDec order : orders) {
            CustomerOrderItemProDec realOrder = orderCache.get(order.getId());
            ordersInProgress.add(realOrder);
        }
    }

    private void initOrdersHighPriority(Map<String, CustomerOrderItemProDec> orderCache) {
        ordersHighPriority = new ArrayList<CustomerOrderItemProDec>();
        List<HighPriorityOrderItemProDec> orders = highPriorityOrderItemProDecService.getByOrgCode(orgCode);
        for (HighPriorityOrderItemProDec order : orders) {
            CustomerOrderItemProDec realOrder = orderCache.get(order.getId());
            ordersHighPriority.add(realOrder);
        }
    }

    private void prepareCandidate(Map<String, Multimap<String, CustomerOrderItemProDec>> candidateMaps, CustomerOrderItemProDec order) {
        String processCode = order.getProcessCode();
        // 获取当前工序的合并条件序列
        ICandidatesHandler[] handlers = candidateHandlersGetter.getCandidatesHandlers(processCode);

        if (candidateMaps.size() == 0) {
            for (ICandidatesHandler handler : handlers) {
                Multimap<String, CustomerOrderItemProDec> candidateMap = ArrayListMultimap.create();
                candidateMaps.put(handler.getMapKey(), candidateMap);
            }
        }

        // 按产出物料号、规格、颜色、大小分类订单为map
        for (ICandidatesHandler handler : handlers) {
            Multimap<String, CustomerOrderItemProDec> candidateMap = candidateMaps.get(handler.getMapKey());
            candidateMap.put(handler.getCandidatesKey(order), order);
        }
    }

    // 输入集合为processCandidateMaps，只合并相同物料
    private void combineOrders() {
        orderListToSchedules = new HashMap<CustomerOrderItemProDec, OrderListToSchedule>();

        // 时间优化候选
        Set<Map.Entry<String, Map<String, Multimap<String, CustomerOrderItemProDec>>>> processCandidateMapEntrySet = processCandidateMaps.entrySet();
        for (Map.Entry<String, Map<String, Multimap<String, CustomerOrderItemProDec>>> processCandidateMapEntry : processCandidateMapEntrySet) {
            String processCode = processCandidateMapEntry.getKey();
            if (ScheduleUtils.isVirtualProcess(processCode)) {
                // 虚拟设备逻辑较简单，可以进行简化 TODO
//                continue;
            }

            Map<String, Multimap<String, CustomerOrderItemProDec>> processCandidateMap = processCandidateMapEntry.getValue();
            Multimap<String, CustomerOrderItemProDec> matCodeMap = processCandidateMap.get(MatCodeCandidatesHandler.MAP_KEY);
            Set<String> matCodeSet = matCodeMap.keySet();
            for (String matCode : matCodeSet) {
                List<CustomerOrderItemProDec> orders = (List<CustomerOrderItemProDec>) matCodeMap.get(matCode);

                // 优先考虑固定设备
                combineFixedEquipOrders(orders);
                if (orders.size() == 0) {
                    continue;
                }
                combineNonFixedEquipOrders(orders);
            }
        }

        preciseEquipMatcher.setOrderListToScheduleMap(orderListToSchedules);
    }

    public List<CustomerOrderItemProDec> getOrdersMatched() {
        return preciseEquipMatcher.getOrdersMatched();
    }

    public void schedulePause() {
        logger.info("==============    【暂停订单】    ==============");
        // 按最早排优先顺序，且不插空档。
        preciseEquipMatcher.setPreciseMatchMode(PreciseMatchMode.PRIORITY);

        List<WorkOrder> pausedWorkOrders = workOrderService.getPausedWorkOrders(orgCode);
        for (WorkOrder pausedWorkOrder : pausedWorkOrders) {
            preciseEquipMatcher.match(pausedWorkOrder);
            workOrderService.update(pausedWorkOrder);
        }
    }

    /**
     * 1、按最早排优先顺序，且不插空档。（考虑合并排程）
     * 2、排完后依次排前道工序（不在ordersHighPriority的部分）。（考虑合并排程）
     * 3、压缩空档。（已删除）
     */
    public void scheduleHighPriority() {
        logger.info("==============    【优先顺序订单】    ==============");
        // 按最早排优先顺序，且不插空档。
        preciseEquipMatcher.setPreciseMatchMode(PreciseMatchMode.PRIORITY);
        for (CustomerOrderItemProDec order : ordersHighPriority) {
        	if(order==null)
        	{
        		continue;
        	}
            if (order.getStatus() != ProductOrderStatus.TO_DO) {
                highPriorityOrderItemProDecService.deleteById(order.getId());
                continue;
            }
            if (order.getIsLocked()) {
                continue;
            }
            matchLastOrders(order, ordersToSchedule);
        }
//        compressAll();
    }


    public void scheduleBottleUnRelated() {
        logger.info("==============    【与瓶颈无关剩余工序排程】    ==============");
        matchLeftInOrderList(ordersToSchedule);
    }

    public void scheduleInProgressForNotUrgent() {
        logger.info("===========    【三天外已开始生产订单排程（无法合并的部分）】    ===========");
        matchLeftInOrderList(ordersInProgress);
    }

    public void compressAll() {
        // 压缩所有设备空档。
        preciseEquipMatcher.compressAll();
    }

    private void combineFixedEquipOrders(List<CustomerOrderItemProDec> orders) {
        // 时间优化候选
        orders = new LinkedList<CustomerOrderItemProDec>(orders);
        Multimap<String, CustomerOrderItemProDec> equipOrderMap = ArrayListMultimap.create();
        Iterator<CustomerOrderItemProDec> it = orders.iterator();
        while (it.hasNext()) {
            CustomerOrderItemProDec order = it.next();
            String fixedEquipCode = order.getFixedEquipCode();
            if (fixedEquipCode != null) {
                equipOrderMap.put(fixedEquipCode, order);
                it.remove();
            }
        }
        // 剩余订单有有此固定设备的也可以合并
        Set<String> equipOrderKeySet = equipOrderMap.keySet();
        it = orders.iterator();
        while (it.hasNext()) {
            CustomerOrderItemProDec order = it.next();
            List<String> availableEquipCodes = ScheduleUtils.getAvailableEquipCodes(order, resourceCache);
            for (String equipCode : equipOrderKeySet) {
                if (availableEquipCodes.contains(equipCode)) {
                    equipOrderMap.put(equipCode, order);
                    it.remove();
                }
            }
        }

        for (String equipCode : equipOrderKeySet) {
            OrderListToSchedule orderListToSchedule = new OrderListToSchedule(resourceCache);
            List<String> defaultEquipInfos = new ArrayList<String>();
            defaultEquipInfos.add(equipCode);

            orderListToSchedule.setOrders((List<CustomerOrderItemProDec>) equipOrderMap.get(equipCode));
            orderListToSchedule.setDefaultEquipCodes(defaultEquipInfos);
            addToOrderListToSchedules(orderListToSchedule);
        }
    }

    private void combineNonFixedEquipOrders(List<CustomerOrderItemProDec> orders) {
        // 时间优化候选
        orders = new LinkedList<CustomerOrderItemProDec>(orders);
        // 无固定设备的一般默认设备相同
        Iterator<CustomerOrderItemProDec> it = orders.iterator();
        CustomerOrderItemProDec firstOrder = it.next();
        List<String> defaultEquipCodes = ScheduleUtils.getDefaultEquipCodes(firstOrder, resourceCache);
        List<String> optionalEquipCodes = ScheduleUtils.getOptionalEquipCodes(firstOrder);
        // 寻找optionalEquipInfos交集
        while (optionalEquipCodes.size() > 0 && it.hasNext()) {
            CustomerOrderItemProDec order = it.next();
            List<String> optionals = ScheduleUtils.getOptionalEquipCodes(order);
            Iterator<String> optionalEquipCodeIt = optionalEquipCodes.iterator();
            while (optionalEquipCodeIt.hasNext()) {
                String optionalEquipCode = optionalEquipCodeIt.next();
                if (!optionals.contains(optionalEquipCode)) {
                    optionalEquipCodeIt.remove();
                }
            }
        }

        OrderListToSchedule orderListToSchedule = new OrderListToSchedule(resourceCache);

        orderListToSchedule.setOrders(orders);
        orderListToSchedule.setDefaultEquipCodes(defaultEquipCodes);
        orderListToSchedule.setOptionalEquipCodes(optionalEquipCodes);
        addToOrderListToSchedules(orderListToSchedule);
    }

    private void addToOrderListToSchedules(OrderListToSchedule orderListToSchedule) {
        List<CustomerOrderItemProDec> orders = orderListToSchedule.getOrders();
        for (CustomerOrderItemProDec order : orders) {
            orderListToSchedules.put(order, orderListToSchedule);
        }
    }

    // 排所有下道工序，同时保证所有前道工序已排
    private void matchNextOrderAndEnsureLast(CustomerOrderItemProDec order, List<CustomerOrderItemProDec> listShouldInclude) {
        // 得到最后一道工序订单
        while (order.getNextOrder() != null) {
            order = order.getNextOrder();
        }

        // 排所有前道工序
        matchLastOrders(order, listShouldInclude);
    }

    /**
     *
     * @param shouldMatch 为false则无须排程仅遍历
     */
    private void matchNextOrder(CustomerOrderItemProDec order, List<CustomerOrderItemProDec> listShouldInclude, boolean shouldMatch) {
        CustomerOrderItemProDec nextOrder = order.getNextOrder();
        if (nextOrder == null) {
            return;
        }
        if (isInListAndNotScheduled(nextOrder, listShouldInclude) && shouldMatch) {
            CustomerOrderItemProDec match = match(nextOrder, true);
            if (match == null) {
                return; // 后续已执行过（执行的为shouldMatch为false的情况）
            }
        }

        matchNextOrder(nextOrder, listShouldInclude, shouldMatch);
    }

    // 找到最前道工序往后排
    private void matchLastOrders(CustomerOrderItemProDec order, List<CustomerOrderItemProDec> listShouldInclude) {
        List<CustomerOrderItemProDec> lastOrders = order.getLastOrders();
        if (lastOrders == null) {
            lastOrders = new ArrayList<CustomerOrderItemProDec>();
        }
        for (CustomerOrderItemProDec lastOrder : lastOrders) {
            matchLastOrders(lastOrder, listShouldInclude);
        }

        if (isInListAndNotScheduled(order, listShouldInclude)) {
            match(order, true);
        }
    }

    private void matchLeftInOrderList(List<CustomerOrderItemProDec> orderList) {
        while (orderList.size() > 0) {
            CustomerOrderItemProDec order = orderList.get(0);
            preciseEquipMatcher.setPreciseMatchMode(PreciseMatchMode.GENERAL);
            matchNextOrderAndEnsureLast(order, orderList);
        }
    }

    // 在listShouldInclude中且未排过
    private boolean isInListAndNotScheduled(CustomerOrderItemProDec order, List<CustomerOrderItemProDec> listShouldInclude) {
        if (ordersToCombine == listShouldInclude) {
            return listShouldInclude.remove(order); // 避免remove多次造成性能问题
        }
        boolean listInclude = listShouldInclude.remove(order);
        return listInclude && ordersToCombine.remove(order);
    }

    /**
     * @param abandonNextIfFailed 如果排程失败则后续订单也不再排程
     * @return
     */
    private CustomerOrderItemProDec match(CustomerOrderItemProDec order, boolean abandonNextIfFailed) {
        OrderListToSchedule orderListToSchedule = orderListToSchedules.get(order);
        CustomerOrderItemProDec match = preciseEquipMatcher.match(orderListToSchedule);
        if (match == null && abandonNextIfFailed) {
            logger.warn("前道工序排程失败，后道工序也不再排程");
            matchNextOrder(order, ordersToCombine, false);
        }
        String processCode = order.getProcessCode();
        if (match != null && match.equals(orderListToSchedule.getLastOrder())) {
            // 全部排完，继续按颜色等合并
            ICandidatesHandler[] handlers = candidateHandlersGetter.getCandidatesHandlers(processCode);
            loop : for (int i = 1; i < handlers.length; i++) { // 同产出已合并过不再遍历
                ICandidatesHandler handler = handlers[i];

                Map<String, Multimap<String, CustomerOrderItemProDec>> candidateMaps = processCandidateMaps.get(processCode);
                List<CustomerOrderItemProDec> candidateList = (List<CustomerOrderItemProDec>) candidateMaps.get(handler.getMapKey()).get(handler.getCandidatesKey(match));

                for (int j = 0; j < candidateList.size(); j++) {
                    CustomerOrderItemProDec candidate = candidateList.get(j);
                    if (!ordersToCombine.contains(candidate)) {
                        candidateList.remove(j);
                        j--;
                        // 已排过
                        continue;
                    }
                    match(candidate, abandonNextIfFailed);
                    break loop;
                }
            }
        }
        return match;
    }


}
