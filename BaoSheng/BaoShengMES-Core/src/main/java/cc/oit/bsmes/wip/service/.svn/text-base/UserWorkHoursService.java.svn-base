package cc.oit.bsmes.wip.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.UserWorkHours;

public interface UserWorkHoursService extends BaseService<UserWorkHours> {

	/**
	 * 查询一个月的员工工时记录
	 * 
	 * @param yearMonh 年月
	 * */
	public List<Map> getUsersHoursByMonth(String yearMonth,String userName, String typeName);
	
	/**
	 * 查询一个月的员工工时记录，导出成excel格式
	 * @param outputStream 输出流
	 * @param yearMonh 年月
	 * @throws IOException
	 * */
	public void usersHoursAMonthOutToExcel(OutputStream outputStream, String yearMonth) throws IOException;
	
	public List<Map<String,String>> getUserName();
}