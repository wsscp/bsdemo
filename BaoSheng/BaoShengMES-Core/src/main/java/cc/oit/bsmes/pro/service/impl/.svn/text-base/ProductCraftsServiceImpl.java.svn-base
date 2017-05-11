package cc.oit.bsmes.pro.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.CraftsImportUtils;
import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.common.util.JxlUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfacePLM.model.Scx;
import cc.oit.bsmes.interfacePLM.service.impl.PrcvServiceImpl;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pro.dao.ProductCraftsDAO;
import cc.oit.bsmes.pro.model.EqipListBz;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProcessInOutBz;
import cc.oit.bsmes.pro.model.ProcessQCEqip;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessQcBz;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductCraftsBz;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.model.ProductProcessBz;
import cc.oit.bsmes.pro.service.EqipListBzService;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProcessInOutBzService;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessQCEqipService;
import cc.oit.bsmes.pro.service.ProcessQcBzService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessBzService;
import cc.oit.bsmes.pro.service.ProductProcessService;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author LeiWei
 * @date 2013-12-25 下午2:25:02
 * @since
 * @version
 */
@Service
@Scope("prototype")
public class ProductCraftsServiceImpl extends BaseServiceImpl<ProductCrafts> implements ProductCraftsService {

	@Resource
	private ProductCraftsDAO productCraftsDAO;

	private static final int IS_CRAFTS_INDEX = 0;

	private static final int IS_PROCESS_INDEX = 2;

	private static final int IS_PROCESS_IN_OUT_INDEX = 4;

	private static final int IS_EQUIP_LIST_INDEX = 12;

	@Resource
	private ProductProcessService productProcessService;

	@Resource
	private ProductService productService;
	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private MatService matService;
	@Resource
	private EquipListService equipListService;
	@Resource
	private EquipInfoService equipInfoService;
	
	@Resource
	private ProcessReceiptService processReceiptService;
	
	/**
	 *记录本次QC ID 已经有的数据，防止重复
	 */
	private static Map<String ,String>  qcBZflagMap=new HashMap<String ,String> () ;
    /**
     * <p>删除qc ID存储map </p> 
     * @author zhangdongping
     * @date 2015-6-19 上午11:45:17
     * @see
     */
    public static void clearQcBZflagMap()
    {
    	qcBZflagMap.clear();
    }
	@Override
	public ProductCrafts getByProductCode(String productCode) {
		return productCraftsDAO.getByProductCode(productCode);
	}

	@Override
	public List<ProductCrafts> getLatest() {
		return productCraftsDAO.getLatest();
	}

	@Override
	public ProductCrafts getByCraftsCode(String craftsCode) {
		ProductCrafts findParams = new ProductCrafts();
		findParams.setCraftsCode(craftsCode);
		return productCraftsDAO.getOne(findParams);
	}

