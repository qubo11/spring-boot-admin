package com.expect.admin.service.vo.db;

import java.util.List;

import com.expect.admin.service.vo.component.html.SelectOptionVo;

/**
 * 值对象vo
 */
public class ValueObjectVo {

	private String id;// id
	private String name;// 值对象名称
	private String packageName;// 包名
	private String comment;// 注释
	private String pojoId;// 实体id
	private String pojoName;// 实体名称
	private SelectOptionVo pojoSov;// 实体sov
	private List<ValueObjectPropertyVo> valueObjectPropertyVos;// 值对象属性

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

	public SelectOptionVo getPojoSov() {
		return pojoSov;
	}

	public void setPojoSov(SelectOptionVo pojoSov) {
		this.pojoSov = pojoSov;
	}

	public List<ValueObjectPropertyVo> getValueObjectPropertyVos() {
		return valueObjectPropertyVos;
	}

	public void setValueObjectPropertyVos(List<ValueObjectPropertyVo> valueObjectPropertyVos) {
		this.valueObjectPropertyVos = valueObjectPropertyVos;
	}

}
