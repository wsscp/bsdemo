package cc.oit.bsmes.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.constants.DataStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.QCDataType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfacePLM.dao.CanShuKuDAO;
import cc.oit.bsmes.interfacePLM.dao.ScxDAO;
import cc.oit.bsmes.interfacePLM.model.CanShuKu;
import cc.oit.bsmes.interfacePLM.model.Prcv;
import cc.oit.bsmes.interfacePLM.model.Process;
import cc.oit.bsmes.interfacePLM.model.Scx;
import cc.oit.bsmes.interfacePLM.service.PrcvService;
import cc.oit.bsmes.interfacePLM.service.ProcessService;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.jobs.EventProcessJob;
import cc.oit.bsmes.job.tasks.AlarmProcessTask;
import cc.oit.bsmes.job.tasks.BZProcessTask;
import cc.oit.bsmes.job.tasks.CanShukuTask;
import cc.oit.bsmes.job.tasks.EquipStatusProcessTask;
import cc.oit.bsmes.job.tasks.EquipUpdateTask;
import cc.oit.bsmes.job.tasks.EventScheduleTask;
import cc.oit.bsmes.job.tasks.PLMPrcvAsyncTask;
import cc.oit.bsmes.job.tasks.PLMProductTask;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pro.dao.ProcessReceiptDAO;
import cc.oit.bsmes.pro.model.EqipListBz;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessQCEqip;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessQcBz;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.model.ProductProcessBz;
import cc.oit.bsmes.pro.service.EqipListBzService;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessQCEqipService;
import cc.oit.bsmes.pro.service.ProcessQcBzService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.pro.service.ProductCraftsBzService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessBzService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.model.WorkOrderOperateLog;
import cc.oit.bsmes.wip.service.WorkOrderOperateLogService;
import cc.oit.bsmes.wip.service.WorkOrderReportService;
import cc.oit.bsmes.wip.service.WorkOrderService;
import cc.oit.bsmes.wwalmdb.service.AlarmHistoryService;

/**
 * Created by chanedi on 14-3-27.
 */
public class PlmMesDataAsync extends BaseTest {
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private DataAcquisitionService dataAcquisitionService;
	@Resource
	private ProcessReceiptDAO processReceiptDAO;
	@Resource
	private org.springframework.core.io.Resource processParmData;
	@Resource
	private AlarmHistoryService alarmHistoryService;
	@Resource
	private PLMProductTask pLMProductTask;
	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private BZProcessTask bZProcessTask;
	@Resource
	private EventScheduleTask eventScheduleTask;
	@Resource
	private WorkOrderReportService workOrderReportService;
	@Resource
	private UserService userService;
	@Resource
	private EventProcessJob eventProcessJob;
	@Resource
	private EquipUpdateTask equipUpdateTask;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private ProcessReceiptService processReceiptService;
	@Resource
	private ProductService productService;
	@Resource
	private WorkOrderOperateLogService workOrderOperateLogService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private BZProcessTask bzProcessTask;
	@Resource
	private PLMPrcvAsyncTask pLMPrcvAsyncTask;
	@Resource
	private CanShukuTask canShukuTask;
	@Resource
	private PrcvService prcvService;
	@Resource
	private ProcessQcBzService processQcBzService;
	@Resource
	private EquipListService equipListService;
	@Resource
	private EqipListBzService eqipListBzService;
	@Resource
	private ProductProcessBzService productProcessBzService;
	@Resource
	private ProductCraftsBzService productCraftsBzService;

	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;

	// @Resource
	// private MpartTask mpartTask;

	private static String[] codeArray = {"CZ20100120345-5*2.5"};

	@Resource
	private CanShuKuDAO canShuKuDAO;
	@Resource
	private ScxDAO scxDAO;

	@Resource
	private EquipStatusProcessTask equipStatusProcessTask;
	
	@Test
	@Rollback(false)
	public void updateQcInOut() throws BiffException, IOException {
		String craftsCode = null ;
		craftsCode = "C42023072-001";
		processInOutService.updateQcInOut(craftsCode);
	}

	/**
	 * 同步PLM数据整合junit test
	 * 
	 * 1、同步产品
	 * 2、同步设备
	 * 3、同步物料
	 * 4、同步工艺
	 * */
	@Test
	@Rollback(false)
	public void dataAsyncPLM2MES() throws BiffException, IOException {
		JobParams parms = new JobParams();
		parms.setOrgCode("bstl01");
		pLMProductTask.process(parms); //1、同步产品
		equipUpdateTask.process(parms); //2、同步设备
//		// 设备同步结束更新设备映射关系
//		Workbook workbook = Workbook.getWorkbook(processParmData.getInputStream());
//		super.importEquipMESWWMapping(workbook, parms.getOrgCode());
		canShukuTask.process(parms); //3、同步物料
		pLMPrcvAsyncTask.process(parms); //4、同步工艺
//		super.insertProcessParmData(workbook, parms.getOrgCode()); // 临时，后期更新到工序生产线上去
	}
	
