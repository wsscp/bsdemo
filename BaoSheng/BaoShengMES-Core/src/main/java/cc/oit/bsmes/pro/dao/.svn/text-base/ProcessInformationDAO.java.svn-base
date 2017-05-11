package cc.oit.bsmes.pro.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessInformation;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-3-26 下午4:25:03
 * @since
 * @version
 */
public interface ProcessInformationDAO extends BaseDAO<ProcessInformation> {
	public List<ProcessInformation> getSection(Map<String, Object> param);

	public List<ProcessInformation> getBySection(Map<String, Object> param);

	/**
	 * <p>
	 * 模糊查询列表：根据name或code
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @param param 参数
	 * @return List<ProcessInformation>
	 * @see
	 */
	List<ProcessInformation> findByCodeOrName(Map<String, Object> param);

	public ProcessInformation getByCode(String code);

	/**
	 * 
	 * <p>
	 * 排生产单 : 根据工段、客户订单ID获取工序列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @param param : processSection 工段; orderItemIdArray 客户订单ID数组
	 * 
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>>getOrderProcessBySection(Map<String, Object> param);
	
	/**
	 * 
	 * <p>
	 * 根据订单ID获取该工序成缆工序的绞向
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId 工序ID
	 * 
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getCableFace(String processId);

	/**
	 * 
	 * <p>
	 * 根据订单ID获取该工序成缆工序的线芯排列
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId 工序ID
	 * 
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getCableOrder(String processId);

	/**
	 * 
	 * <p>
	 * 根据订单ID获取该工序单绞工序的节距
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId 工序ID
	 * 
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getWringDist(String processId);
	
	/**
	 * 
	 * <p>
	 * 根据订单ID获取该工序包带搭盖率
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId 工序ID
	 * 
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getCoverRate(String processId);

	/**
	 * 
	 * <p>
	 * 根据订单ID获取该工序编织密度
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId 工序ID
	 * 
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getBraidDensity(String processId);

}
