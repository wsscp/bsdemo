package cc.oit.bsmes.pro.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProductQCResult;
/**
 * 
 * 产品QC检验结果
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-13 下午3:06:15
 * @since
 * @version
 */
public interface ProductQCResultService extends BaseService<ProductQCResult> {
	
	public void deleteById(String id);
}
