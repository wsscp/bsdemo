package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.MesClientManEqipDAO;
import cc.oit.bsmes.bas.model.MesClientManEqip;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.MesClientManEqipService;
import cc.oit.bsmes.common.constants.QADetectType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.model.ProcessQcValue;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessQcValueService;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ReportService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.api.common.EnergyMonitor;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MesClientManEqipServiceImpl extends BaseServiceImpl<MesClientManEqip>
        implements MesClientManEqipService {

    @Resource
    private WorkOrderService workOrderService;
    @Resource
    private MesClientManEqipDAO mesClientManEqipDAO;
    @Resource
    private DataAcquisitionService dataAcquisitionService;
    @Resource
    private EquipInfoService equipInfoService;
    @Resource
    private ProcessQcService processQcService;
    @Resource
    private OrderTaskService orderTaskService;
    @Resource
    private ReportService reportService;
    @Resource
    private ProcessInformationService processInformationService;

    @Resource
    private ProcessQcValueService processQcValueService;

    @Override
    public List<MesClientEqipInfo> getByMesClientMac(String mac, String ip, boolean loadAttrs) {
        //SqlInterceptor.setRowBounds(new RowBounds(start, max));
        List<MesClientEqipInfo> list = mesClientManEqipDAO.getInfoByMesClientMac(mac, ip);
        if (loadAttrs) {
            for (MesClientEqipInfo eqipInfo : list) {
                loadAttrs(eqipInfo);
            }
        }
        return list;
    }

    @Override
    public MesClientEqipInfo findByMesClientMac(String equipCode) {
    	
//    	String url = "http://www.chinadny.com:8008/token";
//		String entity = "client_id=app_user&client_secret=xldny_app_2016&grant_type=client_credentials";
//		String tokenstr = EnergyMonitor.httpPostGetToken(url, "", entity, false);
//		String token = JSONObject.fromObject(tokenstr).getString("access_token");
//		String token = JSON.parseObject(tokenstr).getString("access_token");
//		net.sf.json.JSONObject jsonparam = new net.sf.json.JSONObject();
//		url = "http://www.chinadny.com:8008/restful/Meter/GetMeterRealData";
//		String energyConsumptio ="";
//		if(equipCode=="442-225"||"442-225".equals(equipCode)){
//			jsonparam.put("did", "185511");
//			jsonparam.put("di", "1,2,5,6");
//			String result = EnergyMonitor.httpPostGetApi(url, jsonparam, token, false);
//			int j = result.indexOf("body");
//			energyConsumptio = result.substring(j+253, j+259);
//		}else if(equipCode=="442-206"||"442-206".equals(equipCode)){
//			jsonparam.put("did", "185512");
//			jsonparam.put("di", "1,2,5,6");
//			String result = EnergyMonitor.httpPostGetApi(url, jsonparam, token, false);
//			int j = result.indexOf("body");
//			energyConsumptio = result.substring(j+252, j+258);
//		}else if(equipCode=="444-79"||"444-79".equals(equipCode)){
//			jsonparam.put("did", "185509");
//			jsonparam.put("di", "1,2,5,6");
//			String result = EnergyMonitor.httpPostGetApi(url, jsonparam, token, false);
//			int j = result.indexOf("body");
//			energyConsumptio = result.substring(j+252, j+258);
//		}
        User user = SessionUtils.getUser();
        EquipInfo equip = equipInfoService.getByCode(equipCode, user.getOrgCode());
        MesClientEqipInfo equipInfo = new MesClientEqipInfo();
//		equipInfo.setEnergyConsumptio(energyConsumptio);	
        equipInfo.setEqipId(equip.getId());
        equipInfo.setEquipCode(equipCode);
        loadAttrs(equipInfo);
        return equipInfo;
    }

    private void loadAttrs(MesClientEqipInfo equipInfo) {
        //workOrder 也可以做缓存
        WorkOrder workOrder = workOrderService.getCurrentByEquipCode(equipInfo.getEquipCode());
        if (workOrder == null) {
            return;
        }
        ProcessInformation processInformation = processInformationService.getByCode(workOrder.getProcessCode());
        equipInfo.setSection(processInformation == null?"":processInformation.getSection());
        equipInfo.setReleaseDate(workOrder.getReleaseDate());
        equipInfo.setRequireFinishDate(workOrder.getRequireFinishDate());
        if("Jacket-Extrusion".equals(processInformation.getCode())){
        	equipInfo.setPlanLength(Double.parseDouble(workOrderService.getContractLengthByWorkOrderNo(workOrder.getWorkOrderNo())));
        }else{
        	 equipInfo.setPlanLength(workOrder.getOrderLength() == null ? 0 : workOrder.getOrderLength());
        }
        equipInfo.setCurrentProcess(workOrder.getProcessName());
        equipInfo.setProductColor(orderTaskService.getWorkOrderColors(workOrder.getWorkOrderNo()));

        //前台显示产品名称 工序代码  by 陶世松
        equipInfo.setWorkOrderNo(workOrder.getWorkOrderNo());
        List<Report> reports = reportService.getGoodLengthByWorkOrder(workOrder.getWorkOrderNo());
        double goodLength = 0;
        for (Report report : reports) {
            goodLength += report.getGoodLength();
        }

        if (goodLength > 0) {
            goodLength = Double.parseDouble(WebConstants.DOUBLE_DF.format(goodLength));
        }
        equipInfo.setQualifiedLength(goodLength);
        EquipInfo mainEquip = StaticDataCache.getMainEquipInfo(equipInfo.getEquipCode());
        double currentLength = 0.0;
        if (mainEquip != null) {
            //得到当前采集器上采集到的生产长度，
            currentLength = getCurrentLength(mainEquip);
            equipInfo.setCurrentReportLength(currentLength);
        }
        equipInfo.setCurrentDiscLength(goodLength);

        double cancelLength = workOrder.getCancelLength() == null ? 0 : workOrder.getCancelLength();
        double remainQLength = equipInfo.getPlanLength() - cancelLength - goodLength;
        equipInfo.setRemainQLength(remainQLength < 0 ? 0 : Math.floor(remainQLength));
    }

    private double getCurrentLength(EquipInfo mainEquip) {
        return dataAcquisitionService.getLength(mainEquip.getCode());
    }

    @Override
    public List<MesClientManEqip> findByRequestMap(String mesClientId, int start, int limit) {
        SqlInterceptor.setRowBounds(new RowBounds(start, limit));
        return mesClientManEqipDAO.findByRequestMap(mesClientId);
    }

    @Override
    public Integer countByRequestMap(String mesClientId) {
        return mesClientManEqipDAO.countByRequestMap(mesClientId);
    }

    @Override
    public MesClientManEqip getByMesClientIdAndEqipId(String mesClientId,
                                                      String eqipId) {
        return mesClientManEqipDAO.getByMesClientIdAndEqipId(mesClientId, eqipId);
    }

    @Override
    public Integer getMiddleCheckInterval() {
        User user = SessionUtils.getUser();
        String interval = WebContextUtils.getSysParamValue(WebConstants.MIDDLE_CHECK_INTERVAL, user.getOrgCode());
        return Integer.parseInt(interval) * 60 * 1000;
    }

    @Override
    public List<Map<String, String>> emphReceipt(String processId, String productLineCode) {
        return processQcService.getEmphShow(processId, productLineCode);
    }
}
