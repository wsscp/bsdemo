package cc.oit.bsmes.fac.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.fac.model.MaintainRecord;
import cc.oit.bsmes.fac.model.MaintainRecordItem;

/**
 * @author chanedi
 */
public interface MaintainRecordService extends BaseService<MaintainRecord> {

    public MaintainRecord insert(MaintainRecord maintainRecord, MaintainTemplateType maintainTemplateType);

    public void complete(String id, List<MaintainRecordItem> maintainRecordItemList );

    public MaintainRecord getLatest(String equipCode, MaintainTemplateType maintainTemplateType);

	public List getDailyCheck(MaintainRecord param, Integer start,Integer limit);

	public Integer countDailyChecks(MaintainRecord param);

	public MaintainRecord getLatestByEquipLine(String equipCode,MaintainTemplateType daily);
	
	/**
	 * 获取终端最新点检完成时间
	 * */
	public Date getDailyCheckFinishTime(String code);
	

	/**
	 * 终端设备质检信息
	 * */
	public List<MaintainRecord> dailyCheck(String equipCode, String workOrderNo);
}