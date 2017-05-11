package cc.oit.bsmes.pla.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.HighPriorityOrderItemProDec;

import java.util.List;

public interface HighPriorityOrderItemProDecService extends
		BaseService<HighPriorityOrderItemProDec> {

	/**
	 * <p>通过设备code获取设备上生成单最大的排序号</p> 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午5:28:33
	 * @param equipCode
	 * @return
	 * @see
	 */
	public int getMaxSeqByEquipCode(String equipCode);

	/**
	 * <p>将生产单从排序表移除</p> 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午6:08:21
	 * @param workOrderNo
	 * @see
	 */
	public void deleteByWorkOrderNo(String workOrderNo);

    /**
     * 根据equip和seq排序
     * @param orgCode
     * @return
     */
    public List<HighPriorityOrderItemProDec> getByOrgCode(String orgCode);

	/**
	 * <p>将生产单从排序表移除</p> 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午6:08:21
	 * @param equipCode
	 * @see
	 */
	public void deleteByEquipCode(String equipCode);

	/**
	 * <p>根据生产单号和序号插入排序表</p> 
	 * @author QiuYangjun
	 * @date 2014-5-20 下午4:13:18
	 * @param workOrderNo
	 * @param equipCode TODO
	 * @param seq
	 * @see
	 */
	public void insertSeqByWorkOrderNo(String workOrderNo, String equipCode, int seq);
}
