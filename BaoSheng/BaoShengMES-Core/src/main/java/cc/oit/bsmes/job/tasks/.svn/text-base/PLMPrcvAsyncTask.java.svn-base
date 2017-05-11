package cc.oit.bsmes.job.tasks;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;
import cc.oit.bsmes.interfacePLM.service.MpartInOutService;
import cc.oit.bsmes.interfacePLM.service.PrcvService;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.pro.service.ProductProcessService;

/**
 * ProductProcessEquipAsyncTask
 * <p style="display:none">
 * 数据同步Task：[产品工艺定义、产品工艺流程、流程投入产出、工序使用设备清单、产品工艺参数]
 * </p>
 * 
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
@Service
public class PLMPrcvAsyncTask extends BaseSimpleTask {

	@Resource
	private PrcvService prcvService;
	@Resource
	private LastExecuteTimeRecordService lastExecuteTimeRecordService;
	@Resource
	private MpartInOutService mpartInOutService;
	@Resource
	private ProductProcessService productProcessService;

	@Override
	public void process(JobParams parms) {

		LastExecuteTimeRecord letRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.PRCV);
		if (letRecord == null) {
			letRecord = new LastExecuteTimeRecord();
			letRecord.setType(InterfaceDataType.PRCV.name());
		}
		Date lastDate = letRecord == null ? null : letRecord.getLastExecuteTime();
		Map<String, Date> findParams = new HashMap<String, Date>();
		findParams.put("lastDate", lastDate);
		/**
		 * 同步同步[工艺、工序、投入产出、生产线、工艺指导]
		 */
		mpartInOutService.initTemplet();
		Date lastReturnDate= prcvService.asyncData(findParams); 
		letRecord.setLastExecuteTime(lastReturnDate);
		lastExecuteTimeRecordService.saveRecord(letRecord);
		productProcessService.setProEqipList();
	}
}
