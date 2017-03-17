package com.expect.admin.service.vo.component.html.page.portlet;

import org.apache.commons.lang3.StringUtils;

import com.expect.custom.utils.DocumentGenernate;

/**
 * portlet头部标题
 */
public class CaptionVo {

	public final String TYPE_BOXED = "Boxed";
	public final String TYPE_LIGHT = "Light";
	public final String TYPE_SOLID = "Solid";

	private String title;// 标题
	private String subTitle;// 子标题(标题描述)
	private String icon;// 图片(fa/icon)

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHtml(String type) {
		DocumentGenernate dg = new DocumentGenernate();
		dg.createOpenElementTag("div", "class", "caption");
		if (!StringUtils.isBlank(icon)) {
			dg.createOpenElementTag("i", "class", icon);
			dg.createCloseElementTag("i");
		}
		if (type != null) {
			switch (type) {
			case TYPE_BOXED:
				if (!StringUtils.isBlank(title)) {
					dg.createText(title);
				}
				break;
			case TYPE_LIGHT:
			case TYPE_SOLID:
				if (!StringUtils.isBlank(title)) {
					dg.createOpenElementTag("span", "class", "caption-subject bold uppercase");
					dg.createText(title);
					dg.createCloseElementTag("span");
				}
				if (!StringUtils.isBlank(subTitle)) {
					dg.createOpenElementTag("span", "class", "caption-helper");
					dg.createText(subTitle);
					dg.createCloseElementTag("span");
				}
				break;
			}
		} else {
			if (!StringUtils.isBlank(title)) {
				dg.createText(title);
			}
		}
		dg.createCloseElementTag("div");
		return dg.getHtml();
	}

}
