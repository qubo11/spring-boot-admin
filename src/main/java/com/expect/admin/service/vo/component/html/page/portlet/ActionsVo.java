package com.expect.admin.service.vo.component.html.page.portlet;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.expect.admin.data.dataobject.custom.Authority;
import com.expect.admin.data.dataobject.custom.User;
import com.expect.admin.service.vo.component.html.ButtonVo;
import com.expect.custom.utils.DocumentGenernate;

/**
 * portlet头部的按钮
 */
public class ActionsVo {

	private StringBuilder htmlSb = new StringBuilder();// 除了以上操作，还可以附加

	public String getHtml() {
		DocumentGenernate dg = new DocumentGenernate();
		dg.createOpenElementTag("div", "class", "actions");
		dg.createText(htmlSb.toString());
		dg.createCloseElementTag("div");
		return dg.getHtml();
	}

	/**
	 * 设置默认的action按钮：增加/批量删除/导入/导出
	 */
	public void setDefaultAction() {
		User user = User.getUser();
		List<Authority> authorities = user.getUserAuthorities();
		if (!CollectionUtils.isEmpty(authorities)) {
			for (Authority authority : authorities) {
				String button = null;
				switch (authority.getCode()) {
				case Authority.AUTHORITY_INSERT:
				case Authority.AUTHORITY_BATCHDELETE:
				case Authority.AUTHORITY_SAVE:
				case Authority.AUTHORITY_QUERY:
				case Authority.AUTHORITY_EXPORT:
				case Authority.AUTHORITY_IMPORT:
					button = createAction(authority.getName(), authority.getCode() + "-button", authority.getIcon());
					break;
				}
				if (button != null) {
					add(" " + button);
				}
			}
		}
	}

	public void add(String button) {
		if (button != null) {
			htmlSb.append(button);
		}
	}

	/**
	 * 创建action
	 * 
	 * @param name
	 *            action名称
	 * @param className
	 *            action class属性
	 */
	public String createAction(String name, String className, String iconClass) {
		ButtonVo.Builder builder = new ButtonVo.Builder();
		builder.setClassName(ButtonVo.Type_Default + " " + className).setText(name).setIcon(iconClass)
				.setSize(ButtonVo.Size_Small);
		ButtonVo button = builder.create();
		return button.getButton(false);
	}

}
