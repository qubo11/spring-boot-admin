package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.impl.db.DaoService;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.DaoVo;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

@Controller
@RequestMapping(value = "/admin/db/dao")
public class DaoController {

	private final String viewName = "admin/system/db/dao/";

	@Autowired
	private DaoService daoService;

	/**
	 * Dao-管理页面
	 */
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage(String pojoId) {
		List<DataTableRowVo> dtrvs = daoService.getDaoDtrvs(pojoId);
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("daos", dtrvs);
		modelAndView.addObject("pojoId", pojoId);
		return modelAndView;
	}

	/**
	 * Dao-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id, String pojoId) {
		DaoVo dao = daoService.getDaoById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("dao", dao);
		modelAndView.addObject("pojoId", pojoId);
		return modelAndView;
	}

	/**
	 * Dao-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		DaoVo dao = daoService.getDaoById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("dao", dao);
		return modelAndView;
	}

	/**
	 * Dao-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo save(DaoVo daoVo) throws AjaxRequestException {
		return daoService.save(daoVo);
	}

	/**
	 * Dao-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo update(DaoVo daoVo) throws AjaxRequestException {
		return daoService.update(daoVo);
	}

	/**
	 * Dao-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo delete(String id) throws AjaxRequestException {
		return daoService.delete(id);
	}

	/**
	 * Dao-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return daoService.deleteBatch(ids);
	}

}
