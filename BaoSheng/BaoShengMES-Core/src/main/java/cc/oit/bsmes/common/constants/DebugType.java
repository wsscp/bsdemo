package cc.oit.bsmes.common.constants;

/**
 * 调试原因
 * @author chanedi
 * @date 2014年2月14日 下午1:42:36
 * @since
 * @version
 */
public enum DebugType {

	COLOR("换颜色"),HEAD("换机头");
	
	private String type;

	private DebugType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
	
}
