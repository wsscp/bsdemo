package cc.oit.bsmes.wip.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.SparkRepairDAO;
import cc.oit.bsmes.wip.model.SparkRepair;
import cc.oit.bsmes.wip.service.SparkRepairService;

@Service
public class SparkRepairServiceImpl extends BaseServiceImpl<SparkRepair> implements SparkRepairService {
	
	@Resource
	private SparkRepairDAO sparkRepairDAO;

	/**
	 * 重写查询
	 * */
	public List<SparkRepair> find(SparkRepair sparkRepair,Integer start, Integer limit) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return sparkRepairDAO.find(sparkRepair);
	}

	/**
	 * 重写查询
	 * */
	public int countFind(SparkRepair sparkRepair) {
		return sparkRepairDAO.countFind(sparkRepair);
	}
}
