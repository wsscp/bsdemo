package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.VProcessEquip;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;

public interface VProcessEquipDAO extends BaseDAO<VProcessEquip>{
	
	public List<VProcessEquip> getItemCode(String equipCode);
}
