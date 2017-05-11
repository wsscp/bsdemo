package cc.oit.bsmes.fac.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.fac.model.MaintainRecord;
import cc.oit.bsmes.fac.model.MaintainRecordItem;

import java.util.*;

/**
 * @author chanedi
 */
public interface MaintainRecordItemService extends BaseService<MaintainRecordItem> {

	public void deleteByRecordId(String recordId);

}