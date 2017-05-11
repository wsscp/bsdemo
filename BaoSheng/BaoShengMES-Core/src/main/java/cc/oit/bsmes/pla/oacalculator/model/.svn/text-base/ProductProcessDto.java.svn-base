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
package cc.oit.bsmes.pla.oacalculator.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.pro.model.ProductProcess;

/**
 * ProductProcessDto
 * @author DingXintao
 * @date 2015-08-28 上午9:19:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ProductProcessDto extends ProductProcess{
	private static final long serialVersionUID = 3384144300152340132L;
	private ProductProcessDto nextProcess;
	private List<ProductProcessDto> preProcesses;
	private List<EquipInfo> availableEquips;
}
