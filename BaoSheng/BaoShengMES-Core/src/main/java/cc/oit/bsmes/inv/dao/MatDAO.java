package cc.oit.bsmes.inv.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.inv.model.AssistOption;
import cc.oit.bsmes.inv.model.Mat;

/**
 * MatDAO
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月31日 下午1:59:12
 * @since
 * @version
 */
public interface MatDAO extends BaseDAO<Mat> {

    List<Mat> getByMatCode(String matCode);
    
    List<Mat> getAllByMatCode(Map<String, Object> findParams);
   
    /**
     * <p>模糊查询列表：根据name或code</p> 
     * @author DingXintao
     * @date 2014-6-30 9:56:48
     * @param param 参数
     * @return List<Mat>
     * @see
     */
    List<Mat> findByCodeOrName(Map<String, Object> param);
    
    /**
     * PLM工艺同步后对相关物料信息的颜色修改：前提，先要导入物料（修改火花半成品的颜色，取绝缘挤出的半成品颜色）
     * @author dingxt
     * @param processId 工序ID
     * */
    public void updateHHColorByJY(String processId);
    
    public List<Mat> getMatName(String workOrderNo, String processCode);
    
    public List<AssistOption> getAssistOp(String processCode);
    
    /**
	 * 获取所有物料名称，去重
	 * 
	 * @author DingXintao
	 * */
	public List<Map<String, String>> getAllMatName();
	
	/**
	 * 获取该物料名称下的所有描述种类
	 * 
	 * @author DingXintao
	 * @param matName 物料名称
	 * */
	public List<Map<String, String>> getDescByMatName(Map param);
	
	public List<Map<String,String>> getPropValue(Map<String,String> param);
	
	public void insertMatPro(Map<String,String> param);
	
	public List<Map<String,Object>> getBZSemiProducts(String taskId);
	
	public List<Mat> getMatsByOrderTask(String taskId);
	
	public List<Map<String,Object>> getBZMaterialProps(String matCode);
	
	public List<Map<String,Object>> getBZOutSemiProducts(String taskId);
	
	public List<Mat> getMatsByCustItemId(String custOrderItemId);
	
	public Map<String,String> getSemiOutColors(String custOrderItemId);

	public List<Mat> getMatNames();
}

