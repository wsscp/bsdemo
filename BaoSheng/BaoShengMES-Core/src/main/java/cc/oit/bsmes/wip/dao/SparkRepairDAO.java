package cc.oit.bsmes.wip.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.SparkRepair;

public interface SparkRepairDAO extends BaseDAO<SparkRepair>{

	/**
	 * 重写查询
	 * */
	public List<SparkRepair> find(SparkRepair sparkRepair);

	/**
	 * 重写查询
	 * */
	public int countFind(SparkRepair sparkRepair);
}
