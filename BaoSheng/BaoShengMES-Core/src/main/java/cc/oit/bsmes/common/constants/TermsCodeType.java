package cc.oit.bsmes.common.constants;

public enum TermsCodeType {
	DATA_PROP_CONFIG("系统属性列表"),
    DATA_PARAM_CONFIG("系统业务参数列表"),
    EVENT_TYPE_DETAIL("事件类型详细定义"),
	DATA_EQUIP_MODEL("设备型号"),
	WORKORDER_PAUSE_REASON_DETAIL("生产单暂停原因详细定义"),
    DATA_WORK_ONOFF_TYPE("上下班类型"),
    EQUIP_TYPE("设备类型"),
    DATA_SECTION_EXCEPTION_TYPE("分段异常类型"),
    DATA_EMPLOYEE_CERTIFICATE("人员资质"),
    DATA_PRODUCT_COLOR("产品颜色"),
	SUB_TYPE("设备子类型"),
	SPARK_REPAIR_TYPE("火花修复方式"),
	SHUT_DOWN_REASON("停机原因"),
	APPLY_MAT_PROCESS_TYPE("工序要料类型"),
	SIN_PROCESS_DESC("新松工序分解");
	private String termsCode;

	private TermsCodeType(String termsCode) {
		this.termsCode = termsCode;
	}

	@Override
	public String toString() {
		return termsCode;
	}
}
