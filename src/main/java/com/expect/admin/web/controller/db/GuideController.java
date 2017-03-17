package com.expect.admin.web.controller.db;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/db/guide")
public class GuideController {

	private final String viewName = "admin/system/db/guide/";

	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage(String projectId,String functionId) {
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("projectId", projectId);
		modelAndView.addObject("functionId", functionId);
		return modelAndView;
	}

	@RequestMapping(value = "/step", method = RequestMethod.GET)
	public ModelAndView step(String projectId, String pojoId, Integer step) {
		ModelAndView modelAndView = new ModelAndView(viewName + "step");
		modelAndView.addObject("pojoId", pojoId);
		modelAndView.addObject("projectId", projectId);
		if (step == null) {
			step = 1;
		} else {
			step++;
		}
		modelAndView.addObject("step", step);
		return modelAndView;
	}

}
