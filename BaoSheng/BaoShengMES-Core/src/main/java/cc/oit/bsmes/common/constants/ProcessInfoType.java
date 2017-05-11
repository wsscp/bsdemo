package cc.oit.bsmes.common.constants;

public enum ProcessInfoType {

	EXTRUSIONSINGLE("挤出-单层"), SHIELD("铠装"), BRAIDING("编织"), WRAPPING("绕包"), CABLING("成缆"), RESPOOL("火花配套"), IRRADITION(
			"辐照"), JACKETEXTRUSION("护套-挤出"), WRAPPINGYMD("云母带绕包两层"), TWISTING("单绞"), JY("绝缘"), CL("成缆"), HT("护套");

	private String processName;

	private ProcessInfoType(String processName) {
		this.processName = processName;
	}

	@Override
	public String toString() {
		return processName;
	}

}
