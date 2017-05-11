package cc.oit.bsmes.pro.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.ProductQCDetailDAO;
import cc.oit.bsmes.pro.model.ProductQCDetail;
import cc.oit.bsmes.pro.service.ProductQCDetailService;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-13 下午3:07:17
 * @since
 * @version
 */
@Service
public class ProductQCDetailServiceImpl extends BaseServiceImpl<ProductQCDetail> implements ProductQCDetailService {
	@Resource
	private ProductQCDetailDAO productQCDetailDAO;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author leiwei
	 * @date 2014-3-14 下午1:18:48
	 * @param resultId
	 * @return 
	 * @see cc.oit.bsmes.pro.service.ProductQCDetailService#getByResId(java.lang.String)
	 */
	@Override
	public List<ProductQCDetail> getByResId(String resultId) {
		return productQCDetailDAO.getByResId(resultId);
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author leiwei
	 * @date 2014-3-17 下午1:13:47
	 * @param sampleCode
	 * @return 
	 * @see cc.oit.bsmes.pro.service.ProductQCDetailService#getBySampleBarCode(java.lang.String)
	 */
	@Override
	public List<ProductQCDetail> getBySampleBarCode(String sampleCode) {
		return productQCDetailDAO.getBySampleBarCode(sampleCode);
	}
}
