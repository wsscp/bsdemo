package cc.oit.bsmes.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class PatchEquipCalendarJDBC {

	/**
	 * 新增T_BAS_EQIP_CALENDAR的SQL 参数：{0}:设备日历ID, {1}:设备编码, {2}:年月日, {3}:组织机构
	 * */
	public static String insertEquipCalendarSQL = "INSERT INTO T_BAS_EQIP_CALENDAR (ID, EQUIP_CODE, DATE_OF_WORK, CREATE_USER_CODE, CREATE_TIME, MODIFY_USER_CODE, MODIFY_TIME, ORG_CODE) VALUES (?, ?, ?, 'ADMIN', SYSTIMESTAMP, 'ADMIN', SYSTIMESTAMP, ?)";
	/**
	 * 新增T_BAS_EQIP_CAL_SHIFT的SQL 参数：{0}:设备日历ID, {1}:班次ID
	 * */
	public static String insertEquipCalShiftSQL = "INSERT INTO T_BAS_EQIP_CAL_SHIFT (ID, EQIP_CALENDAR_ID, WORK_SHIFT_ID, CREATE_USER_CODE, CREATE_TIME, MODIFY_USER_CODE, MODIFY_TIME) VALUES (SYS_GUID(), ?, ?, 'ADMIN', SYSTIMESTAMP, 'ADMIN', SYSTIMESTAMP)";
	/**
	 * 查询设备信息SQL 参数：{0}:组织机构, {1}:组织机构
	 * */
	public static String queryEquipInfoSQL = "SELECT LINE.CODE FROM T_FAC_EQIP_INFO LINE, T_FAC_PRODUCT_EQIP PE, T_FAC_EQIP_INFO EQUIP WHERE LINE.ID = PE.PRODUCT_LINE_ID AND PE.EQUIP_ID = EQUIP.ID AND LINE.TYPE = 'PRODUCT_LINE' AND LINE.ORG_CODE = ? AND EQUIP.ORG_CODE = ? ORDER BY LINE.CODE, NLSSORT(LINE.NAME, 'NLS_SORT=SCHINESE_PINYIN_M')";
	/**
	 * 查询月日历SQL 参数：{0}:组织机构
	 * */
	public static String queryMonthCalendarSQL = "SELECT WORK_MONTH, DAY1, DAY2, DAY3, DAY4, DAY5, DAY6, DAY7, DAY8, DAY9, DAY10, DAY11, DAY12, DAY13, DAY14, DAY15, DAY16, DAY17, DAY18, DAY19, DAY20, DAY21, DAY22, DAY23, DAY24, DAY25, DAY26, DAY27, DAY28, DAY29, DAY30, DAY31 from T_BAS_MONTH_CALENDAR WHERE ORG_CODE = ? ORDER BY WORK_MONTH";
	/**
	 * 默认组织编码
	 * */
	public static String orgCode = "bstl01";
	/**
	 * 默认班次ID
	 * */
	public static String[] shiftArray = { "1", "2", "3" };

	/**
	 * 连接Oracle数据库
	 */
	public static void main(String[] args) throws Exception {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		PreparedStatement pre2 = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		PreparedStatement pre3 = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		PreparedStatement pre4 = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		ResultSet result2 = null;// 创建一个结果集对象
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			System.out.println("开始尝试连接数据库！");
			String url = "jdbc:oracle:" + "thin:@192.168.1.32:1521:bsmesdev";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = "bsmes";// 用户名,系统默认的账户名
			String password = "bsmes";// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			// con.setAutoCommit(false);// 1,首先把Auto commit设置为false,不让它自动提交
			System.out.println("连接成功！");

			process(con, pre, pre2, pre3, pre4, result, result2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (result2 != null)
					result2.close();
				if (pre != null)
					pre.close();
				if (pre2 != null)
					pre2.close();
				if (pre3 != null)
					pre3.close();
				if (pre4 != null)
					pre4.close();
				if (con != null)
					con.close();
				System.out.println("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private static void process(Connection con, PreparedStatement pre, PreparedStatement monthCalendarPre,
			PreparedStatement equipCalendarPre, PreparedStatement equipCalShiftPre, ResultSet equipInfoResult, ResultSet monthCalendarResult)
			throws SQLException {
		equipCalendarPre = con.prepareStatement(insertEquipCalendarSQL);
		equipCalShiftPre = con.prepareStatement(insertEquipCalShiftSQL);
		monthCalendarPre = con.prepareStatement(queryMonthCalendarSQL);

		pre = con.prepareStatement("DELETE FROM T_BAS_EQIP_CAL_SHIFT");
		pre.execute();
		pre = con.prepareStatement("DELETE FROM T_BAS_EQIP_CALENDAR");
		pre.execute();
		pre = con.prepareStatement(queryEquipInfoSQL);
		pre.setString(1, orgCode);
		pre.setString(2, orgCode);
		equipInfoResult = pre.executeQuery();
		while (equipInfoResult.next()) {
			String equipCode = equipInfoResult.getString("CODE");
			System.out.println("========设备" + equipCode + "添加日历中....==========");

			monthCalendarPre.setString(1, orgCode);
			monthCalendarResult = monthCalendarPre.executeQuery();
			while (monthCalendarResult.next()) {
				List<String> dateOfWorkArray = getDateOfWorkArray(monthCalendarResult);
//				System.out.println("========设备" + equipCode + "添加日历" + monthCalendarResult.getString("WORK_MONTH") + "数量："
//						+ dateOfWorkArray.size() + "中....==========");
				for (String dateOfWork : dateOfWorkArray) {
					String uuid = UUID.randomUUID().toString();
					equipCalendarPre.setString(1, uuid);
					equipCalendarPre.setString(2, equipCode);
					equipCalendarPre.setString(3, dateOfWork);
					equipCalendarPre.setString(4, orgCode);
					equipCalendarPre.addBatch();
					for (String shiftId : shiftArray) {
						equipCalShiftPre.setString(1, uuid);
						equipCalShiftPre.setString(2, shiftId);
						equipCalShiftPre.addBatch();
					}
					equipCalShiftPre.executeBatch();
					equipCalShiftPre.clearBatch();
				}
				equipCalendarPre.executeBatch();
				equipCalendarPre.clearBatch();
			}
			// con.commit();// 2,进行手动提交（commit）
			System.out.println("========设备" + equipCode + "添加完毕，并已提交==========");
		}

		System.out.println("========设备添加全部完成==========");
		// con.setAutoCommit(true);// 3,提交完成后回复现场将Auto commit,还原为true,
	}

	/**
	 * 从月日历获取当月天
	 * */
	public static List<String> getDateOfWorkArray(ResultSet resultSet) throws SQLException {
		List<String> dateOfWorkArray = new ArrayList<String>();
		String workMonth = resultSet.getString("WORK_MONTH");
		if (StringUtils.isNotEmpty(resultSet.getString("DAY1"))) {
			dateOfWorkArray.add(workMonth + "01");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY2"))) {
			dateOfWorkArray.add(workMonth + "02");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY3"))) {
			dateOfWorkArray.add(workMonth + "03");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY4"))) {
			dateOfWorkArray.add(workMonth + "04");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY5"))) {
			dateOfWorkArray.add(workMonth + "05");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY6"))) {
			dateOfWorkArray.add(workMonth + "06");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY7"))) {
			dateOfWorkArray.add(workMonth + "07");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY8"))) {
			dateOfWorkArray.add(workMonth + "08");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY9"))) {
			dateOfWorkArray.add(workMonth + "09");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY11"))) {
			dateOfWorkArray.add(workMonth + "10");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY11"))) {
			dateOfWorkArray.add(workMonth + "11");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY12"))) {
			dateOfWorkArray.add(workMonth + "12");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY13"))) {
			dateOfWorkArray.add(workMonth + "13");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY14"))) {
			dateOfWorkArray.add(workMonth + "14");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY15"))) {
			dateOfWorkArray.add(workMonth + "15");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY16"))) {
			dateOfWorkArray.add(workMonth + "16");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY17"))) {
			dateOfWorkArray.add(workMonth + "17");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY18"))) {
			dateOfWorkArray.add(workMonth + "18");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY19"))) {
			dateOfWorkArray.add(workMonth + "19");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY20"))) {
			dateOfWorkArray.add(workMonth + "20");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY21"))) {
			dateOfWorkArray.add(workMonth + "21");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY22"))) {
			dateOfWorkArray.add(workMonth + "22");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY23"))) {
			dateOfWorkArray.add(workMonth + "23");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY24"))) {
			dateOfWorkArray.add(workMonth + "24");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY25"))) {
			dateOfWorkArray.add(workMonth + "25");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY26"))) {
			dateOfWorkArray.add(workMonth + "26");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY27"))) {
			dateOfWorkArray.add(workMonth + "27");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY28"))) {
			dateOfWorkArray.add(workMonth + "28");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY29"))) {
			dateOfWorkArray.add(workMonth + "29");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY30"))) {
			dateOfWorkArray.add(workMonth + "30");
		}
		if (StringUtils.isNotEmpty(resultSet.getString("DAY31"))) {
			dateOfWorkArray.add(workMonth + "31");
		}
		return dateOfWorkArray;
	}
}
