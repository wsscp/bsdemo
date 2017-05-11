package cc.oit.bsmes.common.util;

/**
 * Created by 羽霓 on 2014/5/16.
 */
public class RangeUtils {

    public static Long getRangeMaximum(Iterable<Range> ranges) {
        long max = 0l;
        for (Range range : ranges) {
            max = Math.max(max, range.getMaximum());
        }
        return max;
    }

}
