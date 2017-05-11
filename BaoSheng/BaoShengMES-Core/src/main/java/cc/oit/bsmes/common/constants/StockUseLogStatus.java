package cc.oit.bsmes.common.constants;

/**
 * 
 * 库存日志状态 
 * @author JinHanyun
 * @date 2014-1-7 上午9:36:29
 * @since
 * @version
 */
public enum StockUseLogStatus {
	
	USED("已使用"),UNUSED("未使用"),CANCELED("已取消");

	private String status;

	private StockUseLogStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
}
