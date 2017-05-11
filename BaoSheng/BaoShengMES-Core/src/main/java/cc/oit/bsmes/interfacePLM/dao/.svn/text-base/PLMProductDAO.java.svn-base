package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.PLMProduct;

public interface PLMProductDAO extends BaseDAO<PLMProduct>{

	List<PLMProduct> getLastRecord(Map<String, Object> map);
	
	public void updatePro(PLMProduct param);
	
	public List<PLMProduct> getProductsWithImage();
	
	public List<PLMProduct> getByProductName(Map<String,Object> param);
	
	public List<PLMProduct> getPLMProductByProductName(String productName);
	
	public void updateDescribeInfo(PLMProduct product);
	

}
