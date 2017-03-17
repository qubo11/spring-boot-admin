package com.expect.admin.web.controller.custom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.contants.DataTableLocal;
import com.expect.admin.service.impl.custom.IconService;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.IconVo;
import com.expect.admin.web.interceptor.PageEnter;
import com.expect.custom.config.Settings;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.web.exception.AjaxRequest;
import com.expect.custom.web.exception.AjaxRequestException;

/**
 * 图标管理Controller
 */
@Controller
@RequestMapping("/admin/icon")
public class IconController {

	private final String viewName = "admin/system/custom/icon/";

	@Autowired
	private IconService iconService;
	@Autowired
	private Settings settings;

	/**
	 * 图标-管理页面
	 */
	@PageEnter
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage() {
		List<DataTableRowVo> dtrvs = iconService.getIconDtrsv();
		DataTableLocal.set(dtrvs);
		return new ModelAndView(viewName + "manage");
	}

	/**
	 * 图标-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView iconFormPage(String id) {
		IconVo icon = iconService.getIconById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form/form");
		modelAndView.addObject("icon", icon);
		modelAndView.addObject("path", settings.getIconPath());
		return modelAndView;
	}

	/**
	 * 图标-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(IconVo iconVo)throws AjaxRequestException {
		return iconService.save(iconVo);
	}

	/**
	 * 图标-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(IconVo iconVo) throws AjaxRequestException {
		return iconService.update(iconVo);
	}

	/**
	 * 图标-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return iconService.delete(id);
	}

	/**
	 * 图标-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return iconService.deleteBatch(ids);
	}

}