	@Override
	@Transactional(readOnly = false)
	public void importCrafts(Sheet sheet, String orgCode) {
		int maxRow = sheet.getRows();
		String productType = null;
		String productSpec = null;
		String productCode = null;
		ProductCrafts productCrafts = null;
		ProductProcess productProcess = null;
		ProcessInOut processIn = null;
		EquipList equipList = null;
		String processId = "";
		List<ProductProcess> list = new ArrayList<ProductProcess>();
		Map<String, List<EquipList>> equipMap = new HashMap<String, List<EquipList>>();
		Map<String, List<ProcessInOut>> processInMetMap = new HashMap<String, List<ProcessInOut>>();

		// 添加工装夹具
		insertEquipTools();
		// 查询工装夹具
		EquipInfo findParams = new EquipInfo();
		findParams.setType(EquipType.TOOLS);
		List<EquipInfo> equipTools = equipInfoService.getByObj(findParams);

		for (int i = 1; i < maxRow; i++) {
			if (i % 100 == 0) {
				logger.debug("工艺导入到第" + i + "行");
			}
			Cell[] cells = sheet.getRow(i);
			if (!isEmptyCell(cells[IS_CRAFTS_INDEX])) {
				if (!CollectionUtils.isEmpty(list)) {
					addJYProcess(list, equipMap);
					insertProductProcessAndMatInfo(list, equipMap, processInMetMap, equipTools);
					addProcessInoutAndMat(list, productCode, orgCode, processInMetMap);
				}
				list.clear();
				processInMetMap.clear();
				equipMap.clear();
				productType = JxlUtils.getRealContents(cells[IS_CRAFTS_INDEX]);
				productSpec = JxlUtils.getRealContents(cells[IS_CRAFTS_INDEX + 1]);
				productCode = productType + "(" + productSpec + ")";

				productCrafts = new ProductCrafts();
				productCrafts.setCraftsCode(productCode);
				productCrafts.setCraftsName("工艺" + productCode);
				productCrafts.setStartDate(new Date());
				productCrafts.setCraftsVersion(1);
				productCrafts.setProductCode(productCode);
				productCrafts.setOrgCode(orgCode);
				productCrafts.setIsDefault(true);
				productCraftsDAO.insert(productCrafts);

				Product product = new Product();
				product.setProductCode(productCode);
				product.setProductType(productType);
				product.setProductSpec(productSpec);
				product.setProductName("产品" + productCrafts.getProductCode());
				product.setCraftsCode(productCrafts.getCraftsCode());
				product.setOrgCode(productCrafts.getOrgCode());
				productService.insert(product);
			}
			// 判断这一行是是否是工序
			String seqStr = JxlUtils.getRealContents(cells[IS_PROCESS_INDEX]);
			if (StringUtils.isNotBlank(seqStr)) {
				String processName = JxlUtils.getRealContents(cells[IS_PROCESS_INDEX + 1]);
				String processCode = CraftsImportUtils.getProcessCode(processName);
				processId = UUID.randomUUID().toString();
				productProcess = new ProductProcess();
				productProcess.setId(processId);
				productProcess.setProductCraftsId(productCrafts.getId());
				productProcess.setSeq(Integer.parseInt(seqStr));
				productProcess.setProcessName(processName);
				productProcess.setProcessCode(processCode);
				DataDic dataDic = matService.getColorByName(JxlUtils
						.getRealContents(cells[IS_PROCESS_IN_OUT_INDEX + 5]));
				productProcess.setOutColor(dataDic == null ? "" : dataDic.getCode());
				String quantity = JxlUtils.getRealContents(cells[IS_PROCESS_IN_OUT_INDEX + 4]);
				productProcess.setQuantity(StringUtils.isBlank(quantity) ? 1.0 : Double.parseDouble(quantity));
				list.add(0, productProcess);
			}

			if (StringUtils.isNotBlank(JxlUtils.getRealContents(cells[IS_PROCESS_IN_OUT_INDEX]))) {
				processIn = new ProcessInOut();
				processIn.setProductProcessId(processId);
				for (int j = IS_PROCESS_IN_OUT_INDEX; j < IS_PROCESS_IN_OUT_INDEX + 8; j++) {
					setProperty(processIn, cells[j], j - IS_PROCESS_IN_OUT_INDEX);
				}
				cacheProcessIn(processIn, processId, processInMetMap);
			}

			if (StringUtils.isNotBlank(JxlUtils.getRealContents(cells[IS_EQUIP_LIST_INDEX]))) {
				equipList = new EquipList();
				equipList.setProcessId(processId);
				equipList.setType(EquipType.PRODUCT_LINE);
				for (int j = IS_EQUIP_LIST_INDEX; j < cells.length; j++) {
					setProperty(equipList, cells[j], j - IS_EQUIP_LIST_INDEX);
				}
				if ("LINE-VIRTUAL-JL".equalsIgnoreCase(equipList.getEquipCode())) {
					addProductLine(equipList, processId, equipMap);
				} else if ("LINE-PT".equalsIgnoreCase(equipList.getEquipCode())) {
					addProductLine(equipList, processId, equipMap);
				} else if ("LINE-VIRTUAL-PT".equalsIgnoreCase(equipList.getEquipCode())) {
					addProductLine(equipList, processId, equipMap);
				} else {
					cacheEquipList(equipList, processId, equipMap);
				}
			}

			if ((i + 1) == maxRow) {
				addJYProcess(list, equipMap);
				insertProductProcessAndMatInfo(list, equipMap, processInMetMap, equipTools);
				addProcessInoutAndMat(list, productCode, orgCode, processInMetMap);
			}
		}
	}

