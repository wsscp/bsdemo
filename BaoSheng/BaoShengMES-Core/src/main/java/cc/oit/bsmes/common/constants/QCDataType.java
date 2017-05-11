package cc.oit.bsmes.common.constants;

public enum QCDataType {
	
	STRING("字符串"),DOUBLE("数字型"),BOOLEAN("布尔型");
	
	private String type;
	 

	private QCDataType(String type) {
		this.type = type; 
	}
	 
	@Override
	public String toString() {
		return type;
	}


    public static QCDataType getByDesc(String desc){
        if("字符串".equals(desc)){
            return QCDataType.STRING;
        }else if("数字型".equals(desc)){
            return QCDataType.DOUBLE;
        }else if("布尔型".equals(desc)){
            return QCDataType.BOOLEAN;
        }else{
            return null;
        }
    }
}
