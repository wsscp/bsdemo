package cc.oit.bsmes.interfaceWWIs.service;

import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.wip.model.Receipt;

import java.util.List;

public interface DataIssuedService {	 
	
	/**
	 * <p>下发工艺参数等到机台</p> 
	 * @author zhangdongping
	 * @date 2014-3-25 上午10:10:03
	 * @param receipts 菜单
	 * @param isDebug 是否调试
	 * @see
	 */
	public void IssuedParms(List<Receipt> receipts,Boolean isDebug);
	
	/**
	 * <p>设备状态变更时，处理设备是否开启报警设置</p> 
	 * @author zhangdongping
	 * @date 2014-7-11 下午5:58:49
	 * @param equipCode 设备线代码
	 * @param  设备状态变更之后所属的状态
	 * @see
	 */
	public void UpdateEquipAlarmState(String equipCode ,EquipStatus et);
	

}
