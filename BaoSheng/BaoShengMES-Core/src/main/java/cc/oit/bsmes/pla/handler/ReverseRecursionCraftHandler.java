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

import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.WorkTask;
import cc.oit.bsmes.pla.dto.ProcessDto;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pro.model.EquipList;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-3-28 下午2:34:58
 * @since
 * @version
 */
public class ReverseRecursionCraftHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private Calendar now;
	private MatcherEquipCalendarHandler matcherEquipCalendarHandler;
	
	public ReverseRecursionCraftHandler(ResourceCache resourceCache,EquipCalendarService equipCalendarService,Calendar now){
		this.now = now;
		matcherEquipCalendarHandler = new MatcherEquipCalendarHandler(equipCalendarService, now);
	}

	/**
	 *
	 * @param resourceCache
	 * @param lastProcess 最后一道工序
	 * @param lockedOrderItemProDecIds 锁定的ProDecId集合
	 * @param endDate  订单交期
	 * @param equipListWorkTaskCache  空的缓存集合
	 * @return
	 */
	public Date process(ResourceCache resourceCache, ProcessDto lastProcess, List<String> lockedOrderItemProDecIds, Date endDate, Map<String, List<WorkTask>> equipListWorkTaskCache){
		if(now.getTime().after(endDate)){
			return null;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(DateUtils.convert(DateUtils.convert(endDate, DateUtils.DATE_SHORT_FORMAT),DateUtils.DATE_SHORT_FORMAT));
		Calendar preProcessEndTime = Calendar.getInstance();
		preProcessEndTime.setTime(endDate);
		Date maxFinishTime = endDate;
		
		if(!lastProcess.isSkippable()){
			List<CustomerOrderItemProDec> orderItemProDeclist = lastProcess.getOrderItemProDecList();
			
			if(orderItemProDeclist==null||orderItemProDeclist.size()==0){
				logger.warn("OA 计算警告:工序id:{} 工序名称:{},没有卷需要生产", lastProcess.getId(),lastProcess.getProcessName());
			}else{
				int totalItemCount = orderItemProDeclist.size();
				List<CustomerOrderItemProDec> calculatoredOrderItemProDeclistCache = new ArrayList<CustomerOrderItemProDec>();
				List<String> lockedOrderItemProDecIdsTmp = new ArrayList<String>();
				
				List<EquipInfo> equipInfoList = resourceCache.getDefaultEquips(lastProcess.getId(), 0);
				//可选设备
				List<EquipInfo> optionalEquipInfoList = new ArrayList<EquipInfo>();
				
				if(totalItemCount > 0){
					String[] equipCodes = orderItemProDeclist.get(0).getOptionalEquipCodes();
					List<EquipInfo> eqList = resourceCache.getEquipInfoByOrgCode(orderItemProDeclist.get(0).getOrgCode());
					for(EquipInfo equip:eqList){
						for(String equipCode:equipCodes){
							if(StringUtils.equalsIgnoreCase(equip.getCode(), equipCode)){
								optionalEquipInfoList.add(equip);
							}
						}
					}
				}
				
				//判断是否已经使用可选设备
				boolean usedOptionalEquips  = false;
				
				if ((equipInfoList == null || equipInfoList.isEmpty()) && totalItemCount > 0) {
					equipInfoList = optionalEquipInfoList;
					usedOptionalEquips = true;
				}
				
				if (equipInfoList!=null && !equipInfoList.isEmpty()) {
					Iterator<EquipInfo> equipInfoIterator = equipInfoList.iterator();
					equipLoop : while (equipInfoIterator.hasNext()) {
						EquipInfo equip = equipInfoIterator.next();
						List<WorkTask> newWorkTaskList = equipListWorkTaskCache.get(equip.getCode());
						if(newWorkTaskList == null){
							newWorkTaskList = new ArrayList<WorkTask>();
							equipListWorkTaskCache.put(equip.getCode(), newWorkTaskList);
						}
						
						List<WorkTask> freeWorkTaskList = matcherEquipCalendarHandler.getEquipFreeWorkTask(start, equip,newWorkTaskList);
						
						//如果设备当天没被占用判断是否能完成一卷任务
						List<WorkTask> equipWorkTasksTmp = new ArrayList<WorkTask>();
						equipWorkTasksTmp.addAll(equip.getWorkTasks());
						equipWorkTasksTmp.addAll(newWorkTaskList);
						boolean isEquipOccupation = false;
						//设备负载为空
						if(equipWorkTasksTmp == null || equipWorkTasksTmp.isEmpty()){
							
						}else{
							ListIterator<WorkTask> workTasksIterator = equipWorkTasksTmp.listIterator();
							workTasksLoop:while(workTasksIterator.hasNext()){
								WorkTask wt = workTasksIterator.next();
								String wtStart = DateUtils.convert(wt.getWorkStartTime(), DateUtils.DATE_SHORT_FORMAT);
								if(StringUtils.equalsIgnoreCase(wtStart,  DateUtils.convert(start.getTime(), DateUtils.DATE_SHORT_FORMAT))){
									isEquipOccupation = true;
									break workTasksLoop;
								}
							}
						}
						
				    	//如果给定日期有空余负载
						if(freeWorkTaskList!=null){
							EquipList equipList = resourceCache.getEquipList(lastProcess.getId() + BusinessConstants.CAPACITY_KEY_SEPARATOR + equip.getCode());
							//TODO 设备生产能力单位:米/秒
							BigDecimal equipCapacity = new BigDecimal(equipList.getEquipCapacity());
							//TODO 前置时间 单位: 秒
							BigDecimal setUpTime = new BigDecimal(equipList.getSetUpTime());
							//TODO 后置时间 单位: 秒
							BigDecimal shutDownTime = new BigDecimal(equipList.getShutDownTime());
							
							
							//设备剩余能力按照时间先后倒叙排序 
							Collections.sort(freeWorkTaskList, new Comparator<WorkTask>() {
								@Override
								public int compare(WorkTask o1, WorkTask o2) {
	                             return o2.getWorkStartTime().getTime()-o1.getWorkStartTime().getTime()>0?1:-1;
								}
							});
							ListIterator<WorkTask> freeTimeIterator = freeWorkTaskList.listIterator();
							freeTimeLoop:while(freeTimeIterator.hasNext()){
								WorkTask freeWorkTask = freeTimeIterator.next();
								if(freeWorkTask.getWorkStartTime().after(endDate)||freeWorkTask.getWorkStartTime().equals(endDate)){
									continue freeTimeLoop;
								}else{
									if(freeWorkTask.getWorkEndTime().after(endDate)){
										freeWorkTask.setWorkEndTime(endDate);
									}
								}
								
								//空闲时间可以完成最大任务长度  = (空闲时间-前置时间-后置时间)*设备能力
								BigDecimal freeTime = new BigDecimal(freeWorkTask.getWorkEndTime().getTime() - freeWorkTask.getWorkStartTime().getTime()).divide(new BigDecimal(1000),BigDecimal.ROUND_DOWN);
								freeTime = freeTime.subtract(setUpTime).subtract(shutDownTime);
								if(freeTime.intValue()>0){
									//最大可以完成任务长度
									BigDecimal totalLength = freeTime.multiply(equipCapacity);
																	
									List<CustomerOrderItemProDec> orderItemProDeclistForLoop = new ArrayList<CustomerOrderItemProDec>();
									orderItemProDeclistForLoop.addAll(orderItemProDeclist);
									//移除已经完成计算的卷
									orderItemProDeclistForLoop.removeAll(calculatoredOrderItemProDeclistCache);
									
									//根据卷未完成长度降序排列
									Collections.sort(orderItemProDeclistForLoop, new Comparator<CustomerOrderItemProDec>() {
										@Override
										public int compare(CustomerOrderItemProDec proDec1, CustomerOrderItemProDec proDec2) {
											return new BigDecimal(proDec1.getUnFinishedLength()-proDec2.getUnFinishedLength()).intValue();
										}
									});
									
									ListIterator<CustomerOrderItemProDec> orderItemProDecIterator =  orderItemProDeclistForLoop.listIterator();
									
									List<CustomerOrderItemProDec> orderItemProDeclistTmp = new ArrayList<CustomerOrderItemProDec>();
									//计划开工总长度
									BigDecimal length = BigDecimal.ZERO;
									boolean isFirst = true;
									itemLoop : while(orderItemProDecIterator.hasNext()){
										CustomerOrderItemProDec itemProDec = orderItemProDecIterator.next();
										if(lockedOrderItemProDecIds.contains(itemProDec.getId())||lockedOrderItemProDecIdsTmp.contains(itemProDec.getId())){
											calculatoredOrderItemProDeclistCache.add(itemProDec);
											continue itemLoop;
										}
										BigDecimal unFinishedLength = new BigDecimal(itemProDec.getUnFinishedLength());
										if(unFinishedLength.intValue()==0){
											lockedOrderItemProDecIdsTmp.add(itemProDec.getId());
											continue itemLoop;
										}
										length = length.add(unFinishedLength);
										if(totalLength.compareTo(length) < 0){
											if(!isEquipOccupation){
												logger.warn("========设备{},在{} 全天班次无法完成一卷任务 ,卷信息: ID:{},未完成长度:{} ============",equip.getCode(),DateUtils.convert(start.getTime(), DateUtils.DATE_SHORT_FORMAT),itemProDec.getId(),itemProDec.getUnFinishedLength());
											}
											if(isFirst){
												continue freeTimeLoop;
											}
											//如果最大可以完成任务长度 小于计划开工总长度减去最后一卷长度之后继续循环,查找之后是否有适合的长度合并生产
											length = length.subtract(unFinishedLength);
											continue itemLoop;
										}else if(totalLength.compareTo(length) == 0){
											//如果最大可以完成任务长度 等于计划开工总长度 停止循环
											orderItemProDeclistTmp.add(itemProDec);
											if(orderItemProDecIterator.hasPrevious()){
												isFirst = false;
											}
											orderItemProDecIterator.remove();
											isEquipOccupation = true;
											break itemLoop;
										}else{
											//如果最大可以完成任务长度 大于计划开工总长度 继续循环
											orderItemProDeclistTmp.add(itemProDec);
											if(orderItemProDecIterator.hasPrevious()){
												isFirst = false;
											}
											isEquipOccupation = true;
											orderItemProDecIterator.remove();
											continue itemLoop;
										}
										
									}
									Calendar lastStartTime = Calendar.getInstance();
									lastStartTime.setTime(freeWorkTask.getWorkEndTime());
									if(!orderItemProDeclistTmp.isEmpty()){
										ListIterator<CustomerOrderItemProDec> it = orderItemProDeclistTmp.listIterator();
										while(it.hasNext()){
											int index=it.nextIndex();
											CustomerOrderItemProDec dec = it.next();
											BigDecimal needTime = new BigDecimal(dec.getUnFinishedLength()).divide(equipCapacity,BigDecimal.ROUND_UP);
											
											WorkTask wt = new WorkTask();
											wt.setEquipCode(equip.getCode());
											wt.setOrderItemProDecId(dec.getId());
											if(index==0){
												wt.setWorkEndTime(freeWorkTask.getWorkEndTime());		
												lastStartTime.add(Calendar.SECOND, shutDownTime.negate().intValue());
											}else{
												wt.setWorkEndTime(lastStartTime.getTime());
											}
											lastStartTime.add(Calendar.SECOND, needTime.negate().intValue());
											wt.setWorkStartTime(lastStartTime.getTime());
											//如果是最后一卷加上setUp时间
											if(!it.hasNext()){
												lastStartTime.add(Calendar.SECOND, setUpTime.negate().intValue());
											}
											
											//由于计算中有误差,如果最后一卷开始时间超过可用空挡的开始时间,修正最后一卷的完成时间为可用空挡的开始时间
											if(lastStartTime.getTime().before(freeWorkTask.getWorkStartTime())){
												lastStartTime.setTime(freeWorkTask.getWorkStartTime());
											}
											wt.setWorkStartTime(lastStartTime.getTime());

											if(maxFinishTime.before(wt.getWorkEndTime())){
												maxFinishTime = wt.getWorkEndTime();
											}
											newWorkTaskList.add(wt);
											lockedOrderItemProDecIdsTmp.add(dec.getId());
											//设置前一道工序的最晚结束时间
											if(preProcessEndTime.getTime().after(wt.getWorkStartTime())){
												preProcessEndTime.setTime(wt.getWorkStartTime());
											}
										}
										calculatoredOrderItemProDeclistCache.addAll(orderItemProDeclistTmp);
									}
									
									//修正设备班次可用时间
									if(orderItemProDecIterator.hasNext()||orderItemProDecIterator.hasPrevious()){
										if(totalItemCount != orderItemProDeclistForLoop.size()){
											freeWorkTaskList = matcherEquipCalendarHandler.getEquipFreeWorkTask(start, equip,newWorkTaskList);
											//设备剩余能力按照时间先后倒叙排序 
											Collections.sort(freeWorkTaskList, new Comparator<WorkTask>() {
												@Override
												public int compare(WorkTask o1, WorkTask o2) {
					                             return o2.getWorkStartTime().getTime()-o1.getWorkStartTime().getTime()>0?1:-1;
												}
											});
											freeTimeIterator = freeWorkTaskList.listIterator();
											totalItemCount = orderItemProDeclistForLoop.size();
										}
										continue freeTimeLoop;
									}else{
										//结束计算
										break equipLoop;
									}
								}
							}
						}
						
						
						//判断是否还有没有计算完成的卷
						List<CustomerOrderItemProDec> orderItemProDeclistCheck = new ArrayList<CustomerOrderItemProDec>();
						orderItemProDeclistCheck.addAll(orderItemProDeclist);
						//移除已经完成计算的卷
						orderItemProDeclistCheck.removeAll(calculatoredOrderItemProDeclistCache);
						if(!orderItemProDeclistCheck.isEmpty()){
							//如果还有没有完成的卷
							//如果所有设备当天负载都用完了 还有卷无法完成,尝试提前生产
							if(!equipInfoIterator.hasNext()){
								//判断是否正在使用可选设备
								if(usedOptionalEquips){
									start.add(Calendar.DATE, -1);
									//如果开始时间在当前时间之前,无法倒排,重新正排
									if(start.before(now)){
										return null;
									}
									equipInfoList = lastProcess.getAvailableEquips();
									
									if (equipInfoList == null || equipInfoList.isEmpty()) {
										//使用可选设备
										equipInfoList = optionalEquipInfoList; 
										usedOptionalEquips = true;
									}else{
										usedOptionalEquips = false;
									}
									equipInfoIterator = equipInfoList.iterator();
								}else{
									equipInfoList = optionalEquipInfoList; 
									equipInfoIterator = equipInfoList.iterator();
									usedOptionalEquips = true;
									if(!equipInfoIterator.hasNext()){
										start.add(Calendar.DATE, -1);
										if(start.before(now)){
											return null;
										}
										equipInfoList = lastProcess.getAvailableEquips();
										equipInfoIterator = equipInfoList.iterator();
										usedOptionalEquips = false;
									}
								}
							}
							continue equipLoop;
						}else{
							//结束计算
							break equipLoop;
						}
						
					}
				}else{
					logger.error("OA 计算异常:工序id:{} 工序名称:{},没有配置可用生产线", lastProcess.getId(),lastProcess.getProcessName());
					//TODO 设置异常key
					throw new MESException("");
				}
				lockedOrderItemProDecIds.addAll(lockedOrderItemProDecIdsTmp);
			}
			
		}
		
		
		List<ProcessDto> preProcessList = lastProcess.getPreProcesses();
		if (preProcessList != null && preProcessList.size() > 0) {
			for (ProcessDto process : preProcessList) {
				if(process.isSkippable()){
					continue; 
				}
				Date lastFinishDate = process(resourceCache, process, lockedOrderItemProDecIds, preProcessEndTime.getTime(),equipListWorkTaskCache);
				if(lastFinishDate == null){
					return null;
				}else if (lastFinishDate.after(maxFinishTime)) {
					maxFinishTime = lastFinishDate;
				}
			}
		}
		
		return maxFinishTime;
	
	}
}
