package org.apache.ibatis.session;

import org.apache.ibatis.session.Configuration.StrictMap;

/**
 * For Mybatis。当map中已存在key时忽略之后put的value。
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月16日 上午10:36:19
 * @since
 * @version
 */
public class FirstStrictMap<V> extends StrictMap<V> {

	private static final long serialVersionUID = 6961434644645612608L;

	public FirstStrictMap(String name) {
		super(name);
	}

	public V put(String key, V value) {
		if (containsKey(key)) {
			return get(key);
		}
		return super.put(key, value);
	}

}
