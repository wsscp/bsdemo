package cc.oit.bsmes.inv.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.inv.model.InventoryDetail;

import java.util.List;

public interface InventoryDetailService extends BaseService<InventoryDetail>{

	public List<InventoryDetail> findByMatCodeAndLen(String matCode,Double length);

    /**
     * 订单冲抵
     * @param productCode
     * @param orderLength
     * @param idealMinLength
     * @return
     */
    public List<InventoryDetail> findByProductCodeAndOrderLength(String productCode,Double orderLength,Double idealMinLength,String itemId);


    /**
     *
     * @param itemId
     * @return
     */
    public List<InventoryDetail> getByOrderItemId(String itemId);


    public void recordInWareHouseDetail(String inventoryId,String reportId);


    /**
     * 根据条码删除detail
     * @param barCode
     * @return
     */
    public int deleteByBarCode(String barCode);

}
