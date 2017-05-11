package cc.oit.bsmes.bas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.dao.DataDicDAO;
import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;

@Service
public class DataDicServiceImpl extends BaseServiceImpl<DataDic> implements DataDicService{

	@Resource
	private DataDicDAO dataDicDAO;
	
	@Override
	public DataDic getByDicCode(String termsCode,String code) {
		return dataDicDAO.getByDicCode(termsCode,code);
	}

	@Override
	public List<DataDic> getCodeByTermsCode(TermsCodeType type) {
		DataDic findParams = new DataDic();
		findParams.setTermsCode(type.name());
		findParams.setStatus("1"); // 正常状态的
		return dataDicDAO.get(findParams);
	}

	@Override
	public DataDic geByKeyK(String keyK) {
		return dataDicDAO.geByKeyK(keyK);
	}
	
	/**
	 * 根据词条类型和扩展属性查询词条详细信息
	 * @author DingXintao
	 * @date 2014-6-10 下午5:30:00
	 * @param type 词条类型
	 * @param extatt 扩展属性
	 * @return
	 */
	@Override
	public List<DataDic> getCodeByTermsCodeAndExtatt(TermsCodeType type, String extatt) {
		DataDic findParams = new DataDic();
		findParams.setTermsCode(type.name());
		findParams.setExtatt(extatt);
		findParams.setStatus("1"); // 正常状态的
		return dataDicDAO.getCodeByTermsCodeAndExtatt(findParams);
	}

    @Override
    public List<DataDic> getValidByTermsCode(String termsCode) {
        return dataDicDAO.getValidByTermsCode(termsCode);
    }
}
