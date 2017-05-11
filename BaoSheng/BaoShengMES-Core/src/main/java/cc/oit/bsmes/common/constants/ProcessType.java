package cc.oit.bsmes.common.constants;

public enum ProcessType {
	
	SMS("短信"),MESSAGE("系统消息"),EMAIL("邮件"),SMSPLUSMESSAGE("短息，系统消息"),EMAILPLUSMESSAGE("邮件，系统消息"),SMSPLUSEMAIL("短信，邮件") ,ALL("短信，邮件，系统消息");
	
	private String processType;
 

	private ProcessType(String processType) {
		this.processType = processType; 
	}
	 
	@Override
	public String toString() {
		return processType;
	}
	 
}
