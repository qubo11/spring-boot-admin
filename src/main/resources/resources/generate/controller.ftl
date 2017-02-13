<#if controller.packageName??>
package ${controller.packageName};
</#if>

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.*;

import ${business.packageName};
import ${valueObject.packageName};
import com.expect.admin.service.vo.component.*;
import com.expect.admin.web.exception.*;

/**
 * controller.comment
 */
@Controller
@RequestMapping(value = "${controller.requestMapping}")
public class ${controller.name}Controller {

	private final String viewName = "${controller.requestMapping}/";

	@Autowired
	private ${business.name} ${business.nameLowerCase};

	<#if business.paginate?? && business.paginate == '否'>
	/**
	 * 实体-管理页面
	 */
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage() {
		List<DataTableRowVo> dtrvs = ${business.nameLowerCase}.get${valueObject.name}Dtrvs();
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("${valueObject.nameLowerCase}", dtrvs);
		return modelAndView;
	}
	</#if>
	<#if business.paginate?? && business.paginate == '是'>
	/**
	 * 实体-管理页面
	 */
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage() {
		List<DataTableRowVo> dtrvs = ${business.nameLowerCase}.get${valueObject.name}Dtrvs();
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("${valueObject.nameLowerCase}", dtrvs);
		return modelAndView;
	}
	/**
	 * 实体-获取Datatable
	 */
	@RequestMapping(value = "/get${valueObject.name}Datatable", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableServerArrayVo get${valueObject.name}Datatable(String start, String length)throws AjaxRequestException {
		return ${business.nameLowerCase}.get${valueObject.name}Dtsrv(start,length);
	}
	</#if>

	/**
	 * 实体-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id) {
		${valueObject.name} ${valueObject.nameLowerCase} = ${business.nameLowerCase}.get${valueObject.name}ById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("${valueObject.nameLowerCase}", ${valueObject.nameLowerCase});
		return modelAndView;
	}

	/**
	 * 实体-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		${valueObject.name} ${valueObject.nameLowerCase} = ${business.nameLowerCase}.get${valueObject.name}ById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("${valueObject.nameLowerCase}", ${valueObject.nameLowerCase});
		return modelAndView;
	}

	/**
	 * 实体-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo save(${valueObject.name} ${valueObject.nameLowerCase}) throws AjaxRequestException {
		return ${business.nameLowerCase}.save(${valueObject.nameLowerCase});
	}

	/**
	 * 实体-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo update(${valueObject.name} ${valueObject.nameLowerCase}) throws AjaxRequestException {
		return ${business.nameLowerCase}.update(${valueObject.nameLowerCase});
	}

	/**
	 * 实体-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo delete(String id) throws AjaxRequestException {
		return ${business.nameLowerCase}.delete(id);
	}

	/**
	 * 实体-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return ${business.nameLowerCase}.deleteBatch(ids);
	}

}