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
import cc.oit.bsmes.bas.dao.EquipEnergyMonthMonitorDAO;
import cc.oit.bsmes.bas.model.EquipEnergyMonitor;
import cc.oit.bsmes.bas.model.EquipEnergyMonthMonitor;
import cc.oit.bsmes.bas.service.EquipEnergyMonitorService;
import cc.oit.bsmes.bas.service.EquipEnergyMonthMonitorService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.api.common.EnergyMonitor;

@Service
public class EquipEnergyMonthMonitorServiceImpl extends BaseServiceImpl<EquipEnergyMonthMonitor> implements EquipEnergyMonthMonitorService {

    @Resource
    private EquipEnergyMonthMonitorDAO equipEnergyMonthMonitorDAO;

    public List<EquipEnergyMonthMonitor> getEquipEnergyMonthLoad(Map<String, Object> findParams) {
		return equipEnergyMonthMonitorDAO.getEquipEnergyMonthLoad(findParams);
	}


	@Override
	public void insertMonthEquipEnergyMonitorInfo() {
		
		String url = "http://www.chinadny.com:8012/token";
		String entity = "client_id=app_user&client_secret=xldny_app_2016&grant_type=client_credentials";//
		String tokenstr = EnergyMonitor.httpPostGetToken(url, "", entity, false);
		String token = JSON.parseObject(tokenstr).getString("access_token");
		net.sf.json.JSONObject jsonparam = new net.sf.json.JSONObject();
		url = "http://www.chinadny.com:8012/restful/Meter/GetMeterElectricMonthData";
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		String time = formatter.format(currentTime);
		net.sf.json.JSONObject jsonparam1 = new net.sf.json.JSONObject();
		jsonparam1.put("did","185511,185509,185510,185512,185513,185514");
		jsonparam1.put("sdt",time);
		jsonparam1.put("edt",time);
		String dayResult = EnergyMonitor.httpPostGetApi(url, jsonparam1, token, false);
		int x  = dayResult.indexOf("body");
		String strx = dayResult.substring(x+6, dayResult.length()-1);
		JSONArray list = JSON.parseArray(strx);
		
		String url1 = "http://www.chinadny.com:8012/restful/Meter/GetMeterPowerMonthData";
		net.sf.json.JSONObject jsonparam2 = new net.sf.json.JSONObject();
		jsonparam2.put("did","185511,185509,185510,185512,185513,185514");
		jsonparam2.put("sdt",time);
		jsonparam2.put("edt",time);
		String result = EnergyMonitor.httpPostGetApi(url1, jsonparam2, token, false);
		int y  = result.indexOf("body");
		String stry = result.substring(y+6, result.length()-1);
		JSONArray list1 = JSON.parseArray(stry);
		
		
		for(Object obj : list){
			JSONObject jobj = (JSONObject) obj;
				EquipEnergyMonthMonitor e = new EquipEnergyMonthMonitor();
				e.setId(UUID.randomUUID().toString());
				e.setEfc(jobj.getString("efc"));
				e.setEquipName(jobj.getString("name"));
				e.setSumto(jobj.getString("to"));
				e.setPi(jobj.getString("pi"));
				e.setPe(jobj.getString("pe"));
				e.setFl(jobj.getString("fl"));
				e.setVa(jobj.getString("va"));
				if("绝缘8#".equals(e.getEquipName())){
					e.setEquipcode("442-225");
				}else if("护套4#".equals(e.getEquipName())){
					e.setEquipcode("442-206");
				}else if("4#成缆".equals(e.getEquipName())){
					e.setEquipcode("444-79");
				}else{
					e.setEquipcode("");
				}
				e.setCreateTime(DateUtils.convert(time));
				if("1".equals(e.getEfc())){
					this.insert(e);
				}
		}
		
		for(Object obj : list1){
			JSONObject jobj = (JSONObject) obj;
			EquipEnergyMonthMonitor e = new EquipEnergyMonthMonitor();
			e.setEfc(jobj.getString("efc"));
			if("185509".equals(jobj.getString("did"))){
				e.setEquipName("4#成缆");
			}else if("185510".equals(jobj.getString("did"))){
				e.setEquipName("115#硅橡胶");
			}else if("185511".equals(jobj.getString("did"))){
				e.setEquipName("绝缘8#");
			}else if("185512".equals(jobj.getString("did"))){
				e.setEquipName("护套4#");
			}else if("185513".equals(jobj.getString("did"))){
				e.setEquipName("高登1");
			}else if("185514".equals(jobj.getString("did"))){
				e.setEquipName("高登2");
			}
			e.setMaxPower(jobj.getString("max"));
			e.setMinPower(jobj.getString("min"));
			e.setAvgPower(jobj.getString("avg"));
			e.setDifr(jobj.getString("difr"));
			e.setAvgr(jobj.getString("avgr"));
			e.setCreateTime(DateUtils.convert(time));
			if("1".equals(e.getEfc())){
				equipEnergyMonthMonitorDAO.updateMonthPowerData(e);
			}
		}
		
	}


	@Override
	public List<EquipEnergyMonitor> getEnergyMonthQuantity(
			Map<String, Object> findParams) {
		return equipEnergyMonthMonitorDAO.getEnergyMonthQuantity(findParams);
	}

}
