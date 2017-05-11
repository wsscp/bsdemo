package cc.oit.bsmes.pro.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.ProcessQcWipDAO;
import cc.oit.bsmes.pro.model.ProcessQcWip;
import cc.oit.bsmes.pro.service.ProcessQcWipService;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;

@Service
public class ProcessQcWipServiceImpl extends BaseServiceImpl<ProcessQcWip> implements ProcessQcWipService {

	@Resource
	private ProcessQcWipDAO processQcWipDAO;

	/**
	 * @Title:       getByProcessId
	 * @Description: TODO(工序id获取质量参数)
	 * @param:       processId 工序id
	 * @return:      List<ProcessQcWip>   
	 * @throws
	 */
	@Override
	public List<ProcessQcWip> getByProcessId(String processId) {
		ProcessQcWip findParams = new ProcessQcWip();
		findParams.setProcessId(processId);
		return processQcWipDAO.find(findParams);
	}
	
	/**
	 * @Title:       getCheckTypeByProcessId
	 * @Description: TODO(工序id获取检测类型)
	 * @param:       processId 工序id
	 * @param:       equipInfo 设备终端信息
	 * @return:      MesClientEqipInfo   
	 * @throws
	 */
	@Override
	public MesClientEqipInfo getCheckTypeByProcessId(String processId, MesClientEqipInfo equipInfo) {
		if(null == processId){
			return null;
		}
		List<ProcessQcWip> list = this.getByProcessId(processId);
		for (ProcessQcWip processQc : list) {
			if (!"1".equals(equipInfo.getNeedFirstCheck())) {
				equipInfo.setNeedFirstCheck(processQc.getNeedFirstCheck());
			}

			if (!"1".equals(equipInfo.getNeedMiddleCheck())) {
				equipInfo.setNeedMiddleCheck(processQc.getNeedMiddleCheck());
			}

			if (!"1".equals(equipInfo.getNeedInCheck())) {
				equipInfo.setNeedInCheck(processQc.getNeedInCheck());
			}

			if (!"1".equals(equipInfo.getNeedOutCheck())) {
				equipInfo.setNeedOutCheck(processQc.getNeedOutCheck());
			}
		}
		return equipInfo;
	}
}
