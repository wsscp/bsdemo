package cc.oit.bsmes.interfaceWebClient.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.interfaceWebClient.dao.ProQualitySumDAO;
import cc.oit.bsmes.interfaceWebClient.model.ProQualitySum;
import cc.oit.bsmes.interfaceWebClient.service.ProQualitySumService;

/**
 *  
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2015-5-19 下午1:04:50
 * @since
 * @version
 */
@Service
public class ProQualitySumServiceImpl extends BaseServiceImpl<ProQualitySum> implements ProQualitySumService{
	
	@Resource ProQualitySumDAO proQualitySumDAO;
	@Override
	public List<ProQualitySum> getProProQualitySum(ProQualitySum parm) {
		User user = SessionUtils.getUser();		
		if(user!=null)
		parm.setOrgCode(user.getOrgCode());
		return  proQualitySumDAO.getProQualitySum(parm);
		 
		
	}
	@Override
	public List<ProQualitySum> getTopProProQuality(ProQualitySum parm) {
		User user = SessionUtils.getUser();		
		if(user!=null)
		parm.setOrgCode(user.getOrgCode());
		return  proQualitySumDAO.getTopProProQuality(parm);
		 
	}
}
