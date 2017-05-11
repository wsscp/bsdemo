package cc.oit.bsmes.ord.dto;

import cc.oit.bsmes.ord.dto.LengthConstraints.LengthConstraint;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 原LengthConstraints的String格式为“,100:0;,200:1;,300:3”。
 * TODO 符合,[^:]+:0的仅允许一个
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月23日 下午1:34:05
 * @since
 * @version
 */
public class LengthConstraints implements Serializable, Iterable<LengthConstraint> {

	private static final long serialVersionUID = 2679104572493542258L;
	private static final String ITEM_SEPERATOR = ";";
	private static final String COUNT_SEPERATOR = ":";
	private static final String RANGE_SEPERATOR = ",";

	private List<LengthConstraint> lengthConstraints;
	
	/**
	 * 转换字符串为对象。
	 * @author chanedi
	 * @date 2013年12月25日 上午11:56:18
	 * @param lengthConstraints
	 */
	public LengthConstraints(String lengthConstraints) {
		this.lengthConstraints = new ArrayList<LengthConstraint>();
		String[] items = lengthConstraints.split(ITEM_SEPERATOR);
		for (String item : items) {
			this.lengthConstraints.add(new LengthConstraint(item));
		}
	}
	
	/**
	 * 转换对象为字符串。
	 * @author chanedi
	 * @date 2013年12月25日 上午11:56:36
	 * @return 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        StringBuffer sb = new StringBuffer();
		for (LengthConstraint lengthConstraint : lengthConstraints) {
			if (lengthConstraint.getMinLength() != null) {
				sb.append(lengthConstraint.getMinLength());
			}
			sb.append(RANGE_SEPERATOR);
			if (lengthConstraint.getMaxLength() != null) {
				sb.append(lengthConstraint.getMaxLength());
			}
			sb.append(COUNT_SEPERATOR);
			sb.append(lengthConstraint.getMaxQuantity());
			sb.append(ITEM_SEPERATOR);
		}
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	@Override
	public Iterator<LengthConstraint> iterator() {
		return lengthConstraints.iterator();
	}

    public double getMaxMinLength() {
        if (lengthConstraints == null) {
            return 0;
        }
        double maxMinLength = 0;
        for (LengthConstraint lengthConstraint : lengthConstraints) {
            maxMinLength = maxMinLength > lengthConstraint.getMaxLength() ? maxMinLength : lengthConstraint.getMaxLength();
        }
        return maxMinLength;
    }

    public class LengthConstraint {
		
		@Getter
		private Double minLength;
		
		@Getter
		private Double maxLength;
		
		@Getter
		private int maxQuantity;

		/**
		 * 转换字符串为对象。
		 * @author chanedi
		 * @date 2013年12月25日 上午11:56:53
		 * @param lengthConstraint
		 */
		public LengthConstraint(String lengthConstraint) {
			String[] splits = lengthConstraint.split(COUNT_SEPERATOR);
			maxQuantity = Integer.parseInt(splits[1]);
			String[] ranges = splits[0].split(RANGE_SEPERATOR);
			if (ranges[0].length() > 1) {
				minLength = Double.parseDouble(ranges[0]);
			}
			if (ranges[1].length() > 1) {
				maxLength = Double.parseDouble(ranges[1]);
			}
		}
		
	}
	
}
