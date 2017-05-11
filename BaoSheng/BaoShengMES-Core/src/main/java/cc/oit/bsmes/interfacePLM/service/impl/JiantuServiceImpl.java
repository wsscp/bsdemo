package cc.oit.bsmes.interfacePLM.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.Desf2DAO;
import cc.oit.bsmes.interfacePLM.dao.JiantuDAO;
import cc.oit.bsmes.interfacePLM.dao.ModDAO;
import cc.oit.bsmes.interfacePLM.dao.MpartInOutDAO;
import cc.oit.bsmes.interfacePLM.model.Desf2;
import cc.oit.bsmes.interfacePLM.model.Jiantu;
import cc.oit.bsmes.interfacePLM.model.Mod;
import cc.oit.bsmes.interfacePLM.model.MpartInOut;
import cc.oit.bsmes.interfacePLM.model.PLMProduct;
import cc.oit.bsmes.interfacePLM.service.JiantuService;
import cc.oit.bsmes.interfacePLM.service.PLMProductService;

@Service
public class JiantuServiceImpl extends BaseServiceImpl<Jiantu> implements JiantuService {
	
	private static final String url="\\\\172.168.0.81";
	private static final String path="\\ALLDOC\\14";
	private static final int BUF_SIZE = 8192;
	
	@Resource 
	private JiantuDAO jiantuDAO;
	
	@Resource
	private MpartInOutDAO mpartInOutDAO;
	
	@Resource
	private Desf2DAO desf2DAO;
	
	@Resource
	private PLMProductService plmProductService;
	
	@Resource
	private ModDAO modDAO;
	
	

	@Override
	public List<Jiantu> getJiantuByMpartId(String mpartId) {
		
		return jiantuDAO.getJiantuByMpartId(mpartId);
	}

	@Override
	public List<Jiantu> getAllMaterialJiantu() {
		return jiantuDAO.getAllMaterialJiantu();
	}

