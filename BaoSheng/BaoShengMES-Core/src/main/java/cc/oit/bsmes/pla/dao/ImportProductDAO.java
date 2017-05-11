package cc.oit.bsmes.pla.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.ImportProduct;

/**
 * 
 * @author rongyd
 * @date 2015-8-3 下午5:38:49
 * @since
 * @version
 */
public interface ImportProductDAO extends BaseDAO<ImportProduct> {
	
	public List<ImportProduct> checkExistsByName(String seriesName);
	
	public List<ImportProduct> getSeriesNameAndUserName();
	
	public List<ImportProduct> getUserName();
}
