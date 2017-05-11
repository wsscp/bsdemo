package cc.oit.bsmes.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ValidationCrafts {

	private static String[] productCodeArray = {"CZ20103190051-3*2.5", "CL40100090008-4*0.75", "CL40100090043-4*1.5", "CZ20103190317-5*16", "CZ20100130201-4*2.5", "CZ20103190203-4*2.5", "CL40300040005-4*1.5", "CZ20103190215-4*16"};
	
	private static Connection con = null;
	private static PreparedStatement pre = null;
	private static ResultSet rs = null;

	private static String crafts_query_by_product_code = "select id, crafts_name from t_pro_product_crafts where product_code = ''{0}''";
	private static String process_query_by_crafts_id = "select id, process_name from t_pro_product_process where product_crafts_id = ''{0}''";
	private static String equipline_query_by_process_id = "select id, equip_code from t_pro_eqip_list where process_id = ''{0}''";
	private static String inout_query_by_process_id = "select id, mat_code, in_or_out from t_pro_process_in_out where product_process_id = ''{0}''";
	private static String mat_query_by_product_code = "select id, mat_code, mat_name, mat_type from t_inv_mat where mat_code = ''{0}''";
	private static String insert_inv_mat = "INSERT INTO T_INV_MAT (ID, MAT_CODE, MAT_NAME, MAT_TYPE, HAS_PIC, IS_PRODUCT, PRODUCT_CODE, CREATE_TIME, CREATE_USER_CODE, MODIFY_TIME, MODIFY_USER_CODE, REMARK, TEMPLET_ID, ORG_CODE, COLOR, MAT_SIZE, MAT_SPEC, MAT_SECTION) VALUES (sys_guid(), ''{0}'', ''{1}'', ''SEMI_FINISHED_PRODUCT'', ''0'', ''0'', null, sysdate, ''admin'', sysdate, ''admin'', null, ''1'', ''bstl01'', ''7/黄色'', null, null, null)";
	
	public static void main(String[] args) throws Exception {
		List<String> sql = new ArrayList<String>();
		openCon();
		ResultSet processResult = null;
		ResultSet inoutResult = null;
		ResultSet matResult = null;
		try {

			for (String productCode : productCodeArray) {
				pre = con.prepareStatement(MessageFormat.format(crafts_query_by_product_code, productCode));
				rs = pre.executeQuery();
				int countCrafts = 0;
				while (rs.next()) {
					countCrafts++;
					String crafts_id = rs.getString(1);
					String crafts_name = rs.getString(2);
					pre = con.prepareStatement(MessageFormat.format(process_query_by_crafts_id, crafts_id));
					processResult = pre.executeQuery();
					int countProcess = 0;
					while (processResult.next()) {
						countProcess++;
						String process_id = processResult.getString(1);
						String process_name = processResult.getString(2);
						pre = con.prepareStatement(MessageFormat.format(inout_query_by_process_id, process_id));
						inoutResult = pre.executeQuery();
						int countInout = 0;
						while (inoutResult.next()) {
							countInout ++;
							String mat_id = inoutResult.getString(1);
							String mat_code = inoutResult.getString(2);
							pre = con.prepareStatement(MessageFormat.format(mat_query_by_product_code, mat_id));
							matResult = pre.executeQuery();
							int count = 0;
							while (matResult.next()) {
								count++;
								break;
							}
							if (count == 0) {
								System.out.println("产品:" + productCode + ",工艺:" + crafts_name + ",工序:" + process_name + ",投入产出:" + mat_code + "在表[t_inv_mat]无记录");
//								sql.add(MessageFormat.format(insert_inv_mat, mat_code, mat_code));
//								String a = MessageFormat.format(insert_inv_mat, mat_code, mat_code);
//								System.out.println(a);
//								pre = con.prepareStatement(a);
//								pre.addBatch();
//								pre.executeBatch();
//								con.commit();
							}
						}
						if (countInout == 0) {
							System.out.println("产品:" + productCode + ",工艺:" + crafts_name + ",工序:" + process_name + "没有投入产出");
						}
					}
					if(countProcess == 0){
					    System.out.println("产品:" + productCode + ",工艺:" + crafts_name + "没有工序");
					}
				}
				if(countCrafts == 0){
				    System.out.println("产品:" + productCode + "没有工艺");
				}
				
			}
		} finally {
			if (matResult != null)
				matResult.close();
			if (inoutResult != null)
				inoutResult.close();
			if (processResult != null)
				processResult.close();
			closeCon();
		}
		
//		for(String s : sql){
//			System.out.println(s+";");
//		}
//		System.out.println("--"+sql.size());
		System.out.println("--完成");
	}

	// 打开连接
	private static void openCon() throws ClassNotFoundException, SQLException {
		// 加载驱动
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 得到连接
		con = DriverManager.getConnection(
				"jdbc:oracle:thin:@127.0.0.1:1521:orcl", "bsmestest",
				"bsmestest");
	}

	private static void closeCon() throws SQLException {
		// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
		// 注意关闭的顺序，最后使用的最先关闭
		if (rs != null)
			rs.close();
		if (pre != null)
			pre.close();
		if (con != null)
			con.close();
	}

}