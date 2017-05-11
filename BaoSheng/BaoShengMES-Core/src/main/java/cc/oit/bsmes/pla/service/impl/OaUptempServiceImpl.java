package cc.oit.bsmes.pla.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pla.dao.OaUptempDAO;
import cc.oit.bsmes.pla.model.OaUptemp;
import cc.oit.bsmes.pla.service.OaUptempService;

/**
 * OaUptempServiceImpl 计算OA最后更新数据的中间临时表 独立建表更新，目的是因为计算时间过程，更新常用的几张表锁表，导致其他程序操作死锁
 * 
 * @author DingXintao
 * @date 2015年12月22日 下午4:30:34
 * @since
 * @version
 */
@Service
public class OaUptempServiceImpl extends BaseServiceImpl<OaUptemp> implements OaUptempService {
	@Resource
	private OaUptempDAO oaUptempDAO;
	
	/**
	 * 调用存储过程更新订单的OA时间
	 * 
	 * @param orgCode 组织机构
	 * */ 
	public void callUpdateOrderOaTime(String orgCode){
		oaUptempDAO.callUpdateOrderOaTime(orgCode);
	}
}
