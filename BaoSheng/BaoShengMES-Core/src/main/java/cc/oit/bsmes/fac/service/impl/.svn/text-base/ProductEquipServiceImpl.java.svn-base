package cc.oit.bsmes.fac.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.fac.dao.ProductEquipDAO;
import cc.oit.bsmes.fac.model.ProductEquip;
import cc.oit.bsmes.fac.service.ProductEquipService;
@Service
public class ProductEquipServiceImpl extends BaseServiceImpl<ProductEquip> implements ProductEquipService {
	@Resource private ProductEquipDAO productEquipDAO;

    @Override
    public ProductEquip getByLineAndProId(String proLineId, String proId) {
        return productEquipDAO.getByLineAndProId(proLineId,proId);
    }

    @Override
    public ProductEquip getByEquipId(String equipId) {
        return productEquipDAO.getByEquipId(equipId);
    }
}
