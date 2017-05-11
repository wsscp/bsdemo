package cc.oit.bsmes.interfacePLM.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.PLMProductDAO;
import cc.oit.bsmes.interfacePLM.model.PLMProduct;
import cc.oit.bsmes.interfacePLM.service.PLMProductService;
import cc.oit.bsmes.pla.model.Product;
@Service
public class PLMProductServiceImpl extends BaseServiceImpl<PLMProduct> implements PLMProductService {
	@Resource private PLMProductDAO PLMProductdao;
	@Override
	public List<PLMProduct> getLastRecord(Date lastDate) {
		Map<String, Object> map=new HashMap();
		map.put("lastDate", lastDate);
		return PLMProductdao.getLastRecord(map);
	}

	public void updatePro(PLMProduct param){
		 PLMProductdao.updatePro(param);
	}
	
	public List<PLMProduct> find(PLMProduct param){
		return PLMProductdao.find(param);
	}
	
	@Override
	public List<PLMProduct> getProductsWithImage() {
		
		return PLMProductdao.getProductsWithImage();
	}

	@Override
	public List<PLMProduct> getByProductName(Map<String,Object> param) {
		return PLMProductdao.getByProductName(param);
	}

	@Override
	public List<PLMProduct> getPLMProductByProductName(String productName) {
		return PLMProductdao.getPLMProductByProductName(productName);
	}

	@Override
	public void updateDescribeInfo(PLMProduct product) {
			PLMProductdao.updateDescribeInfo(product);
		
	}
	

	
}
