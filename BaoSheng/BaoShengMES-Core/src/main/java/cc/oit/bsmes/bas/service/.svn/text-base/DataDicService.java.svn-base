package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.service.BaseService;

import java.util.List;

public interface DataDicService extends BaseService<DataDic>{

	/**
	 * 根据词条编号查询词条详细信息
	 * @param termsCode
	 * @param code
	 * @return
	 */
	
	public DataDic getByDicCode(String termsCode,String code);
	
	/**
	 * 根据词条类型查询词条详细信息
	 * @param type 词条类型
	 * @param extatt 扩展属性
	 * @return
	 */
	public List<DataDic> getCodeByTermsCode(TermsCodeType type);
	
	/**
	 * 根据词条类型和扩展属性查询词条详细信息
	 * @author DingXintao
	 * @date 2014-6-10 下午5:30:00
	 * @param type 词条类型
	 * @param extatt 扩展属性
	 * @return
	 */
	public List<DataDic> getCodeByTermsCodeAndExtatt(TermsCodeType type, String extatt);
	
	public DataDic geByKeyK(String keyK);

    public List<DataDic> getValidByTermsCode(String termsCode);
}
