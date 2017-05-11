package cc.oit.bsmes.inv.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.inv.dao.MatPropDAO;
import cc.oit.bsmes.inv.model.MatProp;
import cc.oit.bsmes.inv.service.MatPropService;

@Service
public class MatPropServiceImpl extends BaseServiceImpl<MatProp> implements MatPropService {
	@Resource private MatPropDAO matPropDAO;
	
	@Override
	public void deleteByMatId(String param){
		 matPropDAO.deleteByMatId(param);
	}
	
	/**
	 * @Title: findByMatCode
	 * @Description: TODO(根据matCode去物料属性)
	 * @param: matCode 物料编码
	 * @return: List<MatProp>
	 * @throws
	 */
	public List<MatProp> findByMatCode(String matCode){
		return matPropDAO.findByMatCode(matCode);
	}
}
