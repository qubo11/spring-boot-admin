package com.expect.admin.service.vo.component.html.page;

import java.util.List;

import com.expect.admin.service.vo.component.html.page.portlet.PortletVo;

/**
 * 页面vo
 */
public class PageVo {

	private String name;
	private List<String> navs;
	private List<PortletVo> protlets;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getNavs() {
		return navs;
	}

	public void setNavs(List<String> navs) {
		this.navs = navs;
	}

	public List<PortletVo> getProtlets() {
		return protlets;
	}

	public void setProtlets(List<PortletVo> protlets) {
		this.protlets = protlets;
	}

}
