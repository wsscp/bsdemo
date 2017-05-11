package cc.oit.bsmes.job.tasks;

import cc.oit.bsmes.bas.service.AttachmentService;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;
import cc.oit.bsmes.interfacePLM.model.Desf2;
import cc.oit.bsmes.interfacePLM.model.PLMProduct;
import cc.oit.bsmes.interfacePLM.service.Desf2Service;
import cc.oit.bsmes.interfacePLM.service.PLMProductService;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PLMProductTask extends BaseSimpleTask {
	
	@Resource private LastExecuteTimeRecordService lastExecuteTimeRecordService;
	@Resource private PLMProductService pLMProductService;
	@Resource private ProductService productService;
	@Resource private Desf2Service desf2Services;
	@Resource private AttachmentService attachmentService;
	@Resource private MatService matService;
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void process(JobParams parms) {
		Date lastExecuteDate = null;
		LastExecuteTimeRecord letRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.PLMPRODUCT);
		List<PLMProduct> products=pLMProductService.getLastRecord(letRecord.getLastExecuteTime());
		for(PLMProduct pLMProduct:products){
			if(StringUtils.isBlank(pLMProduct.getNo()) || StringUtils.isBlank(pLMProduct.getName())){
				continue;
			}
			Product product =productService.getByProductCode(pLMProduct.getNo());
			if(product==null){
				product=new Product();
				product.setProductCode(pLMProduct.getNo());
				product.setProductName(pLMProduct.getName());
				product.setProductType(pLMProduct.getSeries());
				product.setProductSpec(pLMProduct.getAsuser01());
				product.setOrgCode(SessionUtils.getUser().getOrgCode());
				product.setUsedStock(true);
				product.setComplex(true);
				product.setClassifyId(pLMProduct.getCatanid()); // 分类ID
				product.setCraftsCode(pLMProduct.getCraftNo());
				productService.insert(product);
			}else{
				product.setProductName(pLMProduct.getName());
				product.setProductType(pLMProduct.getSeries());
				product.setUsedStock(true);
				product.setComplex(true);
				product.setClassifyId(pLMProduct.getCatanid()); // 分类ID
				product.setCraftsCode(pLMProduct.getCraftNo());
				product.setProductSpec(pLMProduct.getAsuser01());
				productService.update(product);
			}
			// TODO
//			List<Desf2>  defs2s=desf2Services.getByProductId(product.getId());
//			attachmentService.delete(product.getId());
//			for(Desf2 def:defs2s){
//				try {
//					attachmentService.upload(new File(def.getLocation()), InterfaceDataType.DESF, null, product.getId());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
			
			//生成成品物料
			Mat mat=matService.getByCode(product.getProductCode());
			if(mat==null){
				mat=new Mat();
				mat.setTempletId("1");
				mat.setMatCode(product.getProductCode());
				mat.setMatName(product.getProductName());
				mat.setMatType(MatType.FINISHED_PRODUCT);
				mat.setHasPic(false);
				mat.setIsProduct(true);
				mat.setOrgCode(matService.getOrgCode());
				matService.insert(mat);
			}
			
		    if(lastExecuteDate==null)
	           {
				  lastExecuteDate=pLMProduct.getCtime();
	           }
	          if(pLMProduct.getCtime().after(lastExecuteDate))
	           {
	            	lastExecuteDate=pLMProduct.getCtime();
	           }
	          if(pLMProduct.getMtime().after(lastExecuteDate))
	            {
	           	lastExecuteDate=pLMProduct.getMtime();
	           } 
			 
		}
		letRecord.setLastExecuteTime(lastExecuteDate);
        lastExecuteTimeRecordService.saveRecord(letRecord);
	
	}

}
