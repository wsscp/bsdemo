package cc.oit.bsmes.interfaceWebClient.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.interfaceWebClient.dao.FacSummaryDAO;
import cc.oit.bsmes.interfaceWebClient.model.FacSummary;
import cc.oit.bsmes.interfaceWebClient.service.FacSummaryService;

/**
 *  
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2015-5-19 下午1:04:50
 * @since
 * @version
 */
@Service
public class FacSummaryServiceImpl extends BaseServiceImpl<FacSummary> implements FacSummaryService{
	
	@Resource FacSummaryDAO facSummaryDAO;
	@Override
	public List<FacSummary> getFacSummary(FacSummary parm) {
		User user = SessionUtils.getUser();		
		if(user!=null)
		parm.setOrgCode(user.getOrgCode());
		return  facSummaryDAO.getFacSummary(parm);
		 
		
	}
}
