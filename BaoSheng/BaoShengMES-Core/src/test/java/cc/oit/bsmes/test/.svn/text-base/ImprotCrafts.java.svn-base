package cc.oit.bsmes.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessService;

public class ImprotCrafts extends BaseTest {

	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private EquipListService equipListService;

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// }

	private static final int craftsIndex = 0;
	private static final int processIndex = 1;
	private static final int inIndex = 2;
	private static final int middleIndex = 3;
	private static final int outIndex = 4;
	private static final int inoutIndex = 5;
	private static final int equipIndex = 6;
	private static Map<String, ProductCrafts> craftsMapCache = new HashMap<String, ProductCrafts>();
	private static Map<String, ProductProcess> processMapCache = new HashMap<String, ProductProcess>();

	private static ProductCrafts craftsCache = new ProductCrafts();
	private static List<ProductProcess> processArrayCache = new ArrayList<ProductProcess>();

	private String[] sheetNameArray = { "工艺信息", "工艺工序", "上车检", "中检", "下车检", "投入产出清单", "使用设备清单" };
	private String[] craftsColumnArray = { "工艺代码", "工艺名称", "工艺版本", "工艺生效时间", "工艺失效时间", "产品代码", "是否默认工艺" };
	private String[] processColumnArray = { "工艺代码", "工艺名称", "工序代码", "工序名称", "加工顺序", "下一道工序", "是否与上一道工序同一生产线", "是否可选",
			"是否默认跳过" };
	private String[] inColumnArray = { "工艺代码", "工艺名称", "工序代码", "工序名称", "检测项CODE", "检测项名称", "检测频率", "参数目标值", "参数上限",
			"参数下限", "参数数据类型", "参数单位", "是否有附件", "是否在终端显示", "是否重点显示" };
	private String[] middleColumnArray = { "工艺代码", "工艺名称", "工序代码", "工序名称", "检测项CODE", "检测项名称", "检测频率", "参数目标值", "参数上限",
			"参数下限", "参数数据类型", "参数单位", "是否有附件", "是否在终端显示", "是否重点显示" };
	private String[] outColumnArray = { "工艺代码", "工艺名称", "工序代码", "工序名称", "检测项CODE", "检测项名称", "检测频率", "参数目标值", "参数上限",
			"参数下限", "参数数据类型", "参数单位", "是否有附件", "是否在终端显示", "是否重点显示" };
	private String[] inoutColumnArray = { "工艺代码", "工艺名称", "工序代码", "工序名称", "物料代码", "物料名称", "投入/产出", "颜色", "规格",
			"单位投入用量", "单位投入用量计算公式", "用量单位", "用法", "描述" };
	private String[] equipColumnArray = { "工艺代码", "工艺名称", "工序代码", "工序名称", "设备代码", "设备名称", "设备类型", "设备能力", "前置时间",
			"后置时间" };

	@Test
	@Rollback(true)
	public void exportExcel() throws IOException {
		List<ProductCrafts> craftsArray = productCraftsService.getAll(); // 查询所有工艺
		for (ProductCrafts crafts : craftsArray) {
			// 将工艺放入缓存 -- 总缓存
			if (null == craftsMapCache.get(crafts.getId())) {
				craftsMapCache.put(crafts.getId(), crafts);
			}
			// 将工艺放入缓存 -- 当前工艺
			craftsCache = crafts;

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet;
			List<?> objectArray;
			for (int i = 0; i < sheetNameArray.length; i++) {
				sheet = wb.createSheet(sheetNameArray[i]);
				objectArray = queryData(i);
				writeSheet(sheet, i, objectArray);
			}
			outToFile(wb, crafts.getCraftsCode());
		}
	}

	private void outToFile(HSSFWorkbook wb, String fileName) throws IOException {
		File file = new File("d:\\" + fileName + "_" + new Date().getTime() + ".xls");
		if (!file.exists()) {
			file.createNewFile();
		}
		// 导出文件
		FileOutputStream fout = new FileOutputStream(file);
		wb.write(fout);
		fout.close();
	}

