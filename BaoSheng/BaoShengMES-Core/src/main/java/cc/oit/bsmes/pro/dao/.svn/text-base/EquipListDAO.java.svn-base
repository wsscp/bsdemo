/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */
package cc.oit.bsmes.pro.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.EquipList;

/**
 * EquipListDAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2014年1月22日 上午10:32:46
 * @since
 * @version
 */
public interface EquipListDAO extends BaseDAO<EquipList> {

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-21 下午5:06:39
	 * @param findParams
	 * @return
	 * @see
	 */
	public List<EquipList> getByProcessId(EquipList findParams);

	/**
	 * <p>
	 * 根据工序code查询所有相关的流程信息，流程使用的设备
	 * </p>
	 * 
	 * @author zhangdongping
	 * @date 2014-6-12 下午2:47:37
	 * @param processCode
	 * @return
	 * @see
	 */
	public List<EquipList> getAllByProcessCode(String processCode);

	/**
	 * <p>
	 * 通过设备代码查询设备工序关联信息
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-11-17 10:49:21
	 * @param equipCode 设备编码
	 * @return List<EquipList>
	 */
	public List<EquipList> getByEquipCodeReal(String equipCode);
	
	public List<EquipList> getAllByProcessId(Map<String, Object> findParams);

	public List<EquipList> getByEquipCode(String equipCode);

	/**
	 * 增加mes中缺少的生产线
	 * 
	 * @param productCode
	 */
	public void insertScxFromPlm(String productCode);

	public void insertBatch(@Param("list") List<EquipList> list);

}
