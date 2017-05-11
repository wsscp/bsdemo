package cc.oit.bsmes.common.mybatis.dialect;

import org.apache.ibatis.session.Configuration;

import cc.oit.bsmes.common.mybatis.dialect.Dialect.DBDialectType;

/**
 * @author Chanedi
 */
public class DialectParser {

    public static Dialect parse(Configuration configuration) {
        DBDialectType databaseType = null;
        try {
            databaseType = DBDialectType.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
        } catch (Exception e) {
            // ignore
        }
        if (databaseType == null) {
            throw new RuntimeException("the value of the dialect property in configuration.xml is not defined : " + configuration.getVariables().getProperty("dialect"));
        }
        Dialect dialect = null;
        switch (databaseType) {
            case MYSQL:
                dialect = new MySql5Dialect();
                break;
            case ORACLE:
                dialect = new OracleDialect();
                break;
		case SQLSERVER:
			dialect = new SqlServerDialect();
			break;
        }
        return dialect;
    }

}
