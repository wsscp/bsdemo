package cc.oit.bsmes.common.mybatis.dialect;

import java.util.List;

import cc.oit.bsmes.common.mybatis.Sort;

public class SqlServerDialect extends Dialect {

    @Override
	public String addLimitString(String sql, int offset, int limit) {
		sql = sql.trim();

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);

		pagingSelect.append("select top ");

		pagingSelect.append(limit);

		pagingSelect.append(" from (");

		pagingSelect.append(sql);

		pagingSelect.append(") t where t.id not in (select top ");

		pagingSelect.append(offset);

		pagingSelect.append(" t1.id from (");

		pagingSelect.append(sql);

		pagingSelect.append(") t1)");



		return pagingSelect.toString();
	}

	@Override
	public String addSortString(String sql, List<Sort> sortList) {
		return SortHelper.addSortString(sql, sortList);
    }

}