package cc.oit.bsmes.pro.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.pro.dao.ProductQCDetailDAO;
import cc.oit.bsmes.pro.dao.ProductQCResultDAO;
import cc.oit.bsmes.pro.model.ProductQCResult;
import cc.oit.bsmes.pro.service.ProductQCResultService;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-13 下午3:08:47
 * @since
 * @version
 */
@Service
public class ProductQCResultServiceImpl extends BaseServiceImpl<ProductQCResult> implements ProductQCResultService {

	@Resource
	private ProductQCResultDAO productQCResultDAO;
	@Resource
	private ProductQCDetailDAO productQCDetailDAO;
	
	public void deleteById(String id){
		if(productQCDetailDAO.deleteByResId(id)<1){
			throw new DataCommitException();
		}
		if (productQCResultDAO.delete(id)!= 1) {
			throw new DataCommitException();
		}
	}
	
	@Override
    public List<ProductQCResult> findForExport(JSONObject queryFilter) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		ProductQCResult findParams = (ProductQCResult)JSONUtils.jsonToBean(queryFilter,ProductQCResult.class);
       // addLike(findParams,ProductQCResult.class);
        return productQCResultDAO.find(findParams);
    }
}
