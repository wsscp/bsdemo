package cc.oit.bsmes.pla.handler;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;





import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

import cc.oit.bsmes.pro.model.ProcessQc;

public class QcWipHandler extends BaseTypeHandler{
	
	@SuppressWarnings("unchecked")
	@Override
	public void setNonNullParameter(PreparedStatement parameterSetter, int i,Object o,JdbcType jdbcType)throws SQLException {
		Connection conn = null;
		try {
			List<ProcessQc> list = (ArrayList<ProcessQc>) o;
			CommonsDbcpNativeJdbcExtractor extractor = new CommonsDbcpNativeJdbcExtractor();
			conn = extractor.getNativeConnectionFromStatement(parameterSetter);
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@220.248.106.86:1521/bsmes","bsmes", "bsmes");
			ARRAY array = getArray(conn,"BSMES.QCWIOOBJ", "BSMES.QCWIPOBJTRUE", list);
			parameterSetter.setArray(i, array);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (null != conn) {
				//conn.close();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private ARRAY getArray(Connection con, String OracleObj,String Oraclelist, List listData)throws Exception {
		ARRAY array = null;
		ArrayDescriptor desc = ArrayDescriptor.createDescriptor(Oraclelist, con);
		STRUCT[] structs = new STRUCT[listData.size()];
		if (listData != null && listData.size() > 0) {
			StructDescriptor structdesc = new StructDescriptor(OracleObj, con);
			for (int i = 0; i < listData.size(); i++) {
				Object[] result = {((ProcessQc) listData.get(i)).getId(),((ProcessQc) listData.get(i)).getProcessId(),((ProcessQc) listData.get(i)).getCheckItemCode(),
						((ProcessQc) listData.get(i)).getCheckItemName(),((ProcessQc) listData.get(i)).getFrequence(),((ProcessQc) listData.get(i)).getNeedDa(),
						((ProcessQc) listData.get(i)).getNeedIs(),((ProcessQc) listData.get(i)).getItemTargetValue(),((ProcessQc) listData.get(i)).getItemMaxValue(),
						((ProcessQc) listData.get(i)).getItemMinValue(),((ProcessQc) listData.get(i)).getDataType(),((ProcessQc) listData.get(i)).getDataUnit(),
						((ProcessQc) listData.get(i)).getMarks(),((ProcessQc) listData.get(i)).getHasPic(),
						((ProcessQc) listData.get(i)).getNeedShow(),((ProcessQc) listData.get(i)).getNeedFirstCheck(),((ProcessQc) listData.get(i)).getNeedMiddleCheck(),
						((ProcessQc) listData.get(i)).getNeedInCheck(),((ProcessQc) listData.get(i)).getNeedOutCheck(),((ProcessQc) listData.get(i)).getNeedAlarm(),
						((ProcessQc) listData.get(i)).getValueDomain(),((ProcessQc) listData.get(i)).getEmphShow(),
						((ProcessQc) listData.get(i)).getModifyRemarks()};
				structs[i] = new STRUCT(structdesc, con, result);
			}
			array = new ARRAY(desc, con, structs);
		}else{
			array = new ARRAY(desc, con, structs);
		}
		return array;
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