	@Test
	@Rollback(false)
	public void  processQcBzTest(){
		String processId = "01_1AD5E4AB6ABD43939F5909B4222FE8AE";
		List<CanShuKu> canshukuArray = canShuKuDAO.getParamArrayByProcessId(processId);
		// 2015.6.16日修改，性能优化，批量删除以及批量插入
		List<ProcessQcBz> processQcBzs = new ArrayList<ProcessQcBz>();
		
		processQcBzService.deleteProcessQCbzByProcessId(processId);
		
		for (CanShuKu canshuku : canshukuArray) {
			// ***********暂时修改为直接新增 不考虑修改，提高性能
			ProcessQcBz processQcBz = new ProcessQcBz();
			processQcBz.setProcessId(processId);
			processQcBz.setCheckItemCode(canshuku.getNo());
			processQcBz.setCheckItemName(canshuku.getName());
			processQcBz.setFrequence(10.00); // 检测频率????
			processQcBz.setNeedDa(false); // 是否需要数采????
			processQcBz.setNeedIs(false); // 是否需要下发????
			processQcBz.setItemTargetValue(canshuku.getValue() == null ? ""
					: canshuku.getValue()); // 参数目标值????
			processQcBz.setItemMaxValue(null); // 参数上限????
			processQcBz.setItemMinValue(null); // 参数下限????
			processQcBz.setDataType(QCDataType.DOUBLE); // 参数数据类型????
			processQcBz.setDataUnit(null); // 参数单位????
			processQcBz.setHasPic("0"); // 是否有附件????
			processQcBz
					.setNeedShow(StringUtils.isNotBlank(canshuku.getValue()) ? "1"
							: "0"); // 是否需要在终端显示????
			processQcBz.setNeedFirstCheck((canshuku.getNo().indexOf(FIRST_CHECK)>=0 || canshuku.getNo().indexOf(FIRST_CHECK_0)>=0) ?"1":"0" );
			processQcBz.setNeedInCheck((canshuku.getNo().indexOf(IN_CHECK)>=0 || canshuku.getNo().indexOf(IN_CHECK_0)>=0) ?"1":"0");
			processQcBz.setNeedOutCheck((canshuku.getNo().indexOf(OUT_CHECK)>=0 || canshuku.getNo().indexOf(OUT_CHECK_0)>=0) ?"1":"0");
			processQcBz.setNeedMiddleCheck((canshuku.getNo().indexOf(MIDDLE_CHECK)>=0 || canshuku.getNo().indexOf(MIDDLE_CHECK_0)>=0) ?"1":"0");
			processQcBz.setNeedAlarm("0"); // 超差是否报警????
			processQcBz.setValueDomain(null); // 值域????
			processQcBz.setDataStatus(DataStatus.NORMAL);
			
			processQcBzs.add(processQcBz);
			
		}
		processQcBzService.batchInsertProcessQcBz(processQcBzs);
	}

	/**
	 * 同步生产线
	 * */
	@Test
	@Rollback(false)
	public void dataAsyncSCX() throws Exception {
		JobParams parms = new JobParams();
		parms.setOrgCode("bstl01");

		List<String> productCodeArray = Arrays.asList(codeArray);
		Map<String, Date> findParams = new HashMap<String, Date>();
		List<Prcv> prcvList = prcvService.getAsyncDataList(findParams); // 获取PLM变更的工艺信息
		for (Prcv prcv : prcvList) {
			if (!productCodeArray.contains(prcv.getProductNo())) {
				continue;
			} else {
				System.out.println(prcv.getId() + "----------------------------");
				List<Process> processList = processService.getAsyncDataList(prcv.getId());
				for (Process process : processList) {
					List<Scx> scxArray = scxDAO.getAsyncDataList(process.getId());
					if (null != scxArray && scxArray.size() > 0) {
						// 同步到标准工艺库
						for (Scx scx : scxArray) {
							scxAsync(scx, process.getId(), prcv.getProductNo());
						}
					}
				}

			}

		}
	}

