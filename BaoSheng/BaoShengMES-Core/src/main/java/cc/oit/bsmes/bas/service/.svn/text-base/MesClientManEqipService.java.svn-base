package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.MesClientManEqip;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;
import cc.oit.bsmes.wip.model.Receipt;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface MesClientManEqipService extends BaseService<MesClientManEqip> {
	/**
	 * 查询终端关联的设备信息
	 * @author JinHanyun
	 * @date 2014-2-20 下午2:05:19
	 * @param mac 终端mac地址
	 * @param ip  终端IP
	 * @param loadAttrs
     * @return
	 * @see
	 */
	public List<MesClientEqipInfo> getByMesClientMac(String mac, String ip, boolean loadAttrs);

    public MesClientEqipInfo findByMesClientMac(String equipCode);
    
    public List<MesClientManEqip> findByRequestMap(String mesClientId, int start,int limit);
    
    public Integer countByRequestMap(String mesClientId);
    
    public MesClientManEqip getByMesClientIdAndEqipId(String mesClientId,String eqipId);

    public Integer getMiddleCheckInterval();

    public List<Map<String,String>> emphReceipt(String processId, String productLineCode) ;
}

