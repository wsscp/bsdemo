package cc.oit.bsmes.interfacePLM.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.Bzgf;

/**
 * BzgfService
 * <p style="display:none">产品工艺参数Service</p>
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
public interface BzgfService extends BaseService<Bzgf> {
	
    /**
	 * 获取PLM更新过的数据
	 * 
	 * @param @param processId 工序Id
	 * @return List<Bzgf>
	 */
	public List<Bzgf> getAsyncDataList(String processId);

}
