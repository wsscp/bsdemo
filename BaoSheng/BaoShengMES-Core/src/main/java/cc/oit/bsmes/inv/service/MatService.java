package cc.oit.bsmes.inv.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.pla.model.Product;

/**
 * MatService
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author zhangdongpign
 * @date 2014-4-18 下午5:24:48
 * @since
 * @version
 */
public interface MatService extends BaseService<Mat> {

	List<Mat> getByMatCode(String matCode);

	public void synFinishedMat(Product product);

	Mat getByCode(String matCode);

	/**
	 * <p>
	 * 模糊查询列表：根据name或code
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<Mat>
	 * @see
	 */
	List<Mat> findByCodeOrName(String query, MatType matType);

	DataDic getColorByCode(String colorCode);

	DataDic getColorByName(String colorName);

	/**
	 * PLM工艺同步后对相关物料信息的颜色修改：前提，先要导入物料（修改火花半成品的颜色，取绝缘挤出的半成品颜色）
	 * 
	 * @author dingxt
	 * @param processId 工序ID
	 * */
	public void updateHHColorByJY(String processId);

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
	public List<Map<String, String>> getDescByMatName(String matName);
	
	public List<Mat> getMatsByOrderTask(String taskId);
	
	public List<Mat> getMatsByCustItemId(String custOrderItemId);

	public List<Mat> getMatNames();
	

}
