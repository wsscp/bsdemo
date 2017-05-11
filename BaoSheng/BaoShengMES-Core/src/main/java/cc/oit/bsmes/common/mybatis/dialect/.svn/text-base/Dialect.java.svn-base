package cc.oit.bsmes.common.mybatis.dialect;

import java.util.List;

import cc.oit.bsmes.common.mybatis.Sort;

/**
 * 数据库方言。
 * Created by unknown
 * Modify by Chanedi
 */
public abstract class Dialect {

    public abstract String addLimitString(String sql, int skipResults, int maxResults);

    public abstract String addSortString(String sql, List<Sort> sortList);

    public static enum DBDialectType {

		MYSQL("mysql"), ORACLE("oracle"), SQLSERVER("sqlserver"), H2("h2");

        private String type;

        private DBDialectType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

    }

}