package cc.oit.bsmes.common.util;

/**
 * 全局变量类：单例模式
 * */
public class GlobalVariableClass {

	private boolean scheduleJobIsRun = false; // 排程任务是否正真进行中
	private boolean instanceJobIsRun = false; // 工序实例任务是否正真进行中
	
	private static class SingletonClassInstance {
		private static final GlobalVariableClass instance = new GlobalVariableClass();
	}

	public static GlobalVariableClass getInstance() {
		return SingletonClassInstance.instance;
	}

	private GlobalVariableClass() {

	}

	public boolean isScheduleJobIsRun() {
		return scheduleJobIsRun;
	}

	public void setScheduleJobIsRun(boolean scheduleJobIsRun) {
		this.scheduleJobIsRun = scheduleJobIsRun;
	}

	public boolean isInstanceJobIsRun() {
		return instanceJobIsRun;
	}

	public void setInstanceJobIsRun(boolean instanceJobIsRun) {
		this.instanceJobIsRun = instanceJobIsRun;
	}
	
}