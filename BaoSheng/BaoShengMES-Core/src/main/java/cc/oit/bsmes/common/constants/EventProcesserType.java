package cc.oit.bsmes.common.constants;

public enum EventProcesserType {
	
	USER("用户"),ROLE("角色");
	
	private String processerType;
 

	private EventProcesserType(String processerType) {
		this.processerType = processerType; 
	}
	 
	@Override
	public String toString() {
		return processerType;
	}
	
 
}
