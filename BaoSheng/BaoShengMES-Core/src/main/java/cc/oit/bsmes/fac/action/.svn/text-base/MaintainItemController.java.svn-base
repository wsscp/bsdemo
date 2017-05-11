package cc.oit.bsmes.fac.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chanedi
 */
@Controller
@RequestMapping("/fac/maintainItem")
public class MaintainItemController {

    @RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "fac");
        model.addAttribute("submoduleName", "maintainItem");
        return "fac.maintainItem";
    }

}