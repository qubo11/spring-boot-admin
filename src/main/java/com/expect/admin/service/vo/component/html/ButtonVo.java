package com.expect.admin.service.vo.component.html;

import com.expect.admin.service.vo.component.BaseVo;

public class ButtonVo extends BaseVo {

	public final String disabled = "disabled";
	public final String outline = "btn-outline";
	public final static String Type_Default = "btn-default";
	public final static String Type_Primary = "btn-primary";
	public final static String Type_Success = "btn-success";
	public final static String Type_Info = "btn-info";
	public final static String Type_Warning = "btn-warning";
	public final static String Type_Danger = "btn-danger";
	public final static String Type_Link = "btn-link";

	public final static int Size_ExtraSmall = 0;
	public final static int Size_Small = 1;
	public final static int Size_Normal = 2;
	public final static int Size_Larger = 3;

	private StringBuilder className = new StringBuilder();
	private StringBuilder text = new StringBuilder();
	private StringBuilder otherAttr = new StringBuilder();
	private StringBuilder icon = new StringBuilder();
	private boolean isDisabled;
	private boolean isOutline;

	public String getButton() {
		String button = "<button type='button' class='btn " + className + "' " + otherAttr + "> " + icon + text
				+ "</button>";
		return button;
	}

	/**
	 * @param isButton
	 *            false:代表标签是a,true:代表标签是button
	 */
	public String getButton(boolean isButton) {
		if (isButton) {
			return getButton();
		} else {
			String button = "<a href='javascript:;' class='btn " + className + "' " + otherAttr + "> " + icon + text + "</a>";
			return button;
		}
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public boolean isOutline() {
		return isOutline;
	}

	public static class Builder {

		private ButtonVo buttonVo;

		public Builder() {
			buttonVo = new ButtonVo();
		}

		public Builder setClassName(String className) {
			buttonVo.className.append(className);
			return this;
		}

		public Builder setText(String text) {
			buttonVo.text.append(text);
			return this;
		}

		public Builder setOtherAttr(String otherAttr) {
			buttonVo.otherAttr.append(otherAttr);
			return this;
		}

		public Builder setSize(int size) {
			String sizeClassName = "";
			switch (size) {
			case Size_ExtraSmall:
				sizeClassName = " btn-xs";
				break;
			case Size_Small:
				sizeClassName = " btn-sm";
				break;
			case Size_Normal:
				break;
			case Size_Larger:
				sizeClassName = " btn-lg";
				break;
			}
			setClassName(sizeClassName);
			return this;
		}

		public Builder setDisabled(boolean isDisabled) {
			buttonVo.isDisabled = isDisabled;
			if (isDisabled) {
				buttonVo.className.append(buttonVo.disabled);
			}
			return this;
		}

		public Builder setOutline(boolean isOutline) {
			buttonVo.isOutline = isOutline;
			if (isOutline) {
				buttonVo.className.append(buttonVo.outline);
			}
			return this;
		}

		public Builder setIcon(String iconClass) {
			buttonVo.icon.append("<i class='");
			buttonVo.icon.append(iconClass);
			buttonVo.icon.append("'></i>");
			return this;
		}

		public ButtonVo create() {
			return buttonVo;
		}
	}

}
