package cc.oit.bsmes.wip.service;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.wip.dto.SectionLength;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.Section;

import java.util.List;
import java.util.Map;

public interface SectionService extends BaseService<Section> {
	
	/**
	 * 检查是否goodLength。
	 * 
	 * 详见《订单OA计算程序逻辑设计-goodLength验证》活动图。
	 * 
	 * @author chanedi
	 * @date 2013年12月25日 下午1:54:33
	 * @param lengthToCheck
	 * @param customerOrderItemId
	 * @param completedLengths 已完成长度，结构为<工序全路径，分段长度>
	 * @param processPath 工序全路径
	 * @param parrelCount
     * @return
	 * @see
	 */
	public boolean isGoodLength(double lengthToCheck, String customerOrderItemId, Map<String, List<Double>> completedLengths, String processPath, int parrelCount);

	/**
	 * 获取可以冲抵的长度。
	 * 详见《订单OA计算程序逻辑设计-库存冲抵》活动图。
	 * 
	 * @author chanedi
	 * @date 2014年1月6日 下午5:49:11
	 * @param completedLengths 已完成长度，结构为<工序全路径，分段长度>
	 * @param customerOrderItemProDec 此方法会更新usedStockLength、finishedLength和unFinishedLength
	 * @param forStock 为true则更新usedStockLength，否则更新finishedLength
	 * @return
	 * @see
	 */
	public List<? extends SectionLength> getMatchedSections(List<? extends SectionLength> sortedSections,
			Map<String, List<Double>> completedLengths,
			CustomerOrderItemProDec customerOrderItemProDec, boolean forStock);

    public List<Section> getMatchedSections(Section section,List<CustomerOrderItemProDec> proDecs,Map<String,
            CustomerOrderItemDec> itemDecMap,String reportId,User user);

    /**
     * 计算section 浪费长度
     * @param section
     * @param proDecs
     * @param itemDecMap
     * @return
     */
    public double calculateWasteLength(Section section,List<CustomerOrderItemProDec> proDecs,Map<String,
            CustomerOrderItemDec> itemDecMap);

    //public List<Section> getMatchedSections(Section section,List<CustomerOrderItemProDec> proDecs);

	/**
	 * 根据{@link CustomerOrderItem#getId()}和生产单号获取分段，根据长度从小到大排序。
	 * @author chanedi
	 * @date 2014年1月6日 下午1:53:10
	 * @param customerOrderItemId
	 * @param workOrderNo
	 * @see
	 */
	public List<Section> getByOrderItemIdAndWoNo(String customerOrderItemId, String workOrderNo);

	/**
	 * 根据{@link CustomerOrderItemDec#getId()}获取已完成长度。 
	 * @author chanedi
	 * @date 2014年1月6日 下午6:44:26
	 * @param orderItemDecId
	 * @return
	 * @see
	 */
	public Map<String, List<Double>> getCompletedLengths(String orderItemDecId);

	/**
	 * 根据生产单号获取分段。
	 * @author chanedi
	 * @date 2014年2月14日 下午3:56:56
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<Section> getByWoNo(String workOrderNo);

    /**
     * 获取待报工的分段。
     * @author chanedi
     * @param loadCurrent if true 组装数据库中没有的分段
     * @return
     * @see
     */
    public List<Section> getToReport(String equipCode, boolean loadCurrent);

    public void insertCurrentSection(String equipCode, Report report, Section section);

    /**
     *
     * @param findParams
     * @return
     */
    public Section getOne(Section findParams);

    /**
     * 得到前一段分段长度
     * @param woNo
     * @return
     */
    public Section getLastSection(String woNo);

    public  List<Section> getByWoNoAndNotReport(String woNo,double sumReportLength);

    public List<Section> getPrintSectionInfo(String reportId);

    /**
     * 入库
     * @param reportId
     * @return
     */
    public List<Section> getByReportIdForInWarehouse(String reportId);
}
