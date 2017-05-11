package cc.oit.bsmes.common.constants;

public enum MaintainStatus {

	FINISHED("已完成"),IN_PROGRESS("未完成");

	private String status;

	private MaintainStatus(String status){
		this.status = status;
	}
	
	@Override
	public String toString() {
		return status;
	}

}
