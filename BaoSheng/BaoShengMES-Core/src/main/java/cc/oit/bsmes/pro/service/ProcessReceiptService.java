package cc.oit.bsmes.pro.service;

import java.util.List;
import java.util.Map;

import jxl.Cell;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProcessReceipt;

public interface ProcessReceiptService extends BaseService<ProcessReceipt> {

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-3 上午11:40:52
	 * @param eqipListId
	 * @return
	 * @see
	 */
	public List<ProcessReceipt> getByEqipListId(String eqipListId);

	/**
	 * getByWorkOrderId
	 * 
	 * @author chanedi
	 * @date 2014年2月21日 下午6:19:56
	 * @param woId
	 * @return
	 * @see
	 */
	public List<ProcessReceipt> getByWorkOrderNo(String woNo, String equipCode);

	/**
	 * <p>
	 * 根据设备code和工艺code查找工艺参数
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-7 上午11:03:29
	 * @param equipCode
	 * @param processCode
	 * @return
	 * @see
	 */
	public List<ProcessReceipt> getByEquipCodeAndProcessCode(String equipCode, String processCode);

	/**
	 * <p>
	 * 根据设备code和工艺code 工艺参数code 查找工艺参数信息
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-7 上午11:03:29
	 * @param equipCode
	 * @param processCode
	 * @param receiptCode
	 * @return
	 * @see
	 */
	public ProcessReceipt getByEquipCodeAndProcessCodeAndReceiptCode(String equipCode, String processCode,
			String receiptCode);

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-21 下午6:18:56
	 * @param eqipListId
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	public List<ProcessReceipt> getByEqipListId(String eqipListId, int start, int limit);

	/**
	 * <p>
	 * 导入工艺参数
	 * </p>
	 * 
	 * @author zhangdongping
	 * @date 2014-6-12 下午1:51:56
	 * @param qcList
	 * @param equipCode 设备编码
	 * @param acEquipCode 真实设备编码
	 * @param orgCode
	 * @see
	 */
	public void importProcessQc(List<Cell[]> qcList, String quipCode, String acEquipCode, String orgCode);

	public List<ProcessReceipt> getByProcessId(String processId);

	public void insertBatchInterface(List<ProcessReceipt> receiptListResult);

	/**
	 * 通过mesid和plmid插入生产线参数表
	 * 
	 * @param mesId
	 * @param plmId
	 * @param equipNo
	 * @return
	 */
	public List<ProcessReceipt> updateEquipQc(String mesId, String plmId, String equipNo);

	/**
	 * 手工用test插入生产线参数表
	 * 
	 * @param craftsCode
	 */
	public void updateEquipQcByHand(String craftsCode);

	Map<String, String> getEquipQcByItemId(String[] orderItemIdArray);
}
