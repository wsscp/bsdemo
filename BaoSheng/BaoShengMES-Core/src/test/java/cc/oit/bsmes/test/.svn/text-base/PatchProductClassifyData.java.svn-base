package cc.oit.bsmes.test;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.interfacePLM.model.PLMProduct;
import cc.oit.bsmes.interfacePLM.service.PLMProductService;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;

/**
 * 补充产品分类数据 T_PLA_PRODUCT_CLASSIFY
 */
public class PatchProductClassifyData extends BaseTest {
	@Resource
	private PLMProductService pLMProductService;
	@Resource
	private ProductService productService;

	/**
	 * bean初始化后执行的方法
	 * */
	@PostConstruct
	public void initMethod() {
	}

	/**
	 * 执行函数
	 * */
	@Test
	@Rollback(false)
	public void process() throws BiffException, IOException {
		List<PLMProduct> products = pLMProductService.getLastRecord(null);
		for (PLMProduct pLMProduct : products) {
			if (StringUtils.isBlank(pLMProduct.getNo()) || StringUtils.isBlank(pLMProduct.getName())) {
				continue;
			}
			Product product = productService.getByProductCode(pLMProduct.getNo());
			if (product != null) {
				product.setClassifyId(pLMProduct.getCatanid()); // 分类ID
				productService.update(product);
			}
		}
	}

}
