package cc.oit.bsmes.interfacePLM.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.BZProcess;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by JinHy on 2014/9/25 0025.
 */
public interface BZProcessDAO extends BaseDAO<BZProcess>{

    /**
     * 查询
     * @param lastDate
     * @return
     */
    List<BZProcess> lastUpdateData( Map<String,Date> findParams);
}

