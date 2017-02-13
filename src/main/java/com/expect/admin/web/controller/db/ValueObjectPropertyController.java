package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.impl.db.ValueObjectPropertyService;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ValueObjectPropertyVo;
import com.expect.admin.web.exception.AjaxRequest;
import com.expect.admin.web.exception.AjaxRequestException;
import com.expect.admin.web.interceptor.role.RoleValidate;

/**
 * 值对象属性 Controller
 */
@Controller
@RequestMapping(value = "/admin/db/valueObject/property")
public class ValueObjectPropertyController {

	private final String viewName = "admin/system/db/valueObject/property/";

	@Autowired
	private ValueObjectPropertyService valueObjectPropertyService;

	/**
	 * 值对象属性-管理页面
	 */
	@RoleValidate
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage(String valueObjectId, String functionId) {
		List<DataTableRowVo> dtrvs = valueObjectPropertyService.getValueObjectPropertyDtrvs(valueObjectId);
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("valueObjectPropertys", dtrvs);
		modelAndView.addObject("valueObjectId", valueObjectId);
		modelAndView.addObject("functionId", functionId);
		return modelAndView;
	}

	/**
	 * 值对象属性-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id, String valueObjectId) {
		ValueObjectPropertyVo valueObjectProperty = valueObjectPropertyService.getValueObjectPropertyById(id,
				valueObjectId);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("valueObjectProperty", valueObjectProperty);
		modelAndView.addObject("valueObjectId", valueObjectId);
		return modelAndView;
	}

	/**
	 * 值对象属性-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		ValueObjectPropertyVo valueObjectProperty = valueObjectPropertyService.getValueObjectPropertyById(id, null);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("valueObjectProperty", valueObjectProperty);
		return modelAndView;
	}

	/**
	 * 值对象属性-配置向导页面
	 */
	@RequestMapping(value = "/guidePage", method = RequestMethod.GET)
	public ModelAndView guidePage(String valueObjectId) {
		List<DataTableRowVo> dtrvs = valueObjectPropertyService.getValueObjectPropertyDtrvs(valueObjectId);
		ModelAndView modelAndView = new ModelAndView(viewName + "guide");
		modelAndView.addObject("valueObjectId", valueObjectId);
		modelAndView.addObject("valueObjectProperties", dtrvs);
		return modelAndView;
	}

	/**
	 * 值对象属性-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(ValueObjectPropertyVo valueObjectPropertyVo) throws AjaxRequestException {
		return valueObjectPropertyService.save(valueObjectPropertyVo);
	}

	/**
	 * 值对象属性-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(ValueObjectPropertyVo valueObjectPropertyVo) throws AjaxRequestException {
		return valueObjectPropertyService.update(valueObjectPropertyVo);
	}

	/**
	 * 值对象属性-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return valueObjectPropertyService.delete(id);
	}

	/**
	 * 值对象属性-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return valueObjectPropertyService.deleteBatch(ids);
	}

	/**
	 * 值对象属性-同步属性(把对应实体的属性(基本属性,不包括关联属性)，同步一份到值对象属性)
	 */
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo sync(String valueObjectId) throws AjaxRequestException {
		return valueObjectPropertyService.sync(valueObjectId);
	}

}
