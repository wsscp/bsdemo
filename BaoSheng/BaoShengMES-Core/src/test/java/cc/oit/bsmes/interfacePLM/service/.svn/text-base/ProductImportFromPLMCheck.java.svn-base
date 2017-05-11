package cc.oit.bsmes.interfacePLM.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.interfacePLM.dao.MpartInOutDAO;
import cc.oit.bsmes.interfacePLM.dao.MpartObjofDAO;
import cc.oit.bsmes.interfacePLM.dao.PLMProductDAO;
import cc.oit.bsmes.interfacePLM.dao.PrcvDAO;
import cc.oit.bsmes.interfacePLM.dao.PrcvObjofDAO;
import cc.oit.bsmes.interfacePLM.dao.ProcessDAO;
import cc.oit.bsmes.interfacePLM.dao.ProcessObjofDAO;
import cc.oit.bsmes.interfacePLM.dao.ProcessProcessObjofDAO;
import cc.oit.bsmes.interfacePLM.dao.ScxDAO;
import cc.oit.bsmes.interfacePLM.dao.ScxObjofDAO;
import cc.oit.bsmes.interfacePLM.dao.ScxkDAO;
import cc.oit.bsmes.interfacePLM.model.MpartInOut;
import cc.oit.bsmes.interfacePLM.model.PLMProduct;
import cc.oit.bsmes.interfacePLM.model.Prcv;
import cc.oit.bsmes.interfacePLM.model.PrcvObjof;
import cc.oit.bsmes.interfacePLM.model.Process;
import cc.oit.bsmes.interfacePLM.model.ProcessObjof;
import cc.oit.bsmes.interfacePLM.model.ProcessProcessObjof;
import cc.oit.bsmes.interfacePLM.model.ScxObjof;
import cc.oit.bsmes.interfacePLM.model.Scxk;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;

/**
 * Created by Jinhy on 2015/3/30. 工序扩展属性导入
 */
public class ProductImportFromPLMCheck extends BaseTest {

	@Resource
	private ProcessDAO processDAO;

	@Resource
	private MpartObjofDAO mpartObjofDAO;

	@Resource
	private PLMProductDAO PLMproductDAO;

	@Resource
	private ProcessProcessObjofDAO processProcessObjofDAO;

	@Resource
	private PrcvObjofDAO prcvObjofDAO;

	@Resource
	private MpartInOutDAO mpartInOutDAO;

	@Resource
	private ProcessObjofDAO processObjofDAO;

	@Resource
	private PrcvDAO prcvDAO;

	@Resource
	private ScxkDAO scxkDAO;

	@Resource
	private ScxObjofDAO scxObjofDAO;

	@Resource
	private ScxDAO scxDAO;

	@Resource
	private PLMProductService plmProductService;

	@Resource
	private MpartInOutService mpartInOutService;

	@Resource
	private ProcessService processService;

	// 同步产品的
	@Resource
	private ClassPathResource plmProductDetail;

	// 同步半成品
	@Resource
	private ClassPathResource plmMpartDetail;

	// 同步工序和输入输出物料关系
	@Resource
	private ClassPathResource insertMpartObj;

	// 同步工序扩展属性
	@Resource
	private ClassPathResource plmProcessDetail;

	// 导入新产品
	@Resource
	private ClassPathResource insertNewProduct;

	private ClassPathResource insertScx;
	
	@Resource
	private ProcessInOutService processInOutService;
	
	@Resource
	private ProcessReceiptService processReceiptService;
	
	
	/**
	 * 同步更新工序下的投入产出和质量参数
	 * @throws BiffException
	 * @throws IOException
	 */
	@Test
	@Rollback(false)
	public void updateQcInOut() throws BiffException, IOException {
		String[] tempStrArray = {"CZ50500380342-001"};
		List<String> listStrings = Arrays.asList(tempStrArray);
		for (String craftsCode : listStrings) {
			processInOutService.updateQcInOut(craftsCode);
		}
	}
	
	
	/**
	 * 同步工序下生产线的设备参数
	 * @throws BiffException
	 * @throws IOException
	 */
	@Test
	@Rollback(false)
	public void updateEquipQcByHand() throws BiffException, IOException {
		String craftsCode = null ;
		craftsCode = "CZ50100050071-001";
		processReceiptService.updateEquipQcByHand(craftsCode);
	}
	

	private static String[] tempStrArray = {};


	private List<String> listStrings = Arrays.asList(tempStrArray);
	
	public boolean checkContinue(List<String> listStrings,String productNo){
		boolean mark = false;
		if(listStrings.size() == 0){
			mark = true;
		}else if(listStrings.contains(productNo)){
			mark = true;
		}
		return mark;
	}

	// TODO
	@Test
	@Rollback(false)
	public void productInfoImport() throws IOException {
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(plmProductDetail.getInputStream());
		} catch (Exception e) {
			workbook = new HSSFWorkbook(plmProductDetail.getInputStream());
		}
		int sheets = workbook.getNumberOfSheets();