	private void addProductLine(EquipList equipList, String processId, Map<String, List<EquipList>> equipMap) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codeOrName", equipList.getEquipCode());
		param.put("orgCode",equipInfoService.getOrgCode());
		List<EquipInfo> equipInfos = equipInfoService.getEquipLine(param);
		for (EquipInfo equipInfo : equipInfos) {
			EquipList newEquipList = new EquipList();
			newEquipList.setProcessId(processId);
			newEquipList.setType(EquipType.PRODUCT_LINE);
			newEquipList.setEquipCode(equipInfo.getCode());
			newEquipList.setEquipCapacity(equipList.getEquipCapacity());
			newEquipList.setSetUpTime(equipList.getSetUpTime());
			newEquipList.setIsDefault(equipList.getIsDefault());
			newEquipList.setId(UUID.randomUUID().toString());
			cacheEquipList(newEquipList, processId, equipMap);
		}
	}

	private void addEquipTools(String processId, List<EquipInfo> equipTools) {
		Random random = new Random();
		EquipInfo equipInfo = equipTools.get(random.nextInt(4));
		EquipList equipList = new EquipList();
		equipList.setEquipCode(equipInfo.getCode());
		equipList.setProcessId(processId);
		equipList.setIsDefault(true);
		equipList.setType(EquipType.TOOLS);
		equipListService.insert(equipList);
	}

	private void addJYProcess(List<ProductProcess> list, Map<String, List<EquipList>> equipMap) {
		ProductProcess lastProcess = list.get(0);
		ProductProcess jyProcess = new ProductProcess();
		jyProcess.setId(UUID.randomUUID().toString());
		jyProcess.setProcessCode("JIANYAN");
		jyProcess.setProcessName("检验");
		jyProcess.setSeq(lastProcess.getSeq() + 1);
		jyProcess.setProductCraftsId(lastProcess.getProductCraftsId());
		jyProcess.setQuantity(1.0);
		list.add(0, jyProcess);

		Map<String, Object> param = new HashMap<String, Object>();
			param.put("codeOrName", "LINE-VIRTUAL-JY");
		param.put("orgCode",equipInfoService.getOrgCode());
		List<EquipInfo> equipInfos = equipInfoService.getEquipLine(param);
		List<EquipList> equipLists = new ArrayList<EquipList>();
		for (EquipInfo equipInfo : equipInfos) {
			EquipList equipList = new EquipList();
			equipList.setId(UUID.randomUUID().toString());
			equipList.setEquipCode(equipInfo.getCode());
			equipList.setProcessId(jyProcess.getId());
			equipList.setType(EquipType.PRODUCT_LINE);
			equipList.setIsDefault(true);
			equipList.setSetUpTime(3600);
			equipList.setEquipCapacity(100000.0);
			equipLists.add(equipList);
		}
		equipMap.put(jyProcess.getId(), equipLists);
	}

	private void cacheProcessIn(ProcessInOut processIn, String processId,
			Map<String, List<ProcessInOut>> processInMetMap) {
		List<ProcessInOut> processInOuts = processInMetMap.get(processId);
		if (processInOuts == null) {
			processInOuts = new ArrayList<ProcessInOut>();
		}
		processInOuts.add(processIn);
		processInMetMap.put(processId, processInOuts);
	}

	// 持久化工序
	private void insertProductProcessAndMatInfo(List<ProductProcess> list, Map<String, List<EquipList>> equipMap,
			Map<String, List<ProcessInOut>> processInMetMap, List<EquipInfo> equipTools) {
		Map<Integer, ProductProcess> cacheMap = new HashMap<Integer, ProductProcess>();
		ProductProcess lastProcess = null;
		int sqNum = 1000;
		for (ProductProcess productProcess : list) {
			ProductProcess nextProcess = cacheMap.get(productProcess.getSeq() + 1);
			sqNum = productProcess.getQuantity().intValue() * sqNum;
			if (nextProcess == null && cacheMap.size() == 0) {
				productProcess.setNextProcessId(WebConstants.ROOT_ID);
				productProcess.setFullPath(productProcess.getId() + ";");
			} else if (nextProcess == null) {
				productProcess.setNextProcessId(lastProcess.getId());
				productProcess.setFullPath(lastProcess.getFullPath() + productProcess.getId() + ";");
			} else {
				productProcess.setNextProcessId(nextProcess.getId());
				productProcess.setFullPath(nextProcess.getFullPath() + productProcess.getId() + ";");
			}
			productProcessService.insert(productProcess);
			cacheMap.put(productProcess.getSeq(), productProcess);

			List<ProcessInOut> inOutList = processInMetMap.get(productProcess.getId());
			if (inOutList != null) {
				for (ProcessInOut inOut : inOutList) {
					inOut.setQuantity(inOut.getQuantity() / sqNum);
				}
			}
			List<EquipList> equipLists = equipMap.get(productProcess.getId());
			if (equipLists != null) {
				for (EquipList equipList : equipLists) {
					EquipInfo equipInfo = equipInfoService.getByCode(equipList.getEquipCode(), "1");
					if (equipInfo == null) {
						equipInfo = new EquipInfo();
						equipInfo.setCode(equipList.getEquipCode());
						equipInfo.setName(equipList.getEquipCode());
						equipInfo.setStatus(EquipStatus.IDLE);
						equipInfo.setType(EquipType.PRODUCT_LINE);
						equipInfo.setOrgCode("1");
						equipInfo.setCenter("test");
						equipInfo.setFactory("test");
						equipInfoService.insert(equipInfo);
					}
					equipListService.insert(equipList);
				}
			}
			lastProcess = productProcess;

			addEquipTools(productProcess.getId(), equipTools);
		}
	}

	private void cacheEquipList(EquipList equipList, String processId, Map<String, List<EquipList>> equipMap) {
		List<EquipList> lists = equipMap.get(processId);
		if (lists == null) {
			lists = new ArrayList<EquipList>();
		}
		lists.add(equipList);
		equipMap.put(processId, lists);
	}

	// 添加工艺的输入鼠、
	private void addProcessInoutAndMat(List<ProductProcess> list, String productCode, String orgCode,
			Map<String, List<ProcessInOut>> processInMetMap) {
		Map<String, List<ProcessInOut>> matCodeMap = new HashMap<String, List<ProcessInOut>>();
		String nextProcessId = "";
		int index = 1;
		Mat mat = null;
		ProcessInOut out = null;
		for (int i = list.size() - 1; i >= 0; i--) {
			ProductProcess productProcess = list.get(i);
			List<ProcessInOut> inOutList = processInMetMap.get(productProcess.getId());
			if (inOutList != null) {
				for (ProcessInOut in : inOutList) {
					mat = matService.getByCode(in.getMatCode());
					if (mat == null) {
						mat = new Mat();
						mat.setMatCode(in.getMatCode());
						mat.setMatName(in.getMatName());
						//mat.setMatSpec(in.getMatSpec());
						mat.setSize(in.getSize());
						mat.setMatType(MatType.MATERIALS);
						mat.setTempletId("1");
						mat.setOrgCode(orgCode);
						matService.insert(mat);
					}
					insertInOut(productProcess, mat.getMatCode(), in.getQuantity(), InOrOut.IN);
				}
			}

			List<ProcessInOut> inList = matCodeMap.get(productProcess.getId());
			if (inList != null) {
				for (ProcessInOut processIn : inList) {
					insertInOut(productProcess, processIn.getMatCode(), processIn.getQuantity(), InOrOut.IN);
				}
			}

			mat = new Mat();
			mat.setColor(productProcess.getOutColor());
			if (StringUtils.equals(WebConstants.ROOT_ID, productProcess.getNextProcessId())) {
				mat.setMatCode(productCode);
				mat.setMatName("成品" + productCode);
				mat.setMatType(MatType.FINISHED_PRODUCT);
				mat.setTempletId("1");
				mat.setMatSpec("1*1");
				mat.setOrgCode(orgCode);
				matService.insert(mat);
			} else {
				mat.setMatCode(productCode + "-SEM-" + index);
				mat.setMatName("半成品" + (productCode + "-SEM-" + index));
				mat.setMatType(MatType.SEMI_FINISHED_PRODUCT);
				mat.setTempletId("1");
				mat.setOrgCode(orgCode);
				mat.setMatSpec("1*1");
				matService.insert(mat);
			}
			insertInOut(productProcess, mat.getMatCode(), 1.0, InOrOut.OUT);

			nextProcessId = productProcess.getNextProcessId();
			List<ProcessInOut> outList = matCodeMap.get(nextProcessId);
			if (outList == null) {
				outList = new ArrayList<ProcessInOut>();
			}
			out = new ProcessInOut();
			out.setMatCode(productCode + "-SEM-" + index);
			out.setQuantity(productProcess.getQuantity());
			outList.add(out);

			matCodeMap.put(nextProcessId, outList);
			index++;
		}
	}

	private void insertInOut(ProductProcess productProcess, String matCode, Double quality, InOrOut inOrOut) {
		ProcessInOut inOut = new ProcessInOut();
		inOut.setProductProcessId(productProcess.getId());
		inOut.setMatCode(matCode);
		inOut.setInOrOut(inOrOut);
		inOut.setQuantity(quality);
		processInOutService.insert(inOut);
	}

	private void setProperty(ProcessInOut processInOut, Cell cell, int index) {
		String contents = JxlUtils.getRealContents(cell);
		switch (index) {
		case 0:
			processInOut.setMatCode(contents);
			break;
		case 1:
			if (StringUtils.isNotBlank(contents)) {
				processInOut.setMatCode(processInOut.getMatCode() + "*" + contents);
				processInOut.setSize(contents);
			}
			break;
		case 2:
			processInOut.setMatName(contents);
			break;
		case 3:
			//processInOut.setMatSpec(contents);
			break;
		case 6:
			processInOut.setUnit(UnitType.getUnitType(contents));
			break;
		case 7:
			if (StringUtils.isNotBlank(contents)) {
				processInOut.setQuantity(Double.parseDouble(contents));
			}
			break;
		default:
			break;
		}
	}

	private void setProperty(EquipList equipList, Cell cell, int index) {
		String contents = JxlUtils.getRealContents(cell);
		switch (index) {
		case 0:
			equipList.setEquipCode(contents);
			break;
		case 1:
			equipList.setEquipCapacity(Double.parseDouble(contents) / 60);
			break;
		case 2:
			equipList.setSetUpTime(Integer.parseInt(contents) * 60);
			break;
		case 3:
			equipList.setIsDefault("是".equals(contents) || "".equals(contents));
			break;
		default:
			break;
		}
	}

	private void insertEquipTools() {
		// 工装夹具
		EquipInfo toolEquip = new EquipInfo();
		toolEquip.setCode("JICHUTOU");
		toolEquip.setName("挤出头");
		toolEquip.setType(EquipType.TOOLS);
		toolEquip.setStatus(EquipStatus.IDLE);
		equipInfoService.insert(toolEquip);

		toolEquip = new EquipInfo();
		toolEquip.setCode("LUOGAN");
		toolEquip.setName("螺杆");
		toolEquip.setType(EquipType.TOOLS);
		toolEquip.setStatus(EquipStatus.IDLE);
		equipInfoService.insert(toolEquip);

		toolEquip = new EquipInfo();
		toolEquip.setCode("JIANDAO");
		toolEquip.setName("剪刀");
		toolEquip.setType(EquipType.TOOLS);
		toolEquip.setStatus(EquipStatus.IDLE);
		equipInfoService.insert(toolEquip);

		toolEquip = new EquipInfo();
		toolEquip.setCode("CELIANGCHI");
		toolEquip.setName("测量尺");
		toolEquip.setType(EquipType.TOOLS);
		toolEquip.setStatus(EquipStatus.IDLE);
		equipInfoService.insert(toolEquip);
	}

	private boolean isEmptyCell(Cell cell) {
		return JxlUtils.getRealContents(cell).isEmpty();
	}

	/**
	 * <p>
	 * 查询条件->工艺信息下拉框：支持模糊查询
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<ProductCrafts>
	 * @see
	 */
	@Override
	public List<ProductCrafts> craftsCombo(String query) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codeOrName", "%" + query + "%");
		return productCraftsDAO.findByCodeOrName(param);
	}

	@Override
	public List<ProductCrafts> findForExport(JSONObject queryFilter) throws InvocationTargetException,
			IllegalAccessException, NoSuchMethodException {
		ProductCrafts findParams = (ProductCrafts) JSONUtils.jsonToBean(queryFilter, ProductCrafts.class);
		return productCraftsDAO.find(findParams);
	}

	@Override
	public int countForExport(JSONObject queryParams) throws InvocationTargetException, IllegalAccessException,
			NoSuchMethodException {
		ProductCrafts findParams = (ProductCrafts) JSONUtils.jsonToBean(queryParams, ProductCrafts.class);
		return productCraftsDAO.count(findParams);
	}

	/**
	 * <p>
	 * 获取产品的可选工艺列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @param productCode 产品编码
	 * @return List<ProductCrafts>
	 */
	@Override
	public List<ProductCrafts> getChooseCraftsArray(String productCode) {
		return productCraftsDAO.getChooseCraftsArray(productCode);
	}

	/**
	 * ****************************************************************<br/>
	 * 工艺实例化 <br/>
	 * ****************************************************************
	 * */

	/**
	 * 前台传入的修改的工艺的工序信息
	 * */
	private Map<String, ProductProcessBz> editProcessBzMapCache = new HashMap<String, ProductProcessBz>();
	/**
	 * 工序ID与所包含的合并工序对象的映射关系
	 * */
	private Map<String, List<ProductProcessBz>> processBzMapCache = new HashMap<String, List<ProductProcessBz>>();
	
	/**
	 * 工序ID与输入工序的关系 :ProductProcess 用来保存新的ID、NEXT_PROCESS_ID、FULL_PATH这三个参数
	 * */
	public Map<String, ProductProcess> processArrayCache = new HashMap<String, ProductProcess>();

	@Resource
	private EquipMESWWMappingService equipMESWWMappingService;
	/**
	 * 标准库
	 * */
	@Resource
	private ProductProcessBzService productProcessBzService;
	@Resource
	private ProcessInOutBzService processInOutBzService;
	@Resource
	private EqipListBzService eqipListBzService;
	@Resource
	private ProcessQcBzService processQcBzService;

	/**
	 * 实例库
	 * */
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private ProcessQCEqipService processQCEqipService;

	/**
	 * 数据拷贝入库：工艺信息<br/>
	 * 1、查询出产品的标准工艺信息；<br/>
	 * 2、初始化工艺信息：工序与合并工序的map映射关系；<br/>
	 * 3、复制到实表；<br/>
	 * 4、复制工艺下工序信息到实例表；<br/>
	 * 
	 * @author DingXintao
	 * @param craftsId 工艺ID/主键
	 * @param craftsCname 工艺别名
	 * @param productCraftsBz 工艺信息
	 * @param editProcessBzArray 编辑的工序列表
	 * @param isDefault 是否默认
	 * @return
	 * */
	@Override
	@Transactional(readOnly = false)
	public void instanceCrafts(String craftsId, String craftsCname, ProductCraftsBz productCraftsBz,
			List<ProductProcessBz> editProcessBzArray, boolean isDefault) {
		// 清空内存中的数据
		editProcessBzMapCache.clear();
		processBzMapCache.clear();
		processArrayCache.clear();
		//qcBZflagMap.clear();

		if (null != editProcessBzArray) {
			for (ProductProcessBz processBz : editProcessBzArray) {
				editProcessBzMapCache.put(processBz.getId(), processBz);
			}
		}

		this.initBzProcessMapCache(productCraftsBz.getId()); // 初始化该工艺下的工序信息

		// 复制属性
		ProductCrafts productCrafts = new ProductCrafts();
		BeanUtils.copyProperties(productCraftsBz, productCrafts);

		productCrafts.setId(craftsId);
		productCrafts.setCraftsCname(craftsCname); // 添加工艺别名
		productCrafts.setIsDefault(isDefault); // 标识非默认工艺
		productCraftsDAO.insert(productCrafts); // 见3
		productCraftsDAO.insertCaftsR(productCrafts.getProductCode(),productCrafts.getId());

		this.instanceProcess(productCraftsBz.getId(), productCrafts.getId()); // 复制工艺下的工序信息

	}

	/**
	 * 数据拷贝入库：工序信息
	 * 
	 * @author DingXintao
	 * @param craftsBzId 工艺ID
	 * @param newCraftsBzId 新实例工艺ID
	 * @return boolean
	 * */
	private boolean instanceProcess(String craftsBzId, String newCraftsBzId) {
		List<ProductProcessBz> processBzArray = productProcessBzService.getByProductCraftsIdAsc(craftsBzId);
		if (!CollectionUtils.isEmpty(processBzArray)) { 
			
			for (ProductProcessBz productProcessBz : processBzArray) {
				if(productProcessBz.getSameProductLine()){
					continue;
				}
				String processId = productProcessBz.getId();
				// 从初始化好的工序缓存中获取工序
				List<ProductProcessBz> processArray = processBzMapCache.get(processId);
				if (null != processArray) { // 存在工序复制入库
					// 复制属性
					List<ProcessQc> processQcResult=new ArrayList<ProcessQc>();
					List<ProcessQCEqip> processQCEqipListResult=new ArrayList<ProcessQCEqip>();
					List<ProcessInOut> processInOutResult=new ArrayList<ProcessInOut>();
					List<EquipList> equipListResult= new  ArrayList<EquipList>();
					List<ProcessReceipt> receiptListResult =new ArrayList<ProcessReceipt>() ;
					ProductProcess productProcess = new ProductProcess();
					BeanUtils.copyProperties(productProcessBz, productProcess);

					ProductProcess processSL = processArrayCache.get(processId);
					productProcess.setId(processSL.getId());
					productProcess.setNextProcessId(processSL.getNextProcessId());
					productProcess.setFullPath(processSL.getFullPath());
					productProcess.setProductCraftsId(newCraftsBzId);
					productProcessService.insert(productProcess);

					this.instanceProcessInOut(productProcess.getId(), processArray,processInOutResult); // 工序投入产出
					List<String> equipCodeArray = this
							.instanceEqipList(processId, productProcess.getId(), processArray,equipListResult,receiptListResult); // 工序设备清单
					this.instanceProcessQc(productProcess.getId(), processArray, equipCodeArray,processQcResult,processQCEqipListResult); // 工艺参数
					
					
					//同步工艺参数 1 ，EQIP_LIST_ID ，EQUIP_CODE
				 

					//this.instanceProcessReceipt(newProcessId, processArray, equipCodeArray);
					
//					if(!CollectionUtils.isEmpty(processInOutResult))
//					{
//						processInOutService.insertBatch(processInOutResult); 
//					}
					if(!CollectionUtils.isEmpty(equipListResult)){
						equipListService.insertBatch(equipListResult);
					}
					 
//					if(!CollectionUtils.isEmpty(processQcResult)){
//						processQcService.insertBatch(processQcResult);
//					}
					
					// TODO Auto-generated method stub
					if(!CollectionUtils.isEmpty(receiptListResult)){
						processReceiptService.insertBatchInterface(receiptListResult);
					}
					
					if(!CollectionUtils.isEmpty(processQCEqipListResult)){
						processQCEqipService.insertBatch(processQCEqipListResult);
					}
				}
					
			} 
			
			 			
			
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 数据拷贝入库：工序投入产出<br/>
	 * 特殊处理(从最后往前)：将合并的第一道产出作为产出，最后合并的投入作为投入，中间的忽略
	 * 
	 * 
	 * @author DingXintao
	 * @param newProcessId 新实例工序ID
	 * @param processArray 合并的工序列表
	 * @return boolean
	 * */
	private boolean instanceProcessInOut(String newProcessId, List<ProductProcessBz> processArray,List<ProcessInOut> processInOutResult) {
		List<ProcessInOutBz> processInOutBzArray = new ArrayList<ProcessInOutBz>();

		if (processArray.size() > 1) { // 说明有多个工序合并
			// 顺序取产出，取到就结束，合并的取合并列表中最后一道的输出为产出
			processBz: for (ProductProcessBz process : processArray) {
				List<ProcessInOutBz> out = processInOutBzService.getByProcessId(process.getId());
				for (ProcessInOutBz inout : out) {
					if (inout.getInOrOut() == InOrOut.OUT) {
						processInOutBzArray.add(inout);
						break processBz;
					}
				}
			}

			List<ProcessInOutBz> in = processInOutBzService.getByProcessId(processArray.get(processArray.size() - 1)
					.getId());
			for (ProcessInOutBz inout : in) {
				if (inout.getInOrOut() == InOrOut.IN) {
					processInOutBzArray.add(inout);
				}
			}
		} else if (processArray.size() == 1) {
			processInOutBzArray.addAll(processInOutBzService.getByProcessId(processArray.get(0).getId()));
		}

		for (ProcessInOutBz processInOutBz : processInOutBzArray) {
			// 复制属性
			ProcessInOut processInOut = new ProcessInOut();
			BeanUtils.copyProperties(processInOutBz, processInOut);

			processInOut.setId(UUID.randomUUID().toString());
			processInOut.setProductProcessId(newProcessId);
			processInOutService.insert(processInOut);
//			processInOutResult.add(processInOut);
		}
		return true;
	}

	/**
	 * 数据拷贝入库：工序设备清单
	 * 
	 * @author DingXintao
	 * @param processId 工序ID
	 * @param newProcessId 新实例工序ID
	 * @param processArray 合并的工序列表
	 * @return List<String> 工序生产线清单
	 * */
	private List<String> instanceEqipList(String processId, String newProcessId, List<ProductProcessBz> processArray,List<EquipList> equipListResult,List<ProcessReceipt> receiptListResult ) {
		List<String> equipCodeArray = new ArrayList<String>();

		List<EqipListBz> eqipListBzArray = eqipListBzService.getByProcessId(processId);
		Map<String,Scx> tempMap=new HashMap<String,Scx>();
		
		List<Scx> scxList=PrcvServiceImpl.scxMapCache.get(processId);
		if(scxList!=null)
		{
			for(int i=0;i<scxList.size();i++)
			{
				Scx scx=scxList.get(i);
				tempMap.put(scx.getNo(), scx);
			}
		}
		
		// 不合并生产线了
		// for (ProductProcessBz productProcessBz : processArray) {
		// eqipListBzArray.addAll(eqipListBzService
		// .getByProcessId(productProcessBz.getId()));
		// }

		Set<String> moveRepeat = new HashSet<String>(); // 去除重复
		for (EqipListBz eqipListBz : eqipListBzArray) {
			if (!moveRepeat.add(eqipListBz.getEquipCode())) {
				continue;
			}
			// 复制属性
			EquipList equipList = new EquipList();
			BeanUtils.copyProperties(eqipListBz, equipList);

			equipList.setId(UUID.randomUUID().toString());
			equipList.setProcessId(newProcessId);
			equipList
					.setIsDefault(this.isDefaultEquip(processId, eqipListBz.getEquipCode(), eqipListBz.getIsDefault()));
			//equipListService.insert(equipList);
			equipListResult.add(equipList);
			// 添加设备工艺参数 
			equipCodeArray.add(equipList.getEquipCode());			
			Scx scx=tempMap.get(eqipListBz.getEquipCode());
			if(scx!=null)
			{
				List<ProcessReceipt> receiptList=processReceiptService.updateEquipQc(equipList.getId(), scx.getId(), eqipListBz.getEquipCode()); 
				receiptListResult.addAll(receiptList);
			}
			
			
		}
		return equipCodeArray;
	}

	/**
	 * 数据拷贝入库：工艺参数
	 * 
	 * @author DingXintao
	 * @param newProcessId 新实例工序ID
	 * @param processArray 合并的工序列表
	 * @param equipCodeArray 工序生产线设备编码code
	 * @return boolean
	 * */
	private boolean instanceProcessReceipt(String newProcessId, List<ProductProcessBz> processArray,
			List<String> equipCodeArray) {

		for (String equipCode : equipCodeArray) {
			EquipInfo equipInfo = StaticDataCache.getMainEquipInfo(equipCode);
			if (null == equipInfo) {
				continue;
			}
			EquipMESWWMapping equipMESWWMapping = new EquipMESWWMapping();
			equipMESWWMapping.setEquipCode(equipInfo.getCode());
			List<EquipMESWWMapping> mappingArray = equipMESWWMappingService.findByObj(equipMESWWMapping);
			for (EquipMESWWMapping mapping : mappingArray) {

//				ProcessReceipt processReceipt = new ProcessReceipt();
//				processReceipt.setAcEquipCode(acEquipCode);
//				processReceipt.setReceiptCode(JxlUtils.getRealContents(cells[1]));
//				processReceipt.setReceiptName(JxlUtils.getRealContents(cells[2]));
//				processReceipt.setSubReceiptCode(JxlUtils.getRealContents(cells[1]));
//				processReceipt.setSubReceiptName(JxlUtils.getRealContents(cells[2]));
//				processReceipt.setReceiptTargetValue(JxlUtils.getRealContents(cells[3]));
//				processReceipt.setReceiptMaxValue(JxlUtils.getRealContents(cells[4]));
//				processReceipt.setReceiptMinValue(JxlUtils.getRealContents(cells[5]));
//				String needDA = JxlUtils.getRealContents(cells[6]);
//				processReceipt.setNeedDa(true);
//				processReceipt.setNeedAlarm(true);
//				processReceipt.setNeedIs(true);
//				processReceipt.setDataType("BOOLEAN");
//				processReceipt.setDataUnit(JxlUtils.getRealContents(cells[9]));
//				processReceipt.setCreateTime(new Date());
//				processReceipt.setModifyTime(new Date());
//				processReceipt.setCreateUserCode("admin");
//				processReceipt.setModifyUserCode("admin");
//				processReceipt.setHasPic(false);
//				processReceipt.setNeedShow("是".equals(JxlUtils.getRealContents(cells[14])));
//				processReceipt.setFrequence(10.0);
//
//				processReceipt.setEqipListId(eqp.getId());
//				processReceipt.setEquipCode(eqp.getEquipCode());
//				processReceipt.setNeedShow(StringUtils.isNotBlank(processReceipt.getReceiptTargetValue()));
//
//				ProcessReceipt findParam = new ProcessReceipt();
//				findParam.setReceiptCode(processReceipt.getReceiptCode());
//				findParam.setEquipCode(processReceipt.getEquipCode());
//				findParam.setEqipListId(processReceipt.getEqipListId()); // 工序使用设备清单ID
//				// 判断是否存在了，是就update，否就insert
//				List<ProcessReceipt> processReceiptList = this.getByObj(findParam);
//				if (null == processReceiptList || processReceiptList.size() == 0) {
//					processReceipt.setId(null);
//					processReceiptService.insert(processReceipt);
//				} else {
//					findParam = processReceiptList.get(0);
//					processReceipt.setId(findParam.getId());
//					processReceiptService.update(processReceipt);
//				}
			}
		}

		return true;
	}

	/**
	 * 数据拷贝入库：质量参数
	 * 
	 * @author DingXintao
	 * @param newProcessId 新实例工序ID
	 * @param processArray 合并的工序列表
	 * @param equipCodeArray 工序生产线设备编码code
	 * @return boolean
	 * */
	private boolean instanceProcessQc(String newProcessId, List<ProductProcessBz> processArray,
			List<String> equipCodeArray,List<ProcessQc> processQcResult,List<ProcessQCEqip> processQCEqipListResult) {
		List<ProcessQcBz> processQcBzArray = new ArrayList<ProcessQcBz>();
		List<String> processIds= new ArrayList<String>();
		boolean returnFlag=false;
		for (ProductProcessBz productProcessBz : processArray) {
			List<ProcessQcBz> qcbz = processQcBzService.getByProcessId(productProcessBz.getId());
			for (ProcessQcBz object : qcbz) {
				String id=object.getId();
				if(qcBZflagMap.get(id)==null){
					qcBZflagMap.put(id, id);
				}else{
					returnFlag=true; 
					return false;
				} 
			}			  
			processQcBzArray.addAll(qcbz);
			processIds.add(productProcessBz.getId());
		}
		
		if(returnFlag){
			return false;
		}
		//processQcService.insertBackGround(newProcessId,processIds);

		Set<String> moveRepeat = new HashSet<String>(); // 去除重复
		for (ProcessQcBz processQcBz : processQcBzArray) {
			if (!moveRepeat.add(processQcBz.getCheckItemCode())) {
				continue;
			}
			// 复制属性
			 ProcessQc processQc = new ProcessQc();
			 BeanUtils.copyProperties(processQcBz, processQc);

			 processQc.setId(UUID.randomUUID().toString());
			 processQc.setProcessId(newProcessId);	
			 processQcService.insert(processQc);
//			 processQcResult.add(processQc);

			for (String equipCode : equipCodeArray) {
				EquipInfo equipInfo = StaticDataCache.getMainEquipInfo(equipCode);
				if (null == equipInfo) {
					continue;
				}
				ProcessQCEqip processQCEqip = new ProcessQCEqip();
				processQCEqip.setId(UUID.randomUUID().toString());
				processQCEqip.setQcId(processQcBz.getId());
				processQCEqip.setEquipCode(equipInfo.getCode());
				processQCEqip.setEquipId(equipInfo.getId());
				//processQCEqipService.insert(processQCEqip);
				processQCEqipListResult.add(processQCEqip);
			}
		}

		return true;
	}

	/**
	 * 工艺下工序<br/>
	 * 初始化ProcessMapCache缓存:<String id, Process process>
	 * 
	 * @author DingXintao
	 * @param prcvId 工艺ID
	 * @return
	 */
	private void initBzProcessMapCache(String prcvId) {
		List<ProductProcessBz> processList = productProcessBzService.getLastProcessList(prcvId); // 标准工艺最后一道工序
		putBzProcessToMapCache(processList, "-1", new StringBuffer(), new ArrayList<ProductProcessBz>());
	}

	/**
	 * 递归调用处理工序：与上一道工序同一生产线
	 * 
	 * @author DingXintao
	 * @param processList 此次同等级工序列表
	 * @param fullPath 工序路径
	 * @param sameProcess 合并的工序列表
	 * @return
	 */
	private void putBzProcessToMapCache(List<ProductProcessBz> processList, String nextProcessId,
			StringBuffer fullPath, List<ProductProcessBz> sameProcess) {
		for (ProductProcessBz process : processList) {
			StringBuffer path = processList.size() > 1 ? new StringBuffer(fullPath) : fullPath;
			sameProcess.add(process);
			// 获取上一道工序信息
			List<ProductProcessBz> prvProcessList = productProcessBzService.getPrvProcessList(process.getId());
			if (this.isSameProductLine(process.getId(), process.getSameProductLine())) { // 表示当前工序为与上一道工序同一生产线，递归完成这一条工序
				putBzProcessToMapCache(prvProcessList, nextProcessId, path, prvProcessList.size() == 1 ? sameProcess
						: new ArrayList<ProductProcessBz>(sameProcess));
				continue;
			} else {
				String processId = UUID.randomUUID().toString();
				path.append(processId).append(";");

				ProductProcess processSL = new ProductProcess();
				processSL.setId(processId);
				processSL.setNextProcessId(nextProcessId);
				processSL.setFullPath(path.toString());
				processArrayCache.put(process.getId(), processSL);

				processBzMapCache.put(process.getId(), new ArrayList<ProductProcessBz>(sameProcess));
				sameProcess.clear();
				putBzProcessToMapCache(prvProcessList, processId, path, prvProcessList.size() == 1 ? sameProcess
						: new ArrayList<ProductProcessBz>(sameProcess));
			}
		}
	}

	/**
	 * 是否与上道同一生产线：判断是否修改，修改则返回修改后的值，否则返回原始值
	 * 
	 * @author DingXintao
	 * @param processId 工序ID
	 * @param sameProductLine 是否与上道同一生产线
	 * @return boolean
	 */
	private boolean isSameProductLine(String processId, boolean sameProductLine) {
		ProductProcessBz processBz = editProcessBzMapCache.get(processId);
		if (null == processBz) {
			return sameProductLine;
		} else {
			return processBz.getSameProductLine();
		}
	}

	/**
	 * 判断是否默认设备
	 * 
	 * @author DingXintao
	 * @param processId 工序ID
	 * @param equipCode 设备编码
	 * @param isDefault 标准默认值
	 * @return boolean
	 * */
	private boolean isDefaultEquip(String processId, String equipCode, boolean isDefault) {
		// 获取修改的标准工序：获取设备信息
		ProductProcessBz processBz = editProcessBzMapCache.get(processId);
		if (null == processBz) {
			return isDefault;
		}
		List<String> equipCodeArray = Arrays.asList(processBz.getEquipCodeArray().split(","));
		if (null == equipCodeArray) {
			return isDefault;
		} else if (equipCodeArray.contains(equipCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * 获取最近的一个该产品的订单所使用的工艺对象
	 * </p>
	 * 
	 * @author DingXintao
	 * @param productCode 产品编码
	 * @return ProductCrafts
	 */
	@Override
	public ProductCrafts getLastOrderUserdCrafts(String productCode) {
		return productCraftsDAO.getLastOrderUserdCrafts(productCode);
	}

	@Override
	public String getCraftIdByProductCode(String productCode) {
		return productCraftsDAO.getCraftIdByProductCode(productCode);
	}
	
	/**
	 * 调用函数查看工艺是否有效：包括工序是否连贯，是否没道工序都有投入产出，产出投入是否连贯，产出是否有且只有一个，是否都有生产线
	 * 
	 * @param craftsId 工艺ID
	 * @return 1:合法, 0:不合法
	 * */
	public String validateProductCrafts(String craftsId){
		return productCraftsDAO.validateProductCrafts(craftsId);
	}

}
