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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.WorkTask;
import cc.oit.bsmes.pla.dto.ProcessDto;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pro.model.EquipList;

/**
 * 正向递归工序
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-3-28 下午2:34:05
 * @since
 * @version
 */
public class ForwardRecursionCraftHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private MatcherEquipCalendarHandler matcherEquipCalendarHandler;

	public ForwardRecursionCraftHandler(ResourceCache resourceCache, EquipCalendarService equipCalendarService,
			Calendar now) {
		matcherEquipCalendarHandler = new MatcherEquipCalendarHandler(equipCalendarService, now);
	}

	public Date process(ResourceCache resourceCache, ProcessDto lastProcess, List<String> lockedOrderItemProDecIds,
			Date startDate, Map<String, List<WorkTask>> equipListWorkTaskCache) {
		List<ProcessDto> preProcessList = lastProcess.getPreProcesses();
		Date lastFinishDate = startDate;
		if (preProcessList != null && preProcessList.size() > 0) {
			for (ProcessDto process : preProcessList) {
				if (process.isSkippable()) {
					continue;
				}
				Date tmp = process(resourceCache, process, lockedOrderItemProDecIds, startDate, equipListWorkTaskCache);
				if (lastFinishDate.before(tmp)) {
					lastFinishDate = tmp;
				}
			}
		}
		Date tmp = forwardRecursionCraft(resourceCache, lastProcess, lockedOrderItemProDecIds, lastFinishDate,
				equipListWorkTaskCache);
		if (lastFinishDate.before(tmp)) {
			lastFinishDate = tmp;
		}

		return lastFinishDate;
	}

	/**
	 * 
	 * 正向递归工艺路线
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-6 上午11:37:40
	 * @param startDate
	 * @see
	 */
	public Date forwardRecursionCraft(ResourceCache resourceCache, ProcessDto lastProcess,
			List<String> lockedOrderItemProDecIds, Date startDate, Map<String, List<WorkTask>> equipListWorkTaskCache) {
		// 如果当前工序所有的卷已经被锁定,遍历orderTask获取最后完成时间
		boolean isProcessAllLocked = true;

		List<CustomerOrderItemProDec> orderItemProDeclist = lastProcess.getOrderItemProDecList();

		if (orderItemProDeclist == null || orderItemProDeclist.size() == 0) {
			logger.warn("OA 计算警告:工序id:{} 工序名称:{},没有卷需要生产", lastProcess.getId(), lastProcess.getProcessName());
			return startDate;
		}

		int totalItemCount = orderItemProDeclist.size();
		List<CustomerOrderItemProDec> calculatoredOrderItemProDeclistCache = new ArrayList<CustomerOrderItemProDec>();

		Calendar start = Calendar.getInstance();
		start.setTime(DateUtils.convert(DateUtils.convert(startDate, DateUtils.DATE_TIMESTAMP_SHORT_FORMAT),
				DateUtils.DATE_TIMESTAMP_SHORT_FORMAT));

		List<String> lockedOrderItemProDecIdsTmp = new ArrayList<String>();

		Date maxFinishTime = startDate;
		List<EquipInfo> equipInfoList = lastProcess.getAvailableEquips();

		// 可选设备
		List<EquipInfo> optionalEquipInfoList = new ArrayList<EquipInfo>();

		if (totalItemCount > 0) {
			String[] equipCodes = orderItemProDeclist.get(0).getOptionalEquipCodes();
			List<EquipInfo> eqList = resourceCache.getEquipInfoByOrgCode(orderItemProDeclist.get(0).getOrgCode());
			for (EquipInfo equip : eqList) {
				for (String equipCode : equipCodes) {
					if (StringUtils.equalsIgnoreCase(equip.getCode(), equipCode)) {
						optionalEquipInfoList.add(equip);
					}
				}
			}
		}

		// 判断是否已经使用可选设备
		boolean usedOptionalEquips = false;

		if ((equipInfoList == null || equipInfoList.isEmpty()) && totalItemCount > 0) {
			equipInfoList = optionalEquipInfoList;
			usedOptionalEquips = true;
		}

		if (equipInfoList != null && !equipInfoList.isEmpty()) {

			Iterator<EquipInfo> equipInfoIterator = equipInfoList.iterator();
			// 循环获取工序关联的设备,如果工序关联的任务分解全部分配完毕,停止循环
			equipLoop: while (equipInfoIterator.hasNext()) {
				EquipInfo equip = equipInfoIterator.next();
				// 根据日期和设备班次匹配相应的设备,同一天的同一个工序关联设备产能尽量全都排满,如果同一天所有设备产能都排满之后,任务排到后一天
				List<WorkTask> newWorkTaskList = equipListWorkTaskCache.get(equip.getCode());
				if (newWorkTaskList == null) {
					newWorkTaskList = new ArrayList<WorkTask>();
					equipListWorkTaskCache.put(equip.getCode(), newWorkTaskList);
				}

				// 根据时间获取空余负载
				List<WorkTask> freeWorkTaskList = matcherEquipCalendarHandler.getEquipFreeWorkTask(start, equip, newWorkTaskList);

				// 如果设备当天没被占用判断是否能完成一卷任务
				List<WorkTask> equipWorkTasksTmp = new ArrayList<WorkTask>();
				equipWorkTasksTmp.addAll(equip.getWorkTasks());
				equipWorkTasksTmp.addAll(newWorkTaskList);
				boolean isEquipOccupation = false;
				// 设备负载为空
				if (equipWorkTasksTmp == null || equipWorkTasksTmp.isEmpty()) {

				} else {
					ListIterator<WorkTask> workTasksIterator = equipWorkTasksTmp.listIterator();
					workTasksLoop: while (workTasksIterator.hasNext()) {
						WorkTask wt = workTasksIterator.next();
						String wtStart = DateUtils.convert(wt.getWorkStartTime(), DateUtils.DATE_SHORT_FORMAT);
						if (StringUtils.equalsIgnoreCase(wtStart,
								DateUtils.convert(start.getTime(), DateUtils.DATE_SHORT_FORMAT))) {
							isEquipOccupation = true;
							break workTasksLoop;
						}
					}
				}

				// 如果给定日期有空余负载
				if (freeWorkTaskList != null) {
					ListIterator<WorkTask> freeTimeIterator = freeWorkTaskList.listIterator();
					EquipList equipList = resourceCache.getEquipList(lastProcess.getId()
							+ BusinessConstants.CAPACITY_KEY_SEPARATOR + equip.getCode());
					// TODO 设备生产能力单位:米/秒
					BigDecimal equipCapacity = new BigDecimal(equipList.getEquipCapacity());
					// TODO 前置时间 单位: 秒
					BigDecimal setUpTime = new BigDecimal(equipList.getSetUpTime());
					// TODO 后置时间 单位: 秒
					BigDecimal shutDownTime = new BigDecimal(equipList.getShutDownTime());
					freeTimeLoop: while (freeTimeIterator.hasNext()) {
						WorkTask freeWorkTask = freeTimeIterator.next();

						if (!freeWorkTask.getWorkEndTime().after(startDate)) {
							continue freeTimeLoop;
						}
						if (freeWorkTask.getWorkStartTime().before(startDate)
								&& freeWorkTask.getWorkEndTime().after(startDate)) {
							freeWorkTask.setWorkStartTime(startDate);
						}

						// 空闲时间可以完成最大任务长度 = (空闲时间-前置时间-后置时间)*设备能力
						BigDecimal freeTime = new BigDecimal(freeWorkTask.getWorkEndTime().getTime()
								- freeWorkTask.getWorkStartTime().getTime()).divide(new BigDecimal(1000),
								BigDecimal.ROUND_DOWN);
						freeTime = freeTime.subtract(setUpTime).subtract(shutDownTime);
						if (freeTime.intValue() > 0) {
							// 最大可以完成任务长度
							BigDecimal totalLength = freeTime.multiply(equipCapacity);

							List<CustomerOrderItemProDec> orderItemProDeclistForLoop = new ArrayList<CustomerOrderItemProDec>();
							orderItemProDeclistForLoop.addAll(orderItemProDeclist);
							// 移除已经完成计算的卷
							orderItemProDeclistForLoop.removeAll(calculatoredOrderItemProDeclistCache);

							// 根据卷未完成长度降序排列
							Collections.sort(orderItemProDeclistForLoop, new Comparator<CustomerOrderItemProDec>() {
								@Override
								public int compare(CustomerOrderItemProDec proDec1, CustomerOrderItemProDec proDec2) {
									return new BigDecimal(proDec1.getUnFinishedLength() - proDec2.getUnFinishedLength())
											.intValue();
								}
							});

							ListIterator<CustomerOrderItemProDec> orderItemProDecIterator = orderItemProDeclistForLoop
									.listIterator();

							List<CustomerOrderItemProDec> orderItemProDeclistTmp = new ArrayList<CustomerOrderItemProDec>();
							// 计划开工总长度
							BigDecimal length = BigDecimal.ZERO;
							boolean isFirst = true;
							itemLoop: while (orderItemProDecIterator.hasNext()) {
								CustomerOrderItemProDec itemProDec = orderItemProDecIterator.next();
								if (lockedOrderItemProDecIds.contains(itemProDec.getId())
										|| lockedOrderItemProDecIdsTmp.contains(itemProDec.getId())) {
									calculatoredOrderItemProDeclistCache.add(itemProDec);
									continue itemLoop;
								}
								BigDecimal unFinishedLength = new BigDecimal(itemProDec.getUnFinishedLength());

								if (unFinishedLength.intValue() == 0) {
									lockedOrderItemProDecIdsTmp.add(itemProDec.getId());
									continue itemLoop;
								}

								length = length.add(unFinishedLength);
								if (totalLength.compareTo(length) < 0) {
									if (!isEquipOccupation) {
										logger.warn("========设备{},在{} 全天班次无法完成一卷任务 ,卷信息: ID:{},未完成长度:{} ============",
												equip.getCode(),
												DateUtils.convert(start.getTime(), DateUtils.DATE_SHORT_FORMAT),
												itemProDec.getId(), itemProDec.getUnFinishedLength());
									}
									if (isFirst) {
										continue freeTimeLoop;
									}
									// 如果最大可以完成任务长度
									// 小于计划开工总长度减去最后一卷长度之后继续循环,查找之后是否有适合的长度合并生产
									length = length.subtract(unFinishedLength);
									continue itemLoop;
								} else if (totalLength.compareTo(length) == 0) {
									// 如果最大可以完成任务长度 等于计划开工总长度 停止循环
									orderItemProDeclistTmp.add(itemProDec);
									if (orderItemProDecIterator.hasPrevious()) {
										isFirst = false;
									}
									isEquipOccupation = true;
									orderItemProDecIterator.remove();
									break itemLoop;
								} else {
									// 如果最大可以完成任务长度 大于计划开工总长度 继续循环
									orderItemProDeclistTmp.add(itemProDec);
									if (orderItemProDecIterator.hasPrevious()) {
										isFirst = false;
									}
									isEquipOccupation = true;
									orderItemProDecIterator.remove();
									continue itemLoop;
								}

							}

							isFirst = true;
							Calendar lastEndTime = Calendar.getInstance();
							lastEndTime.setTime(startDate);
							if (!orderItemProDeclistTmp.isEmpty()) {
								ListIterator<CustomerOrderItemProDec> it = orderItemProDeclistTmp.listIterator();
								while (it.hasNext()) {
									int index = it.nextIndex();
									CustomerOrderItemProDec dec = it.next();
									BigDecimal needTime = new BigDecimal(dec.getUnFinishedLength()).divide(
											equipCapacity, BigDecimal.ROUND_UP);

									WorkTask wt = new WorkTask();
									wt.setEquipCode(equip.getCode());
									wt.setOrderItemProDecId(dec.getId());
									if (index == 0) {
										wt.setWorkStartTime(freeWorkTask.getWorkStartTime());
										lastEndTime.setTime(freeWorkTask.getWorkStartTime());
										lastEndTime.add(Calendar.SECOND, setUpTime.intValue());
									} else {
										wt.setWorkStartTime(lastEndTime.getTime());
									}

									lastEndTime.add(Calendar.SECOND, needTime.intValue());

									// 如果是最后一卷加上shutDown时间
									if (!it.hasNext()) {
										lastEndTime.add(Calendar.SECOND, shutDownTime.intValue());
									}

									// 由于计算中有误差,如果最后一卷完成时间超过可用空挡的完成时间,修正最后一卷的完成时间为可用空挡的完成时间
									if (lastEndTime.getTime().after(freeWorkTask.getWorkEndTime())) {
										lastEndTime.setTime(freeWorkTask.getWorkEndTime());
									}
									wt.setWorkEndTime(lastEndTime.getTime());

									if (maxFinishTime.before(lastEndTime.getTime())) {
										maxFinishTime = lastEndTime.getTime();
									}
									newWorkTaskList.add(wt);
									lockedOrderItemProDecIdsTmp.add(dec.getId());

									// 还有卷没有被锁定
									isProcessAllLocked = false;
								}
								calculatoredOrderItemProDeclistCache.addAll(orderItemProDeclistTmp);
							}

							// 修正设备班次可用时间
							if (orderItemProDecIterator.hasNext() || orderItemProDecIterator.hasPrevious()) {
								if (totalItemCount != orderItemProDeclistForLoop.size()) {
									freeWorkTaskList = matcherEquipCalendarHandler.getEquipFreeWorkTask(start, equip, newWorkTaskList);
									freeTimeIterator = freeWorkTaskList.listIterator();
									totalItemCount = orderItemProDeclistForLoop.size();
								}
								continue freeTimeLoop;
							} else {
								break equipLoop;
							}
						}
					}
				}

				// 判断是否还有没有计算完成的卷
				List<CustomerOrderItemProDec> orderItemProDeclistCheck = new ArrayList<CustomerOrderItemProDec>();
				orderItemProDeclistCheck.addAll(orderItemProDeclist);
				// 移除已经完成计算的卷
				orderItemProDeclistCheck.removeAll(calculatoredOrderItemProDeclistCache);
				if (!orderItemProDeclistCheck.isEmpty()) {
					// 如果还有没有完成的卷
					// 如果所有设备当天负载都用完了 还有卷无法完成,排到下一天
					if (!equipInfoIterator.hasNext()) {
						if (usedOptionalEquips) {
							start.add(Calendar.DATE, 1);
							equipInfoList = lastProcess.getAvailableEquips();

							if (equipInfoList == null || equipInfoList.isEmpty()) {
								equipInfoList = optionalEquipInfoList;
								usedOptionalEquips = true;
							} else {
								usedOptionalEquips = false;
							}
							equipInfoIterator = equipInfoList.iterator();
						} else {
							equipInfoList = optionalEquipInfoList;
							equipInfoIterator = equipInfoList.iterator();
							usedOptionalEquips = true;
							if (!equipInfoIterator.hasNext()) {
								start.add(Calendar.DATE, 1);
								equipInfoList = lastProcess.getAvailableEquips();
								equipInfoIterator = equipInfoList.iterator();
								usedOptionalEquips = false;
							}
						}
					}
					continue equipLoop;
				} else {
					// 如果所有卷都完成了
					break equipLoop;
				}
			}
		} else {
			logger.error("OA 计算异常:工序id:{} 工序名称:{},没有配置可用生产线", lastProcess.getId(), lastProcess.getProcessName());
			// TODO 设置异常key
			throw new MESException("");
		}

		lockedOrderItemProDecIds.addAll(lockedOrderItemProDecIdsTmp);

		// 如果当前工序所有的卷已经被锁定,遍历orderTask获取最后完成时间
		if (isProcessAllLocked) {
			Iterator<CustomerOrderItemProDec> it = orderItemProDeclist.iterator();
			while (it.hasNext()) {
				CustomerOrderItemProDec dec = it.next();
				OrderTask ot = dec.getOrderTask();
				if (ot != null) {
					if (ot.getPlanFinishDate() != null && ot.getPlanFinishDate().after(maxFinishTime)) {
						maxFinishTime = ot.getPlanFinishDate();
					}
				}

			}
		}
		return maxFinishTime;
	}
}
