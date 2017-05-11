package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.VProcessEquipDAO;
import cc.oit.bsmes.bas.model.VProcessEquip;
import cc.oit.bsmes.bas.service.VProcessEquipService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VProcessEquipServiceImpl extends BaseServiceImpl<VProcessEquip> implements VProcessEquipService{

	@Resource
	private VProcessEquipDAO vProcessEquipDAO;
	
	@Override
	public List<VProcessEquip> getItemCode(String equipCode) {
		return vProcessEquipDAO.getItemCode(equipCode);
	}

}
