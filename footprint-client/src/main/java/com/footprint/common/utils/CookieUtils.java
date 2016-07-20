/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.footprint.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cookie工具类
 * <p>
 * 注意：在cookie的名或值中不能使用分号（;）、逗号（,）、等号（=）以及空格
 * </p>
 * 
 * @author hubin
 * @Date 2014-5-8
 */
public class CookieUtils {
	private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);
	
	/* 浏览器关闭时自动删除 */
	public final static int CLEAR_BROWSER_ON_CLOSED = -1;
	
	/* 立即删除 */
	public final static int CLEAR_IMMEDIATELY_REMOVE = 0;

	/**
	 * @Description 根据cookieName获取Cookie
	 * @param request
	 * @param cookieName
	 *            Cookie name
	 * @return Cookie
	 */
	public static Cookie findCookieByName(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieName)) {
				return cookies[i];
			}
		}
		return null;
	}

	/**
	 * 根据 cookieName 清空 Cookie【默认域下】
	 * 
	 * @param response
	 * @param cookieName
	 */
	public static void deleteCookieByName(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(CLEAR_IMMEDIATELY_REMOVE);
		response.addCookie(cookie);
	}

	/**
	 * @Description 清除指定doamin的所有Cookie
	 * @param request
	 * @param response
	 * @param cookieName
	 *            cookie name
	 * @param domain
	 *            Cookie所在的域
	 * @param path
	 *            Cookie 路径
	 * @return
	 */
	public static void clearAllCookie(HttpServletRequest request, HttpServletResponse response, String domain,
			String path) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			deleteCookie(response, cookies[i].getName(), domain, path);
		}
		logger.info("clearAllCookie in domain[{}]", domain);
	}


	/**
	 * @Description 清除指定Cookie 等同于 clearCookieByName(...)
	 *              该方法不判断Cookie是否存在,因此不对外暴露防止Cookie不存在异常.
	 * @param response
	 * @param cookieName
	 *            cookie name
	 * @param domain
	 *            Cookie所在的域
	 * @param path
	 *            Cookie 路径
	 * @return boolean
	 */
	public static void deleteCookie(HttpServletResponse response, String cookieName, String domain, String path) {
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(0);
		cookie.setDomain(domain);
		cookie.setPath(path);
		response.addCookie(cookie);
		logger.info("delete cookie[{}]", cookieName);
	}

	/**
	 * 添加 Cookie
	 * <p>
	 * 
	 * @param response
	 * @param domain
	 *            所在域
	 * @param path
	 *            域名路径
	 * @param name
	 *            名称
	 * @param value
	 *            内容
	 * @param maxAge
	 *            生命周期参数
	 * @param httpOnly
	 *            只读
	 * @param secured
	 *            Https协议下安全传输
	 */
	public static void addCookie(HttpServletResponse response, String domain, String path, String name, String value,
			int maxAge, boolean httpOnly, boolean secured) {
		Cookie cookie = new Cookie(name, value);
		/**
		 * 不设置该参数默认 当前所在域
		 */
		if (domain != null && !"".equals(domain)) {
			cookie.setDomain(domain);
		}
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);

		/** Cookie 只在Https协议下传输设置 */
		if (secured) {
			cookie.setSecure(true);
		}

		/** Cookie 只读设置 */
		if(httpOnly) {
			cookie.setHttpOnly(true);
		}
		response.addCookie(cookie);

	}

}