	/**
	 * 同步生产线信息
	 * 
	 * @author DingXintao
	 * @param scx PLM生产线同步对象
	 * @param processId 工序ID
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * */
	private void scxAsync(Scx scx, String processId, String productCode) throws IllegalAccessException,
			InstantiationException, InvocationTargetException, NoSuchMethodException {

		ProductProcessBz productProcessBz = productProcessBzService.getById(processId);
		if (null == productProcessBz) {
			return;
		}

		boolean isAdd; // 是否新增
		EqipListBz equipListBz = new EqipListBz();
		equipListBz.setEquipCode(scx.getNo());
		equipListBz.setProcessId(processId);
		equipListBz.setType(EquipType.PRODUCT_LINE); // 设备类型
		// 判断是否存在了，是就update，否就insert
		List<EqipListBz> equipListBzList = eqipListBzService.getByObj(equipListBz);
		if (null == equipListBzList || equipListBzList.size() == 0) {
			equipListBz.setCreateUserCode("PLM");
			isAdd = true;
		} else {
			equipListBz = equipListBzList.get(0);
			equipListBz.setModifyUserCode("PLM");
			isAdd = false;
		}
		equipListBz.setEquipCapacity(1.0); // 设备能力 ????
		equipListBz.setSetUpTime(0); // 前置时间 ????
		equipListBz.setShutDownTime(0); // 后置时间 ????
		equipListBz.setIsDefault(true);
		// BNUM 数量
		// PSTNO 顺序号
		// ORG_CODE 数据所属组织
		// STATUS 处理状态
		if (isAdd) {
			eqipListBzService.insert(equipListBz);

			// 同步到实例库
			ProductCrafts findCrafts = new ProductCrafts();
			findCrafts.setProductCode(productCode);
			List<ProductCrafts> productCraftsArray = productCraftsService.findByObj(findCrafts);
			if (null != productCraftsArray && productCraftsArray.size() > 0) {
				for (ProductCrafts productCrafts : productCraftsArray) {
					List<ProductProcess> productProcessArray = productProcessService.getByProductCraftsId(productCrafts
							.getId());
					if (null != productProcessArray && productProcessArray.size() > 0) {
						for (ProductProcess productProcess : productProcessArray) {
							if (productProcessBz.getProcessCode().equals(productProcess.getProcessCode())
									&& productProcessBz.getSeq() == productProcess.getSeq()) {

								EquipList equipList = new EquipList();
								equipList.setEquipCode(scx.getNo());
								equipList.setProcessId(productProcess.getId());
								equipList.setType(EquipType.PRODUCT_LINE); // 设备类型
								equipList.setCreateUserCode("PLM");
								equipList.setEquipCapacity(1.0); // 设备能力 ????
								equipList.setSetUpTime(0); // 前置时间 ????
								equipList.setShutDownTime(0); // 后置时间 ????
								equipList.setIsDefault(true);
								equipListService.insert(equipList);

								List<ProcessQc> processQcArray = processQcService
										.getByProcessId(productProcess.getId());
								// 找到下面的QC 同步质量参数
								for (ProcessQc processQc : processQcArray) {
									EquipInfo equipInfo = StaticDataCache.getMainEquipInfo(equipList.getEquipCode());
									if (null == equipInfo) {
										continue;
									}
									ProcessQCEqip processQCEqip = new ProcessQCEqip();
									processQCEqip.setQcId(processQc.getId());
									processQCEqip.setEquipCode(equipInfo.getCode());
									processQCEqip.setEquipId(equipInfo.getId());
									processQCEqipService.insert(processQCEqip);
								}
							}

						}
					}
				}
			}
		} else {
			// eqipListBzService.update(equipListBz);
		}
	}

	/**
	 * 同步漏掉的质量参数
	 * */
	@Test
	@Rollback(false)
	public void dataAsyncCanShu() throws Exception {
		JobParams parms = new JobParams();
		parms.setOrgCode("bstl01");

		List<Prcv> prcvList = prcvService.getPrcvArrayByProductCodeArray(codeArray); // 获取PLM变更的工艺信息
		for (Prcv prcv : prcvList) {
			List<Process> processList = processService.getAsyncDataList(prcv.getId());
			for (Process process : processList) {
				List<CanShuKu> canshukuArray = new ArrayList<CanShuKu>();
				if (StringUtils.isNotEmpty(process.getCsvalue1())) {
					canshukuArray.addAll(canShuKuDAO.getParamArrayByProcessId(process.getId()));
				}
//				if (StringUtils.isNotEmpty(process.getCsvalue2())) {
//					canshukuArray.addAll(canShuKuDAO.getParamArrayByProcessId2(process.getId()));
//				}
//				if (StringUtils.isNotEmpty(process.getCsvalue3())) {
//					canshukuArray.addAll(canShuKuDAO.getParamArrayByProcessId3(process.getId()));
//				}

				System.out.println("同步漏掉质量参数" + canshukuArray.size() + "---" + process.getId());

//				if("".equals(""))
//				continue;
				// 同步标准库
				for (CanShuKu canshuku : canshukuArray) {
					this.scxAsyncBZ(canshuku, process.getId());
				}

				// 根据产品编码获取实例工艺信息
				ProductCrafts findCrafts = new ProductCrafts();
				findCrafts.setProductCode(prcv.getProductNo());
				List<ProductCrafts> craftsList = productCraftsService.findByObj(findCrafts);
				for (ProductCrafts productCrafts : craftsList) {
					// 获取工序信息
					List<ProductProcess> productProcessList = productProcessService.getByProductCraftsId(productCrafts
							.getId());
					for (ProductProcess productProcess : productProcessList) {
						if (productProcess.getSeq() == process.getGno()) { // 表示是同一道工序，添加质量信息
							List<EquipList> equipListList = equipListService.getByProcessId(productProcess.getId());
							for (CanShuKu canshuku : canshukuArray) {
								this.scxAsync(canshuku, productProcess.getId(), equipListList);
							}
						}
					}
				}
			}
		}

		// bzProcessTask.process(parms);
		// equipUpdateTask.process(parms);
		// pLMProductTask.process(parms);
		// canShukuTask.process(parms);
		// pLMPrcvAsyncTask.process(parms);
		//
		// List<String> in = new ArrayList<String>();
		// List<String> out = new ArrayList<String>();
		//
		// for (String code : codeArray) {
		// Product product = new Product();
		// product.setProductCode(code);
		// List<Product> list = productService.findByObj(product);
		// if (null == list || list.size() == 0) {
		// out.add(code);
		// } else {
		// in.add(code);
		// }
		//
		// }
		// System.out.println("存在............");
		// for (String s : in) {
		// System.out.println(s);
		// }
		// System.out.println("不存在............");
		// for (String s : out) {
		// System.out.println(s);
		// }

	}

