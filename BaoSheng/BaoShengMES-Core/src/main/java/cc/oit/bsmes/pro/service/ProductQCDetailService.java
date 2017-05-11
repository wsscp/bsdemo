package cc.oit.bsmes.pro.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProductQCDetail;
/**
 * 
 * 产品QC检验结果明细
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-13 下午3:05:44
 * @since
 * @version
 */
public interface ProductQCDetailService extends BaseService<ProductQCDetail> {
	/**
	 * 
	 * <p>根据检测结果id 获取结果明细</p> 
	 * @author leiwei
	 * @date 2014-3-14 下午1:16:51
	 * @param resultId
	 * @return
	 * @see
	 */
	List<ProductQCDetail> getByResId(String resultId);
	/**
	 * 
	 * <p>根据产品条码获取产品检验内容模板</p> 
	 * @author leiwei
	 * @date 2014-3-17 下午1:12:46
	 * @param sampleCode 产品条码
	 * @return
	 * @see
	 */
	List<ProductQCDetail> getBySampleBarCode(String sampleCode);

}
