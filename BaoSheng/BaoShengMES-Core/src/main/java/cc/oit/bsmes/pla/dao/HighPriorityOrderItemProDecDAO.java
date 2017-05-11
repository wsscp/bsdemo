package cc.oit.bsmes.pla.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.HighPriorityOrderItemProDec;

import java.util.List;
import java.util.Map;

public interface HighPriorityOrderItemProDecDAO extends BaseDAO<HighPriorityOrderItemProDec> {
	/**
	 * 
	 * <p>通过设备号获取最大序列号</p> 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午6:10:00
	 * @param equipCode
	 * @return
	 * @see
	 */
	public int getMaxSeqByEquipCode(String equipCode);

	/**
	 * <p>将生产单从排序表移除</p> 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午6:09:40
	 * @param workOrderNo
	 * @see
	 */
	public void deleteByWorkOrderNo(String workOrderNo);

    public List<HighPriorityOrderItemProDec> getByOrgCode(String orgCode);

	/**
	 * <p>将生产单从排序表移除</p> 
	 * @author QiuYangjun
	 * @date 2014-5-20 下午3:31:50
	 * @param equipCode
	 * @see
	 */
	public void deleteByEquipCode(String equipCode);
	
	/**
	 * 
	 * <p>根据生产单号和序号插入排序表</p> 
	 * @author QiuYangjun
	 * @date 2014-5-20 下午4:09:46
	 * @param param(SEQ,WORKORDER,USER)
	 * @see
	 */
	public void insertSeqByWorkOrderNo(Map<String,Object> param);
}
