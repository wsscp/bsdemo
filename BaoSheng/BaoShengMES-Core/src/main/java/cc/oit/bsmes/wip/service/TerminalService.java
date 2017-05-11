package cc.oit.bsmes.wip.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.eve.model.EventType;
import cc.oit.bsmes.inv.model.AssistOption;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.pla.model.BorrowMat;
import cc.oit.bsmes.pla.model.SupplementMaterial;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.wip.dto.MethodReturnDto;

/**
 *
 */
public interface TerminalService {

	/**
	 * <p>
	 * 暂停原因
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-7-15 11:12:48
	 * @return List<DataDic>
	 * @see
	 */
	public List<DataDic> queryPauseReasonDic();

	/**
	 * <p>
	 * 切换生产单
	 * </p>
	 * 
	 * @param userCode 工号
	 * @param password 密码
	 * @param equipCode 设备号
	 * @param pauseReason 暂停原因
	 * @return boolean
	 * @author DingXintao
	 * @date 2014-7-1 11:20:48
	 */
	public MethodReturnDto changeOrderSeq(String userCode, String password, String equipCode, String oldWorkOrderNo, String newWorkOrderNo,
			String pauseReason);

	public List<MaterialRequirementPlan> getMaterialInfo(Map<String, Object> params);

	public void insertMoreMaterial(Map<String, Object> params);

	/**
	 * <p>
	 * 获取设备报警类型
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-10 14:16:48
	 * @param query 级联参数
	 * @return
	 * @see
	 */
	public List<EventType> equipAlarmType();

	/**
	 * <p>
	 * 获取设备报警类型 明细
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-10 14:16:48
	 * @param query 级联参数
	 * @return
	 * @see
	 */
	public List<DataDic> equipAlarmTypeDesc(String query);
	
	/**
	 * @Title:       triggerEquipAlarm
	 * @Description: TODO(手动触发报警提交)
	 * @param:       equipCode 设备编码
	 * @param:       eventTypeCode 事件类型
	 * @param:       eventTypeCodeDesc 事件类型明细
	 * @param:       operator 操作工
	 * @param:       director 维修班负责人
	 * @return:      MethodReturnDto   
	 * @throws
	 */
	public MethodReturnDto triggerEquipAlarm(String equipCode, String eventTypeCode, String eventTypeCodeDesc, String operator, String director);

	/**
	 * 终端手动处理警报
	 * 
	 * @author DingXintao
	 * @date 2014年6月10日 下午11:44:33
	 * @param userCode 工号
	 * @param 事件ID
	 * @param equipCode 设备编码
	 * @return MethodReturnDto 返回信息
	 * @see
	 */
	public MethodReturnDto handleEquipAlarm(String userCode, String eventId, String equipCode);

	/**
	 * 处理报设备故障 警告
	 * 
	 * @author DingXintao
	 * @date 2014年6月11日 下午1:44:33
	 * @param userCode 工号
	 * @param 事件ID
	 * @return MethodReturnDto 返回信息
	 * @see
	 */
	public MethodReturnDto handleEquipError(String userCode, String equipCode);

	/**
	 * <p>
	 * 库存管理块：获取当前工序的物料位置信息
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-9-15 11:16:48
	 * @param params 物料号和工序ID
	 * @return List<Inventory>
	 * @see
	 */
	public List<Inventory> invLocationList(Inventory params);

	public void saveBorrowMat(SupplementMaterial supplementMaterial);

	public List<Mat> getMatbyWorkOrderNo(String workOrderNo, String processCode);

	public List<AssistOption> getAssistOp(String processCode);

	public void insertBorrowMat(BorrowMat boMat);

	public void updateBorrowMat(BorrowMat b);

	public void saveSupplementMaterial(SupplementMaterial b);

	public List<Map<String, Object>> getBZSemiProducts(String taskId);

	public List<Map<String, Object>> getBZOutSemiProducts(String taskId);

	public List<Map<String, Object>> getBZMaterialProps(String matCode);

	public void fillPropInMap(List<Map<String, Object>> propMaps, Map<String, String> paramMapping, Map<String, String> outMap);

	public List<Map<String, String>> isJYProcess(String custOrderItemId);

	public List<Map<String, String>> getRBMatPropsByProcessId(String processId);
	
	public Map<String,String> getSemiOutColors(String getSemiOutColors);
	
	public Map<String,String> getOrderTaskId(Map<String,String> param);
}
