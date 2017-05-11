package cc.oit.bsmes.common.cookie;

import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieMachineResolver extends CookieGenerator {

	public CookieGenerator macCookieGenerator;
	public CookieGenerator ipCookieGenerator;
	public static final String IP_NAME = "ip";
	public static final String MAC_NAME = "mac";
	public static final String COOKIE_NAME = "ip_mac";
	public static final String COOKIE_VALUE_SEPARATOR = "_";
	private String mac;
	private String ip;

	public CookieMachineResolver() {
		setCookieName(COOKIE_NAME);
	}

	public String getMac(HttpServletRequest request, HttpServletResponse response) {
		parseIfNecessary(request, response);
		return mac;
	}

	public String getIp(HttpServletRequest request, HttpServletResponse response) {
		parseIfNecessary(request, response);
		return ip;
	}

	private void parseIfNecessary(HttpServletRequest request, HttpServletResponse response) {
		if (mac != null && ip != null) {
			return;
		}
		if (parseRequestParam(request, response)) {
			return;
		}
		
		if (!parseCookie(request)) {
			//TODO 
			//throw new MESException("wip.getIpMacFailed");
		}
	}
	
	/*
	 *  如果request中有值则需解析并加入cookie
	 */
	private boolean parseRequestParam(HttpServletRequest request, HttpServletResponse response) {
		String mac = request.getParameter(MAC_NAME);
		String ip = request.getParameter(IP_NAME);
        if (mac != null && ip != null) {
			this.mac = mac;
			this.ip = ip;
			addCookie(response, ip + COOKIE_VALUE_SEPARATOR + mac);
			return true;
		}
		return false;
	}
	
	private boolean parseCookie(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, getCookieName());
		if (cookie == null) {
			return false;
		}
		String value = cookie.getValue();
		if (value == null) {
			return false;
		}
		
		String[] values = value.split(COOKIE_VALUE_SEPARATOR, 2);
		this.ip = values[0];
		this.mac = values[1];
		return true;
	}

}
