package cc.oit.bsmes.interfacePLM.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.PrcvDAO;
import cc.oit.bsmes.interfacePLM.model.Prcv;
import cc.oit.bsmes.interfacePLM.model.Scx;
import cc.oit.bsmes.interfacePLM.service.PrcvService;
import cc.oit.bsmes.interfacePLM.service.ProcessService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.ord.dao.SalesOrderItemDAO;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.pla.dao.CustomerOrderItemDAO;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pro.dao.ProductCraftsDAO;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductCraftsBz;
import cc.oit.bsmes.pro.service.ProductCraftsBzService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.impl.ProductCraftsServiceImpl;

/**
 * PrcvServiceImpl
 * <p style="display:none">
 * 产品工艺基本信息Service实现类
 * </p>
 * 
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
@Service
@Scope("prototype")
public class PrcvServiceImpl extends BaseServiceImpl<Prcv> implements
		PrcvService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private PrcvDAO prcvDAO;
	@Resource
	private ProductCraftsDAO productCraftsDAO;
	@Resource
	private CustomerOrderItemDAO customerOrderItemDAO;
	@Resource
	private CustomerOrderService customerOrderService;
	@Resource
	private SalesOrderItemDAO salesOrderItemDAO;
	@Resource
	private SalesOrderItemService salesOrderItemService;
	
	
	

	// ----------------------------以下为MES/PLM同步代码
	/**
	 * MES
	 */
	@Resource
	private ProductCraftsBzService productCraftsBzService;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private ProcessService processService;
	@Resource
	private ProductService productService;
	@Resource
	private MatService matService;

	/**
	 * 需要实例化的工艺
	 * */
	private List<ProductCraftsBz> instanceCraftsArrayCache = new ArrayList<ProductCraftsBz>();
	
	
	/**
	 * 生产线缓存
	 */
	public static Map<String, List<Scx>> scxMapCache = new HashMap<String, List<Scx>>();
	
	public static Map<String, String> receiptMap = new HashMap<String, String>();

	/**
	 * MES 同步 PLM 工艺数据
	 * 
	 * @param lastDate
	 *            更新时间
	 */
	public List<Prcv> getAsyncDataList(Map<String, Date> findParams) {
		return prcvDAO.getAsyncDataList(findParams);
	}
	
	/**
	 * MES 同步 PLM 根据产品编码获取PLM工艺信息
	 * 
	 * @param lastDate
	 *            更新时间
	 */
	public List<Prcv> getPrcvArrayByProductCodeArray(String[] productCodeArray){
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("productCodeArray", productCodeArray);
		return prcvDAO.getPrcvArrayByProductCodeArray(findParams);
	}

	
	public List<Prcv> getPrcvByProductNo(String param){
		return prcvDAO.getPrcvByProductNo(param);
	}
	/**
	 * 同步 PLM 工艺数据
	 * 同步工艺：在标准库里面，存在了变更工艺的话，直接删除原来的重新新增，然后再实例化
	 * 
	 * @param lastDate
	 *            更新时间
	 */
	@Override
	public Date asyncData(Map<String, Date> findParams) {
		List<Prcv> prcvList = prcvDAO.getAsyncDataList(findParams); // 获取PLM变更的工艺信息
		Date maxDate=findParams.get("lastDate");
		long now;
		long total;
		if (null != prcvList) {		
			instanceCraftsArrayCache.clear();
			scxMapCache.clear();
			receiptMap.clear();
			total=System.currentTimeMillis();
			for (Prcv prcv : prcvList) {
				now=System.currentTimeMillis();
				this.asyncSingleData(prcv);
				logger.debug("从PLM同步一条标准工艺："+prcv.getNo()+"耗费时间："+(System.currentTimeMillis()-now));
				if(maxDate==null)
				{
					maxDate=prcv.getMtime();
				}
				if(prcv.getMtime().after(maxDate))
				{
					maxDate=prcv.getMtime();
				} 
			}
			logger.debug("从PLM同步标准数据"+prcvList.size()+ "条数耗费时间："+(System.currentTimeMillis()-total));
			// 实例化
			 
			 ProductCraftsServiceImpl.clearQcBZflagMap();
			 total=System.currentTimeMillis();
			for (ProductCraftsBz productCraftsBz : instanceCraftsArrayCache) {
				now=System.currentTimeMillis();
				productCraftsService.instanceCrafts(null, "标准工艺",
						productCraftsBz, null, true); // 实例化工艺
				logger.debug("实例化一条数据消耗时间："+(System.currentTimeMillis()-now));
				this.DynBundling(productCraftsBz); //绑定已有的产品
			} 
			logger.debug("实例化标准数据 条数："+instanceCraftsArrayCache.size()+"耗费时间："+(System.currentTimeMillis()-total));
		}
		return maxDate;
	}

	private void DynBundling(ProductCraftsBz productCraftsBz) {
		SalesOrderItem salesOrderItem=new SalesOrderItem();
		ProductCrafts productCrafts=productCraftsDAO.getLatestPrcvByNo(productCraftsBz.getCraftsCode());
		String productCode=productCrafts.getProductCode();
		List<CustomerOrderItem> custItems=customerOrderItemDAO.getByProductNo(productCrafts.getProductCode());
		//如果没有找到，则在productcode后加上（B）
		if(custItems==null||custItems.size()==0){
			custItems=customerOrderItemDAO.getByProductNo(productCode+"(B)");			
		}
		//产品绑定新工艺
		if(custItems!=null && custItems.size()>0){
			productCrafts.setProductCode(productCode+"(B)");
			customerOrderItemDAO.updateCraftsId(productCrafts);
			String wiresStructure = customerOrderService.getWiresStructure(productCrafts.getId()); 
			//修改导体结构
			for(CustomerOrderItem custItem:custItems){
				salesOrderItem=salesOrderItemDAO.getById(custItem.getSalesOrderItemId());
				salesOrderItem.setWiresStructure(StringUtils.isEmpty(wiresStructure)? "" : wiresStructure); // 线芯结构
				salesOrderItemService.update(salesOrderItem);
				String newCraftsId = salesOrderItemService.dataSeparationFunction(productCrafts.getId(), custItem.getSalesOrderItemId());
				custItem.setCraftsId(newCraftsId);
				customerOrderItemDAO.update(custItem);
			}
			
		}
		
		
	}

	/**
	 * 同步 PLM 工艺数据
	 * 
	 * @param lastDate
	 *            更新时间
	 */
	@Override
	public void asyncData(Prcv prcv) {
//		//同步产品表
//		Product product=productService.sycAddData(prcv.getProductNo());	
//		if(null==product){
//			System.out.println("工艺路线"+prcv.getNo()+"下的产品在plm中不存在或存在多条记录");
//			return;
//		}else{
//			//添加成品到mat表
//			matService.synFinishedMat(product);
//		}
		
		ProductCraftsBz productCraftsBz = this.asyncSingleData(prcv);
		if (null == productCraftsBz) { // 修改的，不需要实例化
			return;
		}
		// 实例化
		productCraftsService.instanceCrafts(null, "标准工艺", productCraftsBz,
				null, true); // 实例化工艺
	}

	/**
	 * 分开做事务：单个同步工艺
	 * 
	 * prcv
	 * 
	 * @param prcv
	 *            PLM工艺同步对象
	 * */
	@Transactional(readOnly = false)
	private ProductCraftsBz asyncSingleData(Prcv prcv) {		
		ProductCraftsBz productCraftsBz = this.craftsAsync(prcv); // 同步工艺基本信息
		processService.asyncData(prcv.getId()); // 同步工序信息
		//修改备注名
		if(prcv.getSmemo()!=null && prcv.getSmemo().equals("未导入")){
			prcv.setSmemo("已导入");
			prcvDAO.updateSmemo(prcv.getId());
		}
		return productCraftsBz;
	}

	public List<Prcv> getNoNotExistsInMes() {
		return prcvDAO.getNoNotExistsInMes();
	}

	/**
	 * 同步工艺信息
	 * 
	 * @author DingXintao
	 * @param prcv
	 *            PLM工艺同步对象
	 * @return
	 * */

	private ProductCraftsBz craftsAsync(Prcv prcv) {
		// boolean isAdd;// 是否新增 *true 新增;false 更新*
		ProductCraftsBz productCraftsBz = null;
		// 判断工艺在MES标准库中是否存在，存在就更新，不存在就新增
		productCraftsBz = productCraftsBzService.getById(prcv.getId());
		if(productCraftsBz!=null){
			productCraftsBzService.delete(productCraftsBz);
		}
		productCraftsBz = new ProductCraftsBz();
		productCraftsBz.setId(prcv.getId());
		productCraftsBz.setCreateUserCode("PLM");
		productCraftsBz.setCraftsCode(prcv.getNo());
		productCraftsBz.setCraftsName(prcv.getNo());
		productCraftsBz.setStartDate(new Date());
		productCraftsBz.setEndDate(null);
		productCraftsBz.setCraftsVersion(prcv.getVer());
		productCraftsBz.setProductCode(prcv.getProductNo());
		productCraftsBz.setOrgCode("bstl01"); // 数据所属组织 ????
		productCraftsBz.setIsDefault(true); // 是否默认工艺路线 ????
		productCraftsBzService.insert(productCraftsBz);
		instanceCraftsArrayCache.add(productCraftsBz);
		return productCraftsBz;	
	}

	@Override
	public List<Map<String, Object>> getAllUnSynPrcv() {
		return prcvDAO.getAllUnSynPrcv();
	}

	@Override
	public List<Prcv> getRelatedPrcvByMpart(String mpartNo) {
		return prcvDAO.getRelatedPrcvByMpart(mpartNo);
	}

	@Override
	public List<Prcv> getAllVersionPrcv(String prcvNo) {
		return prcvDAO.getAllVersionPrcv(prcvNo);
	}
}
