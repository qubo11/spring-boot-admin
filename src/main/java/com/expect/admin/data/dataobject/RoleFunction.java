package com.expect.admin.data.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色功能表
 */
@Entity
@Table(name = "c_role_function")
public class RoleFunction {

	private String id;
	private Role role;// 角色
	private Function function;// 功能
	private Integer insertAuthority;// 增加权限（0:没有,1:有）
	private Integer updateAuthority;// 更新权限（0:没有,1:有）
	private Integer deleteAuthority;// 删除权限（0:没有,1:有）
	private Integer otherAuthority;// 其他权限，除以上三个以外（0:没有,1:有）

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

	@ManyToOne
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name = "function_id")
	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	@Column(name = "insert_authority", precision = 1)
	public Integer getInsertAuthority() {
		return insertAuthority;
	}

	public void setInsertAuthority(Integer insertAuthority) {
		this.insertAuthority = insertAuthority;
	}

	@Column(name = "update_authority", precision = 1)
	public Integer getUpdateAuthority() {
		return updateAuthority;
	}

	public void setUpdateAuthority(Integer updateAuthority) {
		this.updateAuthority = updateAuthority;
	}

	@Column(name = "delete_authority", precision = 1)
	public Integer getDeleteAuthority() {
		return deleteAuthority;
	}

	public void setDeleteAuthority(Integer deleteAuthority) {
		this.deleteAuthority = deleteAuthority;
	}

	@Column(name = "other_authority", precision = 1)
	public Integer getOtherAuthority() {
		return otherAuthority;
	}

	public void setOtherAuthority(Integer otherAuthority) {
		this.otherAuthority = otherAuthority;
	}

}
