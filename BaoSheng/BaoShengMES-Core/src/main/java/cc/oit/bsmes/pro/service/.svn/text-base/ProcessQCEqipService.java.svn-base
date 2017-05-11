package cc.oit.bsmes.pro.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProcessQCEqip;

public interface ProcessQCEqipService extends BaseService<ProcessQCEqip> {
	
	/**
	 * 
	 * <p>根据质量参数、生产单号查询相关生产设备</p>
	 * @author JinHanyun
	 * @date 2014-3-20 下午3:47:53
	 * @param checkItemCode
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<String> getEquipCodeByWorkOrderNo(String checkItemCode,String workOrderNo);
	
	public void insertBatch(List<ProcessQCEqip> list);

}
