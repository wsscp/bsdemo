package cc.oit.bsmes.common.view;

import cc.oit.bsmes.common.util.ReflectUtils;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * PropertyGrid for ext
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013-12-18 下午5:52:46
 * @since
 * @version
 */
public class PropertyGrid implements Serializable {

    private static final long serialVersionUID = 290079866600145694L;
    private Object bean;
	@Getter
	private List<Property> rows = new ArrayList<Property>();

	/**
	 * 添加表格行。
	 * 
	 * @param propName
	 *            属性名称。
	 * @param displayName
	 *            显示名称
	 * @param group
	 *            分组名称。
	 * @param editorType
	 *            编辑器类型。
	 * @param editorOptions
	 *            编辑器选项。
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void addRow(String propName) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method getter = ReflectUtils.getBeanGetter(bean.getClass(), propName);
		Object value = getter.invoke(bean);
		
		Property row = new Property();
		row.setName(propName);
		row.setValue(value);
		rows.add(row);
	}

	/**
	 * 确定接下来准备添加行的所属组和关注对象。
	 * 
	 * @param bean
	 *            关注对象。
	 * @param group
	 *            所属组。
	 */
	public void focusBean(final Object bean) {
		this.bean = bean;
	}

	@Data
	class Property {
		
		private String name;
		private Object value;

	}

}
