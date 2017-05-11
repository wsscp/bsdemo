package cc.oit.bsmes.inv.service.impl;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.inv.dao.MatDAO;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pla.model.Product;

import org.apache.xmlbeans.impl.regex.REUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatServiceImpl extends BaseServiceImpl<Mat> implements MatService {
	@Resource
	private MatDAO matDAO;

	@Override
	public List<Mat> getByMatCode(String matCode) {

		return matDAO.getByMatCode(matCode);
	}

	@Override
	public Mat getByCode(String matCode) {
		Mat findParams = new Mat();
		findParams.setMatCode(matCode);
		List<Mat> matList = matDAO.getByMatCode(matCode);
		return matList.size() > 0 ? matList.get(0) : null;
	}

	/**
	 * <p>
	 * 模糊查询列表：根据name或code
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @param query
	 * @param matType 材料类型
	 * @return List<Mat>
	 * @see
	 */
	@Override
	public List<Mat> findByCodeOrName(String query, MatType matType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codeOrName", "%" + query + "%");
		if (matType != null) {
			param.put("matType", matType);
		} else {
			param.put("matType", null);
		}
		return matDAO.findByCodeOrName(param);
	}

	@Override
	public DataDic getColorByCode(String colorCode) {
		List<DataDic> list = StaticDataCache.getByTermsCode(TermsCodeType.DATA_PRODUCT_COLOR.name());
		for (DataDic dataDic : list) {
			if (dataDic.getCode().equals(colorCode)) {
				return dataDic;
			}
		}
		return null;
	}

	@Override
	public DataDic getColorByName(String colorName) {
		List<DataDic> list = StaticDataCache.getByTermsCode(TermsCodeType.DATA_PRODUCT_COLOR.name());
		for (DataDic dataDic : list) {
			if (dataDic.getName().equals(colorName)) {
				return dataDic;
			}
		}
		return null;
	}

	@Override
	public void synFinishedMat(Product product) {
		Mat mat = new Mat();
		mat.setMatCode(product.getProductCode());
		mat.setMatName(product.getProductName());
		mat.setMatType(MatType.FINISHED_PRODUCT);
		mat.setHasPic(false);
		mat.setIsProduct(true);
		mat.setTempletId("1");
		mat.setOrgCode("bstl01");
		List<Mat> mats = this.getByObj(mat);
		if (null != mats || mats.size() > 0) {
			this.delete(mats);
		}
		this.insert(mat);

	}

	/**
	 * PLM工艺同步后对相关物料信息的颜色修改：前提，先要导入物料（修改火花半成品的颜色，取绝缘挤出的半成品颜色）
	 * 
	 * @author dingxt
	 * @param processId 工序ID
	 * */
	@Override
	public void updateHHColorByJY(String processId) {
		matDAO.updateHHColorByJY(processId);
	}

	/**
	 * 获取所有物料名称，去重
	 * 
	 * @author DingXintao
	 * */
	public List<Map<String, String>> getAllMatName() {
		return matDAO.getAllMatName();
	}
	
	/**
	 * 获取该物料名称下的所有描述种类
	 * 
	 * @author DingXintao
	 * @param matName 物料名称
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, String>> getDescByMatName(String matName){
		Map param = new HashMap();
		param.put("matName", matName);
		return matDAO.getDescByMatName(param);
	}

	
	@Override
	public List<Mat> getMatsByOrderTask(String taskId) {
		return matDAO.getMatsByOrderTask(taskId);
	}

	@Override
	public List<Mat> getMatsByCustItemId(String custOrderItemId) {
		return matDAO.getMatsByCustItemId(custOrderItemId);
	}
	
	@Override
	public List<Mat> getMatNames(){
		return matDAO.getMatNames();
	};
}
