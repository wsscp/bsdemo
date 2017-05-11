package cc.oit.bsmes.fac.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.model.EventStore;
import cc.oit.bsmes.common.model.ResourcesStore;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.fac.model.WorkTask;
import cc.oit.bsmes.fac.service.WorkTaskService;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.service.ProcessInformationService;

@Controller
@RequestMapping("/fac/workTask")
public class WorkTaskController {

	@Resource
	private WorkTaskService workTaskService;
	@Resource
	private ProcessInformationService processInformationService;

	@RequestMapping(produces = "text/html")
	public String index(HttpServletRequest request, Model model) {
		String type = request.getParameter("type");
		if (StringUtils.equals(type, "0")) {
			model.addAttribute("moduleName", "pla");
			model.addAttribute("submoduleName", "orderOAResource");
			return "pla.orderOAResource";
		} else {
			model.addAttribute("moduleName", "pla");
			model.addAttribute("submoduleName", "resourceGantt");
			return "pla.resourceGantt";
		}
	}

	/**
	 * 左侧工序列表
	 * */
	@ResponseBody
	@RequestMapping(value = "/loadData", method = RequestMethod.GET)
	public List<ResourcesStore> listByProductCode(@RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) throws ParseException {
		List<ProcessInformation> processSectionArray = processInformationService.getSection();
		List<ResourcesStore> list = new ArrayList<ResourcesStore>();
		List<WorkTask> workTasks = workTaskService.getWorkTasks(fromDate, toDate);
		for (ProcessInformation processSection : processSectionArray) {
			List<ProcessInformation> processArray = processInformationService.getBySection(processSection.getSection());
			List<ResourcesStore> childrenResourcesArray = this.getChildrenResourcesArray(processArray, workTasks);

			ResourcesStore resourcesStore = new ResourcesStore();
			resourcesStore.setName(processSection.getSection());
			resourcesStore.setExpanded(true);
			resourcesStore.setIconCls("sch-terminal");
			if (childrenResourcesArray != null) {
				resourcesStore.setChildren(childrenResourcesArray);
			}
			list.add(resourcesStore);
		}
		return list;
	}

	/**
	 * 获取树结构子树: 包括树下的设备子树
	 * */
	private List<ResourcesStore> getChildrenResourcesArray(List<ProcessInformation> processArray,
			List<WorkTask> workTasks) {
		List<ResourcesStore> resourcesArray = new ArrayList<ResourcesStore>();
		for (ProcessInformation process : processArray) {
			ResourcesStore resources = new ResourcesStore();
			resources.setName(process.getName());
			resources.setExpanded(true);
			resources.setIconCls("sch-gates-bundle");

			List<ResourcesStore> equipChildResourcesArray = new ArrayList<ResourcesStore>();
			Map<String, String> flagMap = new HashMap<String, String>();
			for (WorkTask workTask : workTasks) {
				if (StringUtils.equals(process.getCode(), workTask.getProcessCode())
						&& flagMap.get(workTask.getEquipCode()) == null) {
					ResourcesStore equipResources = new ResourcesStore();
					equipResources.setId(workTask.getId());
					equipResources.setName(workTask.getEquipName());
					equipResources.setLeaf(true);
					equipResources.setIconCls("sch-gate");
					equipChildResourcesArray.add(equipResources);
					flagMap.put(workTask.getEquipCode(), workTask.getEquipCode());
				}
			}
			resources.setChildren(equipChildResourcesArray);
			resourcesArray.add(resources);
		}
		return resourcesArray;
	}

	/**
	 * <p>
	 * 合并workTask的开始时间结束时间
	 * </p>
	 * 
	 * @param list
	 * @return
	 * @author leiwei
	 * @date 2014-4-2 下午1:46:40
	 * @see
	 */
	List<WorkTask> mergerWorkTasks(List<WorkTask> list) {
		List<WorkTask> workTasks = new ArrayList<WorkTask>();
		for (int i = 0; i < list.size(); i++) {
			WorkTask workTask = list.get(i);
			WorkTask work = new WorkTask();
			work.setId(workTask.getId());
			work.setEquipCode(workTask.getEquipCode());
			work.setWorkStartTime(workTask.getWorkStartTime());
			work.setEquipName(workTask.getEquipName());
			work.setProcessCode(workTask.getProcessCode());
			work.setHalfProductCode(workTask.getHalfProductCode());
			Date endTime = null;
			while (i + 1 < list.size()
					&& (list.get(i + 1).getWorkStartTime().getTime() - list.get(i).getWorkEndTime().getTime()) / 60000 <= BusinessConstants.MAX_INTERVAL) {
				if (StringUtils.equals(list.get(i).getEquipCode(), list.get(i + 1).getEquipCode())
						&& StringUtils.equals(list.get(i).getProcessCode(), list.get(i + 1).getProcessCode())) {
					endTime = list.get(i + 1).getWorkEndTime();
					i++;
				} else {
					break;
				}
			}
			endTime = endTime == null ? workTask.getWorkEndTime() : endTime;
			work.setWorkEndTime(endTime);
			workTasks.add(work);
		}
		return workTasks;

	}

	/**
	 * 右侧任务列表
	 * */
	@ResponseBody
	@RequestMapping(value = "/getTasks", method = RequestMethod.GET)
	public List<EventStore> getWorkTasks(@RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) throws ParseException {
		List<ProcessInformation> processSectionArray = processInformationService.getSection();
		List<EventStore> eventStoreArray = new ArrayList<EventStore>();
		List<WorkTask> workTasks = mergerWorkTasks(workTaskService.getWorkTasks(fromDate, toDate));
		for (ProcessInformation processSection : processSectionArray) {
			List<ProcessInformation> processArray = processInformationService.getBySection(processSection.getSection());
			for (ProcessInformation process : processArray) {
				Map<String, String> flagMap = new HashMap<String, String>();
				for (WorkTask workTask : workTasks) {
					if (StringUtils.equals(process.getCode(), workTask.getProcessCode())) {
						EventStore eventItem = new EventStore(); // 设备
						if (flagMap.get(workTask.getEquipCode()) == null) {
							eventItem.setResourceId(workTask.getId());
							flagMap.put(workTask.getEquipCode(), workTask.getId());
						} else {
							eventItem.setResourceId(flagMap.get(workTask.getEquipCode()));
						}
						eventItem.setStartDate(DateUtils.convert(workTask.getWorkStartTime()));
						eventItem.setEndDate(DateUtils.convert(workTask.getWorkEndTime()));
						eventItem.setName(workTask.getHalfProductCode());
						eventStoreArray.add(eventItem);
					}
				}
			}
		}
		return eventStoreArray;
	}

}
