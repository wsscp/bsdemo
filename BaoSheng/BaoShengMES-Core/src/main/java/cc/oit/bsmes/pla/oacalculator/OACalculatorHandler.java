package cc.oit.bsmes.pla.oacalculator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.WorkTask;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.WorkTaskService;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDecOA;
import cc.oit.bsmes.pla.model.OaMrp;
import cc.oit.bsmes.pla.model.OaUptemp;
import cc.oit.bsmes.pla.oacalculator.model.EquipCapacityLoad;
import cc.oit.bsmes.pla.oacalculator.model.ProductProcessDto;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecOAService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.OaMrpService;
import cc.oit.bsmes.pla.service.OaUptempService;
import cc.oit.bsmes.pro.model.ProcessInOutWip;
import cc.oit.bsmes.pro.model.ProductProcessWip;
import cc.oit.bsmes.pro.service.ProcessInOutWipService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessWipService;

@Service
public class OACalculatorHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private CustomerOrderItemDecService customerOrderItemDecService;
	@Resource
	private CustomerOrderItemProDecOAService customerOrderItemProDecOAService;
	@Resource
	private ProductProcessWipService productProcessWipService;
	@Resource
	private ProcessInOutWipService processInOutWipService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private OaMrpService oaMrpService;
	@Resource
	private CustomerOrderService customerOrderService;
	@Resource
	private EquipCalendarService equipCalendarService;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private WorkTaskService workTaskService;
	@Resource
	private OaUptempService oaUptempService;

	/**
	 * 缓存对象
	 * 
	 * @param outMatCacheMap 产出半成品
	 * @param customerOrderCacheMap 订单产品
	 * @param craftsCacheMap 工艺
	 * @param processCacheMap 工序
	 * @param orderPlanFinishedTimeCacheMap 订单完成时间
	 * @param equipCapacityCacheMap 设备能力
	 * @param inMatCacheMap 投入物料
	 */
	private CacheMapThreadLocal<ProcessInOutWip> outMatCacheMap = new CacheMapThreadLocal<ProcessInOutWip>();
	private CacheMapThreadLocal<CustomerOrder> customerOrderCacheMap = new CacheMapThreadLocal<CustomerOrder>();
	private CacheMapThreadLocal<String> craftsCacheMap = new CacheMapThreadLocal<String>();
	private CacheMapThreadLocal<ProductProcessDto> processDtoCacheMap = new CacheMapThreadLocal<ProductProcessDto>();
	private CacheMapThreadLocal<Date> orderPlanFinishedTimeCacheMap = new CacheMapThreadLocal<Date>();
	private CacheMapThreadLocal<EquipCapacityLoad> equipCapacityLoadCacheMap = new CacheMapThreadLocal<EquipCapacityLoad>();
	private CacheMapThreadLocal<List<ProcessInOutWip>> inMatCacheMap = new CacheMapThreadLocal<List<ProcessInOutWip>>();

	/**
	 * 订单分解到工序: 从当前时间开始算起的，最新导入的300个有工序的订单
	 * 
	 * @author DingXintao
	 * @date 2015-08-27
	 * */
	@Transactional(readOnly = false)
	public void analysisOrderToProcess(String orgCode) {
		// 暂时先删光

		List<CustomerOrderItem> orderItemArray = customerOrderItemService.getUncompleted(orgCode,
				BusinessConstants.ORDER_NUM);
		for (CustomerOrderItem orderItem : orderItemArray) {
			// 0、查看是否需要重新分解，已经分解的就不再分，提高效率:暂时忽略重新设置工艺的问题
			if (customerOrderItemProDecOAService.countProDecOa(orderItem.getId()) > 0) {
				continue;
			}

			// 1、删除下面的PRO_DEC及设备占用和物料需求
			customerOrderItemProDecOAService.deleteByOrderItemId(orderItem.getId(), "");

			// 验证工艺正确性
			if (!this.validateOrder(orderItem.getCraftsId())) {
				continue;
			}

			// 查询分卷
			List<CustomerOrderItemDec> orderItemDecArray = customerOrderItemDecService.getByOrderItemId(orderItem
					.getId());
			for (CustomerOrderItemDec orderItemDec : orderItemDecArray) {
				ProductProcessWip findParams = new ProductProcessWip();
				findParams.setProductCraftsId(orderItem.getCraftsId());
				findParams.setNextProcessId("-1");
				this.insertProdecOA(findParams, orderItem, orderItemDec, orgCode);
			}
		}
	}

	/**
	 * 递归新增PRODECOA
	 * 
	 * @param findParams 递归需要的查询条件
	 * @param orderItem 订单产品
	 * @param orderItemDec 订单产品分卷
	 * @param orgCode 组织编码
	 * */
	private void insertProdecOA(ProductProcessWip findParams, CustomerOrderItem orderItem,
			CustomerOrderItemDec orderItemDec, String orgCode) {

		List<ProductProcessWip> processArray = productProcessWipService.findByObj(findParams);
		for (ProductProcessWip process : processArray) {

			ProcessInOutWip processInOut = this.getProcessOut(process.getId());
			if (null == processInOut) {
				break;
			}

			CustomerOrderItemProDecOA coip = new CustomerOrderItemProDecOA();
			coip.setOrderItemDecId(orderItemDec.getId());
			coip.setCraftsId(findParams.getProductCraftsId());
			coip.setProcessId(process.getId());
			coip.setUseStock(false);
			coip.setIsLocked(false);
			coip.setHalfProductCode(processInOut.getMatCode());
			coip.setUnFinishedLength(orderItemDec.getLength());
			coip.setStatus(ProductOrderStatus.TO_DO);
			coip.setProcessPath(process.getFullPath());
			coip.setProcessName(process.getProcessName());
			coip.setProcessCode(process.getProcessCode());
			coip.setContractNo(orderItem.getContractNo());
			coip.setProductCode(orderItem.getProductCode());
			coip.setProductSpec(orderItem.getProductSpec());
			coip.setOrgCode(orgCode);
			// coip.setParrelCount(1);
			customerOrderItemProDecOAService.insert(coip);

			// 获取上一道工序
			ProductProcessWip preFindParams = new ProductProcessWip();
			preFindParams.setProductCraftsId(process.getProductCraftsId());
			preFindParams.setNextProcessId(process.getId());
			this.insertProdecOA(preFindParams, orderItem, orderItemDec, orgCode);
		}

	}

	/**
	 * <修改版> 客户订单OA推算: 从当前时间开始算起的，最新导入的300个有工序的订单
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @throws ParseException
	 */
	@Transactional(readOnly = false)
	public void calculatorOA(String orgCode) throws ParseException {
		// 1、准备工作：设置业务系统参数、清理设备产能WorkTask、物料需求OaMrp
		BusinessConstants.PRIORITY_USED_ECALENDAR_DAY_NUM = 3;
		BusinessConstants.ECALENDAR_DAY_NUM = this.getDaysByParam(BusinessConstants.DAYS_TO_SCHEDULE, orgCode);
		equipInfoService.callInitOrderTask(orgCode);
		// 2、初始化设备能力 , 速度太慢，重新优化
		// this.initEquipCapacityLoad(orgCode);
		// 3、获取订单产品任务列表
		List<CustomerOrderItem> customerOrderItemList = customerOrderItemService.getUncompleted(orgCode,
				BusinessConstants.ORDER_NUM);
		int i = 0;
		logger.debug("********* 总共{}个任务*************", customerOrderItemList.size());
		for (CustomerOrderItem orderItem : customerOrderItemList) {
			// 4、判断是否有效可以计算的订单产品
			if (!this.validateOrder(orderItem.getCraftsId())) { // 验证工艺情况
				continue;
			}

			logger.debug("========ORDER ITEM ID{}开始第{}个============", orderItem.getId(), i);
			// 5、获取订单合同
			CustomerOrder order = this.getCustomerOrder(orderItem);
			// 6、定义一个合同的结束时间
			Date orderFinishedDate = orderPlanFinishedTimeCacheMap.get(order.getId()); // 合同订单完成时间，用来更新订单的
			Date orderItemFinishedDate = null;
			// 判断是否固定OA
			if (orderItem.getSubOaDate() != null) {
				// 7、如果是固定OA,逆向工序计算
				orderItemFinishedDate = this.calculatorFixedOA(orderItem, orgCode);
			} else {
				// 8、如果不是固定OA,正向工序计算
				orderItemFinishedDate = this.calculatorUnFixedOA(orderItem, orgCode);
			}
			// 9、将item的最后完成时间与订单的完成时间作对比,取最晚的那个时间
			if (null != orderItemFinishedDate
					&& (orderFinishedDate == null || orderFinishedDate.before(orderItemFinishedDate))) {
				orderPlanFinishedTimeCacheMap.put(order.getId(), orderItemFinishedDate);
			}
			logger.debug("========ORDER ITEM ID{} 结束第{}个============", orderItem.getId(), i);
			i++;
		}
		// 10、更新订单CUSTOMER_ORDER、超期新增事件
		this.finishedCalculatorOA(orgCode);
	}

	/**
	 * 计算有固定OA的订单交期
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param orderItem 订单产品
	 * @param orgCode 组织编码
	 * @throws ParseException
	 */
	private Date calculatorFixedOA(CustomerOrderItem orderItem, String orgCode) throws ParseException {
		// 1、获取订单产品下的分卷信息
		List<CustomerOrderItemDec> cusOrderItemDescList = orderItem.getCusOrderItemDesc();
		if (cusOrderItemDescList == null || cusOrderItemDescList.size() == 0) {
			return null;
		}

		// 2、获取工序DTO
		ProductProcessDto processDto = this.getProductProcess(orderItem.getCraftsId());
		// 3、工序和订单明细工序用时分解(卷)的缓存,避免多次嵌套循环
		Map<String, List<CustomerOrderItemProDecOA>> processProDecCache = new HashMap<String, List<CustomerOrderItemProDecOA>>();
		for (CustomerOrderItemDec itemDesc : cusOrderItemDescList) {
			// 将每一卷和工序绑定,用于计算每一步工序上的设备使用情况
			List<CustomerOrderItemProDecOA> customerOrderItemProDecList = itemDesc.getCusOrderItemProDesOAList();
			for (CustomerOrderItemProDecOA proDec : customerOrderItemProDecList) {
				String processId = proDec.getProcessId();
				List<CustomerOrderItemProDecOA> processProDecList = processProDecCache.get(processId);
				if (processProDecList == null) {
					processProDecList = new ArrayList<CustomerOrderItemProDecOA>();
				}
				processProDecList.add(proDec);
				processProDecCache.put(processId, processProDecList);
			}
		}

		// 4、设置指定交期
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(orderItem.getSubOaDate());
		// TODO 指定交期暂时以指定日期的16点为结束时间
		endTime.set(endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH), endTime.get(Calendar.DATE), 16, 0, 0);

		// 5、设备占用任务对象，并调用逆向计算递归方法
		Map<String, WorkTask> workTaskCache = new HashMap<String, WorkTask>();
		Date finishedTime = this.reverseCalculateEndTime(processDto, endTime.getTime(), processProDecCache,
				workTaskCache); // 倒推订单产品结束时间
		// 6、若果返回值为null 说明原始OA时间内无法完成任务,从第一个工序从新开始计算OA时间
		if (null == finishedTime) {
			// 6.1、还原WorkTask到缓存，因为没保存，将用过的还回去
			this.back2EquipCapacity(workTaskCache);
			// 6.2、清理缓存
			workTaskCache.clear();
			// 6.3、调用正向计算递归方法
			Map<String, Boolean> tmp = new HashMap<String, Boolean>(); // 为了正向计算逻辑：前面计算可为空，但不能断层，添加一个缓存变量空间
			tmp.put("preCalculateNull", true); // 判断上道是否为空，因为有些工序开始的部门可能已经做了
			tmp.put("immediateBack", false); // 判断是否立即返回
			finishedTime = this.forwardCalculateEndTime(processDto, new Date(), processProDecCache, workTaskCache, tmp); // 正推订单产品结束时间
		}
		// 7、计算不到结果要还原WorkTask到缓存，因为没保存，将用过的还回去
		if (null == finishedTime) {
			this.back2EquipCapacity(workTaskCache);
			return finishedTime;
		}
		// 8、完成善后工作：保存设备占用产能表
		this.finishedCalculator(orderItem, finishedTime, processProDecCache, workTaskCache, orgCode);
		return finishedTime;
	}

	/**
	 * 计算无固定OA的订单交期
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param orderItem 订单产品
	 * @param orgCode 组织编码
	 * @throws ParseException
	 */
	private Date calculatorUnFixedOA(CustomerOrderItem orderItem, String orgCode) throws ParseException {
		// 1、获取订单产品下的分卷信息
		List<CustomerOrderItemDec> cusOrderItemDescList = orderItem.getCusOrderItemDesc();
		if (cusOrderItemDescList == null || cusOrderItemDescList.size() == 0) {
			return null;
		}

		// 2、获取工序DTO，同时把可用设备挂上去
		ProductProcessDto processDto = this.getProductProcess(orderItem.getCraftsId());
		// 3、工序和订单明细工序用时分解(卷)的缓存,避免多次嵌套循环
		Map<String, List<CustomerOrderItemProDecOA>> processProDecCache = new HashMap<String, List<CustomerOrderItemProDecOA>>();
		for (CustomerOrderItemDec itemDesc : cusOrderItemDescList) {
			// 将每一卷和工序绑定,用于计算每一步工序上的设备使用情况
			List<CustomerOrderItemProDecOA> customerOrderItemProDecList = customerOrderItemProDecOAService
					.getByCusOrderItemDecIdForPlan(itemDesc.getId());
			for (CustomerOrderItemProDecOA proDec : customerOrderItemProDecList) {
				String processId = proDec.getProcessId();
				List<CustomerOrderItemProDecOA> processProDecList = processProDecCache.get(processId);
				if (processProDecList == null) {
					processProDecList = new ArrayList<CustomerOrderItemProDecOA>();
				}
				processProDecList.add(proDec);
				processProDecCache.put(processId, processProDecList);
			}
		}

		// 4、设备占用任务对象，并调用正向计算递归方法
		Map<String, WorkTask> workTaskCache = new HashMap<String, WorkTask>();
		Map<String, Boolean> tmp = new HashMap<String, Boolean>(); // 为了正向计算逻辑：前面计算可为空，但不能断层，添加一个缓存变量空间
		tmp.put("preCalculateNull", true); // 判断上道是否为空，因为有些工序开始的部门可能已经做了
		tmp.put("immediateBack", false); // 判断是否立即返回
		Date finishedTime = this
				.forwardCalculateEndTime(processDto, new Date(), processProDecCache, workTaskCache, tmp); // 倒推订单产品结束时间
		// 5、计算不到结果要还原WorkTask到缓存，因为没保存，将用过的还回去
		if (null == finishedTime) {
			this.back2EquipCapacity(workTaskCache);
			return finishedTime;
		}
		// 6、完成善后工作：保存设备占用产能表
		this.finishedCalculator(orderItem, finishedTime, processProDecCache, workTaskCache, orgCode);
		return finishedTime;
	}

	/**
	 * 正向计算订单产品的加工结束时间
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param processDto 工序树结构对象
	 * @param startDate 正向计算输入开始时间
	 * @param processProDecCache 工序与工序对应的明细PRODEC（一个订单产品的，理论多个，实际情况只要一个）
	 * @param workTaskCache 设备占用任务缓存对象：最后需要保存
	 * @throws ParseException
	 * */
	private Date forwardCalculateEndTime(ProductProcessDto processDto, Date startDate,
			Map<String, List<CustomerOrderItemProDecOA>> processProDecCache, Map<String, WorkTask> workTaskCache,
			Map<String, Boolean> preCalculate) throws ParseException {
		if (preCalculate.get("immediateBack")) { // tmp: 整个都不需要计算了，无意义
			return null;
		}

		// 定义工序加工的结束时间、此道工序的开始结束时间、此道工序的结束时间
		Date finishTime = startDate;

		// 1、core：先递归计算上一道流程结束时间，再算自己，达到顺序计算的效果
		List<ProductProcessDto> preProcessDtoArray = processDto.getPreProcesses();
		if (null != preProcessDtoArray && preProcessDtoArray.size() > 0) {
			for (ProductProcessDto preProcessDto : preProcessDtoArray) {
				Date preFinishTime = this.forwardCalculateEndTime(preProcessDto, startDate, processProDecCache,
						workTaskCache, preCalculate);
				if (null != preFinishTime && preFinishTime.after(finishTime)) {
					finishTime = preFinishTime;
				}
			}
		}
		// 2、计算当前工序任务结束时间
		Date[] processTime = this.calculateProcess(processDto, processProDecCache, workTaskCache, finishTime, null);
		// 2.1、注：正向计算前面一道计算为空的话，可以继续，但前一道不为空(断层了)，这一道为空直接返回吧，没有继续计算的意义。
		if (!preCalculate.get("preCalculateNull") && null == processTime) {
			logger.debug("========工序{}正向计算遇到无意义的计算，返回空==========", processDto.getId());
			preCalculate.put("immediateBack", true); // tmp: 整个都不需要计算了，无意义
			return null;
		}
		if (null != processTime) {
			preCalculate.put("preCalculateNull", false); // tmp: 设置一下上一道计算结果不为空
		}
		if (null != processTime && processTime[1].after(finishTime)) { // 完成时间与上道完成时间对比：实际始终是after
			finishTime = processTime[1];
		}
		// 3、等于没计算，返回空
		if (finishTime.getTime() == startDate.getTime()) { // 等于没计算
			return null;
		}
		return finishTime;
	}

	/**
	 * 逆向计算订单产品的加工结束时间
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param processDto 工序树结构对象
	 * @param startDate 正向计算输入开始时间
	 * @param processCustomerOrderItemProDecCache
	 *            工序与工序对应的明细PRODEC（一个订单产品的，理论多个，实际情况只要一个）
	 * @param workTaskCache 设备占用任务缓存对象：最后需要保存
	 * @throws ParseException
	 * */
	private Date reverseCalculateEndTime(ProductProcessDto processDto, Date endDate,
			Map<String, List<CustomerOrderItemProDecOA>> processProDecCache, Map<String, WorkTask> workTaskCache)
			throws ParseException {
		// 计算当前/传入工序所需要的时间
		if (endDate.before(new Date())) {
			return null;
		}
		// 1、定义工序加工的结束时间、此道工序的开始结束时间、此道工序的结束时间
		Date finishTime = endDate;
		Date preProcessEndTime = endDate;

		// 2、计算当前工序任务结束时间
		Date[] processTime = this.calculateProcess(processDto, processProDecCache, workTaskCache, null, finishTime);
		if (null == processTime) { // 注：逆向计算此次一道计算为空的话，直接返回吧，没有继续算前面工序的意义了
			return null;
		}

		// 3、重新定义完成和开始时间
		preProcessEndTime = processTime[0]; // 上一道完成时间为本次工序的开始时间
		finishTime = processTime[1];

		// 4、core：先算自己，再递归计算上一道流程结束时间，达到逆向计算的效果
		List<ProductProcessDto> preProcessDtoArray = processDto.getPreProcesses();
		for (ProductProcessDto preProcessDto : preProcessDtoArray) {
			// 递归调用：获取上一道工序结束时间
			Date preFinishTime = this.reverseCalculateEndTime(preProcessDto, preProcessEndTime, processProDecCache,
					workTaskCache);
			if (null != preFinishTime && preFinishTime.after(finishTime)) {
				finishTime = preFinishTime;
			}
		}
		// 5、等于没计算，返回空
		if (finishTime.getTime() == endDate.getTime()) {
			return null;
		}
		return finishTime;
	}

	/**
	 * 计算并处理(核心)：计算一道工序所需要的时间
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param processDto 工序树结构对象
	 * @param processCustomerOrderItemProDecCache
	 *            工序与工序对应的明细PRODEC（一个订单产品的，理论多个，实际情况只要一个）
	 * @param workTaskCache 设备占用任务缓存对象：最后需要保存
	 * @param startTime 要求完成区间范围：开始时间，与endTime同时只存在一个
	 * @param endTime 要求完成区间范围：结束时间，与startTime同时只存在一个
	 * @return 返回该工序加工时间: [processStartTime, processEndTime]
	 * @throws ParseException
	 * */
	private Date[] calculateProcess(ProductProcessDto processDto,
			Map<String, List<CustomerOrderItemProDecOA>> processProDecCache, Map<String, WorkTask> workTaskCache,
			Date startTime, Date endTime) throws ParseException {
		// 0、定义工序加工开始时间、结束时间
		Date processStartTime = null, processEndTime = null;

		// 1、获取工序可用设备, 并排序设备
		List<EquipInfo> availableEquips = processDto.getAvailableEquips();
		// 1.1、使用负载均衡策略设置
		if (BusinessConstants.EQUIP_LOAD_BALANCE) {
			long start = new Date().getTime();
			this.sortEquipInfos(availableEquips, start, start + BusinessConstants.PRIORITY_USED_ECALENDAR_DAY_NUM * 24
					* 60 * 60 * 1000); // 排序设备,产能大优先：三天内
		}
		// 2、获取工序加工task
		List<CustomerOrderItemProDecOA> proDecArray = processProDecCache.get(processDto.getId());
		if (null == availableEquips || availableEquips.size() == 0 || null == proDecArray || proDecArray.size() == 0) {
			logger.debug("========工序{}无可用设备或者工序任务不存在==========", processDto.getId());
			return null;
		}

		// 3、循环PRODEC任务和设备：计算任务长度并添加到设备产能情况缓存列表
		for (CustomerOrderItemProDecOA proDec : proDecArray) {
			// 计算此道工序分解明细PRODEC所需要加工的时间
			long[] times = this.calculateSingleProdec(proDec, availableEquips, workTaskCache, startTime, endTime);
			// 3.1、给返回开始和结束时间赋值
			if (null != times) {
				if (null == processStartTime || processStartTime.after(new Date(times[0]))) {
					processStartTime = new Date(times[0]);
				}
				if (null == processEndTime || processEndTime.before(new Date(times[1]))) {
					processEndTime = new Date(times[1]);
				}
			} else {
				logger.debug("========工序{}在所有可用设备负载时间内不能完成该任务==========", processDto.getId());
				return null;
			}
		}

		// 4、返回
		if (null == processStartTime || null == processEndTime) {
			return null;
		}
		return new Date[] { processStartTime, processEndTime };
	}

	/**
	 * 计算并处理(核心2)：计算一道工序所需要的时间
	 * 
	 * @author DingXintao
	 * @date 2015-09-09
	 * @param proDec 订单工序分解的其中一个任务
	 * @param availableEquips 该任务对应工序的可选设备
	 * @param workTaskCache 设备占用任务缓存对象：最后需要保存
	 * @param startTime 要求完成区间范围：开始时间，与endTime同时只存在一个
	 * @param endTime 要求完成区间范围：结束时间，与startTime同时只存在一个
	 * @return 返回该任务加工时间: [processStartTime, processEndTime]
	 * @throws ParseException
	 * */
	private long[] calculateSingleProdec(CustomerOrderItemProDecOA proDec, List<EquipInfo> availableEquips,
			Map<String, WorkTask> workTaskCache, Date startTime, Date endTime) throws ParseException {
		boolean unUsedDilatation = true; // 是否使用了扩容：先3天，再15天
		int userDays = BusinessConstants.PRIORITY_USED_ECALENDAR_DAY_NUM;
		for (int i = 0; i < availableEquips.size(); i++) {
			EquipInfo equipInfo = availableEquips.get(i);
			// 3.1、获取该设备可用负载：list
			EquipCapacityLoad equipCapacityLoad = this.getEquipCapacityLoadByCode(equipInfo.getCode());
			if (null == equipCapacityLoad) {
				logger.debug("========工序{}可用设备{}在设备负载缓存中没有==========", proDec.getProcessId(), equipInfo.getCode());
				continue;
			}

			// 3.2、空闲时间可以完成最大任务长度 = (空闲时间-前置时间-后置时间)*设备能力
			BigDecimal equipCapacity = new BigDecimal(equipInfo.getEquipCapacity()); // 设备生产能力单位:米/秒
			BigDecimal setUpTime = new BigDecimal(equipInfo.getSetUpTime()); // 前置时间,单位:秒
			BigDecimal shutDownTime = new BigDecimal(equipInfo.getShutDownTime()); // 后置时间,单位:秒
			BigDecimal unFinishedLength = new BigDecimal(proDec.getUnFinishedLength()); // 未完成长度
			BigDecimal needTime = unFinishedLength.divide(equipCapacity, BigDecimal.ROUND_UP); // 需要加工时间
			BigDecimal totalTime = needTime.add(setUpTime).add(shutDownTime).multiply(new BigDecimal(1000)); // 总时间，毫秒乘以1000

			// 3.3、核心计算：计算并返回加工开始和结束时间
			Long start = new Date().getTime(); // 设备产能大区间：开始
			Long end = start + (long) (userDays * 24 * 60 * 60 * 1000); // 设备产能大区间：结束[先3天，再15天]
			long[] times = equipCapacityLoad.updateRange(totalTime.longValue(), startTime, endTime, start, end);
			// 3.4、当轮询到最后一台设备还没有计算出时候，将设备能力提高到15天计算，重新轮询计算
			if (null == times && i == (availableEquips.size() - 1) && unUsedDilatation) {
				i = 0; // 重新计算一遍
				userDays = BusinessConstants.ECALENDAR_DAY_NUM; // 能力区间天数放大至15
				unUsedDilatation = false;
				continue;
			}
			// 3.5、计算结果为空说明该设备负载不能完成
			if (null == times) {
				logger.debug("========工序{}下prodec任务{}在设备{}不能完成该任务,转下一个设备排产==========", proDec.getProcessId(),
						proDec.getId(), equipInfo.getCode());
				continue;
			}

			// 3.6、计算和将设备占用任务放入缓存列表
			this.addWorkTask2Cache(workTaskCache, equipInfo.getCode(), proDec.getId(), times);
			return times;
		}
		return null;
	}

	/**
	 * 封装设备占用对象到缓存：WorkTask
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param workTaskCache 更新的设备占用列表缓存
	 * @param equipCode 设备编码
	 * @param itemProDecId 订单分解工序明细任务ID
	 * @param times 计算后返回时间
	 * */
	private void addWorkTask2Cache(Map<String, WorkTask> workTaskCache, String equipCode, String itemProDecId,
			long[] times) {
		WorkTask workTask = new WorkTask();
		workTask.setEquipCode(equipCode);
		workTask.setWorkStartTime(new Date(times[0]));
		workTask.setWorkEndTime(new Date(times[1]));
		workTask.setFinishwork(false);
		workTask.setOrderItemProDecId(itemProDecId);
		workTask.setDescription("OA");
		workTaskCache.put(itemProDecId, workTask);
	}

	/**
	 * 计算完成后更新数据
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * 
	 * @param orderItem 订单产品
	 * @param finishedTime 完成时间：对应于订单产品的
	 * @param processProDecCache 工序:PRODEC缓存
	 * @param workTaskCache 任务占用产能
	 * @param orgCode 组织机构
	 * */
	private void finishedCalculator(CustomerOrderItem orderItem, Date finishedTime,
			Map<String, List<CustomerOrderItemProDecOA>> processProDecCache, Map<String, WorkTask> workTaskCache,
			String orgCode) {
		// 0、删除物料需求
		CustomerOrder order = orderItem.getCustomerOrder();
		oaMrpService.deleteByContractNoOrderItemIdOrgCode(order.getContractNo(), orderItem.getId(), order.getOrgCode());

		logger.debug("========WORK TASK 信息============");
		Date orderItemStartDate = null;
		// 1、更新PRODEC，保存workTask
		Iterator<String> keys = processProDecCache.keySet().iterator();
		while (keys.hasNext()) {
			List<CustomerOrderItemProDecOA> itemProDecArray = processProDecCache.get(keys.next());
			for (CustomerOrderItemProDecOA itemProDec : itemProDecArray) {
				WorkTask workTask = workTaskCache.get(itemProDec.getId());
				if (null == workTask) {
					logger.debug("========ProDec:{}任务没有workTask============", itemProDec.getId());
					continue;
				}
				// itemProDec.setLatestStartDate(workTask.getWorkStartTime());
				// itemProDec.setLatestFinishDate(workTask.getWorkEndTime());
				// itemProDec.setEquipCode(workTask.getEquipCode());
				// customerOrderItemProDecOAService.update(itemProDec);
				workTask.setOrgCode(order.getOrgCode());
				workTaskService.insert(workTask);

				if (orderItemStartDate == null) {
					orderItemStartDate = workTask.getWorkStartTime();
				} else if (orderItemStartDate.after(workTask.getWorkStartTime())) {
					orderItemStartDate = workTask.getWorkStartTime();
				}
				// 添加物料需求 ？？？ 需要修改 getUnFinishedLength
				List<ProcessInOutWip> processInArray = this.getProcessIn(itemProDec.getProcessId());
				for (ProcessInOutWip processIn : processInArray) {
					OaMrp mrp = new OaMrp();
					mrp.setContractNo(itemProDec.getContractNo());
					mrp.setMatCode(processIn.getMatCode());
					mrp.setProcessCode(itemProDec.getProcessCode());
					mrp.setOrderItemProDecId(itemProDec.getId());
					mrp.setOrderItemId(orderItem.getId());
					mrp.setProductCode(orderItem.getProductCode());
					mrp.setPlanDate(workTask.getWorkStartTime());
					mrp.setEquipCode(workTask.getEquipCode());
					mrp.setOrgCode(order.getOrgCode());
					mrp.setStatus(MaterialStatus.UNAUDITED);
					mrp.setUnit(processIn.getUnit());
					if (null != processIn.getQuantity()) {
						mrp.setQuantity(itemProDec.getUnFinishedLength() * processIn.getQuantity());
					} else {
						mrp.setQuantity(itemProDec.getUnFinishedLength());
					}
					oaMrpService.insert(mrp);
				}
			}
		}
		// 2、更新订单产品表
		if (orderItem.getPlanFinishDate() != null) { // 更新上次计算OA时间
			orderItem.setLastOa(orderItem.getPlanFinishDate());
		}
		// 处于加工中的订单产品，开始时间已经固定，不能变，空除外
		if (null == orderItem.getPlanStartDate() || !ProductOrderStatus.IN_PROGRESS.equals(orderItem.getStatus())) {
			orderItem.setPlanStartDate(orderItemStartDate);
		}
		orderItem.setPlanFinishDate(finishedTime);

		this.insertOaUptemp("T_PLA_CUSTOMER_ORDER_ITEM", orderItem.getId(), orderItem.getPlanStartDate(),
				orderItem.getPlanFinishDate(), orderItem.getLastOa(), orgCode);
		// customerOrderItemService.update(orderItem);
	}

	/**
	 * 结束计算更新customerOrder，超交期产生事件
	 * */
	private void finishedCalculatorOA(String orgCode) {
		Map<String, CustomerOrder> orderMap = customerOrderCacheMap.get();
		Iterator<String> keys = orderMap.keySet().iterator();
		while (keys.hasNext()) {
			CustomerOrder order = orderMap.get(keys.next());
			if (order.getPlanFinishDate() != null) {
				order.setLastOa(order.getPlanFinishDate());
			}
			order.setPlanFinishDate(orderPlanFinishedTimeCacheMap.get(order.getId()));
			// 超交期预警
			if (order.getCustomerOaDate() != null && order.getPlanFinishDate() != null
					&& order.getCustomerOaDate().before(order.getPlanFinishDate())) {
				EventInformation eventInformation = new EventInformation();
				eventInformation.setCode(EventTypeContent.OT.name());
				eventInformation.setEventTitle("合同超交期");
				eventInformation.setEventContent("合同号:" + order.getContractNo() + ",超指定交期");
				eventInformation.setEventStatus(EventStatus.UNCOMPLETED);
				eventInformationService.insertInfo(eventInformation);
			}
			this.insertOaUptemp("T_PLA_CUSTOMER_ORDER", order.getId(), order.getPlanStartDate(),
					order.getPlanFinishDate(), order.getLastOa(), orgCode);
			// customerOrderService.update(order);
		}

		// 调用存储过程更新订单的OA时间
		oaUptempService.callUpdateOrderOaTime(orgCode);
	}

	/**
	 * 缓存中获取订单产品
	 * 
	 * @author DingXintao
	 * @date 2015-08-28
	 * @param orderItem 订单产品
	 * */
	private CustomerOrder getCustomerOrder(CustomerOrderItem orderItem) {
		CustomerOrder order = customerOrderCacheMap.get(orderItem.getCustomerOrderId());
		if (order == null) {
			order = customerOrderService.getById(orderItem.getCustomerOrderId());
			customerOrderCacheMap.put(orderItem.getCustomerOrderId(), order);
		}
		return order;
	}

	/**
	 * 根据设备编码获取设备负载：先从缓存中获取，再查询
	 * 
	 * @param
	 * @throws ParseException
	 * */
	private EquipCapacityLoad getEquipCapacityLoadByCode(String equipCode) throws ParseException {
		EquipCapacityLoad equipCapacityLoad = equipCapacityLoadCacheMap.get(equipCode);
		if (null == equipCapacityLoad) {
			EquipInfo equipInfo = equipInfoService.getEquipLineByEquip(equipCode);
			// 1、 故障 或 维修 直接返回，不参与计算
			if (null == equipInfo || EquipStatus.ERROR == equipInfo.getStatus() || EquipStatus.IN_MAINTAIN == equipInfo.getStatus()) {
				return null;
			}
			// 2、获取设备日历
			List<EquipCalendar> equipCalendar = equipCalendarService.getEquipCalendarByCode(equipInfo.getCode(), null,
					null); // 设备工作日历(get方法默认加载3个月)
			// 3、查询存在的固定的设备负载: 条件为 没有加工完成
			List<WorkTask> workTaskArray = workTaskService.getByEquipCode(equipInfo.getCode());
			// 4、创建设备负载
			equipCapacityLoad = new EquipCapacityLoad(equipInfo, equipCalendar, workTaskArray);
			// 5、放入缓存
			equipCapacityLoadCacheMap.put(equipCode, equipCapacityLoad);
		}
		return equipCapacityLoad;
	}

	/**
	 * 还原WorkTask到缓存，因为没保存，将用过的还回去
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param workTaskCache 计算的设备占用任务缓存
	 */
	public void back2EquipCapacity(Map<String, WorkTask> workTaskCache) {
		Iterator<String> keys = workTaskCache.keySet().iterator();
		while (keys.hasNext()) {
			WorkTask workTask = workTaskCache.get(keys.next());
			EquipCapacityLoad equipCapacityLoad = equipCapacityLoadCacheMap.get(workTask.getEquipCode());
			equipCapacityLoad.addRange(workTask);
		}
	}

	/**
	 * 验证订单是否合法：即工艺是否存在，存在放入缓存
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param craftsId 工艺ID
	 * */
	private boolean validateOrder(String craftsId) {
		if (StringUtils.isEmpty(craftsId)) {
			return false;
		}
		// 查询工艺
		if (null != craftsCacheMap.get(craftsId)) {
			return true;
		}

		// 修改直接查看工艺是否合法，调用数据库函数，提升效率
		// ProductCrafts productCrafts = productCraftsService.getById(craftsId);
		// if (null == productCrafts) {
		// return false;
		// }
		// 1:合法, 0:不合法
		String isPass = productCraftsService.validateProductCrafts(craftsId);
		if (StringUtils.isNotEmpty(isPass)) {
			return false;
		}

		craftsCacheMap.put(craftsId, craftsId);
		return true;
	}

	/**
	 * 获取订单合同信息：缓存中取，不存在直接取，并放缓存
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param craftsId 工艺ID
	 * */
	private ProductProcessDto getProductProcess(String craftsId) {
		ProductProcessDto processDto = processDtoCacheMap.get(craftsId);
		if (null == processDto) {
			processDto = new ProductProcessDto();
			processDto.setProductCraftsId(craftsId);
			// 调用递归方法
			this.recurseInitProcessDto(processDto);
			processDtoCacheMap.put(craftsId, processDto);
		}
		return processDto;
	}

	/**
	 * 获取工序的产出信息：缓存中取，不存在直接取，并放缓存
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param processId 工序ID
	 * */
	private ProcessInOutWip getProcessOut(String processId) {
		ProcessInOutWip processInOut = outMatCacheMap.get(processId);
		if (null == processInOut) {
			processInOut = new ProcessInOutWip();
			processInOut.setProductProcessId(processId);
			processInOut.setInOrOut(InOrOut.OUT);
			List<ProcessInOutWip> processInOutArray = processInOutWipService.findByObj(processInOut);
			if (processInOutArray.size() > 0) {
				processInOut = processInOutArray.get(0);
				outMatCacheMap.put(processId, processInOut);
			}
		}
		return processInOut;
	}

	/**
	 * 获取工序的投出指定物料信息：缓存中取，不存在直接取，并放缓存
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param processId 工序ID
	 * @param matCode 投入物料编码
	 * */
	private List<ProcessInOutWip> getProcessIn(String processId) {
		List<ProcessInOutWip> processInArray = inMatCacheMap.get(processId);
		if (null == processInArray) {
			ProcessInOutWip processInOut = new ProcessInOutWip();
			processInOut.setProductProcessId(processId);
			processInOut.setInOrOut(InOrOut.IN);
			processInArray = processInOutWipService.findByObj(processInOut);
			inMatCacheMap.put(processId, processInArray);
		}
		return processInArray;
	}

	/**
	 * 递归初始化工序流程：转换成树结构
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param processDto 工序DTO对象
	 * */
	private void recurseInitProcessDto(ProductProcessDto processDto) {
		// 0、判断最后一道工序：处理最后一道工序
		if (StringUtils.isEmpty(processDto.getId())) {
			// 最后一道工序
			ProductProcessWip findParams = new ProductProcessWip();
			findParams.setProductCraftsId(processDto.getProductCraftsId());
			findParams.setNextProcessId("-1");
			List<ProductProcessWip> processArray = productProcessWipService.findByObj(findParams);
			if (null == processArray || processArray.size() == 0) {
				logger.debug("========工序存在问题，id：{}============", processDto.getProductCraftsId());
				return;
			}
			BeanUtils.copyProperties(processArray.get(0), processDto); // bean拷贝
			processDto.setAvailableEquips(this.getAvailableEquips(processDto.getId()));
			// 调用递归
			this.recurseInitProcessDto(processDto);
			return; // 最后一道直接返回的
		}

		// 1、查询出上一道工序列表
		ProductProcessWip findParams = new ProductProcessWip();
		findParams.setProductCraftsId(processDto.getProductCraftsId());
		findParams.setNextProcessId(processDto.getId());
		List<ProductProcessWip> preProcessArray = productProcessWipService.findByObj(findParams);
		// 递归结束标识：上一道工序列表为空
		if (null == preProcessArray || preProcessArray.size() == 0) {
			return;
		}

		// 2、新建上一道工序列表DTO
		List<ProductProcessDto> preProcessDtoArray = new ArrayList<ProductProcessDto>();
		// 3、循环递归获取上一道工序列表
		for (ProductProcessWip preProcess : preProcessArray) {
			// 3.1、创建上一道工序DTO
			ProductProcessDto preProcessDto = new ProductProcessDto();
			BeanUtils.copyProperties(preProcess, preProcessDto); // bean拷贝
			preProcessDto.setNextProcess(processDto);
			preProcessDto.setAvailableEquips(this.getAvailableEquips(preProcessDto.getId()));
			// 3.2、调用递归
			this.recurseInitProcessDto(preProcessDto);
			// 3.3、保存工序到上一道工序列表
			preProcessDtoArray.add(preProcessDto);
		}
		processDto.setPreProcesses(preProcessDtoArray);
	}

	/**
	 * 获取工序的可用设备：包含设备能力
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param processId 工序ID
	 * @param comparator if 0 use equipWorkLoadComparator else use
	 *            equipOrderTaskLoadComparator
	 * */
	private List<EquipInfo> getAvailableEquips(String processId) {
		List<EquipInfo> equipInfos = equipInfoService.getByWipProcessId(processId);
		return equipInfos;
	}

	/**
	 * 对工序下的可用设备排序，产能大的放前面优先选择
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param equipInfoArray 工序下设备列表
	 * @param comparator if 0 use equipWorkLoadComparator else use
	 *            equipOrderTaskLoadComparator
	 * */
	private void sortEquipInfos(List<EquipInfo> equipInfoArray, final long start, final long end) {
		Collections.sort(equipInfoArray, new Comparator<EquipInfo>() {
			@Override
			public int compare(EquipInfo e1, EquipInfo e2) {
				EquipCapacityLoad e1Load = null;
				EquipCapacityLoad e2Load = null;
				try {
					e1Load = getEquipCapacityLoadByCode(e1.getCode());
					e2Load = getEquipCapacityLoadByCode(e2.getCode());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (null == e1Load || null == e2Load) {
					return 0;
				}
				if (e1Load.getLong(start, end) < e2Load.getLong(start, end)) {
					return 1;
				} else if (e1Load.getLong(start, end) > e2Load.getLong(start, end)) {
					return -1;
				} else {
					return 0;
				}
			}
		});
	}

	/**
	 * 获取系统参数：T_BAS_SYSTEM_PARAM_CONFIG
	 * 
	 * @param param 参数名
	 * @param orgCode 组织编码
	 * */
	private int getDaysByParam(String param, String orgCode) {
		return WebContextUtils.getSysParamIntValue(param, orgCode);
	}

	/**
	 * 线程安全缓存MAP类
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * */
	private class CacheMapThreadLocal<T> extends ThreadLocal<Map<String, T>> {
		public CacheMapThreadLocal() {
			super();
		}

		@Override
		protected Map<String, T> initialValue() {
			return new HashMap<String, T>();
		}

		public T put(String key, T value) {
			Map<String, T> map = this.get();
			return map.put(key, value);
		}

		public T get(String key) {
			Map<String, T> map = this.get();
			return map.get(key);
		}
	}

	/**
	 * 更新数据的中间临时表
	 * 
	 * @param tableName 更新表名
	 * @param tableUid 更新表ID
	 * @param planStartDate 计划开工日期
	 * @param planFinishDate 计划完成日期
	 * @param lastOa 上次计算日期
	 * @param orgCode 组织机构
	 * */
	private void insertOaUptemp(String tableName, String tableUid, Date planStartDate, Date planFinishDate,
			Date lastOa, String orgCode) {
		OaUptemp oaUptemp = new OaUptemp();
		oaUptemp.setTableName(tableName);
		oaUptemp.setTableUid(tableUid);
		oaUptemp.setPlanStartDate(planStartDate);
		oaUptemp.setPlanFinishDate(planFinishDate);
		oaUptemp.setLastOa(lastOa);
		oaUptemp.setOrgCode(orgCode);
		oaUptempService.insert(oaUptemp);
	}
}
