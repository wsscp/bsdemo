package cc.oit.bsmes.pro.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.EqipListBzDAO;
import cc.oit.bsmes.pro.model.EqipListBz;
import cc.oit.bsmes.pro.service.EqipListBzService;

/**
 * @author 陈翔
 */
@Service
public class EqipListBzServiceImpl extends BaseServiceImpl<EqipListBz> implements EqipListBzService {
	@Resource
	public EqipListBzDAO eqipListBzDAO;

	/**
	 * 
	 * <p>
	 * 根据工序ID获取标准工序使用设备清单
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId
	 */
	public List<EqipListBz> getByProcessId(String processId) {
		return eqipListBzDAO.getByProcessId(processId);
	}

	@Override
	public List<EqipListBz> getByProcessId(String processId, int start,
			int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
        return getByProcessId(processId);
	}
}