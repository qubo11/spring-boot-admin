package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.impl.db.PropertyService;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.PropertyVo;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

/**
 * 属性Controller
 */
@Controller
@RequestMapping(value = "/admin/db/property")
public class PropertyController {

	private final String viewName = "admin/system/db/property/";

	@Autowired
	private PropertyService propertyService;

	/**
	 * 属性-管理页面
	 */
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage(String pojoId) {
		List<DataTableRowVo> dtrvs = propertyService.getPropertyDtrvs(pojoId);
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("pojoId", pojoId);
		modelAndView.addObject("propertys", dtrvs);
		return modelAndView;
	}

	/**
	 * 属性-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id, String pojoId) {
		PropertyVo property = propertyService.getPropertyById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("property", property);
		modelAndView.addObject("pojoId", pojoId);
		return modelAndView;
	}

	/**
	 * 属性-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		PropertyVo property = propertyService.getPropertyById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("property", property);
		return modelAndView;
	}

	/**
	 * 属性-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo save(PropertyVo propertyVo) throws AjaxRequestException {
		return propertyService.save(propertyVo);
	}

	/**
	 * 属性-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo update(PropertyVo propertyVo) throws AjaxRequestException {
		return propertyService.update(propertyVo);
	}

	/**
	 * 属性-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo delete(String id) throws AjaxRequestException {
		return propertyService.delete(id);
	}

	/**
	 * 属性-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return propertyService.deleteBatch(ids);
	}

}
