package cc.oit.bsmes.job.tasks;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;
import cc.oit.bsmes.interfacePLM.model.CanShuKu;
import cc.oit.bsmes.interfacePLM.model.MpartInOut;
import cc.oit.bsmes.interfacePLM.service.CanShuKuService;
import cc.oit.bsmes.interfacePLM.service.MpartInOutService;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.model.MatProp;
import cc.oit.bsmes.inv.model.Templet;
import cc.oit.bsmes.inv.model.TempletDetail;
import cc.oit.bsmes.inv.service.MatPropService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.inv.service.TempletDetailService;
import cc.oit.bsmes.inv.service.TempletService;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;

/**
 * 
 * 参数库
 * <p style="display:none">modifyRecord</p>
 * @author leiw
 * @date 2015-01-11 上午11:39:55
 * @since
 * @version
 */
@Service
public class CanShukuTask  extends BaseSimpleTask {
	
	@Resource private LastExecuteTimeRecordService lastExecuteTimeRecordService;
	@Resource private CanShuKuService canShuKuService;
	@Resource private TempletService templetService;
	@Resource private TempletDetailService templetDetailService;
	@Resource private MpartInOutService mpartInOutService;
	@Resource private MatService matService; 
	@Resource private MatPropService matPropService;

	private Map<String,String> tempDetailCache = new HashMap<String, String>();
	private Map<String,Templet> tempCache=new HashMap<String,Templet>();
	private Map<String,String> csvalueMap = new HashMap<String, String>();

	private void init(){
		List<TempletDetail> tdList = templetDetailService.getAll();
		for (TempletDetail templetDetail : tdList) {
			tempDetailCache.put(templetDetail.getPropCode(),templetDetail.getId());
		}
		List<Templet> tempList=templetService.getAll();
		for (Templet templet : tempList) {
			tempCache.put(templet.getName(),templet);
		}
	}

	private Date asyncCanShuKu(LastExecuteTimeRecord tempRecord){
		List<CanShuKu> paramList=canShuKuService.getRealCanShuKu(tempRecord.getLastExecuteTime());
		Date lastExecuteDate = tempRecord.getLastExecuteTime();
		for (CanShuKu canShuKu : paramList) {

			Templet temp = tempCache.get(canShuKu.getZtype());
			if(temp == null){
				temp=new Templet();
				temp.setName(canShuKu.getZtype());
				temp.setOrgCode(templetService.getOrgCode());
				templetService.insert(temp);
				tempCache.put(canShuKu.getZtype(),temp);
			}
			//同步物料模板属性
			updateTempletDetail(canShuKu, temp);

			if(canShuKu.getMtime() == null){
				canShuKu.setMtime(canShuKu.getCtime());
			}
			
			if(lastExecuteDate == null){
				lastExecuteDate = canShuKu.getCtime();
			}
			
			if(lastExecuteDate.before(canShuKu.getCtime())){
				lastExecuteDate = canShuKu.getCtime();
			}
			if(lastExecuteDate.before(canShuKu.getMtime())){
				lastExecuteDate = canShuKu.getMtime();
			}
		}
		return lastExecuteDate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void process(JobParams parms) {
		LastExecuteTimeRecord tempRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.TEMPLET);
		LastExecuteTimeRecord matRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.MPART);

		//初始化缓存数据
		init();
		//同步模板、模板属性
		Date lastExecuteDate = asyncCanShuKu(tempRecord);
		//同步物料，物料属性
		Date lastExecuteDateMat = updateMat(matRecord.getLastExecuteTime());

		tempDetailCache = new HashMap<String, String>();
		tempCache=new HashMap<String,Templet>();
		csvalueMap = new HashMap<String, String>();
		tempRecord.setLastExecuteTime(lastExecuteDate);
		lastExecuteTimeRecordService.saveRecord(tempRecord);

