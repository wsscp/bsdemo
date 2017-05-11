package cc.oit.bsmes.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.oacalculator.OACalculatorHandler;

/**
 * 计算OA的junit测试
 */
public class CalculatorOAJunit extends BaseTest {
	@Resource
	private OACalculatorHandler oACalculatorHandler;

	public String orgCode = "bstl01";

	/**
	 * 计算OA - 准备：分解
	 * */
	@Test
	@Rollback(false)
	public void analysisOrderToProcess() {
		try {
			oACalculatorHandler.analysisOrderToProcess(orgCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 计算OA - 主要：计算方法
	 * */
	@Test
	@Rollback(false)
	public void calculatorOA() {
		try {
			oACalculatorHandler.calculatorOA(orgCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 计算OA - 先分解、然后计算
	 * */
	@Test
	@Rollback(false)
	public void analysisCalculatorOA() {
		try {
			oACalculatorHandler.analysisOrderToProcess(orgCode);
			oACalculatorHandler.calculatorOA(orgCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
