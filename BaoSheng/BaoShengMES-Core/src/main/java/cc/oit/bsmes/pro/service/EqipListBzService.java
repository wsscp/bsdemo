package cc.oit.bsmes.pro.service;

import java.util.List;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.EqipListBz;

/**
 * @author 陈翔
 */
public interface EqipListBzService extends BaseService<EqipListBz> {
	/**
	 * 
	 * <p>
	 * 根据工序ID获取标准工序使用设备清单
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId
	 */
	public List<EqipListBz> getByProcessId(String processId);
	
	/**
	 * 查询标准工序使用设备清单列表
	 * @param processId
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 */
	public List<EqipListBz> getByProcessId(String processId, int start, int limit, List<Sort> sortList);
}