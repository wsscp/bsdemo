package cc.oit.bsmes.fac.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.fac.model.MaintainRecordItem;
import java.util.*;

/**
 * @author chanedi
 */
public interface MaintainRecordItemDAO extends BaseDAO<MaintainRecordItem> {

    public void deleteByRecordId(String recordId);

}