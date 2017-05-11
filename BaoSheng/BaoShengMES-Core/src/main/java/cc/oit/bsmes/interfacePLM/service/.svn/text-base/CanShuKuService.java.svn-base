package cc.oit.bsmes.interfacePLM.service;

import java.util.Date;
import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.CanShuKu;
import cc.oit.bsmes.interfacePLM.model.Process;

/**
 * CanShuKuServiceImpl
 * <p style="display:none">
 * 参数库Service
 * </p>
 * 
 * @author DingXintao
 * @date 2014-12-16 13:34:34
 */
public interface CanShuKuService extends BaseService<CanShuKu> {

	public List<CanShuKu> getRealCanShuKu(Date lastExecuteTime);

	/**
	 * MES 同步 PLM 工艺参数数据 <br/>
	 * 根据工艺 ID单个工艺同步
	 * 
	 * @author DingXintao
	 * @param process
	 *            工序对象
	 * @return
	 */
	public void asyncData(Process process);

}
