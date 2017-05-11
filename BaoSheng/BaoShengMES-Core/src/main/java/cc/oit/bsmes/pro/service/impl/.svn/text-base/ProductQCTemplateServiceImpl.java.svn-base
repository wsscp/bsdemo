package cc.oit.bsmes.pro.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.oit.bsmes.common.util.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import jxl.Cell;
import jxl.Sheet;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cc.oit.bsmes.common.constants.ProductQCStatus;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JxlUtils;
import cc.oit.bsmes.pla.dao.ProductDAO;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pro.dao.ProductQCTemplateDAO;
import cc.oit.bsmes.pro.model.ProductQCTemplate;
import cc.oit.bsmes.pro.service.ProductQCTemplateService;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-12 下午1:33:18
 * @since
 * @version
 */
@Service
public class ProductQCTemplateServiceImpl extends BaseServiceImpl<ProductQCTemplate> implements ProductQCTemplateService {
	@Resource 
	private ProductQCTemplateDAO productQCTemplateDAO;
	@Resource 
	private ProductDAO  productDAO;

	@Override
	public ProductQCTemplate getByNameAndProductCode(String name,String productCode) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", name);
		map.put("productCode", productCode);
		return productQCTemplateDAO.getByNameAndProductCode(map);
	}

	@Override
	public void importQcTemp(Sheet sheet, String orgCode) {
		 int maxRow = sheet.getRows();
		List<ProductQCTemplate> list =new ArrayList<ProductQCTemplate>();
		 for(int i =3; i < maxRow; i++) {
			 ProductQCTemplate object=new ProductQCTemplate(); 
			 Cell[] cells = sheet.getRow(i); 
			 String exName=JxlUtils.getRealContents(cells[1]);
			 if(StringUtils.isEmpty(exName))
			 {
				 break;
			 }
			 object.setName(exName);
			 object.setPreProcess(JxlUtils.getRealContents(cells[2]));
			 object.setEnvironmentParameter(JxlUtils.getRealContents(cells[3]));
			 object.setEnvironmentValue(JxlUtils.getRealContents(cells[4]));
			 object.setMatRequest(JxlUtils.getRealContents(cells[5]));
			 object.setEquipRequest(JxlUtils.getRealContents(cells[6]));
			 object.setCharacterDesc(JxlUtils.getRealContents(cells[7]));
			 object.setCharacterValue(JxlUtils.getRealContents(cells[8]));
			 object.setRefContent(JxlUtils.getRealContents(cells[9]));
			 object.setOrgCode(orgCode);
			 object.setCreateTime(new Date());
			 object.setCreateUserCode("admin");
			 object.setModifyTime(new Date());
			 object.setModifyUserCode("admin");
			 object.setStatus(ProductQCStatus.VALID); 
			 list.add(object); 
		 }
		 
		List<Product> allProduct = productDAO.getAll();
		if(CollectionUtils.isEmpty(allProduct))
		{
			return ;
		}
		for(int j=0;j<list.size();j++)
		{
			ProductQCTemplate object = list.get(j);
			for(int k=0;k<allProduct.size();k++)
			{
				Product pro = allProduct.get(k);
				object.setProductCode(pro.getProductCode());
				object.setId(null);
				super.insert(object); 
			}
			
		}
		
		 
		
	}

    @Override
    public List<ProductQCTemplate> findForExport(JSONObject queryFilter) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        ProductQCTemplate findParams = (ProductQCTemplate) JSONUtils.jsonToBean(queryFilter,ProductQCTemplate.class);
        return productQCTemplateDAO.find(findParams);
    }
}
