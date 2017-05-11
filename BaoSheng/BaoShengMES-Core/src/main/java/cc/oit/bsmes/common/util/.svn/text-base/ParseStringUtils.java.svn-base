package cc.oit.bsmes.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/*
	private static final String REGEX_6 = "\\d{1,2}\\*\\d{1,2}\\.?\\d{0,2}\\([\u4E00-\u9FA5]+\\)";
 */
public class ParseStringUtils {

	public static void main(String[] args) {
		
		
		
		
	
	}
	
	
	private static final String REGEX = "\\d+[A-Z]\\*.+";
	private static final String REGEX_1 = "\\d+[P]\\*.+";
	private static final String REGEX_2 = "\\d+[G][0-9].+";

	/**
	 * 由产品规格计算芯数
	 * 
	 * @return
	 */
	public static Integer parseNumberOfWires(String arg) {
		try {
			String[] a = arg.split("\\+");
			if (a.length > 1 && a[0] != null && !"".equals(a[0]) && a[1] != null && !"".equals(a[1])) {
				BigDecimal cd = new BigDecimal(0);
				for (String obj : a) {
					if (obj.contains("(") && obj.contains(")")) {
						obj = obj.substring(obj.indexOf("(") + 1, obj.indexOf(")"));
					} else if (obj.contains("(") && !obj.contains(")")) {
						obj = obj.substring(obj.indexOf("(") + 1);
					} else if (!obj.contains("(") && obj.contains(")")) {
						obj = obj.substring(0, obj.indexOf(")"));
					}
					String[] c = obj.split("\\*");
					if (c.length > 1) {
						if (c.length == 2 && c[0] != null && !"".equals(c[0])) {
							if (c[0].contains("P")) {
								String s = c[0].replaceAll("(P)", "");
								cd = cd.add(new BigDecimal(s));
							} else {
								cd = cd.add(new BigDecimal(c[0]));
							}
						}
					} else {
						if (!obj.contains("S")) {
							cd = cd.add(new BigDecimal(1));
						}
					}
				}
				return cd.intValue();
			} else {
				if (arg.matches(REGEX)) {
					String[] b = arg.split("\\*");
					if (arg.matches(REGEX_1)) {
						return new BigDecimal(b[0].substring(0, b[0].indexOf('P'))).multiply(new BigDecimal(2))
								.intValue();
					} else {
						return new BigDecimal(b[0].substring(0, b[0].length() - 1)).intValue();
					}
				} else if (arg.matches(REGEX_2)) {
					String[] b = arg.split("G");
					return new BigDecimal(b[0]).intValue();
				} else {
					String s = arg.replaceAll("\\(", "").replaceAll("\\)", "");
					String[] b = s.split("\\*");
					if (b.length == 2 && b[0] != null && !"".equals(b[0])) {
						return new BigDecimal(b[0]).intValue();
					} else if (b.length == 3 && b[0] != null && !"".equals(b[0]) && b[1] != null && !"".equals(b[1])) {
						return new BigDecimal(b[0]).multiply(new BigDecimal(b[1])).intValue();
					}
				}
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public static String parseNumberOfSection(String arg) {
		try {
			String[] a = arg.split("\\+");
			if (a.length > 1 && a[0] != null && !"".equals(a[0]) && a[1] != null && !"".equals(a[1])) {
				List<String> cs = new ArrayList<String>();
				for (String obj : a) {
					if (obj.contains("(") && obj.contains(")")) {
						obj = obj.substring(obj.indexOf("(") + 1, obj.indexOf(")"));
					} else if (obj.contains("(") && !obj.contains(")")) {
						obj = obj.substring(obj.indexOf("(") + 1);
					} else if (!obj.contains("(") && obj.contains(")")) {
						obj = obj.substring(0, obj.indexOf(")"));
					}
					String[] c = obj.split("\\*");
					if (c.length > 1) {
						if (c.length == 2 && c[1] != null && !"".equals(c[1])) {
							cs.add(c[1]);
						}
					} else {
						if (!obj.contains("S")) {
							cs.add(obj.replaceAll("[^\\d.]+", ""));
						}
					}
				}
				HashSet<String> h = new HashSet<String>(cs);
				cs.clear();
				cs.addAll(h);
				return cs.toString().replaceAll("[\\[\\s\\]]", "");
			} else {
				if (arg.matches(REGEX_2)) {
					String[] b = arg.split("G");
					return b[1].replaceAll("[([^0-9\\.])]", "");
				} else {
					String s = arg.replaceAll("\\(", "").replaceAll("\\)", "");
					String[] b = s.split("\\*");
					if (b.length == 2 && b[1] != null && !"".equals(b[1])) {
						return b[1].replaceAll("([^0-9.]+)", "");
					} else if (b.length == 3 && b[0] != null && !"".equals(b[0]) && b[2] != null && !"".equals(b[2])) {
						return b[2].replaceAll("([^0-9.]+)", "($1)");
					}
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static Integer parseNumber(String str) {
		try {
			String[] arg = str.split("[+]");
			int sum = 0;
			int value = 1;
			for (int i = 0; i < arg.length; i++) {
				String[] numArray = arg[i].split("[*]");
				for (int j = 0; j < numArray.length - 1; j++) {
					int v = Integer.parseInt(numArray[j]);
					value *= v;
				}
				sum = sum + value;
				value = 1;
			}
			return sum;
		} catch (Exception e) {
			return 0;
		}
	}

	public static String parseSection(String str) {
		try {
			String[] arg = str.split("[+]");
			StringBuffer buffer = new StringBuffer();
			String value = "";
			Map<String, String> param = new HashMap<String, String>();
			for (int i = 0; i < arg.length; i++) {
				String[] numArray = arg[i].split("[*]");
				buffer.append(numArray[numArray.length - 1] + ",");
			}
			// 去除重复的线芯直径
			value = buffer.toString().substring(0, buffer.toString().length() - 1);
			buffer.delete(0, buffer.toString().length());
			for (String s : value.split(",")) {
				param.put(s, s);
			}
			for (Map.Entry<String, String> m : param.entrySet()) {
				buffer.append(m.getValue() + ",");
			}
			return buffer.toString().substring(0, buffer.toString().length() - 1);
		} catch (Exception e) {
			return null;
		}
	}
}
