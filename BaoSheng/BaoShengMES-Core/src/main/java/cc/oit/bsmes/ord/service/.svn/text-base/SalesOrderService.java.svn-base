package cc.oit.bsmes.ord.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.service.CustomerOrderService;

import com.alibaba.fastjson.JSONObject;

/**
 * SalesOrderService
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
public interface SalesOrderService extends BaseService<SalesOrder> {
	
	/**
	 * 从xls导入订单为{@link SalesOrder}，
	 * 导入中应调用{@link CustomerOrderService#insert(SalesOrder)}
	 * 生成默认的{@link CustomerOrder}。
	 * 
	 * @author chanedi
	 * @date 2013年12月25日 下午5:32:08
	 * @param sheet
	 * @param orgCode
	 * @see
	 */
	// public void importOrders(Sheet sheet, String orgCode);
	
	public List<SalesOrder> getCustomerCompany(String contractNo,String orgcode);


    public SalesOrder getByContractNo(String contractNo,String orgCode);

    public void importProPlan(Sheet sheet, String orgCode,JSONObject result);
    
    /**
     * 新的导入方法： 不作分卷，分卷表只有一条数据，不拆分
     * */
    public void importProPlanToItemDec(Sheet sheet, String orgCode,JSONObject result);
    
    /**
	 * 根据合同号(不带字母)的后五位匹配订单查询订单信息
	 * @author chenxiang
	 * @param contractNo
	 * @param orgCode
	 * @return
	 */
    public SalesOrder getByContract(String contractNo,String orgCode);


	public List<SalesOrder> getSalesOrderByGW();
    
}
