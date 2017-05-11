package cc.oit.bsmes.pro.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProcessQcWip;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;

public interface ProcessQcWipService extends BaseService<ProcessQcWip> {

	
	/**
	 * @Title:       getByProcessId
	 * @Description: TODO(工序id获取质量参数)
	 * @param:       processId 工序id
	 * @return:      List<ProcessQcWip>   
	 * @throws
	 */
	public List<ProcessQcWip> getByProcessId(String processId);
	

	/**
	 * @Title:       getCheckTypeByProcessId
	 * @Description: TODO(工序id获取检测类型)
	 * @param:       processId 工序id
	 * @param:       equipInfo 设备终端信息
	 * @return:      MesClientEqipInfo   
	 * @throws
	 */
	public MesClientEqipInfo getCheckTypeByProcessId(String processId, MesClientEqipInfo equipInfo);
}
