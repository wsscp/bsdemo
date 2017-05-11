package cc.oit.bsmes.pro.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.model.ProductProcessWip;

public interface ProductProcessWipService extends BaseService<ProductProcessWip> {

	/**
	 * 工艺ID获取工序明细: 查看工艺工序详情：升序
	 * 
	 * @author DingXintao
	 * @param productCraftsId 工艺ID
	 * @return List<ProductProcess>
	 */
	public List<ProductProcessWip> getByProductCraftsIdAsc(String productCraftsId);
	
	// 批量插入：形成批量插入sql，提高insert效率
	public void insertAll(List<ProductProcess> productProcessList);
	// 批量删除: 根据工艺Id
	public void deleteDate(String oldCraftsId);

}
