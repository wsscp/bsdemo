package cc.oit.bsmes.pla.action;

import cc.oit.bsmes.bas.model.WorkShift;
import cc.oit.bsmes.bas.service.WorkShiftService;
import cc.oit.bsmes.common.constants.JobConstants;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.model.EventStore;
import cc.oit.bsmes.common.model.ResourcesStore;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.job.base.factory.InitSchedulerFactoryBean;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.OrderOAService;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.service.EquipListService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单OA结果查看
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-3-25 下午3:51:32
 */
@Controller
@RequestMapping("/pla/orderOA")
public class OrderOAController {

	private static int MAX_INTERVAL_WEEK = 15;
	@Resource
	private OrderOAService orderOAService;
	@Resource
	private EquipListService equipListService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private WorkShiftService workShiftService;

	private static int DEFAULT_ADD_DAY = 7;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "pla");
		model.addAttribute("submoduleName", "orderOA");
		return "pla.orderOA";
	}

	/**
	 * OA查看主列表：订单产品
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping
	@ResponseBody
	public TableView list(HttpServletRequest request, @RequestParam(required = false) String sort, @RequestParam int page,
			@RequestParam int start, @RequestParam int limit) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		Map<String, String[]> parameterMap = request.getParameterMap(); // 把请求参数放入map对象
		Iterator<String> paramKeys = parameterMap.keySet().iterator();
		while (paramKeys.hasNext()) {
			String paramKey = paramKeys.next();
			String[] param = parameterMap.get(paramKey);
			if (null != param && param.length > 0 && StringUtils.isNotBlank(param[0])) {
				// 特殊参数用字符串，其他用数组，sql处有处理
				if ("planDate".equals(paramKey) || "to".equals(paramKey)) {
					findParams.put(paramKey, param[0]);
				} else {
					findParams.put(paramKey, new ArrayList<String>(Arrays.asList(param)));
				}
			}
		}
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		this.changeSortProperty2Column(sortArray);
		findParams.put("start", start); // 顺序不能变，必须要覆盖
		findParams.put("end", (start + limit));
		findParams.put("sort", sortArray);
		findParams.put("orgCode", orderOAService.getOrgCode());

		List<OrderOA> list = orderOAService.list(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(orderOAService.count(findParams));
		return tableView;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		Map<String, Object> param = new HashMap<String, Object>();
		UpdateResult updateResult = new UpdateResult();
		OrderOA orderOA = JSON.parseObject(jsonText, OrderOA.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(orderOA.getOaDate());
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		param.put("id", orderOA.getId());
		param.put("date", calendar.getTime());
		orderOAService.setOaDate(param);
		updateResult.addResult(orderOA);
		return updateResult;
	}

	/**
	 * OA查看子列表：工序分解明细
	 * */
	@ResponseBody
	@RequestMapping(value = "/orderProcess/{orderItemId}", method = RequestMethod.GET)
	public List<OrderOA> findSubResult(@PathVariable String orderItemId) {
		List<OrderOA> list = orderOAService.getSubListByOrderItemId(orderItemId);
		return list;
	}

	/**
	 * 将排序的字段自动转换为数据库字段 适用于自定义分页
	 * */
	private void changeSortProperty2Column(List<Sort> sortArray) {
		if (null != sortArray) {
			for (Sort sort : sortArray) {
				sort.setProperty(sort.getProperty().replaceAll("([A-Z])", "_$0").toUpperCase());
			}
		}
	}

	/**
	 * 查看OA -> 重新计算
	 * */
	@ResponseBody
	@RequestMapping(value = "/calculateOA", method = RequestMethod.POST)
	public JSONObject calculateOA() throws InvocationTargetException, IllegalAccessException {
		InitSchedulerFactoryBean initSchedulerFactoryBean = (InitSchedulerFactoryBean) ContextUtils.getBean(InitSchedulerFactoryBean.class);
		try {
			initSchedulerFactoryBean.startJob(JobConstants.OA_SCHEDULE);
		} catch (Exception e) {
			throw new MESException(e);
		}
		JSONObject result = new JSONObject();
		result.put("succcess", true);
		return result;
	}

	/**
	 * 资源甘特图：左侧树
	 * */
	@ResponseBody
	@RequestMapping(value = "/getResource", method = RequestMethod.GET)
	public List<ResourcesStore> getResource(@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate)
			throws ParseException {
		OrderOA orderOA = new OrderOA();
		SimpleDateFormat fd = new SimpleDateFormat(DateUtils.DATE_FORMAT);
		if (StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate)) {
			orderOA.setPlanDate(new Date());
			orderOA.setTo(new Date(new Date().getTime() + DEFAULT_ADD_DAY * 24 * 60 * 60 * 1000)); // 默认七天
		} else {
			orderOA.setPlanDate(fd.parse(fromDate));
			orderOA.setTo(DateUtils.addDayToDate(fd.parse(toDate), 1));
		}

		// 获取后台数据
		Multimap<String, OrderOA> workOrderTaskMultimap = orderOAService.getContractNo(orderOA);
		// 处理后台数据：转换成三层结构[合同号 -> 产品 -> 工序/设备]
		List<ResourcesStore> resourcesStore = this.change2ResourceTree(workOrderTaskMultimap);
		return resourcesStore;
	}

	@ResponseBody
	@RequestMapping(value = "/zone", method = RequestMethod.GET)
	public List<WorkShift> zone() throws ParseException {
		Date start = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		cal.add(Calendar.WEEK_OF_MONTH, MAX_INTERVAL_WEEK);
		Date end = cal.getTime();
		return workShiftService.getWorkShifByStartAndEnd(start, end, SessionUtils.getUser().getOrgCode());
	}

	/**
	 * 资源甘特图：右侧数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/getEvent", method = RequestMethod.GET)
	public List<EventStore> getEvent(@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate)
			throws ParseException {
		OrderOA orderOA = new OrderOA();
		SimpleDateFormat fd = new SimpleDateFormat(DateUtils.DATE_FORMAT);
		if (StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate)) {
			orderOA.setPlanDate(new Date());
			orderOA.setTo(new Date(new Date().getTime() + DEFAULT_ADD_DAY * 24 * 60 * 60 * 1000)); // 默认七天
		} else {
			orderOA.setPlanDate(fd.parse(fromDate));
			orderOA.setTo(DateUtils.addDayToDate(fd.parse(toDate), 1));
		}

		// 获取后台数据
		Multimap<String, OrderOA> workOrderTaskMultimap = orderOAService.getContractNo(orderOA);
		// 处理后台数据：转换成三层结构[合同号 -> 产品 -> 工序/设备]
		List<EventStore> eventStore = this.change2EventTree(workOrderTaskMultimap);
		return eventStore;
	}

	@ResponseBody
	@RequestMapping(value = "findIdleEquip/{processId}", method = RequestMethod.GET)
	public List<EquipList> findIdleEquip(@PathVariable String processId) {
		return equipListService.getIdleEquip(processId);
	}

	@ResponseBody
	@RequestMapping(value = "updateProDecEquipInfo", method = RequestMethod.POST)
	public int updateProDecEquipInfo(@RequestParam String equipCodes, @RequestParam String orderItemId, @RequestParam String processId) {
		return customerOrderItemProDecService.updateOptionalEquipCode(equipCodes, orderItemId, processId);
	}

	@ResponseBody
	@RequestMapping(value = "/sectionOrStructure/{combType}", method = RequestMethod.GET)
	public List<OrderOA> findSectionOrStructure(@PathVariable String combType) {
		List<OrderOA> list = orderOAService.getSectionOrStructure();
		if (list != null) {
			list = getResultList(list, combType);
		}
		return list;
	}

	private List<OrderOA> getResultList(List<OrderOA> list, String combType) {
		List<OrderOA> results = new ArrayList<OrderOA>();
		Set<Object> set = new HashSet<Object>();
		for (OrderOA order : list) {
			if (combType.equals("1") && order.getSection() != null) {
				set.add(order.getSection());
			} else {
				if (StringUtils.isNotBlank(order.getWiresStructure())) {
					set.add(order.getWiresStructure());
				}
			}
		}
		Iterator<Object> it = set.iterator();
		while (it.hasNext()) {
			OrderOA o = new OrderOA();
			if (combType.equals("1")) {
				o.setSection(it.next().toString());
			} else {
				o.setWiresStructure(it.next().toString());
			}
			results.add(o);
		}
		return results;
	}

	public Multimap<String, OrderOA> getProductMultimap(Collection<OrderOA> taskArray) {
		// 分组
		Multimap<String, OrderOA> taskMultimap = ArrayListMultimap.create();
		for (OrderOA task : taskArray) {
			taskMultimap.put(task.getProductType() + "-" + task.getProductSpec(), task);
		}
		return taskMultimap;
	}

	public Multimap<String, OrderOA> getProcessMultimap(Collection<OrderOA> taskArray) {
		// 分组
		Multimap<String, OrderOA> taskMultimap = ArrayListMultimap.create();
		for (OrderOA task : taskArray) {
			taskMultimap.put(task.getProcessName() + "&" + task.getProcessCode(), task);
		}
		return taskMultimap;
	}

	public Multimap<String, OrderOA> getEquipMultimap(Collection<OrderOA> taskArray) {
		// 分组
		Multimap<String, OrderOA> taskMultimap = ArrayListMultimap.create();
		for (OrderOA task : taskArray) {
			taskMultimap.put(task.getEquipName(), task);
		}
		return taskMultimap;
	}

	/**
	 * 将数据转换为整体结构：合同号 -> 产品 -> 工序 -> 设备
	 * */
	private List<ResourcesStore> change2ResourceTree(Multimap<String, OrderOA> workOrderTaskMultimap) {
		List<ResourcesStore> cnResourcesArray = new ArrayList<ResourcesStore>(); // 合同树
		// 1、循环查询后的数据
		Iterator<String> cnKeys = workOrderTaskMultimap.keySet().iterator();
		while (cnKeys.hasNext()) {
			String contractNo = (String) cnKeys.next();
			Collection<OrderOA> cnGroupArray = workOrderTaskMultimap.get(contractNo); // 合同号分组的对象

			// 1.1合同层级资源对象
			Date cstart = null;
			Date cend = null;
			Double cfinishLen = 0d;
			Double cunfinishLen = 0d;
			ResourcesStore cnResources = new ResourcesStore();
			cnResources.setId(contractNo);
			cnResources.setName(contractNo);
			cnResources.setExpanded(true);

			// 2、合同号下面的产品分组
			List<ResourcesStore> prodResourcesArray = new ArrayList<ResourcesStore>(); // 产品树
			Multimap<String, OrderOA> productMultimap = this.getProductMultimap(cnGroupArray);
			Iterator<String> prodKeys = productMultimap.keySet().iterator();
			while (prodKeys.hasNext()) {
				String productCode = (String) prodKeys.next();
				Collection<OrderOA> prodGroupArray = productMultimap.get(productCode); // 合同号分组的对象

				// 2.1、产品层级资源对象
				Date prstart = null;
				Date prend = null;
				Double prfinishLen = 0d;
				Double prunfinishLen = 0d;
				ResourcesStore prodResources = new ResourcesStore();
				prodResources.setId(contractNo + productCode);
				prodResources.setName(productCode);
				prodResources.setExpanded(true);

				// 3、产品下的工序分组
				List<ResourcesStore> processResourcesArray = new ArrayList<ResourcesStore>(); // 产品树
				Multimap<String, OrderOA> processMultimap = this.getProcessMultimap(prodGroupArray);
				Iterator<String> processKeys = processMultimap.keySet().iterator();
				while (processKeys.hasNext()) {
					String processCode = (String) processKeys.next();
					Collection<OrderOA> processGroupArray = processMultimap.get(processCode); // 合同号分组的对象

					// 3.1、工序层级资源对象
					Date pstart = null;
					Date pend = null;
					Double pfinishLen = 0d;
					Double punfinishLen = 0d;
					ResourcesStore processResources = new ResourcesStore();
					processResources.setId(contractNo + productCode + processCode);
					processResources.setName(processCode.split("&")[0]);
					processResources.setExpanded(true);

					// 4、工序下的设备分组
					List<ResourcesStore> equipResourcesArray = new ArrayList<ResourcesStore>(); // 产品树
					Multimap<String, OrderOA> equipMultimap = this.getEquipMultimap(processGroupArray);
					Iterator<String> equipKeys = equipMultimap.keySet().iterator();
					while (equipKeys.hasNext()) {
						String equipName = (String) equipKeys.next();
						Collection<OrderOA> equipGroupArray = equipMultimap.get(equipName); // 合同号分组的对象

						// 4.1、设备层级资源对象
						Date start = null;
						Date end = null;
						Double finishLen = 0d;
						Double unfinishLen = 0d;
						String outPut = "";
						ResourcesStore equipResources = new ResourcesStore();
						equipResources.setId(contractNo + productCode + processCode + equipName);
						equipResources.setName(equipName);
						equipResources.setLeaf(true);

						for (OrderOA prodec : equipGroupArray) {
							if (start == null || start.after(prodec.getLatestStartDate())) {
								start = prodec.getLatestStartDate();
							}
							if (end == null || end.before(prodec.getLatestFinishDate())) {
								end = prodec.getLatestFinishDate();
							}
							finishLen += prodec.getFinishedLength();
							unfinishLen += prodec.getUnFinishedLength();

							outPut = prodec.getMatName();
						}

						equipResources.setStartDate(start);
						equipResources.setEndDate(end);
						equipResources.setOutPut(outPut);
						equipResources.setUnfinishedLength(unfinishLen);
						equipResources.setFinishedLength(finishLen);
						equipResources.setPercentDone(String.format("%.0f", (finishLen / (finishLen + unfinishLen) * 100)));
						equipResourcesArray.add(equipResources);

						if (pstart == null || pstart.after(start)) {
							pstart = start;
						}
						if (pend == null || pend.before(end)) {
							pend = end;
						}
						pfinishLen += finishLen;
						punfinishLen += unfinishLen;
					}

					processResources.setProcessCode(processCode.split("&")[1]);
					processResources.setUnfinishedLength(punfinishLen);
					processResources.setFinishedLength(pfinishLen);
					processResources.setUsedStockLength(0d);
					processResources.setStartDate(pstart);
					processResources.setEndDate(pend);
					processResources.setPercentDone(String.format("%.0f", (pfinishLen / (pfinishLen + punfinishLen) * 100)));
					processResources.setChildren(equipResourcesArray);
					processResourcesArray.add(processResources);

					if (prstart == null || prstart.after(pstart)) {
						prstart = pstart;
					}
					if (prend == null || prend.before(pend)) {
						prend = pend;
					}
					prfinishLen += pfinishLen;
					prunfinishLen += punfinishLen;
				}

				prodResources.setStartDate(prstart);
				prodResources.setEndDate(prend);
				prodResources.setPercentDone(String.format("%.0f", (prfinishLen / (prfinishLen + prunfinishLen) * 100)));
				prodResources.setChildren(processResourcesArray);
				prodResourcesArray.add(prodResources);

				if (cstart == null || cstart.after(prstart)) {
					cstart = prstart;
				}
				if (cend == null || cend.before(prend)) {
					cend = prend;
				}
				cfinishLen += prfinishLen;
				cunfinishLen += prunfinishLen;
			}

			cnResources.setStartDate(cstart);
			cnResources.setEndDate(cend);
			cnResources.setPercentDone(String.format("%.0f", (cfinishLen / (cfinishLen + cunfinishLen) * 100)));
			cnResources.setChildren(prodResourcesArray); // 产品子对象
			cnResourcesArray.add(cnResources);
		}

		return cnResourcesArray;
	}

	private List<EventStore> change2EventTree(Multimap<String, OrderOA> workOrderTaskMultimap) {
		List<EventStore> eventArray = new ArrayList<EventStore>(); // 合同树
		// 1、循环查询后的数据
		Iterator<String> cnKeys = workOrderTaskMultimap.keySet().iterator();
		while (cnKeys.hasNext()) {
			String contractNo = (String) cnKeys.next();
			Collection<OrderOA> cnGroupArray = workOrderTaskMultimap.get(contractNo); // 合同号分组的对象

			// 1.1合同层级资源对象
			Date cstart = null;
			Date cend = null;
			Double cfinishLen = 0d;
			Double cunfinishLen = 0d;
			EventStore cnEvent = new EventStore();
			cnEvent.setResourceId(contractNo);
			cnEvent.setName(contractNo);
			cnEvent.setLevel(0);

			// 2、合同号下面的产品分组
			Multimap<String, OrderOA> productMultimap = this.getProductMultimap(cnGroupArray);
			Iterator<String> prodKeys = productMultimap.keySet().iterator();
			while (prodKeys.hasNext()) {
				String productCode = (String) prodKeys.next();
				Collection<OrderOA> prodGroupArray = productMultimap.get(productCode); // 合同号分组的对象

				// 2.1、产品层级资源对象
				Date prstart = null;
				Date prend = null;
				Double prfinishLen = 0d;
				Double prunfinishLen = 0d;
				EventStore prodEvent = new EventStore();
				prodEvent.setResourceId(contractNo + productCode);
				prodEvent.setName(productCode);
				prodEvent.setLevel(1);

				// 3、产品下的工序分组
				Multimap<String, OrderOA> processMultimap = this.getProcessMultimap(prodGroupArray);
				Iterator<String> processKeys = processMultimap.keySet().iterator();
				while (processKeys.hasNext()) {
					String processCode = (String) processKeys.next();
					Collection<OrderOA> processGroupArray = processMultimap.get(processCode); // 合同号分组的对象

					// 3.1、工序层级资源对象
					Date pstart = null;
					Date pend = null;
					Double pfinishLen = 0d;
					Double punfinishLen = 0d;
					String poutPut = "";
					EventStore processEvent = new EventStore();
					processEvent.setResourceId(contractNo + productCode + processCode);
					processEvent.setName(processCode);
					processEvent.setLevel(2);

					// 4、工序下的设备分组
					Multimap<String, OrderOA> equipMultimap = this.getEquipMultimap(processGroupArray);
					Iterator<String> equipKeys = equipMultimap.keySet().iterator();
					while (equipKeys.hasNext()) {
						String equipCode = (String) equipKeys.next();
						Collection<OrderOA> equipGroupArray = equipMultimap.get(equipCode); // 合同号分组的对象

						// 4.1、设备层级资源对象
						Date start = null;
						Date end = null;
						Double finishLen = 0d;
						Double unfinishLen = 0d;
						String outPut = "";
						String halfProductCode = "";
						EventStore equipEvent = new EventStore();
						equipEvent.setResourceId(contractNo + productCode + processCode + equipCode);
						equipEvent.setName(equipCode);
						equipEvent.setLevel(3);

						for (OrderOA prodec : equipGroupArray) {
							if (start == null || start.after(prodec.getLatestStartDate())) {
								start = prodec.getLatestStartDate();
							}
							if (end == null || end.before(prodec.getLatestFinishDate())) {
								end = prodec.getLatestFinishDate();
							}
							finishLen += prodec.getFinishedLength();
							unfinishLen += prodec.getUnFinishedLength();
							outPut = poutPut = prodec.getMatName();
							halfProductCode = prodec.getHalfProductCode();
						}

						equipEvent.setStartDate(DateUtils.convert(start));
						equipEvent.setEndDate(DateUtils.convert(end));
						equipEvent.setOutPut(outPut);
						equipEvent.setHalfProductCode(halfProductCode);
						equipEvent.setUnfinishedLength(unfinishLen);
						equipEvent.setFinishedLength(finishLen);
						equipEvent.setPercentDone(String.format("%.0f", (finishLen / (finishLen + unfinishLen) * 100)));
						eventArray.add(equipEvent);

						if (pstart == null || pstart.after(start)) {
							pstart = start;
						}
						if (pend == null || pend.before(end)) {
							pend = end;
						}
						pfinishLen += finishLen;
						punfinishLen += unfinishLen;
					}

					processEvent.setUnfinishedLength(punfinishLen);
					processEvent.setFinishedLength(pfinishLen);
					processEvent.setUsedStockLength(0d);
					processEvent.setStartDate(DateUtils.convert(pstart));
					processEvent.setEndDate(DateUtils.convert(pend));
					processEvent.setOutPut(poutPut);
					processEvent.setPercentDone(String.format("%.0f", (pfinishLen / (pfinishLen + punfinishLen) * 100)));
					eventArray.add(processEvent);

					if (prstart == null || prstart.after(pstart)) {
						prstart = pstart;
					}
					if (prend == null || prend.before(pend)) {
						prend = pend;
					}
					prfinishLen += pfinishLen;
					prunfinishLen += punfinishLen;
				}

				prodEvent.setStartDate(DateUtils.convert(prstart));
				prodEvent.setEndDate(DateUtils.convert(prend));
				prodEvent.setOutPut(productCode);
				prodEvent.setPercentDone(String.format("%.0f", (prfinishLen / (prfinishLen + prunfinishLen) * 100)));
				eventArray.add(prodEvent);

				if (cstart == null || cstart.after(prstart)) {
					cstart = prstart;
				}
				if (cend == null || cend.before(prend)) {
					cend = prend;
				}
				cfinishLen += prfinishLen;
				cunfinishLen += prunfinishLen;
			}

			cnEvent.setStartDate(DateUtils.convert(cstart));
			cnEvent.setEndDate(DateUtils.convert(cend));
			cnEvent.setPercentDone(String.format("%.0f", (cfinishLen / (cfinishLen + cunfinishLen) * 100)));
			eventArray.add(cnEvent);
		}

		return eventArray;

	}

	@RequestMapping(value = "/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName,
			@RequestParam(required = false) String queryParams) throws Exception {

		JSONObject queryFilter = JSONObject.parseObject(queryParams);
		List<OrderOA> list = orderOAService.findForExport(queryFilter);
		Map<String, Object> beans = new HashMap<String, Object>();
		for (OrderOA orderOA : list) {
			List<OrderOA> results = orderOAService.getSubListByOrderItemId(orderOA.getId());
			orderOA.setSubPage(results);
		}
		beans.put("list", list);
		XLSTransformer transformer = new XLSTransformer();
		Workbook wb;
		try {
			// 模板路径
			String classPath = this.getClass().getResource("/").getPath() + "exportfile/orderOA.xls";
			wb = transformer.transformXLS(new FileInputStream(classPath), beans); // 获得Workbook对象
			fileName = URLEncoder.encode(fileName, "UTF8") + ".xls";
			String userAgent = request.getHeader("User-Agent").toLowerCase(); // 获取用户代理
			if (userAgent.indexOf("msie") != -1) { // IE浏览器
				fileName = "filename=\"" + fileName + "\"";
			} else if (userAgent.indexOf("mozilla") != -1) { // firefox浏览器
				fileName = "filename*=UTF-8''" + fileName;
			}
			response.reset();
			response.setContentType("application/ms-excel");
			response.setHeader("Content-Disposition", "attachment;" + fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
		} catch (Exception e) {
			throw e;
		}

	}

}
