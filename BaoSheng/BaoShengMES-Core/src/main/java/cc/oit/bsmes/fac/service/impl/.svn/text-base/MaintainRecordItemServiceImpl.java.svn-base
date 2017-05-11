package cc.oit.bsmes.fac.service.impl;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.fac.dao.MaintainRecordItemDAO;
import cc.oit.bsmes.fac.service.MaintainRecordItemService;
import cc.oit.bsmes.fac.model.MaintainRecordItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author chanedi
 */
@Service
public class MaintainRecordItemServiceImpl extends BaseServiceImpl<MaintainRecordItem> implements MaintainRecordItemService {

    @Resource
    private MaintainRecordItemDAO maintainRecordItemDAO;

    @Override
    public void deleteByRecordId(String recordId) {
        maintainRecordItemDAO.deleteByRecordId(recordId);
    }

}