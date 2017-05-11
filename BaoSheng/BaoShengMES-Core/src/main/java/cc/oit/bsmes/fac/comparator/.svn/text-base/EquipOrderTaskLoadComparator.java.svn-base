package cc.oit.bsmes.fac.comparator;

import cc.oit.bsmes.common.util.RangeList;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.pla.model.OrderTask;

import java.util.Comparator;
import java.util.List;

public class EquipOrderTaskLoadComparator implements Comparator<EquipInfo> {

	@Override
	public int compare(EquipInfo o1, EquipInfo o2) {
		RangeList timeRanges1 = getTimeRange(o1);
		RangeList timeRanges2 = getTimeRange(o2);
		// 负载最满的排前面
		return - timeRanges1.compareTo(timeRanges2);
	}
	
	private RangeList getTimeRange(EquipInfo equipInfo) {
		RangeList timeRanges = new RangeList();
		List<OrderTask> orderTasks = equipInfo.getOrderTasks();
		for (OrderTask orderTask : orderTasks) { // 注意不可省略，此处为深克隆
			timeRanges.add(orderTask.getRange().getMinimum(), orderTask.getRange().getMaximum());
		}
		return timeRanges;
	}

}
