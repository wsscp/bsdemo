package cc.oit.bsmes.bas.dao;

import java.util.List;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.common.dao.BaseDAO;

public interface DataDicDAO extends BaseDAO<DataDic>{

	/**
	 * 根据词条编号查询词条详细信息
	 * @param code
	 * @return
	 */
	public DataDic getByDicCode(String termsCode,String code);
	
	public DataDic geByKeyK(String keyK);

    public List<DataDic> getValidByTermsCode(String termsCode);

	/**
	 * 根据词条类型和扩展属性查询词条详细信息
	 * 
	 * @author DingXintao
	 * @date 2014-6-10 下午5:30:00
	 * @param DataDic param 条件
	 * @return
	 */
	public List<DataDic> getCodeByTermsCodeAndExtatt(DataDic param);
}
