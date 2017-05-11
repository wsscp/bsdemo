package cc.oit.bsmes.fac.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.fac.model.ProductEquip;

public interface ProductEquipDAO extends BaseDAO<ProductEquip> {

    ProductEquip getByLineAndProId(String proLineId,String proId);

    ProductEquip getByEquipId(String equipId);

}
