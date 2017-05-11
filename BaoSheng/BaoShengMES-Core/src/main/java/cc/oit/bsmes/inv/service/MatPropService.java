package cc.oit.bsmes.inv.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.inv.model.MatProp;

public interface MatPropService extends BaseService<MatProp> {

	public void deleteByMatId(String param);

	/**
	 * @Title: findByMatCode
	 * @Description: TODO(根据matCode去查询)
	 * @param: matCode 物料编码
	 * @return: List<MatProp>
	 * @throws
	 */
	public List<MatProp> findByMatCode(String matCode);

}
