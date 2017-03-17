package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.impl.db.ControllerService;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ControllerVo;
import com.expect.admin.web.interceptor.PageEnter;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.web.exception.AjaxRequest;
import com.expect.custom.web.exception.AjaxRequestException;

/**
 * 控制层 Controller
 */
@Controller
@RequestMapping(value = "/admin/db/controller")
public class ControllerController {

	private final String viewName = "admin/system/db/controller/";

	@Autowired
	private ControllerService controllerService;

	/**
	 * 控制层-管理页面
	 */
	@PageEnter
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage(String pojoId, String functionId) {
		List<DataTableRowVo> dtrvs = controllerService.getControllerDtrvs();
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("controllers", dtrvs);
		modelAndView.addObject("pojoId", pojoId);
		modelAndView.addObject("functionId", functionId);
		return modelAndView;
	}

	/**
	 * 控制层-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id, String pojoId) {
		ControllerVo controller = controllerService.getControllerById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("controller", controller);
		modelAndView.addObject("pojoId", pojoId);
		return modelAndView;
	}

	/**
	 * 控制层-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		ControllerVo controller = controllerService.getControllerById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("controller", controller);
		return modelAndView;
	}

	/**
	 * 业务-配置向导页面
	 */
	@RequestMapping(value = "/guidePage", method = RequestMethod.GET)
	public ModelAndView guidePage(String pojoId) {
		ModelAndView modelAndView = new ModelAndView(viewName + "guide");
		ControllerVo controller = controllerService.getControllerByPojoId(pojoId);
		modelAndView.addObject("controller", controller);
		modelAndView.addObject("pojoId", pojoId);
		return modelAndView;
	}

	/**
	 * 控制层-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(ControllerVo controllerVo) throws AjaxRequestException {
		return controllerService.save(controllerVo);
	}

	/**
	 * 控制层-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(ControllerVo controllerVo) throws AjaxRequestException {
		return controllerService.update(controllerVo);
	}

	/**
	 * 控制层-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return controllerService.delete(id);
	}

	/**
	 * 控制层-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return controllerService.deleteBatch(ids);
	}

	/**
	 * 控制层-保存或者更新
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo saveOrUpdate(ControllerVo controllerVo) throws AjaxRequestException {
		return controllerService.saveOrUpdate(controllerVo);
	}

}
