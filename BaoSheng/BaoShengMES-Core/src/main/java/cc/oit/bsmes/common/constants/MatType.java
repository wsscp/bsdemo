package cc.oit.bsmes.common.constants;

public enum MatType {
	
	MATERIALS("原材料"),SEMI_FINISHED_PRODUCT("半成品"),FINISHED_PRODUCT("成品");
	
	private String matType;

	private MatType(String matType) {
		this.matType = matType;
	}
	
	@Override
	public String toString() {
		return matType;
	}
	
}
