package cc.oit.bsmes.pla.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.FinishedProduct;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.pla.model.OrderProcessPR;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.model.ProductProcessBz;
import cc.oit.bsmes.wip.dto.MethodReturnDto;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.WorkOrder;

/**
 * 手动排程
 * 
 * @author DingXintao
 */
public interface HandScheduleService {

	/**
	 * @Title: onExportOutPlan
	 * @Description: TODO(导出外计划)
	 * @param: dataArray 订单数据
	 * @return: String 文件路径
	 * @throws IOException
	 */
	public String putOutPlan2Excel(String columnArray, String dataIndexArray, String dataArray)
			throws FileNotFoundException, IOException, RowsExceededException, WriteException, BiffException;

	/**
	 * @Title: onExportOutPlan
	 * @Description: TODO(导出外计划)
	 * @param: outputStream 响应流
	 * @param: file 下载文件
	 * @return: void
	 * @throws IOException
	 */
	public void onExportOutPlan(OutputStream outputStream, File file) throws IOException;

	/**
	 * 保存排序并计算排程
	 * 
	 * @author DingXintao
	 * @param jsonText 编辑的排序对象
	 * @return MethodReturnDto
	 */
	public MethodReturnDto updateSeqAndCalculate(String jsonText) throws Exception;

	/**
	 * 重新计算排程
	 * 
	 * @author DingXintao
	 * @param jsonText 编辑的排序对象
	 * @return MethodReturnDto
	 */
	public MethodReturnDto calculateAgain() throws Exception;

	/**
	 * 查看生产单 - 调整生产单加工顺序 - 保存
	 * 
	 * @author DingXintao
	 * @param equipCode 设备编码
	 * @param updateSeq 编辑的排序对象
	 * @return MethodReturnDto
	 */
	public MethodReturnDto updateWOrkOrderSeq(String equipCode, String updateSeq) throws Exception;

	/**
	 * @Title mergeCustomerOrderItem
	 * @Description TODO(保存合并订单侯的生产单)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年4月6日 下午4:18:26
	 * @param docMakerUserCode 制单人
	 * @param receiverUserCode 接受人
	 * @param requireFinishDate 完成日期
	 * @param equipCodes 设备编码数组
	 * @param equipName 设备名称
	 * @param processName 工序名称
	 * @param processCode 工序编码
	 * @param userComment 备注
	 * @param specialReqSplit
	 * @param processJsonData 
	 *            工序Json对象，投入产出：转换对象CustomerOrderItemProDec、MaterialRequirementPlan保存
	 * @param orderTaskLengthJsonData 该订单产品该生产单所下发的长度
	 * @param preWorkOrderNo 上一道生产单的生产单号
	 * @param processesMergedArray 成缆工段生产单保存当前生产单所用工序的Json字符串
	 * @param workOrderSection 生产单所属工段 1:绝缘工段 2:成缆工段 3:护套工段
	 * @param nextSection 生产单下一个加工工段 1:绝缘工段 2:成缆工段 3:护套工段
	 * @param cusOrderItemIds 当前生产单中下发的所有客户生产订单明细IDs
	 * @param ids4FinishedJY 绝缘工段：已经完全下发所有工序的订单ID
	 * @param completeCusOrderItemIds 成缆工段：已经完全下完成缆工序的订单ID
	 * @param isDispatch 是否急件
	 * @param isHaved 是否陈线
	 * @param isAbroad 是否出口
	 * @param @return
	 * @return MethodReturnDto
	 * @throws
	 */
	public MethodReturnDto mergeCustomerOrderItem(String docMakerUserCode, String receiverUserCode,
			String requireFinishDate, String equipCodes, String equipName, String processName, String processCode,
			String userComment, String specialReqSplit, String processJsonData, String orderTaskLengthJsonData,
			String preWorkOrderNo, String processesMergedArray, String workOrderSection, String nextSection,
			String cusOrderItemIds, String ids4FinishedJY, String completeCusOrderItemIds, String isDispatch,
			String isHaved, String isAbroad);

