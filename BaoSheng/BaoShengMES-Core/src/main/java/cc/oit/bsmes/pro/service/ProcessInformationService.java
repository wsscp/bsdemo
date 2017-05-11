package cc.oit.bsmes.pro.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.constants.ProcessSection;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProcessInformation;

/**
 * 
 * 工序信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-3-26 下午4:20:21
 * @since
 * @version
 */
public interface ProcessInformationService extends BaseService<ProcessInformation> {
	/**
	 * 
	 * 获取工段信息
	 * 
	 * @author leiwei
	 * @date 2014-3-26 下午4:17:48
	 * @return
	 * @see
	 */
	List<ProcessInformation> getSection();

	/**
	 * 
	 * <p>
	 * TODO(根据工段名称获取工序信息 )
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-3-26 下午4:23:52
	 * @param section
	 * @return
	 * @see
	 */
	List<ProcessInformation> getBySection(String section);

	/**
	 * <p>
	 * 查询条件->工序信息下拉框：支持模糊查询
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<ProcessInformation>
	 * @see
	 */
	public List<ProcessInformation> findByCodeOrName(String query);

	public ProcessInformation getByCode(String code);

    /**
	 * 
	 * <p>
	 * 排生产单 : 根据工段、客户订单ID获取工序列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @param map 保存查询结果到map
	 * @param section 工段
	 * @param orderItemIdArray 客户订单ID数组
	 * 
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public void getOrderProcessBySection(Map map, ProcessSection section, String[] orderItemIdArray);

}
