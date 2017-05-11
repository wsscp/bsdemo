package cc.oit.bsmes.interfaceWebClient.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfaceWebClient.model.FacSummary;

/**
 *  
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2015-5-19 下午1:04:50
 * @since
 * @version
 */
public interface FacSummaryService extends BaseService<FacSummary>{
	
	public List<FacSummary> getFacSummary(FacSummary parm) ;
}
