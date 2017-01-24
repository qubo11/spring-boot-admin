package com.expect.admin.service.vo.db;

/**
 * 项目vo
 */
public class ProjectVo {

	private String id;
	private String name;// 项目名称
	private String packageName;// 包名
	private String remark;// 项目备注

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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
