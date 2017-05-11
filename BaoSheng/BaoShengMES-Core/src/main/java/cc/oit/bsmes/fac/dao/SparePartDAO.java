package cc.oit.bsmes.fac.dao;


import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.fac.model.SparePart;

/**
 * EquipInfoDAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2013年12月31日 下午3:43:43
 * @since
 * @version
 */
public interface SparePartDAO extends BaseDAO<SparePart> {

	List<SparePart> getSparePartsByRecordId(String recordId);

}
