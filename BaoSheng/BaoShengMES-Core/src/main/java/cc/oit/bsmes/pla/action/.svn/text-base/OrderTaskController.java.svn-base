package cc.oit.bsmes.pla.action;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.model.EventStore;
import cc.oit.bsmes.common.model.ResourcesStore;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.service.WorkTaskService;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pla/orderTask")
public class OrderTaskController {

	@Resource private OrderTaskService orderTaskService;
	@Resource private WorkTaskService workTaskService;
	@Resource private ProcessInformationService processInformationService;
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "pla");
        model.addAttribute("submoduleName", "orderTask");
        return "pla.orderTask"; 
    }
	
	@ResponseBody
	@RequestMapping(value="/order",method=RequestMethod.GET)
	public  List<ResourcesStore> listByProductCode(@RequestParam(required=false)  String fromDate,@RequestParam(required=false)  String toDate) throws Exception{
		return getResource(processInformationService.getSection(),fromDate,toDate);
	}
	
	@ResponseBody
	@RequestMapping(value="/event",method=RequestMethod.GET)
	public List<EventStore> event(@RequestParam(required=false)  String fromDate,@RequestParam(required=false)  String toDate) throws Exception{
		return getEvent(processInformationService.getSection(),fromDate,toDate);
	}
	
	private List<EventStore> getEvent(List<ProcessInformation> sections,String fromDate,String toDate) throws Exception{
		if(sections==null){
			return null;
		}
		Map<String, Mat> matMap = StaticDataCache.getMatMap();
		Map<String, Product> productMap = StaticDataCache.getProductMap();
		User u=SessionUtils.getUser(); 
		List<OrderTask> orderTasks=orderTaskService.getLimitByTime(u.getOrgCode(),fromDate,toDate);
		List<EventStore> list=new ArrayList<EventStore>();
		for(int i=0;i<sections.size();i++){
//			Date minSectionDate=null; //工段的最小时间
//			Date maxSectionDate=null; //工段的最大时间
			ProcessInformation pro=sections.get(i);
			List<ProcessInformation> processes=processInformationService.getBySection(pro.getSection());
			if(processes!=null){
				for(ProcessInformation proc:processes ){
//					Date minProcessDate=null; //工序的最小时间
//					Date maxProcessDate=null; //工序的最大时间
					for(OrderTask o:orderTasks){
						if(StringUtils.equals(proc.getCode(), o.getProcessCode())){
							EventStore equip=new EventStore();  //设备
							equip.setResourceId(proc.getCode()+o.getEquipCode());
							equip.setStartDate(DateUtils.convert(o.getPlanStartDate()));
							equip.setEndDate(DateUtils.convert(o.getPlanFinishDate()));
							equip.setName(o.getCustomerOrderNo());
							equip.setCustomerOrderNo(o.getCustomerOrderNo());
							equip.setProductCode(o.getProductCode());
							equip.setHalfProductCode(o.getHalfProductCode());
							equip.setTaskLength(o.getTaskLength());
							equip.setContractNo(o.getContractNo());
							equip.setWorkOrderNo(o.getWorkOrderNo());
							equip.setPercentDone(String.format("%.2f",o.getPercent() ));
							Mat mat=matMap.get(o.getHalfProductCode());
							if(mat!=null&&mat.getColor()!=null)
							{								  
							  equip.setHalfProductCodeColor(mat.getColor());
							}
							Product product = productMap.get(o.getProductCode());
							if(product!=null)
							{
								equip.setProductSpec(product.getProductSpec());
							}
							
							list.add(equip);
//							if(minProcessDate==null || minProcessDate.after(o.getPlanStartDate())){
//								minProcessDate=o.getPlanStartDate();
//							 }
//							 if(maxProcessDate==null || maxProcessDate.before(o.getPlanFinishDate())){
//								 maxProcessDate=o.getPlanFinishDate();
//							 }
						}
					}
//					if(StringUtils.isNotBlank(DateUtils.convert(minProcessDate))){
//						EventStore process=new EventStore();//工序
//						process.setName(proc.getCode()+"("+proc.getName()+")");
//						process.setStartDate(DateUtils.convert(minProcessDate));
//						process.setEndDate(DateUtils.convert(maxProcessDate));
//						process.setResourceId(proc.getId()+proc.getCode());
//						list.add(process);
//					}									
//					if(minProcessDate!=null){
//						if(minSectionDate==null ||minSectionDate.after(minProcessDate)){
//							minSectionDate=minProcessDate;
//						}
//					}					
//					if(maxProcessDate!=null){
//						if(maxSectionDate==null ||maxSectionDate.after(maxProcessDate)){
//							maxSectionDate=maxProcessDate;
//						}
//					}
					
				}
//				if(StringUtils.isNotBlank(DateUtils.convert(minSectionDate))){
//					EventStore section=new EventStore();
//					section.setName(pro.getSection());
//					section.setResourceId(pro.getId());
//					section.setStartDate(DateUtils.convert(minSectionDate));
//					section.setEndDate(DateUtils.convert(maxSectionDate));
//					list.add(section);
//				}
			}
			
		}
		
		return list;
	}
	
	//顶层 工段
	private List<ResourcesStore> getResource(List<ProcessInformation> processInformation,String fromDate,String toDate) throws Exception{
		if(processInformation==null){
			return null;
		}
		List<ResourcesStore> list=new ArrayList<ResourcesStore>();
		User u=SessionUtils.getUser();  
		List<OrderTask> orderTasks=orderTaskService.getLimitByTime(u.getOrgCode(),fromDate,toDate);
		for(ProcessInformation pro:processInformation){
			ResourcesStore rc=new ResourcesStore();
			rc.setName(pro.getSection());
			rc.setExpanded(true);
			rc.setIconCls("sch-terminal");
//			rc.setId(pro.getId());
			List<ProcessInformation> process=processInformationService.getBySection(pro.getSection());
			List<ResourcesStore> pros=getProcess(process,orderTasks);
			if(!CollectionUtils.isEmpty(pros))
			{
				rc.setChildren(pros);
				list.add(rc);
			}
			
		}
		return list;
	}
	//中层 工序
 	private List<ResourcesStore> getProcess(List<ProcessInformation> process,List<OrderTask> orderTasks){
 		

		List<ResourcesStore> processResult = new ArrayList<ResourcesStore>();

		for (ProcessInformation pro : process) {
			ResourcesStore rc = new ResourcesStore();
			List<ResourcesStore> equip = new ArrayList<ResourcesStore>();
			rc.setId(pro.getId());
			rc.setName(pro.getName());
			rc.setExpanded(true);
			rc.setIconCls("sch-gates-bundle");
			boolean addFlag=false;
		
			Map<String, String> flagMap = new HashMap<String, String>();

			for (OrderTask workTask : orderTasks) {
				if (StringUtils.equals(pro.getCode(), workTask.getProcessCode())
						&& flagMap.get(workTask.getEquipCode()) == null) {
					ResourcesStore ec = new ResourcesStore();
					ec.setId(pro.getCode() + workTask.getEquipCode());
					ec.setName(workTask.getEquipCode());
					ec.setLeaf(true);
					ec.setIconCls("sch-gate");
					rc.setLeaf(false);
					rc.setChildren(equip); 
					equip.add(ec);
					addFlag=true;					
					flagMap.put(workTask.getEquipCode(),workTask.getEquipCode());
				}

			}
			if(addFlag)
			{
				processResult.add(rc);
			}

		}
		return processResult;
	
		
//		List<ResourcesStore> list=new ArrayList<ResourcesStore>();
//		List<ResourcesStore> equip=null;
//		for(ProcessInformation pro:process){
////			ResourcesStore rc=null;
//			ResourcesStore rc=new ResourcesStore();
//			equip=new ArrayList<ResourcesStore>();
//			for(OrderTask o:orderTasks){
//				 if(StringUtils.equals(pro.getCode(), o.getProcessCode())){
//					 String result=null;
//					 for(ResourcesStore rec:equip){
//						 if(StringUtils.equals(rec.getName(), o.getEquipCode())){
//							 result=rec.getName();
//						 }
//					 }
//					 if(StringUtils.isEmpty(result)){
////						 rc=new ResourcesStore();
//						 ResourcesStore ec=new ResourcesStore();
//						 ec.setId(pro.getCode()+o.getEquipCode());					
//						 ec.setName(o.getEquipCode());
//						 ec.setLeaf(true);
//						 ec.setIconCls("sch-gate");
//						 equip.add(ec);
//					 }
//				 }
//			}
////			if(rc!=null){
////				rc.setId(pro.getId()+pro.getCode());
//				rc.setName(pro.getCode()+"("+pro.getName()+")");
//				rc.setExpanded(true);
//				rc.setChildren(equip);
//				list.add(rc);
////			}
//		}
//		return list;
 	}

}
