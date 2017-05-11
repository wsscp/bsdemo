package cc.oit.bsmes.common.constants;

public enum WeekCalendarType {
	SUN("周日","7"),MON("周一","1"),TUE("周二","2"),WED("周三","3"),THI("周四","4"),FRI("周五","5"),SAT("周六","6");
	
	private String day ;
	private String order;
	
	private WeekCalendarType(String day,String order){
		this.day = day;
		this.order = order;
	}
	
	@Override
	public String toString(){
		return day;
	}
	
	public String getOrder(){
		return order;
	}
}
