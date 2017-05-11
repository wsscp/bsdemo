package cc.oit.bsmes.wip.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.Section;

import java.util.List;

/**
 * SectionDAO
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月31日 下午2:12:07
 * @since
 * @version
 */
public interface SectionDAO extends BaseDAO<Section> {

	public List<Section> getByOrderItemIdAndWoNo(String customerOrderItemId,
			String workOrderNo);

	public List<Section> getByWoNo(String workOrderNo);

    public List<Section> getToReport(String workOrderNo);

    public Section getLastByWoNo(String woNo);

    public List<Section> getByWoNoAndNotReport(String woNo,double sumReportLength);

    public List<Section> getPrintSectionInfo(String reportId);

    public List<Section> getByReportIdForInWarehouse(String reportId);
}
