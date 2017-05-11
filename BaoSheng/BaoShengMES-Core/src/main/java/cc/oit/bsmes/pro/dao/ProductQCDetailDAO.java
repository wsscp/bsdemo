package cc.oit.bsmes.pro.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProductQCDetail;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-13 下午3:05:02
 * @since
 * @version
 */
public interface ProductQCDetailDAO extends BaseDAO<ProductQCDetail> {
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author leiwei
	 * @date 2014-3-14 下午1:19:17
	 * @param resultId
	 * @return
	 * @see
	 */
	List<ProductQCDetail> getByResId(String resultId);
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author leiwei
	 * @date 2014-3-17 下午1:14:54
	 * @param sampleCode
	 * @return
	 * @see
	 */
	List<ProductQCDetail> getBySampleBarCode(String sampleCode);
	/**
	 * 
	 * <p>根据检测结果ID删除明细</p> 
	 * @author leiwei
	 * @date 2014-5-19 下午1:21:03
	 * @param resId
	 * @return
	 * @see
	 */
	int deleteByResId(String resId);

}
