package cc.oit.bsmes.interfacePLM.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.ProcessDAO;
import cc.oit.bsmes.interfacePLM.model.Process;
import cc.oit.bsmes.interfacePLM.model.Scx;
import cc.oit.bsmes.interfacePLM.service.CanShuKuService;
import cc.oit.bsmes.interfacePLM.service.MpartInOutService;
import cc.oit.bsmes.interfacePLM.service.ProcessService;
import cc.oit.bsmes.interfacePLM.service.ScxService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pro.model.ProductCraftsBz;
import cc.oit.bsmes.pro.model.ProductProcessBz;
import cc.oit.bsmes.pro.service.ProductCraftsBzService;
import cc.oit.bsmes.pro.service.ProductProcessBzService;

/**
 * ProcessServiceImpl
 * <p style="display:none">
 * 产品工艺流程Service实现类
 * </p>
 * 
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
@Service
@Scope("prototype")
public class ProcessServiceImpl extends BaseServiceImpl<Process> implements ProcessService {
	@Resource
	private ProcessDAO processDAO;

	// ----------------------------以下为MES/PLM同步代码
	@Resource
	private ProductCraftsBzService productCraftsBzService;
	@Resource
	private ProductProcessBzService productProcessBzService;
	@Resource
	private ScxService scxService;
	@Resource
	private MpartInOutService mpartInOutService;
	@Resource
	private CanShuKuService canShuKuService;
	@Resource
	private MatService matService;

	public static Map<String, String> processCodeMap = new HashMap<String, String>();

	/**
	 * 获取PLM更新过的数据
	 * 
	 * @param @param craftsId 工艺ID
	 * @return List<Process>
	 */
	@Override
	public List<Process> getAsyncDataList(String craftsId) {
		return processDAO.getAsyncDataList(craftsId);
	}

	private static void initProcessCodeMap() {
		processCodeMap.put("铠装配套", "KAIZHUANG_PT");
		processCodeMap.put("检验", "JIANYAN");
		processCodeMap.put("复绕", "RESPOOL");
		// processCodeMap.put("护套-挤出", "JACKET_EXTRUSION");
		// processCodeMap.put("挤出-双层", "EXTRUSION_DUAL");
		// processCodeMap.put("挤出-单层", "EXTRUSION_SINGLE");
		processCodeMap.put("云母带绕包单层", "wrapping_ymd");
		processCodeMap.put("云母带绕包两层", "wrapping_ymd");
		processCodeMap.put("云母带立式绕包单层", "wrapping_ymd");
		processCodeMap.put("云母带立式绕包两层", "wrapping_ymd");
		processCodeMap.put("铠装后绕包一层", "wrapping_ht");
		processCodeMap.put("铠装后绕包两层", "wrapping_ht");
		processCodeMap.put("铠装后绕包", "wrapping_ht");
		// processCodeMap.put("单绞", "单绞");
		// processCodeMap.put("卧式绕包一层", "卧式绕包一层");
		// processCodeMap.put("卧式绕包二层", "卧式绕包二层");
		// processCodeMap.put("卧式绕包三层", "卧式绕包三层");
		// processCodeMap.put("卧式绕包四层", "卧式绕包四层");
		// processCodeMap.put("卧式绕包五层", "卧式绕包五层");
		// processCodeMap.put("卧式绕包六层", "卧式绕包六层");
		// processCodeMap.put("卧式绕包七层", "卧式绕包七层");
		// processCodeMap.put("成缆", "成缆");
		// processCodeMap.put("成缆二层", "成缆二层");
		// processCodeMap.put("成缆三层", "成缆三层");
		// processCodeMap.put("成缆四层", "成缆四层");
		// processCodeMap.put("成缆五层", "成缆五层");
		// processCodeMap.put("成缆六层", "成缆六层");
		// processCodeMap.put("绕包一层", "绕包一层");
		// processCodeMap.put("绕包二层", "绕包二层");
		// processCodeMap.put("绕包三层", "绕包三层");
		// processCodeMap.put("绕包四层", "绕包四层");
		// processCodeMap.put("绕包五层", "绕包五层");
		// processCodeMap.put("绕包六层", "绕包六层");
		// processCodeMap.put("绕包七层", "绕包七层");
		// processCodeMap.put("铠装内护钢带", "铠装内护钢带");
		// processCodeMap.put("铠装绕包钢带", "铠装绕包钢带");
		// processCodeMap.put("印字", "印字");
		// processCodeMap.put("成品复绕", "成品复绕");
		// processCodeMap.put("扭绞", "扭绞");
		// processCodeMap.put("护套印字", "护套印字");
		// processCodeMap.put("束线、绞线", "束线、绞线");
		// processCodeMap.put("条纹印字", "条纹印字");
		// processCodeMap.put("火花配套", "火花配套");
		// processCodeMap.put("烧结", "烧结");
		// processCodeMap.put("绕包", "绕包");
		// processCodeMap.put("编织", "编织");
		// processCodeMap.put("辐照", "辐照");
		// processCodeMap.put("铠装钢丝", "铠装钢丝");
		// processCodeMap.put("隔氧层", "隔氧层");
	}

	/**
	 * MES 同步 PLM 工艺流程数据 <br/>
	 * 同步MES下存在的标准工艺的工序
	 */
	@Override
	@Transactional(readOnly = false)
	public void asyncData() {
		if (processCodeMap.isEmpty()) {
			initProcessCodeMap();
		}

		// 获取MES系统中的工艺
		List<ProductCraftsBz> craftsArray = productCraftsBzService.getAll();
		for (ProductCraftsBz crafts : craftsArray) {
			this.asyncData(crafts.getId());
		}
	}

	/**
	 * MES 同步 PLM 工艺流程数据 <br/>
	 * 根据工艺 ID单个工艺同步
	 * 
	 * @author DingXintao
	 * @param prcvId 工艺ID
	 * @return
	 */
	@Override
	public void asyncData(String prcvId) {
		if (processCodeMap.isEmpty()) {
			initProcessCodeMap();
		}

		List<Process> processList = processDAO.getAsyncDataList(prcvId); // 获取工艺下的所有工序
		List<ProductProcessBz> productProcessBzList = productProcessBzService.getByProductCraftsIdAsc(prcvId);
		for (ProductProcessBz t : productProcessBzList) {
			productProcessBzService.delete(t);
		}
		
		List<Process> lastProcessList = processDAO.getLastProcessList(prcvId); // 获取最后一道工序：因为PLM垃圾数据，所以使用最后一道工序过滤没有下一道工序的数据
		if (null != processList && null != lastProcessList && lastProcessList.size() > 0) {
			Process lastProcess = lastProcessList.get(0);
			for (Process process : processList) {
				if(!process.getId().equals(lastProcess.getId())){
					if(StringUtils.isEmpty(process.getNextProcessId())){
						continue;
					}
				}
				ProductProcessBz productProcessBz = this.processAsync(process); // 同步表标准工序基本信息

				if (productProcessBz != null) { // 新增的执行操作：暂时为性能
					List<Scx> scxlist = scxService.asyncData(process.getId()); // 同步工序下生产线					
					PrcvServiceImpl.scxMapCache.put(process.getId(), scxlist);					
					mpartInOutService.asyncData(process.getId()); // 同步工序下投入产出
					canShuKuService.asyncData(process); // 同步工序下工艺参数
					matService.updateHHColorByJY(productProcessBz.getId()); //
				}
			}
		}
	}

	/**
	 * 同步工序信息
	 * 
	 * @author DingXintao
	 * @param process PLM工序同步对象
	 * @return
	 * */
	private ProductProcessBz processAsync(Process process) {

		ProductProcessBz productProcessBz = new ProductProcessBz();
		
//		productProcessBzService.deleteById(process.getId());
//		if (productProcessBz != null) {
//			productProcessBzService.deleteById(process.getId());
//		}
		
		productProcessBz.setId(process.getId());
		productProcessBz.setCreateUserCode("PLM");
		productProcessBz.setProductCraftsId(process.getPrcvId());
		String processCode = processCodeMap.get(process.getName());
		if (StringUtils.isNotBlank(processCode)) {
			productProcessBz.setProcessCode(processCode);
		} else {
			productProcessBz.setProcessCode(process.getEname()); // ????
		}

		productProcessBz.setProcessName(process.getName());
		productProcessBz.setSeq(process.getGno()); // 加工顺序
		productProcessBz.setNextProcessId(StringUtils.isEmpty(process.getNextProcessId()) ? "-1" : process
				.getNextProcessId());
		productProcessBz.setFullPath(new StringBuffer().toString());
		boolean sameProductLine = process.getSfscc();
		if (process.getName().indexOf("印字") >= 0) {
			sameProductLine = true;
		}
		productProcessBz.setSameProductLine(sameProductLine); // 是否与上一道工序同一生产线
		productProcessBz.setIsOption(false); // 是否可选 ????
		productProcessBz.setIsDefaultSkip(false); // 是否默认跳过????
		// ORG_CODE 数据所属组织
		// STATUS 处理状态
		productProcessBzService.insert(productProcessBz);
		
		//同步实例工序
		
		
		return productProcessBz;

	}

	@Override
	public void insertBatch(List<Process> param) {
		processDAO.insertBatch(param);
		
	}

}
