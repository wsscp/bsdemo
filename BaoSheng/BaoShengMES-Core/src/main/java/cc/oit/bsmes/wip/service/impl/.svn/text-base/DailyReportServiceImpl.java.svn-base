package cc.oit.bsmes.wip.service.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.DailyReportDAO;
import cc.oit.bsmes.wip.model.DailyReport;
import cc.oit.bsmes.wip.service.DailyReportService;
@Service
public class DailyReportServiceImpl extends BaseServiceImpl<DailyReport>
		implements DailyReportService {
	
	@Resource
	private DailyReportDAO dailyReportDAO;

	@Override
	public List<DailyReport> getDailyReportByDate(Map<String, String> param, int start, int limit) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		DailyReport ld = null;
		List<DailyReport> returnList = new ArrayList<DailyReport>();
		
		List<DailyReport> list = getUserDailyReport(param,start,limit);
		if(param.containsKey("shiftId")){
			return list;
		}
		//给机台补齐早中晚三班
		List<DailyReport> buffList = new ArrayList<DailyReport>();
		for(int i=0;i<list.size();i++){
			DailyReport dr = list.get(i);
			/*if(dr.getOffTime() == null){
				dr.setRemark("未刷下班卡");
			}*/
			if(ld != null && dr.getEquipAlias().equals(ld.getEquipAlias())){
				buffList.add(dr);
				if(i != list.size()-1){
					continue;
				}
			}
			if(!buffList.isEmpty()){
				repairShift(buffList,ld,returnList);
			}
			buffList.add(dr);
			if(list.size() == 1){
				repairShift(buffList,dr,returnList);
				break;
			}
			if(i == list.size()-1 && !dr.getEquipAlias().equals(ld.getEquipAlias())){
				repairShift(buffList,dr,returnList);
			}
			ld = dr;
		}
		return returnList;
	}
	
	/**
	 * 返回某一天的工人工时记录
	 * @param param
	 * @param start
	 * @param limit
	 * @return
	 */
	private List<DailyReport> getUserDailyReport(Map<String, String> param,
			int start, int limit) {
		
		return dailyReportDAO.queryUsersHoursForDate(param);
	}

	@Override
	public List<DailyReport> getDailyReportByDate(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return dailyReportDAO.getDailyUsersByDate(param);
	}

	@Override
	public List<DailyReport> getDailyEquipCodeByDate(Map<String, String> param) {
		// TODO Auto-generated method stub
		return dailyReportDAO.getDailyEquipCodeByDate(param);
	}
	/**
	 * 为机台补齐早中晚三班，便于显示，美观大方
	 * @param buffList
	 * @param ld
	 * @param returnList
	 */
	public void repairShift(List<DailyReport> buffList,DailyReport ld,List<DailyReport> returnList){
		int j=0;
		Set<String> set = new HashSet<String>();
		for(DailyReport d : buffList){
			set.add(d.getShiftId());
		}
		if(set.size() == 1){
			DailyReport d1 = new DailyReport();
			DailyReport d2 = new DailyReport();
			if(set.contains("1")){
				d1.setEquipAlias(ld.getEquipAlias());
				d1.setShiftName("中班");
				d2.setEquipAlias(ld.getEquipAlias());
				d2.setShiftName("夜班");
				buffList.add(d1);
				buffList.add(d2);
				returnList.addAll(buffList);
			}else if(set.contains("2")){
				d1.setEquipAlias(ld.getEquipAlias());
				d1.setShiftName("早班");
				d2.setEquipAlias(ld.getEquipAlias());
				d2.setShiftName("夜班");
				returnList.add(d1);
				returnList.addAll(buffList);
				returnList.add(d2);
			}else{
				d1.setEquipAlias(ld.getEquipAlias());
				d1.setShiftName("早班");
				d2.setEquipAlias(ld.getEquipAlias());
				d2.setShiftName("中班");
				returnList.add(d1);
				returnList.add(d2);
				returnList.addAll(buffList);
			}
		}else if(set.size() == 3){
			returnList.addAll(buffList);
		}else{
			DailyReport d1 = new DailyReport();
			if(set.contains("1") && set.contains("2")){
				d1.setEquipAlias(ld.getEquipAlias());
				d1.setShiftName("夜班");
				buffList.add(d1);
				returnList.addAll(buffList);
			}else if(set.contains("3") && set.contains("2")){
				d1.setEquipAlias(ld.getEquipAlias());
				d1.setShiftName("早班");
				returnList.add(d1);
				returnList.addAll(buffList);
			}else{
				for(int x = 0;x<buffList.size();x++){
					DailyReport d2 = buffList.get(x);
					if(!d2.getShiftId().equals("1") && j == 0){
						d1.setEquipAlias(ld.getEquipAlias());
						d1.setShiftName("中班");
						returnList.add(d1);
						j++;
					}
					returnList.add(d2);
				}
			}
		}
		buffList.clear();
	}

	@Override
	public Integer countDailyReportByDate(Map<String, String> param) {
		return dailyReportDAO.countDailyReportByDate(param);
	}

	@Override
	public List<DailyReport> searchCreditCardList(Map<String, Object> param,
			int start, int limit) {
		// TODO Auto-generated method stub
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return dailyReportDAO.searchCreditCardList(param);
	}

	@Override
	public Integer countCreditCardList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return dailyReportDAO.countCreditCardList(param);
	}

}
