package cc.oit.bsmes.pro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.model.ProcessReceiptWip;

public interface ProcessReceiptWipDAO extends BaseDAO<ProcessReceiptWip>{
	public void insertAll(@Param("processReceiptWip")List<ProcessReceipt> processReceiptWip);
	
	public void deleteDate(String oldCraftsId);
}
