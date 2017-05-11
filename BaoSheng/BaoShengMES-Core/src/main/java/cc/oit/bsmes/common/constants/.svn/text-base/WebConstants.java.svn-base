package cc.oit.bsmes.common.constants;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public abstract  class WebConstants {

    public static void main(String[] args) {
        System.out.println("fe");
    }

    public static final String ROOT_ID = "-1";
    
    public static final String END_WO_ORDER = "99"; // 表示最后一道生产单:最后一道工序:T_WIP_WORK_ORDER:NEXT_SECTION:99

	public static final String TILES_VIEW_LAYOUT_W = "w_";

	public static final String YES = "1";
	
	public static final String NO = "0";

	public static final String USER_SESSION_KEY="userSessionKey";
	public static final String USER_ROLES_SESSION_KEY="userRolesSessionKey";
	public static final String USER_RESOURCES_SESSION_KEY="userResourcesSessionKey";
	public static final String USER_RESOURCES_FOR_PAGE="resources";
	public static final int COOKIES_MAX_AGE = -1; //-1为内存cookie（负数为内存cookie）关闭浏览器,就自动清除
	
	public static final String REQUEST_PARAM_LIST_SEPARATOR = ",";

	public static final String ROLE_TYPE_GROUPLEADER = "1"; // TODO
															// 用户角色类型（1:组长级别）

    public static final String WASTE_PERCENT = "wastePercent"; //浪费百分比 0.1 0.2 0.12 。。。。

    public static final String WASTE_LENGTH  = "wasteLength"; //浪费长度 50 60 ... 在这个范围内允许进行冲抵

    public static final String MIDDLE_CHECK_INTERVAL = "middleCheckInterval"; //设置设备中间检时间间隔 输入 分钟 60 30 。。。

    public static final String MAX_EXPORT_LINE = "maxExportLine";//最大导出行数

    public static final String FAC_ALRM_TIMELATETOALARM= "fac.alarm.timeLateToAlarm"; //实际和计划完成时间差异的阀值

    public static final DecimalFormat DOUBLE_DF = new DecimalFormat("#.00");
    public static final DecimalFormat INT_DF = new DecimalFormat("#");

    public static final String TL_WAREHOUSE_CODE = "BSTL"; //特缆仓库CODE
    
    public static final String PAUSE_ROLE = "PAUSEORDER";

    /**
     * R_HotAD	热态外径平均值
     * R_ColdAD	冷态外径平均值
     * 前台每次采集工艺数据时，需要采集这2个值
     */
    public static Map<String,String> receiptCodes = new HashMap<String, String>();

    static {
        receiptCodes.put("R_HotAD","热态外径平均值");
        receiptCodes.put("R_ColdAD","冷态外径平均值");
    }
    
}
