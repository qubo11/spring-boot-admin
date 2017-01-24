package com.expect.admin.data.dataobject.db;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 实体类
 * 
 * 用于配置实体的表
 */
@Entity
@Table(name = "c_db_pojo")
public class Pojo {

	private String id;
	private String name;// 实体名
	private String tableName;// 对应的表名
	private String packageName;// 包名
	private String comment;// 注释
	private Project project;// 项目
	private Dao dao;// dao
	private Business business;// 业务类
	private ValueObject valueObject;// 值对象
	private Controller controller;// controller
	private List<Property> properties;// 属性

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

	@Column(name = "table_name", length = 255)
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	@ManyToOne
	@JoinColumn(name = "project_id")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@OneToOne(mappedBy = "pojo")
	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@OneToOne(mappedBy = "pojo")
	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	@OneToOne(mappedBy = "pojo")
	public ValueObject getValueObject() {
		return valueObject;
	}

	public void setValueObject(ValueObject valueObject) {
		this.valueObject = valueObject;
	}

	@OneToOne(mappedBy = "pojo")
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@OneToMany(mappedBy = "pojo")
	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

}
