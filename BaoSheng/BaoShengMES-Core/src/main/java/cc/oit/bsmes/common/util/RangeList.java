package cc.oit.bsmes.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 可以合并range的list
 * 
 * @author chanedi
 * @date 2014-2-27 下午2:03:41
 * @since
 * @version
 */
public class RangeList extends LinkedList<Range> implements Comparable<RangeList>, Cloneable {

	private static final long serialVersionUID = 6159860443282139613L;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public RangeList() {
		super();
	}

	@Override
	public boolean add(Range e) {
		return add(e, true);
	}

	/**
	 * @author chanedi
	 * @date 2014-2-27 下午9:32:59
	 * @param e
	 * @param merge 为true时合并相邻区域
	 * @see
	 */
	public boolean add(Range e, boolean merge) {
		if (e == null || e.getLong() == 0) {
			return false;
		}

		int size = size();
		for (int i = 0; i < size; i++) {
			Range currentElement = get(i);
			int compareResult = e.compareTo(currentElement);
			if (compareResult == 0 || ((compareResult == -1 || compareResult == 1) && merge)) {
				// 有重复区域
				currentElement.setMinimum(Math.min(currentElement.getMinimum(), e.getMinimum()));
				currentElement.setMaximum(Math.max(currentElement.getMaximum(), e.getMaximum()));
				if (!merge) {
					logger.warn("非merge模式因为range重合而合并了！");
					throw new RuntimeException("非merge模式因为range重合而合并了！just for debug，如需生产单数据可注释此行");
				}
				return false;
			} else if (compareResult <= 0) {
				// currentElement足够大，插入
				super.add(i, e);
				return true;
			}
		}
		// 比所有都大
		return super.add(e);
	}

	public void add(long start, long end) {
		add(new Range(start, end));
	}

	public void add(Date start, Date end) {
		add(new Range(start.getTime(), end.getTime()));
	}

	/**
	 * 获取所有range的总长度
	 * 
	 * @return
	 */
	public long getLong() {
		Iterator<Range> it = iterator();
		long rangeLong = 0;
		while (it.hasNext()) {
			Range range = (Range) it.next();
			rangeLong += range.getLong();
		}
		return rangeLong;
	}

	/**
	 * 获取给定区间内所有range的总长度
	 * 
	 * @return
	 */
	public long getLong(long start, long end) {
		long rangeLong = 0;
		if (start > end) { // 无效参数
			return rangeLong;
		}
		for (Range range : this) {
			if (range.getMaximum() < start) { // 在所需要统计区间的前面
				continue;
			}
			if (range.getMinimum() > end) { // 在需要统计区间的后面
				continue;
			}
			// 4种情况： range在区间内，
			// 区间在range内，start在range内end在range外，end在range内start在range外
			if (start < range.getMinimum() && end > range.getMaximum()) { // range在区间内
				rangeLong += range.getLong();
			} else if (start > range.getMinimum() && end < range.getMaximum()) { // 区间在range内
				rangeLong += (end - start);
			} else if (start > range.getMinimum() && end > range.getMaximum()) { // start在range内end在range外
				rangeLong += (range.getMaximum() - start);
			} else if (start < range.getMinimum() && end < range.getMaximum()) { // end在range内start在range外
				rangeLong += (end - range.getMinimum());
			}
		}
		return rangeLong;
	}

	@Override
	public int compareTo(RangeList o) {
		if (this.getLong() > o.getLong()) {
			return 1;
		} else if (this.getLong() < o.getLong()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public Object clone() {
		RangeList clone = new RangeList();
		for (Range range : this) {
			clone.add(range.clone(), false);
		}
		return clone;
	}

	public static void main(String[] args) {
		RangeList r = new RangeList();
		r.add(new Range(3, 4));
		r.add(new Range(3, 6));
		r.add(new Range(5, 6));
		r.add(new Range(9, 16));
		r.add(new Range(1, 2), false);
		r.add(new Range(6, 9), false);
		System.out.println(r.toString());
		System.out.println(new Date(1398126946119l));
		System.out.println(1398126509756l + 1036363);
	}

}
