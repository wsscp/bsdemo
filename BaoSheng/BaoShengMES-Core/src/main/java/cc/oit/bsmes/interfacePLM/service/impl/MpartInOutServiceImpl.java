package cc.oit.bsmes.interfacePLM.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.MpartInOutDAO;
import cc.oit.bsmes.interfacePLM.model.MpartInOut;
import cc.oit.bsmes.interfacePLM.service.MpartInOutService;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.model.MatProp;
import cc.oit.bsmes.inv.model.Templet;
import cc.oit.bsmes.inv.model.TempletDetail;
import cc.oit.bsmes.inv.service.MatPropService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.inv.service.TempletDetailService;
import cc.oit.bsmes.inv.service.TempletService;
import cc.oit.bsmes.pro.model.ProcessInOutBz;
import cc.oit.bsmes.pro.service.ProcessInOutBzService;

/**
 * MpartInOutServiceImpl
 * <p style="display:none">
 * 流程投入产出Service实现类
 * </p>
 * 
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
@Service
public class MpartInOutServiceImpl extends BaseServiceImpl<MpartInOut> implements MpartInOutService {
	@Resource
	private MpartInOutDAO mpartInOutDAO;
	@Resource
	private MatService matService;
	@Resource
	private TempletDetailService templetDetailService;
	@Resource
	private TempletService templetService;
	@Resource
	private MatPropService matPropService;
	
	
	private Map<String,String> tempDetailCache = new HashMap<String, String>();
	private Map<String,Templet> tempCache=new HashMap<String,Templet>();
	private Map<String,String> csvalueMap = new HashMap<String, String>();
	
	public void   initTemplet(){
		
		List<TempletDetail> tdList = templetDetailService.getAll();
		for (TempletDetail templetDetail : tdList) {
			tempDetailCache.put(templetDetail.getPropCode(),templetDetail.getId());
		}
		List<Templet> tempList=templetService.getAll();
		for (Templet templet : tempList) {
			tempCache.put(templet.getName(),templet);
		}
	}
	
	
	@Override
	public List<MpartInOut> getRealMpart(Date lastDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lastDate", lastDate);
		return mpartInOutDAO.getRealMpart(map);
	}

	// ----------------------------以下为MES/PLM同步代码
	@Resource
	private ProcessInOutBzService processInOutBzService;

	/**
	 * MES 同步 PLM 工序投入产出数据 <br/>
	 * 根据工艺序ID同步下面的投入产出
	 * 
	 * @author DingXintao
	 * @param processId 工序ID
	 * @return
	 */
	@Override
	public void asyncData(String processId) {
		List<MpartInOut> inoutArray = mpartInOutDAO.getAsyncDataList(processId); // 获取工序下的投入产出
		boolean outflag=false; 
		 processInOutBzService.deleteByProcessId(processId);  
		
		Map<String,String> mapFlag=new HashMap<String,String>();
		if (null != inoutArray && inoutArray.size()>0) {
			for (MpartInOut mpartInOut : inoutArray) {
				if(mpartInOut.getInOut()==InOrOut.OUT)
				{
					if(!outflag)
					{
						//只同步一个产出，避免错误
						this.inOutAsync(mpartInOut, processId); // 同步投入产出信息
						this.inOutProsync(mpartInOut);     //同步投入产出物料属性
						outflag=true;
					}
					
				}else
				{
					String key=processId+mpartInOut.getNo();
					//不允许重复数据
					if(mapFlag.get(key)==null)
					{
						this.inOutAsync(mpartInOut, processId); // 同步投入产出信息
						this.inOutProsync(mpartInOut);     //同步投入产出物料属性
						mapFlag.put(key, key);
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
	 * */
	private void inOutAsync(MpartInOut mpartInOut, String processId) {
        
		ProcessInOutBz processInOutBz = new ProcessInOutBz();
		processInOutBz.setProductProcessId(processId);
		processInOutBz.setMatCode(mpartInOut.getNo());
		processInOutBz.setInOrOut(mpartInOut.getInOut());
		//List<ProcessInOutBz> processInOutBzList = processInOutBzService.getByObj(processInOutBz);
//		if(processInOutBzList!=null && processInOutBzList.size()>0){			
//				processInOutBzService.deleteById(processInOutBzList.get(0).getId());			
//		}
		processInOutBz.setCreateUserCode("PLM");
		Double quantity = mpartInOut.getQuantity() == null ? 1 : mpartInOut.getQuantity();
		processInOutBz.setQuantity(quantity); // 单位投入用量
		processInOutBz.setQuantityFormula(""); // 单位投入用量计算公式 ????
		processInOutBz.setUnit(mpartInOut.getName().indexOf("半成品") >= 0 ? UnitType.KM : UnitType.KG); // 计量单位????
		// STNDCOST 单元成本
		// MTLSTANDNO 材料技术要求
		// SOURCE 制造方式
		// ORG_CODE 数据所属组织
		// STATUS 处理状态
			processInOutBzService.insert(processInOutBz);
		} 
	
	 private void inOutProsync(MpartInOut mpartInOut){
		 //this.init();
		 List<Mat> mats=matService.getByMatCode(mpartInOut.getNo());
        if(mats!=null && mats.size()==1){
        	Mat mat =mats.get(0);
        	 Templet temp =  tempCache.get(mpartInOut.getCstype());
        	 mat.setTempletId(temp == null?"1":temp.getId());
        	 mat.setMatName(StringUtils.isBlank(mpartInOut.getName())?mpartInOut.getNo():mpartInOut.getName());
        	 if(StringUtils.isNotBlank(mpartInOut.getCsvalue1())){
 				spileCsvalue(mpartInOut.getCsvalue1()+mpartInOut.getCsvalue2()+mpartInOut.getCsvalue3(),mat);
 			}
        	 csvalueMap.clear();
        	 matService.update(mat);
			 return;
		 }
		 if(mats!=null && mats.size()>1){
			 for(Mat mat:mats){
				 if(mat.getMatType().equals(MatType.SEMI_FINISHED_PRODUCT)){
					 matPropService.deleteByMatId(mat.getId());
					 matService.delete(mat); 
				 }
				 if(mat.getMatType().equals(MatType.MATERIALS)){
					 return;
				 }
			 }
		 }		
		
		Mat mat = new Mat();
		 if(StringUtils.isNotBlank(mpartInOut.getCsvalue1())){
				spileCsvalue(mpartInOut.getCsvalue1()+mpartInOut.getCsvalue2()+mpartInOut.getCsvalue3(),mat);
			}
		    Templet temp =  tempCache.get(mpartInOut.getCstype());
			mat.setMatCode(mpartInOut.getNo());
			mat.setMatName(StringUtils.isBlank(mpartInOut.getName())?mpartInOut.getNo():mpartInOut.getName());
			mat.setHasPic(false);
			mat.setIsProduct(false);
			mat.setTempletId(temp == null?"1":temp.getId());
			if(StringUtils.contains(mpartInOut.getCstype(), "半成品")){
				mat.setMatType(MatType.SEMI_FINISHED_PRODUCT);
			}else{
				mat.setMatType(MatType.MATERIALS);
			}
			mat.setOrgCode("bstl01");
			//mat.setOrgCode(SessionUtils.getUser().getOrgCode());
				matService.insert(mat);
			this.insertMatProp(mat.getId());
			csvalueMap.clear();
		 
	 }

	private void insertMatProp(String matId) {
		if(csvalueMap.size() > 0){
			Iterator<String> it = csvalueMap.keySet().iterator();
			while (it.hasNext()){
				String key = it.next();
				String detaileId = tempDetailCache.get(key);
				if(StringUtils.isBlank(detaileId)){
					continue;
				}
				MatProp matProp=new MatProp();
				matProp.setMatId(matId);
				matProp.setTempletDetailId(detaileId);
				matProp.setPropTargetValue(csvalueMap.get(key));
				matPropService.insert(matProp);
			}

		}
	
		
		
	}


	private void spileCsvalue(String csvalue, Mat mat) {

		 String[] resultCsValue=csvalue.trim().split("\\^");
		 for (String result : resultCsValue) {
			 String[] key_val = result.trim().split("=");
			 if(key_val.length>1){
				 csvalueMap.put(key_val[0],key_val[1]);
				 if((StringUtils.equals(key_val[0], "BJY-001-001")|| StringUtils.equals(key_val[0], "JY-001-004")||
						 StringUtils.equals(key_val[0], "BYZ-001-002"))){
					 mat.setColor(key_val[1]);
					 System.out.println(mat.getColor() + "---------");
				 }
				 if(StringUtils.equals(key_val[0], "JT-001-002") || StringUtils.equals(key_val[0],"DT-001-001")){
					 mat.setMatSpec(key_val[1]);
					 System.out.println(mat.getMatSpec() + "---------");
				 }
			 }
		 }
	
		
	}


	@Override
	public List<MpartInOut> getAllMpartByProductId(String productId) {
		
		return mpartInOutDAO.getAllMpartByProductId(productId);
	}


	@Override
	public List<MpartInOut> getMpartByName(String name) {
		return mpartInOutDAO.getMpartByName(name);
	}


	@Override
	public Map<String, String> getFieldsByMpartNo(String mpartNo) {
		return mpartInOutDAO.getFieldsByMpartNo(mpartNo);
	}


	@Override
	public Map<String, String> getFieldsByMpartNoDT(String mpartNo) {
		return mpartInOutDAO.getFieldsByMpartNoDT(mpartNo);
	}

}
