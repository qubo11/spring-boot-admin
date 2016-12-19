package com.expect.admin.service.vo.component.html.portlet;

import java.util.List;

import com.expect.admin.data.dataobject.User;
import com.expect.admin.service.vo.component.html.ButtonVo;

/**
 * Action操作的Vo
 */
public class ActionVo {

	private boolean save;// 保存
	private boolean delete;// 删除
	private boolean query;// 查询
	private boolean export;// 导入
	private StringBuilder htmlSb;// 除了以上操作，还可以附加

	public ActionVo() {

	}

	/**
	 * autoJudge自动判断，是否包含增加和批量删除按钮
	 */
	public ActionVo(boolean autoJudge) {
		if (autoJudge) {
			autoJudge();
		}
	}

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
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

	public void autoJudge() {
		if (User.getUser().hasDeleteAuthority()) {
			this.delete = true;
		}
		if (User.getUser().hasInsertAuthority()) {
			this.save = true;
		}
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

		public Builder setSave(boolean save) {
			actionVo.setSave(save);
			return this;
		}

		public Builder setDelete(boolean delete) {
			actionVo.setDelete(delete);
			return this;
		}

		public Builder autoJudge() {
			actionVo.autoJudge();
			return this;
		}

		public Builder setAutoJudge(boolean autoJudge) {
			if (autoJudge) {
				actionVo.autoJudge();
			}
			return this;
		}

		public Builder addExport() {
			actionVo.setExport(true);
			return this;
		}

		public Builder setExport(boolean export) {
			actionVo.setExport(export);
			return this;
		}

		public Builder addQuery() {
			actionVo.setQuery(true);
			return this;
		}

		public Builder setQuery(boolean query) {
			actionVo.setQuery(query);
			return this;
		}

		public Builder setButtons(List<ButtonVo> buttons) {
			for (ButtonVo button : buttons) {
				addButton(button);
			}
			return this;
		}

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
