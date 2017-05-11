package cc.oit.bsmes.interfacePLM.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.CanShukuTask;
import cc.oit.bsmes.job.tasks.PLMProductTask;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import cc.oit.bsmes.pro.service.ProductProcessService;

public class PLMProductTest extends BaseTest{

	@Resource private PLMProductTask  pLMProductTask;
	@Resource private ProductProcessService productProcessService;
	@Resource private ProcessInformationService processInformationService; 
	@Resource private CanShukuTask canShukuTask;
	
	@Test
	@Rollback(false)
	public void testPLMProductTask(){
		JobParams params =  new JobParams();
		params.setOrgCode("1");
		pLMProductTask.process(params);
	}

   @Test
	@Rollback(false)
	public void tesCanShukuTask(){
		JobParams params =  new JobParams();
		params.setOrgCode("1");
		canShukuTask.process(params);
	}

	@Test
	@Rollback(false)
	public void updateProcessInfo(){
		List<ProductProcess> list = productProcessService.getAll();
		List<ProcessInformation> proInfoList= new ArrayList<ProcessInformation>();


		Map<String,String> map = new HashMap<String, String>();
		for(int i=0;i<list.size();i++){
			ProductProcess pro=list.get(i);
			if(map.get(pro.getProcessCode())==null){
				ProcessInformation info=new ProcessInformation();
				info.setCode(pro.getProcessCode());
				info.setName(pro.getProcessName());
				info.setOrgCode("1");
				proInfoList.add(info);
				map.put(pro.getProcessCode(), pro.getProcessCode());
			}
		}
		processInformationService.insert(proInfoList);
	}

}
