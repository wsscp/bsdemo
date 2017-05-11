/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.pla.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;

import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.WorkTask;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-3-28 下午2:40:25
 * @since
 * @version
 */
public class MatcherEquipCalendarHandler {
	private EquipCalendarService equipCalendarService;
	private Calendar now;
	
	public MatcherEquipCalendarHandler(EquipCalendarService equipCalendarService,Calendar now){
		this.equipCalendarService = equipCalendarService;
		this.now = now;
	}
	
	public List<WorkTask> getEquipFreeWorkTask(Calendar dateTime,EquipInfo equipInfo,List<WorkTask> equipWorkTaskList)throws MESException{

		Calendar startTmp = Calendar.getInstance();
		startTmp.setTime(dateTime.getTime());
		
		Iterator<EquipCalendar> equipCalendarIterator = equipInfo.getEquipCalendar().iterator();
		
		Calendar lastDateTime = Calendar.getInstance();
		lastDateTime.setTime(DateUtils.convert(DateUtils.convert(dateTime.getTime(), DateUtils.DATE_TIMESTAMP_SHORT_FORMAT),DateUtils.DATE_TIMESTAMP_SHORT_FORMAT));
		String timeStr = DateUtils.convert(startTmp.getTime(), DateUtils.TIME_SHORT_FORMAT);
		boolean flg = true;
		do{
			while(equipCalendarIterator.hasNext()){
				EquipCalendar ec = equipCalendarIterator.next();
				String startStr = DateUtils.convert(startTmp.getTime(), DateUtils.DATE_SHORT_FORMAT);
				
				if(DateUtils.convert(ec.getDateOfWork(), DateUtils.DATE_SHORT_FORMAT).after(DateUtils.convert(startStr, DateUtils.DATE_SHORT_FORMAT))){
					return null;
				}
				lastDateTime.setTime(DateUtils.convert(ec.getDateOfWork()+timeStr, DateUtils.DATE_TIMESTAMP_SHORT_FORMAT));
				
				if(StringUtils.equals(startStr, ec.getDateOfWork())){
					return getEquipCalShiftFreeTime(equipInfo,lastDateTime.getTime(),equipWorkTaskList, ec);
//					try {
//						return  matcherEquipCalendarsShift(processId,length,lastDateTime,equipInfo,equipWorkTaskList,ec,needSetUpOrShutDown);
//					} catch (ParseException e) {
//						throw new MESException(e.getCause());
//					}
				}
			}
			//如果没有查到班次信息 并且设备日历最后一条记录的时间在开始时间之前,继续取三个月的设备日历
			if(flg && lastDateTime.before(startTmp)){
				Calendar begin = Calendar.getInstance();
				begin.setTime(lastDateTime.getTime());
				Date beginDate=begin.getTime();

				begin.add(Calendar.MONTH, 3);
				
				Date end = begin.getTime();
				
				List<EquipCalendar> addEquipCalendarList = equipCalendarService.getByEquipInfo(equipInfo, beginDate, end);
				equipCalendarIterator = addEquipCalendarList.iterator();
				equipInfo.getEquipCalendar().addAll(addEquipCalendarList);
			}else{
				flg = false;
			}
		}while(flg);

		return null;
	}
	
