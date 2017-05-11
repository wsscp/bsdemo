package cc.oit.bsmes.pro.action;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cc.oit.bsmes.common.constants.ProductQCStatus;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.pro.model.ProductQCDetail;
import cc.oit.bsmes.pro.model.ProductQCResult;
import cc.oit.bsmes.pro.service.ProductQCDetailService;
import cc.oit.bsmes.pro.service.ProductQCResultService;

/**
 * 
 * 产品QC检验结果明细
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-14 上午10:31:14
 * @since
 * @version
 */
@Controller
@RequestMapping("/pro/productQCDetail")
public class ProductQCDetailController {
	@Resource private ProductQCDetailService productQCDetailService;
	@Resource private ProductQCResultService productQCResultService;
	@RequestMapping(value="getByResId/{resultId}",method=RequestMethod.GET)		    
	@ResponseBody
	public List getByResId(@PathVariable String resultId){
		List<ProductQCDetail> productQCDetailList=productQCDetailService.getByResId(resultId);
		return productQCDetailList;
	}
	
	@ResponseBody
	@RequestMapping(value="updateQCDetail",method = RequestMethod.POST)
	public void updateQCDetail(@RequestParam String id,@RequestParam String qcResult){
		ProductQCDetail qcDetails=new ProductQCDetail();
		qcDetails.setQcResult(qcResult);
		qcDetails.setId(id);
		productQCDetailService.update(qcDetails);
	}
	
	@RequestMapping(value="getReslist",method=RequestMethod.GET)	
	@ResponseBody
	public List getReslist(@RequestParam String sampleCode){
		List<ProductQCDetail> list=null;
		if(StringUtils.isNotBlank(sampleCode)){
			list=productQCDetailService.getBySampleBarCode(sampleCode);
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="insert",method = RequestMethod.POST)
	public void insert(@RequestParam String jsonText,@RequestParam String resDet){
		ProductQCResult qcResult=JSON.parseObject(jsonText, ProductQCResult.class);
		qcResult.setOrgCode(SessionUtils.getUser().getOrgCode());
		qcResult.setStatus(ProductQCStatus.VALID);
		List<ProductQCDetail> qcDetail=JSON.parseArray(resDet, ProductQCDetail.class);
		qcResult.setProductCode(qcDetail.get(0).getProductCode());
		List<ProductQCDetail> list=new ArrayList<ProductQCDetail>();
		productQCResultService.insert(qcResult);
		for(ProductQCDetail pro :qcDetail){
			ProductQCDetail pros=new ProductQCDetail();
			pros.setQcTempId(pro.getId());
			pros.setQcResId(qcResult.getId());
			pros.setId(UUID.randomUUID().toString());
			pros.setQcResult(pro.getQcResult());
			pros.setOrgCode(SessionUtils.getUser().getOrgCode());
			pros.setStatus(ProductQCStatus.VALID);
			list.add(pros);
		}
		productQCDetailService.insert(list);
		
	}
	
}
