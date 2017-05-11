package cc.oit.bsmes.inv.service.impl;

import cc.oit.bsmes.common.constants.InventoryDetailStatus;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.inv.dao.InventoryDetailDAO;
import cc.oit.bsmes.inv.model.InventoryDetail;
import cc.oit.bsmes.inv.service.InventoryDetailService;
import cc.oit.bsmes.wip.model.Section;
import cc.oit.bsmes.wip.service.SectionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InventoryDetailServiceImpl extends BaseServiceImpl<InventoryDetail> implements InventoryDetailService {

	@Resource
	private InventoryDetailDAO inventoryDetailDAO;

    @Resource
    private SectionService sectionService;

	@Override
	public List<InventoryDetail> findByMatCodeAndLen(String matCode,
			Double length) {
		return inventoryDetailDAO.findByMatCodeAndLen(matCode, length);
	}

    @Override
    public List<InventoryDetail> findByProductCodeAndOrderLength(String productCode, Double orderLength, Double idealMinLength,String itemId) {
        return inventoryDetailDAO.getByProductCodeAndOrderLength(productCode,orderLength,idealMinLength,itemId);
    }

    @Override
    public List<InventoryDetail> getByOrderItemId(String itemId) {
        return inventoryDetailDAO.getByOrderItemId(itemId);
    }

    @Override
    public void recordInWareHouseDetail(String inventoryId, String reportId) {
        //查询出来的section 按节点位置倒序
        List<Section> sections = sectionService.getByReportIdForInWarehouse(reportId);
        int i = 0;
        for(Section section:sections){
            InventoryDetail detail = new InventoryDetail();
            detail.setInventoryId(inventoryId);
            detail.setLength(section.getLength());
            detail.setSeq(i++);
            if(StringUtils.isNotBlank(section.getOrderItemProDecId())){
                detail.setStatus(InventoryDetailStatus.FREEZE);
            }else{
                detail.setStatus(InventoryDetailStatus.AVAILABLE);
            }
            inventoryDetailDAO.insert(detail);
        }
    }

    @Override
    public int deleteByBarCode(String barCode) {
        return inventoryDetailDAO.deleteByBarCode(barCode);
    }
}
