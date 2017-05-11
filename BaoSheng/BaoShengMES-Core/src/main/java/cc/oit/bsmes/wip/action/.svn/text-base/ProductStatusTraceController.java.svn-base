package cc.oit.bsmes.wip.action;

import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.ProductStatus;
import cc.oit.bsmes.wip.service.ProductStatusService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jxl.write.WriteException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;
/**
 * 
 * 生产状态报表
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-3 上午10:56:34
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/productStatusTrace")
public class ProductStatusTraceController {
	@Resource private ProductStatusService productStatusService;
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "productStatusTrace");
        return "wip.productStatusTrace"; 
    }
	@RequestMapping
    @ResponseBody
    public TableView list(HttpServletRequest request,@ModelAttribute ProductStatus param, @RequestParam String sort, 
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit)  {
		param.setModifyUserCode(SessionUtils.getUser().getUserCode()); 
		param.setOrgCode(SessionUtils.getUser().getOrgCode());
		List<ProductStatus> list = productStatusService.find(param, start, limit,null);
		for(int i=0;i<list.size();i++){
			ProductStatus productStatus=list.get(i);
			if(productStatus.getCol()!=null){
				list.get(i).setColor(productStatus.getCol().toString());
			}
		}
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(productStatusService.count(param));
    	return tableView;
    }
	
	@RequestMapping(value="/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable String fileName,
                       @RequestParam String params,
                       @RequestParam(required = false) String queryParams) throws IOException, WriteException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
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
        productStatusService.export(os, sheetName, columns,queryFilter);
        os.close();
	}
}
