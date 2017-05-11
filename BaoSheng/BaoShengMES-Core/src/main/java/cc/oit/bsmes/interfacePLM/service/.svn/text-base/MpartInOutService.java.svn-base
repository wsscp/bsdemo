package cc.oit.bsmes.interfacePLM.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.MpartInOut;

/**
 * MpartInOutService
 * <p style="display:none">流程投入产出Service</p>
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
public interface MpartInOutService extends BaseService<MpartInOut> {

    public List<MpartInOut> getRealMpart(Date lastDate);
    

	/**
	 * MES 同步 PLM 工序投入产出数据 <br/>
	 * 根据工艺序ID同步下面的投入产出
	 * 
	 * @author DingXintao
	 * @param processId
	 *            工序ID
	 * @return
	 */
	public void asyncData(String processId);
	
	public void initTemplet();
	
	public List<MpartInOut> getAllMpartByProductId(String productId);
	
	public List<MpartInOut> getMpartByName(String name);
	
	//根据物料名称获取特定的单丝属性值
	public Map<String,String> getFieldsByMpartNo(String mpartNo);
	
	//根据物料名称获取特定的导体属性值
	public Map<String,String> getFieldsByMpartNoDT(String mpartNo);
	
}
