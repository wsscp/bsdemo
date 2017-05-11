package cc.oit.bsmes.interfacePLM.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.PLMProduct;
import cc.oit.bsmes.pla.model.Product;

public interface PLMProductService extends BaseService<PLMProduct> {

	List<PLMProduct> getLastRecord(Date date);
	
	public void updatePro(PLMProduct param);

	public List<PLMProduct> find(PLMProduct param);
	
	public List<PLMProduct> getProductsWithImage();
	
	public List<PLMProduct> getByProductName(Map<String,Object> param);
	
	public List<PLMProduct> getPLMProductByProductName(String productName);
	
	public void updateDescribeInfo(PLMProduct product);
	
	
	



}
