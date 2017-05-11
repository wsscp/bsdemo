package cc.oit.bsmes.pro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import cc.oit.bsmes.common.mybatis.Sort;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.common.constants.DataStatus;
import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.QCDataType;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.CanShuKuDAO;
import cc.oit.bsmes.interfacePLM.model.CanShuKu;
import cc.oit.bsmes.pro.dao.ProcessInOutDAO;
import cc.oit.bsmes.pro.dao.ProcessQcDAO;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessQcBz;
import cc.oit.bsmes.pro.service.ProcessInOutService;

@Service
public class ProcessInOutServiceImpl extends BaseServiceImpl<ProcessInOut> implements ProcessInOutService {
	private static String FIRST_CHECK = "-11-"; // 首
	private static String IN_CHECK = "-12-"; // 上
	private static String OUT_CHECK = "-13-"; // 下
	private static String MIDDLE_CHECK = "-14-"; // 中

	private static String FIRST_CHECK_0 = "-011-"; // 首
	private static String IN_CHECK_0 = "-012-"; // 上
	private static String OUT_CHECK_0 = "-013-"; // 下
	private static String MIDDLE_CHECK_0 = "-014-"; // 中

	@Resource
	private ProcessInOutDAO processInOutDAO;

	@Resource
	private CanShuKuDAO canShuKuDAO;

	@Resource
	private ProcessQcDAO processQcDAO;

	@Override
	public List<ProcessInOut> getByProcessId(String processId) {
		return processInOutDAO.getByProductProcessId(processId);
	}

	/**
	 * <p>
	 * 通过工序ID查询所有工序投入
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午3:29:08
	 * @param processId
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProcessInOutService#getInByProcessId(java.lang.String)
	 */
	@Override
	public List<ProcessInOut> getInByProcessId(String processId) {
		ProcessInOut findParams = new ProcessInOut();
		findParams.setProductProcessId(processId);
		findParams.setInOrOut(InOrOut.IN);
		return processInOutDAO.get(findParams);
	}

	@Override
	public ProcessInOut getOutByProcessId(String processId) {
		ProcessInOut findParams = new ProcessInOut();
		findParams.setProductProcessId(processId);
		findParams.setInOrOut(InOrOut.OUT);
		return processInOutDAO.getOne(findParams);
	}

	/**
	 * <p>
	 * 查询工序的产出 和投入的成品 半成品
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-25 上午11:33:38
	 * @param processId
	 * @param start
	 * @param limit
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProcessInOutService#getByProcessId(java.lang.String,
	 *      int, int)
	 */
	@Override
	public List<ProcessInOut> getByProcessId(String processId, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return getByProcessId(processId);
	}

	/**
	 * <p>
	 * 统计工序的产出 和投入的成品 半成品
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-25 上午11:36:01
	 * @param processId
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProcessInOutService#countByProcessId(java.lang.String)
	 */
	@Override
	public int countByProcessId(String processId) {
		return processInOutDAO.countByProcessId(processId);
	}

	/**
	 * 终端获取生产单投入物料
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * @return List<ProcessInOut>
	 */
	@Override
	public List<ProcessInOut> getInByWorkOrderNo(String woNo) {
		List<ProcessInOut> list = processInOutDAO.getInByWorkOrderNo(woNo);
		/*
		 * ProcessInOut nextInOut = null; for(int i=list.size() -1; i>=0; i--){
		 * ProcessInOut inOut = list.get(i);
		 * if(StringUtils.isNotBlank(inOut.getBatchNo())){
		 * inOut.setHasPutIn(true); }else{ inOut.setHasPutIn(false); }
		 * if(nextInOut == null){ inOut.setRowSpanSize(1); }else{
		 * if(inOut.getMatCode().equals(nextInOut.getMatCode())){
		 * inOut.setRowSpanSize(nextInOut.getRowSpanSize() + 1);
		 * nextInOut.setRowSpanSize(1); }else{ inOut.setRowSpanSize(1); } }
		 * nextInOut = inOut; }
		 */
		return list;
	}

	@Override
	@Transactional(readOnly = false)
	public void insertBatch(List<ProcessInOut> processInOutList) {
		processInOutDAO.insertBatch(processInOutList);
	}

	@Override
	public void updateQcInOut(String craftsCode) {
		processInOutDAO.updateQcInOut(craftsCode);
		List<Map<String, String>> process = processInOutDAO.findProcessMap();
		List<ProcessQc> processQcs = new ArrayList<ProcessQc>();
		for (Map<String, String> m : process) {
			String mesId = m.get("MES_ID");
			String plmId = m.get("PLM_ID");
			System.out.println("mes:" + mesId + " --------plm:" + plmId);
			List<CanShuKu> canshukuArray = canShuKuDAO.getParamArrayByProcessId(plmId);
			for (CanShuKu canshuku : canshukuArray) {
				Boolean isContin = false;
				for (ProcessQc p : processQcs) {
					if (mesId.equals(p.getProcessId()) && canshuku.getNo().equals(p.getCheckItemCode())) {
						isContin = true;
						break;
					}
				}
				if (isContin) {
					continue;
				}
				System.out.println("pid:" + mesId + "------code:" + canshuku.getNo() + " --------name:"
						+ canshuku.getName());
				ProcessQc processQc = new ProcessQc();
				processQc.setId(UUID.randomUUID().toString());
				processQc.setProcessId(mesId);
				processQc.setCheckItemCode(canshuku.getNo());
				processQc.setCheckItemName(canshuku.getName());
				processQc.setFrequence(10.00); // 检测频率????
				processQc.setNeedDa(false); // 是否需要数采????
				processQc.setNeedIs(false); // 是否需要下发????
				processQc.setItemTargetValue(canshuku.getValue() == null ? "" : canshuku.getValue()); // 参数目标值????
				processQc.setItemMaxValue(null); // 参数上限????
				processQc.setItemMinValue(null); // 参数下限????
				processQc.setDataType(QCDataType.DOUBLE); // 参数数据类型????
				processQc.setDataUnit(null); // 参数单位????
				processQc.setHasPic("0"); // 是否有附件????
				processQc.setNeedShow(StringUtils.isNotBlank(canshuku.getValue()) ? "1" : "0"); // 是否需要在终端显示????
				processQc.setNeedFirstCheck((canshuku.getNo().indexOf(FIRST_CHECK) >= 0 || canshuku.getNo().indexOf(
						FIRST_CHECK_0) >= 0) ? "1" : "0");
				processQc.setNeedInCheck((canshuku.getNo().indexOf(IN_CHECK) >= 0 || canshuku.getNo().indexOf(
						IN_CHECK_0) >= 0) ? "1" : "0");
				processQc.setNeedOutCheck((canshuku.getNo().indexOf(OUT_CHECK) >= 0 || canshuku.getNo().indexOf(
						OUT_CHECK_0) >= 0) ? "1" : "0");
				processQc.setNeedMiddleCheck((canshuku.getNo().indexOf(MIDDLE_CHECK) >= 0 || canshuku.getNo().indexOf(
						MIDDLE_CHECK_0) >= 0) ? "1" : "0");
				processQc.setNeedAlarm("0"); // 超差是否报警????
				processQc.setValueDomain(null); // 值域????
				processQc.setDataStatus(DataStatus.NORMAL);
				processQcs.add(processQc);
			}
		}
		if (!CollectionUtils.isEmpty(processQcs)) {
			processQcDAO.insertBatch(processQcs);
		}
	}

}
