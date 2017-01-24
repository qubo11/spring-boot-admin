package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.impl.db.ValueObjectService;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ValueObjectVo;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

/**
 * 值对象 Controller
 */
@Controller
@RequestMapping(value = "/admin/db/valueObject")
public class ValueObjectController {

	private final String viewName = "admin/system/db/valueObject/";

	@Autowired
	private ValueObjectService valueObjectService;

	/**
	 * 值对象-管理页面
	 */
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage() {
		List<DataTableRowVo> dtrvs = valueObjectService.getValueObjectDtrvs();
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("valueObjects", dtrvs);
		return modelAndView;
	}

	/**
	 * 值对象-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id) {
		ValueObjectVo valueObject = valueObjectService.getValueObjectById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("valueObject", valueObject);
		return modelAndView;
	}

	/**
	 * 值对象-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		ValueObjectVo valueObject = valueObjectService.getValueObjectById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("valueObject", valueObject);
		return modelAndView;
	}

	/**
	 * 值对象-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo save(ValueObjectVo valueObjectVo) throws AjaxRequestException {
		return valueObjectService.save(valueObjectVo);
	}

	/**
	 * 值对象-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo update(ValueObjectVo valueObjectVo) throws AjaxRequestException {
		return valueObjectService.update(valueObjectVo);
	}

	/**
	 * 值对象-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo delete(String id) throws AjaxRequestException {
		return valueObjectService.delete(id);
	}

	/**
	 * 值对象-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return valueObjectService.deleteBatch(ids);
	}

}
