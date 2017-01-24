package com.expect.admin.data.dataobject.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 属性表
 * 
 * 实体的属性
 */
@Entity
@Table(name = "c_db_property")
public class Property {

	private String id;// id
	private String isId;// 是否是id属性,不是/是
	private String name;// 属性名称
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
	private Pojo pojo;// 实体

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

	@Column(name = "isId", length = 2)
	public String getIsId() {
		return isId;
	}

	public void setIsId(String isId) {
		this.isId = isId;
	}

	@Column(name = "name", length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "column_name", length = 255)
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "type", length = 127)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "length", precision = 11)
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@Column(name = "m_precision", precision = 11)
	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	@Column(name = "scale", precision = 11)
	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	@Column(name = "m_unique", length = 2)
	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	@Column(name = "nullable", length = 2)
	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	@Column(name = "relation", length = 15)
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	@Column(name = "join_columns", length = 255)
	public String getJoinColumns() {
		return joinColumns;
	}

	public void setJoinColumns(String joinColumns) {
		this.joinColumns = joinColumns;
	}

	@Column(name = "join_table", length = 255)
	public String getJoinTable() {
		return joinTable;
	}

	public void setJoinTable(String joinTable) {
		this.joinTable = joinTable;
	}

	@Column(name = "inverse_join_columns", length = 255)
	public String getInverseJoinColumns() {
		return inverseJoinColumns;
	}

	public void setInverseJoinColumns(String inverseJoinColumns) {
		this.inverseJoinColumns = inverseJoinColumns;
	}

	@Column(name = "mappedBy", length = 255)
	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	@Column(name = "m_fetch", length = 7)
	public String getFetch() {
		return fetch;
	}

	public void setFetch(String fetch) {
		this.fetch = fetch;
	}

	@Column(name = "m_cascade", length = 15)
	public String getCascade() {
		return cascade;
	}

	public void setCascade(String cascade) {
		this.cascade = cascade;
	}

	@Column(name = "m_comment", length = 255)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne
	@JoinColumn(name = "pojo_id")
	public Pojo getPojo() {
		return pojo;
	}

	public void setPojo(Pojo pojo) {
		this.pojo = pojo;
	}

}