	@Override
	//designNo设计号;type(半成品，二维图，三维图，成缆截面图);halfProNo半成品编码/PLM中产品名称
	public void upload(File file,String designNo, String type,String halfProNo) throws IOException {		
		String fname=file.getName();	
		String realFileName="01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase()+".jpg";
		String location=path+"\\"+realFileName;
		File outFile=new File(url+path+"\\"+realFileName);
		int fsize=Integer.parseInt(String.valueOf(file.length()));
		Map<String,String> paramObjof=new HashMap<String,String>();
		PLMProduct product=null;
		//若传入类型是二维图或三维图，首先查询该产品信息
		if(type.equals("二维图")||type.equals("三维图")){
			List<PLMProduct> products=plmProductService.getPLMProductByProductName(halfProNo);
			if(products!=null && products.size()>0){
				product=products.get(0);
			}else{
				logger.info("产品"+halfProNo+"在PLM中无法找到!");
				return;
			}
		}
		if(type.equals("半成品")){
			List<MpartInOut> mparts=mpartInOutDAO.getMpartByName(halfProNo);
			String mpartId="";
			if(mparts!=null && mparts.size()>0){
				mpartId=mparts.get(0).getId();
			}else{
				System.out.println("半成品"+halfProNo+"在plm中无数据");
				return;
			}
			List<Jiantu> jiantus=this.getJiantuByMatName(halfProNo);
			//如果plm中已经存在，则删除改图片和其对应关系，并文件服务器上的图片
			if(jiantus.size()>0){
				jiantuDAO.deleteById(jiantus.get(0).getId());
				jiantuDAO.deleteObjofByJiantuId(jiantus.get(0).getId());
				File f=new File(url+jiantus.get(0).getLocation());
				if(f.exists()){
					f.delete();
				}
			}										
			//文件服务器中创建图片文件
			this.createImageFile(file,outFile);			
			//把数据插入jiantu
			Jiantu param=new Jiantu();
			String jiantuId="01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
			param.setId(jiantuId);
			param.setDel("0");
			param.setMsym("N");
			param.setWkaid("1");
			param.setDesignno(designNo);
			param.setBldesignno(designNo);
			param.setNo(halfProNo);
			param.setVer("1");
			param.setFname(fname);
			param.setLocation(location);
			param.setOwner("admin");
			param.setState("A");
			param.setFsize(fsize);
			this.insertJiantu(param);
			//建立物料简图关系			
			paramObjof.put("id","01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase());
			paramObjof.put("mpartId", mpartId);
			paramObjof.put("jiantuId", jiantuId);
			paramObjof.put("designNo", designNo);
			this.insertJiantuObjof(paramObjof);
			//System.out.println(halfProNo+"已生成");
		}
		if(type.equals("二维图")){						
			List<Desf2> desf2s=desf2DAO.getByProductNo(product.getNo());
			if(desf2s!=null && desf2s.size()>0){
				desf2DAO.deleteByProductNo(product.getNo());
				desf2DAO.deleteObjofByProductId(product.getId());
				File f=new File(url+desf2s.get(0).getLocation());
				f.deleteOnExit();
			}
			this.createImageFile(file, outFile);
			//把数据插入DESF2
			Desf2 param=new Desf2();
			String desf2Id="01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
			param.setId(desf2Id);
			param.setDesignno(designNo);
			param.setBldesignno(designNo);
			param.setNo(product.getNo());
			param.setName(product.getName());
			param.setFname(fname);
			param.setLocation(location);
			param.setFsize(fsize);
			desf2DAO.insertDesf2(param);			
			//建立产品和desf2的关系
			paramObjof.put("id","01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase());
			paramObjof.put("productId", product.getId());
			paramObjof.put("desf2Id", desf2Id);
			paramObjof.put("designNo", designNo);
			desf2DAO.insertDesf2Objof(paramObjof);
		}
		if(type.equals("三维图")){
			List<Mod> mods=modDAO.getByProductNo(product.getNo());
			if(mods!=null && mods.size()>0){
				modDAO.deleteByProductNo(product.getNo());
				modDAO.deleteObjofByProductId(product.getId());
				File f=new File(url+mods.get(0).getLocation());
				f.deleteOnExit();
			}
			
			this.createImageFile(file, outFile);
			//把数据插入MOD
			Mod param=new Mod();
			String modId="01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
			param.setId(modId);
			param.setDesignno(designNo);
			param.setBldesignno(designNo);
			param.setNo(product.getNo());
			param.setName(product.getName());
			param.setFname(fname);
			param.setLocation(location);
			param.setFsize(fsize);
			modDAO.insertMod(param);
			//建立mod和产品的关系
			paramObjof.put("id","01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase());
			paramObjof.put("productId", product.getId());
			paramObjof.put("modId", modId);
			paramObjof.put("designNo", designNo);
			modDAO.insertModObjof(paramObjof);
		}
		
	}

	@Override
	public List<Jiantu> getJiantuByMatName(String name) {		
		return jiantuDAO.getJiantuByMatName(name);
		
	}

	@Override
	public void insertJiantu(Jiantu param) {		
		jiantuDAO.insertJiantu(param);
		
	}
	
	public void insertJiantuObjof(Map<String,String> param){
		jiantuDAO.insertJiantuObjof(param);
	}

	public void createImageFile(File inFile,File outFile) throws IOException{
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		if(outFile.exists()){
			outFile.delete();
			outFile.createNewFile();
		}else{
			outFile.createNewFile();
		}
		try{
			bufferedInputStream=new BufferedInputStream(new FileInputStream(inFile));
			bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(outFile));
			final byte[] temp=new byte[BUF_SIZE];
			int readByte=0;
			while((readByte=bufferedInputStream.read(temp))!=-1){
				bufferedOutputStream.write(temp, 0, readByte);
			}
				bufferedOutputStream.flush();
		}finally{
			if(bufferedOutputStream!=null){
				bufferedOutputStream.close();
			}
			if(bufferedInputStream!=null){
				bufferedInputStream.close();
			}
		}
	}


	


}
