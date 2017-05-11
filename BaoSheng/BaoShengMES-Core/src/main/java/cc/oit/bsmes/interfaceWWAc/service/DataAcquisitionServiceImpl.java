package cc.oit.bsmes.interfaceWWAc.service;

import cc.oit.bsmes.bas.dao.ParmsMappingDAO;
import cc.oit.bsmes.bas.model.ParmsMapping;
import cc.oit.bsmes.common.constants.StandardParamCode;
import cc.oit.bsmes.interfaceWWAc.dao.EquipParamHistoryAcquisitionDAO;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamHistoryAcquisition;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.service.ReceiptService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class DataAcquisitionServiceImpl implements DataAcquisitionService {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private ReceiptService receiptService;
	@Resource
	EquipParamAcquisitionClient equipParamAcquisitionClient;
	@Resource
	ParmsMappingDAO parmsMappingDAO;
	@Resource
	EquipParamHistoryAcquisitionDAO equipParamHistoryAcquisitionDAO;

	private static Map<String, Receipt> valueCache = new HashMap<String, Receipt>();
	// 数据过期时间 暂定2小时
	private static long DATA_EXPIRED_MILLISECONDS = 1000 * 3600 * 2;

	private static String CODE_SPLIT = ".";
	/**
	 * 历史数据查询默认周期数
	 */
	private static int DEFAULT_CYCLES = 200;

	@SuppressWarnings("unused")
	@Override
	public double getLength(String equipCode) {
		Receipt re = new Receipt();
		re.setEquipCode(equipCode);
		re.setReceiptCode(StandardParamCode.R_Length.name());
		queryLiveReceiptByCodes(re);
		if (re != null) {
			String value = re.getDaValue();
			// 这里采集的长度单位为毫米
			double currentValue = 0;
			if (StringUtils.isNotBlank(value)) {
				currentValue = (Double.parseDouble(value));
				DecimalFormat df = new DecimalFormat("#.00");
				currentValue = Double.parseDouble(df.format(currentValue));
			}
			return currentValue;
		} else {
			return 0;
		}

	}

	public void queryLiveReceiptByCodes(Receipt receipt) {
		List<Receipt> receipts = new ArrayList<Receipt>();
		receipts.add(receipt);
		List<Receipt> result = queryLiveReceiptByCodes(receipts);
		if (result.size() > 0) {
			receipt.setDaValue(result.get(0).getDaValue());
			receipt.setCreateTime(result.get(0).getCreateTime());
		} else {
			return;
		}
	}

	@SuppressWarnings("unused")
	public List<Receipt> queryLiveReceiptByCodes(List<Receipt> receipts) {
		// 不可能为空
		/*
		 * if(receipts==null||receipts.size()<1) { return null ; }
		 */
		Map<String, Receipt> resultMap = new HashMap<String, Receipt>();
		for (int loop = 0; loop < receipts.size(); loop++) {
			Receipt receipt = receipts.get(loop);
			StringBuffer bf = new StringBuffer();
			bf.append(receipt.getEquipCode()).append(CODE_SPLIT).append(receipt.getReceiptCode());
			String codeCompise = bf.toString();

			Receipt receiptcache = valueCache.get(codeCompise);
			if (receiptcache == null) {
				Receipt receiptnew = receiptService.getByProcessReceiptAndQA(receipt.getReceiptCode(),
						receipt.getEquipCode());
				if (receiptnew != null) {
					ParmsMapping findParams = new ParmsMapping();
					findParams.setEquipCode(receiptnew.getEquipCode());
					findParams.setParmCode(receiptnew.getReceiptCode());
					ParmsMapping mappngobject = parmsMappingDAO.getOne(findParams);
					if (mappngobject == null) {
						throw new RuntimeException("设备：" + receiptnew.getEquipCode() + "的参数："
								+ receiptnew.getReceiptCode() + "没有在映射表T_INT_EQUIP_MES_WW_MAPPING中配置映射信息！");
					}
					receiptnew.setTagName(mappngobject.getTagName());
					receiptnew.setLastLoadDate(new Date());
					resultMap.put(mappngobject.getTagName(), receiptnew);
					valueCache.put(codeCompise, receiptnew);
				}

			} else {
				Date lastDate = receiptcache.getLastLoadDate();
				if (lastDate != null) {
					long diff = new Date().getTime() - receiptcache.getLastLoadDate().getTime();
					// 数据过期，重新加载
					if (diff > DATA_EXPIRED_MILLISECONDS) {
						Receipt receiptnew = receiptService.getByProcessReceiptAndQA(receipt.getReceiptCode(),
								receipt.getEquipCode());
						if (receiptnew != null) {

							ParmsMapping findParams = new ParmsMapping();
							findParams.setEquipCode(receiptnew.getEquipCode());
							findParams.setParmCode(receiptnew.getReceiptCode());
							ParmsMapping mappngobject = parmsMappingDAO.getOne(findParams);
							if (mappngobject == null) {
								throw new RuntimeException("设备：" + receiptnew.getEquipCode() + "的参数："
										+ receiptnew.getReceiptCode() + "没有在映射表T_INT_EQUIP_MES_WW_MAPPING中配置映射信息！");
							}
							receiptnew.setTagName(mappngobject.getTagName());
							resultMap.put(mappngobject.getTagName(), receiptnew);
							receiptnew.setLastLoadDate(new Date());
							valueCache.put(codeCompise, receiptnew);
						} else {
							valueCache.remove(codeCompise);
						}

					} else {
						resultMap.put(receiptcache.getTagName(), receiptcache);
					}
				} else {
					receiptcache.setLastLoadDate(new Date());
					resultMap.put(receiptcache.getTagName(), receiptcache);
				}
			}

		}

		try {
			equipParamAcquisitionClient.queryReceiptByDB(resultMap);
			if (resultMap != null) {
				List<Receipt> result = new ArrayList<Receipt>();
				result.addAll(resultMap.values());
				return result;
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return receipts;

	}

	@Override
	public List<EquipParamHistoryAcquisition> findParamHistory(String equipCode, String parmCode, Date startTime,
			Date endTime, Integer cycleCount) {
		if (StringUtils.isEmpty(equipCode) || StringUtils.isEmpty(parmCode)) {
			return null;
		}
		if (startTime == null) {
			startTime = DateUtils.addHours(new Date(), -1);
		}
		// 考虑到系统采集数据延时，查询两分钟前的数据
		if (endTime == null) {
			endTime = DateUtils.addMinutes(new Date(), -2);
		} else {
			// endTime=DateUtils.addMinutes(endTime,-2);
		}
		if (cycleCount == null) {
			cycleCount = DEFAULT_CYCLES;
		}
		ParmsMapping findParams = new ParmsMapping();
		findParams.setEquipCode(equipCode);
		findParams.setParmCode(parmCode);
		ParmsMapping mappngobject = parmsMappingDAO.getOne(findParams);
		if (mappngobject == null) {
			throw new RuntimeException("设备：" + equipCode + "的参数：" + parmCode
					+ "没有在映射表T_INT_EQUIP_MES_WW_MAPPING中配置映射信息！");
		}
		EquipParamHistoryAcquisition parm = new EquipParamHistoryAcquisition();
		parm.setTagname(mappngobject.getTagName());
		parm.setStartTime(startTime);
		parm.setEndTime(endTime);
		parm.setWwcyclecount(cycleCount);
		return equipParamHistoryAcquisitionDAO.findParamHistory(parm);
	}

	@Override
	public List<EquipParamHistoryAcquisition> findLengthLiveData(String equipCode, String parmCode) {
		if (StringUtils.isEmpty(equipCode) || StringUtils.isEmpty(parmCode)) {
			return null;
		}

		ParmsMapping findParams = new ParmsMapping();
		findParams.setEquipCode(equipCode);
		findParams.setParmCode(parmCode);
		ParmsMapping mappngobject = parmsMappingDAO.getOne(findParams);
		if (mappngobject == null) {
			throw new RuntimeException("设备：" + equipCode + "的参数：" + parmCode
					+ "没有在映射表T_INT_EQUIP_MES_WW_MAPPING中配置映射信息！");
		}
		EquipParamHistoryAcquisition parm = new EquipParamHistoryAcquisition();
		parm.setTagname(mappngobject.getTagName());
		return equipParamHistoryAcquisitionDAO.findLengthLiveData(parm);
	}

	// @Override
	// public List<EquipParamHistoryAcquisition>
	// findParamHistory(EquipParamHistoryAcquisition historyAcquisition) {
	// return
	// equipParamHistoryAcquisitionDAO.findParamHistory(historyAcquisition);
	// }

	@Override
	public List<EquipParamHistoryAcquisition> findSubParamHistory(String equipCode, String parmCode, Date startTime,
			Date endTime, Integer cycleCount) {

		ParmsMapping findParams = new ParmsMapping();
		findParams.setEquipCode(equipCode);
		findParams.setParmCode(parmCode);
		ParmsMapping mappngobject = parmsMappingDAO.getOne(findParams);
		if (mappngobject == null) {
			throw new RuntimeException("设备：" + equipCode + "的参数：" + parmCode
					+ "没有在映射表T_INT_EQUIP_MES_WW_MAPPING中配置映射信息！");
		}
		EquipParamHistoryAcquisition parm = new EquipParamHistoryAcquisition();
		parm.setTagname(mappngobject.getTagName());
		parm.setStartTime(startTime);
		parm.setEndTime(endTime);
		parm.setWwcyclecount(cycleCount);
		return equipParamHistoryAcquisitionDAO.findHistoryData(parm);
	}
}
