package cc.oit.bsmes.fac.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.fac.dao.WorkTaskDAO;
import cc.oit.bsmes.fac.model.WorkTask;
import cc.oit.bsmes.fac.service.WorkTaskService;
import cc.oit.bsmes.pla.service.OrderTaskService;

@Service
public class WorkTaskServiceImpl extends BaseServiceImpl<WorkTask> implements WorkTaskService {

	public static final int LOCKED_DAYS = 1; // TODO 接口
	@Resource
	private WorkTaskDAO workTaskDAO;
	@Resource
	private OrderTaskService orderTaskService;

	@Override
	public List<WorkTask> getByProcessId(String processId) {
		return workTaskDAO.getByProcessId(processId);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByOrgCode(String orgCode) {
		workTaskDAO.deleteByOrgCode(orgCode);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByOrgCodeForOA(String orgCode) {
		workTaskDAO.deleteByOrgCodeForOA(orgCode);
		// workTaskDAO.deleteByOrgCodeForOANotExistsOrderTask(orgCode);
	}

	@Override
	public WorkTask getByEquipCodeAndOrderItemProDecId(String equipCode, String orderItemProDecId) {
		WorkTask findParams = new WorkTask();
		findParams.setOrderItemProDecId(orderItemProDecId);
		findParams.setEquipCode(equipCode);
		return workTaskDAO.getOne(findParams);
	}

	@Override
	@Transactional(readOnly = false)
	public void insert(WorkTask wt) throws DataCommitException {
		if (StringUtils.isBlank(wt.getId())) {
			wt.setId(UUID.randomUUID().toString());
		}
		if (workTaskDAO.insert(wt) != 1) {
			throw new DataCommitException();
		}
	}

	@Override
	public List<WorkTask> getByProcessIdAndDate(String processId) {
		return workTaskDAO.getByProcessIdAndDate(processId);
	}

	@Override
	public List<WorkTask> getByCode(String equipCode) {
		return workTaskDAO.getByCode(equipCode);
	}

	@Override
	public List<WorkTask> getWorkTasks(String fromDate, String toDate) throws ParseException {
		Map<String, Object> param = new HashMap<String, Object>();
		User user = SessionUtils.getUser();
		param.put("orgCode", user.getOrgCode());

		Date dFromDate = null;
		Date dtoDate = null;
		SimpleDateFormat fd = new SimpleDateFormat(DateUtils.DATE_FORMAT);
		if (StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate)) {
			dFromDate = fd.parse(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT));
			dtoDate = DateUtils.addDayToDate(dFromDate, 8);

		} else {
			dFromDate = fd.parse(fromDate);
			dtoDate = DateUtils.addDayToDate(fd.parse(toDate), 1);

		}
		param.put("dFromDate", dFromDate);
		param.put("dtoDate", dtoDate);
		return workTaskDAO.getWorkTasks(param);
	}

	/**
	 * 根据设备编码获取设备加工任务负载，条件为 没有加工完成
	 * 
	 * @author DingXintao
	 * @date 2015-08-28
	 * @param equipCode 设备编码
	 * */
	@Override
	public List<WorkTask> getByEquipCode(String equipCode) {
		return workTaskDAO.getByEquipCode(equipCode);
	}
}