	/**
	 * 同步生产线信息
	 * 
	 * @author DingXintao
	 * @param canshuObj PLM工艺参数同步对象
	 * @param processId 工序ID
	 * @return
	 * */
	private void scxAsyncBZ(CanShuKu canshuObj, String processId) {

		boolean isAdd; // 是否新增
		ProcessQcBz processQcBz = new ProcessQcBz();
		processQcBz.setProcessId(processId);
		processQcBz.setCheckItemCode(canshuObj.getNo());
		// 判断是否存在了，是就update，否就insert
		processQcBz.setCreateUserCode("PLM");
		isAdd = true;

		processQcBz.setCheckItemName(canshuObj.getName());
		processQcBz.setFrequence(10.00); // 检测频率????
		processQcBz.setNeedDa(false); // 是否需要数采????
		processQcBz.setNeedIs(false); // 是否需要下发????
		processQcBz.setItemTargetValue(canshuObj.getValue() == null ? "" : canshuObj.getValue()); // 参数目标值????
		processQcBz.setItemMaxValue(null); // 参数上限????
		processQcBz.setItemMinValue(null); // 参数下限????
		processQcBz.setDataType(QCDataType.DOUBLE); // 参数数据类型????
		processQcBz.setDataUnit(null); // 参数单位????
		processQcBz.setHasPic("0"); // 是否有附件????
		processQcBz.setNeedShow(StringUtils.isNotBlank(canshuObj.getValue()) ? "1" : "0"); // 是否需要在终端显示????
		processQcBz.setNeedFirstCheck((canshuObj.getNo().indexOf(FIRST_CHECK) >= 0 || canshuObj.getNo().indexOf(
				FIRST_CHECK_0) >= 0) ? "1" : "0");
		processQcBz
				.setNeedInCheck((canshuObj.getNo().indexOf(IN_CHECK) >= 0 || canshuObj.getNo().indexOf(IN_CHECK_0) >= 0) ? "1"
						: "0");
		processQcBz.setNeedOutCheck((canshuObj.getNo().indexOf(OUT_CHECK) >= 0 || canshuObj.getNo()
				.indexOf(OUT_CHECK_0) >= 0) ? "1" : "0");
		processQcBz.setNeedMiddleCheck((canshuObj.getNo().indexOf(MIDDLE_CHECK) >= 0 || canshuObj.getNo().indexOf(
				MIDDLE_CHECK_0) >= 0) ? "1" : "0");
		processQcBz.setNeedAlarm("0"); // 超差是否报警????
		processQcBz.setValueDomain(null); // 值域????
		processQcBz.setDataStatus(DataStatus.NORMAL);

		if (isAdd) {
			processQcBzService.insert(processQcBz);
		} else {
			processQcBzService.update(processQcBz);
		}
	}

	/**
	 * 同步生产线信息
	 * 
	 * @author DingXintao
	 * @param canshuObj PLM工艺参数同步对象
	 * @param processId 工序ID
	 * @return
	 * */
	private void scxAsync(CanShuKu canshuObj, String processId, List<EquipList> equipListList) {

		boolean isAdd; // 是否新增
		ProcessQc processQc = new ProcessQc();
		processQc.setProcessId(processId);
		processQc.setCheckItemCode(canshuObj.getNo());
		// 判断是否存在了，是就update，否就insert
		processQc.setCreateUserCode("PLM");
		isAdd = true;

		processQc.setCheckItemName(canshuObj.getName());
		processQc.setFrequence(10.00); // 检测频率????
		processQc.setNeedDa(false); // 是否需要数采????
		processQc.setNeedIs(false); // 是否需要下发????
		processQc.setItemTargetValue(canshuObj.getValue() == null ? "" : canshuObj.getValue()); // 参数目标值????
		processQc.setItemMaxValue(null); // 参数上限????
		processQc.setItemMinValue(null); // 参数下限????
		processQc.setDataType(QCDataType.DOUBLE); // 参数数据类型????
		processQc.setDataUnit(null); // 参数单位????
		processQc.setHasPic("0"); // 是否有附件????
		processQc.setNeedShow(StringUtils.isNotBlank(canshuObj.getValue()) ? "1" : "0"); // 是否需要在终端显示????
		processQc.setNeedFirstCheck((canshuObj.getNo().indexOf(FIRST_CHECK) >= 0 || canshuObj.getNo().indexOf(
				FIRST_CHECK_0) >= 0) ? "1" : "0");
		processQc
				.setNeedInCheck((canshuObj.getNo().indexOf(IN_CHECK) >= 0 || canshuObj.getNo().indexOf(IN_CHECK_0) >= 0) ? "1"
						: "0");
		processQc
				.setNeedOutCheck((canshuObj.getNo().indexOf(OUT_CHECK) >= 0 || canshuObj.getNo().indexOf(OUT_CHECK_0) >= 0) ? "1"
						: "0");
		processQc.setNeedMiddleCheck((canshuObj.getNo().indexOf(MIDDLE_CHECK) >= 0 || canshuObj.getNo().indexOf(
				MIDDLE_CHECK_0) >= 0) ? "1" : "0");
		processQc.setNeedAlarm("0"); // 超差是否报警????
		processQc.setValueDomain(null); // 值域????
		processQc.setDataStatus(DataStatus.NORMAL);

		if (isAdd) {
			processQcService.insert(processQc);
		} else {
			processQcService.update(processQc);
		}

		for (EquipList equipList : equipListList) {
			EquipInfo equipInfo = StaticDataCache.getMainEquipInfo(equipList.getEquipCode());
			if (null == equipInfo) {
				continue;
			}
			ProcessQCEqip processQCEqip = new ProcessQCEqip();
			processQCEqip.setQcId(processQc.getId());
			processQCEqip.setEquipCode(equipInfo.getCode());
			processQCEqip.setEquipId(equipInfo.getId());
			processQCEqipService.insert(processQCEqip);
		}

	}

