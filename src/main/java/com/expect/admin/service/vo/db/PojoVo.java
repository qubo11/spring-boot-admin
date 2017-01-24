package com.expect.admin.service.vo.db;

import java.util.List;

/**
 * 实体vo
 */
public class PojoVo {

	private String id;
	private String name;// 实体名
	private String packageName;// 包名
	private String tableName;// 对应的表名
	private String comment;// 注释
	private List<PropertyVo> propertyVos;

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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<PropertyVo> getPropertyVos() {
		return propertyVos;
	}

	public void setPropertyVos(List<PropertyVo> propertyVos) {
		this.propertyVos = propertyVos;
	}

}
