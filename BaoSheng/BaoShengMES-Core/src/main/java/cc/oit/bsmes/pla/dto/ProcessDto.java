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
package cc.oit.bsmes.pla.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pro.model.ProductProcess;

/**
 * ProcessDto
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-1-2 下午3:10:48
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ProcessDto extends ProductProcess{
	private static final long serialVersionUID = 3384144300152340132L;
	private ProcessDto nextProcess;
	private List<ProcessDto> preProcesses = new ArrayList<ProcessDto>();
	private List<CustomerOrderItemProDec> orderItemProDecList;
	private List<EquipInfo> availableEquips;
	
	public List<CustomerOrderItemProDec> getOrderItemProDecList(){
		if(orderItemProDecList==null){
			orderItemProDecList = new ArrayList<CustomerOrderItemProDec>();
		}
		return orderItemProDecList;
	}
	
	public List<EquipInfo> getAvailableEquips(){
		if(availableEquips==null){
			availableEquips = new ArrayList<EquipInfo>();
		}
		return availableEquips;
	}
	
	/**
	 * 递归增加前置步骤集合
	 * @author QiuYangjun
	 * @date 2014-1-2 下午5:50:21
	 * @param processDto
	 * @return
	 * @see
	 */
	public boolean addPreProcess(ProcessDto processDto){
		//判断传入的对象的下一步工序ID是否是当前对象的id
		if(StringUtils.equalsIgnoreCase(processDto.getNextProcessId(), this.getId())){
			//如果是设置前置工序的下一步工序为当前对象
			processDto.setNextProcess(this);
			//增加当前工序的前置工序
			this.getPreProcesses().add(processDto);
			return true;
		}else{
			//如果不是递归前置工序
			for(ProcessDto preProcess:this.getPreProcesses()){
				if(StringUtils.equalsIgnoreCase(processDto.getNextProcessId(), preProcess.getId())){
					preProcess.addPreProcess(processDto);
				}else {
					for(ProcessDto preProcess2: preProcess.getPreProcesses())
					preProcess2.addPreProcess(processDto);
				}
			}
		}
		return false;
	}
	
	/**
	 * 增加后续工序节点
	 * @author QiuYangjun
	 * @date 2014-1-2 下午5:56:03
	 * @param processDto
	 * @return
	 * @see
	 */
	public boolean addNextProcess(ProcessDto processDto){
		//判断传入参数的id是否是当前对象的下一道工序id
		if(StringUtils.equalsIgnoreCase(this.getNextProcessId(), processDto.getId())){
			//如果是将当前工序增加传入参数的前置工序集合中
			processDto.getPreProcesses().add(this);
			//设置当前对象的下一道工序为传入参数
			this.setNextProcess(processDto);
			return true;
		}else{
			//如果不是递归下一道工序继续判断
			if(this.getNextProcess()!=null){
				this.getNextProcess().addNextProcess(processDto);
			}
		}
		return false;
	}
}
