package cc.oit.bsmes.common.constants;

public enum MesStatus {
	SUCCESS("发送成功"),SENDED("已处理"),NEW("未发送");
	
	private String status;
	private MesStatus(String status){
		this.status = status;
	}
	
	public String getStatus(){
		return this.status;
	}
	
}
