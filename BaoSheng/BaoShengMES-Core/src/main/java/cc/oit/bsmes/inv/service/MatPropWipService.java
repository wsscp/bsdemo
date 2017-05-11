package cc.oit.bsmes.inv.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.inv.model.MatProp;
import cc.oit.bsmes.inv.model.MatPropWip;

public interface MatPropWipService extends BaseService<MatPropWip> {

	/**
	 * @Title: findByMatCode
	 * @Description: TODO(根据matCode去查询)
	 * @param: matCode 物料编码
	 * @return: List<MatProp>
	 * @throws
	 */
	public List<MatProp> findByMatCode(String inOutId);
}
