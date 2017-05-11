package cc.oit.bsmes.job.tasks;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.model.EventType;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.EventTypeService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

/**
 * Created by joker on 2014/10/28 0028.
 */
@Service
public class WorkOrderExtensionAlarmTask extends BaseSimpleTask{

    @Resource
    private EquipInfoService equipInfoService;

    @Resource
    private WorkOrderService workOrderService;

    @Resource
    private EquipListService equipListService;

    @Resource
    private OrderTaskService orderTaskService;

    @Resource
    private EventInformationService eventInformationService;
    @Resource
    private EventTypeService eventTypeService;


	@Override
	public void process(JobParams parms) {
		EventType eventType = eventTypeService.getByCode(EventTypeContent.WT.name());
		if (eventType == null) {
			logger.warn("请添加'生产执行延期'事件类型!");
			return;
		}
		String eventTypeId = eventType.getId();
		String alarmHour = WebContextUtils.getSysParamValue(WebConstants.FAC_ALRM_TIMELATETOALARM, parms.getOrgCode());
		if (StringUtils.isBlank(alarmHour)) {
			alarmHour = "1";
		}
		List<EquipInfo> equipInfos = equipInfoService.getAllProductLine();
		for (EquipInfo equipInfo : equipInfos) {
			List<WorkOrder> list = workOrderService.getByEquipCode(equipInfo.getCode());
			if (list.size() == 0)
				continue;

			WorkOrder lastWorkOrder = list.get(0);

			// 计算已完成长度
			// if(lastWorkOrder.getPreEndTime().getTime())
			EquipList equipList = equipListService.getByProcessIdAndEquipCode(lastWorkOrder.getProcessId(), equipInfo.getCode());
			if (equipList == null)
				continue;
			// 计算按现在的生产速度完成时间
			Double finishLength = orderTaskService.getSumFinishTaskLength(lastWorkOrder.getWorkOrderNo());
			if (finishLength == null) {
				finishLength = 0.0;
			}

			Double cancelLength = lastWorkOrder.getCancelLength();
			if (cancelLength == null) {
				cancelLength = 0.0;
			}
			long lastWorkingTime = getProcessTime(equipList, lastWorkOrder.getOrderLength() - cancelLength - finishLength);
			long planFinishTime = new Date().getTime() + (long) (Double.valueOf(alarmHour) * 60 * 60 * 1000) + lastWorkingTime;

			// 表示没有在计划时间内完成
			if (lastWorkOrder.getPreEndTime().getTime() > planFinishTime) {
				int eventNum = eventInformationService.findForWorkOrderExtensionAlarmTask(lastWorkOrder.getWorkOrderNo());
				if (eventNum > 0)
					continue;

				// 设置eventInfo 数据
				EventInformation eventInfo = new EventInformation();
				eventInfo.setEquipCode(equipInfo.getCode());
				eventInfo.setEventTitle("生产单延期!");
				eventInfo.setEventStatus(EventStatus.UNCOMPLETED);
				eventInfo.setProcessTriggerTime(new Date());
				eventInfo.setCode(EventTypeContent.WT.name());
				eventInfo.setEventTypeId(eventTypeId);

				for (WorkOrder workOrder : list) {
					eventInfo.setId(null);
					eventInfo.setProcessId(lastWorkOrder.getProcessId());
					eventInfo.setEventContent("设备：" + equipInfo.getCode() + "，生产单单号为：" + workOrder.getWorkOrderNo() + "延期!");
					eventInfo.setBatchNo(workOrder.getWorkOrderNo());
					eventInformationService.insertInfo(eventInfo);
				}
			}
		}
	}

    /**
     * 计算花费的时间
     * @return
     * @author chanedi
     * @date 2014-2-28 下午1:15:29
     * @see
     */
    public static long getProcessTime(EquipList equipList, double taskLength) {
        double equipCapacity = equipList.getEquipCapacity();
        long time = (long) (taskLength / equipCapacity * 1000);
        //time = strategy.isIncludeSetUpTime() ? time + equipList.getSetUpTimeMilSec() : time;
        //time = strategy.isIncludeShutDownTime() ? time + equipList.getShutDownTimeMilSec() : time;
        return time;
    }

}
