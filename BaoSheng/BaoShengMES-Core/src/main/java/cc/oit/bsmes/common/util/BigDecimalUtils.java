package cc.oit.bsmes.common.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;


/**
 * 数字通用类 
 * @date 2012-10-17 下午1:50:43
 */
public class BigDecimalUtils extends NumberUtils {

	/**
	 * 乘法
	 * 如果输入值为空,返回值也为空 
	 * @date 2012-10-17 下午2:11:07
	 */
	public static BigDecimal multiply(BigDecimal d1, Number d2) {
		
		if (d1 == null || d2 == null)
			return null;
		
		BigDecimal d3 = createBigDecimal(d2.toString());
		
		return d1.multiply(d3);
	}
	
	/**
	 * 加法
	 * 对于为空的输入值,以零处理 
	 * @date 2012-10-17 下午2:12:18
	 */
	public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
		
		BigDecimal sum = BigDecimal.ZERO;
		if (d1 != null)
			sum = sum.add(d1);
		if (d2 != null)
			sum = sum.add(d2);
		
		return sum;
	}

	/**
	 * 合计
	 * 对于为空的输入值,以零处理 
	 * @date 2012-10-17 下午2:12:18
	 */
	public static BigDecimal sum(BigDecimal... ds) {

		BigDecimal sum = BigDecimal.ZERO;
		
		for(BigDecimal d : ds){
			if (d != null) {
				sum = sum.add(d);
			}
        }
		
		return sum;
	}
}
