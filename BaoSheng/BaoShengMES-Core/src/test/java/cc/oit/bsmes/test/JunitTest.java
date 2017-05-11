package cc.oit.bsmes.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.opc.client.OpcClient;
import cc.oit.bsmes.opc.client.OpcParmVO;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;

/**
 * 计算OA的junit测试
 */
public class JunitTest extends BaseTest {
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	
	@Resource
	private OpcClient opcClient;

	public String orgCode = "bstl01";

	/**
	 * 计算OA - 准备：分解
	 * */
	@Test
	@Rollback(false)
	public void process() {
		JobParams parms = new JobParams();
		parms.setOrgCode(orgCode);
		CustomerOrderItem customerOrderItem = new CustomerOrderItem();
		customerOrderItem.setCreateTime(DateUtils.convert(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT)));
		int count = customerOrderItemService.count(customerOrderItem);
		System.out.println(count);
	}
	
	@Test
	public void issueParam() {
		String str1="宝胜科技股份有限公司";
		String str2="2017-01-23 早班";
		String str3="（苏）XK06-001-00238 TL";
		String str4="护套06";
		String str5="(材料厂家)";
		String str6="WDZB-YJY-0.6/1kV 5*16";
		String str7="1";
		List<OpcParmVO> parmvoList=new ArrayList<OpcParmVO>();
		OpcParmVO o1=new OpcParmVO("PMJ6.PMJ6.W_CompanyName", str1);
		OpcParmVO o2=new OpcParmVO("PMJ6.PMJ6.W_Date", str2);
		OpcParmVO o3=new OpcParmVO("PMJ6.PMJ6.W_Licence", str3);
		OpcParmVO o4=new OpcParmVO("PMJ6.PMJ6.W_MaNumber", str4);
//		OpcParmVO o5=new OpcParmVO("PMJ6.PMJ6.W_Material", str5);
		OpcParmVO o6=new OpcParmVO("PMJ6.PMJ6.W_Model", str6);
		OpcParmVO o7=new OpcParmVO("PMJ6.PMJ6.W_Switch", str7);
//		OpcParmVO o2=new OpcParmVO("PMJ_test.String2", str2);
//		OpcParmVO o3=new OpcParmVO("PMJ_test.String3", str3);
		parmvoList.add(o1);
		parmvoList.add(o2);
		parmvoList.add(o3);
		parmvoList.add(o4);
		parmvoList.add(o6);
		parmvoList.add(o7);
		opcClient.writeOpcValue(parmvoList);
	}

}
