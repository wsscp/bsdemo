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
import cc.oit.bsmes.wip.model.RealCost;

import java.util.List;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-2-21 下午5:00:17
 * @since
 * @version
 */
public interface RealCostDAO extends BaseDAO<RealCost>{

    /**
     * 根据生产单号、条码 验证是否已投料
     * @param workOrderNo
     * @param batchNo
     * @return
     */
    List<RealCost> checkProductPutIn(String workOrderNo,String batchNo);

    /**
     * 取消投料
     * @param barCode
     * @return
     */
    int deleteByBarCode(String barCode);

}
