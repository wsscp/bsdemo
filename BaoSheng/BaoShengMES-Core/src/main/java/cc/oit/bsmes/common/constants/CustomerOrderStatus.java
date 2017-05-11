package cc.oit.bsmes.common.constants;

public enum CustomerOrderStatus {
	
	//TO_CONFIRM("待确认"),
    TO_DO("未开始"), IN_PROGRESS("生产中"), CANCELED("已取消"), FINISHED("已完成");

	private String status;

	private CustomerOrderStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
	
}
