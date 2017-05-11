package cc.oit.bsmes.pla.service.impl;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pla.dao.InvOaUseLogDAO;
import cc.oit.bsmes.pla.model.InvOaUseLog;
import cc.oit.bsmes.pla.service.InvOaUseLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InvOaUseLogServiceImpl extends BaseServiceImpl<InvOaUseLog> implements InvOaUseLogService {
	
	@Resource
	private InvOaUseLogDAO invOaUseLogDAO;
	
	@Override
	public List<InvOaUseLog> getByRefId(String refId) {
		return invOaUseLogDAO.getByRefId(refId);
	}


    @Override
    public void cancelOffSet(String proDecId, String updator) {
        invOaUseLogDAO.cancelOffSet(proDecId,updator);
    }
}
