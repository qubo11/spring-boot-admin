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
 * Dao方法类
 * 
 * 用于配置Dao方法的类
 */
@Entity
@Table(name = "c_db_dao_method")
public class DaoMethod {

	private String id;// 主键id
	private String name;// 方法名(完整的方法名)
	private String query;// 是否自定义查询语句
	private String modifying;// 是否使用update/delete语句
	private String querySentence;// 查询语句
	private String returnType;// 返回类型
	private String comment;// 注释
	private Dao dao;// 对应的dao

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

	@Column(name = "name", length = 63)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "query", length = 2)
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Column(name = "modifying", length = 2)
	public String getModifying() {
		return modifying;
	}

	public void setModifying(String modifying) {
		this.modifying = modifying;
	}

	@Column(name = "query_sentence", length = 511)
	public String getQuerySentence() {
		return querySentence;
	}

	public void setQuerySentence(String querySentence) {
		this.querySentence = querySentence;
	}

	@Column(name = "return_type", length = 127)
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Column(name = "comment", length = 511)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne
	@JoinColumn(name = "dao_id")
	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

}
