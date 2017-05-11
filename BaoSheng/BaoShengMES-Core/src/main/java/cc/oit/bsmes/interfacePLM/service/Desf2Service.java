package cc.oit.bsmes.interfacePLM.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.Desf2;
/**
 * 
 *二维图
 * <p style="display:none">modifyRecord</p>
 * @author leiw
 * @date 2014-9-29 上午11:28:26
 * @since
 * @version
 */
public interface Desf2Service extends BaseService<Desf2> {
	
	public List<Desf2> getByProductId(String productId);
}
