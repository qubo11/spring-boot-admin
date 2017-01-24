package com.expect.admin.service.vo.db;

import com.expect.admin.service.vo.component.html.SelectOptionVo;

/**
 * 业务实体vo
 */
public class ControllerVo {

	private String id;// id
	private String name;// 业务类名称
	private String packageName;// 包名
	private String requestMapping;// 该控制层的全局路径
	private String comment;// 注释
	private String pojoId;// 实体id
	private String pojoName;// 实体名称
	private SelectOptionVo pojoSov;// 实体sov

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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getRequestMapping() {
		return requestMapping;
	}

	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
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

}
