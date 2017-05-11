package cc.oit.bsmes.wip.service.impl;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.ProductStatusDAO;
import cc.oit.bsmes.wip.model.ProductStatus;
import cc.oit.bsmes.wip.service.ProductStatusService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-3 下午12:05:13
 * @since
 * @version
 */
@Service
public class ProductStatusServiceImpl extends BaseServiceImpl<ProductStatus> implements ProductStatusService {
 
	@Resource private ProductStatusDAO productStatusDAO;
	

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author leiwei
	 * @date 2014-3-4 上午11:03:08
	 * @param param
	 * @param start
	 * @param limit
	 * @return 
	 * @see cc.oit.bsmes.wip.service.ProductStatusService#getProductionProcess(cc.oit.bsmes.wip.model.ProductStatus, int, int)
	 */
	@Override
	public List<ProductStatus> getProductionProcess(ProductStatus param,
			int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return productStatusDAO.getProductionProcess(param);
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author leiwei
	 * @date 2014-3-4 上午11:58:37
	 * @param param
	 * @return 
	 * @see cc.oit.bsmes.wip.service.ProductStatusService#countTotalProcess(cc.oit.bsmes.wip.model.ProductStatus)
	 */
	@Override
	public Integer countTotalProcess(ProductStatus param) {
		return productStatusDAO.countTotalProcess(param);
	}
	
	 @Override
	public List<ProductStatus> findForExport(JSONObject json) {
		 ProductStatus productStatus=JSON.toJavaObject(json, ProductStatus.class);
		 List<ProductStatus> list=null;
		 if(StringUtils.equalsIgnoreCase("productProcessTrace", productStatus.getSpecification())){
			 list=productStatusDAO.getProductionProcess(productStatus);
		 }else{
			 list = productStatusDAO.find(productStatus);
				for(int i=0;i<list.size();i++){
					ProductStatus status=list.get(i);
					if(productStatus.getCol()!=null){
						list.get(i).setColor(status.getCol().toString());
					}
				}
		 }
		 return list;
	}
	@Override
	public List<ProductStatus> getProcessReport(ProductStatus productStatus) {
		return productStatusDAO.getProcessReport(productStatus);
	}
	@Override
	public Integer countTotalProcessReport(ProductStatus productStatus) {
		return productStatusDAO.countTotalProcessReport(productStatus);
	}

}
