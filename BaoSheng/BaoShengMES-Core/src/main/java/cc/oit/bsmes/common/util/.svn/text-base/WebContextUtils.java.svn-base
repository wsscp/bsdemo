package cc.oit.bsmes.common.util;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.exception.ErrorBasConfigException;
import cc.oit.bsmes.common.service.impl.DatabasePropertyLoaderStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chanedi on 14-3-13.
 */
@SuppressWarnings("unchecked")
public class WebContextUtils {

    private static volatile Map<String, String> propMap;
    private static volatile Map<String, String> paramMap;

    @SuppressWarnings("rawtypes")
    private static Map resultMap = new HashMap();
    private static final String RESULT_PROP_KEY = "propMap";
    private static final String RESULT_PARAM_KEY = "paramMap";
    private static final String TIME_KEY = "time";
    //数据超时时间 2小时
    private static final long expired_milliseconds = 1000 * 3600 * 2;

    static {
        resultMap.put(TIME_KEY, System.currentTimeMillis());
    }

    public static void init() {
        propMap = (Map<String, String>) resultMap.get(RESULT_PROP_KEY);
        paramMap = (Map<String, String>) resultMap.get(RESULT_PARAM_KEY);
        long oldtime = (Long) resultMap.get(TIME_KEY);
        long now = System.currentTimeMillis();
        if (propMap != null && paramMap != null && (now - oldtime) < expired_milliseconds) {
            return;
        }
        synchronized (WebContextUtils.class) {
            DatabasePropertyLoaderStrategy databasePropertyLoaderStrategy = (DatabasePropertyLoaderStrategy) ContextUtils.getBean(DatabasePropertyLoaderStrategy.class);
            propMap = databasePropertyLoaderStrategy.loadProperties();
            paramMap = databasePropertyLoaderStrategy.loadParams();
        }
        resultMap.put(RESULT_PROP_KEY, propMap);
        resultMap.put(RESULT_PARAM_KEY, paramMap);
        resultMap.put(TIME_KEY, System.currentTimeMillis());
    }

    public static String getPropValue(String key) {
        init();
        String value = propMap.get(key);
        if (value == null) {
            throw new ErrorBasConfigException("系统参数"+key+"没有找到");
        }
        return value;
    }

    public static String getSysParamValue(String key, String orgCode) {
        init();
        String value = paramMap.get(orgCode + key);
        if (value == null) {
            throw new ErrorBasConfigException("系统业务配置参数"+key+"没有找到");
        }
        return value;
    }

    public static int getSysParamIntValue(String key, String orgCode) {
        try {
            return Integer.parseInt(getSysParamValue(key, orgCode));
        } catch (NumberFormatException e) {
            throw new ErrorBasConfigException("系统业务配置参数"+key+"数据转换错误");
        }
    }

}
