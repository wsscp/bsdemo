package cc.oit.bsmes.test;

import org.junit.Test;

import cc.oit.bsmes.common.util.QRCodeUtil;
import cc.oit.bsmes.junit.BaseTest;


/**
 * 补充生产单数据 OUT_ATTR_DESC IN_ATTR_DESC
 */
public class QRCreate extends BaseTest {
	//二维码扫描影射地址
	private static String URL="http://10.1.1.149:8088/bsmes/sin/mobile.action";
	//生成的二维码存放地址
	private static String SRC ="d:/barcode";
	
	@Test
	public void create() throws Exception  {
		QRCodeUtil.encode(URL, "", SRC, true);
	}
}
