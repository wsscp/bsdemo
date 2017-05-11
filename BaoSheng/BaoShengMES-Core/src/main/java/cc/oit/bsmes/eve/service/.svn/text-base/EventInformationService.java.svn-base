package cc.oit.bsmes.eve.service;

import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.fac.model.EquipRepairRecord;

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
 * @date 2014-2-20 上午10:48:33
 * @since
 * @version
 */
public interface EventInformationService extends BaseService<EventInformation> {
	/**
	 * 
	 * <p>
	 * 根据事件处理流程ID判断该事件是否已处理
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-2-20 下午3:48:30
	 * @param eventProcessId
	 * @return
	 * @see
	 */
	int getEventStatusByEventProcessId(String eventProcessId);

	/**
	 * 
	 * <p>
	 * TODO(获取异常事件信息)
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-3-6 上午9:44:00
	 * @param param
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see
	 */
	List<EventInformation> getInfo(EventInformation param, int start, int limit, List<Sort> sortList);

	/**
	 * 
	 * <p>
	 * TODO(获取异常事件记录数)
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-3-6 上午9:44:30
	 * @param param
	 * @return
	 * @see
	 */
	Integer totalCount(EventInformation param);

	void insertInfo(EventInformation param);

	void insertInfo(List<EventInformation> param);

	/**
	 * <p>
	 * 事件创建接口
	 * </p>
	 * 
	 * @author zhangdongping
	 * @date 2014-6-24 下午5:00:19
	 * @param param 事件信息
	 * @param userList 事件关联人列表 存放usr_code
	 * @see
	 */
	void insertInfo(EventInformation param, List<String> userList);

	/**
	 * <p>
	 * TODO(处理设备警报列表)
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-18 下午4:52:30
	 * @param param
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<EventInformation> getAlarmInfo(Map<String,Object> param, int start, int limit, List<Sort> sortList);

	/**
	 * <p>
	 * TODO(处理设备警报列表记录数)
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-18 下午4:52:30
	 * @param param
	 * @return
	 * @see
	 */
	Integer alarmTotalCount(Map<String,Object> param);

	/**
	 * 在产品生产的第一道工序接受任务时，查询事件表中T_EVE_EVENT_INFO 的数据，
	 * 如果该成品存在拦截在制品的未处理的事件，不允许接受任务，只允许暂停，和恢复。 条件： PRODUCT_CODE=将要生产的成品的代码 and
	 * PENDING_PROCESSING =1 and EVENT_STATUS=UNCOMPLETED
	 * 
	 * @param productProcessId
	 * @return
	 */
	public boolean isProIntercept(String productProcessId);

	List<EventInformation> findNeedToProcess(EventInformation findParams);

	/**
	 * <p>
	 * 获取事件明细列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @date  2014-10-22 上午9:45:11
	 * @param  param
	 * @return List<EventInformation>
	 */
	public List<EventInformation> getEventInfoList(EventInformation param);

    /**
     * @param  orgCode
     * @return saleitemid
     */
    public List<String> getUncompletedOrderLateEvents(String[] equipCodes, String orgCode);

    public int countUncompletedByType(EventTypeContent type, String equipCode, String orgCode);

    public int findForWorkOrderExtensionAlarmTask(String bachNo);

	public List<EventInformation> getDistinctTileByOrgCode(String orgCode);

	List<String> getEquipCodes();

	List<EventInformation> getWarnShow();

	List<EventInformation> getByEquipCode(String equipCode,String eventTypeCodeDesc);

	EventInformation getRegisterData(String id);

	List<EquipRepairRecord> getCheckData(String id);

	List<EventInformation> getPendingEvent(Map<String, Object> param);

	List<EventInformation> getEquipEventPending();

	List<EventInformation> getEquipEventInfo(EventInformation param, int start,
			int limit, List<Sort> parseArray);

	Integer totalEquipEventInfo(EventInformation param);
	
	/**
	 * @Title:       hasRecentEvent
	 * @Description: TODO(根据标题判断最近一天该类型的事件是否已经产生)
	 * @param:       param 参数
	 * @return:      Integer   
	 * @throws
	 */
	public Integer hasRecentEvent(EventInformation param);
}
