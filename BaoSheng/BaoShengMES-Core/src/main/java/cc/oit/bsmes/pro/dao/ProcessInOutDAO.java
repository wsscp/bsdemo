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

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessInOut;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * ProcessInOutDAO
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月31日 下午3:55:21
 * @since
 * @version
 */
public interface ProcessInOutDAO extends BaseDAO<ProcessInOut> {

    public List<ProcessInOut> getByProductProcessId(String productProcessId);

    public ProcessInOut getOutByProductProcessId(String productProcessId);
    
    public List<ProcessInOut> getAllByProductProcessId(Map<String, Object> findParams);

	/**
	 * <p>统计工序的产出 和投入的成品 半成品</p> 
	 * @author QiuYangjun
	 * @date 2014-3-25 上午11:37:03
	 * @param processId
	 * @return
	 * @see
	 */
	int countByProcessId(String processId);

	/**
     * 终端获取生产单投入物料
     * 
     * @author DingXintao
     * @param workOrderNo 生产单号
     * @return List<ProcessInOut>
     */
    public List<ProcessInOut> getInByWorkOrderNo(String workNo);
    
    public void insertBatch(@Param("processInOutList")List<ProcessInOut> processInOutList) ;
    
    public void updateQcInOut(String craftsCode);
    
    public List<Map<String, String>> findProcessMap();

}
