package cc.oit.bsmes.pro.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.ProcessQCEqipDAO;
import cc.oit.bsmes.pro.model.ProcessQCEqip;
import cc.oit.bsmes.pro.service.ProcessQCEqipService;

@Service
public class ProcessQCEqipServiceImpl extends BaseServiceImpl<ProcessQCEqip> implements ProcessQCEqipService{

	@Resource
	private ProcessQCEqipDAO processQCEqipDAO;
	
	
	@Override
	public List<String> getEquipCodeByWorkOrderNo(String checkItemCode,
			String workOrderNo) {
		return processQCEqipDAO.getEquipCodeByWorkOrderNo(checkItemCode, workOrderNo);
	}
    @Override
    @Transactional(readOnly = false)
	public void insertBatch(List<ProcessQCEqip> list){
		processQCEqipDAO.insertBatch(list);
	}
}
