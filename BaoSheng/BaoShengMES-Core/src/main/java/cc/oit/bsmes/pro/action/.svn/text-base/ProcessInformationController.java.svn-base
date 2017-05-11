package cc.oit.bsmes.pro.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.service.ProcessInformationService;

/**
 * @author DingXintao
 *	
 */
@Controller
@RequestMapping("/pro/processInfo")
public class ProcessInformationController {
	
	@Resource
	private ProcessInformationService processInformationService;
	
	/**
	 * 查询所有工段
	 * @param request
	 * @return List<ProcessInformation>
	 */
	@ResponseBody
	@RequestMapping(value = "section")
	public List<ProcessInformation> section(HttpServletRequest request) {
		List<ProcessInformation> list = processInformationService.getSection();
		return list;
	}
	
    // 获取所有的工序信息 ：获取查询下拉框：工序信息
 	@ResponseBody
	@RequestMapping(value = "getAllProcess", method=RequestMethod.GET)
	public List<ProcessInformation> getAllProcess(HttpServletRequest request,@RequestParam String query) {
 		return processInformationService.findByCodeOrName(query);
	}
}