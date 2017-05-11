package cc.oit.bsmes.common.service.impl;

import cc.oit.bsmes.bas.dao.ParamConfigDAO;
import cc.oit.bsmes.bas.dao.PropConfigDAO;
import cc.oit.bsmes.bas.model.ParamConfig;
import cc.oit.bsmes.bas.model.PropConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 读取配置信息
 * <p style="display:none">modifyRecord</p>
 *
 * @author zhangdongping
 * @date 2014-3-5 上午9:59:40
 */
@Service
public class DatabasePropertyLoaderStrategy {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private PropConfigDAO propConfigDAO;
    @Resource
    private ParamConfigDAO paramConfigDAO;

    public Map<String, String> loadProperties() {
        logger.trace("entering...");
        PropConfig findParams = new PropConfig();
        findParams.setStatus(Boolean.TRUE);
        Map<String, String> maps = new HashMap<String, String>();
        List<PropConfig> alist = propConfigDAO.get(findParams);
        for (PropConfig prop : alist) {
            maps.put(prop.getKeyK(), prop.getValueV());
        }
        return maps;
    }

    public Map<String, String> loadParams() {
        logger.trace("entering...");
        ParamConfig findParams = new ParamConfig();
        findParams.setStatus(Boolean.TRUE);
        Map<String, String> maps = new HashMap<String, String>();
        List<ParamConfig> alist = paramConfigDAO.get(findParams);
        for (ParamConfig prop : alist) {
            maps.put(prop.getOrgCode() + prop.getCode(), prop.getValue());
        }
        return maps;
    }


}
