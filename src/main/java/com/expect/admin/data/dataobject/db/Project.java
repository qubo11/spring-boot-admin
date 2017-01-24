package com.expect.admin.data.dataobject.db;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 项目实体
 * 
 * 用于配置项目的表
 */
@Entity
@Table(name = "c_db_project")
public class Project {

	private String id;
	private String name;// 项目名称
	private String packageName;// 包名
	private String remark;// 项目备注
	private List<Pojo> pojos;// 实体

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

	@Column(name = "remark", length = 511)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(mappedBy = "project")
	public List<Pojo> getPojos() {
		return pojos;
	}

	public void setPojos(List<Pojo> pojos) {
		this.pojos = pojos;
	}

}
