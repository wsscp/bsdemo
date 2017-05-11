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
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.service.AttachmentService;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.interfacePLM.dao.Desf2DAO;
import cc.oit.bsmes.interfacePLM.dao.ModDAO;
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
import cc.oit.bsmes.interfacePLM.model.Desf2;
import cc.oit.bsmes.interfacePLM.model.Jiantu;
import cc.oit.bsmes.interfacePLM.model.Jpgfile;
import cc.oit.bsmes.interfacePLM.model.Mod;
import cc.oit.bsmes.interfacePLM.model.MpartInOut;
import cc.oit.bsmes.interfacePLM.model.MpartObjof;
import cc.oit.bsmes.interfacePLM.model.PLMProduct;
import cc.oit.bsmes.interfacePLM.model.Prcv;
import cc.oit.bsmes.interfacePLM.model.PrcvObjof;
import cc.oit.bsmes.interfacePLM.model.Process;
import cc.oit.bsmes.interfacePLM.model.ProcessObjof;
import cc.oit.bsmes.interfacePLM.model.ProcessProcessObjof;
import cc.oit.bsmes.interfacePLM.model.ScxObjof;
import cc.oit.bsmes.interfacePLM.model.Scxk;
import cc.oit.bsmes.inv.dao.MatDAO;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.dao.ProductDAO;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;

/**
 * Created by Jinhy on 2015/3/30. 工序扩展属性导入
 */
public class ProductImportFromPLM extends BaseTest {
	
	private static final String remotePath="E:\\BSPLM\\plmdata";
	
	private static final String sharePath="\\\\192.167.36.94";
	
	private static final String shareImagePath="\\\\192.167.36.94\\plm录入数据excel\\电子工厂图片库";
	
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	private static Map<String, String[]> processMap=new HashMap<String, String[]>();
	
	private static Map<String,String> clImageMap=new HashMap<String, String>();
	
	private static Map<String, String> CLMap=new HashMap<String, String>();
	
	@Resource
	private ProcessDAO processDAO;
	
	@Resource
	private JpgfileService jpgfileService;
	
	@Resource
	private ProductService productService;

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
	private ProductDAO productDAO;

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
	
	@Resource
	private Desf2DAO desf2DAO;
	
	@Resource
	private ModDAO modDAO;
	
	@Resource
	private MatDAO matDAO;
	
	@Resource
	private JiantuService jiantuService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private MatService matService;
	
	@Resource
	private PrcvService prcvService;
	
	@Resource
	private AddPrcvXmlTask addPrcvXmlTask;
	
	
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
	
	//同步导体类别
	@Test
	@Rollback(false)
	public void synDaotiLeibie(){
		List<MpartInOut> mparts=mpartInOutDAO.getAllDaoti();
		Map<String,String> map=new HashMap<String,String>();
		map.put("导体", "3f26ffd0-959b-4b0d-b261-cbd7da433b01");
		map.put("单丝", "dc642548-b6ec-4fb5-824a-7df2858c9242");
		for(MpartInOut mpart: mparts){		
			String matCode=mpart.getNo();
			String cstype=mpart.getCstype();
			String templetDetailId=map.get(cstype);
			List<Mat> mats=matDAO.getByMatCode(matCode);
			Map<String,String> param=new HashMap<String,String>();
			if(mats.size()>0){
				Mat mat=mats.get(0);
				String daoti=mpart.getDiaotie();
				String matId=mat.getId();
				param.put("matId",matId);
				param.put("daoti",daoti);
				param.put("templetDetailId",templetDetailId);
				if(matDAO.getPropValue(param).size()>0){
					continue;
				}else{				
					matDAO.insertMatPro(param);
				}				
			}else{
				System.out.println(matCode+"在mes中不存在！");
				continue;
			}						
			}
		}
		
	//在共享文件夹中生成图片目录（需要提供产品名称）
	@Test
	@Rollback(false)
	public void generateFile(){
		String[] lists={"KVVP-450/750V-2*0.5",
				"KVVP-450/750V-2*0.75"};
		Map<String,Object> param=new HashMap<String,Object>();
		List<String> productLists=Arrays.asList(lists);
		param.put("productNames",productLists );
		List<PLMProduct> plmProducts=plmProductService.getByProductName(param);
		logger.info("已生成"+productLists.size()+"个文件夹");
		for(PLMProduct p: plmProducts){
			//*替换成x  /替换成-
			String fileName=p.getName().replace("*", "x").replace("/", "&");
			List<MpartInOut> mparts=mpartInOutService.getAllMpartByProductId(p.getId());
			List<Process> processCLList=processDAO.getCLProcessByProductId(p.getId());
			String dir=sharePath+"\\plm录入数据excel\\电子工厂图片库\\"+fileName;
			File f=new File(dir);
			if(!f.exists()){
				f.mkdirs();
			}else{
				continue;
			}
			String halfDir=dir+"\\半成品";
			String desf2Dir=dir+"\\二维图";
			String modDir=dir+"\\三维图";
			String CablingDir=dir+"\\成缆截面";	
			File halfFile=new File(halfDir);
			File desf2File=new File(desf2Dir);
			File modFile=new File(modDir);
			File cablingFile=new File(CablingDir);
			if(!halfFile.exists()){
				halfFile.mkdirs();
			}
			if(!desf2File.exists()){
				desf2File.mkdirs();
			}
			if(!modFile.exists()){
				modFile.mkdirs();
			}
			if(!cablingFile.exists()){
				cablingFile.mkdirs();
			}
			
			//半成品文件夹下生成半成品目录
			for(MpartInOut m: mparts){
				String MFileName=m.getNo().replace("*", "x")+m.getName().replace("/", "");
				String mpartDir=halfDir+"\\"+MFileName;
				File mpartFile=new File(mpartDir);
				if(!mpartFile.exists()){
					mpartFile.mkdirs();
				}
			}
			
			for(Process cl: processCLList){
				String processName=cl.getName();
				String clDir=CablingDir+"\\"+processName;
				File clFile=new File(clDir);
				if(!clFile.exists()){
					clFile.mkdirs();
				}
			}
		}
			
		
		
	}
	
	
	//同步原材料辅料图片
	@Test
	@Rollback(false)
	public void synMaterial(){
		List<Jiantu> jiantulists= jiantuService.getAllMaterialJiantu();
		for(Jiantu jt: jiantulists){
			String matId="";
			List<Mat> mats=matDAO.getByMatCode(jt.getNo());
			if(mats!=null && mats.size()>0){
				matId=mats.get(0).getId();
			}else{	
				System.out.println("物料"+jt.getNo()+"在MES中没有数据");
				continue;
				//如果mes中不存在该物料，则重新导入
				/*List<MpartInOut> lists=mpartInOutService.getMpartByName(jt.getNo());
				if(lists!=null && lists.size()>0){
					Mat mat=new Mat();
					mat.setMatCode(lists.get(0).getNo());
					mat.setMatName(lists.get(0).getName());
					mat.setMatType(MatType.MATERIALS);					
					mat.setHasPic(false);
					mat.setIsProduct(false);
					mat.setOrgCode("bstl01");
					mat.setTempletId("133cd489-7ac4-49f2-af2f-e94ac91d98c8");
					matService.insert(mat);					
					System.out.println("物料"+jt.getNo()+"已加入mes");
				}else{
					System.out.println("物料"+jt.getNo()+"在PLM中没有数据");
				}*/
			}
			String location=jt.getLocation();
			String fileDir=remotePath+location;
			File file=new File(fileDir);
			attachmentService.uploadUrl(file, InterfaceDataType.MPART, "JIANTU", matId,fileDir);
		}
	}
	
