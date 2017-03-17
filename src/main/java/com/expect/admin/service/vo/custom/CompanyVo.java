package com.expect.admin.service.vo.custom;

/**
 * 公司vo
 */
public class CompanyVo {

	private String id;
	private String name;// 公司名称
	private String code;// 公司代码
	private String address;// 公司地址
	private String description;// 公司描述
	private String parentCompanyId;// 父公司id
	private String parentCompanyName;// 父公司名称

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentCompanyId() {
		return parentCompanyId;
	}

	public void setParentCompanyId(String parentCompanyId) {
		this.parentCompanyId = parentCompanyId;
	}

	public String getParentCompanyName() {
		return parentCompanyName;
	}

	public void setParentCompanyName(String parentCompanyName) {
		this.parentCompanyName = parentCompanyName;
	}

}
