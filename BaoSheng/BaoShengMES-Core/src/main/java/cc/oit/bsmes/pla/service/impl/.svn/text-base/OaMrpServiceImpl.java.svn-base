package cc.oit.bsmes.pla.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pla.dao.OaMrpDAO;
import cc.oit.bsmes.pla.model.OaMrp;
import cc.oit.bsmes.pla.service.OaMrpService;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * OA物料需求
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-4-15 下午2:23:32
 * @since
 * @version
 */
@Service
public class OaMrpServiceImpl extends BaseServiceImpl<OaMrp> implements OaMrpService {
	@Resource
	private OaMrpDAO oaMrpDAO;
	@Resource
	private MatService matService;

	/**
	 * 菜单首页主列表
	 * 
	 * @author DingXintao
	 * @date 2015-09-17
	 * @return List<OrderOA>
	 */
	public List<OaMrp> list(Map<String, Object> findParams) {
		return oaMrpDAO.list(findParams);
	}

	/**
	 * 菜单首页主列表
	 * 
	 * @author DingXintao
	 * @date 2015-09-17
	 * @return List<OrderOA>
	 */
	public int count(Map<String, Object> findParams) {
		return oaMrpDAO.count(findParams);
	}

	@Override
	public int deleteByContractNoOrderItemIdOrgCode(String contractNo, String orderItemId, String orgCode) {
		return oaMrpDAO.deleteByContractNoOrderItemIdOrgCode(contractNo, orderItemId, orgCode);
	}

	/**
	 * <p>
	 * 查询条件->物料信息下拉框：支持模糊查询
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<OaMrp>
	 * @see
	 */
	@Override
	public List<OaMrp> matsCombo(Map<String, Object> findParams) {
		List<OaMrp> list = oaMrpDAO.matsCombo(findParams);
		return list;
	}

	/**
	 * <p>
	 * 查询条件->生产线信息下拉框：支持模糊查询
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<OaMrp>
	 * @see
	 */
	@Override
	public List<OaMrp> equipCombo(Map<String, Object> findParams) {
		List<OaMrp> list = oaMrpDAO.equipCombo(findParams);
		return list;
	}

	/**
	 * <p>
	 * 导出功能
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-7-18 16:56:48
	 * @return List<Mat>
	 * @see
	 */
	@Override
	public List<OaMrp> findForExport(JSONObject queryFilter) throws InvocationTargetException, IllegalAccessException,
			NoSuchMethodException {
		OaMrp findParams = (OaMrp) JSONUtils.jsonToBean(queryFilter, OaMrp.class);
		// 导出查询条件queryStatus处理，因为页面传递字符串JSON无法自动转换成String数组，人工处理
		Object obj = queryFilter.get("queryStatus");
		String[] queryStatus = null;
		if (obj instanceof List) {
			List<String> queryStatusList = (List<String>) obj;
			queryStatus = queryStatusList.toArray(new String[0]);
		} else if (obj instanceof String) {
			String queryStatusStr = (String) obj;
			queryStatus = new String[] { queryStatusStr };
		}
		findParams.setQueryStatus(queryStatus);
		// 导出查询条件queryStatus处理结束
		findParams.setOrgCode(getOrgCode());
		return oaMrpDAO.find(findParams);
	}
}
