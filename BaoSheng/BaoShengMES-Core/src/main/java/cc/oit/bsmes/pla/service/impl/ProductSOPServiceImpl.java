package cc.oit.bsmes.pla.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.pla.exception.UnableToProductException;
import cc.oit.bsmes.pla.model.*;
import cc.oit.bsmes.pla.schedule.IEquipMatcher;
import cc.oit.bsmes.pla.schedule.matcher.SOPEquipMatcher;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.pla.dao.ProductSOPDAO;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.ProductSOPService;
import cc.oit.bsmes.pro.model.ProductProcess;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * ProductSOPServiceImpl
 * 
 * @author leiwei
 * @date 2014-1-22 下午6:08:30
 * @since
 * @version
 */
@Service
public class ProductSOPServiceImpl extends BaseServiceImpl<ProductSOP>
		implements ProductSOPService {

	@Resource
	private ProductSOPDAO productSOPDAO;
	@Resource
	private CustomerOrderItemService orderItemService;
	private static final int ADD_ORDER_COUNT = 10; // 要追加的订单数
	private static final int INIT_AVERAGE_LENGTH = 1000; // 要追加的订单数

	@Override
	@Transactional(readOnly = false)
	public void calculateSOP(ResourceCache resourceCache, String orgCode) {
        productSOPDAO.deleteByOrgCode(orgCode);
        IEquipMatcher matcher = new SOPEquipMatcher(resourceCache, orgCode);
		Multimap<String, CustomerOrderItem> productOrders = ArrayListMultimap.create();
		
		// 已有订单
		List<CustomerOrderItem> orders = orderItemService.getUncompleted(orgCode, null);
		for (CustomerOrderItem order : orders) {
			productOrders.put(order.getProductCode(), order);
		}
		
		List<Product> productList = resourceCache.getProductList();
		for (Product product : productList) {
			// 获取所有订单的平均大小
			String productCode = product.getProductCode();
			Collection<CustomerOrderItem> proOrders = productOrders.get(productCode);
			double sumLength = 0;
			for (CustomerOrderItem order : proOrders) {
				sumLength += order.getOrderLength();
			}
			double aveLength = proOrders.size() == 0 ? INIT_AVERAGE_LENGTH : sumLength / proOrders.size();

			// 添加n张订单计算完成时间
            CustomerOrderItemProDec lastOrder = null; // 上个工序
			for (int i = 0; i < ADD_ORDER_COUNT; i++) {
				List<ProductProcess> processes = resourceCache.getProductProcessByProductCode(productCode);
				for (ProductProcess productProcess : processes) {
                    CustomerOrderItemProDec order = new CustomerOrderItemProDec();
                    order.addLastOrder(lastOrder);
                    order.setUnFinishedLength(aveLength);
                    order.setProductProcess(productProcess);
                    CustomerOrderItemProDec match = matcher.match(order);
                    if (match == null) {
                        throw new UnableToProductException();
                    }
                    lastOrder = order;
				}
			}
            OrderTask lastOrderOrderTask = lastOrder.getOrderTask();

			// 创建SOP
			ProductSOP productSOP = new ProductSOP();
			productSOP.setProductCode(productCode);
			productSOP.setOrgCode(orgCode);
            productSOP.setEarliestFinishDate(lastOrderOrderTask.getPlanFinishDate());
			productSOP.setLastFinishDate(lastOrderOrderTask.getPlanFinishDate());
			productSOPDAO.insert(productSOP);
		}
	}

    @Override
    public List<ProductSOP> findForExport(JSONObject queryParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        ProductSOP findParams = (ProductSOP)JSONUtils.jsonToBean(queryParams,ProductSOP.class);
        return productSOPDAO.find(findParams);
    }

    @Override
    public int countForExport(JSONObject queryParams) {
        ProductSOP findParams = (ProductSOP)JSONUtils.jsonToBean(queryParams,ProductSOP.class);
        return productSOPDAO.count(findParams);
    }
}
