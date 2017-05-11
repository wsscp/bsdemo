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
package cc.oit.bsmes.wip.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.Debug;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-2-20 下午3:38:31
 * @since
 * @version
 */
public interface DebugDAO extends BaseDAO<Debug>{
	/** 
	 * <p>根据工单号查询最后一次未结束的调试信息</p> 
	 * @author QiuYangjun
	 * @date 2014-3-3 下午5:09:51
	 * @param workOrderNo
	 * @return 
	 */
	public Debug getByWorkOrderNoAndNullEndTime(String workOrderNo);
}
