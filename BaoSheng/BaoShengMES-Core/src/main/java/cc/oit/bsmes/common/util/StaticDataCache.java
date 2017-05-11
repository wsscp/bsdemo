package cc.oit.bsmes.common.util;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class StaticDataCache {
	private static Map resultMap = new HashMap();
	// 数据过期时间 暂定2小时
	private static long expired_milliseconds = 1000 * 3600 * 2;
	private static final String PRODUCT_MAP_KEY = "productMap";
	private static final String MAT_MAP_KEY = "matMap";
	private static final String TIME_KEY = "time";
	private static final String PROCESS_MAP_KEY = "processMap";
	private static final String PROCESS_CRAFTS_MAP_KEY = "processCraftsMapKey";
	private static final String MAIN_EQUIP_KEY = "mainEquipKey";
	private static final String WW_MAPPING_KEY = "wwMappingKey";
	private static final String EQUIP_MAPPING_KEY = "equipMappingKey";
	private static final String EQUIP_LINE_KEY = "equipLineKey";
	private static final String DATA_DIC_KEY = "dataDicKey";
	private static final String PROCESS_IN_OUT_KEY = "processInOutKey";

	static {
		Map temp = new HashMap();
		temp.put(TIME_KEY, System.currentTimeMillis());
		resultMap.put(PRODUCT_MAP_KEY, temp);
		temp = new HashMap();
		temp.put(TIME_KEY, System.currentTimeMillis());
		resultMap.put(MAT_MAP_KEY, temp);
		temp = new HashMap();
		temp.put(TIME_KEY, System.currentTimeMillis());
		resultMap.put(PROCESS_MAP_KEY, temp);
		temp = new HashMap();
		temp.put(TIME_KEY, System.currentTimeMillis());
		resultMap.put(PROCESS_CRAFTS_MAP_KEY, temp);
		temp = new HashMap();
		temp.put(TIME_KEY, System.currentTimeMillis());
		resultMap.put(MAIN_EQUIP_KEY, temp);
		temp = new HashMap();
		temp.put(TIME_KEY, System.currentTimeMillis());
		resultMap.put(WW_MAPPING_KEY, temp);
		temp = new HashMap();
		temp.put(TIME_KEY, System.currentTimeMillis());
		resultMap.put(EQUIP_MAPPING_KEY, temp);
		
		temp = new HashMap();
		temp.put(TIME_KEY, System.currentTimeMillis());
		resultMap.put(EQUIP_LINE_KEY, temp);
		temp = new HashMap();
		temp.put(TIME_KEY, System.currentTimeMillis());
		resultMap.put(DATA_DIC_KEY, temp);
		temp = new HashMap();
		temp.put(TIME_KEY, System.currentTimeMillis());
		resultMap.put(PROCESS_IN_OUT_KEY, temp);

		// 设备状态报警 映射 静态数据
		resultMap.put("R_ASPARK", "火花报警");
		resultMap.put("R_ACONX", "凹凸报警");
		resultMap.put("R_AOD", "外径报警");
		resultMap.put("R_AMFAULT", "异常报警");
		resultMap.put("R_AFINISH", "计米到达报警");
		resultMap.put("R_AHOPPER", "无料报警");
		resultMap.put("R_FAULTC_1", "放线报警");
		resultMap.put("R_FAULTC_2", "收线报警");
		resultMap.put("R_FAULTC_3", "主机报警");
		resultMap.put("R_FAULTC_4", "收线盘报警");

	}

	public static synchronized void init() {

		Map temp = (Map<String, String>) resultMap.get(PRODUCT_MAP_KEY);
		long oldtime = (Long) temp.get(TIME_KEY);

		long now = System.currentTimeMillis();
		if (temp.get(PRODUCT_MAP_KEY) == null || (now - oldtime) > expired_milliseconds) {
			ProductService productService = (ProductService) ContextUtils.getBean(ProductService.class);
			List<Product> alist = productService.getAll();
			Map<String, Product> productMap = new HashMap<String, Product>();
			for (int i = 0; i < alist.size(); i++) {
				productMap.put(alist.get(i).getProductCode(), alist.get(i));
			}
			productMap = productMap.isEmpty() ? null : productMap;
			temp.put(PRODUCT_MAP_KEY, productMap);
			temp.put(TIME_KEY, System.currentTimeMillis());
		}

		temp = (Map<String, String>) resultMap.get(MAT_MAP_KEY);
		oldtime = (Long) temp.get(TIME_KEY);
		now = System.currentTimeMillis();
		if (temp.get(MAT_MAP_KEY) == null || (now - oldtime) > expired_milliseconds) {
			MatService matService = (MatService) ContextUtils.getBean(MatService.class);
			List<Mat> alist = matService.getAll();
			Map<String, Mat> matMap = new HashMap<String, Mat>();
			for (int i = 0; i < alist.size(); i++) {
				matMap.put(alist.get(i).getMatCode(), alist.get(i));
			}
			matMap = matMap.isEmpty() ? null : matMap;
			temp.put(MAT_MAP_KEY, matMap);
			temp.put(TIME_KEY, System.currentTimeMillis());
		}
		
		
		// 初始化 投入产出
		Map<String, List<ProcessInOut>> proInOutCache = new HashMap<String, List<ProcessInOut>>();
		ProcessInOutService processInOutService = (ProcessInOutService) ContextUtils
				.getBean(ProcessInOutService.class);
		List<ProcessInOut> allProcessInout = processInOutService.getAll();
		List<ProcessInOut> processInOuts = null;
		for (ProcessInOut inOut : allProcessInout) {
			processInOuts = proInOutCache.get(inOut.getProductProcessId());
			if (processInOuts == null) {
				processInOuts = new ArrayList<ProcessInOut>();
				proInOutCache.put(inOut.getProductProcessId(), processInOuts);
			}
			if (inOut.getInOrOut() == InOrOut.OUT) {
				processInOuts.add(0, inOut);
			} else {
				processInOuts.add(inOut);
			}
		}
		temp.put(TIME_KEY, System.currentTimeMillis());
		temp.put(PROCESS_IN_OUT_KEY, proInOutCache);
		
		
	}

	public static Map<String, Product> getProductMap() {
		init();
		Map temp = (Map) resultMap.get(PRODUCT_MAP_KEY);
		return (Map<String, Product>) temp.get(PRODUCT_MAP_KEY);
	}

	public static Map<String, Mat> getMatMap() {
		init();
		Map temp = (Map) resultMap.get(MAT_MAP_KEY);
		return (Map<String, Mat>) temp.get(MAT_MAP_KEY);
	}

	public static Mat getByMatCode(String matCode) {
		Map<String, Mat> matMap = getMatMap();
		return matMap.get(matCode);
	}

//	public static Map<String, ProductProcess> getProcessMap() {
//		Map temp = (Map) resultMap.get(PROCESS_MAP_KEY);
//		long oldTime = (Long) temp.get(TIME_KEY);
//		long nowTime = System.currentTimeMillis();
//		Map<String, ProductProcess> processMap = null;
//		if (temp.get(PROCESS_MAP_KEY) == null || (nowTime - oldTime) > expired_milliseconds) {
//			processMap = new HashMap<String, ProductProcess>();
//			ProductProcessService processService = (ProductProcessService) ContextUtils
//					.getBean(ProductProcessService.class);
//			List<ProductProcess> list = processService.getAll();
//			for (ProductProcess productProcess : list) {
//				processMap.put(productProcess.getId(), productProcess);
//			}
//			temp.put(PROCESS_MAP_KEY, processMap);
//			temp.put(TIME_KEY, System.currentTimeMillis());
//		} else {
//			processMap = (Map<String, ProductProcess>) temp.get(PROCESS_MAP_KEY);
//		}
//		return processMap;
//	}

	public static ProductProcess getProcessByProcessId(String processId) {
		ProductProcessService processService = (ProductProcessService) ContextUtils
 				.getBean(ProductProcessService.class);
		return processService.getById(processId);
	 
	}

	public static EquipInfo getMainEquipInfo(String equipLineCode) {
		Map temp = (Map) resultMap.get(MAIN_EQUIP_KEY);
		Long oldTime = (Long) temp.get(TIME_KEY);
		Long nowTime = System.currentTimeMillis();
		Map<String, EquipInfo> mainEquipCache = null;
		EquipInfo mainEquip = null;
		if (temp.get(MAIN_EQUIP_KEY) == null || (nowTime - oldTime) > expired_milliseconds) {
			mainEquipCache = new HashMap<String, EquipInfo>();
			EquipInfoService equipInfoService = (EquipInfoService) ContextUtils.getBean(EquipInfoService.class);
			mainEquip = equipInfoService.getMainEquipByEquipLine(equipLineCode);
			mainEquipCache.put(equipLineCode, mainEquip);
			temp.put(TIME_KEY, System.currentTimeMillis());
			temp.put(MAIN_EQUIP_KEY, mainEquipCache);
		} else {
			mainEquipCache = (Map<String, EquipInfo>) temp.get(MAIN_EQUIP_KEY);
			mainEquip = mainEquipCache.get(equipLineCode);
			if (mainEquip == null) {
				EquipInfoService equipInfoService = (EquipInfoService) ContextUtils.getBean(EquipInfoService.class);
				mainEquip = equipInfoService.getMainEquipByEquipLine(equipLineCode);
				mainEquipCache.put(equipLineCode, mainEquip);
			}
		}
		return mainEquip;
	}

	public static EquipMESWWMapping getEquipMESWWMapping(String tagName) {
		Map temp = (Map) resultMap.get(WW_MAPPING_KEY);
		Long oldTime = (Long) temp.get(TIME_KEY);
		Long nowTime = System.currentTimeMillis();
		Map<String, EquipMESWWMapping> equipMESWWMappingMap = null;
		if (temp.get(WW_MAPPING_KEY) == null || (nowTime - oldTime) > expired_milliseconds) {
			equipMESWWMappingMap = new HashMap<String, EquipMESWWMapping>();
			EquipMESWWMappingService equipMESWWMappingService = (EquipMESWWMappingService) ContextUtils
					.getBean(EquipMESWWMappingService.class);
			List<EquipMESWWMapping> list = equipMESWWMappingService.getAll();
			for (EquipMESWWMapping equipMESWWMapping : list) {
				equipMESWWMappingMap.put(equipMESWWMapping.getTagName(), equipMESWWMapping);
			}
			temp.put(TIME_KEY, System.currentTimeMillis());
			temp.put(WW_MAPPING_KEY, equipMESWWMappingMap);
		} else {
			equipMESWWMappingMap = (Map<String, EquipMESWWMapping>) temp.get(WW_MAPPING_KEY);
		}
		return equipMESWWMappingMap.get(tagName);
	}
	
	public static EquipMESWWMapping getEquipMESWWMapping(String equipCdoe,String parmCode) {
		Map temp = (Map) resultMap.get(EQUIP_MAPPING_KEY);
		Long oldTime = (Long) temp.get(TIME_KEY);
		Long nowTime = System.currentTimeMillis();
		Map<String, EquipMESWWMapping> equipMESWWMappingMap = null;
		if (temp.get(EQUIP_MAPPING_KEY) == null || (nowTime - oldTime) > expired_milliseconds) {
			equipMESWWMappingMap = new HashMap<String, EquipMESWWMapping>();
			EquipMESWWMappingService equipMESWWMappingService = (EquipMESWWMappingService) ContextUtils
					.getBean(EquipMESWWMappingService.class);
			List<EquipMESWWMapping> list = equipMESWWMappingService.getAll();
			for (EquipMESWWMapping equipMESWWMapping : list) {
				equipMESWWMappingMap.put(equipMESWWMapping.getEquipCode()+equipMESWWMapping.getParmCode(), equipMESWWMapping);
			}
			temp.put(TIME_KEY, System.currentTimeMillis());
			temp.put(EQUIP_MAPPING_KEY, equipMESWWMappingMap);
		} else {
			equipMESWWMappingMap = (Map<String, EquipMESWWMapping>) temp.get(EQUIP_MAPPING_KEY);
		}
		return equipMESWWMappingMap.get(equipCdoe+parmCode);
	}

	public static EquipInfo getLineEquipInfo(String equipCode) {
		Map temp = (Map) resultMap.get(EQUIP_LINE_KEY);
		Long oldTime = (Long) temp.get(TIME_KEY);
		Long nowTime = System.currentTimeMillis();
		Map<String, EquipInfo> lineEquipCache = null;
		EquipInfo equipLineInfo = null;
		if (temp.get(EQUIP_LINE_KEY) == null || (nowTime - oldTime) > expired_milliseconds) {
			lineEquipCache = new HashMap<String, EquipInfo>();
			EquipInfoService equipInfoService = (EquipInfoService) ContextUtils.getBean(EquipInfoService.class);
			equipLineInfo = equipInfoService.getEquipLineByEquip(equipCode);
			lineEquipCache.put(equipCode, equipLineInfo);
			temp.put(TIME_KEY, System.currentTimeMillis());
			temp.put(EQUIP_LINE_KEY, lineEquipCache);
		} else {
			lineEquipCache = (Map<String, EquipInfo>) temp.get(EQUIP_LINE_KEY);
			equipLineInfo = lineEquipCache.get(equipCode);
			if (equipLineInfo == null) {
				EquipInfoService equipInfoService = (EquipInfoService) ContextUtils.getBean(EquipInfoService.class);
				equipLineInfo = equipInfoService.getEquipLineByEquip(equipCode);
				lineEquipCache.put(equipCode, equipLineInfo);
			}
		}
		return equipLineInfo;
	}

	//termsCode=DATA_EMPLOYEE_CERTIFICATE("人员资质"),
	public static List<DataDic> getByTermsCode(String termsCode) {
		Map temp = (Map) resultMap.get(DATA_DIC_KEY);//类加载，在静态块中初始化了时间
		Long oldTime = (Long) temp.get(TIME_KEY);//同样在静态块中初始化了时间
		Long nowTime = System.currentTimeMillis();//当前时间
		Map<String, List<DataDic>> dataDicCache = null;//字典容器
		List<DataDic> dataDics = null;//数据字典
		//expired_milliseconds：数据过期时间，定为两小时
		if (temp.get(DATA_DIC_KEY) == null || (nowTime - oldTime) > expired_milliseconds) {
			dataDicCache = new HashMap<String, List<DataDic>>();
			DataDicService dataDicService = (DataDicService) ContextUtils.getBean(DataDicService.class);
			//dataDicService.getValidByTermsCode(termsCode)数据库xml文件去查询
			dataDics = dataDicService.getValidByTermsCode(termsCode);//人员资质
			dataDicCache.put(termsCode, dataDics);
			temp.put(TIME_KEY, System.currentTimeMillis());
			temp.put(DATA_DIC_KEY, dataDicCache);
		} else {
			dataDicCache = (Map<String, List<DataDic>>) temp.get(DATA_DIC_KEY);
			dataDics = dataDicCache.get(termsCode);
			if (dataDics == null) {
				DataDicService dataDicService = (DataDicService) ContextUtils.getBean(DataDicService.class);
				dataDics = dataDicService.getValidByTermsCode(termsCode);
				dataDicCache.put(termsCode, dataDics);
			}
		}
		return dataDics;
	}

	public static List<ProcessInOut> getByProcessId(String processId) {
		ProcessInOutService processInOutService = (ProcessInOutService) ContextUtils
				.getBean(ProcessInOutService.class);
		return processInOutService.getByProcessId(processId);
		
//		Map temp = (Map) resultMap.get(PROCESS_IN_OUT_KEY);
//		Long oldTime = (Long) temp.get(TIME_KEY);
//		Long nowTime = System.currentTimeMillis();
//		Map<String, List<ProcessInOut>> proInOutCache = null;
//		List<ProcessInOut> processInOuts = null;
//		if (temp.get(PROCESS_IN_OUT_KEY) == null || (nowTime - oldTime) > expired_milliseconds) {
//			proInOutCache = new HashMap<String, List<ProcessInOut>>();
//			ProcessInOutService processInOutService = (ProcessInOutService) ContextUtils
//					.getBean(ProcessInOutService.class);
//			List<ProcessInOut> allProcessInout = processInOutService.getAll();
//			for (ProcessInOut inOut : allProcessInout) {
//				processInOuts = proInOutCache.get(inOut.getProductProcessId());
//				if (processInOuts == null) {
//					processInOuts = new ArrayList<ProcessInOut>();
//					proInOutCache.put(inOut.getProductProcessId(), processInOuts);
//				}
//				if (inOut.getInOrOut() == InOrOut.OUT) {
//					processInOuts.add(0, inOut);
//				} else {
//					processInOuts.add(inOut);
//				}
//			}
//			temp.put(TIME_KEY, System.currentTimeMillis());
//			temp.put(PROCESS_IN_OUT_KEY, proInOutCache);
//		} else {
//			proInOutCache = (Map<String, List<ProcessInOut>>) temp.get(PROCESS_IN_OUT_KEY);
//		}
//		return proInOutCache.get(processId);
	}

	public static ProductCrafts getCrafts(String craftsId) {
		Map temp = (Map) resultMap.get(PROCESS_CRAFTS_MAP_KEY);
		Long oldTime = (Long) temp.get(TIME_KEY);
		Long nowTime = System.currentTimeMillis();
		Map<String, ProductCrafts> craftsCache = null;
		if (temp.get(PROCESS_CRAFTS_MAP_KEY) == null || (nowTime - oldTime) > expired_milliseconds) {
			craftsCache = new HashMap<String, ProductCrafts>();
			ProductCraftsService productCraftsService = (ProductCraftsService) ContextUtils
					.getBean(ProductCraftsService.class);
			List<ProductCrafts> allCrafts = productCraftsService.getAll();
			for (ProductCrafts productCrafts : allCrafts) {
				craftsCache.put(productCrafts.getId(), productCrafts);
			}
			temp.put(TIME_KEY, System.currentTimeMillis());
			temp.put(PROCESS_CRAFTS_MAP_KEY, craftsCache);
		} else {
			craftsCache = (Map<String, ProductCrafts>) temp.get(PROCESS_CRAFTS_MAP_KEY);
		}
		return craftsCache.get(craftsId);
	}

	public static DataDic getByColorCode(String colorCode) {
		List<DataDic> colors = getByTermsCode(TermsCodeType.DATA_PRODUCT_COLOR.name());
		if (colors == null) {
			return null;
		}
		for (DataDic dataDic : colors) {
			if (colorCode.equals(dataDic.getCode())) {
				return dataDic;
			}
		}
		return null;
	}

	public static Map getResultMap() {
		return resultMap;
	}

	public static void setResultMap(Map resultMap) {
		StaticDataCache.resultMap = resultMap;
	}

}
