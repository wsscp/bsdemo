/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */
package cc.oit.bsmes.wip.service.impl;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.wip.dao.RealCostDAO;
import cc.oit.bsmes.wip.model.RealCost;
import cc.oit.bsmes.wip.service.RealCostService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-2-20 下午3:44:31
 * @since
 * @version
 */
@Service
public class RealCostServiceImpl extends BaseServiceImpl<RealCost> implements
		RealCostService {
	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private RealCostDAO realCostDAO;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private MatService matService;

	@Override
	public List<RealCost> getByWorkOrderNO(String workOrderNo) {
		RealCost findParams = new RealCost();
		findParams.setWorkOrderNo(workOrderNo);
		return realCostDAO.get(findParams);
	}

	@Override
	public boolean isFeedCompleted(String workOrderNo) {
		List<RealCost> costList = getByWorkOrderNO(workOrderNo);
		if (costList == null || costList.isEmpty()) {
			return false;
		} else {
			List<ProcessInOut> list = processInOutService.getInByWorkOrderNo(workOrderNo);
			if (list.size() > costList.size()) {
				return false;
//有可能同一个物料投了多个批次的
//			}else if(list.size() < costList.size()){
//				//如果需要投料的种类数量小于 已经投料的种类数量 数据出错
//				throw new MESException("error.wip.feedMaterial.dataError");
			}else{
				return true;
			}
		}
	}

    @Override
    public List<RealCost> checkProductPutIn(String workOrderNo, String batchNo) {
        return realCostDAO.checkProductPutIn(workOrderNo,batchNo);
    }

    @Override
    public int deleteByBarCode(String barCode) {
        return realCostDAO.deleteByBarCode(barCode);
    }

	@Override
	public void cancelPutMat(String  barCode){
		realCostDAO.deleteByBarCode(barCode);
		Mat mat = matService.getByCode(barCode);
		if (mat == null) {
			inventoryService.handInWarehouse(barCode);
		}
	}
}
