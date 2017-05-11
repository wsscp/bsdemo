package cc.oit.bsmes.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.ProductCraftsBzDAO;
import cc.oit.bsmes.pro.model.ProductCraftsBz;
import cc.oit.bsmes.pro.service.ProductCraftsBzService;

/**
 * @author 陈翔
 */
@Service
public class ProductCraftsBzServiceImpl extends BaseServiceImpl<ProductCraftsBz> implements ProductCraftsBzService {
	
	@Resource
	private ProductCraftsBzDAO productCraftsBzDAO;

	@Override
	public List<ProductCraftsBz> craftsCombo(String codeOrName) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("codeOrName", "%"+codeOrName+"%");
		return productCraftsBzDAO.getByCodeOrName(param);
	}
}