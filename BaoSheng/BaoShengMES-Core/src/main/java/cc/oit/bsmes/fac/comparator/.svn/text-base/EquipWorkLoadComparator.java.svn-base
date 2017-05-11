package cc.oit.bsmes.fac.comparator;

import java.util.Comparator;
import java.util.List;

import cc.oit.bsmes.common.util.RangeList;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.WorkTask;

public class EquipWorkLoadComparator implements Comparator<EquipInfo> {

	@Override
	public int compare(EquipInfo o1, EquipInfo o2) {
		RangeList timeRanges1 = getTimeRange(o1);
		RangeList timeRanges2 = getTimeRange(o2);
		// 负载最满的排前面
		return - timeRanges1.compareTo(timeRanges2);
	}
	
	private RangeList getTimeRange(EquipInfo equipInfo) {
		RangeList timeRanges = new RangeList();
		List<WorkTask> workTasks = equipInfo.getWorkTasks();
		for (WorkTask workTask : workTasks) {
			timeRanges.add(workTask.getWorkStartTime(), workTask.getWorkEndTime());
		}
		return timeRanges;
	}

}
