package cc.oit.bsmes.interfacePLM.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.BzgfDAO;
import cc.oit.bsmes.interfacePLM.model.Bzgf;
import cc.oit.bsmes.interfacePLM.service.BzgfService;

/**
 * BzgfServiceImpl
 * <p style="display:none">产品工艺参数Service实现类</p>
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
@Service
public class BzgfServiceImpl extends BaseServiceImpl<Bzgf> implements BzgfService {
	@Resource
	private BzgfDAO bzgfDAO;

	/**
	 * 获取PLM更新过的数据
	 * 
	 * @param @param processId 工序Id
	 * @return List<Bzgf>
	 */
	@Override
	public List<Bzgf> getAsyncDataList(String processId) {
		List<Bzgf> list = bzgfDAO.getAsyncDataList(processId);
		if(null == list){
			return new ArrayList<Bzgf>();
		}
		return list;
	}

}
