package com.expect.admin.data.dataobject.db;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 值对象DO
 * 
 * 用于值对象实体的表
 */
@Entity
@Table(name = "c_db_valueObject")
public class ValueObject {

	private String id;
	private String name;// 值对象名
	private String packageName;// 包名
	private String comment;// 注释
	private Pojo pojo;// 实体
	private List<ValueObjectProperty> valueObjectProperties;// 值对象属性

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", nullable = false, unique = true, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "package_name", length = 255)
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Column(name = "comment", length = 511)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@OneToOne
	@JoinColumn(name = "pojo_id")
	public Pojo getPojo() {
		return pojo;
	}

	public void setPojo(Pojo pojo) {
		this.pojo = pojo;
	}

	@OneToMany(mappedBy = "valueObject")
	public List<ValueObjectProperty> getValueObjectProperties() {
		return valueObjectProperties;
	}

	public void setValueObjectProperties(List<ValueObjectProperty> valueObjectProperties) {
		this.valueObjectProperties = valueObjectProperties;
	}

}
