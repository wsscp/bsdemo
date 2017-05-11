package cc.oit.bsmes.fac.dao;

import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.fac.model.MaintainRecord;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * @author chanedi
 */
public interface MaintainRecordDAO extends BaseDAO<MaintainRecord> {

    public MaintainRecord getLatest(@Param("equipCode") String equipCode, @Param("maintainTemplateType") MaintainTemplateType maintainTemplateType);

	public List getDailyCheck(MaintainRecord param);

	public Integer countDailyChecks(MaintainRecord param);

	public MaintainRecord getLatestByEquipLine(String equipCode,MaintainTemplateType daily);
	
	public Date getDailyCheckFinishTime(String code);

}