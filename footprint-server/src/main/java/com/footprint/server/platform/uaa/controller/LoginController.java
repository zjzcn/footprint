package com.footprint.server.platform.uaa.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.footprint.common.WebResult;
import com.footprint.common.captcha.service.CaptchaService;
import com.footprint.server.common.exception.BizException;

@Controller
@RequestMapping("uaa")
public class LoginController {
	
	@Autowired
	private CaptchaService captchaService;
	
	/**
	 * 登录
	 * 
	 * @throws IOException
	 */
	@RequestMapping("login")
	@ResponseBody
	public WebResult login(String username, String password, String captcha, HttpServletRequest req, HttpServletResponse resp) {
//		String challenge = captchaService.getChallengeInSession(req);
//		if (!captcha.equalsIgnoreCase(challenge)) {
//			return WebResult.newErrorResult("验证码错误");
//		}
		try {
			Subject currentUser = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password, false);
			currentUser.login(token);
			return WebResult.newResult();
		} catch (UnknownAccountException e) {
			return WebResult.newErrorResult("用户名错误");
		} catch (DisabledAccountException e) {
			return WebResult.newErrorResult("用户被禁用");
		} catch (IncorrectCredentialsException e) {
			return WebResult.newErrorResult("密码错误");
		} catch (AuthenticationException e) {
			throw new BizException("未知错误", e);
		}
	}

	@RequestMapping("test")
	@ResponseBody
	public WebResult test(String username, String password, String captcha, HttpServletRequest req) {
		WebResult result = WebResult.newResult();
		result.put("id", req.getSession().getId());
		return result;
	}
}
