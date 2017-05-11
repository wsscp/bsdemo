package cc.oit.bsmes.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;



/**
 * net.sf.json.JSONArray工具类。
 * 
 * @author chanedi - 季景瑜
 */
public final class JSONArrayUtils {

	private static final String KEY_NAME = "value";
	private static final String VALUE_NAME = "text";

	/**
	 * 不允许创建实例。
	 */
	private JSONArrayUtils() {}

	/**
	 * 将枚举类转换为JSONArray。
	 * 
	 * @param enumClass
	 * @return JSONArray
	 */
	public static JSONArray enumToJSON(final Class<? extends Enum<?>> enumClass) {
		return enumToJSON(enumClass, KEY_NAME, VALUE_NAME);
	}

	/**
	 * 将枚举类转换为JSONArray。
	 * 
	 * @param enumClass
	 * @param keyName
	 * @param valueName
	 * @return JSONArray
	 */
	public static JSONArray enumToJSON(final Class<? extends Enum<?>> enumClass, String keyName,
			String valueName) {
		if (StringUtils.isEmpty(keyName)) {
			keyName = KEY_NAME;
		}
		if (StringUtils.isEmpty(valueName)) {
			valueName = VALUE_NAME;
		}

		final JSONArray jsonArray = new JSONArray();
		final Enum<?>[] constants = enumClass.getEnumConstants();
		for (final Enum<?> constant : constants) {
			final JSONObject o = new JSONObject();
			o.put(keyName, constant.name());
			o.put(valueName, constant.toString());
			jsonArray.add(o);
		}

		return jsonArray;
	}

	/**
	 * 将map转换为JSONArray。
	 * 
	 * @param map
	 * @return JSONArray
	 */
	@SuppressWarnings("rawtypes")
	public static JSONArray mapToJSON(final Map map) {
		return mapToJSON(map, KEY_NAME, VALUE_NAME);
	}

	/**
	 * 将map转换为JSONArray。
	 * 
	 * @param map
	 * @return JSONArray
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray mapToJSON(final Map map, String keyName, String valueName) {
		if (StringUtils.isEmpty(keyName)) {
			keyName = KEY_NAME;
		}
		if (StringUtils.isEmpty(valueName)) {
			valueName = VALUE_NAME;
		}

		final JSONArray jsonArray = new JSONArray();
		final Set<Entry> entrySet = map.entrySet();
		for (final Entry entry : entrySet) {
			final JSONObject o = new JSONObject();
			o.put(keyName, entry.getKey());
			o.put(valueName, entry.getValue());
			jsonArray.add(o);
		}
		return jsonArray;
	}
	
	/**
	 * 
	 * <p>方便Ext combombox组件显示</p> 
	 * @date 2014-3-26 下午3:13:17
	 * @param enumClass
	 * @return
	 * @see
	 */
	public static JSONArray enumToJSONForExt(final Class<? extends Enum<?>> enumClass){
		final JSONArray jsonArray = new JSONArray();
		final Enum<?>[] constants = enumClass.getEnumConstants();
		for (final Enum<?> constant : constants) {
			final JSONArray subJsonArray = new JSONArray();
			subJsonArray.add(constant.name());
			subJsonArray.add(constant.toString());
			jsonArray.add(subJsonArray);
		}
		return jsonArray;
	}
	
	/**
	 * AJAX请求响应JSON格式
	 * @date 2014-7-12 15:13:17
	 * @author DingXintao
	 * @param success 是否成功
	 * @param message 返回信息
	 * @param param 自定义参数
	 * @return JSON
	 */
	@SuppressWarnings("static-access")
	public static JSON ajaxJsonResponse(Boolean success, String message, Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
		map.putAll(param);
		JSONObject object = new JSONObject();
		JSON json = (JSON) object.toJSON(map);
		return json;
	}
	
	/**
	 * AJAX请求响应JSON格式
	 * @date 2014-7-12 15:13:17
	 * @author DingXintao
	 * @param success 是否成功
	 * @param message 返回信息
	 * @return JSON
	 */
	@SuppressWarnings("static-access")
	public static JSON ajaxJsonResponse(Boolean success, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
		JSONObject object = new JSONObject();
		JSON json = (JSON) object.toJSON(map);
		return json;
	}
	
}
