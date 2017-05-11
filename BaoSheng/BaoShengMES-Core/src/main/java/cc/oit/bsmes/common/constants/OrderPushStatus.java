package cc.oit.bsmes.common.constants;

public enum OrderPushStatus {
	TO_PUSH("待推送"),SUCCESS("推送成功"),FALSE("推送失败");
	
	private String status;

	private OrderPushStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
}
