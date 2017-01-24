package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.impl.db.PojoService;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.PojoVo;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

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
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage() {
		List<DataTableRowVo> dtrvs = pojoService.getPojoDtrvs();
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("pojos", dtrvs);
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
	 * 实体-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo save(PojoVo pojoVo) throws AjaxRequestException {
		return pojoService.save(pojoVo);
	}

	/**
	 * 实体-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo update(PojoVo pojoVo) throws AjaxRequestException {
		return pojoService.update(pojoVo);
	}

	/**
	 * 实体-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo delete(String id) throws AjaxRequestException {
		return pojoService.delete(id);
	}

	/**
	 * 实体-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return pojoService.deleteBatch(ids);
	}

	/**
	 * 下载代码
	 * 
	 * @param ids
	 *            实体id(使用,分隔)
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(String ids) {
//		RequestUtil.downloadFile(buffer, fileName, response);
	}
}
