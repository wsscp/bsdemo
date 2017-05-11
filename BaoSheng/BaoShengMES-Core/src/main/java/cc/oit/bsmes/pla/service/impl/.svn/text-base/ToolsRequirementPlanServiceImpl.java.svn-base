package cc.oit.bsmes.pla.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import cc.oit.bsmes.common.util.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pla.dao.ToolsRequirementPlanDAO;
import cc.oit.bsmes.pla.model.ToolsRequirementPlan;
import cc.oit.bsmes.pla.service.ToolsRequirementPlanService;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-1-22 下午6:06:27
 * @since
 * @version
 */
@Service
public class ToolsRequirementPlanServiceImpl extends BaseServiceImpl<ToolsRequirementPlan> implements ToolsRequirementPlanService {
	@Resource ToolsRequirementPlanDAO toolsRequirementPlanDAO;

	@Override
	public List<ToolsRequirementPlan> findByOrgCode(String orgCode) {
		return toolsRequirementPlanDAO.findByOrgCode(orgCode);
	}
	@Override
	public List<ToolsRequirementPlan> getByWorkOrderNo(String workOrderNo) {
		return toolsRequirementPlanDAO.getByWorkOrderNo(workOrderNo);
	}

    @Override
    public List<ToolsRequirementPlan> findForExport(JSONObject queryFilter){
        ToolsRequirementPlan findParams = (ToolsRequirementPlan) JSONUtils.jsonToBean(queryFilter,ToolsRequirementPlan.class);
        return toolsRequirementPlanDAO.find(findParams);
    }

    @Override
    public int countForExport(JSONObject queryParams) {
        ToolsRequirementPlan findParams = (ToolsRequirementPlan) JSONUtils.jsonToBean(queryParams,ToolsRequirementPlan.class);
        return  toolsRequirementPlanDAO.count(findParams);
    }
}
