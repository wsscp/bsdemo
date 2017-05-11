package cc.oit.bsmes.interfacePLM.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.MachObjof;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by JinHy on 2014/9/28 0028.
 */
public interface MachObjofDAO extends BaseDAO<MachObjof>{
    List<MachObjof> lastUpdateData( Map<String,Date> findParams);
}
