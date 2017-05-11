package cc.oit.bsmes.fac.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.action.BaseController;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import cc.oit.bsmes.common.mybatis.complexQuery.WithValueQueryParam;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.MaintainRecord;
import cc.oit.bsmes.fac.service.MaintainRecordService;

import com.alibaba.fastjson.JSONArray;

/**
 * @author chanedi
 */
@Controller
@RequestMapping("/fac/maintainRecord")
public class MaintainRecordController {

    @Resource
    private MaintainRecordService maintainRecordService;

    @RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "fac");
        model.addAttribute("submoduleName", "maintainRecord");

        return "fac.maintainRecord";
    }

    @RequestMapping
    @ResponseBody
    public TableView list(HttpServletRequest request, @RequestParam(required = false) String sort,
                          @RequestParam(required = false) Integer start, @RequestParam(required = false) Integer limit, @RequestParam(required = false) String equipCode) throws UnsupportedEncodingException, ParseException {
        // 设置findParams属性
        List<CustomQueryParam> queryParams = new ArrayList<CustomQueryParam>();
        queryParams.add(new WithValueQueryParam("equipCode", "=", URLDecoder.decode(equipCode, "utf-8")));
        String[] statuses = request.getParameterValues("status");
        if (statuses != null && statuses.length == 1 ) {
            queryParams.add(new WithValueQueryParam("status", "=", statuses[0]));
        }

        // 根据filter设置findParams属性
        BaseController.addFilterQueryParams(request, queryParams);

        // 查询
        List<?> list = maintainRecordService.query(queryParams, start, limit, JSONArray.parseArray(sort, Sort.class));
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(maintainRecordService.countQuery(queryParams));
        return tableView;
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        maintainRecordService.deleteById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/complete/{id}", method = RequestMethod.GET)
    public void complete(@PathVariable String id, HttpServletRequest request) {
        boolean touch = "true".equalsIgnoreCase(request.getParameter("touch"));
//        maintainRecordService.complete(id, touch);
    }

    @ResponseBody
    @RequestMapping(value = "/startTime/{startTime}", method = RequestMethod.POST)
    public void updateStartTime(@PathVariable Date startTime, HttpServletRequest request) {
        if (startTime.getTime() > new Date().getTime()) {
            throw new MESException("fac.maintainTime");
        }

        MaintainRecord record = maintainRecordService.getById(request.getParameter("recordId"));
        if (record.getFinishTime() != null && record.getFinishTime().getTime() < record.getStartTime().getTime()) {
            throw new MESException("fac.maintainTime");
        }
        record.setStartTime(startTime);
        maintainRecordService.update(record);
    }

    @ResponseBody
    @RequestMapping(value = "/finishTime/{finishTime}", method = RequestMethod.POST)
    public void updateFinishTime(@PathVariable Date finishTime, HttpServletRequest request) {
        MaintainRecord record = maintainRecordService.getById(request.getParameter("recordId"));
        if (finishTime.getTime() < record.getStartTime().getTime()) {
            throw new MESException("fac.maintainTime");
        }
        record.setFinishTime(finishTime);
        maintainRecordService.update(record);
    }

}