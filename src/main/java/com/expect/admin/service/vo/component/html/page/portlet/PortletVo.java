package com.expect.admin.service.vo.component.html.page.portlet;

import java.util.ArrayList;
import java.util.List;

/**
 * portlet
 */
public class PortletVo {

	public static final int COLOR_RED = 1;// 红
	public static final int COLOR_BLUE = 2;// 蓝
	public static final int COLOR_YELLOW = 3;// 黄
	public static final int COLOR_GREEN = 4;// 绿
	public static final int COLOR_PURPLE = 5;// 紫

	public static final int STYLE_BOX = 1;// box样式
	public static final int STYLE_LIGHT = 2;// light样式
	public static final int STYLE_SOLID = 3;// solid样式

	private CaptionVo caption = new CaptionVo();// 标题
	private List<ActionVo> actions=new ArrayList<>();// actions按钮
	private int style = 1;// 样式,1:box;2:light;3.solid
	private StringBuilder classAttrs = new StringBuilder();// class属性
	private StringBuilder type = new StringBuilder();// portlet类型,1:普通;2:tab
	private StringBuilder templateName = new StringBuilder();// 模版名称
	private int width = 12;// 宽度

	public PortletVo() {
		classAttrs.append("portlet");
	}

	private void addAction(ActionVo action) {
		this.actions.add(action);
	}

	private void addClassAttr(String classAttr) {
		classAttrs.append(classAttr);
	}

	private void setTemplateName(String templateName) {
		this.templateName.append(templateName);
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getCaptionHtml() {
		String html = "<div class='caption'><i class='" + caption.getIcon()
				+ "'></i><span class='caption-subject bold uppercase'>" + caption.getTitle()
				+ "</span><span class='caption-helper'>" + caption.getSubTitle() + "</span></div>";
		return html;
	}

	public void getActionsHtml() {
		String html = "<div class='actions'></div>";
	}

	public static class Builder {

		private PortletVo portlet;

		public Builder() {
			portlet = new PortletVo();
		}

		/**
		 * 设置标题
		 * 
		 * @param title
		 *            标题
		 */
		public Builder setTitle(String title) {
			portlet.caption.setTitle(title);
			return this;
		}

		/**
		 * 设置子标题
		 * 
		 * @param subTitle
		 *            子标题
		 */
		public Builder setSubTitle(String subTitle) {
			portlet.caption.setSubTitle(subTitle);
			return this;
		}

		/**
		 * 设置icon
		 * 
		 * @param icon
		 *            图标样式
		 */
		public Builder setIcon(String icon) {
			portlet.caption.setIcon(icon);
			return this;
		}

		/**
		 * 设置样式类型
		 * 
		 * @param style
		 *            样式
		 */
		public Builder setStyle(int style) {
			portlet.setStyle(style);
			switch (style) {
			case STYLE_BOX:
				portlet.addClassAttr(" box");
				break;
			case STYLE_LIGHT:
				portlet.addClassAttr(" light");
				break;
			case STYLE_SOLID:
				portlet.addClassAttr(" solid");
				break;
			}
			return this;
		}

		/**
		 * 设置颜色
		 * 
		 * @param color的css
		 */
		public Builder setColor(String color) {
			portlet.addClassAttr(" " + color);
			return this;
		}

		/**
		 * 设置颜色
		 * 
		 * @param 颜色,1:red,2:blue,3:yellow,4:green,5:purple
		 */
		public Builder setColor(int color) {
			switch (color) {
			case COLOR_RED:
				portlet.addClassAttr(" red");
				break;
			case COLOR_BLUE:
				portlet.addClassAttr(" blue");
				break;
			case COLOR_YELLOW:
				portlet.addClassAttr(" yellow");
				break;
			case COLOR_GREEN:
				portlet.addClassAttr(" green");
				break;
			case COLOR_PURPLE:
				portlet.addClassAttr(" purple");
				break;
			}
			return this;
		}

		/**
		 * 设置模版名称
		 * 
		 * @param templateName
		 *            模版名称
		 */
		public Builder setTemplateName(String templateName) {
			portlet.setTemplateName(templateName);
			return this;
		}

		/**
		 * 增加action
		 * 
		 * @param action
		 *            按钮
		 */
		public Builder addAction(ActionVo action) {
			portlet.addAction(action);
			return this;
		}

		/**
		 * 设置宽度
		 * 
		 * @param width
		 *            宽度(1-12)
		 */
		public Builder setWidth(int width) {
			portlet.setWidth(width);
			return this;
		}

		public PortletVo create() {
			return this.portlet;
		}

	}

}
