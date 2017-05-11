package cc.oit.bsmes.bas.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.bas.model.Amoeba;
import cc.oit.bsmes.common.service.BaseService;

/**
 * 阿米巴
 * @author 王国华
 * @date  2017-01-06 13:52:41
 */
public interface AmoebaService extends BaseService<Amoeba>{

	/**
	 * 导入阿米巴数据
	 * @param sheet
	 * @param result
	 * @throws Exception 
	 */
	void importAmoebaData(Sheet sheet, JSONObject result) throws Exception;

}
