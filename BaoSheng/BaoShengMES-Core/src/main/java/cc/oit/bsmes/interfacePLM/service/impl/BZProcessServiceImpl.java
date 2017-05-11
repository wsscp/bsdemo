package cc.oit.bsmes.interfacePLM.service.impl;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.BZProcessDAO;
import cc.oit.bsmes.interfacePLM.model.BZProcess;
import cc.oit.bsmes.interfacePLM.service.BZProcessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Jinhy on 2014/9/26 0026.
 *
 */
@Service
public class BZProcessServiceImpl extends BaseServiceImpl<BZProcess> implements BZProcessService{

    @Resource
    private BZProcessDAO bzProcessDAO;

    @Override
    public List<BZProcess> lastUpdateData( Map<String,Date> findParams) {
        return bzProcessDAO.lastUpdateData(findParams);
    }
}
