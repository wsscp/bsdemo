package cc.oit.bsmes.pla.dao;


import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.FinishedProduct;

public interface FinishedProductDAO extends BaseDAO<FinishedProduct> {

	List<FinishedProduct> getAllModel(Map<String, Object> map);

	List<FinishedProduct> getAllSpec(Map<String, Object> map);

	List<FinishedProduct> listFinishedProduct(FinishedProduct findParams);

	Integer countFinishedProduct(FinishedProduct finishedProduct);

	List<FinishedProduct> getFinishedProductById(String salesOrderItemId);

}
