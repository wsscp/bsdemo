package cc.oit.bsmes.pla.service.impl;

import javax.annotation.Resource;

import cc.oit.bsmes.common.util.SessionUtils;
import lombok.extern.java.Log;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pla.dao.HighPriorityOrderItemProDecDAO;
import cc.oit.bsmes.pla.model.HighPriorityOrderItemProDec;
import cc.oit.bsmes.pla.service.HighPriorityOrderItemProDecService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log
public class HighPriorityOrderItemProDecServiceImpl extends BaseServiceImpl<HighPriorityOrderItemProDec> implements
		HighPriorityOrderItemProDecService {

	@Resource
	private HighPriorityOrderItemProDecDAO  highPriorityOrderItemProDecDAO;

	@Override
	public int getMaxSeqByEquipCode(String equipCode){
		return highPriorityOrderItemProDecDAO.getMaxSeqByEquipCode(equipCode);
	}

	/** 
	 * <p>将生产单从排序表移除</p> 
	 * @author Administrator
	 * @date 2014-5-19 下午6:08:55
	 * @param workOrderNo 
	 * @see cc.oit.bsmes.pla.service.HighPriorityOrderItemProDecService#deleteByWorkOrderNo(java.lang.String)
	 */
	@Override
	public void deleteByWorkOrderNo(String workOrderNo) {
		highPriorityOrderItemProDecDAO.deleteByWorkOrderNo(workOrderNo);
		
	}

	/** 
	 * <p>将生产单从排序表移除</p> 
	 * @author QiuYangjun
	 * @date 2014-5-20 下午3:31:15
	 * @param equipCode 
	 * @see cc.oit.bsmes.pla.service.HighPriorityOrderItemProDecService#deleteByEquipCode(java.lang.String)
	 */
	@Override
	public void deleteByEquipCode(String equipCode) {
		highPriorityOrderItemProDecDAO.deleteByEquipCode(equipCode);
	}
	
	/**
	 * 
	 * <p>根据生产单号和序号插入排序表</p> 
	 * @author QiuYangjun
	 * @date 2014-5-20 下午4:13:18
	 * @param workOrderNo
	 * @param seq
	 * @see
	 */
	@Override
	public void insertSeqByWorkOrderNo(String workOrderNo,String equipCode, int seq){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("workOrderNo", workOrderNo);
		param.put("equipCode", equipCode);
		param.put("seq", seq);
		param.put("user", SessionUtils.getUser().getUserCode());
		highPriorityOrderItemProDecDAO.insertSeqByWorkOrderNo(param);
	}

    @Override
    public List<HighPriorityOrderItemProDec> getByOrgCode(String orgCode) {
        return highPriorityOrderItemProDecDAO.getByOrgCode(orgCode);
    }
}
