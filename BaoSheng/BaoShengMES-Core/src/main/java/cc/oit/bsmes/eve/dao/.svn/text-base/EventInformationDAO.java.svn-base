package cc.oit.bsmes.eve.dao;

import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.eve.model.EventInformation;

import java.util.List;
import java.util.Map;

/**
 * 
 * 异常事件信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-2-20 上午10:55:55
 * @since
 * @version
 */
public interface EventInformationDAO extends BaseDAO<EventInformation> {

	public int getEventStatusByEventProcessId(String eventProcessId);

	/**
	 * 
	 * @author leiwei
	 * @date 2014-3-6 上午9:56:22
	 * @param param
	 * @return
	 * @see
	 */
	public Integer totalCount(EventInformation param);

	/**
	 * 
	 * @author leiwei
	 * @date 2014-3-6 上午9:56:28
	 * @param param
	 * @return
	 * @see
	 */
	public List<EventInformation> getInfo(EventInformation param);

	/**
	 * 
	 * @author DingXintao
	 * @date 2014-6-18 下午4:52:30
	 * @param param
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	public Integer alarmTotalCount(Map<String,Object> param);

	/**
	 * 
	 * @author DingXintao
	 * @date 2014-6-18 下午4:52:30
	 * @param param
	 * @return
	 * @see
	 */
	public List<EventInformation> getAlarmInfo(Map<String,Object> param);

	/**
	 * 
	 * @param productProcessId
	 * @return
	 */
	public int checkProIntercept(String productProcessId);

	public List<EventInformation> findNeedToProcess(EventInformation findParams);

	/**
	 * <p>
	 * 获取事件明细列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-10-22 上午9:45:11
	 * @param param
	 * @return List<EventInformation>
	 */
	public List<EventInformation> getEventInfoList(EventInformation param);

    /**
     *
     * @param orgCode
     * @return saleitemid
     */
    public List<String> getUncompletedOrderLateEvents(String[] equipCodes, String orgCode);

    public int countUncompletedByType(EventTypeContent type, String equipCode, String orgCode);

    public int findForWorkOrderExtensionAlarmTask(String bachNo);

	public List<EventInformation> getDistinctTileByOrgCode(String orgCode);

	public List<String> getEquipCodes();

	public List<EventInformation> getWarnShow();

	public List<EventInformation> getByEquipCode(String equipCode,String eventTypeCodeDesc);

	public EventInformation getRegisterData(String id);

	public List<EventInformation> getPendingEvent(Map<String, Object> param);

	public List<EventInformation> getEquipEventPending();
	
	
	/**
	 * @Title:       hasRecentEvent
	 * @Description: TODO(根据标题判断最近一天该类型的事件是否已经产生)
	 * @param:       param 参数
	 * @return:      Integer   
	 * @throws
	 */
	public Integer hasRecentEvent(EventInformation param);
	
}