	/**
	 * 
	 * <p>匹配设备日历班次</p> 
	 * @author QiuYangjun
	 * @date 2014-3-12 下午5:58:14
	 * @param processId 工序id
	 * @param length 
	 * 			计划生产长度
	 * @param start
	 * 			开始日期
	 * @param needSetUpOrShutDown 
	 * @return
	 * @throws ParseException
	 * @see
	 */
//	private WorkTask matcherEquipCalendarsShift(String processId,BigDecimal length,Calendar start,EquipInfo equipInfo,List<WorkTask> equipWorkTaskList,EquipCalendar equipCalendar, boolean needSetUpOrShutDown) throws ParseException{
////		Calendar startTmp = Calendar.getInstance();
////		startTmp.setTime(start.getTime());
////		Calendar endTmp =Calendar.getInstance();
////		endTmp.setTime(start.getTime());
//		List<WorkTask> freeWorkTaskList = getEquipCalShiftFreeTime(equipInfo,start.getTime(),equipWorkTaskList, equipCalendar);
//        
//		Collections.sort(freeWorkTaskList, new Comparator<WorkTask>() {
//			@Override
//			public int compare(WorkTask o1, WorkTask o2) {
//				long timeLong1 = o1.getWorkEndTime().getTime()-o1.getWorkStartTime().getTime();
//				long timeLong2 = o2.getWorkEndTime().getTime()-o2.getWorkStartTime().getTime();
//				return new BigDecimal(timeLong1-timeLong2).abs().intValue();
//			}
//		});	
//        
//        Collections.sort(freeWorkTaskList, new Comparator<WorkTask>() {
//			@Override
//			public int compare(WorkTask o1, WorkTask o2) {
//				long timeLong = o1.getWorkStartTime().getTime()-o2.getWorkStartTime().getTime();
//				return new BigDecimal(timeLong).negate().intValue();
//			}
//		});	
//        
//		if(!freeWorkTaskList.isEmpty()){
//			//TODO 设备生产能力单位 米/秒
//            EquipList equipList = resourceCache.getEquipList(processId + BusinessConstants.CAPACITY_KEY_SEPARATOR + equipInfo.getCode());
//            BigDecimal equipCapacity = new BigDecimal(equipList.getEquipCapacity());
//			//完成任务需要用时 (秒) = 任务长度/设备生产能力 + 前置时间+后置时间
//			int usedTime = length.divide(equipCapacity,BigDecimal.ROUND_UP).intValue();
//			usedTime = usedTime + equipList.getSetUpTime();
//			if(needSetUpOrShutDown){
//				usedTime = usedTime + equipList.getShutDownTime();
//			}
//			Iterator<WorkTask> freeWorkTaskIterator = freeWorkTaskList.iterator();
//			while(freeWorkTaskIterator.hasNext()){
//				WorkTask freeWorkTask = freeWorkTaskIterator.next();
//				if(freeWorkTask.getWorkStartTime().before(start.getTime())){
//					continue;
//				}
//				
//				int freeTime = new BigDecimal(freeWorkTask.getWorkEndTime().getTime()-freeWorkTask.getWorkStartTime().getTime()).divide(new BigDecimal(1000),BigDecimal.ROUND_DOWN).intValue();
//				if(usedTime<=freeTime){
//					Calendar endTime = Calendar.getInstance();
//					endTime.setTime(freeWorkTask.getWorkStartTime());
//					endTime.add(Calendar.SECOND, usedTime);
//					freeWorkTask.setWorkEndTime(endTime.getTime());
////					equipInfo.getWorkTasks().add(freeWorkTask);
//					freeWorkTaskIterator.remove();
//					return freeWorkTask;
//				}
//				
//			}
//		}
//		
//		return null;
//	}
	
