package cc.oit.bsmes.pro.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.ProductProcessWipDAO;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.model.ProductProcessWip;
import cc.oit.bsmes.pro.service.ProductProcessWipService;

@Service
public class ProductProcessWipServiceImpl extends BaseServiceImpl<ProductProcessWip> implements ProductProcessWipService {

	@Resource
	private ProductProcessWipDAO productProcessWipDAO;

	
	/**
	 * 工艺ID获取工序明细: 查看工艺工序详情：升序
	 * 
	 * @author DingXintao
	 * @param productCraftsId 工艺ID
	 * @return List<ProductProcess>
	 */
	public List<ProductProcessWip> getByProductCraftsIdAsc(String productCraftsId){
		return productProcessWipDAO.getByProductCraftsIdAsc(productCraftsId);
	}
	
	// 批量插入：形成批量插入sql，提高insert效率
	@Override
	public void insertAll(List<ProductProcess> productProcessList){
		productProcessWipDAO.insertAll(productProcessList);
	}
	// 批量删除: 根据工艺Id
	@Override
	public void deleteDate(String oldCraftsId){
		productProcessWipDAO.deleteDate(oldCraftsId);
	}
}
