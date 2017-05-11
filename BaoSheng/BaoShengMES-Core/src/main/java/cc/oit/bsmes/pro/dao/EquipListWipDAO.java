package cc.oit.bsmes.pro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.EquipListWip;

public interface EquipListWipDAO extends BaseDAO<EquipListWip>{
	public void insertAll(@Param("equipListWip")List<EquipList> equipListWip);
	
    /**
     * <P>
     * 根据工序id获取工序所绑定的生产线列表
     * <P>
     * @author 前克
     * @date 200116-03-15 16:36
     * @param processId(工序ID)
     */
	public List<EquipListWip> getByProcessId(String processId);
	
	public void deleteDate(String oldCraftsId);
}