	private static String FIRST_CHECK_0 = "-011-"; // 首
	private static String IN_CHECK_0 = "-012-"; // 上
	private static String OUT_CHECK_0 = "-013-"; // 下
	private static String MIDDLE_CHECK_0 = "-014-"; // 中

	private static String FIRST_CHECK = "-11-"; // 首
	private static String IN_CHECK = "-12-"; // 上
	private static String OUT_CHECK = "-13-"; // 下
	private static String MIDDLE_CHECK = "-14-"; // 中

	// productProcessEquipAsyncTask.process(parms);

	// customerOrderItemProDecService.splitOrderByOrderItemId("30b2385a-c1a6-4da8-b62a-c01aacd1f6df",
	// "1");

	@Test
	@Rollback(false)
	public void testAbc() throws Exception {
		String equipCode = "B117火花复绕_EQUIP";
		String processCode = "MATCHING_RESPOOLING";
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("equipCode", equipCode);
		findParams.put("processCode", processCode);
		List<ProcessReceipt> processReceiptList = processReceiptDAO.getByEquipCodeAndProcessCode(findParams);
		if (processReceiptList != null && !processReceiptList.isEmpty()) {
			for (ProcessReceipt processReceipt : processReceiptList) {
				System.out.println(processReceipt.getReceiptCode());
			}
		}

		if (processReceiptList != null && !processReceiptList.isEmpty()) {
			List<Receipt> receipts = new ArrayList<Receipt>();
			for (ProcessReceipt processReceipt : processReceiptList) {
				Receipt receipt = new Receipt();
				receipt.setReceiptCode(processReceipt.getReceiptCode());
				receipt.setEquipCode(equipCode);
				receipts.add(receipt);
			}
			// 接入外部接口获取质量参数实时数据
			List<Receipt> receiptsResutl = dataAcquisitionService.queryLiveReceiptByCodes(receipts);

			for (Receipt receipt : receiptsResutl) {
				for (ProcessReceipt processReceipt : processReceiptList) {
					if (StringUtils.equalsIgnoreCase(receipt.getEquipCode(), processReceipt.getEquipCode())
							&& StringUtils.equalsIgnoreCase(receipt.getReceiptCode(), processReceipt.getReceiptCode())) {
						processReceipt.setDaValue(receipt.getDaValue());

						System.out.println(processReceipt.getReceiptCode() + ":" + processReceipt.getDaValue());
					}
				}
			}
		}

	}

	@Resource
	public CustomerOrderService customerOrderService;

	@Resource
	public SalesOrderService salesOrderService;

	// 产品工艺、工序、投入产出、设备清单、工艺/质量参数
	@Test
	@Rollback(false)
	public void productProcessEquipAsyncTask() throws Exception {
		JobParams parms = new JobParams();
		parms.setOrgCode("1");
		// productProcessEquipAsyncTask.process(parms);

		// eventScheduleTask.process(parms);
		List<SalesOrder> salesOrders = salesOrderService.getAll();
		for (SalesOrder order : salesOrders) {
			CustomerOrder customerOrder = customerOrderService.insert(order);
			customerOrderService.setCustomerOaDate(customerOrder, order.getConfirmDate());
		}
	}

	@Resource
	private OrderTaskService orderTaskService;

