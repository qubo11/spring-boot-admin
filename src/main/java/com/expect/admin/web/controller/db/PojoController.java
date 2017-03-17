package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.impl.db.PojoService;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.PojoVo;
import com.expect.admin.web.interceptor.PageEnter;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.web.exception.AjaxRequest;
import com.expect.custom.web.exception.AjaxRequestException;

/**
 * 实体Controller
 */
@Controller
@RequestMapping(value = "/admin/db/pojo")
public class PojoController {

	private final String viewName = "admin/system/db/pojo/";

	@Autowired
	private PojoService pojoService;

	/**
	 * 实体-管理页面
	 */
	@PageEnter
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage(String functionId) {
		List<DataTableRowVo> dtrvs = pojoService.getPojoDtrvs();
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("pojos", dtrvs);
		modelAndView.addObject("functionId", functionId);
		return modelAndView;
	}

	/**
	 * 实体-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id) {
		PojoVo pojo = pojoService.getPojoById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("pojo", pojo);
		return modelAndView;
	}

	/**
	 * 实体-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		PojoVo pojo = pojoService.getPojoById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("pojo", pojo);
		return modelAndView;
	}

	/**
	 * 实体-选择实体页面
	 */
	@RequestMapping(value = "/selectPojoPage", method = RequestMethod.GET)
	public ModelAndView selectPojoPage(String projectId) {
		ModelAndView modelAndView = new ModelAndView(viewName + "selectPojo");
		List<PojoVo> pojos = pojoService.getPojoByProjectId(projectId);
		modelAndView.addObject("pojos", pojos);
		return modelAndView;
	}

	/**
	 * 实体-配置向导页面
	 */
	@RequestMapping(value = "/guidePage", method = RequestMethod.GET)
	public ModelAndView guidePage(String projectId, String id) {
		ModelAndView modelAndView = new ModelAndView(viewName + "guide");
		PojoVo pojo = pojoService.getPojoById(id);
		modelAndView.addObject("pojo", pojo);
		modelAndView.addObject("projectId", projectId);
		return modelAndView;
	}

	/**
	 * 实体-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(PojoVo pojoVo) throws AjaxRequestException {
		return pojoService.save(pojoVo);
	}

	/**
	 * 实体-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(PojoVo pojoVo) throws AjaxRequestException {
		return pojoService.update(pojoVo);
	}

	/**
	 * 实体-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return pojoService.delete(id);
	}

	/**
	 * 实体-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return pojoService.deleteBatch(ids);
	}

	/**
	 * 实体-保存或者更新
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo saveOrUpdate(PojoVo pojoVo) throws AjaxRequestException {
		return pojoService.saveOrUpdate(pojoVo);
	}

	/**
	 * 下载代码
	 * 
	 * @param ids
	 *            实体id(使用,分隔)
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(String ids) {
		// RequestUtil.downloadFile(buffer, fileName, response);
	}
}
