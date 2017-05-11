package cc.oit.bsmes.pro.service;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProcessInOut;

import java.util.List;

/**
 * ProcessInOutService
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-25 下午2:00:28
 * @since
 * @version
 */
public interface ProcessInOutService extends BaseService<ProcessInOut>{

	/**
	 * 
	 * 查询工序的产出 和投入的成品 半成品 
	 * @author JinHanyun
	 * @date 2013-12-25 下午2:01:54
	 * @param processId
	 * @return
	 * @see
	 */
	public List<ProcessInOut> getByProcessId(String processId);
	
	
	/**
	 * 
	 * <p>通过工序ID查询所有工序投入</p> 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午3:26:13
	 * @param processId
	 * @return
	 * @see
	 */
	public List<ProcessInOut> getInByProcessId(String processId);

	/**
	 *
	 * @param processId
	 * @return
	 */
	public ProcessInOut getOutByProcessId(String processId);

	/**
	 * <p>查询工序的产出 和投入的成品 半成品 </p> 
	 * @author QiuYangjun
	 * @date 2014-3-25 上午11:31:09
	 * @param processId
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	public List<ProcessInOut> getByProcessId(String processId, int start,int limit, List<Sort> sortList);


	/**
	 * <p>统计工序的产出 和投入的成品 半成品</p> 
	 * @author  QiuYangjun
	 * @date 2014-3-25 上午11:34:59
	 * @param processId
	 * @return
	 * @see
	 */
	public int countByProcessId(String processId);

    /**
     * 终端获取生产单投入物料
     * 
     * @author DingXintao
     * @param workOrderNo 生产单号
     * @return List<ProcessInOut>
     */
    public List<ProcessInOut> getInByWorkOrderNo(String workOrderNo);
    
    public void insertBatch(List<ProcessInOut> processInOutList);
    
    public void updateQcInOut(String craftsCode);
}