	// 产品
	@Test
	@Rollback(false)
	public void pLMProductTask() throws Exception {
		Map<String, ProductProcess> processCache = new HashMap<String, ProductProcess>();
		Set<String> proDecIdsCache = new HashSet<String>();
		WorkOrderOperateLog findParamsOperateLog = new WorkOrderOperateLog();
		List<WorkOrderOperateLog> workOrderOperateLogList = workOrderOperateLogService.findByObj(findParamsOperateLog);
		for (WorkOrderOperateLog workOrderOperateLog : workOrderOperateLogList) {
			String workOrderNo = workOrderOperateLog.getWorkOrderNo();
			String equipCode = workOrderOperateLog.getEquipCode();

			OrderTask orderTaskParam = new OrderTask();
			orderTaskParam.setWorkOrderNo(workOrderNo);
			orderTaskParam.setEquipCode(equipCode);
			List<OrderTask> OrderTaskList = orderTaskService.getByObj(orderTaskParam);
			for (OrderTask orderTask : OrderTaskList) {
				if (!WorkOrderStatus.PAUSE.equals(orderTask.getStatus())) {
					continue;
				}

				this.putProcessToMap(orderTask.getProcessId(), processCache, true);

				List<CustomerOrderItemProDec> customerOrderItemProDecList = customerOrderItemProDecService
						.getProDecByOrderTaskId(orderTask.getId());
				Set<String> processIdsCache = processCache.keySet();
				for (CustomerOrderItemProDec customerOrderItemProDec : customerOrderItemProDecList) {
					// 如果ProDec的工艺流程Id存在于验证列表，将其放入更新列表中
					if (ProductOrderStatus.PAUSE.equals(customerOrderItemProDec.getStatus())
							&& processIdsCache.contains(customerOrderItemProDec.getProcessId())) {
						// customerOrderItemProDec.setStatus(ProductOrderStatus.TO_DO);
						// System.out.println(customerOrderItemProDec.getId() +
						// "---------------等待加工");
						// count++;
						proDecIdsCache.add(customerOrderItemProDec.getId());
					}
				}

			}

			WorkOrder findParams = new WorkOrder();
			findParams.setWorkOrderNo(workOrderNo);
			findParams.setEquipCode(equipCode);
			List<WorkOrder> updateWorkOrder = workOrderService.findByObj(findParams);
			for (WorkOrder workOrder : updateWorkOrder) {
				if (!WorkOrderStatus.PAUSE.equals(workOrder.getStatus())) {
					continue;
				}
				System.out.println("update t_wip_work_order set status='PAUSE' where id='" + workOrder.getId() + "';");
			}

		}

		for (String proDecId : proDecIdsCache) {
			System.out.println("update t_pla_cu_order_item_pro_dec set status='TO_DO' where id='" + proDecId + "';");
		}
	}

	/**
	 * <p>
	 * 有工艺流程Id获取所有相关子流程的信息
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-7-2 9:20:48
	 * @param processId 工艺流程ID
	 * @param processCache 存放缓存Map
	 */
	private void putProcessToMap(String processId, Map<String, ProductProcess> processCache, Boolean isInit) {
		ProductProcess process = processCache.get(processId);
		if (process == null) {
			process = productProcessService.getById(processId);
			if (!isInit) {
				processCache.put(processId, process);
			}
			if (!StringUtils.equals(process.getNextProcessId(), WebConstants.ROOT_ID)) {
				putProcessToMap(process.getNextProcessId(), processCache, false);
			}
		}
	}

	// 标准工序库
	@Test
	@Rollback(false)
	public void bZProcessTask() throws Exception {
		List<WorkOrderOperateLog> list = workOrderOperateLogService.getAll();
		Set<String> set = new HashSet<String>();
		for (WorkOrderOperateLog log : list) {
			set.add(log.getWorkOrderNo());
		}

		for (String log : set) {
			WorkOrder findParams = new WorkOrder();
			findParams.setWorkOrderNo(log);
			List<WorkOrder> workOrderList = workOrderService.findByObj(findParams);
			if (null == workOrderList || workOrderList.size() == 0) {
				System.out.println("生产单[" + log + "]不存在！");
			} else {
				findParams = workOrderList.get(0);

				ProductProcess productProcess = StaticDataCache.getProcessByProcessId(findParams.getProcessId());
				ProductCrafts crafts = StaticDataCache.getCrafts(productProcess.getProductCraftsId());
//				Product product = StaticDataCache.getProductMap().get(crafts.getProductCode());

				String outProductColor = StaticDataCache.getMatMap().get(findParams.getHalfProductCode()).getColor();
				// System.out.println("update t_wip_work_order_operate_log l set l.out_product_color='"
				// + outProductColor
				// + "', l.PRODUCT_TYPE='" + product.getProductType() +
				// "', l.PRODUCT_SPEC='"
				// + product.getProductSpec() + "' where l.work_order_no='" +
				// log + "';");

				System.out.println("update t_wip_work_order_operate_log l set l.out_product_color='" + outProductColor
						+ "' where l.work_order_no='" + log + "';");
			}
		}

	}

	@Resource
	private ProcessQCEqipService processQCEqipService;
	@Resource
	private ProcessService processService;

	@Test
	@Rollback(false)
	public void aaaa() throws Exception {
	}

	@Resource
	private AlarmProcessTask alarmProcessTask;

