package cc.oit.bsmes.common.constants;

public enum ProductQCStatus {
	VALID("有效"),INVALID("无效");
	
	private String status;
	
	private ProductQCStatus(String status){
		this.status=status;
	}
	
	@Override
	public String toString() {
		return status;
	}
}