	/**
	 * 查询数据
	 * */
	private List<?> queryData(int i) {
		switch (i) {
		case craftsIndex:
			List<ProductCrafts> craftsArray = new ArrayList<ProductCrafts>();
			craftsArray.add(craftsCache);
			return craftsArray;
		case processIndex:
			return queryProcessData();
		case inIndex:
			return queryQcData(i);
		case middleIndex:
			return queryQcData(i);
		case outIndex:
			return queryQcData(i);
		case inoutIndex:
			return queryInoutData();
		case equipIndex:
			return queryEquipData();
		default:
			return null;
		}
	}

	/**
	 * 获取工序数据
	 * */
	private List<?> queryProcessData() {
		List<ProductProcess> processArray = productProcessService.getByProductCraftsId(craftsCache.getId());
		// 将工序信息放入缓存 -- 总缓存
		for (ProductProcess process : processArray) {
			if (null == processMapCache.get(process.getId())) {
				processMapCache.put(process.getId(), process);
			}
		}
		// 将工序信息放入缓存 -- 当前工艺
		processArrayCache = processArray;
		return processArray;
	}

	/**
	 * 获取上/中/下检验数据
	 * */
	private List<?> queryQcData(int index) {
		List<ProcessQc> backDataArray = new ArrayList<ProcessQc>();
		for (int i = 0; i < processArrayCache.size(); i++) {
			ProductProcess process = processArrayCache.get(i);
			ProcessQc findParams = new ProcessQc();
			findParams.setProcessId(process.getId());
			switch (i) {
			case inIndex:
				findParams.setNeedInCheck("1");
				break;
			case middleIndex:
				findParams.setNeedMiddleCheck("1");
				break;
			case outIndex:
				findParams.setNeedOutCheck("1");
				break;
			}
			List<ProcessQc> qcArray = processQcService.getByObj(findParams);
			backDataArray.addAll(qcArray);
		}
		return backDataArray;
	}

	/**
	 * 获取投入产出数据
	 * */
	private List<?> queryInoutData() {
		List<ProcessInOut> backDataArray = new ArrayList<ProcessInOut>();
		for (int i = 0; i < processArrayCache.size(); i++) {
			ProductProcess process = (ProductProcess) processArrayCache.get(i);
			List<ProcessInOut> inoutArray = processInOutService.getByProcessId(process.getId());
			backDataArray.addAll(inoutArray);
		}
		return backDataArray;
	}

	/**
	 * 获取设备清单数据
	 * */
	private List<?> queryEquipData() {
		List<EquipList> backDataArray = new ArrayList<EquipList>();
		for (int i = 0; i < processArrayCache.size(); i++) {
			ProductProcess process = (ProductProcess) processArrayCache.get(i);
			List<EquipList> equipArray = equipListService.getByProcessId(process.getId());
			backDataArray.addAll(equipArray);
		}
		return backDataArray;
	}

	private void writeSheet(HSSFSheet sheet, int i, List<?> dataArray) {
		if (null == dataArray) {
			return;
		}
		switch (i) {
		case craftsIndex:
			data(sheet, craftsColumnArray, dataArray);
			break;
		case processIndex:
			data(sheet, processColumnArray, dataArray);
			break;
		case inIndex:
			data(sheet, inColumnArray, dataArray);
			break;
		case middleIndex:
			data(sheet, middleColumnArray, dataArray);
			break;
		case outIndex:
			data(sheet, outColumnArray, dataArray);
			break;
		case inoutIndex:
			data(sheet, inoutColumnArray, dataArray);
			break;
		case equipIndex:
			data(sheet, equipColumnArray, dataArray);
			break;
		default:
			break;
		}
	}

