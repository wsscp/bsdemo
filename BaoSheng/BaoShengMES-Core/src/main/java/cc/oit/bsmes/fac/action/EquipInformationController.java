package cc.oit.bsmes.fac.action;


import cc.oit.bsmes.bas.dao.EmployeeDAO;
import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.service.AttachmentService;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.fac.dao.EquipImageDAO;
import cc.oit.bsmes.fac.model.EquipImage;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.EquipRepairRecord;
import cc.oit.bsmes.fac.model.MaintainTemplate;
import cc.oit.bsmes.fac.model.ProductEquip;
import cc.oit.bsmes.fac.model.SparePart;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.MaintainTemplateService;
import cc.oit.bsmes.fac.service.ProductEquipService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import jxl.write.WriteException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/fac/equipInformation")
public class EquipInformationController {

    @Resource
    private EquipInfoService equipInfoService;
    @Resource
    private ProductEquipService productEquipService;
    @Resource
    private MaintainTemplateService maintainTemplateService;
    @Resource
    private EquipImageDAO equipImageDAO;
    @Resource
    private EmployeeDAO employeeDAO;
    @Resource
    private AttachmentService attachmentService;
    @Resource private EventInformationService eventInformationService;

    @Resource private DataDicService dataDicService;
    @RequestMapping(produces = "text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "fac");
        model.addAttribute("submoduleName", "equipInformation");
        return "fac.equipInformation";
    }

    @RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(HttpServletRequest request, @ModelAttribute EquipInfo param, @RequestParam(required = false) String sort,
                          @RequestParam int page, @RequestParam int start, @RequestParam int limit) {
    	param.setOrgCode(equipInfoService.getOrgCode());
    	TableView tableView = new TableView();
    	List<EquipInfo>  list=null;
    	int tatal = 0;
    	if(StringUtils.equals(param.getSection(), null)){
    		list = equipInfoService.find(param, start, limit, JSONArray.parseArray(sort, Sort.class));
    		tatal = equipInfoService.count(param);
    	}else{
    		list = equipInfoService.findInfo(param, start, limit, JSONArray.parseArray(sort, Sort.class));
    		tatal = equipInfoService.countInfo(param);
    	}
    	this.getMaintainer(list);
		tableView.setRows(list);
        tableView.setTotal(tatal);
        return tableView;
    }

    @ResponseBody
    @RequestMapping(value = "equipModel", method = RequestMethod.GET)
    public List<DataDic> equipModel() {
    	 List<DataDic> result = dataDicService.getCodeByTermsCode(TermsCodeType.DATA_EQUIP_MODEL);
    	 return result;
    }

    @ResponseBody
    @RequestMapping(value = "selectModel", method = RequestMethod.GET)
    public Map<String, MaintainTemplate> selectModel(@RequestParam(required = false) String model) {
        if (model == null) {
            return new HashMap<String, MaintainTemplate>();
        }
        Map<String, MaintainTemplate> maintainTemplates = maintainTemplateService.getByModel(model,maintainTemplateService.getOrgCode());
        
        return maintainTemplates;
    }

