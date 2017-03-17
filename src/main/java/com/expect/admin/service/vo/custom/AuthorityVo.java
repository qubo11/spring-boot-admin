package com.expect.admin.service.vo.custom;

/**
 * 权限vo
 */
public class AuthorityVo {

	private String id;// 权限id
	private String code;// 权限代码
	private String name;// 权限名称
	private String icon;// 如果对应按钮，可以加上按钮的图标
	private Integer sequence;// 顺序
	private String description;// 权限描述

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