	@Test
	@Rollback(false)
	public void eventScheduleTask_insertTest() throws Exception {
		try {
			JobParams parms = new JobParams();
			parms.setOrgCode("1");
			// eventScheduleTask.process(parms);
			// eventScheduleTask.process(parm);

			// pLMProductTask.process(parms);
			// productProcessEquipAsyncTask.process(parms);
			// processQcAsyncServiceImpl.process();

			alarmProcessTask.process(parms);
			// processService.process();
			System.out.println("----------------------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception();
		}

	}

	@Test
	@Rollback(false)
	public void createMaintainEvent() throws Exception {
		List<ProductCrafts> productCraftss = productCraftsService.getAll();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet");// 添加sheet
		// 表格样式\
		HSSFPalette palette = wb.getCustomPalette(); // wb HSSFWorkbook对象
		// palette.setColorAtIndex((short) 9, (byte) (color.getRed()), (byte)
		// (color.getGreen()), (byte) (color.getBlue()));
		palette.setColorAtIndex((short) 9, (byte) (218), (byte) (150), (byte) (148));
		palette.setColorAtIndex((short) 10, (byte) (230), (byte) (184), (byte) (183));
		palette.setColorAtIndex((short) 11, (byte) (242), (byte) (220), (byte) (219));

		HSSFFont firstFont = wb.createFont();
		firstFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		firstFont.setFontHeightInPoints((short) 14);
		HSSFCellStyle firstStep = wb.createCellStyle();
		// firstStep.setFillBackgroundColor(HSSFColor.YELLOW.index);
		firstStep.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置前景填充样式
		firstStep.setFillForegroundColor((short) 9);// 前景填充色
		firstStep.setFont(firstFont);

		HSSFFont secondFont = wb.createFont();
		secondFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		secondFont.setFontHeightInPoints((short) 12);
		HSSFCellStyle secondStep = wb.createCellStyle();
		// secondStep.setFillBackgroundColor(HSSFColor.YELLOW.index2);
		secondStep.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置前景填充样式
		secondStep.setFillForegroundColor((short) 10);// 前景填充色
		secondStep.setFont(secondFont);

		HSSFFont thirdFont = wb.createFont();
		thirdFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		thirdFont.setFontHeightInPoints((short) 10);
		HSSFCellStyle thirdStep = wb.createCellStyle();
		thirdStep.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置前景填充样式
		thirdStep.setFillForegroundColor((short) 11);// 前景填充色
		thirdStep.setFont(thirdFont);

		// 行号
		int rowNum = 0;
		for (int i = 0; i < productCraftss.size(); i++) {
			ProductCrafts productCrafts = productCraftss.get(i);
			HSSFRow row = sheet.createRow(rowNum);
			rowNum++;
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(firstStep);
			cell.setCellValue("产品代码");
			cell = row.createCell(1);
			cell.setCellStyle(firstStep);
			cell.setCellValue("工艺代码");
			cell = row.createCell(2);
			cell.setCellStyle(firstStep);
			cell.setCellValue("工艺名称");
			cell = row.createCell(3);
			cell.setCellStyle(firstStep);
			cell.setCellValue("");
			cell = row.createCell(4);
			cell.setCellStyle(firstStep);
			cell.setCellValue("");
			cell = row.createCell(5);
			cell.setCellStyle(firstStep);
			cell.setCellValue("");
			cell = row.createCell(6);
			cell.setCellStyle(firstStep);
			cell.setCellValue("");
			cell = row.createCell(7);
			cell.setCellStyle(firstStep);
			cell.setCellValue("");
			cell = row.createCell(8);
			cell.setCellStyle(firstStep);
			cell.setCellValue("");
			cell = row.createCell(9);
			cell.setCellStyle(firstStep);
			cell.setCellValue("");

			row = sheet.createRow(rowNum);
			rowNum++;
			cell = row.createCell(0);
			cell.setCellValue(productCrafts.getProductCode());
			cell = row.createCell(1);
			cell.setCellValue(productCrafts.getCraftsCode());
			cell = row.createCell(2);
			cell.setCellValue(productCrafts.getCraftsName());

			ProductProcess findParam = new ProductProcess();
			findParam.setProductCraftsId(productCrafts.getId());
			List<ProductProcess> productProcesss = productProcessService.findByObj(findParam);
			for (ProductProcess productProcess : productProcesss) {
				row = sheet.createRow(rowNum);
				rowNum++;

				cell = row.createCell(1);
				cell.setCellStyle(secondStep);
				cell.setCellValue("工序");
				cell = row.createCell(2);
				cell.setCellStyle(secondStep);
				cell.setCellValue("工序代码");
				cell = row.createCell(3);
				cell.setCellStyle(secondStep);
				cell.setCellValue("工序名称");
				cell = row.createCell(4);
				cell.setCellStyle(secondStep);
				cell.setCellValue("");
				cell = row.createCell(5);
				cell.setCellStyle(secondStep);
				cell.setCellValue("");
				cell = row.createCell(6);
				cell.setCellStyle(secondStep);
				cell.setCellValue("");
				cell = row.createCell(7);
				cell.setCellStyle(secondStep);
				cell.setCellValue("");
				cell = row.createCell(8);
				cell.setCellStyle(secondStep);
				cell.setCellValue("");
				cell = row.createCell(9);
				cell.setCellStyle(secondStep);
				cell.setCellValue("");

				row = sheet.createRow(rowNum);
				rowNum++;
				cell = row.createCell(2);
				cell.setCellValue(productProcess.getProcessCode());
				cell = row.createCell(3);
				cell.setCellValue(productProcess.getProcessName());

				row = sheet.createRow(rowNum);
				rowNum++;
				cell = row.createCell(2);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("工艺参数");
				cell = row.createCell(3);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("参数代码");
				cell = row.createCell(4);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("参数名称");
				cell = row.createCell(5);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("参数目标值");
				cell = row.createCell(6);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("参数上限");
				cell = row.createCell(7);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("参数下限");
				cell = row.createCell(8);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("数据类型");
				cell = row.createCell(9);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("单位");

				List<ProcessReceipt> processReceipts = processReceiptService.getByProcessId(productProcess.getId());
				for (ProcessReceipt receipt : processReceipts) {
					row = sheet.createRow(rowNum);
					rowNum++;
					cell = row.createCell(3);
					cell.setCellValue(receipt.getReceiptCode());
					cell = row.createCell(4);
					cell.setCellValue(receipt.getReceiptName());
					cell = row.createCell(5);
					cell.setCellValue(receipt.getReceiptTargetValue());
					cell = row.createCell(6);
					cell.setCellValue(receipt.getReceiptMaxValue());
					cell = row.createCell(7);
					cell.setCellValue(receipt.getReceiptMinValue());
					cell = row.createCell(8);
					cell.setCellValue(receipt.getDataType());
					cell = row.createCell(9);
					cell.setCellValue(receipt.getDataUnit());

				}

				row = sheet.createRow(rowNum);
				rowNum++;
				cell = row.createCell(2);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("质量参数");
				cell = row.createCell(3);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("参数代码");
				cell = row.createCell(4);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("参数名称");
				cell = row.createCell(5);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("参数目标值");
				cell = row.createCell(6);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("参数上限");
				cell = row.createCell(7);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("参数下限");
				cell = row.createCell(8);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("数据类型");
				cell = row.createCell(9);
				cell.setCellStyle(thirdStep);
				cell.setCellValue("单位");

				List<ProcessQc> processQcs = processQcService.getByProcessId(productProcess.getId());
				for (ProcessQc qc : processQcs) {
					row = sheet.createRow(rowNum);
					rowNum++;
					cell = row.createCell(3);
					cell.setCellValue(qc.getCheckItemCode());
					cell = row.createCell(4);
					cell.setCellValue(qc.getCheckItemName());
					cell = row.createCell(5);
					cell.setCellValue(qc.getItemTargetValue());
					cell = row.createCell(6);
					cell.setCellValue(qc.getItemMaxValue());
					cell = row.createCell(7);
					cell.setCellValue(qc.getItemMinValue());
					cell = row.createCell(8);
					cell.setCellValue(qc.getItemMinValue());
					cell = row.createCell(9);
					cell.setCellValue(qc.getDataUnit());
				}

			}

		}

		// 导出文件
		FileOutputStream fout = new FileOutputStream("d:\\" + new Date().getTime() + ".xls");
		wb.write(fout);
		fout.close();

	}

	private String[] titleNameArray = { "产品名称", "工艺代码", "工艺名称", "工序代码", "工序名称", "参数代码", "参数名称", "参数目标值", "参数上限",
			"参数下限", "数据类型", "单位" };

	@Test
	@Rollback(false)
	public void createMaintainEvent1() throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet");// 添加sheet
		// 表格样式\
		HSSFPalette palette = wb.getCustomPalette(); // wb HSSFWorkbook对象
		palette.setColorAtIndex((short) 9, (byte) (242), (byte) (220), (byte) (219));
		HSSFFont titleFont = wb.createFont();
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置前景填充样式
		titleStyle.setFillForegroundColor((short) 9);// 前景填充色
		titleStyle.setFont(titleFont);

		int rowNum = 0; // 行号
		// 创建title行
		HSSFRow row = sheet.createRow(rowNum);
		rowNum++;
		HSSFCell cell = null;
		for (int i = 0; i < titleNameArray.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(titleNameArray[i]);
		}

		List<ProductCrafts> productCraftss = productCraftsService.getAll();
		for (int i = 0; i < productCraftss.size(); i++) {
			ProductCrafts productCrafts = productCraftss.get(i);
			Product findParams = new Product();
			findParams.setProductCode(productCrafts.getProductCode());
			List<Product> products = productService.getByObj(findParams);
			if (null != products && products.size() > 0) {
				findParams = products.get(0);
				productCrafts.setProductCode(findParams.getProductName());
			}

			ProductProcess findParam = new ProductProcess();
			findParam.setProductCraftsId(productCrafts.getId());
			List<ProductProcess> productProcesss = productProcessService.findByObj(findParam);
			for (ProductProcess productProcess : productProcesss) {

				List<ProcessReceipt> processReceipts = processReceiptService.getByProcessId(productProcess.getId());
				for (ProcessReceipt receipt : processReceipts) {
					row = sheet.createRow(rowNum);
					rowNum++;
					this.setRow(row, productCrafts.getProductCode(), productCrafts.getCraftsCode(),
							productCrafts.getCraftsName(), productProcess.getProcessCode(),
							productProcess.getProcessName(), receipt.getReceiptCode(), receipt.getReceiptName(),
							receipt.getReceiptTargetValue(), receipt.getReceiptMaxValue(),
							receipt.getReceiptMinValue(), receipt.getDataType(), receipt.getDataUnit());

				}

				List<ProcessQc> processQcs = processQcService.getByProcessId(productProcess.getId());
				for (ProcessQc qc : processQcs) {
					row = sheet.createRow(rowNum);
					rowNum++;
					this.setRow(row, productCrafts.getProductCode(), productCrafts.getCraftsCode(), productCrafts
							.getCraftsName(), productProcess.getProcessCode(), productProcess.getProcessName(), qc
							.getCheckItemCode(), qc.getCheckItemName(), qc.getItemTargetValue(), qc.getItemMaxValue(),
							qc.getItemMinValue(), qc.getDataType().name(), qc.getDataUnit());
				}

			}

		}
		// 导出文件
		FileOutputStream fout = new FileOutputStream("d:\\" + new Date().getTime() + ".xls");
		wb.write(fout);
		fout.close();

	}

	private void setRow(HSSFRow row, String... params) {
		HSSFCell cell;
		for (int i = 0; i < params.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(params[i]);
		}
	}

}
