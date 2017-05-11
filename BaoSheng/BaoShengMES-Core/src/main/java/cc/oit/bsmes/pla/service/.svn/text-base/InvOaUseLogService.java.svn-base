package cc.oit.bsmes.pla.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.InvOaUseLog;

import java.util.List;

public interface InvOaUseLogService extends BaseService<InvOaUseLog>{
	
	/**
	 * 查询工序使用库存冲抵日志
	 * @author JinHanyun
	 * @date 2014-1-6 下午4:07:20
	 * @param refId
	 * @return
	 * @see
	 */
	public List<InvOaUseLog> getByRefId(String refId);

    /**
     * 取消冲抵
     * @param proDecId
     * @param updator
     */
    public void cancelOffSet(String proDecId,String updator);

}
