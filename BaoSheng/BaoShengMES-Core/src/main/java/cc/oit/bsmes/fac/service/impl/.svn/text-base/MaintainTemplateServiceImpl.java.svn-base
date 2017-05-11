package cc.oit.bsmes.fac.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.common.constants.MaintainTriggerType;
import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JxlUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.fac.dao.MaintainItemDAO;
import cc.oit.bsmes.fac.dao.MaintainTemplateDAO;
import cc.oit.bsmes.fac.model.MaintainItem;
import cc.oit.bsmes.fac.model.MaintainRecord;
import cc.oit.bsmes.fac.model.MaintainTemplate;
import cc.oit.bsmes.fac.service.MaintainItemService;
import cc.oit.bsmes.fac.service.MaintainRecordService;
import cc.oit.bsmes.fac.service.MaintainTemplateService;

/**
 * @author chanedi
 */
@Service
public class MaintainTemplateServiceImpl extends BaseServiceImpl<MaintainTemplate> implements MaintainTemplateService {

    @Resource
    private MaintainRecordService maintainRecordService;
    @Resource
    private MaintainTemplateDAO maintainTemplateDAO;
    @Resource
    private MaintainItemDAO maintainItemDAO;
    @Resource
    private MaintainItemService maintainItemService;

	/**
	 * 重写新增方法
	 * */
    @Override
    public void insert(MaintainTemplate maintainTemplate) throws DataCommitException {

		if (maintainTemplate.getType() != MaintainTemplateType.DAILY && maintainTemplate.getTriggerCycle() == 0) {
			maintainTemplate.setTriggerType(MaintainTriggerType.NATURE);
            maintainTemplate.setTriggerCycle(1);
        } else if (maintainTemplate.getType() == MaintainTemplateType.DAILY) {
			maintainTemplate.setTriggerType(MaintainTriggerType.NATURE);
            maintainTemplate.setTriggerCycle(0);
        }

		this.insertMaintainTemplate(maintainTemplate);
    }

    @Override
    public void deleteById(String id) throws DataCommitException {
        MaintainRecord findParams = new MaintainRecord();
        findParams.setTmplId(id);
        List<MaintainRecord> list = maintainRecordService.getByObj(findParams);
        if (list.size() > 0) {
            throw new MESException("fac.deleteTmplInUse");
        }

        maintainItemDAO.deleteByTmplId(id);
        super.deleteById(id);
    }

    @Override
    public Map<String, MaintainTemplate> getByModel(String model,String orgCode) {
        MaintainTemplate findParams = new MaintainTemplate();
        findParams.setModel(model);
        findParams.setOrgCode(orgCode);
        List<MaintainTemplate> list = maintainTemplateDAO.get(findParams);

        Map<String, MaintainTemplate> map = new HashMap<String, MaintainTemplate>();
        for (MaintainTemplate template : list) {
            map.put(template.getType().name(), template);
        }
        return map;
    }

	@Override
	public void initMaintItem(Workbook workbook, String orgCode) {
		Sheet[] sheets = workbook.getSheets();
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -10);
		for(Sheet sheet:sheets){
			String equipLine=sheet.getName();
			int maxRow = sheet.getRows();
			MaintainTemplate dailyTmpl = new MaintainTemplate();
			dailyTmpl.setOrgCode(orgCode);
			dailyTmpl.setType(MaintainTemplateType.DAILY);
			if(StringUtils.equalsIgnoreCase(equipLine, "挤塑")){
				 dailyTmpl.setModel("挤出");
			}else{
				dailyTmpl.setModel(equipLine);
			}
			dailyTmpl.setCreateTime(cal.getTime());
			maintainTemplateDAO.insert(dailyTmpl);
			
			for (int i = 5; i < maxRow+5; i++) {
				Cell[] cell=sheet.getRow(i);
				String comment=JxlUtils.getRealContents(cell[1]);
				if(StringUtils.equals(JxlUtils.getRealContents(cell[0]), "操作点检人员签名")){
					break;
				}
				if(StringUtils.isBlank(comment) || StringUtils.equals("设备开机前:", comment) || StringUtils.equals("设备开机后:", comment)){
					continue;
				}
				comment=JxlUtils.getRealContents(cell[1]);
				 MaintainItem dailyItem = new MaintainItem();
				 dailyItem.setTempId(dailyTmpl.getId());
				 dailyItem.setDescribe(comment);
				 maintainItemService.insert(dailyItem);
			}
		}
	
		
	}

	/**
	 * 新增[单个]
	 * */
	private void insertMaintainTemplate(MaintainTemplate t) throws DataCommitException {
		// 1、验证是否可以新增 runtime
		boolean hasNature = false;
		boolean hasRuntime = false;
		MaintainTemplate findParams = new MaintainTemplate();
		findParams.setModel(t.getModel());
		findParams.setType(t.getType());
		findParams.setOrgCode(SessionUtils.getUser().getOrgCode());

		if (null != t.getTriggerType()) {
			// 设备型号不能重复
			findParams.setTriggerType(t.getTriggerType());
			if (getByObj(findParams).size() > 0) {
				hasNature = true;
			}
		}
		if (null != t.getTriggerTypeH()) {
			// 设备型号不能重复
			findParams.setTriggerType(MaintainTriggerType.valueOf(t.getTriggerTypeH()));
			if (getByObj(findParams).size() > 0) {
				hasRuntime = true;
			}
		}
		if (hasNature && hasRuntime) {
			throw new MESException("fac.repeatTmpl");
		}

		int i = 0;
		// 2、 新增
		if (null != t.getTriggerType() && !hasNature) {
			maintainTemplateDAO.insert(t);
			i++;
		}
		if (null != t.getTriggerTypeH() && !hasRuntime) {
			t.setId(null);
			t.setTriggerCycle(t.getTriggerCycleH());
			t.setTriggerType(MaintainTriggerType.valueOf(t.getTriggerTypeH()));
			maintainTemplateDAO.insert(t);
			i++;
		}
		if (i == 0) { // 没有添加给予存在提示
			throw new MESException("fac.repeatTmpl");
		}

	}


}