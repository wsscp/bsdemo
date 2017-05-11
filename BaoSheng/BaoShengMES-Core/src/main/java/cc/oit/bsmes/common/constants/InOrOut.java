package cc.oit.bsmes.common.constants;

public enum InOrOut {
	IN("投入"),OUT("产出");
	
	private String inOrOut;

	private InOrOut(String inOrOut) {
		this.inOrOut = inOrOut;
	}

	//判断投入还是产出
	public boolean chackInOrOut(){
		if(inOrOut.equals(IN.inOrOut)){
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return inOrOut;
	}
}