		for (int i = 0; i < sheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			productAnalySheet(sheet);
		}
	}

	// TODO
	@Test
	@Rollback(false)
	public void processInfoImport() throws IOException {
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(plmProcessDetail.getInputStream());
		} catch (Exception e) {
			workbook = new HSSFWorkbook(plmProcessDetail.getInputStream());
		}
		int sheets = workbook.getNumberOfSheets();

		for (int i = 0; i < sheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			/* sheet.setForceFormulaRecalculation(true); */
			if (sheet.getSheetName().indexOf("模板") < 0 && sheet.getSheetName().indexOf("库") < 0) {
				processAnalySheet(sheet);
			} else {
				continue;
			}
		}
	}

	// TODO
	@Test
	@Rollback(false)
	public void mpartInfoImport() throws IOException {
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(plmMpartDetail.getInputStream());
		} catch (Exception e) {
			workbook = new HSSFWorkbook(plmMpartDetail.getInputStream());
		}
		int sheets = workbook.getNumberOfSheets();

		for (int i = 0; i < sheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			mpartAnalySheet(sheet);
		}
	}

	// TODO
	@Test
	@Rollback(false)
	public void insertMpartObj() throws IOException {
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(insertMpartObj.getInputStream());
		} catch (Exception e) {
			workbook = new HSSFWorkbook(insertMpartObj.getInputStream());
		}
		int sheets = workbook.getNumberOfSheets();

		for (int i = 0; i < sheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			insertMpartObjAnalySheet(sheet);
		}
	}

	@Test
	@Rollback(false)
	public void insertNewProduct() throws IOException {
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(insertNewProduct.getInputStream());
		} catch (Exception e) {
			workbook = new HSSFWorkbook(insertNewProduct.getInputStream());
		}
		int sheets = workbook.getNumberOfSheets();

		for (int i = 0; i < sheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getSheetName().equals("导入产品")) {
				newProductAnalySheet(sheet);
			}
		}

	}

	// TODO
	@Test
	@Rollback(false)
	public void insertScx() throws IOException {
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(insertScx.getInputStream());
		} catch (Exception e) {
			workbook = new HSSFWorkbook(insertScx.getInputStream());
		}
		int sheets = workbook.getNumberOfSheets();

		for (int i = 0; i < sheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getSheetName().equals("绝缘模具和收线盘") || sheet.getSheetName().equals("绝缘和护套温度表格")
					|| sheet.getSheetName().equals("护套模具和收放线盘") || sheet.getSheetName().equals("编织收线")
					|| sheet.getSheetName().equals("钢丝装铠收放线") || sheet.getSheetName().equals("钢带装铠收放线")
					|| sheet.getSheetName().equals("成缆收放线") || sheet.getSheetName().equals("绕包收放线")) {
				continue;
			}
			insertScxSheet(sheet);
		}
	}

	private void insertScxSheet(Sheet sheet) {
		try {
			if (sheet.getRow(2) == null || getRowValue(sheet.getRow(2).getCell(0)).equals("")) {
				return;
			}
		} catch (Exception e) {
			System.out.println(sheet.getSheetName() + "行出错");
			return;
		}
		int maxRow = sheet.getLastRowNum() + 1;
		int maxCol = sheet.getRow(0).getLastCellNum();
		int proobjgnoCol = 2;
		int scxNoCol = 4;
		int count = 0; // 记录单元操作属性开始的列数
		for (int a = 4; a < maxCol; a++) {
			if (getRowValue(sheet.getRow(1).getCell(a)).contains("单元操作")) {
				count = a;
				break;
			}
		}

		for (int i = 2; i < maxRow; i++) {
			Row row = sheet.getRow(i);
			if (row == null || (getRowValue(row.getCell(0)).equals("") && getRowValue(row.getCell(4)).equals(""))) {
				return;
			}
			if (getRowValue(row.getCell(0)).equals("") && !getRowValue(row.getCell(4)).equals("")) {
				continue;
			}
			for (int col = 0; col < 5; col++) {
				if (getRowValue(sheet.getRow(0).getCell(col)).equals("单元操作代号")) {
					proobjgnoCol = col;
					break;
				}
			}
			for (int col = 0; col < 5; col++) {
				if (getRowValue(sheet.getRow(0).getCell(col)).equals("生产线代号")) {
					scxNoCol = col;
					break;
				}
			}
			String productNo = getRowValue(row.getCell(0));
			String prcvNo = getRowValue(row.getCell(1));
			String proobjgno = getRowValue(row.getCell(proobjgnoCol));
			if (proobjgno.endsWith(".0")) {
				proobjgno = proobjgno.substring(0, proobjgno.length() - 2);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("productNo", productNo);
			map.put("prcvNo", prcvNo);
			map.put("proobjgno", proobjgno);
			List<String> scxLists = new ArrayList<String>();

			scxLists.add(getRowValue(row.getCell(scxNoCol)));
			for (int k = i + 1; sheet.getRow(k) != null && !getRowValue(sheet.getRow(k).getCell(4)).equals("")
					&& getRowValue(sheet.getRow(k).getCell(1)).equals(""); k++) {
				scxLists.add(getRowValue(sheet.getRow(k).getCell(4)));
			}
			List<Process> processes = processDAO.getByProductAndPrcv(map);
			if (processes == null || processes.size() == 0) {
				System.out.println(productNo + "-" + prcvNo + "-" + proobjgno + ":找不到对应工序");
				continue;
			}
			if (processes.size() > 1) {
				System.out.println(productNo + "-" + prcvNo + "-" + proobjgno + "存在多条对应工序");
				continue;
			}
			Process process = processes.get(0);
			String processId = process.getId();
			for (int m = 0; m < scxLists.size(); m++) {
				String scxNo = scxLists.get(m);
				Scxk scxk = scxkDAO.getByProductLineNo(scxNo);
				if (scxk == null) {
					System.out.println(sheet.getSheetName() + "产品" + productNo + "的工序" + prcvNo + "-" + proobjgno
							+ "生产线库找不到对应设备" + scxNo);
					continue;
				}
				String scxkId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				scxk.setId(scxkId);
				String designno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				scxk.setDesignno(designno);

				String csvalue1 = scxk.getCsvalue1() == null ? "" : scxk.getCsvalue1();
				String csvalue2 = scxk.getCsvalue2() == null ? "" : scxk.getCsvalue2();
				String csvalue3 = scxk.getCsvalue3() == null ? "" : scxk.getCsvalue3();
				String csOldvalue = csvalue1 + csvalue2 + csvalue3;
				Map<String, String> mapParam = new HashMap<String, String>();

				if (count != 0) {

					if (csOldvalue != null && !csOldvalue.equals("")) {
						String[] valueArray = csOldvalue.split("\\^");
						for (String pro : valueArray) {
							String[] subArray = pro.split("=");
							if (subArray.length == 2) {
								mapParam.put(subArray[0], subArray[1]);
							} else {
								mapParam.put(subArray[0], "");
							}
						}
					}

					StringBuffer buffer = new StringBuffer();
					for (int j = count; j < maxCol; j++) {

						try {
							if (getRowValue(sheet.getRow(0).getCell(j)).equals("")) {
								break;
							}
							String proCode = getRowValue(sheet.getRow(0).getCell(j));
							if (proCode.endsWith("-000")) {
								continue;
							}
							String value = getRowValue(sheet.getRow(i + m).getCell(j));
							if (value.endsWith(".0")) {
								value = value.substring(0, value.length() - 2);
							}
							mapParam.put(proCode, value);
						} catch (Exception e) {
							System.out.println(sheet.getSheetName() + "-第" + i + "行-" + "第" + j + "列出错");
						}
					}

					for (Map.Entry<String, String> mm : mapParam.entrySet()) {
						buffer.append(mm.getKey() + "=" + mm.getValue() + "^");
					}
					String csvalue = buffer.toString();
					csvalue = csvalue.substring(0, csvalue.length() - 1);
					if (csvalue.length() > 2000) {
						scxk.setCsvalue1(csvalue.substring(0, 2000));
						if (csvalue.length() > 4000) {
							scxk.setCsvalue2(csvalue.substring(2000, 4000));
							scxk.setCsvalue3(csvalue.substring(4000, csvalue.length()));
						} else {
							scxk.setCsvalue2(csvalue.substring(2000, csvalue.length()));
						}
					} else {
						scxk.setCsvalue1(csvalue);
					}
				}

				List<ScxObjof> scxObjofLists = scxObjofDAO.findByProcessId(processId);
				if (scxObjofLists.size() != 0) {
					Map<String, ScxObjof> mapParm = new HashMap<String, ScxObjof>();
					for (ScxObjof scxObjof : scxObjofLists) {
						String no = scxDAO.getById(scxObjof.getItemid2()).getNo();
						mapParm.put(no, scxObjof);
					}

					if (mapParm.containsKey(scxNo)) {
						continue;
					} else {
						scxDAO.insertScx(scxk);
						ScxObjof scxobjof = new ScxObjof();
						String scxobjofId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
						String scxobjofDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
						scxobjof.setId(scxobjofId);
						scxobjof.setDesignno(scxobjofDesignno);
						scxobjof.setItemid1(processId);
						scxobjof.setItemid2(scxkId);
						scxObjofDAO.insertObjof(scxobjof);
					}
				} else {
					scxDAO.insertScx(scxk);
					ScxObjof scxobjof = new ScxObjof();
					String scxobjofId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
					String scxobjofDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
					scxobjof.setId(scxobjofId);
					scxobjof.setDesignno(scxobjofDesignno);
					scxobjof.setItemid1(processId);
					scxobjof.setItemid2(scxkId);
					scxObjofDAO.insertObjof(scxobjof);
				}
			}
		}
	}

	private void insertMpartObjAnalySheet(Sheet sheet) {
		if (sheet.getRow(1) == null) {
			return;
		}
		int maxRow = sheet.getLastRowNum() + 1;
		int maxCol = sheet.getRow(0).getLastCellNum();
		Map<Integer, String> mpartMap = new HashMap<Integer, String>();

		for (int j = 3; j < maxCol; j++) {
			if (getRowValue(sheet.getRow(0).getCell(j)).equals("输入物料物料编码")) {
				mpartMap.put(Integer.valueOf(j), "输入物料物料编码");

			}
			if (getRowValue(sheet.getRow(0).getCell(j)).equals("输出物料物料编码")) {
				mpartMap.put(Integer.valueOf(j), "输出物料物料编码");
			}
		}
		List<Integer> in = new ArrayList<Integer>();
		List<Integer> out = new ArrayList<Integer>();
		for (Map.Entry<Integer, String> map : mpartMap.entrySet()) {
			if (map.getValue().equals("输入物料物料编码")) {
				in.add(map.getKey());
			}
			if (map.getValue().equals("输出物料物料编码")) {
				out.add(map.getKey());
			}
		}

		for (int i = 1; i < maxRow; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				return;
			}
			String proNo = getRowValue(row.getCell(0));
			String prcvNo = getRowValue(row.getCell(1));
			String gno = getRowValue(row.getCell(2));
			Map<String,String> cache=new HashMap<String,String>();
			cache.put("productNo", proNo);
			cache.put("prcvNo", prcvNo);
			if (!checkContinue(listStrings, proNo)) {
				continue;
			}

			if (proNo.equals("") || prcvNo.equals("")/* ||gno.equals("") */) {
				return;
			}
			if (gno.endsWith(".0")) {
				gno = gno.substring(0, getRowValue(row.getCell(2)).length() - 2);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("productNo", proNo);
			map.put("prcvNo", prcvNo);
			map.put("proobjgno", gno);
			List<Process> processes = processDAO.getByProductAndPrcv(map);
			if (processes == null || processes.size() == 0) {
				System.out.println("产品" + "\t" + proNo + "\t" + "工艺路线" + "\t" + prcvNo  + "工序号" + "\t" + gno + "\t" + "找不到工序信息");
				continue;
			}
			if (processes.size() > 1) {
				System.out.println("产品" + "\t" + proNo + "\t" + "工艺路线" + "\t" + prcvNo  + "工序号" + "\t" + gno + "\t" + "存在多条工序信息");
				continue;
			}
			Process process = processes.get(0);
			String processId = process.getId();
			String prcvGno = getRowValue(row.getCell(1)) + getRowValue(row.getCell(2));
			String formerPrcvGno = getRowValue(sheet.getRow(i - 1).getCell(1))
					+ getRowValue(sheet.getRow(i - 1).getCell(2));
			if (i == 1 || (i > 1 && !prcvGno.equals(formerPrcvGno))) {
//				processDAO.deleteMpartByProcessId(processId);
//				processDAO.deleteMpart2ByProcessId(processId);
			}
			for (Integer inNum : in) {
				String mpartInNo = getRowValue(row.getCell(inNum));
				String mpartConsume = getRowValue(row.getCell(inNum + 1));
				String mpartAmount = getRowValue(row.getCell(inNum + 2));
				if (mpartInNo == null || mpartInNo.trim().equals("")) {
					continue;
				}
				MpartInOut param = new MpartInOut();
				param.setNo(mpartInNo);
				List<MpartInOut> mpartInLists = mpartInOutDAO.find(param);
				if (mpartInLists == null || mpartInLists.size() == 0) {
					System.out.println("产品" + "\t" + proNo + "\t" + "工艺路线" + "\t" + prcvNo  + "工序号" + "\t" +gno + "下找不到对应输入物料" + "\t" + mpartInNo);
					continue;
				}

				String mpartInId = mpartInLists.get(0).getId();

				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("processId", processId);
				map1.put("mpartInId", mpartInId);
				map1.put("mpartConsume", mpartConsume);
				map1.put("mpartAmount", mpartAmount);
				/*
				 * if (processDAO.checkExists(map1) != null) { if
				 * (!mpartConsume.equals("") || !mpartAmount.equals("")) {
				 * processDAO.updateMpartObj(map1); } continue; }
				 */
				String mpartObjofId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				String mpartObjofDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				map1.put("mpartObjofId", mpartObjofId);
				map1.put("mpartObjofDesignno", mpartObjofDesignno);
//				processDAO.insertMpartObj(map1);
				
				if (!mpartConsume.equals("") || !mpartAmount.equals("")) {
//					processDAO.updateMpartObj(map1);
				}
			}

			for (Integer outNum : out) {
				String mpartOutNo = getRowValue(row.getCell(outNum));
				String mpartAmount = getRowValue(row.getCell(outNum + 2));
				if (mpartOutNo == null || mpartOutNo.equals("")) {
					continue;
				}
				MpartInOut param = new MpartInOut();
				param.setNo(mpartOutNo);
				List<MpartInOut> mpartOutLists = mpartInOutDAO.find(param);
				if (mpartOutLists == null || mpartOutLists.size() == 0) {
					System.out.println("产品" + "\t" + proNo + "\t" + "工艺路线" + "\t" + prcvNo + "工序号" + "\t" + gno + "\t" + "下找不到对应输出物料" + "\t" + mpartOutNo);
					return;
				}
				String mpartOutId = mpartOutLists.get(0).getId();

				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("processId", processId);
				map1.put("mpartOutId", mpartOutId);
				map1.put("mpartAmount", mpartAmount);

				// if (processDAO.checkOutExists(map1) != null) {
				// if (!mpartAmount.equals("")) {
				// processDAO.updateMpartObj2(map1);
				// }
				// continue;
				// }
				String mpartObjofId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				String mpartObjofDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				map1.put("mpartObjofId", mpartObjofId);
				map1.put("mpartObjofDesignno", mpartObjofDesignno);
//				processDAO.insertMpartObj2(map1);
				if (!mpartAmount.equals("")) {
//					processDAO.updateMpartObj2(map1);
				}
			}

			// 及时更新工艺的更新时间，利于mes及时对工艺进行同步
//			List<Prcv> prcvList=prcvDAO.getPrcvByPrcvNoAndProNo(cache);
//			prcvDAO.updateModifyTimeByPrcvId(prcvList.get(0).getId());
		}
	}

	private void mpartAnalySheet(Sheet sheet) {
		if (sheet.getRow(2) == null) {
			return;
		}
		int maxRow = sheet.getLastRowNum() + 1;
		int maxCol = sheet.getRow(0).getLastCellNum();
		for (int i = 2; i < maxRow; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				return;
			}
			if (getRowValue(row.getCell(0)) == null || getRowValue(row.getCell(0)).equals("")
					|| getRowValue(row.getCell(1)).length() < 2) {
				return;
			}

			String gno = getRowValue(row.getCell(1));
			if (gno.endsWith(".0")) {
				gno = getRowValue(row.getCell(1)).substring(0, getRowValue(row.getCell(1)).length() - 2);
			}
			String no = getRowValue(row.getCell(0)) + "-" + gno + "-" + getRowValue(row.getCell(2));
			String name = getRowValue(row.getCell(5));
			String ename = getRowValue(row.getCell(6));
			String cstype = getRowValue(row.getCell(7));
	/*		String prcvNo=getRowValue(row.getCell(0)).substring(0, getRowValue(row.getCell(0)).indexOf("-"))+"-001"; 
			System.out.println(prcvNo);
			Map<String,String> cache=new HashMap<String,String>();
			cache.put("productNo", no);
			cache.put("prcvNo", prcvNo);*/

			MpartInOut findParams = new MpartInOut();
			findParams.setNo(no);
			
			if (!checkContinue(listStrings, getRowValue(row.getCell(0)))) {
				continue;
			}

			List<MpartInOut> list = mpartInOutService.findByObj(findParams);
			if (list == null || list.size() == 0) {
				System.out.println(no + "\t" + "在plm中没有模板数据");
				continue;
			} else {
				MpartInOut mpart = list.get(0);

				String csvalue2 = mpart.getCsvalue2() == null ? "" : mpart.getCsvalue2();
				String csvalue3 = mpart.getCsvalue3() == null ? "" : mpart.getCsvalue3();
				String csvalue1 = mpart.getCsvalue1() == null ? "" : mpart.getCsvalue1();
				String mpartOldCsvalue = csvalue1 + csvalue2 + csvalue3;
				Map<String, String> map = new HashMap<String, String>();
				if (mpartOldCsvalue != null && !mpartOldCsvalue.equals("")) {
					String[] valueArray = mpartOldCsvalue.split("\\^");
					for (String pro : valueArray) {
						String[] subArray = pro.split("=");
						if (subArray.length == 2) {
							map.put(subArray[0], subArray[1]);
						} else {
							map.put(subArray[0], "");
						}
					}
				}

				StringBuffer buffer = new StringBuffer();
				for (int j = 8; j < maxCol; j++) {
					String proCode = getRowValue(sheet.getRow(0).getCell(j));
					if (proCode.endsWith("-000")) {
						continue;
					}
					String value = getRowValue(row.getCell(j));
					if (value.endsWith(".0")) {
						value = value.substring(0, value.length() - 2);
					}
					map.put(proCode, value);
				}

				for (Map.Entry<String, String> m : map.entrySet()) {
					buffer.append(m.getKey() + "=" + m.getValue() + "^");
				}
				String csvalue = buffer.toString();
				csvalue = csvalue.substring(0, csvalue.length() - 1);
				if (csvalue.length() > 2000) {
					mpart.setCsvalue1(csvalue.substring(0, 2000));
					if (csvalue.length() > 4000) {
						mpart.setCsvalue2(csvalue.substring(2000, 4000));
						mpart.setCsvalue3(csvalue.substring(4000, csvalue1.length()));
					} else {
						mpart.setCsvalue2(csvalue.substring(2000, csvalue1.length()));
					}
				} else {
					mpart.setCsvalue1(csvalue);
				}

				if (cstype.equals("半成品绝缘/火花")) {
					mpart.setYanse(getRowValue(row.getCell(9)));
				}
				if (cstype.equals("半成品印字")) {
					mpart.setYanse(getRowValue(row.getCell(10)));
				}				
//				mpartInOutDAO.updateCsvalue1(mpart);
				/*List<Prcv> prcvList=prcvDAO.getPrcvByPrcvNoAndProNo(cache);
				prcvDAO.updateModifyTimeByPrcvId(prcvList.get(0).getId());*/
			}
		}
	}

	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	private void newProductAnalySheet(Sheet sheet) throws IOException {
		if (sheet.getRow(1) == null) {
			return;
		}
		int maxRow = sheet.getLastRowNum() + 1;

		for (int i = 1; i < maxRow; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				return;
			}
			String prcvNo = getRowValue(row.getCell(10));
			String newPrcvNo = getRowValue(row.getCell(9));
			String no = getRowValue(row.getCell(6)) + "-" + getRowValue(row.getCell(4));
			if (no == null || no.length() < 2) {
				return;
			}

			String name = getRowValue(row.getCell(3)) + "-" + getRowValue(row.getCell(4));
			String series = getRowValue(row.getCell(3));
			String type = getRowValue(row.getCell(4));
			String descrip = getRowValue(row.getCell(5));
			PLMProduct findParams = new PLMProduct();
			findParams.setNo(no);
			List<PLMProduct> list = plmProductService.findByObj(findParams);
			if (list.size() == 0 || list == null) {
				System.out.println("产品" + no + "无法找到！");
				continue;
			}
			if (list.size() > 1) {
				System.out.println("产品" + no + "有多条产品记录");
				continue;
			}
			if (prcvNo.equals("")) {
				System.out.println("请添加产品工艺路线模板");
				continue;
			}

			if (list.size() == 1) {
				PLMProduct product = list.get(0);
				String productId = product.getId();
				product.setName(name);
				product.setEname(name);
				product.setSeries(series);
				product.setAsuser01(type);
				product.setDescribe(descrip);
				plmProductService.updatePro(product);
				if (prcvNo != null && !prcvNo.equals("")) {
					copyMassPrcv(prcvNo, no, newPrcvNo, productId);
				}
			}
		}
	}

	public void productAnalySheet(Sheet sheet) throws IOException {
		if (sheet.getRow(1) == null) {
			return;
		}
		int maxRow = sheet.getLastRowNum() + 1;

		for (int i = 1; i < maxRow; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				return;
			}
			String prcvNo = getRowValue(row.getCell(9));
			String newPrcvNo = getRowValue(row.getCell(8));
			String no = getRowValue(row.getCell(5)) + "-" + getRowValue(row.getCell(4));

			if (!checkContinue(listStrings, no)) {
				continue;
			}

			if (no == null || no.length() < 2) {
				return;
			}

			String name = getRowValue(row.getCell(3)) + "-" + getRowValue(row.getCell(4));
			String series = getRowValue(row.getCell(3));
			String type = getRowValue(row.getCell(4));
			Map<String, String> cache = new HashMap<String, String>();
			cache.put("productNo", no);
			cache.put("prcvNo", newPrcvNo);
			PLMProduct findParams = new PLMProduct();
			findParams.setNo(no);
			List<PLMProduct> list = plmProductService.findByObj(findParams);
			if (list.size() == 0 || list == null) {
				System.out.println("产品" + "\t" +no+ "\t" + "无法找到！");
				continue;
			}
			if (list.size() > 1) {
				System.out.println("产品"+ "\t" + no + "\t" + "有多条产品记录");
				continue;
			}
			if (prcvNo.equals("")) {
				if (prcvDAO.checkPrcvByNo(newPrcvNo).size() == 0) {
					System.out.println("系统中产品" + "\t" + no + "\t" + "的工艺路线" + "\t" +newPrcvNo + "\t" + "不存在！");
				}

				else {
					Map<String, String> paramMap = new HashMap<String, String>();
					String productId = list.get(0).getId();
					paramMap.put("newPrcvNo", newPrcvNo);
					paramMap.put("productId", productId);
					// 如果该产品没有关联该工艺路线，而是关联了其他工艺路线，需要删除错误关联的工艺路线
					if (prcvDAO.checkExistPrcvObjof(paramMap).size() == 0) {
						if (prcvObjofDAO.getByProductId(productId) != null) {
							prcvObjofDAO.deleteByProuductId(productId);
							System.out.println("该产品" + "\t" + no + "\t" + "关联其他工艺路线并已删除该对应关系");
						}
					}
				}
			}

			if (list.size() == 1) {
				PLMProduct product = list.get(0);
				String productId = product.getId();
				product.setName(name);
				product.setEname(name);
				product.setSeries(series);
				product.setAsuser01(type);
				if (prcvNo != null && !prcvNo.equals("")) {
					if(!prcvNo.equalsIgnoreCase(newPrcvNo)){
						copyPrcv(prcvNo, no, newPrcvNo, productId, cache);
					}
				}
			}
		}
	}

	private void copyPrcv(String prcvNo, String no, String newPrcvNo, String productId, Map<String, String> cache)
			throws IOException {
		// 如果有模板需要mes生成工艺路线，但是plm系统里已经做过的工艺路线视为垃圾数据
		List<Prcv> newPrcvList = prcvDAO.checkPrcvByNo(newPrcvNo);
		if (prcvDAO.checkNoUseData(newPrcvNo).size() > 0 && !newPrcvNo.equals(prcvNo)) {
			System.out.println("存在需要删除的工艺路线" + "\t" +newPrcvNo+ "\t" + "请输入1确认：");
		}
		// 如果系统中存在新的工艺路线，先判断该存在的工艺路线是否与改产品关联，如果没有关联，但是该产品下关联了其他的工艺路线id，需要删除
		if (newPrcvList.size() > 0) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("newPrcvNo", newPrcvNo);
			paramMap.put("productId", productId);
			if (prcvDAO.checkExistPrcvObjof(paramMap).size() == 0) {
				if (prcvObjofDAO.getByProductId(productId) != null) {
					prcvObjofDAO.deleteByProuductId(productId);
					System.out.println("该产品" + "\t" +no+ "\t" + "关联其他工艺路线并且已经删除");
					// return;
				}
				// System.out
				// .println(no + "新的工艺路线模板存" + newPrcvNo + "在但与产品缺少对应关系");
				/*
				 * String prcvObjofId = "01_" +
				 * UUID.randomUUID().toString().replace("-", "") .toUpperCase();
				 * String designNo = "01_" +
				 * UUID.randomUUID().toString().replace("-", "") .toUpperCase();
				 * String prcvId = newPrcvList.get(0).getId(); PrcvObjof
				 * prcvObjof = new PrcvObjof(); prcvObjof.setId(prcvObjofId);
				 * prcvObjof.setDesignno(designNo);
				 * prcvObjof.setItemid1(productId);
				 * prcvObjof.setItemid2(prcvId);
				 * prcvObjofDAO.insertPrcvObjof(prcvObjof);
				 * System.out.println(no + "新的工艺路线对应关系已添加");
				 */
			}
		}

		if (prcvDAO.getPrcvByPrcvNoAndProNo(cache).size() > 0) {
			return;
		}

		List<Prcv> prcvs = prcvDAO.checkPrcvByNo(prcvNo);
		if (prcvs == null || prcvs.size() == 0) {
			System.out.println("模板" + "\t" +prcvNo + "\t" + "工艺路线无法找到！");
			return;
		}
		if (prcvs.size() > 1) {
			System.out.println("模板" + "\t" +prcvNo + "\t" + "存在多条相同的工艺路线");
		}

		String prcvOldid = prcvs.get(0).getId();
		String prcvNewId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		String prcvDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prcvOldid", prcvOldid);
		paramMap.put("prcvNewId", prcvNewId);
		paramMap.put("newPrcvNo", newPrcvNo);
		paramMap.put("prcvDesignno", prcvDesignno);
		paramMap.put("productId", productId);

		// 添加新的工艺路线
	/*	prcvDAO.insertPrcvByCopy(paramMap);
		PrcvObjof prcvobjof = new PrcvObjof();
		String prcvObId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		String prcvDesigno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		prcvobjof.setId(prcvObId);
		prcvobjof.setDesignno(prcvDesigno);
		prcvobjof.setItemid1(productId);
		prcvobjof.setItemid2(prcvNewId);*/

		// 添加产品和新工艺路线的关联