	/**
	 * 
	 * <p>获取设备日历当天负载空闲情况</p> 
	 * @author Qiuyangjun
	 * @date 2014-3-13 上午10:28:22
	 * @param equipInfo
	 * @param dateTime
	 * @return
	 * @see
	 */
	protected List<WorkTask> getEquipCalShiftFreeTime(EquipInfo equipInfo,Date dateTime,List<WorkTask> equipWorkTaskList,EquipCalendar equipCalendar){
		
		String startStr = DateUtils.convert(dateTime, DateUtils.DATE_SHORT_FORMAT);
//		Calendar lastStartDate = Calendar.getInstance();
//		lastStartDate.setTime(dateTime);
		List<WorkTask> resultTmp = new ArrayList<WorkTask>();
		
		//根据任务开始时间查找设备日历获取开始时间的设备班次信息
		List<EquipCalShift>  equipCalShiftList = equipCalendar.getEquipCalShift();
		
		if(equipCalShiftList == null || equipCalShiftList.isEmpty()){
			//如果设备当天没有排版次,直接返回
			return null;
		}else{
			//如果设备当天有排版次,获取当天班次内的设备空闲负载
			Collections.sort(equipCalShiftList, new CalendarShiftComparator(startStr));
			ListIterator<EquipCalShift> equipCalShiftIterator = equipCalShiftList.listIterator();
			
			List<WorkTask> equipWorkTasksTmp = new ArrayList<WorkTask>();
			equipWorkTasksTmp.addAll(equipInfo.getWorkTasks());
			equipWorkTasksTmp.addAll(equipWorkTaskList);

			if(equipWorkTasksTmp == null || equipWorkTasksTmp.isEmpty()){
				//如果当前设备没有负载 直接返回设备班次做为可用负载
			
				while(equipCalShiftIterator.hasNext()){
					EquipCalShift shift = equipCalShiftIterator.next();
					
					Date shiftStartTime = DateUtils.convert(startStr + shift.getShiftStartTime(),DateUtils.DATE_TIME_SHORT_FORMAT);
					Date shiftEndTime = DateUtils.convert(startStr + shift.getShiftEndTime(),DateUtils.DATE_TIME_SHORT_FORMAT);

					WorkTask freeWorkTask = new WorkTask();
					freeWorkTask.setWorkStartTime(shiftStartTime);
					freeWorkTask.setWorkEndTime(shiftEndTime);
					resultTmp.add(freeWorkTask);
				}
				
			}else{
				
				//根据卷未完成长度降序排列
				Collections.sort(equipWorkTasksTmp, new Comparator<WorkTask>() {
					@Override
					public int compare(WorkTask o1, WorkTask o2) {
						long timeLong = o1.getWorkStartTime().getTime()-o2.getWorkStartTime().getTime();
						return timeLong > 0 ?1:-1;
					}
				});
				
				
				
				shiftLoop:while(equipCalShiftIterator.hasNext()){
					EquipCalShift shift = equipCalShiftIterator.next();
					Date shiftStartTime = DateUtils.convert(startStr + shift.getShiftStartTime(),DateUtils.DATE_TIME_SHORT_FORMAT);
					Date shiftEndTime = DateUtils.convert(startStr + shift.getShiftEndTime(),DateUtils.DATE_TIME_SHORT_FORMAT);
				
					
					//如果当前时间是当前班次内或者班次结束时间在当前时间之前,跳过此班次,获取下一个班次
					if((shiftStartTime.before(now.getTime())&&shiftEndTime.after(now.getTime())) ||shiftEndTime.before(now.getTime())){
						continue shiftLoop;
					}

					boolean flg = true;
					ListIterator<WorkTask> workTasksIterator = equipWorkTasksTmp.listIterator();
					workTasksLoop:while(workTasksIterator.hasNext()){
						WorkTask wt = workTasksIterator.next();

						String wtStart = DateUtils.convert(wt.getWorkStartTime(), DateUtils.DATE_SHORT_FORMAT);
						if(StringUtils.equals(startStr, wtStart)){

							if(wt.getWorkStartTime().after(shiftStartTime) && wt.getWorkEndTime().before(shiftEndTime)){
								//负载开始时间在班次开始时间之后并且负载结束时间在班次结束时间之前
								//负载介于班次之间 空余容量开始时间=班次开始时时间,空余容量结束时间=负载开始时间
								WorkTask freeWorkTask = new WorkTask();
								freeWorkTask.setWorkStartTime(shiftStartTime);
								freeWorkTask.setWorkEndTime(wt.getWorkStartTime());
								resultTmp.add(freeWorkTask);
								shiftStartTime = wt.getWorkEndTime();
								flg = false;
								continue workTasksLoop;
							}else if(wt.getWorkStartTime().after(shiftStartTime) && wt.getWorkStartTime().before(shiftEndTime) && (wt.getWorkEndTime().after(shiftEndTime)||wt.getWorkEndTime().equals(shiftEndTime))){
								//负载的起始时间在班次内,结束时间在班次结束时间之后或者等于班次结束时间
								WorkTask freeWorkTask = new WorkTask();
								freeWorkTask.setWorkStartTime(shiftStartTime);
								freeWorkTask.setWorkEndTime(wt.getWorkStartTime());
								resultTmp.add(freeWorkTask);
								
								shiftStartTime = wt.getWorkEndTime();
								if(equipCalShiftIterator.hasNext()){
									shift = equipCalShiftIterator.next();
									if(shiftEndTime.equals(DateUtils.convert(startStr + shift.getShiftStartTime(),DateUtils.DATE_TIME_SHORT_FORMAT))){
										shiftEndTime = DateUtils.convert(startStr + shift.getShiftEndTime(),DateUtils.DATE_TIME_SHORT_FORMAT);
									}else{
										//返回上一个班次
										shift = equipCalShiftIterator.previous();
										continue shiftLoop;
									}
								}
								flg = false;
								continue workTasksLoop;
								
							}else if((wt.getWorkStartTime().before(shiftStartTime) || wt.getWorkStartTime().equals(shiftStartTime) )&& wt.getWorkEndTime().before(shiftEndTime)){
								//负载的起始时间在班之前或者等于班次开始时间,结束时间在班次结束前
								shiftStartTime = wt.getWorkEndTime();
								continue workTasksLoop;
							}else if((wt.getWorkStartTime().before(shiftStartTime) || wt.getWorkStartTime().equals(shiftStartTime) )&& 
									(wt.getWorkEndTime().after(shiftEndTime)||wt.getWorkEndTime().equals(shiftEndTime))){
								//负载的起始时间在班之前或者等于班次开始时间,并且,结束时间在班次结束之后或者等于班次结束时间
								wt = workTasksIterator.previous();
								continue shiftLoop;
							}
							
						}
					}
					if(flg){
						WorkTask freeWorkTask = new WorkTask();
						freeWorkTask.setWorkStartTime(shiftStartTime);
						freeWorkTask.setWorkEndTime(shiftEndTime);
						resultTmp.add(freeWorkTask);
					}
				}
			}
			
		}
		
		List<WorkTask> result = new ArrayList<WorkTask>();
		if(resultTmp.size()>1){
			Collections.sort(resultTmp, new Comparator<WorkTask>() {
				@Override
				public int compare(WorkTask o1, WorkTask o2) {
					long timeLong = o1.getWorkStartTime().getTime()-o2.getWorkStartTime().getTime();
					return timeLong > 0 ?1:-1;
				}
			});
			Iterator<WorkTask> resultIt = resultTmp.iterator();
			if(resultIt.hasNext()){
				WorkTask freeWorkTask = resultIt.next();
				Date lastStartTime = freeWorkTask.getWorkStartTime();
				Date lastEndTime = freeWorkTask.getWorkEndTime();
				while(resultIt.hasNext()){
					freeWorkTask = resultIt.next();
					Date startTime = freeWorkTask.getWorkStartTime();
					Date endTime = freeWorkTask.getWorkEndTime();
					//如果前一班次的结束时间等于后一班次的开始时间,合并两个班次可用时间
					if(lastEndTime.equals(startTime)){
						lastEndTime = endTime;
					}else{
						WorkTask wt = new WorkTask();
						wt.setWorkStartTime(lastStartTime);
						wt.setWorkEndTime(lastEndTime);
						result.add(wt);
						
						lastStartTime = startTime;
						lastEndTime = endTime;
					}
				}
				WorkTask wt = new WorkTask();
				wt.setWorkStartTime(lastStartTime);
				wt.setWorkEndTime(lastEndTime);
				result.add(wt);
			}
		}else{
			result.addAll(resultTmp); 
		}
		return result;
	}
	
	//获取加班时间
	private int getOverTime(){
		// TODO 加班时间计算
		return 0;
	}
	class CalendarShiftComparator implements Comparator<EquipCalShift>{
		private String date;
		public CalendarShiftComparator(String date){
			this.date  = date;
		}
		/** 
		 *  
		 * @author QiuYangjun
		 * @date 2014-4-16 上午10:36:38
		 * @param o1
		 * @param o2
		 * @return 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(EquipCalShift shift1, EquipCalShift shift2) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(DateUtils.convert(date+shift1.getShiftStartTime(),DateUtils.DATE_TIME_SHORT_FORMAT));
			Calendar c2 = Calendar.getInstance();
			c2.setTime(DateUtils.convert(date+shift2.getShiftStartTime(),DateUtils.DATE_TIME_SHORT_FORMAT));
			return c1.getTimeInMillis()-c2.getTimeInMillis()>0?1:-1;
		}
		
	}
}
