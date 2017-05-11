package cc.oit.bsmes.interfacePLM.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.DataStatus;
import cc.oit.bsmes.common.constants.QCDataType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.CanShuKuDAO;
import cc.oit.bsmes.interfacePLM.model.CanShuKu;
import cc.oit.bsmes.interfacePLM.model.Process;
import cc.oit.bsmes.interfacePLM.service.CanShuKuService;
import cc.oit.bsmes.pro.model.ProcessQcBz;
import cc.oit.bsmes.pro.service.ProcessQcBzService;

/**
 * CanShuKuServiceImpl
 * <p style="display:none">
 * 产品工艺流程Service实现类
 * </p>
 * 
 * @author DingXintao
 * @date 2014-12-16 13:34:34
 */
@Service
@Scope("prototype")
public class CanShuKuServiceImpl extends BaseServiceImpl<CanShuKu> implements
		CanShuKuService {
	@Resource
	private CanShuKuDAO canShuKuDAO;

	@Override
	public List<CanShuKu> getRealCanShuKu(Date lastExecuteTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lastDate", lastExecuteTime);
		return canShuKuDAO.getRealCanShuKu(map);
	}

	// ----------------------------以下为MES/PLM同步代码
	@Resource
	private ProcessQcBzService processQcBzService;

	public Map<String, CanShuKu> plmCanShuKuArrayCache = new HashMap<String, CanShuKu>();

	private List<ProcessQcBz> updateProcessQcBzArrayCache = new ArrayList<ProcessQcBz>();

	/**
	 * MES 同步 PLM 工艺参数数据 <br/>
	 * 根据工艺 ID单个工艺同步
	 * 
	 * @author DingXintao
	 * @param process
	 *            工序对象
	 * @return
	 */
	public void asyncData(Process process) {
		if (StringUtils.isEmpty(process.getCsvalue1())) {
			logger.debug("=========工序{}[{}]没有参数属性=========", process.getName(),
					process.getId());
			return;
		}
		
		List<CanShuKu> canshukuArray = canShuKuDAO.getParamArrayByProcessId(process.getId());
		// 2015.6.16日修改，性能优化，批量删除以及批量插入
		List<ProcessQcBz> processQcBzList = new ArrayList<ProcessQcBz>();
		
		processQcBzService.deleteProcessQCbzByProcessId(process.getId());
		
		for (CanShuKu canshuku : canshukuArray) {
			// ***********暂时修改为直接新增 不考虑修改，提高性能
			ProcessQcBz processQcBz = new ProcessQcBz();
			processQcBz.setProcessId(process.getId());
			processQcBz.setCheckItemCode(canshuku.getNo());
			processQcBz.setCheckItemName(canshuku.getName());
			processQcBz.setFrequence(10.00); // 检测频率????
			processQcBz.setNeedDa(false); // 是否需要数采????
			processQcBz.setNeedIs(false); // 是否需要下发????
			processQcBz.setItemTargetValue(canshuku.getValue() == null ? ""
					: canshuku.getValue()); // 参数目标值????
			processQcBz.setItemMaxValue(null); // 参数上限????
			processQcBz.setItemMinValue(null); // 参数下限????
			processQcBz.setDataType(QCDataType.DOUBLE); // 参数数据类型????
			processQcBz.setDataUnit(null); // 参数单位????
			processQcBz.setHasPic("0"); // 是否有附件????
			processQcBz
					.setNeedShow(StringUtils.isNotBlank(canshuku.getValue()) ? "1"
							: "0"); // 是否需要在终端显示????
			processQcBz.setNeedFirstCheck((canshuku.getNo().indexOf(FIRST_CHECK)>=0 || canshuku.getNo().indexOf(FIRST_CHECK_0)>=0) ?"1":"0" );
			processQcBz.setNeedInCheck((canshuku.getNo().indexOf(IN_CHECK)>=0 || canshuku.getNo().indexOf(IN_CHECK_0)>=0) ?"1":"0");
			processQcBz.setNeedOutCheck((canshuku.getNo().indexOf(OUT_CHECK)>=0 || canshuku.getNo().indexOf(OUT_CHECK_0)>=0) ?"1":"0");
			processQcBz.setNeedMiddleCheck((canshuku.getNo().indexOf(MIDDLE_CHECK)>=0 || canshuku.getNo().indexOf(MIDDLE_CHECK_0)>=0) ?"1":"0");
			processQcBz.setNeedAlarm("0"); // 超差是否报警????
			processQcBz.setValueDomain(null); // 值域????
			processQcBz.setDataStatus(DataStatus.NORMAL);
			
			processQcBzService.insert(processQcBz);
			processQcBzList.add(processQcBz);
			canshuku = null;
		}
//		if (processQcBzList.size() >0){
//			processQcBzService.batchInsertProcessQcBz(processQcBzList);
//		}
//		processQcBzService
//				.updateProcessQcBzDataUnit(updateProcessQcBzArrayCache); // 更新：主要是数据单位
	}

	/**
	 * 同步生产线信息
	 * 
	 * @author DingXintao
	 * @param canshuObj
	 *            PLM工艺参数同步对象
	 * @param processId
	 *            工序ID
	 * @return
	 * */
	private void scxAsync(CanShuKu canshuObj, String processId) {

		// ***********暂时修改为直接新增 不考虑修改，提高性能
		
		ProcessQcBz processQcBz = new ProcessQcBz();
		processQcBz.setProcessId(processId);
		processQcBz.setCheckItemCode(canshuObj.getNo());
     	List<ProcessQcBz> processQcBzList = processQcBzService.getByObj(processQcBz);
        if (null != processQcBzList || processQcBzList.size() > 0) {
        	processQcBzService.delete(processQcBzList);
		processQcBz.setCheckItemName(canshuObj.getName());
		processQcBz.setFrequence(10.00); // 检测频率????
		processQcBz.setNeedDa(false); // 是否需要数采????
		processQcBz.setNeedIs(false); // 是否需要下发????
		processQcBz.setItemTargetValue(canshuObj.getValue() == null ? ""
				: canshuObj.getValue()); // 参数目标值????
		processQcBz.setItemMaxValue(null); // 参数上限????
		processQcBz.setItemMinValue(null); // 参数下限????
		processQcBz.setDataType(QCDataType.DOUBLE); // 参数数据类型????
		processQcBz.setDataUnit(null); // 参数单位????
		processQcBz.setHasPic("0"); // 是否有附件????
		processQcBz
				.setNeedShow(StringUtils.isNotBlank(canshuObj.getValue()) ? "1"
						: "0"); // 是否需要在终端显示????
		processQcBz.setNeedFirstCheck((canshuObj.getNo().indexOf(FIRST_CHECK)>=0 || canshuObj.getNo().indexOf(FIRST_CHECK_0)>=0) ?"1":"0" );
		processQcBz.setNeedInCheck((canshuObj.getNo().indexOf(IN_CHECK)>=0 || canshuObj.getNo().indexOf(IN_CHECK_0)>=0) ?"1":"0");
		processQcBz.setNeedOutCheck((canshuObj.getNo().indexOf(OUT_CHECK)>=0 || canshuObj.getNo().indexOf(OUT_CHECK_0)>=0) ?"1":"0");
		processQcBz.setNeedMiddleCheck((canshuObj.getNo().indexOf(MIDDLE_CHECK)>=0 || canshuObj.getNo().indexOf(MIDDLE_CHECK_0)>=0) ?"1":"0");
		processQcBz.setNeedAlarm("0"); // 超差是否报警????
		processQcBz.setValueDomain(null); // 值域????
		processQcBz.setDataStatus(DataStatus.NORMAL);
		processQcBzService.insert(processQcBz);
        }
	}

	private static String FIRST_CHECK_0 = "-011-"; // 首
	private static String IN_CHECK_0 = "-012-"; // 上
	private static String OUT_CHECK_0 = "-013-"; // 下
	private static String MIDDLE_CHECK_0 = "-014-"; // 中

	private static String FIRST_CHECK = "-11-"; // 首
	private static String IN_CHECK = "-12-"; // 上
	private static String OUT_CHECK = "-13-"; // 下
	private static String MIDDLE_CHECK = "-14-"; // 中
}
