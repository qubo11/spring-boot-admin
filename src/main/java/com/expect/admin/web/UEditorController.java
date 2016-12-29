package com.expect.admin.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.expect.admin.plugins.ueditor.ActionEnter;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

@Controller
@RequestMapping("/ueditor")
public class UEditorController {

	@Autowired
	private ActionEnter actionEnter;

	@RequestMapping("/init")
	@ResponseBody
	@AjaxException
	public String index(MultipartFile upfile, HttpServletRequest request) throws AjaxRequestException{
		String rootPath = UEditorController.class.getResource("/").getPath();
		rootPath = rootPath + "static" + File.separator + "plugins";
		actionEnter.init(request, rootPath);
		String result=actionEnter.exec(upfile);
		return result;
	}

}
