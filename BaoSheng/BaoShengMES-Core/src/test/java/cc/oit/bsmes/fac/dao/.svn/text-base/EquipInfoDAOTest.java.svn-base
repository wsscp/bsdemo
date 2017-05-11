/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */
package cc.oit.bsmes.fac.dao;


import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.ProductEquip;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-1-16 下午6:06:53
 * @since
 * @version
 */
public class EquipInfoDAOTest extends BaseTest {
	@Resource
	private EquipInfoDAO equipInfoDAO;
	@Resource
	private ProductEquipDAO productEquipDAO;
	@Resource
	private EquipInfoService equipInfoService;

	@Test
	@Rollback(false)
	public void testInsert() {
		EquipInfo findParams = new EquipInfo();
		findParams.setType(EquipType.MAIN_EQUIPMENT);
		findParams.setOrgCode("bstl01");
		List<EquipInfo> list = equipInfoDAO.find(findParams);
		for (EquipInfo equipInfo : list) {
			EquipInfo equipInfo1 = equipInfoService.getEquipLineByEquip(equipInfo.getCode());
			equipInfo1.setEquipAlias(equipInfo.getEquipAlias());
			equipInfoDAO.update(equipInfo1);
		}
	}
	
	@Test
	public void testLazy() {
		EquipInfo findParams = new EquipInfo();
		findParams.setCode("LINE-JC1-0001");
		EquipInfo ei = equipInfoDAO.getOne(findParams);
		System.out.println(ei.getEquipCalendar());
		System.out.println(ei.getEquipCalendar().size());
	}

	@Test
	@Rollback(false)
	public void equipTest(){
		EquipInfo findParams = new EquipInfo();
		findParams.setType(EquipType.PRODUCT_LINE);
		List<EquipInfo> list = equipInfoDAO.get(findParams);
		for (EquipInfo equipInfo : list) {
			ProductEquip equip = new ProductEquip();
			equip.setProductLineId(equipInfo.getId());
			List<ProductEquip> productEquips = productEquipDAO.get(equip);
			for (int i = 0; i < productEquips.size(); i++) {
				if(i > 0){
					ProductEquip pe = productEquips.get(i);
					productEquipDAO.delete(pe.getId());
					equipInfoDAO.delete(pe.getEquipId());
				}
			}
		}
	}
}
