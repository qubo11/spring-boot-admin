package com.expect.admin.service.vo.component;

public class SimpleVo extends BaseVo {

	private String id;
	private String value;

	public SimpleVo() {
	}

	public SimpleVo(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
