package com.expect.admin.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.expect.admin.plugins.ueditor.ActionEnter;
import com.expect.admin.web.exception.AjaxRequest;
import com.expect.admin.web.exception.AjaxRequestException;

@Controller
@RequestMapping("/ueditor")
public class UEditorController {

	@Autowired
	private ActionEnter actionEnter;

	@RequestMapping("/init")
	@ResponseBody
	@AjaxRequest
	public String index(MultipartFile upfile, HttpServletRequest request) throws AjaxRequestException {
		String rootPath = UEditorController.class.getResource("/").getPath();
		rootPath = rootPath + "static" + File.separator + "plugins";
		actionEnter.init(request, rootPath);
		String result = null;
		try {
			result = actionEnter.exec(upfile);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

}
