package com.expect.admin.web.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.vo.component.ResultVo;

/**
 * 全局错误处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 普通页面跳转异常处理
	 */
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		logger.error("访问路径：" + req.getRequestURL(), e);
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", "发生系统异常，请与管理员联系");
		mav.setViewName("admin/error");
		return mav;
	}

	/**
	 * Ajax请求的异常处理
	 */
	@ExceptionHandler(value = AjaxRequestException.class)
	@ResponseBody
	public ResultVo ajaxErrorHandler(HttpServletRequest req, AjaxRequestException e) throws Exception {
		logger.error("访问路径：" + req.getRequestURL(), e);
		ResultVo rv = new ResultVo();
		rv.setResult(false);
		rv.setMessage("发生系统异常，请与管理员联系");
		return rv;
	}

}
