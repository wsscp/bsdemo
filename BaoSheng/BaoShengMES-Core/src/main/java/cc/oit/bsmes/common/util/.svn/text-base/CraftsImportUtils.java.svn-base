package cc.oit.bsmes.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JinHanyun on 2014/5/19 0019.
 */
public class CraftsImportUtils {
    /**
     * key  processName
     * value processCode
     */
    private static Map<String,String> map = new HashMap<String, String>();

    static{
        map.put("绝缘", "EXTRUSION_SINGLE");
        map.put("成缆", "CABLING");
        map.put("对绞", "CABLING");
        map.put("总屏", "ZONGPING");
        map.put("内护套", "JACKET_EXTRUSION");
        map.put("铠装", "KAIZHUANG");
        map.put("外护套", "JACKET_EXTRUSION");
        map.put("绕包", "TAPEWRAP");
        map.put("分屏", "FENPING");
        map.put("交联", "JIAOLIAN");
        map.put("屏蔽", "PINGBI");
        map.put("编织", "BRAIDING");
        map.put("配套", "MATCHING_RESPOOLING");
        map.put("铠装配套","KAIZHUANG_PT");
    }

    public static String getProcessCode(String processName){
        String processCode =  map.get(processName);
        if(StringUtils.isBlank(processCode)){
            throw new RuntimeException();
        }
        return processCode;
    }
}
