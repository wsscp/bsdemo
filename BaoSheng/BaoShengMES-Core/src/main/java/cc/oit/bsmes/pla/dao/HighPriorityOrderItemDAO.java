package cc.oit.bsmes.pla.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.HighPriorityOrderItem;

public interface HighPriorityOrderItemDAO extends BaseDAO<HighPriorityOrderItem> {

	/**
	 * 更新顺序号为空 
	 * @author JinHanyun
	 * @date 2014-2-12 上午9:27:46
	 * @param id
	 * @see
	 */
	public void updateSeq(String id);
}
