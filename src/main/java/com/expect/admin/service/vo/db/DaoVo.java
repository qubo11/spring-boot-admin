package com.expect.admin.service.vo.db;

import java.util.List;

import com.expect.admin.service.vo.component.html.SelectOptionVo;

/**
 * dao vo
 */
public class DaoVo {

	private String id;// 主键
	private String name;// 名称
	private String packageName;// 包名
	private String inherit;// 继承类(JPARepository,CustomRepository)
	private String comment;// 注释
	private String pojoId;// 实体id
	private String pojoName;// 实体名称
	private List<DaoMethodVo> daoMethods;// dao方法
	private SelectOptionVo inheritSov;// 继承sov
	private SelectOptionVo pojoSov;// 实体sov

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

	public String getInherit() {
		return inherit;
	}

	public void setInherit(String inherit) {
		this.inherit = inherit;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPojoId() {
		return pojoId;
	}

	public void setPojoId(String pojoId) {
		this.pojoId = pojoId;
	}

	public String getPojoName() {
		return pojoName;
	}

	public void setPojoName(String pojoName) {
		this.pojoName = pojoName;
	}

	public List<DaoMethodVo> getDaoMethods() {
		return daoMethods;
	}

	public void setDaoMethods(List<DaoMethodVo> daoMethods) {
		this.daoMethods = daoMethods;
	}

	public SelectOptionVo getInheritSov() {
		return inheritSov;
	}

	public void setInheritSov(SelectOptionVo inheritSov) {
		this.inheritSov = inheritSov;
	}

	public SelectOptionVo getPojoSov() {
		return pojoSov;
	}

	public void setPojoSov(SelectOptionVo pojoSov) {
		this.pojoSov = pojoSov;
	}

}
