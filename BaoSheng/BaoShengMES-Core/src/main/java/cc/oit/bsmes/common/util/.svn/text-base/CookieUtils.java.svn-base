package cc.oit.bsmes.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

public class CookieUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(CookieUtils.class);

	public static String getCookieValue(HttpServletRequest request,
			String cookieName) {
		if (null == request) {
			return "";
		}
		Cookie cookie = getCookie(request, cookieName);
		if (cookie == null) {
			return "";
		} else {
			try {
				return URLDecoder.decode(cookie.getValue(), "UTF-8");
			} catch (Exception e) {

				logger.error(e.getMessage(), e);
			}
		}
		return "";

	}

	public static void saveCookieValue(HttpServletRequest request,
			HttpServletResponse response, String cookieName, String cookieValue) {
		Cookie cookie = getCookie(request, cookieName);
		if (cookie != null) {
			// 修改cookie时间戳
			cookie.setValue(cookieValue);
		} else {
			// 重新new一个Cookie
			cookie = new javax.servlet.http.Cookie(cookieName, cookieValue);
		}
		cookie.setPath("/");// 同一个域名所有url cookie共享
		response.addCookie(cookie);
	}
	
	/**
	 * 失效Cookie
	 * 
	 * @author ningyu
	 * @date 2012-11-30 上午10:12:24
	 * @see
	 */
	public static void invalidateCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName) {		 
		// 失效掉token的cookie
		Cookie cookie_token = getCookie(request,cookieName);
		if (cookie_token != null) {
			cookie_token.setMaxAge(0);// 设置为0立即删除
			response.addCookie(cookie_token);
		} 
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		if (null == request) {
			return null;
		}
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		javax.servlet.http.Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (name.equals(cookies[i].getName())) {
					return cookies[i];
				}
			}
		}
		return null;
	}
}
