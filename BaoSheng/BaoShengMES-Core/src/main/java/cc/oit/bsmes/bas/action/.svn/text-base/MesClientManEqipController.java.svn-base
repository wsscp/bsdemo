package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.bas.model.MesClientManEqip;
import cc.oit.bsmes.bas.service.MesClientManEqipService;
import cc.oit.bsmes.bas.service.MesClientService;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/bas/mesClientManEqip")
public class MesClientManEqipController {
	@Resource
	private MesClientManEqipService mesClientManEqipService;
	@Resource
	private MesClientService mesClientService;
	@Resource
	private EquipInfoService equipInfoService;
	
	
	@ResponseBody
	@RequestMapping(value="getClientName")		    
	public List<MesClient> getClientName(){
	    List<MesClient> result = mesClientService.getClientName();
	    return result;
	}
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public TableView list(@RequestParam int start,
    						@RequestParam int limit,
    						@RequestParam(required = false) String mesClientId) throws UnsupportedEncodingException{

    	List<MesClientManEqip> list = mesClientManEqipService.findByRequestMap(mesClientId, start, limit);
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(mesClientManEqipService.countByRequestMap(mesClientId));
    	return tableView;
    }
	
	@ResponseBody
	@RequestMapping(value="getEqipName")		    
	public List<JSONObject> getEqipName(){
	    List<EquipInfo> result = equipInfoService.getAllProductLine();

		Map<String ,MesClientManEqip> usedMap =new HashMap<String ,MesClientManEqip>();
		List<MesClientManEqip> usedlist = mesClientManEqipService.getAll();
		if(usedlist!=null)
		{
			for(int j=0;j<usedlist.size();j++)
			{
				MesClientManEqip object = usedlist.get(j);
				usedMap.put(object.getEqipId(),object);
			}
		}
		List<JSONObject> resultAll=new ArrayList<JSONObject>();
		JSONObject jsonObject = null;
		if(result!=null)
		{
			for(int i=0;i<result.size();i++)
			{
				EquipInfo object = result.get(i);
				if(usedMap.get(object.getId())==null)
				{
					jsonObject = new JSONObject();
					jsonObject.put("name","["+object.getCode()+"]"+object.getName());
					jsonObject.put("eqipId",object.getId());
					resultAll.add(jsonObject);
				}
			}
		}
		return resultAll;

	}
	
	
	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		MesClientManEqip mesClientManEqip =  JSON.parseObject(jsonText, MesClientManEqip.class);
		mesClientManEqip.setMesClientId(null);
		mesClientManEqipService.insert(mesClientManEqip);

		updateResult.addResult(mesClientManEqip);
		return updateResult;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	@ResponseBody
	public void update(@RequestParam String equipId,@PathVariable String id) throws ClassNotFoundException {
		MesClientManEqip mesClientManEqip =  new MesClientManEqip();
		mesClientManEqip.setId(id);
		mesClientManEqip.setEqipId(equipId);
		mesClientManEqipService.update(mesClientManEqip);
	}
	
	@RequestMapping(value="checkUnique/{mesClientId}/{eqipId}")
	@ResponseBody
	public void checkUnique(@PathVariable String mesClientId,@PathVariable String eqipId){
		MesClientManEqip mesClientManEqip = mesClientManEqipService.getByMesClientIdAndEqipId(mesClientId,eqipId);
		if(mesClientManEqip == null){
			mesClientManEqip = new MesClientManEqip();
			mesClientManEqip.setEqipId(eqipId);
			mesClientManEqip.setMesClientId(mesClientId);
			mesClientManEqipService.insert(mesClientManEqip);
		}else{
			throw new MESException("该设备已添加到该终端");
		}
	}
}
