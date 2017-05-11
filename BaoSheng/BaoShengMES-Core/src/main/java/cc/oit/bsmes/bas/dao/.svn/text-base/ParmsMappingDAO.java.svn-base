package cc.oit.bsmes.bas.dao;


import cc.oit.bsmes.bas.model.ParmsMapping;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 设备参数代码WW与MES映射表dao
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2013-12-16 上午9:24:51
 * @since
 * @version
 */
public interface ParmsMappingDAO extends BaseDAO<ParmsMapping>{ 

    List<Map<String,Object>> getParmsMapping();
    
    /**
     * 获取设备上该标签的警戒值
     * 
     * @param targetCode 标签名
     * @param equipCode 设备编码
     * @param orgCode 组织编码
     * */
    public String getEquipWWProductValue(String targetCode, String equipCode, String orgCode);
	 
}
