package cc.oit.bsmes.bas.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.dao.EquipEnergyMonitorDAO;
import cc.oit.bsmes.bas.model.EquipEnergyMonitor;
import cc.oit.bsmes.bas.model.EquipEnergyMonthMonitor;
import cc.oit.bsmes.bas.service.EquipEnergyMonitorService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.api.common.EnergyMonitor;

@Service
public class EquipEnergyMonitorServiceImpl extends BaseServiceImpl<EquipEnergyMonitor> implements EquipEnergyMonitorService {

    @Resource
    private EquipEnergyMonitorDAO equipEnergyMonitorDAO;

    
    @Override
	public void insertEquipEnergyMonitorInfo()throws Exception {
		EquipEnergyMonitor equipEnergyMonitor = new EquipEnergyMonitor();
		String url = "http://www.chinadny.com:8012/token";
		String entity = "client_id=app_user&client_secret=xldny_app_2016&grant_type=client_credentials";
		String tokenstr = EnergyMonitor.httpPostGetToken(url, "", entity, false);
		String token = JSON.parseObject(tokenstr).getString("access_token");
		net.sf.json.JSONObject jsonparam = new net.sf.json.JSONObject();
		url = "http://www.chinadny.com:8008/restful/Meter/GetMeterRealData";
		jsonparam.put("did", "185511,185509,185510,185512,185513,185514");
		jsonparam.put("di", "1,2,3,5,6");
		String result = EnergyMonitor.httpPostGetApi(url, jsonparam, token, false);
		int i  = result.indexOf("body");
		String str = result.substring(i+6, result.length()-1);
		JSONArray list = JSON.parseArray(str);
		
		String url1 = "http://www.chinadny.com:8012/restful/Meter/GetMeterElectricDayData";
	    String time = DateUtils.addDay(new Date(), -1);
		net.sf.json.JSONObject jsonparam1 = new net.sf.json.JSONObject();
		jsonparam1.put("did","185511,185509,185510,185512,185513,185514");
		jsonparam1.put("sdt","2017-03-16");
		jsonparam1.put("edt","2017-03-16");
		String dayResult = EnergyMonitor.httpPostGetApi(url1, jsonparam1, token, false);
		int y  = dayResult.indexOf("body");
		String stry = dayResult.substring(y+6, dayResult.length()-1);
		JSONArray list2 = JSON.parseArray(stry);
		
		for(Object obj : list){
			JSONObject jobj = (JSONObject) obj;
			EquipEnergyMonitor e = new EquipEnergyMonitor();
			e.setEleFa(jobj.getString("elc_fa"));
			e.setEquipName(jobj.getString("name"));
			e.setPowAt(jobj.getString("pow_at"));
			e.setPowAa(jobj.getString("pow_aa"));
			e.setPowAb(jobj.getString("pow_ab"));
			e.setPowAc(jobj.getString("pow_ac"));
			if("绝缘8#".equals(e.getEquipName())){
				e.setEquipcode("442-225");
			}else if("护套4#".equals(e.getEquipName())){
				e.setEquipcode("442-206");
			}else if("4#成缆".equals(e.getEquipName())){
				e.setEquipcode("444-79");
			}else{
				e.setEquipcode("");
			}
			if(StringUtils.isNotEmpty(jobj.getString("dt"))){
				e.setCreateTime(DateUtils.convert(jobj.getString("dt"), DateUtils.DATE_TIME_SHORT_FORMAT2));
			}
			this.insert(e);
		}
		
		for(Object obj : list2){
			JSONObject jobj = (JSONObject) obj;
				EquipEnergyMonitor e = new EquipEnergyMonitor();
				e.setEfc(jobj.getString("efc"));
				e.setEquipName(jobj.getString("name"));
				e.setSumto(StringUtils.isNotEmpty(jobj.getString("to"))?jobj.getString("to"):"0");
				e.setPi(StringUtils.isNotEmpty(jobj.getString("pi"))?jobj.getString("pi"):"0");
				e.setPe(StringUtils.isNotEmpty(jobj.getString("pe"))?jobj.getString("pe"):"0");
				e.setFl(StringUtils.isNotEmpty(jobj.getString("fl"))?jobj.getString("fl"):"0");
				e.setVa(StringUtils.isNotEmpty(jobj.getString("va"))?jobj.getString("va"):"0");
				e.setCreateTime(DateUtils.convert(time));
				if("1".equals(e.getEfc())){
					equipEnergyMonitorDAO.updateMeterElectricDayData(e);
				}
		}
		
	}
    
    @Override
	public Map<String, Object> energyReceiptChart(String equipName,String startTime,String endTime) {
		Map<String, Object> result = new HashMap<String, Object>();
		EquipEnergyMonitor findparams = new EquipEnergyMonitor();
		findparams.setEquipName(equipName);
		findparams.setHistoryStartTime(startTime);
		findparams.setHistoryEndTime(endTime);
		List<EquipEnergyMonitor> list = equipEnergyMonitorDAO.findEnergyHistory(findparams);
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}
		List<Object[]> historyData = new ArrayList<Object[]>();
		for (EquipEnergyMonitor paramHis : list) {
			double powat = Double.parseDouble(StringUtils.isNotEmpty(paramHis.getPowAt())?paramHis.getPowAt():"0");
			Object[] array = new Object[2];
			array[1] = powat;
			array[0] = paramHis.getCreateTime();
			historyData.add(array);
		}
			result.put("realdata",historyData);
		return result;
		}

    public List<EquipEnergyMonitor> getEquipEnergyMonitor(Map<String, Object> findParams) {
		return equipEnergyMonitorDAO.getEquipEnergyMonitor(findParams);
	}

	@Override
	public List<EquipEnergyMonitor> getEquipEnergyLoad(
			Map<String, Object> findParams) {
		return equipEnergyMonitorDAO.getEquipEnergyLoad(findParams);
	}

	@Override
	public List<EquipEnergyMonitor> getEnergyQuantity(
			Map<String, Object> findParams) {
		return equipEnergyMonitorDAO.getEnergyQuantity(findParams);
	}

}
