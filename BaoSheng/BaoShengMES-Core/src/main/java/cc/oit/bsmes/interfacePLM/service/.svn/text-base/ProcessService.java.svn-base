package cc.oit.bsmes.interfacePLM.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.Process;


/**
 * ProcessService
 * <p style="display:none">产品工艺流程Service</p>
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
public interface ProcessService extends BaseService<Process> {
	
	/**
	 * MES 同步 PLM 工艺流程数据
	 */
	public void asyncData();

	/**
	 * MES 同步 PLM 工艺流程数据 <br/>
	 * 根据工艺 ID单个工艺同步
	 * 
	 * @author DingXintao
	 * @param prcvId 工艺ID
	 * @return
	 */
	public void asyncData(String prcvId);
	
    /**
	 * 获取PLM更新过的数据
	 * 
	 * @param @param craftsId 工艺ID
	 * @return List<Process>
	 */
	public List<Process> getAsyncDataList(String craftsId);
	
	public void insertBatch(List<Process> param);


}