		matRecord.setLastExecuteTime(lastExecuteDateMat);
		lastExecuteTimeRecordService.saveRecord(matRecord);
	}
	private Date updateMat(Date lastExecuteDate) {
		String csvalue="";
		List<MpartInOut> list=mpartInOutService.getRealMpart(lastExecuteDate);
		logger.info("mat len :"+list.size());
		int i = 0;
		for(MpartInOut mpartInOut:list){
			i++;
			if(i%100 == 0){
				System.out.println("执行："+i);
			}
			Mat mat=matService.getByCode(mpartInOut.getNo());
			if(mat == null) {
				mat = new Mat();
			}
			//拆分csvalue字段
			if(StringUtils.isNotBlank(mpartInOut.getCsvalue1())){
				if(StringUtils.isNotBlank(mpartInOut.getCsvalue2())&&StringUtils.isNotBlank(mpartInOut.getCsvalue2())){
					csvalue=mpartInOut.getCsvalue1()+mpartInOut.getCsvalue2()+mpartInOut.getCsvalue3();
				}
				else if(StringUtils.isNotBlank(mpartInOut.getCsvalue2())){
					csvalue=mpartInOut.getCsvalue1()+mpartInOut.getCsvalue2();
				}else{
					csvalue=mpartInOut.getCsvalue1();
				}				
				spileCsvalue(csvalue,mat);
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
			mat.setOrgCode(SessionUtils.getUser().getOrgCode());
			if(StringUtils.isBlank(mat.getId())){
				matService.insert(mat);
			}else{
				matService.update(mat);
			}
			this.updateMatProp(mat.getId());
			csvalueMap.clear();
			
			if(mpartInOut.getMtime() == null){
				mpartInOut.setMtime(mpartInOut.getCtime());
			}
			
			if(lastExecuteDate == null){
				lastExecuteDate = mpartInOut.getCtime();
			}
			
			if(lastExecuteDate.before(mpartInOut.getCtime())){
				lastExecuteDate = mpartInOut.getCtime();
			}
			if(lastExecuteDate.before(mpartInOut.getMtime())){
				lastExecuteDate = mpartInOut.getMtime();
			}
		}
		return lastExecuteDate;
	}
	private void updateTempletDetail(CanShuKu canShuKu, Templet temp) {
		if(StringUtils.isBlank(canShuKu.getNo())){
			return;
		}
		TempletDetail tempDetail=new TempletDetail();
		tempDetail.setPropCode(canShuKu.getNo());
		tempDetail.setTempletId(temp.getId());
		String detailId = tempDetailCache.get(tempDetail.getPropCode());
		if(StringUtils.isBlank(detailId)){
			if(StringUtils.isNotBlank(canShuKu.getName())){
				tempDetail.setPropName(canShuKu.getName());
			}
			tempDetail.setPropIsRange(false);
			templetDetailService.insert(tempDetail);
			tempDetailCache.put(tempDetail.getPropCode(),tempDetail.getId());
		}else{
			if(StringUtils.isNotBlank(canShuKu.getName())){
				tempDetail.setId(detailId);
				tempDetail.setPropName(canShuKu.getName());
				templetDetailService.update(tempDetail);
			}
		}
	}

	public void updateMatProp(String matId){
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
				// 旧的删掉
				List<MatProp> list = matPropService.findByObj(matProp);
				for(MatProp p : list){
					matPropService.delete(p);
				}
				matProp.setPropTargetValue(csvalueMap.get(key));
				matPropService.insert(matProp);
			}

		}
	}

	private void spileCsvalue(String csvalue,Mat mat){
		 String[] resultCsValue=csvalue.trim().split("\\^");
		 for (String result : resultCsValue) {
			 String[] key_val = result.trim().split("=");
			 if(key_val.length>1){
				 csvalueMap.put(key_val[0],key_val[1]);
				 if((StringUtils.equals(key_val[0], "BJY-001-001")|| StringUtils.equals(key_val[0], "JY-001-004")||
						 StringUtils.equals(key_val[0], "BYZ-001-002"))){
					 if(StringUtils.isNotBlank(key_val[1])){
						 mat.setColor(key_val[1].replace("双色","").replace("色",""));
					 }
					 System.out.println(mat.getColor() + "---------");
				 }
				 if(StringUtils.equals(key_val[0], "JT-001-002") || StringUtils.equals(key_val[0],"DT-001-001")){
					 mat.setMatSpec(key_val[1]);
					 System.out.println(mat.getMatSpec() + "---------");
				 }
			 }
		 }
	}
}
