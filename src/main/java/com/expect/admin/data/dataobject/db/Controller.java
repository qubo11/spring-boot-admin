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
 * 控制层类
 * 
 * 用于配置控制层的表
 */
@Entity
@Table(name = "c_db_controller")
public class Controller {

	private String id;// id
	private String name;// 业务类名称
	private String packageName;// 包名
	private String requestMapping;// 该控制层的全局路径
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

	@Column(name = "request_mapping", length = 511)
	public String getRequestMapping() {
		return requestMapping;
	}

	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}

	@Column(name = "comment", length = 511)
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
