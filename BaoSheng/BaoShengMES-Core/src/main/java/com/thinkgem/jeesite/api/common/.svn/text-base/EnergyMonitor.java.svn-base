package com.thinkgem.jeesite.api.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class EnergyMonitor {
	public static void main(String... strings) {
		System.out.println("---------------");
		String url = "http://www.chinadny.com:8008/token";
		String entity = "client_id=app_user&client_secret=xldny_app_2016&grant_type=client_credentials";
		String tokenstr = httpPostGetToken(url, "", entity, false);
		System.out.println(tokenstr);
		//这里
		String token = JSONObject.fromObject(tokenstr).getString("access_token");
		
		JSONObject jsonparam = new JSONObject();
//		jsonparam.put("uid", "126296");
		jsonparam.put("sdt", "2016-09-01 00:00");
		jsonparam.put("edt", "2016-09-01 16:00");
		jsonparam.put("did", "185511");
		jsonparam.put("di", "1,2,5,6");
//		jsonparam.put("un", "bskj");
//		jsonparam.put("pw", "123456");
		url = "http://www.chinadny.com:8008/restful/Meter/GetMeterCurveData";
//		url = "http://www.chinadny.com:8008/restful/Meter/GetMeterRealData";
		String result = httpPostGetApi(url, jsonparam, token, false);
		System.out.println(result);
	}

	/**
	 * posth获取token请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @param noNeedResponse
	 *            不需要返回结果
	 * @return
	 */
	public static String httpPostGetToken(String url, String token,
			String entity1, boolean noNeedResponse) {
		System.out.println("--2--");
		// post请求返回结果
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String jsonResult = null;
		try {
			url = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpPost method = new HttpPost(url);

		try {
			if (null != entity1) {
				// 解决中文乱码问题
				StringEntity entity = null;
				entity = new StringEntity(entity1, "utf-8");
				System.out.println("===>" + entity);

				String time = new SimpleDateFormat("yyyyMMdd")
						.format(new Date());
				String mad5key = MD5("app_user" + time + token);
				System.out.println("====md5+++" + mad5key);
				// method.setHeader("Content-Type",
				// "application/x-www-form-urlencoded;charset=utf-8");
				method.addHeader("Content-Type",
						"application/json;charset=utf-8");
				method.addHeader("time", time);
				method.addHeader("token", token);
				method.addHeader("key", mad5key);
				method.addHeader("Authorization", "Bearer " + token);
				method
						.addHeader(
								"USER_AGENT",
								"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");

				method.setEntity(entity);
			}
			System.out.println("--url-->" + url);
			HttpResponse result = httpClient.execute(method);
			System.out.println("code" + result.getStatusLine().getStatusCode());
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200
					|| result.getStatusLine().getStatusCode() == 401) {
				String str = "";
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					str = EntityUtils.toString(result.getEntity());
					if (noNeedResponse) {
						return null;
					}
					/** 把json字符串转换成json对象 **/
					jsonResult = str;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	public static String httpPostGetApi(String url, JSONObject jsonParam,
			String token, boolean noNeedResponse) {
		// post请求返回结果
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String jsonResult = null;
		try {
			url = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpPost method = new HttpPost(url);

		try {
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = null;
				entity = new StringEntity(jsonParam.toString(), "utf-8");
				String time = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date());
				String mad5key = MD5("app_user" + time + token);

				method.addHeader("Content-Type",
						"application/json;charset=utf-8");
				method.addHeader("time", time);
				method.addHeader("token", token);
				method.addHeader("key", mad5key);
				method.addHeader("Authorization", "Bearer " + token);
				method
						.addHeader(
								"USER_AGENT",
								"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");

				// entity.setContentEncoding("UTF-8");
				// entity.setContentType("application/json");
				method.setEntity(entity);
			}
			HttpResponse result = httpClient.execute(method);
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200
					|| result.getStatusLine().getStatusCode() == 401) {
				String str = "";
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					str = EntityUtils.toString(result.getEntity());
					if (noNeedResponse) {
						return null;
					}
					/** 把json字符串转换成json对象 **/
					jsonResult = str;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 提取字符串中的数字
	 * 
	 * @param s
	 * @return
	 */
	public static int StringToInt(String s) {
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(s);
		String ss = "";
		while (m.find()) {
			ss = ss + m.group();
		}
		return Integer.parseInt(ss);
	}
}