	/**
	 * 获取查询下拉框：合同号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getContractNo(Map<String, Object> param);

	/**
	 * 获取查询下拉框：单位
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getCustomerCompany(Map<String, Object> param);

	/**
	 * 获取查询下拉框：经办人
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getOperator(Map<String, Object> param);

	/**
	 * 获取查询下拉框：客户型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getCustproductType(Map<String, Object> param);

	/**
	 * 获取查询下拉框：产品型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getProductType(Map<String, Object> param);

	/**
	 * 获取查询下拉框：产品规格
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getProductSpec(Map<String, Object> param);

	/**
	 * 获取查询下拉框：线芯结构
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getWiresStructure(Map<String, Object> param);

	/**
	 * 获取查询下拉框：线芯数
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getnumberOfWires(Map<String, Object> param);

	/**
	 * 获取查询下拉框：截面
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getSection(Map<String, Object> param);

	/**
	 * 获取查询下拉框：状态
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getstatus(Map<String, Object> param);

	/**
	 * [生产单]获取查询下拉框：合同号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getWorkOrderContractNo(Map<String, Object> param);

	/**
	 * [生产单]获取查询下拉框：单位
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getWorkOrderCustomerCompany(Map<String, Object> param);

	/**
	 * [生产单]获取查询下拉框：经办人
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getWorkOrderOperator(Map<String, Object> param);

	/**
	 * [生产单]获取查询下拉框：客户型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getWorkOrderCustproductType(Map<String, Object> param);

	/**
	 * [生产单]获取查询下拉框：产品型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getWorkOrderProductType(Map<String, Object> param);

	/**
	 * [生产单]获取查询下拉框：产品规格
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getWorkOrderProductSpec(Map<String, Object> param);

	public List<WorkOrder> getWorkOrderProcessName();

	/**
	 * [生产单]获取查询下拉框：线芯结构
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getWorkOrderWiresStructure(Map<String, Object> param);

	/**
	 * 查询接收人信息
	 * 
	 * @author DingXintao
	 * @param section 工段
	 * */
	public List<WorkOrder> getReceiver(String section);

	/**
	 * 设备调整：修改生产单的使用设备
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单编号
	 * @param equipNameArrayStr 设备名称集
	 * @param equipCodeArrayStr 设备编码集
	 * @return MethodReturnDto
	 * */
	public MethodReturnDto changeWorkEquipSub(String workOrderNo, String equipNameArrayStr, String equipCodeArrayStr);

	public List<MesClient> getEquipInfo(Map<String, Object> param);

	public List<MesClient> getProcessCode(Map<String, Object> param);

	public List<MesClient> getTaskStatueInEquip(Map<String, Object> param);

	public List<CustomerOrderItemProDec> getSwitchHistory(Map<String, Object> param);

	public List<Report> getWorkOrderReportHistory(Map<String, Object> param);

	/**
	 * 排程前校验：产品是否已经下发
	 * 
	 * @param orderItemIdArray 校验的订单ID
	 * @param isyunmu 是否云母绕包工序
	 * */
	public MethodReturnDto hasAuditOrder(String orderItemIdArrayStr, boolean isyunmu);

	public void insertNewColorData(Map<String, Object> param);

	/**
	 * @param useLength 
	 * @Title: updateIsReport
	 * @Description: TODO((更新订单的特殊状态: 0:厂外计划;1:计划已报;2:手工单;3:库存生产)
	 * @author: DinXintao
	 * @version V1.0
	 * @date: 2016年3月17日 上午9:44:43
	 * @param: ids 订单ID(T_PLA_CUSTOMER_ORDER_ITEM)
	 * @param: specialFlag 0:厂外计划;1:计划已报;2:手工单;3:库存生产
	 * @param: finished 是否完成订单状态
	 * @return: MethodReturnDto
	 * @throws
	 */
	public MethodReturnDto updateSpecialFlag(String ids, String specialFlag, Boolean finished);

	public void importFinishedProduct(Sheet sheet, JSONObject result);

	public List<FinishedProduct> listFinishedProduct(Integer start,
			Integer limit, FinishedProduct findParams);

	public Integer countFinishedProduct(FinishedProduct findParams);

	public List<FinishedProduct> getAllModelORSpec(String type,String query);

	public void updateFinishedProduct(String id, String uselength,
			String salesOrderItemId, JSONObject result);

	public List<FinishedProduct> getFinishedProductById(String salesOrderItemId);

}
