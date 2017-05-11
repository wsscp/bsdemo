package cc.oit.bsmes.inv.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.inv.model.InventoryDetail;

import java.util.List;

/**
 * 
 * 
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2014-1-3 下午5:16:52
 * @since
 * @version
 */
public interface InventoryDetailDAO extends BaseDAO<InventoryDetail>{
	
	/**
	 * 
	 * 根据半成品成品编号和长度查询符合条件的库存记录
	 * @author JinHanyun
	 * @date 2014-1-3 下午5:16:49
	 * @param matCode
	 * @param length
	 * @return
	 * @see
	 */
	public List<InventoryDetail> findByMatCodeAndLen(String matCode,Double length);

    /**
     * 订单冲抵
     * @param productCode
     * @param orderLength
     * @param idealMinLength
     * @return
     */
    public List<InventoryDetail> getByProductCodeAndOrderLength(String productCode,Double orderLength,Double idealMinLength,String itemId);

    /**
     *
     * @param itemId
     * @return
     */
    public List<InventoryDetail> getByOrderItemId(String itemId);

    /**
     *
     * @param barCode
     * @return
     */
    public int deleteByBarCode(String barCode);

}
