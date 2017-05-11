package cc.oit.bsmes.pro.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.model.MatProp;
import cc.oit.bsmes.inv.model.MatPropWip;
import cc.oit.bsmes.inv.model.MatWip;
import cc.oit.bsmes.inv.service.MatPropService;
import cc.oit.bsmes.inv.service.MatPropWipService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.inv.service.MatWipService;
import cc.oit.bsmes.pla.service.OrderProcessPRService;
import cc.oit.bsmes.pro.dao.ProcessInOutWipDAO;
import cc.oit.bsmes.pro.model.ProcessInOutWip;
import cc.oit.bsmes.pro.service.ProcessInOutWipService;

@Service
public class ProcessInOutWipServiceImpl extends BaseServiceImpl<ProcessInOutWip> implements ProcessInOutWipService {

	@Resource
	private ProcessInOutWipDAO processInOutWipDAO;
	@Resource
	private OrderProcessPRService orderProcessPRService;
	@Resource
	private MatService matService;
	@Resource
	private MatPropService matPropService;
	@Resource
	private MatWipService matWipService;
	@Resource
	private MatPropWipService matPropWipService;
	

	/**
	 * @Title:       getByProcessId
	 * @Description: TODO(工序ID获取投入产出)
	 * @param:       processId 工序ID
	 * @return:      List<ProcessInOut>   
	 * @throws
	 */
	@Override
	public List<ProcessInOutWip> getByProcessId(String processId) {
		return processInOutWipDAO.getByProcessId(processId);
	}
	
	/**
	 * @Title: insert
	 * @Description: TODO(更新投入产出信息)
	 * @param: processInOutWip 投入产出更新参数: salesOrderItemId 客户订单id, oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return: UpdateResult
	 * @throws
	 */
	@Override
	@Transactional(readOnly = false)
	public UpdateResult insertProcessInOut(ProcessInOutWip processInOutWip){
		// 1、新增工序投入产出
		this.insert(processInOutWip);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(processInOutWip);
		// 2、如果是特殊工艺的话: 添加三张表记录T_INV_MAT_WIP、T_INV_MAT_PROP_WIP、T_PLA_ORDER_PROCESS_PR
		if (StringUtils.isNotEmpty(processInOutWip.getSalesOrderItemId())) {
			// 2.1、添加物料相关表: T_INV_MAT_WIP、T_INV_MAT_PROP_WIP
			this.insertMatAndPro(processInOutWip);
			// 2.3、添加工艺的变更记录: T_INV_MAT_WIP
			orderProcessPRService.insertSpencial(processInOutWip.getSalesOrderItemId(), processInOutWip.getProductProcessId(), "投入原料",
					processInOutWip.getOldMatCode(), processInOutWip.getOldMatName(), processInOutWip.getMatCode(), processInOutWip.getOldMatCode(),
					processInOutWip.getOldMatName(), processInOutWip.getInOrOut().name());
		}
		return updateResult;
	}
	
	/**
	 * @Title: update
	 * @Description: TODO(更新投入产出信息)
	 * @param: processInOutWip 投入产出更新参数: salesOrderItemId 客户订单id, oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return: UpdateResult
	 * @throws
	 */
	@Override
	@Transactional(readOnly = false)
	public UpdateResult updateProcessInOut(ProcessInOutWip processInOutWip){
		// 1、更新工序投入产出
		this.update(processInOutWip);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(processInOutWip);
		// 2、如果是特殊工艺的话: 添加三张表记录T_INV_MAT_WIP、T_INV_MAT_PROP_WIP、T_PLA_ORDER_PROCESS_PR
		if (StringUtils.isNotEmpty(processInOutWip.getSalesOrderItemId()) && !processInOutWip.getMatCode().equals(processInOutWip.getOldMatCode())) {
			// 2.1、添加物料相关表: T_INV_MAT_WIP、T_INV_MAT_PROP_WIP
			this.insertMatAndPro(processInOutWip);
			// 2.3、添加工艺的变更记录: T_INV_MAT_WIP
			orderProcessPRService.insertSpencial(processInOutWip.getSalesOrderItemId(), processInOutWip.getProductProcessId(), "投入原料",
					processInOutWip.getOldMatCode(), processInOutWip.getOldMatName(), processInOutWip.getMatCode(), processInOutWip.getOldMatCode(),
					processInOutWip.getOldMatName(), processInOutWip.getInOrOut().name());
		}
		return updateResult;
	}
	
	/**
	 * @Title:       insertMatAndPro
	 * @Description: TODO(新增物料相关信息: T_INV_MAT_WIP、T_INV_MAT_PROP_WIP)
	 * @param:       processInOutWip 投入产出更新参数: 投入产出更新参数: salesOrderItemId 客户订单id, oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return:      void   
	 * @throws
	 */
	private void insertMatAndPro(ProcessInOutWip processInOutWip){
		// 2.1、判断物料订单下面是否已经存在: 防止多次切换产生的存在的数据
		MatWip params = new MatWip();
		params.setMatCode(processInOutWip.getMatCode());
		params.setProcessInOutWipId(processInOutWip.getId());
		List<MatWip> list = matWipService.findByObj(params);
		if(!CollectionUtils.isEmpty(list)){
			return;
		}
		// 2.2、添加T_INV_MAT_WIP: 物料表
		Mat mat = matService.getByCode(processInOutWip.getMatCode());
		MatWip matWip = new MatWip();
		BeanUtils.copyProperties(mat, matWip);
		matWip.setId(null);
		matWip.setProcessInOutWipId(processInOutWip.getId());
		matWip.setProcessWipId(processInOutWip.getProductProcessId());
		matWip.setSalesOrderItemId(processInOutWip.getSalesOrderItemId());
		matWipService.insert(matWip);
		
		// 2.3、添加T_INV_MAT_PROP_WIP: 物料属性表
		MatProp findParams = new MatProp();
		findParams.setMatId(mat.getId()); 
		List<MatProp> matPropArray = matPropService.getByObj(findParams);
		for(MatProp matProp : matPropArray){
			MatPropWip matPropWip = new MatPropWip();
			BeanUtils.copyProperties(matProp, matPropWip);
			matPropWip.setId(null);
			matPropWip.setMatId(matWip.getId());
			matPropWipService.insert(matPropWip);
		}
	}

}
