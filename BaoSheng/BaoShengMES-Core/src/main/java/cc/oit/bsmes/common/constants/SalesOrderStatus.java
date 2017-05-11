package cc.oit.bsmes.common.constants;

public enum SalesOrderStatus {
	
	TO_DO("未开始"), IN_PROGRESS("进行中"), CANCELED("已取消"), FINISHED("已完成"),QUALIFIED("合格"),NOTQUALIFIED("未合格");

	private String status;

	private SalesOrderStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
	
}
