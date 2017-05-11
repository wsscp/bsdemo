package cc.oit.bsmes.inv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.model.MatWip;

public interface MatWipDAO extends BaseDAO<MatWip>{
	public void insertAll(@Param("matWip")List<Mat> matWip);
	
	 /**
     * 根据物料代码，订单明细和投入产出获取业务数据中物料信息
     */ 
	public List<MatWip> getByMatCode(String matCode,String salesOrderItemId,String processInOutId);
	
    /**
     * 获取所有原材料列表，包括型号规格单丝直径等信息
     * @author 宋前克
     * @date 2016-03-16
     * */
    public List<MatWip> getMaterialsArray();
    
    public void deleteDate(String oldCraftsId);
}
