package cc.oit.bsmes.pla.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cc.oit.bsmes.common.mybatis.Sort;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.pla.model.ProductSOP;
import cc.oit.bsmes.pla.service.ProductSOPService;
import cc.oit.bsmes.pla.service.TempService;

@Controller
@RequestMapping("/pla/productSOP")
public class ProductSOPController {

    @Resource
    private ProductSOPService productSOPService;

    @RequestMapping(produces = "text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "pla");
        model.addAttribute("submoduleName", "productSOP");
        return "pla.productSOP";
    }

    @RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(HttpServletRequest request, @ModelAttribute ProductSOP param, @RequestParam String sort,
                          @RequestParam int page, @RequestParam int start, @RequestParam int limit) {
        List list = productSOPService.find(param, start, limit, JSONArray.parseArray(sort, Sort.class));
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(productSOPService.count(param));
        return tableView;
    }

    @RequestMapping(value = "/calculateSOP", method = RequestMethod.GET)
    public String calculateSOP() throws InvocationTargetException, IllegalAccessException {
        TempService tempService = (TempService) ContextUtils.getBean(TempService.class);
        tempService.sop(SessionUtils.getUser().getOrgCode());
        return "redirect:/pla/productSOP.action";
    }
}
