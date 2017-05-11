package cc.oit.bsmes.inv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.inv.model.MatProp;
import cc.oit.bsmes.inv.model.MatPropWip;

public interface MatPropWipDAO extends BaseDAO<MatPropWip>{
	public void insertAll(@Param("matPropWip")List<MatProp> matPropWip);
	
	
	/**
	 * <P>
	 * 根据ID获取质量信息（由于MatPropWip表中有个字段名为DESC，与数据库关键字冲突使得getById方法无法使用）
	 * <P>
	 * @author 前克
	 * @date 2106-03-22
	 * @param id
	 * @return
	 */
	public MatPropWip getMatPropWipById(String id);
	
	/**
	 * <P>
	 * 根据输入输出ID获取物料属性
	 * <P>
	 * @author 前克
	 * @date 2016-03-17
	 * @param processInOutId
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 */
	public List<MatPropWip> getByProcessInOutId(String processInOutId, int start,int limit, List<Sort> sortList);
	/**
	 * <P>
	 * 根据输入输出ID获取物料属性
	 * <P>
	 * @author 前克
	 * @date 2016-03-16
 	 * @param processInOutId(投入产出ID)
	 * @return
	 */
	public List<MatPropWip> getByProcessInOutId(String processInOutId);
	
	/**
	 * <P>
	 * 根据输入输出ID获取物料属性条数
	 * <P>
	 * @author 前克
	 * @date 2016-03-16
 	 * @param processInOutId(投入产出ID)
	 * @return
	 */
	public int countByProcessInOutId(String processInOutId);
	
	public void deleteDate(String oldCraftsId);
	
	/**
	 * @Title: findByMatCode
	 * @Description: TODO(根据matCode去查询)
	 * @param: matCode 物料编码
	 * @return: List<MatProp>
	 * @throws
	 */
	public List<MatProp> findByMatCode(String inOutId);
}
