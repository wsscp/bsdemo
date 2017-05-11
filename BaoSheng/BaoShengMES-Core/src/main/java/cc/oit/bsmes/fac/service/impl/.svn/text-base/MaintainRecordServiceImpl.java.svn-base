package cc.oit.bsmes.fac.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.MaintainStatus;
import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.common.constants.MaintainTriggerType;
import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import cc.oit.bsmes.common.mybatis.complexQuery.NoValueQueryParam;
import cc.oit.bsmes.common.mybatis.complexQuery.WithValueQueryParam;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.fac.dao.MaintainRecordDAO;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.MaintainItem;
import cc.oit.bsmes.fac.model.MaintainRecord;
import cc.oit.bsmes.fac.model.MaintainRecordItem;
import cc.oit.bsmes.fac.model.MaintainTemplate;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.MaintainItemService;
import cc.oit.bsmes.fac.service.MaintainRecordItemService;
import cc.oit.bsmes.fac.service.MaintainRecordService;
import cc.oit.bsmes.fac.service.MaintainTemplateService;
import cc.oit.bsmes.pro.model.ProcessQcValue;
import cc.oit.bsmes.pro.service.ProcessQcValueService;

/**
 * @author chanedi
 */
@Service
public class MaintainRecordServiceImpl extends BaseServiceImpl<MaintainRecord> implements MaintainRecordService {

	@Resource
	private MaintainItemService maintainItemService;
	@Resource
	private MaintainRecordItemService maintainRecordItemService;
	@Resource
	private MaintainTemplateService maintainTemplateService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private MaintainRecordDAO maintainRecordDAO;
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private ProcessQcValueService processQcValueService;

	@Override
	public MaintainRecord insert(MaintainRecord maintainRecord, MaintainTemplateType maintainTemplateType) {
		// 找到模版
		String code = maintainRecord.getEquipCode();
		String orgCode = SessionUtils.getUser().getOrgCode();

		// 确定设备
		EquipInfo equipInfo = equipInfoService.getByCode(code, orgCode);
		if (equipInfo.getModel() == null) {
			equipInfo = equipInfoService.getMainEquipByEquipLine(equipInfo.getCode());
			if (equipInfo == null || equipInfo.getModel() == null) {
				throw new MESException("fac.noModel");
			}

			code = equipInfo.getCode();
			maintainRecord.setEquipCode(code);
		}

		// 检查维修单是否已创建
		List<CustomQueryParam> queryParams = new ArrayList<CustomQueryParam>();
		queryParams.add(new NoValueQueryParam("finishTime", "IS NULL"));
		queryParams.add(new WithValueQueryParam("equipCode", "=", code));
		List<MaintainRecord> existsList = query(queryParams);
		if (existsList.size() > 0) {
			if (maintainTemplateType == MaintainTemplateType.DAILY && equipInfo.getType() == EquipType.MAIN_EQUIPMENT) {
				return existsList.get(0);// 点检继续未完成维修
			} else {
				throw new MESException("fac.maintainExists");
			}
		}

		// 查找模板
		MaintainTemplate tmplParams = new MaintainTemplate();
		tmplParams.setType(maintainTemplateType);
		tmplParams.setModel(equipInfo.getModel());
		List<MaintainTemplate> list = maintainTemplateService.getByObj(tmplParams);
		if (list.size() == 0) {
			throw new MESException("fac.noTmpl");
		}
		MaintainTemplate maintainTemplate = list.get(0);
		maintainRecord.setTmplId(maintainTemplate.getId());
		maintainRecord.setType(maintainTemplate.getType());

		insert(maintainRecord);

		// if (equipInfo.getType() == EquipType.MAIN_EQUIPMENT) {
		// equipInfoService.changeEquipStatus(equipInfoService.getEquipLineByEquip(code).getCode(),
		// EquipStatus.IN_MAINTAIN, new Date());
		// } else {
		// equipInfoService.changeEquipStatus(code, EquipStatus.IN_MAINTAIN, new
		// Date());
		// }

		MaintainItem itemParams = new MaintainItem();
		itemParams.setTempId(maintainTemplate.getId());
		List<MaintainItem> tmplItems = maintainItemService.getByObj(itemParams);
		if (tmplItems.size() == 0) {
			throw new MESException("fac.noTmpl");
		}
		// 检查项目数量
		for (MaintainItem tmplItem : tmplItems) {
			MaintainRecordItem item = new MaintainRecordItem();
			item.setRecordId(maintainRecord.getId());
			item.setDescribe(tmplItem.getDescribe());
			maintainRecordItemService.insert(item);
		}

		return maintainRecord;
	}

	@Override
	public void deleteById(String id) throws DataCommitException {
		MaintainRecord record = getById(id);
		String equipCode = record.getEquipCode();
		maintainRecordItemService.deleteByRecordId(id);
		super.deleteById(id);

		EquipInfo equipInfo = equipInfoService.getByCode(equipCode, SessionUtils.getUser().getOrgCode());
		// if (equipInfo.getType() == EquipType.MAIN_EQUIPMENT) {
		// equipInfoService.reverseEquipStatus(equipInfoService.getEquipLineByEquip(equipCode).getCode());
		// } else {
		// equipInfoService.reverseEquipStatus(equipCode);
		// }
	}

