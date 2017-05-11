package cc.oit.bsmes.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessService;

public class TestProductAndCrafts extends BaseTest {

	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private EquipListService equipListService;
	@Resource
	private ProcessQcService processQcService;

	@Test
	public void process() {
		Connection con = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		try {
			// 加载驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 得到连接
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:orcl", "bsmestest",
					"bsmestest");
			List<String> cantCode = new ArrayList<String>();
			List<String> noin = new ArrayList<String>();
			List<String> noout = new ArrayList<String>();
			List<String> nooutmuch = new ArrayList<String>();
			List<String> noline = new ArrayList<String>();
			List<String> nohas = new ArrayList<String>();
			pre = con.prepareStatement("select * from NEWTABLE1");
			rs = pre.executeQuery();
			while (rs.next()) {
				boolean flag = false;
				// System.out.println("用户名:" + rs.getString(0));
				String productCode = rs.getString(1);
				ProductCrafts productCrafts = new ProductCrafts();
				productCrafts.setProductCode(productCode);
				List<ProductCrafts> productCraftsArray = productCraftsService
						.findByObj(productCrafts);
				if (null != productCraftsArray && productCraftsArray.size() > 0) {
					System.out.println("产品包含工艺路线:" + productCraftsArray.size()
							+ "条;");
					productCrafts = productCraftsArray.get(0);
					List<ProductProcess> productProcessArray = productProcessService
							.getByProductCraftsId(productCrafts.getId());
					if (null != productProcessArray
							&& productProcessArray.size() > 0) {
						System.out.println("----工艺:"
								+ productCrafts.getCraftsCode() + "包含--工序:"
								+ productProcessArray.size() + "条;");
						for (ProductProcess productProcess : productProcessArray) {
							ProcessInOut processInOut = new ProcessInOut();
							processInOut.setProductProcessId(productProcess
									.getId());
							processInOut.setInOrOut(InOrOut.IN);
							List<ProcessInOut> processInArray = processInOutService
									.findByObj(processInOut);
							processInOut.setInOrOut(InOrOut.OUT);
							List<ProcessInOut> processOutArray = processInOutService
									.findByObj(processInOut);
							List<EquipList> eqipListArray = equipListService
									.getByProcessId(productProcess.getId());
							// List<ProcessQc> processQcArray =
							// processQcService.getByProcessId(productProcess.getId());
							// ,质量参数:"+processQcArray.size()+"条
							System.out.println("--------工序:"
									+ productProcess.getSeq() + "包含--投入:"
									+ processInArray.size() + "条,产出:"
									+ processOutArray.size() + "条,生产线:"
									+ eqipListArray.size() + "条;");

							if (processInArray == null
									|| processInArray.size() == 0) {
								noin.add(productProcess.getId());
								flag = true;
							}
							if (processOutArray == null
									|| processOutArray.size() == 0) {
								noout.add(productProcess.getId());
							} else if (processOutArray.size() > 1) {
								nooutmuch.add(productProcess.getId());
								flag = true;
							}
							if (eqipListArray == null
									|| eqipListArray.size() == 0) {
								noline.add(productProcess.getId());
								flag = true;
							}

						}
					}

				} else {
					nohas.add(productCode);
				}

				if (flag) {
					cantCode.add(productCode);
				}

			}
			System.out.println("process end...");
			System.out.println("以下产品没有工艺");
			for (String s : nohas) {
				System.out.println(s);
			}
			System.out.println("没有投入");
			for (String s : noin) {
				System.out.println(s);
			}
			System.out.println("没有产出");
			for (String s : noout) {
				System.out.println(s);
			}
			System.out.println("产出只能一个");
			for (String s : nooutmuch) {
				System.out.println(s);
			}
			System.out.println("没有生产线");
			for (String s : noline) {
				System.out.println(s);
			}
			System.out.println("不能使用的产品");
			for (String s : cantCode) {
				System.out.println(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (rs != null)
					rs.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				System.out.println("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
