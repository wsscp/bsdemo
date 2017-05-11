package cc.oit.bsmes.inv.service.impl;

import javax.annotation.Resource;

import cc.oit.bsmes.common.constants.WarehouseType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.inv.dao.WarehouseDAO;
import cc.oit.bsmes.inv.model.Warehouse;
import cc.oit.bsmes.inv.service.WarehouseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseServiceImpl extends BaseServiceImpl<Warehouse> implements WarehouseService {
	@Resource private WarehouseDAO wareHouseDAO;

    @Resource private WorkOrderService workOrderService;

	@Override
	public Warehouse checkExtist(Warehouse warehouse) {
		return wareHouseDAO.checkExtist(warehouse);
	}

    @Override
    public List<Warehouse> getByBarCode(String barCode) {
        WorkOrder workOrder = workOrderService.getByBarCode(barCode,workOrderService.getOrgCode());
        if(workOrder == null)
            return new ArrayList<Warehouse>();
      //  Map<String,ProductProcess> processMap = StaticDataCache.getProcessMap();
        ProductProcess productProcess = StaticDataCache.getProcessByProcessId( workOrder.getProcessId());
        if(productProcess == null)
            return new ArrayList<Warehouse>();
        String processCode = "";
        ProductProcess nextProductProcess = null;
        if(WebConstants.ROOT_ID.equals(productProcess.getNextProcessId())){
            processCode = WebConstants.ROOT_ID;
        }else{
            nextProductProcess = StaticDataCache.getProcessByProcessId(productProcess.getNextProcessId());
        }
        if(StringUtils.isBlank(processCode) && nextProductProcess == null)
            return new ArrayList<Warehouse>();

        if(StringUtils.isBlank(processCode)){
            processCode = nextProductProcess.getProcessCode();
        }
        return wareHouseDAO.getByProcess(processCode, SessionUtils.getUser().getOrgCode());
    }

    @Override
    public Warehouse getByCode(String wareHouseCode) {
        Warehouse warehouse = wareHouseDAO.getByCode(wareHouseCode);
        if(warehouse == null){
            warehouse = new Warehouse();
            warehouse.setWarehouseCode(wareHouseCode);
            warehouse.setWarehouseName(wareHouseCode);
            warehouse.setType(WarehouseType.WIP);
            warehouse.setOrgCode(getOrgCode());
            insert(warehouse);
        }
        return warehouse;
    }
}
