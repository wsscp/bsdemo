package cc.oit.bsmes.wip.service.impl;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.MaterialTraceDAO;
import cc.oit.bsmes.wip.model.MaterialTrace;
import cc.oit.bsmes.wip.service.MaterialTraceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 
 * TODO(原材物料追溯)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-5 下午2:52:06
 * @since
 * @version
 */
@Service
public class MaterialTraceServiceImpl extends BaseServiceImpl<MaterialTrace>
		implements MaterialTraceService {
	
	@Resource private MaterialTraceDAO materialTraceDAO;
	@Override
	public List<MaterialTrace> findForExport(JSONObject queryFilter) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
		MaterialTrace materialTrace=JSON.toJavaObject(queryFilter, MaterialTrace.class);
		return materialTraceDAO.find(materialTrace);
	}
}
