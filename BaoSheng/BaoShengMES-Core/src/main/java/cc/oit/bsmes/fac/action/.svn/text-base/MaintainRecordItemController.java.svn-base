package cc.oit.bsmes.fac.action;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cc.oit.bsmes.common.constants.MaintainStatus;
import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.fac.model.MaintainRecord;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.MaintainRecordService;

/**
 * @author chanedi
 */
@Controller
@RequestMapping("/fac/maintainRecordItem")
public class MaintainRecordItemController {

    @Resource
    private MaintainRecordService maintainRecordService;
    @Resource
    private EquipInfoService equipInfoService;

    @RequestMapping(produces="text/html")
    public String index(HttpServletRequest request, @RequestParam String equipCode, @RequestParam String recordId, Model model) {
        model.addAttribute("moduleName", "fac");
        model.addAttribute("submoduleName", "maintainRecordItem");
        if ("-1".equals(recordId)) {
            MaintainRecord maintainRecord = new MaintainRecord();
            maintainRecord.setEquipCode(equipCode);
            maintainRecord.setStartTime(new Date());
            maintainRecord.setStatus(MaintainStatus.IN_PROGRESS);
            String type = request.getParameter("type");
            maintainRecord = maintainRecordService.insert(maintainRecord, MaintainTemplateType.valueOf(type));

            String queryStr = request.getQueryString().replaceAll("recordId=-1", "recordId=" + maintainRecord.getId());
            return "redirect:http://" + request.getLocalAddr() + ":" + request.getServerPort() + request.getRequestURI() + "?" + queryStr;
        } else {
            MaintainRecord record = maintainRecordService.getById(recordId);
            model.addAttribute("completed", record.getStatus() == MaintainStatus.FINISHED);
            if ("true".equals(request.getParameter("touch"))) {
                model.addAttribute("touch", true);
            }
            if (record.getStartTime() != null) {
                model.addAttribute("startTime", record.getStartTime().getTime());
            }
            if (record.getFinishTime() != null) {
                model.addAttribute("finishTime", record.getFinishTime().getTime());
            }
            return "fac.maintainRecordItem";
        }

    }
}