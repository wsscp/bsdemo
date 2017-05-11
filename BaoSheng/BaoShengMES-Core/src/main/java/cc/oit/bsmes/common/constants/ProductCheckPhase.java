package cc.oit.bsmes.common.constants;

public enum ProductCheckPhase {

	FIRST_CHECK("首检"),INSPECTION("巡检");
	
	private String phase;
	
	private ProductCheckPhase(String phase) {
		this.phase = phase;
	}
	
	@Override
	public String toString() {
		return phase;
	}
}