	//同步图片到PLM中
	@Test
	@Rollback(false)
	public void synImageToPLM() throws IOException{		
		File file=new File(shareImagePath);
		long time=System.currentTimeMillis();
		//循环产品文件夹
		for(File productFile: file.listFiles()){
			String designNO="01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
			String productId="";
			if(!productFile.getName().contains("未导入") || getSize(productFile)==0.0){
				continue;
			}		
			//把文件夹名变回正确的产品名称
			String productName=productFile.getName().replace("&", "/").replace("x", "*").substring(0, productFile.getName().length()-3);
			System.out.println("开始导入产品"+productName+"图片-----------------------------");
			List<PLMProduct> products=plmProductService.getPLMProductByProductName(productName);
			if(products!=null && products.size()>0){
				productId=products.get(0).getId();
			}else{
				System.out.println("产品"+productName+"在PLM中无法找到!");
				continue;
			}
			//循环半成品，成缆，二维图，三维图文件夹
			for(File detailFile: productFile.listFiles()){
				if(getSize(detailFile)==0.0){
					if(detailFile.getName().equals("二维图")){
						System.out.println("产品"+productName+"缺少二维图");
					}
					if(detailFile.getName().equals("三维图")){
						System.out.println("产品"+productName+"缺少三维图");
					}
					if(detailFile.getName().equals("成缆截面")){
						System.out.println("产品"+productName+"缺少成缆截面图");
					}else{
						System.out.println("产品"+productName+"缺少所有半成品图");
					}
					continue;
				}
				if(detailFile.exists()&& detailFile.isDirectory()){
					if(detailFile.getName().equals("半成品")){
						//如果是半成品文件夹，循环所有的半成品
						for(File innnerFile:detailFile.listFiles()){
							if(getSize(innnerFile)==0.0){
								System.out.println("产品"+productName+"中缺少半成品"+innnerFile.getName().replace("x", "*")+"的图片");
								continue;
							}
							String innerFileName=innnerFile.getName().replace("x", "*");
							//匹配半成品编码
							Pattern p=Pattern.compile(".{10,100}-\\d{3}");
							Matcher m=p.matcher(innerFileName);							
							String halfProNo="";
							while(m.find()){
								 halfProNo=m.group();
							}
							if(halfProNo==""){
								System.out.println(innerFileName+"半成品编码匹配错误");
								continue;
							}
							
							File[] halfProductFiles=innnerFile.listFiles();
							//生成简图
								for(File halfProductFile: halfProductFiles){
									if(halfProductFile.getName().equals("Thumbs.db")){
										continue;
									}
									jiantuService.upload(halfProductFile,designNO, detailFile.getName(),halfProNo);					
								}
							
						}
					}
					if(detailFile.getName().equals("二维图")||detailFile.getName().equals("三维图")){
						for(File image : detailFile.listFiles()){
							if(image.getName().equals("Thumbs.db")){
								continue;
							}
							jiantuService.upload(image,designNO, detailFile.getName(),productName);
						}
					}
					if(detailFile.getName().equals("成缆截面")){
						//初始化成缆截面数据
						this.init();
						for(File innnerFile: detailFile.listFiles()){
							if(getSize(innnerFile)==0.0){
								System.out.println("产品"+productName+"缺少"+innnerFile.getName()+"工序成缆截面图");
								continue;
							}
							Map<String, String> map = new HashMap<String, String>();
							Map<String,String> param=new HashMap<String,String>();	
							String innerFileName=innnerFile.getName();
							param.put("productName", innerFileName);
							param.put("productId", productId);
							List<Process> processCLs=processDAO.getExactCLProcessByName(param);
							StringBuffer buffer=new StringBuffer();
							if(processCLs!=null && processCLs.size()>0){		
								Process process=processCLs.get(0);
								String csvalue2 = process.getCsvalue2() == null ? "" : process.getCsvalue2();
								String csvalue3 = process.getCsvalue3() == null ? "" : process.getCsvalue3();
								String csvalue1 = process.getCsvalue1() == null ? "" : process.getCsvalue1();
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
								List<String> attrCodes=Arrays.asList(processMap.get(innerFileName));
								for(String attrCode: attrCodes){
									if(StringUtils.isNotEmpty(map.get(attrCode))){
										jpgfileService.deleteById(map.get(attrCode));
									}
									File image=null;
									File[] clImages=innnerFile.listFiles();
									for(File clImage :clImages){
										if(clImage.getName().substring(0, 2).equals(clImageMap.get(attrCode))){
											image=clImage;
											break;
										}
									}
									if(image==null){
										System.out.println("产品"+productName+"工序"+innnerFile.getName()+"成缆截面图中找不到"+clImageMap.get(attrCode)+"的图片");
										continue;
									}
									String jpgfileId=jpgfileService.upLoadClImage(image);
									map.put(attrCode, jpgfileId);								
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
								processDAO.updateCsValue1(process);
							}else{
								System.out.println("找不到产品"+productName+"中"+innerFileName+"的工序信息");
							}
							
							}
							
						}
					}
					
				}	
			    productFile.renameTo(new File(shareImagePath+"\\"+productFile.getName().replace("未导入", "已导入")));
			    products.get(0).setDescribe("有图片");
			    plmProductService.updateDescribeInfo(products.get(0));
			    System.out.println("产品"+productName+"图片导入结束-------------------------------\n");
			}
			long period=System.currentTimeMillis()-time;
			System.out.println("一共用时:"+period+"ms");
			
		}
	
	
	//同步图片到MES中
	@Test
	@Rollback(false)
	public void testImage() throws IOException{

		List<PLMProduct> products=plmProductService.getProductsWithImage();
		
		Map<String,Object> param=new HashMap<String,Object>();
		//产品二维图
	    Map<String,Desf2> productCache2=new HashMap<String,Desf2>();
	    //产品三维图
	    Map<String,Mod> productCache3=new HashMap<String,Mod>();
	    //物料半成品
	    Map<String,Jiantu> materialsCache=new HashMap<String,Jiantu>();	    
	    //成缆截面图
	    Map<String,String> processJpgFile=new HashMap<String,String>();	   
	    //成缆工序截面图缓存
	    Map<String, Map<String,String>> processCLImage=new HashMap<String,Map<String,String>>();
		int i=1;
	    
	    this.init();
	    
	    long now=System.currentTimeMillis();
	    
	    logger.info("导入开始。。。");
	      
		
		for(PLMProduct product: products){
		
			processCLImage.clear();
			String no=product.getNo();
			Product mesProduct=productService.getByProductCode(no);
			String productId=mesProduct.getId();
			param.put("productId", product.getId());
		    //二维图放入缓存
			List<Desf2> desf2s=desf2DAO.getByProductId(param);
			if(desf2s!=null && desf2s.size()>0){
				productCache2.put(productId, desf2s.get(0));
			}
			//三维图放入缓存
			List<Mod> mods=modDAO.getByProductId(param);
			if(mods!=null && mods.size()>0){
				productCache3.put(productId, mods.get(0));
			}
					
			List<MpartInOut> mparts=mpartInOutService.getAllMpartByProductId(product.getId());
			//半成品图片放入缓存
			if(mparts!=null && mparts.size()>0){				
				for(MpartInOut mpart: mparts){
					String mpartId=mpart.getId();
					String matCode=mpart.getNo();
					List<Mat> mats=matDAO.getByMatCode(matCode);
					if(mats!=null){
						Mat mat=mats.get(0);
						List<Jiantu> jiantus=jiantuService.getJiantuByMpartId(mpartId);
						if(jiantus!=null && jiantus.size()>0){
							materialsCache.put(mat.getId(), jiantus.get(0));
						}		
					}
								
				}				
			}
			List<Process> processCLList=processDAO.getCLProcessByProductId(product.getId());
			if(processCLList!=null && processCLList.size()>0){
				for(Process CLProcess: processCLList){
					processJpgFile.clear();
					String processName=CLProcess.getName();
					String processCode=CLProcess.getEname()+CLProcess.getGno();
					String csValue1=CLProcess.getCsvalue1()==null ? "" : CLProcess.getCsvalue1();
					String csValue2=CLProcess.getCsvalue2()==null ? "" : CLProcess.getCsvalue2();
					String csValue3=CLProcess.getCsvalue3()==null ? "" : CLProcess.getCsvalue3();
					String csValue=csValue1+csValue2+csValue3;
					String[] csValueArrays=csValue.split("\\^");					
					List<String> listProcessAttribueCode=Arrays.asList(processMap.get(processName));
					if(listProcessAttribueCode!=null && listProcessAttribueCode.size()>0){
						for(String attributeCode: listProcessAttribueCode){							
							if(csValue.contains(attributeCode)){
								for(String csValueArray: csValueArrays){
									if(csValueArray.contains(attributeCode)){
										if(csValueArray.split("=").length==2){
											String jpgFileId=csValueArray.split("=")[1];
											processJpgFile.put(attributeCode, jpgFileId);
										}
									}
								}
							}
						}
					}
					processCLImage.put(processCode, processJpgFile);
				}
			}
			
			//上传成缆截面图
			if(processCLImage!=null && processCLImage.size()>0){
				for(Entry<String, Map<String, String>> map:processCLImage.entrySet()){
					for(Entry<String,String> map1:map.getValue().entrySet()){
						String num=map1.getKey().substring(map1.getKey().length()-3, map1.getKey().length());
						String dataType=CLMap.get(num);
						String jpgFileId=map1.getValue();
						List<Jpgfile> fileLists=jpgfileService.getByJpgfileId(jpgFileId);
						if(fileLists!=null && fileLists.size()>0){
							byte[] content=fileLists.get(0).getData();
							attachmentService.uploadByByte(content, InterfaceDataType.valueOf(dataType), map.getKey(), productId);
						}
					}
				}
			}			
				logger.info("第"+i+"个保存成功");	
				product.setDescribe("已导入");
				plmProductService.updateDescribeInfo(product);
				i++;
		}
		
		//保存二维图路径
		if(productCache2!=null && productCache2.size()>0){
			for(Entry<String, Desf2> map:productCache2.entrySet()){
				String location=remotePath+map.getValue().getLocation();						
				File file=new File(location);
				attachmentService.uploadUrl(file, InterfaceDataType.PRODUCT, "DESF2", map.getKey(), location);				
			}
		}
		
		//保存三维图路径
		if(productCache3!=null && productCache3.size()>0){
			for(Entry<String, Mod> map:productCache3.entrySet()){
				String location=remotePath+map.getValue().getLocation();						
				File file=new File(location);
				attachmentService.uploadUrl(file, InterfaceDataType.PRODUCT, "MOD", map.getKey(),location);				
			}
		}	
		
		//保存物料图片路径
		if(materialsCache!=null && materialsCache.size()>0){
				for(Entry<String,Jiantu> map:materialsCache.entrySet()){
					String location=remotePath+map.getValue().getLocation();
					File file=new File(location);
					attachmentService.uploadUrl(file, InterfaceDataType.MPART, "JIANTU", map.getKey(),location);
				}
			
		}
		
		   logger.info("导入结束用时："+(System.currentTimeMillis()-now));
		   

	}
	
	@Test
	@Rollback(false)
	public void updateVersion() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException{
		String mpartNo="Y101001015032";
		//1.检查该导体的几个必要属性是否有数值（导体结构，导体单丝直径，导体外径近似值，导体重量近似值）
		List<MpartInOut> mparts=mpartInOutService.getMpartByName(mpartNo);		
		MpartInOut mpart=null;
		//导体单丝直径
		String diaSingle="";
		//导体外径近似值
		String materialDia="";
		//导体重量近似值
		String materialWeight="";
		//导体结构
		String structure="";
		//绕包后外径
		double wrappingDia=0;
		//绕包层数
		int num = 0;
		//绝缘前外径
		double jueyuanBe;
		//绝缘标称外径
		double jueyuanStandard = 0;
		//绝缘最小外径
		double jueyuanMin;
		//绝缘最大外径
		double jueyuanMax;
		//绝缘标称厚度
		String standardThickness;
		//绝缘指导厚度
		String guidThickness;
		//绝缘最大厚度
		String maxThickness;
		//绝缘最小厚度
		String minThickness;
		//绝缘料密度
		String jueYuanSpec="";
		//导体根数
		int count;
		//绝缘工序成品重量
		double proWeight;
		//绝缘根数
		int jueyuanNum;
		//绞合外径
		double cablingDia;
		Map<String,String> results=null;
		Map<Integer,Double> cablingParam=new HashMap<Integer,Double>();
		//初始化成缆系数的map参数
		initCablingParam(cablingParam);
		//中文数字和数字映射参数
		Map<String,String> mapping=new HashMap<String,String>();	
		mappingParam(mapping);
		if(mparts!=null && mparts.size()>0){
			mpart=mparts.get(0);
		}else{
			logger.info("物料不存在");
			return;
		}
		String cstype=mpart.getCstype();
		if(cstype.equals("单丝")){
			results=mpartInOutService.getFieldsByMpartNo(mpartNo);
			diaSingle=results.get("DIA")==null?"":results.get("DIA");
			materialDia=diaSingle;
			materialWeight=results.get("WEIGHT")==null?"":results.get("WEIGHT");
			structure=results.get("STRU")==null?"":results.get("STRU");
			count=1;
		}else{
			results=mpartInOutService.getFieldsByMpartNoDT(mpartNo);
			diaSingle=results.get("DIA")==null?"":results.get("DIA");
			materialDia=results.get("MDIA")==null?"":results.get("MDIA");
			materialWeight=results.get("WEIGHT")==null?"":results.get("WEIGHT");
			structure=results.get("STRU")==null?"":results.get("STRU");
			count=Integer.parseInt(structure.substring(0, structure.indexOf("/")));
		}
		if(diaSingle.equals("")||materialDia.equals("")||materialWeight.equals("")||structure.equals("")){
			logger.info("物料"+mpartNo+"缺少必要属性");
			return;
		}
		//2.根据改动的物料找出所有涉及的工艺路线
		//List<Prcv> prcvs=prcvService.getRelatedPrcvByMpart(mpartNo);
		
		List<Prcv> prcvs=prcvService.getPrcvByProductNo("CZ50500370139-12*4");
		
		//3.循环所找到的工艺路线
		for(Prcv prcv:prcvs){
			//4.copy一份该工艺路线
			//copyOldVersion(prcv,logger);
			List<Process> processLists=processService.getAsyncDataList(prcv.getId());
			List<Product> products= null;
					// productDAO.getProductByPrcvNo(prcv.getNo());
			if(products!=null && products.size()>0){
				String productNo=products.get(0).getProductCode();
				jueyuanNum=parseJueYuanCount(productNo);
			}else{
				logger.info("工艺"+prcv.getNo()+"找不到对应产品！");
				continue;
			}
		//5.1 第一次循环processLists,首先判断是否有云母带绕包			
			for(Process process:processLists){
				String processName=process.getName();
				String csvalue2 = process.getCsvalue2() == null ? "" : process.getCsvalue2();
				String csvalue3 = process.getCsvalue3() == null ? "" : process.getCsvalue3();
				String csvalue1 = process.getCsvalue1() == null ? "" : process.getCsvalue1();
				String csvalue=csvalue1+csvalue2+csvalue3;
				if(StringUtils.isBlank(csvalue)){
					continue;
				}
				if(processName.equals("云母带立式绕包两层")){					
					num=2;
					//绕包后外径
				    wrappingDia=Double.valueOf(materialDia)+0.14*3*2;
					String[] strs={"LRB2-011-005="+materialWeight,"LRB2-011-006="+materialDia,"LRB2-012-005="+materialDia,
							"LRB2-013-006="+materialDia,"LRB2-014-004="+materialDia,"LRB2-011-015="+diaSingle,"LRB2-012-010="+diaSingle,
							"LRB2-013-015="+diaSingle,"LRB2-011-028="+wrappingDia,"LRB2-012-020="+wrappingDia,"LRB2-013-027="+wrappingDia,
							"LRB2-014-017="+wrappingDia,"LRB2-011-007="+structure,"LRB2-012-006="+structure,"LRB2-013-008="+structure,
							"LRB2-014-005="+structure};
					String nCsvalue=updateString(csvalue,strs);
					setCsvalueIntoProcess(process,nCsvalue);
					processDAO.updateCsValue1(process);
				}
				if(processName.equals("云母带立式绕包单层")){
					num=1;
					//绕包后外径
				    wrappingDia=Double.valueOf(materialDia)+0.14*3*1;
				    String[] strs={"LRB-011-005="+materialWeight,"LRB-011-006="+materialDia,"LRB-012-005="+materialDia,
							"LRB-013-005="+materialDia,"LRB-014-004="+materialDia,"LRB-011-015="+diaSingle,"LRB-012-010="+diaSingle,
							"LRB-013-012="+diaSingle,"LRB-011-028="+wrappingDia,"LRB-012-020="+wrappingDia,"LRB-013-022="+wrappingDia,
							"LRB-014-017="+wrappingDia,"LRB-011-007="+structure,"LRB-012-006="+structure,"LRB-013-006="+structure,
							"LRB-014-005="+structure};
					String nCsvalue=updateString(csvalue,strs);
					setCsvalueIntoProcess(process,nCsvalue);
					processDAO.updateCsValue1(process);
				}
			}
			
			//5.2 第二次循环
			for(Process process:processLists){
				String processName=process.getName();
				String csvalue2 = process.getCsvalue2() == null ? "" : process.getCsvalue2();
				String csvalue3 = process.getCsvalue3() == null ? "" : process.getCsvalue3();
				String csvalue1 = process.getCsvalue1() == null ? "" : process.getCsvalue1();
				String csvalue=csvalue1+csvalue2+csvalue3;
				if(StringUtils.isBlank(csvalue)){
					continue;
				}
				if(processName.equals("挤出-单层")){
					List<MpartInOut> lists=mpartInOutDAO.getMaterialJYByProcessId(process.getId());
					if(lists!=null && lists.size()>0){
						Map<String,String> param=mpartInOutDAO.getMatJYSpecById(lists.get(0).getId());
						jueYuanSpec=param.get("SPEC")==null?"":param.get("SPEC");
					}else{
						logger.info("绝缘工序下找不到绝缘料");
					}
					Map<String,String> result=processDAO.getJueyuanPropById(process.getId());
					jueyuanBe=wrappingDia==0?Double.valueOf(materialDia):wrappingDia;
					standardThickness=result.get("STANDARD")==null?"":result.get("STANDARD");
					maxThickness=result.get("MAX")==null?"":result.get("MAX");
					minThickness=result.get("MIN")==null?"":result.get("MIN");
					guidThickness=result.get("GUID")==null?"":result.get("GUID");
					if(standardThickness.equals("")||maxThickness.equals("")||minThickness.equals("")||
							guidThickness.equals("")){
						logger.info("绝缘工序缺少四厚度之一");
					}
					//绝缘标称外径：
					jueyuanStandard=jueyuanBe+Double.parseDouble(standardThickness)*2;
					//绝缘最小外径
					jueyuanMin=jueyuanBe+Double.parseDouble(minThickness)*2;
					//绝缘最大外径
					jueyuanMax=jueyuanBe+Double.parseDouble(maxThickness)*2;
					//导体重量
					double conWeight=new BigDecimal(Double.toString(Math.PI*Double.parseDouble(diaSingle)*Double.parseDouble(diaSingle)*count*8.89/4))
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					//绝缘重量
					double jyWeight=new BigDecimal(Math.PI*(jueyuanBe+Double.parseDouble(standardThickness))*Double.parseDouble(standardThickness)*1.03*Double.parseDouble(jueYuanSpec))
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					if(num>0){
						//耐火绕包重量
						double naihuoRaoBaoWeight=new BigDecimal(Double.toString(Math.PI*(Double.parseDouble(materialDia)+0.14*num)*0.14*num*1.3/0.85))
												.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();	
						proWeight=conWeight+jyWeight+naihuoRaoBaoWeight;
					}else{
						proWeight=conWeight+jyWeight;
					}		
					String[] strs={"JCD-11-005="+materialWeight,"JCD-11-006="+materialDia,"JCD-12-005="+materialDia,
							"JCD-13-005="+materialDia,"JCD-14-004="+materialDia,"JCD-11-015="+diaSingle,"JCD-12-010="+diaSingle,
							"JCD-13-012="+diaSingle,"JCD-11-020="+jueyuanBe,"JCD-12-015="+jueyuanBe,
							"JCD-13-016="+jueyuanBe,"JCD-14-009="+jueyuanBe,"JCD-11-022="+jueyuanStandard,
							"JCD-12-019="+jueyuanStandard,"JCD-13-018="+jueyuanStandard,"JCD-14-011="+jueyuanStandard,
							"JCD-11-048="+jueyuanMin,"JCD-12-035="+jueyuanMin,"JCD-13-042="+jueyuanMin,"JCD-14-025="+jueyuanMin,
							"JCD-11-050="+jueyuanMax,"JCD-12-037="+jueyuanMax,"JCD-13-044="+jueyuanMax,"JCD-14-027="+jueyuanMax,
							"JCD-11-007="+structure,"JCD-12-006="+structure,"JCD-13-006="+structure,"JCD-11-028="+proWeight
					};
					String nCsvalue=updateString(csvalue,strs);
					setCsvalueIntoProcess(process,nCsvalue);
					processDAO.updateCsValue1(process);
				}
				if(processName.equals("成缆")){
					//绞合外径=绝缘指导外径*成缆外径比
					cablingDia=jueyuanStandard*cablingParam.get(jueyuanNum);
					String[] strs={"CL-011-001="+structure,"CL-012-001="+structure,"CL-013-001="+structure,
							"CL-011-002="+diaSingle,"CL-012-002="+diaSingle,"CL-013-002="+diaSingle,
							"CL-011-005="+jueyuanStandard,"CL-012-005="+jueyuanStandard,"CL-013-005="+jueyuanStandard,
							"CL-014-002="+jueyuanStandard,"CL-011-012="+cablingDia,"CL-013-012="+cablingDia,"CL-014-008="+cablingDia
					};
					if(processName.contains("层")){
						String cablingCount=processName.substring(2, 3);										
						String cablingNum=mapping.get(cablingCount);
						//转换属性名称：例如CL-011-001可以转化为CL2-011-001
						strs=parseStringArray(strs,cablingNum);
					}					
					String nCsvalue=updateString(csvalue,strs);
					setCsvalueIntoProcess(process,nCsvalue);
					processDAO.updateCsValue1(process);					
				}
				if(processName.startsWith("绕包")){
					if(processName.equals("绕包一层")){
						//String postRBDia=
					}
				}
			}
		}
		
	
	}
	
	public String[] parseStringArray(String[] strs, String cablingNum) {
		List<String> lists=new ArrayList<String>();
		for(String str:strs){
			int start=str.indexOf("-");
			String newStr=str.substring(0, start)+cablingNum+str.substring(start);
			lists.add(newStr);
		}
		return (String[])lists.toArray();
	}

	public void mappingParam(Map<String, String> mapping) {
		mapping.put("二", "2");
		mapping.put("三", "3");
		mapping.put("四", "4");
		mapping.put("五", "5");
		mapping.put("六", "6");
		
	}

	public int parseJueYuanCount(String productNo) {
		int jueyuanNum=1;
		String spec=productNo.substring(productNo.indexOf("-")+1, productNo.length());
		if(spec.contains("+")){
			jueyuanNum=0;
			for(String s:spec.split("\\+")){
				jueyuanNum+=parseJueYuanCount(s);
			}
		}else{
			Pattern p=Pattern.compile("\\*[^*]{1,10}$");
			Matcher m=p.matcher(productNo);
			m.find();
			String jueyuanNumStr=productNo.substring(productNo.indexOf("-")+1, m.start());
			for(String str:jueyuanNumStr.split("\\*")){
				jueyuanNum=jueyuanNum*Integer.parseInt(str);
			}		
		}
		return jueyuanNum;
		
		
	}

	public void initCablingParam(Map<Integer, Double> param) {
		param.put(1, 1.0);
		param.put(2, 2.0);
		param.put(3, 2.154);
		param.put(4, 2.414);
		param.put(5, 2.7);
		param.put(6, 3.0);
		param.put(7, 3.0);
		param.put(8, 3.3);
		param.put(9, 3.7);
		param.put(10, 4.0);
		param.put(11, 4.154);
		param.put(12, 4.154);
		param.put(13, 4.414);
		param.put(14, 4.414);
		param.put(15, 4.7);
		param.put(16, 4.7);
		param.put(17, 5.0);
		param.put(18, 5.0);
		param.put(19, 5.0);
		param.put(20, 5.154);
		param.put(21, 5.3);
		param.put(22, 5.7);
		param.put(23, 6.0);
		param.put(24, 6.0);
		param.put(25, 6.154);
		param.put(26, 6.154);
		param.put(27, 6.154);
		param.put(28, 6.414);
		param.put(29, 6.414);
		param.put(30, 6.414);
		param.put(31, 6.7);
		param.put(32, 6.7);
		param.put(33, 6.7);
		param.put(34, 7.0);
		param.put(35, 7.0);
		param.put(36, 7.0);
		param.put(37, 7.0);
		param.put(38, 7.3);
		param.put(39, 7.3);
		param.put(40, 7.3);
	}

	private void setCsvalueIntoProcess(Process process, String csvalue) {
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
	}

	private String updateString(String csvalue, String[] strs) {
			String newStr=csvalue;
			for(String s:strs){
				String tag=s.substring(0,s.indexOf("="));
				if(csvalue.contains(tag)){
					int begin=csvalue.indexOf(tag);
					String str=csvalue.substring(begin, csvalue.indexOf("^", begin));
					newStr=newStr.replaceAll(str, s);
				}
			}
			return newStr;
		}


	private void copyOldVersion(Prcv prcv, Logger logger) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		PrcvObjof prcvObjof=null;
		String prcvId=prcv.getId();
		String nPrcvId="01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		String designNo = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		List<Prcv> prcvs=prcvService.getAllVersionPrcv(prcv.getNo());
		//版本号
		int version=0;
		if(prcvs.size()>0){
			for(Prcv p: prcvs){
				if(p.getNo().contains("_V")){
					String vString=p.getNo().substring(p.getNo().indexOf("V")+1, p.getNo().length());					
					if(Integer.parseInt(vString)>version){
						version=Integer.parseInt(vString);
					}
				}else{
					continue;
				}
			}		
		}
		List<PrcvObjof> prcvObjofs=prcvObjofDAO.getByPrcv(prcv.getId());
		List<Process> processLists=processService.getAsyncDataList(prcv.getId());
		List<ProcessProcessObjof> processProcessObjofLists = processProcessObjofDAO.getByPrcvId(prcv.getId());
		if(processProcessObjofLists==null || processProcessObjofLists.size()==0){
			logger.info("找不到process_process对应关系");
			return;
		}
		if(prcvObjofs!=null && prcvObjofs.size()>0){
			prcvObjof=prcvObjofs.get(0);
		}else{
			logger.info("找不到prcvObjof对象");
			return;
		}		
		//复制工艺prcv记录
		//Prcv nprcv=(Prcv) BeanUtils.cloneBean(prcv);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prcvOldid", prcvId);
		paramMap.put("prcvNewId", nPrcvId);
		paramMap.put("newPrcvNo", prcv.getNo()+"_V"+(++version));
		paramMap.put("prcvDesignno", designNo);
		prcvDAO.insertPrcvByCopy(paramMap);
		//复制工艺路线和产品的对应关系记录
		prcvObjof.setId("01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase());
		prcvObjof.setDesignno(designNo);
		prcvObjof.setCtime(new Date());
		prcvObjof.setItemid2(nPrcvId);
		prcvObjofDAO.insertPrcvObjof(prcvObjof);
		//复制工艺路线下的工序
		List<Process> nProcesses=new ArrayList<Process>();
		List<ProcessObjof> nProcessObjof=new ArrayList<ProcessObjof>();
		for(Process process:processLists){
			String processId=process.getId();
			String nProcessId="01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
			process.setId(nProcessId);
			process.setCtime(new Date());
			process.setCreator("MES");
			process.setMtime(new Date());
			nProcesses.add(process);
			
			//找出原来processId下对应的输入输出物料记录
			List<MpartObjof> mpartObjof=mpartObjofDAO.findByProcessId(processId);
			List<MpartObjof> mpart2Objof=mpartObjofDAO.findMpartoutByProcessId(processId);
			List<MpartObjof> nMpartObjofIn=new ArrayList<MpartObjof>();
			List<MpartObjof> nMpartObjofOut=new ArrayList<MpartObjof>();
			//把新复制的mpart_objof对象放入list中
			pack(mpartObjof,nMpartObjofIn,nProcessId);
			pack(mpart2Objof,nMpartObjofOut,nProcessId);
			if(nMpartObjofIn.size()>0){
				mpartObjofDAO.insertBatchIn(nMpartObjofIn);
			}		
			if(nMpartObjofOut.size()>0){
				mpartObjofDAO.insertBatchOut(nMpartObjofOut);
			}

			List<ProcessObjof> processObjofs=processObjofDAO.getByProcessId(processId);
			if(processObjofs!=null && processObjofs.size()>0){
				ProcessObjof processObjof=processObjofs.get(0);
				processObjof.setId("01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase());
				processObjof.setCtime(new Date());
				processObjof.setItemid1(nPrcvId);
				processObjof.setItemid2(nProcessId);
				nProcessObjof.add(processObjof);
			}			
			//复制process_process_Objof记录
			for(ProcessProcessObjof p:processProcessObjofLists){
				p.setId("01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase());
				p.setCtime(new Date());
				if (p.getItemid1().equals(processId)) {
					p.setItemid1(nProcessId);
					continue;
				}
				if (p.getItemid2().equals(processId)) {
					p.setItemid2(nProcessId);
					continue;
				}
				
			}			
		}
		//批量插入数据
		processObjofDAO.insertBatch(nProcessObjof);
		processService.insertBatch(nProcesses);
		processProcessObjofDAO.insertBatch(processProcessObjofLists);
		Prcv nprcv=prcvDAO.getPrcvByNo(prcv.getNo()+"_V"+version).get(0);
		try {
			addPrcvXmlTask.addByPrcv(nprcv);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void pack(List<MpartObjof> mpartObjof,
			List<MpartObjof> nMpartObjofIn,String nProcessId) {
		if(mpartObjof!=null && mpartObjof.size()>0){
			for(MpartObjof m:mpartObjof){
				m.setId("01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase());
				m.setCtime(new Date());
				m.setItemid1(nProcessId);
				nMpartObjofIn.add(m);
			}
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
	

	private static String[] tempStrArray = {""};


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
				processDAO.deleteMpartByProcessId(processId);
				processDAO.deleteMpart2ByProcessId(processId);
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
				processDAO.insertMpartObj(map1);
					if (!mpartConsume.trim().equals("") || !mpartAmount.trim().equals("")) {
						try{
							processDAO.updateMpartObj(map1);
						}catch(Exception e){
							System.out.println("工序"+prcvNo+gno+"输入物料数量有误");
						}
						
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
				processDAO.insertMpartObj2(map1);
				if (!mpartAmount.trim().equals("")) {
					processDAO.updateMpartObj2(map1);
				}
			}

			// 及时更新工艺的更新时间，利于mes及时对工艺进行同步
			/*List<Prcv> prcvList=prcvDAO.getPrcvByPrcvNoAndProNo(cache);
			prcvDAO.updateModifyTimeByPrcvId(prcvList.get(0).getId());*/
		}
	}

	private void mpartAnalySheet(Sheet sheet) {
		//int count=0;
		if (sheet.getRow(2) == null) {
			return;
		}
		int maxRow = sheet.getLastRowNum() + 1;
		int maxCol = sheet.getRow(0).getLastCellNum();
		for (int i = 2; i < maxRow; i++) {
			//System.out.println(sheet.getSheetName()+"第"+i+"行");
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
				System.out.println(no +  "\t" + "在plm中没有模板数据");
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
				mpartInOutDAO.updateCsvalue1(mpart);
				/*count++;
				System.out.println(count);*/
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
			
			//判断系统中有没有该产品
			if (list.size() == 0 || list == null) {
				System.out.println("产品" + "\t" +no+ "\t" + "无法找到！");
				continue;
			}
			if (list.size() > 1) {
				System.out.println("产品"+ "\t" + no + "\t" + "有多条产品记录");
				continue;
			}
			
			//在没有工艺模板的情况下
			if (prcvNo.equals("")) {
				//必须先在PLM系统中建立好工艺路线图
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
							//System.out.println("该产品" + "\t" + no + "\t" + "关联其他工艺路线并已删除该对应关系");
						}
						String prcvObjofId = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
						String designNo = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
						String prcvId = prcvDAO.checkPrcvByNo(newPrcvNo).get(0).getId();
						PrcvObjof prcvObjof = new PrcvObjof();
						prcvObjof.setId(prcvObjofId);
						prcvObjof.setDesignno(designNo);
						prcvObjof.setItemid1(productId);
						prcvObjof.setItemid2(prcvId);
						// 建立正确的关联关系
						prcvObjofDAO.insertPrcvObjof(prcvObjof);
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
				plmProductService.updatePro(product);
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
		if("CZ50100440235-21*0.75".equals(no)){
			System.out.println(no);
		}
		System.out.println("copyPrcv:" + prcvNo  + no  + "----" + DateUtils.convert(new Date()));
		
		//判断是否需要修改prcv中fname的值
		List<Prcv> contains=prcvDAO.checkPrcvContain(prcvNo);
		if(null!=contains&&contains.size()>0){
			prcvDAO.changePrcvFname(prcvNo);
		}
		// 如果需要依靠工艺路线模板生成新的工艺路线，把plm系统里已经做过的工艺路线视为垃圾数据并且删除
		List<Prcv> newPrcvList = prcvDAO.checkPrcvByNo(newPrcvNo);
		if (prcvDAO.checkNoUseData(newPrcvNo).size() > 0 && !newPrcvNo.equals(prcvNo)) {
			/*System.out.println("存在需要删除的工艺路线" + "\t" +newPrcvNo+ "\t" + "请输入1确认：");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String data = "0";
			if (data.equals("1")) {*/
				scxDAO.deleteScxByPrcvNo(newPrcvNo);
				scxObjofDAO.deleteScxObjofByPrcvNo(newPrcvNo);
				mpartObjofDAO.deleteMpartObjofByPrcvNo(newPrcvNo);
				mpartObjofDAO.deleteMpartObjofByPrcvNo2(newPrcvNo);
				processProcessObjofDAO.deletePPObjofByPrcvNo(newPrcvNo);
				processDAO.deleteProcessByPrcvNo(newPrcvNo);
				processObjofDAO.deleteProcessObjofByPrcvNo(newPrcvNo);
				prcvObjofDAO.deletePrcvObjofByPrcvNo(newPrcvNo);
				prcvDAO.deletePrcvByPrcvNo(newPrcvNo);
			/*}*/
		}
		// 如果系统中存在新的工艺路线，先判断该存在的工艺路线是否与改产品关联，如果没有关联，但是该产品下关联了其他的工艺路线id，需要删除
		if (newPrcvList.size() > 0) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("newPrcvNo", newPrcvNo);
			paramMap.put("productId", productId);
			if (prcvDAO.checkExistPrcvObjof(paramMap).size() == 0) {
				if (prcvObjofDAO.getByProductId(productId) != null) {
					prcvObjofDAO.deleteByProuductId(productId);
					//System.out.println("该产品" + "\t" +no+ "\t" + "关联其他工艺路线");
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
			System.out.println("找不到" + "\t" +prcvNo + "\t" + "工艺路线下的工序");
			return;
		} else {
			// 获模板工艺路线下的process_process关系
			List<ProcessProcessObjof> processProcessObjofLists = processProcessObjofDAO.getByPrcvId(prcvOldid);

			String processProcessObjofNewDesignno = "01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
			// 复制一份
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
				// 把新复制出来的process_process信息，替换掉里面的id
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
					System.out.println(prcvNo + "\t" + "没有找到工序对应工序数据");
					return;
				}
				// 添加工序信息
				processDAO.insertProcessByCopy(paramMap1);
				// 添加工艺，工序之间的关联信息
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
		for(int k=3;k<20;k++){
			if(getRowValue(sheet.getRow(0).getCell(k)).contains("-")){
				c=k;
				break;
			}
		}
		/*try{*/
		if(getRowValue(sheet.getRow(0).getCell(c-1)).equals("工序类型")){
			d=true;			
		}
		/*}catch(Exception e){
			System.out.println(sheet.getSheetName()+":"+c);
		}*/
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
			processDAO.updateCsValue1(process);

			// 及时更新工艺的更新时间，利于mes及时对工艺进行同步
			//prcvDAO.updatePrcvModifyTime(productNo);
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
	
	public void init(){
		String[] CL={"CL-001-009"};
		String[] CL2={"CL2-001-009","CL2-001-016"};
		String[] CL3={"CL3-001-009","CL3-001-016","CL3-001-023"};
		String[] CL4={"CL4-001-009","CL4-001-016","CL4-001-023","CL4-001-030"};
		String[] CL5={"CL5-001-009","CL5-001-016","CL5-001-023","CL5-001-030","CL5-001-037"};
		String[] CL6={"CL6-001-009","CL6-001-016","CL6-001-023","CL6-001-030","CL6-001-037","CL6-001-044"};
		processMap.put("成缆",CL);
		processMap.put("成缆二层",CL2);
		processMap.put("成缆三层",CL3);
		processMap.put("成缆四层",CL4);
		processMap.put("成缆五层",CL5);
		processMap.put("成缆六层",CL6);
		CLMap.put("009","CL");
		CLMap.put("016","CL2");
		CLMap.put("023","CL3");
		CLMap.put("030","CL4");
		CLMap.put("037","CL5");
		CLMap.put("044","CL6");
		clImageMap.put("CL-001-009","一层");
		clImageMap.put("CL2-001-009","一层");
		clImageMap.put("CL3-001-009","一层");
		clImageMap.put("CL4-001-009","一层");
		clImageMap.put("CL5-001-009","一层");
		clImageMap.put("CL6-001-009","一层");
		clImageMap.put("CL2-001-016","二层");
		clImageMap.put("CL3-001-016","二层");
		clImageMap.put("CL4-001-016","二层");
		clImageMap.put("CL5-001-016","二层");
		clImageMap.put("CL6-001-016","二层");
		clImageMap.put("CL3-001-023","三层");
		clImageMap.put("CL4-001-023","三层");
		clImageMap.put("CL5-001-023","三层");
		clImageMap.put("CL6-001-023","三层");
		clImageMap.put("CL4-001-030","四层");
		clImageMap.put("CL5-001-030","四层");
		clImageMap.put("CL6-001-030","四层");
		clImageMap.put("CL5-001-037","五层");
		clImageMap.put("CL6-001-037","五层");
		clImageMap.put("CL6-001-044","六层");	
	}

	//计算文件夹中文件大小
	public static double getSize(File file){
		if(file.exists()){
			if(file.isDirectory()){
				File[] children=file.listFiles();
				double size=0;
				for(File child:children){
					size+=getSize(child);
				}
				return size;
			}else{
				return file.length();
			}
		}else{
			System.out.println("文件不存在");
			return 0;
		}
	}
}
