package com.expect.admin.service.vo.db;

import java.util.List;

import com.expect.admin.service.vo.component.html.SelectOptionVo;

/**
 * 实体属性Vo
 */
public class PropertyVo {

	private String id;// id
	private String isId;// 是否是id属性,不是/是
	private String name;// 属性名称
	private String nameUpperCase;// 属性名首字母大写
	private String columnName;// 数据库对应的列名
	private String type;// 属性类型
	private Integer length;// 长度，字符串类型才需要
	private Integer precision;// 数值型，位数
	private Integer scale;// 浮点型，小数位
	private String unique;// 是否唯一,不是/是
	private String nullable;// 是否为空,不是/是
	private String relation;// 关联关系,OneToOne;OneToMany;ManyToOne;ManyToMany
	private String joinColumns;// 关联的外键,主要管理的一端
	private String joinTable;// ManyToMany关系中，中间表名称
	private String inverseJoinColumns;// ManyToMany关系中,被管理一端
	private String mappedBy;// 被管理一端映射的属性名
	private String fetch;// 抓取策略,LAZY/EAGER
	private String cascade;// 级联,ALL;PERSIST;MERGE;REMOVE;REFRESH;DETACH
	private String comment;// 注释
	private String otherType;// 其他类型
	private List<String> types;// 所有的类型
	private String pojoId;// 实体id
	private String pojoName;// 实体名称
	private SelectOptionVo pojoSov;// 实体sov

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsId() {
		return isId;
	}

	public void setIsId(String isId) {
		this.isId = isId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameUpperCase() {
		return nameUpperCase;
	}

	public void setNameUpperCase(String nameUpperCase) {
		this.nameUpperCase = nameUpperCase;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getJoinColumns() {
		return joinColumns;
	}

	public void setJoinColumns(String joinColumns) {
		this.joinColumns = joinColumns;
	}

	public String getJoinTable() {
		return joinTable;
	}

	public void setJoinTable(String joinTable) {
		this.joinTable = joinTable;
	}

	public String getInverseJoinColumns() {
		return inverseJoinColumns;
	}

	public void setInverseJoinColumns(String inverseJoinColumns) {
		this.inverseJoinColumns = inverseJoinColumns;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	public String getFetch() {
		return fetch;
	}

	public void setFetch(String fetch) {
		this.fetch = fetch;
	}

	public String getCascade() {
		return cascade;
	}

	public void setCascade(String cascade) {
		this.cascade = cascade;
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

	public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

}
