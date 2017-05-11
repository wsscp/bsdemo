package cc.oit.bsmes.pro.service;

import jxl.Sheet;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProductQCTemplate;
/**
 * 产品QC检验内容模板
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author Administrator
 * @date 2014-3-12 下午1:33:26
 * @since
 * @version
 */
public interface ProductQCTemplateService extends BaseService<ProductQCTemplate> {
	/**
	 * 
	 * <p>根据实验名称，产品代码确认该检验内容模板是否重复</p> 
	 * @author Administrator
	 * @date 2014-5-19 下午3:51:16
	 * @param name
	 * @param productCode
	 * @return
	 * @see
	 */
	ProductQCTemplate getByNameAndProductCode(String name, String productCode);

	void importQcTemp(Sheet sheet, String orgCode);

}
