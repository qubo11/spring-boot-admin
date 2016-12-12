package com.expect.admin.service.vo.component;

public class SimpleVo extends BaseVo {

	private String id;
	private String name;

	public SimpleVo() {
	}

	public SimpleVo(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
