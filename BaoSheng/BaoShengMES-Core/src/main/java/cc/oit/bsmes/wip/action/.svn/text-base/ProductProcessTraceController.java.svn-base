package cc.oit.bsmes.wip.action;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.ProductStatus;
import cc.oit.bsmes.wip.service.ProductStatusService;
/**
 * 
 * TODO(生产过程追溯)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-4 上午11:59:41
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/productProcessTrace")
public class ProductProcessTraceController {
	@Resource private ProductStatusService productStatusService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "productProcessTrace");
        return "wip.productProcessTrace"; 
    }
	@RequestMapping
    @ResponseBody
    public TableView list(HttpServletRequest request,@ModelAttribute ProductStatus param, @RequestParam String sort, 
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit)  {
		param.setModifyUserCode(SessionUtils.getUser().getUserCode());
		List<ProductStatus> list = productStatusService.getProductionProcess(param, start, limit,null);
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(productStatusService.countTotalProcess(param));
    	return tableView;
    }
	
	@RequestMapping(value="/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable String fileName,
                       @RequestParam String params,
                       @RequestParam(required = false) String queryParams) throws IOException, WriteException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		JSONObject queryFilter  = JSONObject.parseObject(queryParams);
		queryFilter.put("specification", "productProcessTrace");
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
        productStatusService.export(os, sheetName, columns,queryFilter);
        os.close();
	}
}
