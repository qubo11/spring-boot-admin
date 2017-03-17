package com.expect.admin.service.vo.component.html.page.portlet;

import com.expect.custom.utils.DocumentGenernate;

/**
 * portlet头部工具栏
 * 
 * 主要功能:折叠/展开,全屏,移除
 */
public class ToolsVo {

	private StringBuilder htmlSb = new StringBuilder();

	private final String collapseHtml = "<a href='javascript:;' class='collapse' title='折叠/展开' data-original-title='折叠/展开'></a>";
	private final String fullscreenHtml = "<a href='javascript:;' class='fullscreen' title='全屏' data-original-title='全屏'></a>";
	private final String removeHtml = "<a href='javascript:;' class='remove' data-original-title='移除' title='移除'></a>";

	public void setCollapse() {
		htmlSb.append(collapseHtml);
	}

	public void setFullscreen() {
		htmlSb.append(fullscreenHtml);
	}

	public void setRemove() {
		htmlSb.append(removeHtml);
	}

	public void set(boolean isCollpase, boolean isFullscreen, boolean isRemove) {
		if (isCollpase) {
			setCollapse();
		}
		if (isFullscreen) {
			setFullscreen();
		}
		if (isRemove) {
			setRemove();
		}
	}

	public String getHtml() {
		DocumentGenernate dg = new DocumentGenernate();
		dg.createOpenElementTag("div", "class", "tools");
		dg.createText(htmlSb.toString());
		dg.createCloseElementTag("div");
		return dg.getHtml();
	}

	public String getHtml(boolean isCollpase, boolean isFullscreen, boolean isRemove) {
		if (isCollpase) {
			setCollapse();
		}
		if (isFullscreen) {
			setFullscreen();
		}
		if (isRemove) {
			setRemove();
		}
		return htmlSb.toString();
	}

}
