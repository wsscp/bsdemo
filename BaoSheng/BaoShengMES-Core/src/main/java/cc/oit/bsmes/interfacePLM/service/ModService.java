package cc.oit.bsmes.interfacePLM.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.Desf2;
import cc.oit.bsmes.interfacePLM.model.Mod;

/**
 * 
 *三维图
 * <p style="display:none">modifyRecord</p>
 * @author rongyidong
 * @date 
 * @since
 * @version
 */
public interface ModService extends BaseService<Mod> {
	
	public List<Mod> getByProductId(Map<String,Object> param);
	

}
