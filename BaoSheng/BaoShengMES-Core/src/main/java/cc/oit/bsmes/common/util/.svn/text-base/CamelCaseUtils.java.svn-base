/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */
package cc.oit.bsmes.common.util;

/**
 * <p style="display:none">
 * 驼峰式命名 转换
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-2-25 下午3:36:33
 * @since
 * @version
 */
public class CamelCaseUtils {

	private static final char SEPARATOR = '_';

	/**
	 * 
	 * <p>将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</p> 
	 * @author QiuYangjun
	 * @date 2014-2-25 下午3:38:08
	 * @param name
	 * @return
	 * @see
	 */
	public static String toUnderlineName(String name) {
		if (name == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);

			boolean nextUpperCase = true;

			if (i < (name.length() - 1)) {
				nextUpperCase = Character.isUpperCase(name.charAt(i + 1));
			}

			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	public static String toCamelCase(String name) {
		if (name == null) {
			return null;
		}

		name = name.toLowerCase();

		StringBuilder sb = new StringBuilder(name.length());
		boolean upperCase = false;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String toCapitalizeCamelCase(String name) {
		if (name == null) {
			return null;
		}
		name = toCamelCase(name);
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

}