package cc.oit.bsmes.pro.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessReceipt;

/**
 * 
 * 产品工艺参数 DAO
 * @author JinHanyun
 * @date 2014-1-21 下午5:57:23
 * @since
 * @version
 */
public interface ProcessReceiptDAO extends BaseDAO<ProcessReceipt> {

	public List<ProcessReceipt> getByWorkOrderNo(String woNo,String equipCode);

	/**
	 * <p>根据设备code和工艺code查找工艺参数</p> 
	 * @author QiuYangjun
	 * @date 2014-3-7 上午11:02:46
	 * @param findParams
	 * @return
	 * @see
	 */
	public List<ProcessReceipt> getByEquipCodeAndProcessCode(Map<String, Object> findParams);

	/**
	 * <p>根据设备code和工艺code 工艺参数code 查找工艺参数信息</p> 
	 * @author QiuYangjun
	 * @date 2014-4-28 下午5:15:01
	 * @see
	 */
	public  List<ProcessReceipt> getByEquipCodeAndProcessCodeAndReceiptCode(Map<String, Object> findParams);
	
	public  List<ProcessReceipt> getByEquipCodeAndReceiptCode(Map<String, Object> findParams);

	public List<ProcessReceipt> getByProcessId(String processId);


	public void insertBatchInterface(List<ProcessReceipt> receiptListResult);

	
	public List<ProcessReceipt> getByEquipIdAndReceiptCode(String equipListId ,String receiptCode);
	
    public void updateReceiptQc(String craftsCode);
    
    public List<Map<String, String>> findReceiptMap();
    
	public void insertBatch(@Param("list") List<ProcessReceipt>  list);
	
	public  List<ProcessReceipt> getEquipQcByItemId(Map<String, Object> param);
	
	public List<ProcessReceipt> getByEquipListId(Map<String, Object> findParams);

}
