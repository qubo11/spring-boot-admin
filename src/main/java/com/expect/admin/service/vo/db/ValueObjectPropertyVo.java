package com.expect.admin.service.vo.db;

import java.util.List;

import com.expect.admin.service.vo.component.html.SelectOptionVo;

/**
 * 值对象属性vo
 */
public class ValueObjectPropertyVo {

	private String id;// id
	private String name;// vo名称
	private String type;// vo类型
	private String dateFormat;// 日期格式化
	private String comment;// 注释
	private Integer dataTableSequence;// 表格显示顺序
	private String isDataTableShow;// 是否表格显示
	private String propertyId;// 对应的属性id
	private String propertyName;// 对应属性名称
	private String propertyType;// 对应属性类型
	private String valueObjectId;// 业务id
	private String valueObjectName;// 业务名称
	private SelectOptionVo propertySov;// 属性sov
	private List<String> types;// 类型list
	private List<String> dateFormats;// 日期格式化类型
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
	private String validateType;// 验证类型
	private List<String> validateTypes;// 验证类型
	private List<String> formTypes;// 表单域类型

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getValueObjectId() {
		return valueObjectId;
	}

	public void setValueObjectId(String valueObjectId) {
		this.valueObjectId = valueObjectId;
	}

	public String getValueObjectName() {
		return valueObjectName;
	}

	public void setValueObjectName(String valueObjectName) {
		this.valueObjectName = valueObjectName;
	}

	public SelectOptionVo getPropertySov() {
		return propertySov;
	}

	public void setPropertySov(SelectOptionVo propertySov) {
		this.propertySov = propertySov;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<String> getDateFormats() {
		return dateFormats;
	}

	public void setDateFormats(List<String> dateFormats) {
		this.dateFormats = dateFormats;
	}

	public Integer getDataTableSequence() {
		return dataTableSequence;
	}

	public void setDataTableSequence(Integer dataTableSequence) {
		this.dataTableSequence = dataTableSequence;
	}

	public String getIsDataTableShow() {
		return isDataTableShow;
	}

	public void setIsDataTableShow(String isDataTableShow) {
		this.isDataTableShow = isDataTableShow;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public Integer getFormSequence() {
		return formSequence;
	}

	public void setFormSequence(Integer formSequence) {
		this.formSequence = formSequence;
	}

	public String getValidateType() {
		return validateType;
	}

	public void setValidateType(String validateType) {
		this.validateType = validateType;
	}

	public List<String> getValidateTypes() {
		return validateTypes;
	}

	public void setValidateTypes(List<String> validateTypes) {
		this.validateTypes = validateTypes;
	}

	public List<String> getFormTypes() {
		return formTypes;
	}

	public void setFormTypes(List<String> formTypes) {
		this.formTypes = formTypes;
	}

}