	@Override
	public void complete(String id, List<MaintainRecordItem> maintainRecordItemList) {
		maintainRecordItemService.update(maintainRecordItemList);
		MaintainRecord record = getById(id);
		// if (record.getFinishTime() == null) {
		// if (isTouch) {
		// record.setFinishTime(new Date());
		// } else {
		// throw new MESException("fac.noMaintainFinishTime");
		// }
		// }
		record.setFinishTime(new Date());
		record.setStatus(MaintainStatus.FINISHED);
		update(record);

		String equipCode = record.getEquipCode();

		EquipInfo equipInfo = equipInfoService.getByCode(equipCode, SessionUtils.getUser().getOrgCode());
		Date finishTime = record.getFinishTime();
		// if (equipInfo.getType() == EquipType.MAIN_EQUIPMENT) {
		// equipInfoService.changeEquipStatus(equipInfoService.getEquipLineByEquip(equipCode).getCode(),
		// EquipStatus.IDLE, finishTime);
		// } else {
		// equipInfoService.changeEquipStatus(equipCode, EquipStatus.IDLE,
		// finishTime);
		// }

		String tmplId = record.getTmplId();
		MaintainTemplate maintainTemplate = maintainTemplateService.getById(tmplId);

		if (maintainTemplate != null) {
			EventInformation findParams = new EventInformation();
			findParams.setBatchNo(maintainTemplate.getId());
			EquipInfo lineObject = StaticDataCache.getLineEquipInfo(equipCode);
			if (lineObject != null) {
				findParams.setEquipCode(lineObject.getCode());
			} else {
				findParams.setEquipCode(equipCode);
			}

			findParams.setEventStatus(EventStatus.UNCOMPLETED);
			List<EventInformation> alist = eventInformationService.getByObj(findParams);
			if (alist != null) {
				for (int i = 0; i < alist.size(); i++) {
					EventInformation object = alist.get(i);
					object.setEventStatus(EventStatus.COMPLETED);
					object.setCompleteTime(new Date());
					object.setEventResult("设别检查完毕，事件自动完成");
				}
				eventInformationService.update(alist);
			}

		}

		if (maintainTemplate.getTriggerType() != MaintainTriggerType.NATURE
				|| maintainTemplate.getType() == MaintainTemplateType.DAILY) {
			return;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(finishTime);
		calendar.add(Calendar.MONTH, maintainTemplate.getTriggerCycle());
		Date nextMaintainDate = calendar.getTime();
		switch (maintainTemplate.getType()) {
		case MONTHLY:
			equipInfo.setNextMaintainDate(nextMaintainDate);
		case FIRST_CLASS:
			equipInfo.setNextMaintainDateFirst(nextMaintainDate);
		case SECOND_CLASS:
			equipInfo.setNextMaintainDateSecond(nextMaintainDate);
		case OVERHAUL:
			equipInfo.setNextMaintainDateOverhaul(nextMaintainDate);
		}
		equipInfoService.update(equipInfo);
	}

	@Override
	public MaintainRecord getLatest(String equipCode, MaintainTemplateType maintainTemplateType) {
		return maintainRecordDAO.getLatest(equipCode, maintainTemplateType);
	}

	@Override
	public List getDailyCheck(MaintainRecord param, Integer start, Integer limit) {
		if (start != null && limit != null) {
			SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		}
		return maintainRecordDAO.getDailyCheck(param);
	}

	@Override
	public Integer countDailyChecks(MaintainRecord param) {
		return maintainRecordDAO.countDailyChecks(param);
	}

	@Override
	public MaintainRecord getLatestByEquipLine(String equipCode, MaintainTemplateType daily) {
		return maintainRecordDAO.getLatestByEquipLine(equipCode, daily);
	}
	
	/**
	 * 获取终端最新点检完成时间
	 * */
	public Date getDailyCheckFinishTime(String code){
		return maintainRecordDAO.getDailyCheckFinishTime(code);
	};

	/**
	 * 终端设备质检信息
	 * */
	@Override
	public List<MaintainRecord> dailyCheck(String equipCode, String workOrderNo) {
		MaintainRecord maintainRecord = this.getLatestByEquipLine(equipCode, MaintainTemplateType.DAILY);
		List<MaintainRecord> list = new ArrayList<MaintainRecord>();
		if (maintainRecord != null) {
			maintainRecord.setEquipCode("点检");
			list.add(maintainRecord);
		}
		List<ProcessQcValue> qcValueList = processQcValueService.getLastByWorkOrderNoAndType(workOrderNo);
		for (ProcessQcValue processQcValue : qcValueList) {
			maintainRecord = new MaintainRecord();
			if (StringUtils.equals(processQcValue.getType().name(), "OUT_CHECK")) {
				maintainRecord.setEquipCode("下车检");
			} else if (StringUtils.equals(processQcValue.getType().name(), "MIDDLE_CHECK")) {
				maintainRecord.setEquipCode("中检");
			} else {
				maintainRecord.setEquipCode("上车检");
			}
			maintainRecord.setFinishTime(processQcValue.getCreateTime());
			list.add(maintainRecord);
		}
		return list;
	}

}