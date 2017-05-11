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
package cc.oit.bsmes.common.filter;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登陆检测过滤器
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-5-7 上午10:58:58
 */
public class LoginFilter implements Filter {

	private UserService userService;

	public static final String USER = "user";
	public static final String PASSWORD = "password";

	/**
	 * @param filterConfig
	 * @throws ServletException
	 * @author QiuYangjun
	 * @date 2014-5-7 上午10:58:59
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * <p>
	 * session中检测是否登陆,如果没有检测是否有cookie,如果有cookie重建登陆信息
	 * </p>
	 * 
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 * @author QiuYangjun
	 * @date 2014-5-7 上午10:58:59
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		// 不过滤的uri
		String[] notFilter = new String[] { "login", "logout", "interfaceVF", "webClient" }; // "terminal/refresh"

		// 如果处理HTTP请求，并且需要访问诸如getHeader或getCookies等在ServletRequest中无法得到的方法，就要把此request对象构造成HttpServletRequest
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// 取得请求所对应的绝对路径:
		String currentURL = request.getRequestURI();
		String targetURL = StringUtils.substring(currentURL, StringUtils.indexOf(currentURL, "/", 1));
		// 是否过滤
		boolean doFilter = true;
		for (String s : notFilter) {
			if (targetURL.length() > 0 && StringUtils.indexOf(targetURL, s) != -1) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				doFilter = false;
				break;
			}
		}

		if (StringUtils.indexOf(targetURL, "terminal.action") != -1
				|| StringUtils.indexOf(targetURL, "materialMng.action") != -1) {
			if (userService == null) {
				userService = (UserService) ContextUtils.getBean(UserService.class);
			}

			String loginName = request.getParameter(USER);
			String password = request.getParameter(PASSWORD);

			User user = userService.login(loginName, password);
			if (user != null) {
				userService.userLoginSuccess(user, request, (HttpServletResponse) servletResponse);
			}
		}

		boolean loginSuccess = true;
		if (doFilter) {
			HttpSession session = request.getSession();
			if (session.getAttribute(WebConstants.USER_SESSION_KEY) == null) {
				// 如果session内用户信息为空
				loginSuccess = setLoginCookies(request, response, session);
			} else {
				SessionUtils.setUser(session);
				// loginSuccess = setLoginCookies(request,response,session);
				// if(!loginSuccess){
				// User user =
				// (User)session.getAttribute(WebConstants.USER_SESSION_KEY);
				// UserService userService = (UserService)
				// ContextUtils.getBean(UserService.class);
				// loginSuccess =
				// userService.userLoginSuccess(user,request,response);
				// }
			}
		}
        System.out.println("【currentURL】" + currentURL);
		if (loginSuccess) {
			if (StringUtils.equals(targetURL, "/") || StringUtils.equals(targetURL, "/login.jsp")) { // 已经登录了，并且是bsmes项目名结尾的
				response.sendRedirect(request.getContextPath() + "/index.action"); // 直接跳index.action
			} else {
				chain.doFilter(servletRequest, servletResponse);
			}
		} else {
			if (!StringUtils.equals(targetURL, "/")) { // 判断url是否是bsmes结尾(项目名结尾)，否则会多一步登录
				request.setAttribute("url", request.getRequestURL());
			}
			request.getRequestDispatcher("/login.jsp").forward(request, servletResponse);
		}
	}

	private boolean setLoginCookies(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		boolean loginSuccess = false;
		// 判断cookies内是否有用户登录信息,如果有用户登录信息,重建用户登录
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(WebConstants.USER_SESSION_KEY, cookie.getName())) {
					UserService userService = (UserService) ContextUtils.getBean(UserService.class);
					User user = userService.checkUserCodeUnique(cookie.getValue());
					if (user != null) {
						loginSuccess = userService.userLoginSuccess(user, request, response);
						break;
					}
				}
			}
		} else {
			loginSuccess = false;
		}
		return loginSuccess;
	}

	/**
	 * @author QiuYangjun
	 * @date 2014-5-7 上午10:58:59
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
