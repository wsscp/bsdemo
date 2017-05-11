package cc.oit.bsmes.interfacePLM.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.ModDAO;
import cc.oit.bsmes.interfacePLM.model.Mod;
import cc.oit.bsmes.interfacePLM.service.ModService;

@Service
public class ModServiceImpl extends BaseServiceImpl<Mod> implements ModService {
	
	@Resource
	private ModDAO modDAO;
	
	@Override
	public List<Mod> getByProductId(Map<String,Object> param) {
		
		return modDAO.getByProductId(param);
	}
	
	
	
}
