package cc.oit.bsmes.interfaceWWAc.service;

import cc.oit.bsmes.interfaceWWAc.model.EquipParamHistoryAcquisition;
import cc.oit.bsmes.wip.model.Receipt;

import java.util.Date;
import java.util.List;

public interface DataAcquisitionService {

    /**
     * 获取上次分段后记录下的长度。
     * @param equipCode
     * @return
     */
	public double getLength(String equipCode);
	
    /**
     * 批量获取参数的实时值
     * @param receipts 参数基本信息 只需填写receiptCode  和 equipCode 两个属性
     * @return
     */
	public  List<Receipt>  queryLiveReceiptByCodes(List<Receipt> receipts);
	
    /**
     * 单个获取参数的实时值
     * @param receipt 参数基本信息 只需填写receiptCode  和 equipCode 两个属性
     * @return
     */
	public void  queryLiveReceiptByCodes(Receipt receipt);
	
	
	/**
	 * <p>查询参数历史信息</p> 
	 * @author zhangdongping
	 * @date 2014-3-27 下午12:08:55
	 * @param  equipCode 设备代码
	 * @param  parmCode 参数代码
	 * @param  startTime 开始时间 默认当前时间往前推一个小时
	 * @param  endTime 结束时间  默认当前事件爱你
	 * @param  cycleCount 时间间隔内的周期数周期  默认200
	 * @return
	 */
	List<EquipParamHistoryAcquisition> findParamHistory(String equipCode,String parmCode,Date startTime,Date endTime ,Integer cycleCount);

    //List<EquipParamHistoryAcquisition> findParamHistory(EquipParamHistoryAcquisition historyAcquisition);
    
	List<EquipParamHistoryAcquisition> findLengthLiveData(String equipCode,String parmCode);
	List<EquipParamHistoryAcquisition> findSubParamHistory(String equipCode,String parmCode,Date startTime,Date endTime ,Integer cycleCount);

}
