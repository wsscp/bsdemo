package cc.oit.bsmes.pro.service.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.ProductCraftsWipDAO;
import cc.oit.bsmes.pro.model.ProductCraftsWip;
import cc.oit.bsmes.pro.service.ProductCraftsWipService;

@Service
@Scope("prototype")
public class ProductCraftsWipServiceImpl extends BaseServiceImpl<ProductCraftsWip> implements 
ProductCraftsWipService {
	@Resource
    private ProductCraftsWipDAO productCraftsWipDAO;
	
}
