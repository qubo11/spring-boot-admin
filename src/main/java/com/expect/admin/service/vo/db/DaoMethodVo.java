package com.expect.admin.service.vo.db;

import com.expect.admin.service.vo.component.html.SelectOptionVo;

/**
 * dao method vo
 */
public class DaoMethodVo {

	private String id;// 主键id
	private String name;// 方法名(完整的方法名)
	private String query;// 是否自定义查询语句
	private String modifying;// 是否使用update/delete语句
	private String querySentence;// 查询语句
	private String returnType;// 返回类型
	private String comment;// 注释
	private String daoId;// dao id
	private String daoName;// dao 名称
	private SelectOptionVo operationSov;// 操作的sov
	private SelectOptionVo propertySov;// 属性的sov
	public SelectOptionVo methodNameSov;// 方法名的sov

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

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getModifying() {
		return modifying;
	}

	public void setModifying(String modifying) {
		this.modifying = modifying;
	}

	public String getQuerySentence() {
		return querySentence;
	}

	public void setQuerySentence(String querySentence) {
		this.querySentence = querySentence;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDaoId() {
		return daoId;
	}

	public void setDaoId(String daoId) {
		this.daoId = daoId;
	}

	public String getDaoName() {
		return daoName;
	}

	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}

	public SelectOptionVo getOperationSov() {
		return operationSov;
	}

	public void setOperationSov(SelectOptionVo operationSov) {
		this.operationSov = operationSov;
	}

	public SelectOptionVo getPropertySov() {
		return propertySov;
	}

	public void setPropertySov(SelectOptionVo propertySov) {
		this.propertySov = propertySov;
	}

	public SelectOptionVo getMethodNameSov() {
		return methodNameSov;
	}

	public void setMethodNameSov(SelectOptionVo methodNameSov) {
		this.methodNameSov = methodNameSov;
	}

}
