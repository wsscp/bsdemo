/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */
package cc.oit.bsmes.common.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.bas.model.Resources;
import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.ResourcesService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.wip.dto.MethodReturnDto;

import com.alibaba.fastjson.JSON;

/**
 * 用户登录
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-5-6 下午3:51:55
 * @since
 * @version
 */
@Controller
public class LoginController {

	@Resource
	private UserService userService;
	@Resource
	private ResourcesService resourcesService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		// 1、获取当前用户的角色
	    HttpSession	session = request.getSession();
		List<Role> roleList = SessionUtils.getUserRoles(session);
		// 2、设置默认资源菜单
		Resources resources = new Resources();
		resources.setName("计划与排程");
		resources.setUri("pla/handSchedule.action");
		// 3、从角色中获取角色的默认菜单
		for(Role role :roleList){
			if(StringUtils.isNotEmpty(role.getDefaultResource())){
				resources = resourcesService.getById(role.getDefaultResource());
				break;
			}
		}
		request.setAttribute("resources1111", resources);
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam String loginName, @RequestParam String password, Model model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		MethodReturnDto dto = null;
		User user = userService.login(loginName, password);
		if (user != null) {
			if (WebConstants.NO.equals(user.getStatus())) {
				dto = new MethodReturnDto(false, java.net.URLEncoder.encode("用户被冻结！", "UTF-8"));
			} else {
				if(!userService.userLoginSuccess(user, request, response)){
					dto = new MethodReturnDto(false, java.net.URLEncoder.encode("用户没有权限访问！", "UTF-8"));
				}
				
			}
		} else {
			dto = new MethodReturnDto(false, java.net.URLEncoder.encode("用户名或密码错误！", "UTF-8"));
		}

		if (null == dto) {
			dto = new MethodReturnDto(true);
		}
		return JSON.toJSONString(dto);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute(WebConstants.USER_SESSION_KEY, null);
		session.setAttribute(WebConstants.USER_ROLES_SESSION_KEY, null);
		session.setAttribute(WebConstants.USER_RESOURCES_SESSION_KEY, null);

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(WebConstants.USER_SESSION_KEY)) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				break;
			}
		}

		return "redirect:/login.jsp";
	}

}
