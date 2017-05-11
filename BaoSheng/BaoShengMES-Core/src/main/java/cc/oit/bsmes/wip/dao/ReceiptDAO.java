package cc.oit.bsmes.wip.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.Receipt;

import java.util.List;
import java.util.Map;

public interface ReceiptDAO extends BaseDAO<Receipt> {
	/** 
	 * <p>根据工单号查询下发参数(工单号下所有最新的参数)</p> 
	 * @author QiuYangjun
	 * @date 2014-3-3 下午5:09:51
	 * @param woNo
	 * @return 
	 */
	public List<Receipt> getByWorkOrderNo(String woNo,String orgCode);
	
	/**
	 * 
	 * <p>根据工艺参数Code，查询当前生产中的订单，正在使用的工艺参数信息</p> 
	 * @author JinHanyun
	 * @date 2014-3-19 下午1:51:31
	 * @param receiptCode
     * @param equipCode
	 * @return
	 * @see
	 */
	public Receipt getByEquipCodeAndParamsCode(String receiptCode,String equipCode,String orgCode);

    /**
     * <p>根据tagName 查询下发参数</p>
     * @param tagName
     * @return
     */
    public Receipt getByTagName(String tagName,String orgCode);

    public List<Receipt> getByEquipCode(String equipCode,String processId,String orgCode);
    
    public Receipt getReceiptName(Map<String, Object> map);

    public Receipt getByProcessReceiptAndQA(Map<String,String> map);

	public List<Receipt> getByReceiptCodeAndTime(Map<String,Object> findParams);
	
	
	public Receipt getLastReceipt(Map<String,Object> findParams);
	/**
	 * 获取下发参数的最新的一组数据
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * */
	public List<Receipt> getLastReceiptList(String workOrderNo);
}
