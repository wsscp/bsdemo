package cc.oit.bsmes.interfacePLM.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.ProductEquip;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.ProductEquipService;
import cc.oit.bsmes.interfacePLM.dao.ScxDAO;
import cc.oit.bsmes.interfacePLM.dao.ScxkDAO;
import cc.oit.bsmes.interfacePLM.model.Scx;
import cc.oit.bsmes.interfacePLM.model.Scxk;
import cc.oit.bsmes.interfacePLM.service.ScxService;
import cc.oit.bsmes.job.tasks.EquipUpdateTask;
import cc.oit.bsmes.pro.dao.EqipListBzDAO;
import cc.oit.bsmes.pro.model.EqipListBz;
import cc.oit.bsmes.pro.service.EqipListBzService;

/**
 * Created by JinHy on 2014/9/28 0028.
 */
@Service
public class ScxServiceImpl extends BaseServiceImpl<Scx> implements ScxService {

	@Resource
	private ScxDAO scxDAO;
	@Resource
	private ScxkDAO scxkDAO;
	@Resource
	private EqipListBzDAO eqipListBzDAO;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private LastExecuteTimeRecordService lastExecuteTimeRecordService;
	@Resource
	private ProductEquipService productEquipService;

	/***
	 * 同步生产线,并生成主设备
	 */
	@Override
	public void syncScxData() {
		LastExecuteTimeRecord letRecord = lastExecuteTimeRecordService
				.getOne(InterfaceDataType.SCX);
		Map<String, Date> findParams = new HashMap<String, Date>();
		findParams.put("lastDate", letRecord.getLastExecuteTime());
		List<Scxk> list = scxkDAO.lastUpdateData(findParams);

		Date lastExecuteTime = null;
		for (Scxk scxk : list) {
			if (StringUtils.isBlank(scxk.getName())) {
				continue;
			}
			
			
			EquipInfo equipInfo = equipInfoService.findForDataUpdate(
					scxk.getNo(), EquipType.PRODUCT_LINE.name());
			if(null == equipInfo){
				equipInfo =	equipInfoService.getById(scxk.getId());
			}
			if (equipInfo == null) {
				equipInfo = new EquipInfo();
				equipInfo.setId(scxk.getId());
				equipInfo.setCode(scxk.getNo());
				equipInfo.setName(scxk.getName());
				equipInfo.setEname(scxk.getEname());
				equipInfo.setType(EquipType.PRODUCT_LINE);
				equipInfo.setStatus(EquipStatus.IDLE);
				equipInfo.setOrgCode(this.getOrgCode());
				equipInfo.setCreateUserCode(EquipUpdateTask.SYNC_FROM);
				equipInfo.setModifyUserCode(EquipUpdateTask.SYNC_FROM);
				equipInfoService.insert(equipInfo);
				// 生成主设备
				generateMainEquip(equipInfo);
			} else {
				equipInfo.setCode(scxk.getNo());
				equipInfo.setName(scxk.getName());
				equipInfo.setEname(scxk.getEname());
				equipInfo.setModifyUserCode(EquipUpdateTask.SYNC_FROM);
				equipInfoService.update(equipInfo);
			}
            if(lastExecuteTime==null)
            {
            	lastExecuteTime=scxk.getCtime();
            }
            if(scxk.getCtime().after(lastExecuteTime))
            {
            	lastExecuteTime=scxk.getCtime();
            }
            if(scxk.getMtime().after(lastExecuteTime))
            {
            	lastExecuteTime=scxk.getMtime();
            } 
            
		}

		letRecord.setLastExecuteTime(lastExecuteTime);
		lastExecuteTimeRecordService.saveRecord(letRecord);
	}

	/**
	 * 生成生产线主设备
	 * 
	 * @param productLine
	 */
	private void generateMainEquip(EquipInfo productLine) {
		EquipInfo newEquip = new EquipInfo();
		BeanUtils.copyProperties(productLine, newEquip);
		String uuid = UUID.randomUUID().toString();
		newEquip.setId(uuid);
		newEquip.setCode(productLine.getCode() + "_EQUIP");
		newEquip.setName(productLine.getName() + "_设备");
		newEquip.setType(EquipType.MAIN_EQUIPMENT);
		newEquip.setCreateUserCode(EquipUpdateTask.SYNC_FROM);
		newEquip.setModifyUserCode(EquipUpdateTask.SYNC_FROM);
		newEquip.setCreateTime(new Date());
		newEquip.setModifyTime(new Date());
		newEquip.setStatus(EquipStatus.IDLE);
		newEquip.setOrgCode(this.getOrgCode());
		newEquip.setModel("普通设备");
		equipInfoService.insert(newEquip);

		ProductEquip t = new ProductEquip();
		t.setCreateUserCode(EquipUpdateTask.SYNC_FROM);
		t.setModifyUserCode(EquipUpdateTask.SYNC_FROM);
		t.setCreateTime(new Date());
		t.setModifyTime(new Date());
		t.setEquipId(newEquip.getId());
		t.setProductLineId(productLine.getId());
		t.setIsMain(true);
		t.setOrgCode(this.getOrgCode());
		t.setStatus("VALID");
		productEquipService.insert(t);
	}

	// ----------------------------以下为MES/PLM同步代码
	@Resource
	private EqipListBzService eqipListBzService;

	/**
	 * MES 同步 PLM 工序生产线数据 <br/>
	 * 根据工艺序ID同步下面的生产线
	 * 
	 * @author DingXintao
	 * @param processId
	 *            工序ID
	 * @return
	 */
	@Override
	public List<Scx>  asyncData(String processId) {
		List<Scx> scxArray = scxDAO.getAsyncDataList(processId); // 获取工序下的生产线
		if (null != scxArray && scxArray.size()>0) {
			for (Scx scx : scxArray) {
				this.scxAsync(scx, processId); // 同步生产线信息
			}
		}
		return scxArray;
	}

	/**
	 * 同步生产线信息
	 * 
	 * @author DingXintao
	 * @param scx
	 *            PLM生产线同步对象
	 * @param processId
	 *            工序ID
	 * @return
	 * */
	private void scxAsync(Scx scx, String processId) {
		EqipListBz equipListBz = new EqipListBz();
		equipListBz.setEquipCode(scx.getNo());
		equipListBz.setProcessId(processId);
		equipListBz.setType(EquipType.PRODUCT_LINE); // 设备类型
		// 判断是否存在了，是就update，否就insert
		List<EqipListBz> equipListBzList = eqipListBzService
				.getByObj(equipListBz);
		if (equipListBzList != null && equipListBzList.size() != 0) {
				eqipListBzService.deleteById(equipListBzList.get(0).getId());

		}
		equipListBz.setId(scx.getId());
		equipListBz.setCreateUserCode("PLM");
		equipListBz.setCreateTime(new Date());
		equipListBz.setEquipCapacity(1.0); // 设备能力 ????
		equipListBz.setSetUpTime(0); // 前置时间 ????
		equipListBz.setShutDownTime(0); // 后置时间 ????
		equipListBz.setIsDefault(true);
		// BNUM 数量
		// PSTNO 顺序号
		// ORG_CODE 数据所属组织
		// STATUS 处理状态
		eqipListBzService.insert(equipListBz);
	}

}