//		prcvObjofDAO.insertPrcvObjof(prcvobjof);

		List<cc.oit.bsmes.interfacePLM.model.Process> lists = processDAO.getProcessByPrcvId(prcvOldid);
		if (lists == null || lists.size() == 0) {
			System.out.println("找不到" + "\t" +prcvNo + "\t" + "工艺路线下的工序");
			return;
		} /*else {
			// 获模板工艺路线下的process_process关系
			List<ProcessProcessObjof> processProcessObjofLists = processProcessObjofDAO.getByPrcvId(prcvOldid);

			String processProcessObjofNewDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
			// 复制一份
			for (ProcessProcessObjof processProcessObjof : processProcessObjofLists) {
				String processProcessObjofNewId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				processProcessObjof.setId(processProcessObjofNewId);
				processProcessObjof.setDesignno(processProcessObjofNewDesignno);
//				processProcessObjofDAO.insertNewProcessProcessObjof(processProcessObjof);
			}
			for (Process process : lists) {
				String processOldId = process.getId();
				String processNewId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();

				Map<String, String> paramMap1 = new HashMap<String, String>();
				String processDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				paramMap1.put("processOldId", processOldId);
				paramMap1.put("processNewId", processNewId);
				paramMap1.put("processDesignno", processDesignno);
				// 把新复制出来的process_process信息，替换掉里面的id
				if (processProcessObjofDAO.checkProcess(paramMap1).size() > 0) {
					for (ProcessProcessObjof p : processProcessObjofDAO.checkProcess(paramMap1)) {
						if (p.getItemid1().equals(processOldId)) {
//							processProcessObjofDAO.updateItemId1(paramMap1);
							continue;
						}
						if (p.getItemid2().equals(processOldId)) {
//							processProcessObjofDAO.updateItemId2(paramMap1);
							continue;
						}
					}
				} else {
					System.out.println("工艺路线:"+newPrcvNo+"\t"+"工序id:"+processOldId + "\t" + "没有找到工序对应工序数据");
				}
				// 添加工序信息
//				processDAO.insertProcessByCopy(paramMap1);
				ProcessObjof processObjof = new ProcessObjof();
				String processObjofId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				String processObjofDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				processObjof.setId(processObjofId);
				processObjof.setDesignno(processObjofDesignno);
				processObjof.setItemid1(prcvNewId);
				processObjof.setItemid2(processNewId);
				// 添加工艺，工序之间的关联信息
//				processObjofDAO.insertProcessObjof(processObjof);
			}
		}*/
	}

	private void copyMassPrcv(String prcvNo, String no, String newPrcvNo, String productId) throws IOException {
		if (prcvDAO.checkNoUseData(newPrcvNo).size() > 0 && !newPrcvNo.equals(prcvNo)) {
			// System.out.println("存在需要删除的工艺路线" + newPrcvNo + "请输入1确认：");
			// BufferedReader br = new BufferedReader(new InputStreamReader(
			// System.in));
			String data = "0";
			if (data.equals("1")) {
				scxDAO.deleteScxByPrcvNo(newPrcvNo);
				scxObjofDAO.deleteScxObjofByPrcvNo(newPrcvNo);
				mpartObjofDAO.deleteMpartObjofByPrcvNo(newPrcvNo);
				mpartObjofDAO.deleteMpartObjofByPrcvNo2(newPrcvNo);
				processProcessObjofDAO.deletePPObjofByPrcvNo(newPrcvNo);
				processDAO.deleteProcessByPrcvNo(newPrcvNo);
				processObjofDAO.deleteProcessObjofByPrcvNo(newPrcvNo);
				prcvObjofDAO.deletePrcvObjofByPrcvNo(newPrcvNo);
				prcvDAO.deletePrcvByPrcvNo(newPrcvNo);
			}
		}

		if (prcvDAO.checkPrcvByNo(newPrcvNo).size() > 0) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("newPrcvNo", newPrcvNo);
			paramMap.put("productId", productId);
			if (prcvDAO.checkExistPrcvObjof(paramMap) == null || prcvDAO.checkExistPrcvObjof(paramMap).size() == 0) {

				if (prcvObjofDAO.getByProductId(productId) != null) {
					/* prcvObjofDAO.deleteByProuductId(productId); */
					System.out.println("该产品" + no + "关联其他工艺路线");
					return;
				}

				System.out.println(no + "新的工艺路线模板存" + newPrcvNo + "在但与产品缺少对应关系");
				String prcvObjofId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				String designNo = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				String prcvId = prcvDAO.checkPrcvByNo(newPrcvNo).get(0).getId();
				PrcvObjof prcvObjof = new PrcvObjof();
				prcvObjof.setId(prcvObjofId);
				prcvObjof.setDesignno(designNo);
				prcvObjof.setItemid1(productId);
				prcvObjof.setItemid2(prcvId);
				prcvObjofDAO.insertPrcvObjof(prcvObjof);
				System.out.println(no + "新的工艺路线对应关系已添加");
			}
			return;
		}

		List<Prcv> prcvs = prcvDAO.checkPrcvByNo(prcvNo);
		if (prcvs == null || prcvs.size() == 0) {
			System.out.println("模板" + prcvNo + "工艺路线无法找到！");
			return;
		}
		if (prcvs.size() > 1) {
			System.out.println("模板" + prcvNo + "存在多条相同的工艺路线");
			return;
		}

		String prcvOldid = prcvs.get(0).getId();
		String prcvNewId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		String prcvDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prcvOldid", prcvOldid);
		paramMap.put("prcvNewId", prcvNewId);
		paramMap.put("newPrcvNo", newPrcvNo);
		paramMap.put("prcvDesignno", prcvDesignno);
		paramMap.put("productId", productId);

		// 添加新的工艺路线
		prcvDAO.insertPrcvByCopy(paramMap);
		PrcvObjof prcvobjof = new PrcvObjof();
		String prcvObId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		String prcvDesigno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		prcvobjof.setId(prcvObId);
		prcvobjof.setDesignno(prcvDesigno);
		prcvobjof.setItemid1(productId);
		prcvobjof.setItemid2(prcvNewId);

		// 添加产品和新工艺路线的关联
		prcvObjofDAO.insertPrcvObjof(prcvobjof);

		List<cc.oit.bsmes.interfacePLM.model.Process> lists = processDAO.getProcessByPrcvId(prcvOldid);
		if (lists == null || lists.size() == 0) {
			System.out.println("找不到" + prcvNo + "工艺路线下的工序");
			return;
		} else {
			List<ProcessProcessObjof> processProcessObjofLists = processProcessObjofDAO.getByPrcvId(prcvOldid);
			if (null == processProcessObjofLists || processProcessObjofLists.size() == 0) {
				System.out.println(prcvNo + "工艺路线模板下找不到processprocess关系");
				return;
			}

			String processProcessObjofNewDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
			for (ProcessProcessObjof processProcessObjof : processProcessObjofLists) {
				String processProcessObjofNewId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				processProcessObjof.setId(processProcessObjofNewId);
				processProcessObjof.setDesignno(processProcessObjofNewDesignno);
				processProcessObjofDAO.insertNewProcessProcessObjof(processProcessObjof);
			}
			for (Process process : lists) {
				String processOldId = process.getId();
				String processNewId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();

				Map<String, String> paramMap1 = new HashMap<String, String>();
				String processDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				paramMap1.put("processOldId", processOldId);
				paramMap1.put("processNewId", processNewId);
				paramMap1.put("processDesignno", processDesignno);

				if (processProcessObjofDAO.checkProcess(paramMap1).size() > 0) {
					for (ProcessProcessObjof p : processProcessObjofDAO.checkProcess(paramMap1)) {
						if (p.getItemid1().equals(processOldId)) {
							processProcessObjofDAO.updateItemId1(paramMap1);
							continue;
						}
						if (p.getItemid2().equals(processOldId)) {
							processProcessObjofDAO.updateItemId2(paramMap1);
							continue;
						}
					}
				} else {
					System.out.println("产品" + no + processOldId + "没有找到工序对应工序数据");
					return;
				}

				processDAO.insertProcessByCopy(paramMap1);
				ProcessObjof processObjof = new ProcessObjof();
				String processObjofId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				String processObjofDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
				processObjof.setId(processObjofId);
				processObjof.setDesignno(processObjofDesignno);
				processObjof.setItemid1(prcvNewId);
				processObjof.setItemid2(processNewId);
				processObjofDAO.insertProcessObjof(processObjof);
			}
		}
	}

	private void processAnalySheet(Sheet sheet) {
		if (sheet.getRow(2) == null) {
			return;
		}
		int maxRow = sheet.getLastRowNum() + 1;
		int maxCol = sheet.getRow(0).getLastCellNum();
		int c=0;
		boolean d=false;
		for(int k=3;k<10;k++){
			if(getRowValue(sheet.getRow(0).getCell(k)).contains("-")){
				c=k;
				break;
			}
		}
		if(getRowValue(sheet.getRow(0).getCell(c-1)).equals("工序类型")){
			d=true;			
		}
		for (int i = 2; i < maxRow; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				return;
			}
			String productNo = getRowValue(row.getCell(0));
			String processType="";
			if (!checkContinue(listStrings, productNo)) {
				continue;
			}

			if (productNo.equals("")) {
				return;
			}
			if(d){
				processType=getRowValue(row.getCell(c-1));
			}		
			String prcvNo = getRowValue(row.getCell(1));
			String proobjgno = getRowValue(row.getCell(2));
			if (proobjgno.endsWith(".0")) {
				proobjgno = proobjgno.substring(0, proobjgno.length() - 2);
			}

			Map<String, String> findParams = new HashMap<String, String>();
			findParams.put("productNo", productNo);
			findParams.put("prcvNo", prcvNo);
			findParams.put("proobjgno", proobjgno);

			List<Process> processes = processDAO.getByProductAndPrcv(findParams);

			if (processes == null || processes.size() == 0) {
				System.out.println("找不到" + "\t" +productNo + "\t" + prcvNo + "\t" + proobjgno + "\t" + "工序");
				continue;
			}
			if (processes.size() > 1) {
				System.out.println(productNo + "\t" + prcvNo + "\t" + proobjgno + "\t" + "存在多条工序");
				continue;
			}
			// 把已经存在的属性放入map集合中缓存
			Process process = processes.get(0);
			if(processType!= null && !processType.equals("")){
				process.setPtype(processType);
			}
			String csvalue2 = process.getCsvalue2() == null ? "" : process.getCsvalue2();
			String csvalue3 = process.getCsvalue3() == null ? "" : process.getCsvalue3();
			String csvalue1 = process.getCsvalue1() == null ? "" : process.getCsvalue1();
			Map<String, String> map = new HashMap<String, String>();
			String scxkOldCsvalue = csvalue1 + csvalue2 + csvalue3;
			if (scxkOldCsvalue != null && !scxkOldCsvalue.equals("")) {
				String[] valueArray = scxkOldCsvalue.split("\\^");
				for (String pro : valueArray) {
					String[] subArray = pro.split("=");
					if (subArray.length == 2) {
						map.put(subArray[0], subArray[1]);
					} else {
						map.put(subArray[0], "");
					}
				}
			}

			StringBuffer buffer = new StringBuffer();
			for (int j = c; j < maxCol; j++) {

				try {
					String proCode = getRowValue(sheet.getRow(0).getCell(j));
					if (proCode == null || proCode.equals("")) {
						break;
					}
					if (proCode.endsWith("-000")) {
						continue;
					}
					String value = getRowValue(row.getCell(j));
					if (value.endsWith(".0")) {
						value = value.substring(0, value.length() - 2);
					}
					map.put(proCode, value);
				} catch (Exception e) {
					System.out.println(sheet.getSheetName() + "-第" + i + "行-" + "第" + j + "列出错");
				}
			}

			for (Map.Entry<String, String> m : map.entrySet()) {
				buffer.append(m.getKey() + "=" + m.getValue() + "^");
			}
			String csvalue = buffer.toString();
			csvalue = csvalue.substring(0, csvalue.length() - 1);
			if (csvalue.length() > 2000) {
				process.setCsvalue1(csvalue.substring(0, 2000));
				if (csvalue.length() > 4000) {
					process.setCsvalue2(csvalue.substring(2000, 4000));
					process.setCsvalue3(csvalue.substring(4000, csvalue.length()));
				} else {
					process.setCsvalue2(csvalue.substring(2000, csvalue.length()));
				}
			} else {
				process.setCsvalue1(csvalue);
			}
//			processDAO.updateCsValue1(process);

			// 及时更新工艺的更新时间，利于mes及时对工艺进行同步
//			prcvDAO.updatePrcvModifyTime(productNo);
		}
	}

	private String getRowValue(Cell cell) {
		Object result = null;
		if (cell == null) {
			return "";
		}
		int cellType = cell.getCellType();

		switch (cellType) {
		case Cell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			new BigDecimal(cell.getNumericCellValue()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			result = new BigDecimal(cell.getNumericCellValue()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			/* result=cell.getNumericCellValue(); */
			break;
		case Cell.CELL_TYPE_FORMULA:
			try {
				new BigDecimal(cell.getNumericCellValue()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				result = new BigDecimal(cell.getNumericCellValue()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			} catch (Exception e) {

				result = cell.getRichStringCellValue();
			}
			break;
		default:
			result = "";
		}
		return String.valueOf(result);
	}

	private void copyXmlFile(Prcv nPrcv, String prcvNo) throws IOException {
		List<Prcv> prcvs = prcvDAO.checkPrcvByNo(prcvNo);
		if (prcvs == null || prcvs.size() == 0) {
			System.out.println("模板" + prcvNo + "工艺路线无法找到！");
			return;
		}
		Prcv oPrcv = prcvs.get(0);
		String oLocation = oPrcv.getLocation();
		String oldFileLocation = "\\\\192.167.29.93" + oLocation;

		InputStream in = null;
		try {
			in = new FileInputStream(oldFileLocation);
		} catch (FileNotFoundException e) {
			System.out.println("找不到文件+oldFileLocation");
		}
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(reader);
		String nPrcvId = nPrcv.getId();
		String nFileName = nPrcvId + ".sfm";
		String nLocation = oLocation.substring(0, 10) + nFileName;
		String nFname = nPrcv.getNo() + ".sfm";
		String fileDir = "\\\\192.167.29.93" + nLocation;
		File file = new File(fileDir);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {

				System.out.println("找不到文件路径" + fileDir);
				return;
			}
		}
		OutputStream out = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(out);
		BufferedWriter bw = new BufferedWriter(writer);
		PrintWriter pw = new PrintWriter(bw);
		String oldPrcvId = prcvDAO.checkPrcvByNo(prcvNo).get(0).getId();
		List<Process> oldLists = processDAO.getProcessByPrcvId(oldPrcvId);
		List<Process> newLists = processDAO.getProcessByPrcvId(nPrcvId);
		List<ProcessProcessObjof> oldppoLists = processProcessObjofDAO.getByPrcvId(oldPrcvId);
		List<ProcessProcessObjof> newppoLists = processProcessObjofDAO.getByPrcvId(nPrcvId);
		Map<String, String> param = new HashMap<String, String>();
		param.put("location", nLocation);
		param.put("fname", nFname);
		param.put("nPrcvId", nPrcvId);
		for (Process op : oldLists) {
			String opGno = String.valueOf(op.getGno());
			String opId = op.getId();
			for (Process np : newLists) {
				String npGno = String.valueOf(np.getGno());
				String npId = np.getId();
				if (opGno.equals(npGno)) {
					param.put(opId, npId);
					break;
				}

			}
		}
		for (ProcessProcessObjof oppo : oldppoLists) {
			String oppoId = oppo.getId();
			String oitemid1 = oppo.getItemid1();
			String oitemid2 = oppo.getItemid2();
			if (param.get(oitemid1) == null || param.get(oitemid1).equals("") || param.get(oitemid2) == null
					|| param.get(oitemid2).equals("")) {
				System.out.println("新的工艺路线" + nPrcv.getNo() + "中" + "工序id" + oitemid1 + "或工序id" + oitemid2
						+ "找不到对应新的工序id");
				return;
			}
			for (ProcessProcessObjof nppo : newppoLists) {
				String nppoId = nppo.getId();
				String nitemid1 = nppo.getItemid1();
				String nitemid2 = nppo.getItemid2();

				if (param.get(oitemid1).equals(nitemid1) && param.get(oitemid2).equals(nitemid2)) {
					param.put(oppoId, nppoId);
					break;
				}
			}
		}

		String data;
		StringBuffer buf = new StringBuffer();
		while ((data = br.readLine()) != null) {
			Pattern p = Pattern.compile("WK\\w+");
			Matcher m = p.matcher(data);
			if (m.find()) {
				String key = m.group().substring(3);
				String value = param.get(key);
				if (value == null || value.equals("")) {
					System.out.println("新工艺路线" + nPrcv.getNo() + "-" + key + "的配置文件找不到对应替换的id!");
					/* file.delete(); */
					continue;
				}
				m.appendReplacement(buf, "WK_" + value);
				m.appendTail(buf);
				/* System.out.println(buf); */
				pw.println(buf);
				buf.delete(0, buf.length());
				continue;
			}
			/* System.out.println(data); */
			pw.println(data);
		}
		br.close();
		pw.close();
		InputStream in2 = new FileInputStream(file);
		String fileSize = String.valueOf(in2.available());
		param.put("fsize", fileSize);
		in2.close();
		prcvDAO.updateLoNameFsize(param);
	}

}
