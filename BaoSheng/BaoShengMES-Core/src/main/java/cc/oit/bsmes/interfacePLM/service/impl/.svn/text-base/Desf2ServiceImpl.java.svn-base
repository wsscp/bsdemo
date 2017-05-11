package cc.oit.bsmes.interfacePLM.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.Desf2DAO;
import cc.oit.bsmes.interfacePLM.model.Desf2;
import cc.oit.bsmes.interfacePLM.service.Desf2Service;

@Service
public class Desf2ServiceImpl extends BaseServiceImpl<Desf2> implements Desf2Service {
	@Resource private Desf2DAO desf2DAO;

	@Override
	public List<Desf2> getByProductId(String productId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("productId", productId);
		return desf2DAO.getByProductId(map);
	}
}
