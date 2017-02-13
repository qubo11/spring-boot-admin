package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.impl.db.ProjectService;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ProjectVo;
import com.expect.admin.web.exception.AjaxRequest;
import com.expect.admin.web.exception.AjaxRequestException;
import com.expect.admin.web.interceptor.role.RoleValidate;

/**
 * 项目Controller
 */
@Controller
@RequestMapping(value = "/admin/db/project")
public class ProjectController {

	private final String viewName = "admin/system/db/project/";

	@Autowired
	private ProjectService projectService;

	/**
	 * 项目-管理页面
	 */
	@RoleValidate
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage(String functionId) {
		List<DataTableRowVo> dtrvs = projectService.getProjectDtrvs();
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("projects", dtrvs);
		modelAndView.addObject("functionId", functionId);
		return modelAndView;
	}

	/**
	 * 项目-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id) {
		ProjectVo project = projectService.getProjectById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("project", project);
		return modelAndView;
	}

	/**
	 * 项目-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		ProjectVo project = projectService.getProjectById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("project", project);
		return modelAndView;
	}

	/**
	 * 项目-配置向导页面
	 */
	@RequestMapping(value = "/guidePage", method = RequestMethod.GET)
	public ModelAndView guidePage(String id) {
		ProjectVo project = projectService.getProjectById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "guide");
		modelAndView.addObject("project", project);
		return modelAndView;
	}

	/**
	 * 项目-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(ProjectVo projectVo) throws AjaxRequestException {
		return projectService.save(projectVo);
	}

	/**
	 * 项目-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(ProjectVo projectVo) throws AjaxRequestException {
		return projectService.update(projectVo);
	}

	/**
	 * 项目-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return projectService.delete(id);
	}

	/**
	 * 项目-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return projectService.deleteBatch(ids);
	}

	/**
	 * 项目-保存或者更新
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo saveOrUpdate(ProjectVo projectVo) throws AjaxRequestException {
		return projectService.saveOrUpdate(projectVo);
	}

	/**
	 * 下载代码
	 * 
	 * @param ids
	 *            项目id(使用,分隔)
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(String ids) {
		// RequestUtil.downloadFile(buffer, fileName, response);
	}
}