	/**
	 * 写数据
	 * */
	private void data(HSSFSheet sheet, String[] columnArray, List<?> dataArray) {
		int rowNum = 0; // 行号
		// 创建title行
		HSSFRow row = sheet.createRow(rowNum);
		rowNum++;
		HSSFCell cell = null;
		for (int i = 0; i < columnArray.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(columnArray[i]);
		}

		for (int i = 0; i < dataArray.size(); i++) {
			row = sheet.createRow(rowNum);
			rowNum++;

			if (dataArray.get(i) instanceof ProductCrafts) { // 工艺
				ProductCrafts crafts = (ProductCrafts) dataArray.get(i);
				setDataToRow(row, crafts.getCraftsCode(), crafts.getCraftsName(),
						String.valueOf(crafts.getCraftsVersion()), formatTime(crafts.getStartDate()),
						formatTime(crafts.getEndDate()), crafts.getProductCode(), crafts.getIsDefaultText());
			} else if (dataArray.get(i) instanceof ProductProcess) { // 工序
				ProductProcess process = (ProductProcess) dataArray.get(i);
				ProductCrafts crafts = craftsMapCache.get(process.getProductCraftsId());
				if (null == crafts) {
					continue;
				}
				setDataToRow(row, crafts.getCraftsCode(), crafts.getCraftsName(), process.getProcessCode(),
						process.getProcessName(), String.valueOf(process.getSeq()), process.getNextProcessName(),
						process.getSameProductLineText(), process.getIsOptionText(), process.getIsDefaultSkipText());
			} else if (dataArray.get(i) instanceof ProcessQc) { // 质量参数
				ProcessQc qc = (ProcessQc) dataArray.get(i);
				ProductProcess process = processMapCache.get(qc.getProcessId());
				ProductCrafts crafts = craftsMapCache.get(process.getProductCraftsId());
				if (null == process || null == crafts) {
					continue;
				}
				setDataToRow(row, crafts.getCraftsCode(), crafts.getCraftsName(), process.getProcessCode(),
						process.getProcessName(), qc.getCheckItemCode(), qc.getCheckItemName(), String.valueOf(qc
								.getFrequence()), qc.getItemTargetValue(), qc.getItemMaxValue(), qc.getItemMinValue(),
						qc.getDataType().toString(), qc.getDataUnit(), "1".equals(qc.getHasPic()) ? "是" : "否",
						"1".equals(qc.getNeedShow()) ? "是" : "否", "1".equals(qc.getEmphShow()) ? "是" : "否");
			} else if (dataArray.get(i) instanceof ProcessInOut) { // 投入产出
				ProcessInOut inout = (ProcessInOut) dataArray.get(i);
				ProductProcess process = processMapCache.get(inout.getProductProcessId());
				ProductCrafts crafts = craftsMapCache.get(process.getProductCraftsId());
				if (null == process || null == crafts) {
					continue;
				}
				setDataToRow(row, crafts.getCraftsCode(), crafts.getCraftsName(), process.getProcessCode(),
						process.getProcessName(), inout.getMatCode(), inout.getMatName(),
						inout.getInOrOut().toString(), inout.getColor(),
						// inout.getMatSpec(),
						String.valueOf(inout.getQuantity()), inout.getQuantityFormula(), inout.getUnit().toString(),
						inout.getUseMethod(), inout.getRemark());
			} else if (dataArray.get(i) instanceof EquipList) { // 设备清单
				EquipList equip = (EquipList) dataArray.get(i);
				ProductProcess process = processMapCache.get(equip.getProcessId());
				ProductCrafts crafts = craftsMapCache.get(process.getProductCraftsId());
				if (null == process || null == crafts) {
					continue;
				}
				setDataToRow(row, crafts.getCraftsCode(), crafts.getCraftsName(), process.getProcessCode(),
						process.getProcessName(), equip.getEquipCode(), equip.getEquipName(), equip.getType()
								.toString(), String.valueOf(equip.getEquipCapacity()), String.valueOf(equip
								.getSetUpTime()), String.valueOf(equip.getShutDownTime()));
			}

		}

	}

	/**
	 * 每行写入数据
	 * */
	private void setDataToRow(HSSFRow row, String... params) {
		HSSFCell cell;
		for (int i = 0; i < params.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(params[i]);
		}
	}

	private String formatTime(Date date) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = sdf.format(date);
		return result;
	}

}
