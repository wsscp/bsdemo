package cc.oit.bsmes.interfaceWebService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class InstantiateService {

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-beans.xml");
	private static MesService erpService = (MesService)ctx.getBean("mesService");
	
	/**
	 * 生产完工产品信息推送
	 * @author 前克
	 * @date 2016-06-01
	 * @param jjdBeans
	 * @return
	 * @throws Exception
	 */
	public String saveJJD(String jjdBeans) throws Exception
	{
		return erpService.saveJJD(jjdBeans);
	}
	
	/**
	 * 采购申请推送
	 * @author 前克
	 * @date 216-06-01
	 * @param cgsqBeans
	 * @return
	 * @throws Exception
	 */
	public String saveCGSQ(String cgsqBeans) throws Exception
	{
		return erpService.saveCGSQ(cgsqBeans);
	}
}