    /**
     * <p>字典查询下拉框</p> 
     * @author DingXintao
     * @date 2014-7-15 11:12:48
     * @return List<DataDic>
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "equipType", method = RequestMethod.GET)
    public List<DataDic> equipType() {
    	List<DataDic> equipTypeList = dataDicService.getCodeByTermsCode(TermsCodeType.EQUIP_TYPE);
    	return equipTypeList;
    }
    /**
     * 
     * <p>设备子类型</p> 
     * @author leiw
     * @date 2014-10-27 下午1:54:32
     * @return
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "subType", method = RequestMethod.GET)
    public List<DataDic> subType() {
    	List<DataDic> subTypeList = dataDicService.getCodeByTermsCode(TermsCodeType.SUB_TYPE);
    	return subTypeList;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public UpdateResult update(@RequestBody String jsonText) {
        EquipInfo equipInfo = JSON.parseObject(jsonText, EquipInfo.class);
        equipInfoService.update(equipInfo);

        UpdateResult updateResult = new UpdateResult();
        updateResult.addResult(equipInfo);
        return updateResult;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public UpdateResult save(@RequestBody String jsonText) {
        EquipInfo equipInfo = JSON.parseObject(jsonText, EquipInfo.class);
        equipInfo.setOrgCode(SessionUtils.getUser().getOrgCode());
        equipInfoService.insert(equipInfo);
        if (StringUtils.equals(equipInfo.getType().name(), "MAIN_EQUIPMENT")) {
            ProductEquip productEquip = new ProductEquip();
            productEquip.setEquipId(equipInfo.getId());
            productEquip.setProductLineId(equipInfo.getProductLineId());
            productEquip.setOrgCode(SessionUtils.getUser().getOrgCode());
            productEquip.setIsMain(true);
            productEquipService.insert(productEquip);
        }
        UpdateResult updateResult = new UpdateResult();
        updateResult.addResult(equipInfo);
        return updateResult;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable String id, @RequestBody String jsonText) {
        if (jsonText.startsWith("[")) {
            JSONArray jsonArray = JSON.parseArray(jsonText);
            List<String> list = new ArrayList<String>();
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;
                list.add((String) jsonObject.get("id"));
            }
            equipInfoService.deleteById(list);
        } else {
            equipInfoService.deleteById(id);
        }
    }

    @RequestMapping(value = "checkCode/{equipCode}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkUserCodeUnique(@PathVariable String equipCode) {
        JSONObject object = new JSONObject();
        EquipInfo equipInfo = equipInfoService.getByCode(equipCode, SessionUtils.getUser().getOrgCode());
        object.put("checkResult", equipInfo == null ? true : false);
        return object;
    }

    @RequestMapping(value="/export/{fileName}", method = RequestMethod.POST)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable String fileName,
                       @RequestParam String params,
                       @RequestParam(required = false) String queryParams) throws IOException, WriteException, InvocationTargetException, IllegalAccessException,ClassNotFoundException,NoSuchMethodException {
        JSONObject queryFilter  = JSONObject.parseObject(queryParams);
        JSONArray columns = JSONArray.parseArray(params);
        String sheetName = fileName;
        fileName = URLEncoder.encode(fileName, "UTF8") + ".xls";
        String userAgent = request.getHeader("User-Agent").toLowerCase(); // 获取用户代理
        if (userAgent.indexOf("msie") != -1) { // IE浏览器
            fileName = "filename=\"" + fileName + "\"";
        } else if (userAgent.indexOf("mozilla") != -1) { // firefox浏览器
            fileName = "filename*=UTF-8''" + fileName;
        }
        response.reset();
        response.setContentType("application/ms-excel");
        response.setHeader("Content-Disposition", "attachment;" + fileName);
        OutputStream os = response.getOutputStream();
        equipInfoService.export(os, sheetName, columns,queryFilter);
        os.close();
    }
    
	@RequestMapping(value = "/importEqipImage", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject importEqipImage(HttpServletRequest request,
			@RequestParam MultipartFile importFile,
			@RequestParam String eqipId,
			@RequestParam String imageName) throws Exception {
    	JSONObject result = new JSONObject();
    	String str = importFile.getOriginalFilename();
    	str = str.substring(str.indexOf('.')+1);
    	if(importFile.getSize()>5000000){
			result.put("message", "文件大小不能超过5M");
			result.put("success", false);
			return result;
		}
    	if(!"jpg".equals(str) && !"gif".equals(str) && !"png".equals(str) && !"bmp".equals(str)){
    		result.put("success", false);
    		result.put("message", "请选择图片类型");
    		return result;
    	}
    	String realFileName = UUID.randomUUID().toString();
    	attachmentService.upload(importFile, InterfaceDataType.MACH, "", realFileName);
        result.put("message", "设备图片上传成功");
		result.put("success", true);
		EquipImage e = new EquipImage();
		e.setEqipId(eqipId);
		e.setImagePath(realFileName);
		e.setImageName(imageName);
		equipImageDAO.insert(e);
    	return result;
    }
	@RequestMapping(value = "/showFile", method = RequestMethod.GET)
	@ResponseBody
	public JSON showFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String id, @RequestParam(required = false) String refId) throws IOException {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			if (StringUtils.isNotEmpty(id)) {
				attachmentService.downLoad(os, id);
			} else if (StringUtils.isNotEmpty(refId)) {
				attachmentService.downLoadOne(os, refId);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return JSONArrayUtils.ajaxJsonResponse(false, "文件读取错误");
		} finally {
			if (null != os) {
				os.close();
			}
		}
		return JSONArrayUtils.ajaxJsonResponse(true, "");
	}
	
	@ResponseBody
    @RequestMapping(value = "lookImage", method = RequestMethod.GET)
	public List<EquipImage> lookImage(@RequestParam String eqipId){
		return equipImageDAO.getByEqipId(eqipId);
	}
	
	@RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteImage(@RequestParam String id)
            throws Exception {
		JSONObject result = new JSONObject();
		EquipImage e = equipImageDAO.getById(id);
		attachmentService.delete(e.getImagePath());
		equipImageDAO.delete(id);
		result.put("success", true);
		return result;
	}
	@RequestMapping(value = "/saveEquipRepairRecord", method = RequestMethod.POST)
    @ResponseBody
    public JSON saveEquipRepairRecord(@RequestParam String jsonData,@RequestParam String isDone){
		List<EquipRepairRecord> r = JSONArray.parseArray(jsonData, EquipRepairRecord.class);
		EquipRepairRecord record = r.get(0);
		record.setSeq(equipInfoService.getRecordsByEventInfoId(record.getEventInfoId()));
		equipInfoService.insetRecord(record);
		EventInformation e = eventInformationService.getById(r.get(0).getEventInfoId());
		if("0".equals(isDone)){
			if(!EventStatus.INCOMPLETED.equals(e.getEventStatus())){
				e.setEventStatus(EventStatus.INCOMPLETED);
				eventInformationService.update(e);
			}
		}else{
			e.setEventStatus(EventStatus.PENDING);
			eventInformationService.update(e);
		}
		return JSONArrayUtils.ajaxJsonResponse(true, "成功");
	}
	
	@RequestMapping(value = "/saveSparePart", method = RequestMethod.POST)
    @ResponseBody
    public JSON saveSparePart(@RequestParam String newSparePartCode,@RequestParam String recordId,@RequestParam String eventInfoId,
    		@RequestParam String oldSparePartCode,@RequestParam String oldSparePartSituation,
    		@RequestParam String sparePartModel,@RequestParam String useSite,@RequestParam String quantity){
		SparePart s = new SparePart();
		s.setRepairedRecordId(recordId);
		s.setEventInfoId(eventInfoId);
		s.setNewSparePartCode(newSparePartCode);
		s.setOldSparePartCode(oldSparePartCode);
		s.setOldSparePartSituation(oldSparePartSituation);
		s.setUseSite(useSite);
		s.setSparePartModel(sparePartModel);
		s.setQuantity(quantity);
		equipInfoService.insetSparePart(s);
		return JSONArrayUtils.ajaxJsonResponse(true, "成功");
	}
	@RequestMapping(value = "/querySpareParts", method = RequestMethod.GET)
    @ResponseBody
    public List<SparePart> querySpareParts(@RequestParam String recordId){
		List<SparePart> list = equipInfoService.querySpareParts(recordId);
		return list;
	}
	
	@RequestMapping(value = "/deleteSparePart", method = RequestMethod.POST)
    @ResponseBody
    public JSON deleteSparePart(@RequestParam String id){
		equipInfoService.deleteSparePart(id);
		return JSONArrayUtils.ajaxJsonResponse(true, "成功");
	}
	@RequestMapping(value = "/getEventInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSON getEventInfo(@RequestParam String id){
		List<EquipRepairRecord> records = equipInfoService.getEventInfo(id);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("records", records);
		return JSONArrayUtils.ajaxJsonResponse(true, "成功", map);
	}
	
	public void getMaintainer(List<EquipInfo> list){
		Date now = new Date();
        for (EquipInfo equipInfo : list) {
        	List<String> codes = equipInfo.getMaintainers();
            equipInfoService.fixNextMaintainDate(now, equipInfo);
            String name = "";
            if(codes.size()>0){
            	for(String code : codes){
            		Employee employee = employeeDAO.getByUserCode(code);
            		if(employee==null){
            			continue;
            		}
            		if("".equals(name)){
            			name = employee.getName();
            		}else{
            			name = name +","+ employee.getName();
            		}
                }
            	equipInfo.setMaintainer(name);
            }
        }
	}
	
}
