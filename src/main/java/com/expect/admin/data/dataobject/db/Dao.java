package com.expect.admin.data.dataobject.db;

import java.util.Arrays;
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
 * Dao类
 * 
 * 用于配置实体对应的Dao表
 */
@Entity
@Table(name = "c_db_dao")
public class Dao {

	public final static String INHERIT_JPA = "JPARepository";
	public final static String INHERIT_CUSTOM = "CustomRepository";
	private static List<String> inherits = Arrays.asList(INHERIT_JPA, INHERIT_CUSTOM);

	private String id;// 主键
	private String name;// 名称
	private String packageName;// 包名
	private String inherit;// 继承类(JPARepository,CustomRepository)
	private String comment;// 注释
	private Pojo pojo;// 对应的实体类
	private List<DaoMethod> daoMethods;// dao方法

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

	@Column(name = "package_name", length = 255)
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Column(name = "inherit", length = 63)
	public String getInherit() {
		return inherit;
	}

	public void setInherit(String inherit) {
		this.inherit = inherit;
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

	@OneToMany(mappedBy = "dao")
	public List<DaoMethod> getDaoMethods() {
		return daoMethods;
	}

	public void setDaoMethods(List<DaoMethod> daoMethods) {
		this.daoMethods = daoMethods;
	}

	/**
	 * 获取所有的继承
	 */
	public static List<String> getInherits() {
		return inherits;
	}

}
