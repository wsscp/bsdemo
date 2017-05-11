package cc.oit.bsmes.pla.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.interfacePLM.model.PLMProduct;
import cc.oit.bsmes.interfacePLM.service.PLMProductService;
import cc.oit.bsmes.pla.dao.ProductDAO;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSONObject;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements
		ProductService {
	@Resource
	private ProductDAO productDAO;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private PLMProductService pLMProductService;
	
	@Override
	public Product getByProductCode(String productCode) {
		Product findParams = new Product();
		findParams.setProductCode(productCode);
		return productDAO.getOne(findParams);
	}

	@Override
	public Product getByProcessId(String processId) {
		return productDAO.getByProcessId(processId);
	}

	@Override
	public List<Product> getByWorkOrderNO(String workOrderNo){
		return productDAO.getByWorkOrderNO(workOrderNo);
	}
	
	@Override
	public List<Product> getByEquipCode(String equipCode) {
		WorkOrder workOrder = workOrderService.getCurrentByEquipCode(equipCode);
		if(workOrder == null){
			return new ArrayList<Product>();
		}
		return productDAO.getByWorkOrderNO(workOrder.getWorkOrderNo());
	}
	
	  /**
     * <p>查询条件->产品信息下拉框：支持模糊查询</p> 
     * @author DingXintao
     * @date 2014-6-30 9:56:48
     * @return List<Product>
     * @see
     */
	public List<Product> productsCombo(String query){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("codeOrName", "%" + query + "%");
        List<Product> list = productDAO.findByCodeOrName(param);
        return list;
	}
	                    
	public List<Product> productsTypeCombo(String query){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", "%" + query + "%");
        List<Product> list = productDAO.findByType(param);
        return list;
	}
	
    @Override
    public List<Product> findForExport(JSONObject queryParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Product findParams = (Product) JSONUtils.jsonToBean(queryParams,Product.class);
        addLike(findParams, Product.class);
        return productDAO.find(findParams);
    }

    @Override
    public int countForExport(JSONObject queryParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        Product findParams = (Product) JSONUtils.jsonToBean(queryParams,Product.class);
        addLike(findParams, Product.class);
        return productDAO.count(findParams);
    }

	@Override
	public List<Product> getByProductCodeOrName(String query) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("query", query);
		return productDAO.getByProductCodeOrName(map);
	}
	
	@Override
	public Product sycAddData(String productNo){
		PLMProduct param=new PLMProduct();
		param.setNo(productNo);
		List<PLMProduct> lists=pLMProductService.find(param);
		if(null==lists||lists.size()==0){
			System.out.println("产品"+productNo+"在PLM中不存在");
			return null;
		}
		if(lists.size()>1){
			System.out.println("产品"+productNo+"在PLM中存在多个相同的产品");
			return null;
		}
		PLMProduct plmProduct=lists.get(0);
		Product product =this.getByProductCode(productNo);
		if(product!=null){
			this.delete(product);
		}
		product=new Product();
		product.setProductCode(plmProduct.getNo());
		product.setProductName(plmProduct.getName());
		product.setProductType(plmProduct.getSeries());
		product.setProductSpec(plmProduct.getAsuser01());
		product.setOrgCode("bstl01");
		//product.setOrgCode(SessionUtils.getUser().getOrgCode());
		product.setUsedStock(true);
		product.setComplex(true);
		product.setCraftsCode(plmProduct.getCraftNo());
		this.insert(product);
		return product;
	}

	@Override
	public Product getByProductTypeAndSpec(String productType,
			String productSpec) {
		return productDAO.getByProductTypeAndSpec(productType,productSpec);
	}
}
