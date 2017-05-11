package cc.oit.bsmes.inv.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.inv.dao.MatPropWipDAO;
import cc.oit.bsmes.inv.model.MatProp;
import cc.oit.bsmes.inv.model.MatPropWip;
import cc.oit.bsmes.inv.service.MatPropWipService;

@Service
public class MatPropWipServiceImpl extends BaseServiceImpl<MatPropWip> implements MatPropWipService {
	@Resource private MatPropWipDAO matPropWipDAO;
	
	/**
	 * @Title: findByMatCode
	 * @Description: TODO(根据matCode去物料属性)
	 * @param: matCode 物料编码
	 * @return: List<MatProp>
	 * @throws
	 */
	public List<MatProp> findByMatCode(String inOutId){
		return matPropWipDAO.findByMatCode(inOutId);
	}
}
