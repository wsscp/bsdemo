package cc.oit.bsmes.common.util;

import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class Range implements Comparable<Range>, Cloneable {

	private long minimum;
	private long maximum;
	private List<Range> lastRanges; // range前移时受此限制
	private Range nextRange; // range后移时受此限制
    private String characteristics; // TODO 用于辅助range的合并优化，如不满足用户需求且排程速度快时可考虑加入

	public Range() {
		super();
	}

	public Range(long minimum, long maximum) {
		this();
		if (minimum > maximum) {
			this.maximum = minimum;
			this.minimum = maximum;
		}
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public void setMinimum(long minimum) {
		if (minimum > maximum) { // long为0
			this.minimum = maximum;
		}
		this.minimum = minimum;
	}
	
	public void setMaximum(long maximum) {
		if (maximum > minimum) { // long为0
			this.maximum = minimum;
		}
		this.maximum = maximum;
	}
	
	public void setRange(Range range) {
		this.minimum = range.getMinimum();
		this.maximum = range.getMaximum();
	}
	
	public long getLong() {
		return maximum - minimum;
	}

	public void setLastRanges(List<Range> lastRanges) {
		this.lastRanges = lastRanges;
        if (lastRanges == null) {
            return;
        }
//        lastRanges.setNextRange(this);
	}

	/**
	 * element在range中(包括range两端端点)时返回true。
	 * @param element
	 * @return
	 */
	public boolean contains(Long element) {
		if (element == null) {
			return false;
		}
		return element >= minimum && element <= maximum;
	}

	/**
	 * 与指定range有重合
	 */
	public boolean isOverlappedBy(Range range) {
		return this.compareTo(range) == 0;
	}
	
	/**
	 * range完全大于element，没有重合
	 * 
	 * @param element
	 * @return
	 */
	public boolean isAfter(Long element) {
		if (element == null) {
			return false;
		}
		return minimum > element;
	}
	
	/**
	 * range完全大于目标range，没有重合
	 * 
	 * @return
	 */
	public boolean isAfter(Range range) {
		if (range == null) {
			return false;
		}
		return minimum > range.getMaximum();
	}

	/**
	 * 在指定range之后且与其相邻
	 * @author chanedi
	 * @date 2014-2-27 下午8:56:08
	 * @param range
	 * @see
	 */
	public boolean isAdjacentAfter(Range range) {
		return minimum == range.maximum;
	}
	
	/**
	 * range完全小于element，没有重合
	 * 
	 * @param element
	 * @return
	 */
	public boolean isBefore(Long element) {
		if (element == null) {
			return false;
		}
		return maximum < element;
	}
	
	/**
	 * range完全小于目标range，没有重合
	 * @return
	 */
	public boolean isBefore(Range range) {
		if (range == null) {
			return false;
		}
		return maximum < range.getMinimum();
	}

	/**
	 * 在指定range之前且与其相邻
	 * @author chanedi
	 * @date 2014-2-27 下午8:56:08
	 * @param range
	 * @see
	 */
	public boolean isAdjacentBefore(Range range) {
		return maximum == range.minimum;
	}


    /**
     * 根据空着的range（rangeLeft）向前移动，受lastRange限制。
     * 可以移动时rangeLeft也被压缩。
     * @author chanedi
     * @date 2014-2-27 下午9:00:04
     * @see
     * @return true if成功移动
     */
    public boolean moveBefore(long min) {
        long max = min + getLong();
        setMinimum(min);
        setMaximum(max);

        return true;
    }

	/**
	 * 根据空着的range（rangeLeft）向前移动，受lastRange限制。
	 * 可以移动时rangeLeft也被压缩。
	 * @author chanedi
	 * @date 2014-2-27 下午9:00:04
	 * @param rangeLeft
	 * @param minBoundary
     * @see
	 * @return true if成功移动
	 */
	public boolean moveBefore(Range rangeLeft, long minBoundary) {
        if (lastRanges != null) {
            minBoundary = Math.max(RangeUtils.getRangeMaximum(lastRanges), minBoundary);
        }
		if (contains(minBoundary)) {
			// 已经不能移动
			return false;
		}

		long min;
		if (lastRanges != null && rangeLeft.contains(minBoundary)) {
			// 受minBoundary影响
			rangeLeft.setMaximum(minBoundary);
			min = minBoundary;
		} else {
			rangeLeft.setMaximum(rangeLeft.getMinimum());
			min = rangeLeft.getMinimum();
		}

		long max = min + getLong();
		setMinimum(min);
		setMaximum(max);
		
		return true;
	}

    /**
	 * 如可以合并则将指定range合并到当前range。
	 * 只有两个range相邻时才可以合并。
	 * @author chanedi
	 * @date 2014-2-27 下午9:22:27
	 * @param range
	 * @see
	 * @return false 不能进行合并
	 */
	public boolean merge(Range range) {
		if (isAdjacentBefore(range)) {
			setMaximum(range.getMaximum());
			return true;
		}
		if (isAdjacentBefore(range)) {
			setMinimum(range.getMinimum());
			return false;
		}
		return false;
	}

	@Override
	public int compareTo(Range o) {
		if (this.maximum < o.minimum) {
			return -2;
		} else if (this.minimum > o.maximum) {
			return 2;
		} else if (this.maximum == o.minimum) {
			return -1;
		} else if (this.minimum == o.maximum) {
			return 1;
		} else {
			return 0;
		}
	}

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(getCharacteristics());
        sb.append("]");
        sb.append(getLong());
        sb.append("(");
        sb.append(getMinimum());
        sb.append(",");
        sb.append(getMaximum());
        sb.append(")");
//        return sb.toString();
        return toStringDate();
    }

    public String toStringDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
        StringBuffer sb = new StringBuffer();
//        sb.append("[");
//        sb.append(getCharacteristics());
//        sb.append("]");
        sb.append(getLong());
        sb.append("(");
        sb.append(dateFormat.format(new Date(getMinimum())));
        sb.append(",");
        sb.append(dateFormat.format(new Date(getMaximum())));
        sb.append(")");
        return sb.toString();
    }

    @Override
    protected Range clone() {
        try {
            return (Range) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
