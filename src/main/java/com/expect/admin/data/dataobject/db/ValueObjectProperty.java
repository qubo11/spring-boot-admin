package com.expect.admin.data.dataobject.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 值对象属性
 *
 * 用于配置值对象属性的表
 */
@Entity
@Table(name = "c_db_valueObject_property")
public class ValueObjectProperty {

	private String id;// id
	private String name;// vo名称
	private String type;// vo类型
	private String dateFormat;// 日期格式化
	private String comment;// 注释
	/**
	 * 表格显示
	 */
	private Integer dataTableSequence;// 表格显示顺序
	private String isDataTableShow;// 是否表格显示
	/**
	 * 以下是表单域
	 */
	private String nullable;// 是否为空
	private Integer minLength;// 最小长度
	private Integer maxLength;// 最大长度
	private Integer minValue;// 最小值
	private Integer maxValue;// 最大值
	private String regex;// 正则表达式
	private String formType;// 表单域类型
	private Integer formSequence;// 顺序

	private Property property;// 对应属性
	private ValueObject valueObject;// 值对象

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

	@Column(name = "type", length = 255)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "date_format", length = 255)
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Column(name = "comment", length = 511)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "dataTable_sequence", precision = 11)
	public Integer getDataTableSequence() {
		return dataTableSequence;
	}

	public void setDataTableSequence(Integer dataTableSequence) {
		this.dataTableSequence = dataTableSequence;
	}

	@Column(name = "is_dataTable_show", length = 2)
	public String getIsDataTableShow() {
		return isDataTableShow;
	}

	public void setIsDataTableShow(String isDataTableShow) {
		this.isDataTableShow = isDataTableShow;
	}

	@Column(name = "nullable", length = 2)
	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	@Column(name = "min_length", precision = 11)
	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	@Column(name = "max_length", precision = 11)
	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	@Column(name = "min_value", precision = 11)
	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	@Column(name = "max_value", precision = 11)
	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	@Column(name = "regex", length = 127)
	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	@Column(name = "form_type", length = 255)
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	@Column(name = "form_sequence", precision = 11)
	public Integer getFormSequence() {
		return formSequence;
	}

	public void setFormSequence(Integer formSequence) {
		this.formSequence = formSequence;
	}

	@OneToOne
	@JoinColumn(name = "property_id")
	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	@ManyToOne
	@JoinColumn(name = "valueObject_id")
	public ValueObject getValueObject() {
		return valueObject;
	}

	public void setValueObject(ValueObject valueObject) {
		this.valueObject = valueObject;
	}

}
