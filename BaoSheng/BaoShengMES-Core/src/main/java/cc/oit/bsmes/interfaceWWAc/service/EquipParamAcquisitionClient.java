package cc.oit.bsmes.interfaceWWAc.service;

import cc.oit.bsmes.common.util.CollectionUtils;
import cc.oit.bsmes.interfaceWWAc.dao.EquipParamAcquisitionDAO;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamAcquisition;
import cc.oit.bsmes.wip.model.Receipt;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class EquipParamAcquisitionClient {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private static Map<String, Receipt> valueCache = new HashMap<String, Receipt>();
	private long waitTime = 500;

	// 数据缓存多久不使用，丢弃时间5分钟
	private static long DATA_EXPIRED_MILLISECONDS = 1000 * 60 * 5;

	private static Object objectRead = new Object();
	private static Object objectWait = new Object();
	@Resource
	EquipParamAcquisitionDAO equipParamAcquisitionDAO;
	static {

		// Receipt value = new Receipt();
		// value.setReceiptCode("Bucket Brigade.zdp");
		// value.setFrequence(2);
		// value.setLastDate(new Date());
		// valueCache.put("Bucket Brigade.zdp", value);
	}

	public void queryReceiptByDB(Map<String, Receipt> receipts) {
		if (receipts == null || receipts.isEmpty()) {
			return;
		}
		Map<String, Receipt> needManuMap = new HashMap<String, Receipt>();

		Iterator<String> it = receipts.keySet().iterator();
		while (it.hasNext()) {
			String receiptKey = it.next();
			receipts.get(receiptKey).setAccessDate(new Date());
			// 从缓存中读取
			Receipt receiptCatchValue = valueCache.get(receiptKey);
			if (receiptCatchValue == null) {
				// 需要读取
				needManuMap.put(receiptKey, receipts.get(receiptKey));
			} else {
				// 直接取值
				receipts.put(receiptKey, receiptCatchValue);
			}
		}
		if (needManuMap != null && !needManuMap.isEmpty()) {
			manualCollectData(needManuMap);
			receipts.putAll(needManuMap);
		}
	}

	// @JOpcConnect
	public void autoCollectDatas() {
		try {
			while (true) {
				try {
					synchronized (objectWait) {
						objectWait.wait(waitTime);
					}
					Map<String, Receipt> needManuMap = new HashMap<String, Receipt>();
					needManuMap.putAll(valueCache);
					collectData(needManuMap, false);
				} catch (InterruptedException ex) {
					log.debug("autoCollectDatas, wait interrupted");
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	private void manualCollectData(Map<String, Receipt> receiptMap) {
		try {
			collectData(receiptMap, true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private void collectData(Map<String, Receipt> receiptMap, boolean isManual) throws Exception {
		synchronized (objectRead) {
			try {
				if (isManual) {
					saveTraceAttrValue(receiptMap);
					valueCache.putAll(receiptMap);
				} else {
					Map<String, Receipt> needRefreshReceiptMap = new HashMap<String, Receipt>();
					Iterator<String> it = receiptMap.keySet().iterator();
					it = receiptMap.keySet().iterator();
					while (it.hasNext()) {
						String receiptKey = it.next();
						Receipt data = receiptMap.get(receiptKey);
						Date lastDate = data.getLastDate();
						Date accessDate = data.getAccessDate();
						if (accessDate != null) {
							long diff = new Date().getTime() - accessDate.getTime();
							// 超时丢弃
							if (diff >= DATA_EXPIRED_MILLISECONDS) {
								// receiptMap.remove(receiptKey);
								valueCache.remove(receiptKey);

								log.info("丢弃：" + receiptKey);
								continue;
							}
						} else {
							data.setAccessDate(new Date());
						}

						long interval = 0;
						if (lastDate != null) {
							long diff = new Date().getTime() - data.getLastDate().getTime();
							interval = diff;
						}
						if (lastDate == null || interval >= data.getFrequence() * 1000) {
							needRefreshReceiptMap.put(receiptKey, data);
						}

					}
					saveTraceAttrValue(needRefreshReceiptMap);
					valueCache.putAll(needRefreshReceiptMap);
				}

			} catch (Exception e) {
				throw e;
			}
		}
	}

	private void saveTraceAttrValue(Map<String, Receipt> data) {
		if (data == null || data.size() < 1) {
			return;
		}
		String[] set = new String[data.size()];
		data.keySet().toArray(set);
		List<String> tagNames = new ArrayList<String>();
		tagNames = Arrays.asList(set);
		Date endTime = new Date();
		Date startTime = DateUtils.addMinutes(endTime, -1);
		List<List<String>> newList = CollectionUtils.splitList(tagNames, 100);
		Map<String, EquipParamAcquisition> newRecordesMap = new HashMap<String, EquipParamAcquisition>();
		for (int loop = 0; loop < newList.size(); loop++) {
			List<String> loopObject = newList.get(loop);
			List<EquipParamAcquisition> resultList = null;
			try {
				resultList = equipParamAcquisitionDAO.findLiveValue(loopObject, startTime, endTime);
			} catch (Exception e) {
				log.warn(loopObject.toString() + "中有不可采集的tagName");
			}
			if (resultList != null && resultList.size() > 0) {
				for (int j = 0; j < resultList.size(); j++) {
					EquipParamAcquisition newRecordes = resultList.get(j);
					newRecordesMap.put(newRecordes.getTagname(), newRecordes);
				}
			}

		}

		Iterator<String> it = data.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Receipt receipt = data.get(key);
			EquipParamAcquisition newRecordes = newRecordesMap.get(key);
			if (newRecordes != null) {
				if(newRecordes.getValue() != null){
					receipt.setDaValue(newRecordes.getValue().toString());
					receipt.setLastDate(new Date());
					receipt.setCreateTime(newRecordes.getDatetime());
				}else{
					receipt.setDaValue(null);
					receipt.setLastDate(new Date());
					receipt.setCreateTime(newRecordes.getDatetime());
				}
			} else {
				// 写入空值
				receipt.setDaValue(null);
				receipt.setLastDate(new Date());
				receipt.setCreateTime(new Date());
			}
		}

	}

}
