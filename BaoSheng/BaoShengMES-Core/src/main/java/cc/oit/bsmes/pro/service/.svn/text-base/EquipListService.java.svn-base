package cc.oit.bsmes.pro.service;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.EquipList;
import jxl.Sheet;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * EquipListService
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2014年1月22日 上午10:33:53
 * @since
 * @version
 */
public interface EquipListService extends BaseService<EquipList> {

	/**
	 * <p>
	 * 根据工序ID获取所有工装夹具
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午4:37:41
	 * @param processId
	 * @return
	 * @see
	 */
	List<EquipList> getToolsByProcessId(String processId);

	/**
	 * <p>
	 * 通过工序号和设备代码查询工序使用设备
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-13 下午5:20:13
	 * @param processId
	 * @param equipCode
	 * @return
	 * @see
	 */
	public EquipList getByProcessIdAndEquipCode(String processId, String equipCode);

	/**
	 * 
	 * <p>
	 * getByProcessId
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-3 下午2:00:03
	 * @param processId
	 * @return
	 * @see
	 */
	public List<EquipList> getByProcessId(String processId);

	/**
	 * <p>
	 * 根据工序ID获取所有设备列表
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-17 下午4:24:55
	 * @param processId
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see
	 */
	public List<EquipList> getByProcessId(String processId, int start, int limit, List<Sort> sortList);

	/**
	 * <p>
	 * 根据工序ID获取所有设备列表
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-17 下午4:24:55
	 * @param processId
	 * @return
	 * @see
	 */
	public List<EquipList> getIdleEquip(String processId);

	public void importEquipList(Sheet sheet, String orgCode);

	public List<EquipList> getByEquipCode(String equipCode);

	public void insertBatch(List<EquipList> list);
}
