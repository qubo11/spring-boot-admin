package com.expect.admin.service.vo.component.html.page.portlet;

import com.expect.admin.service.vo.component.html.ButtonVo;

/**
 * portlet头部的按钮
 */
public class ActionVo {

	private boolean insert;// 增加
	private boolean delete;// 删除
	private boolean query;// 查询
	private boolean export;// 导入
	private StringBuilder htmlSb;// 除了以上操作，还可以附加

	public ActionVo() {

	}

	public boolean isInsert() {
		return insert;
	}

	public void setInsert(boolean insert) {
		this.insert = insert;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isQuery() {
		return query;
	}

	public void setQuery(boolean query) {
		this.query = query;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public StringBuilder getHtmlSb() {
		return htmlSb;
	}

	public void setHtmlSb(StringBuilder htmlSb) {
		this.htmlSb = htmlSb;
	}

	public String getHtml() {
		if (htmlSb != null) {
			return htmlSb.toString();
		}
		return null;
	}

	public void appendHtml(String html) {
		htmlSb.append(html);
	}

	public static class Builder {

		private ActionVo actionVo;

		public Builder() {
			actionVo = new ActionVo();
		}

		/**
		 * 增加按钮
		 */
		public Builder addInsert() {
			actionVo.setInsert(true);
			return this;
		}

		/**
		 * 删除按钮
		 */
		public Builder addDelete() {
			actionVo.setDelete(true);
			return this;
		}

		/**
		 * 导出按钮
		 */
		public Builder addExport() {
			actionVo.setExport(true);
			return this;
		}

		/**
		 * 查询按钮
		 */
		public Builder addQuery() {
			actionVo.setQuery(true);
			return this;
		}

		/**
		 * 附加按钮的增加
		 */
		public Builder addButton(ButtonVo button) {
			if (actionVo.getHtmlSb() == null) {
				actionVo.setHtmlSb(new StringBuilder());
			}
			actionVo.appendHtml(button.getButton(false));
			return this;
		}

		public ActionVo create() {
			return actionVo;
		}

	}
}
