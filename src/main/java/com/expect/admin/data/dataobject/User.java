package com.expect.admin.data.dataobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户
 */
@Entity
@Table(name = "c_user")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", nullable = false, unique = true, length = 32)
	private String id;
	@Column(name = "username", length = 31)
	private String username;// 用户名
	@Column(name = "password", length = 31)
	private String password;// 密码
	@Column(name = "full_name", length = 31)
	private String fullName;// 姓名
	@Column(name = "sex", length = 2)
	private String sex;// 性别
	@Column(name = "phone", length = 15)
	private String phone;// 手机
	@Column(name = "email", length = 63)
	private String email;// 电子邮件
	@Column(name = "remark1", length = 511)
	private String remark1;// 备注1
	@Column(name = "remark2", length = 511)
	private String remark2;// 备注2
	@Column(name = "is_delete", precision = 1)
	private Integer isDelete;// 是否删除,0删除,1未删除
	@OneToOne
	@JoinColumn(name = "avatar_id")
	private Attachment avatar;// 头像
	/**
	 * joinColumns代表维护端
	 * 
	 * inverseJoinColumns代表被维护段
	 * 
	 * 维护端负责管理多对多关系，被维护端不负责，就代表在被维护端setXXX不会有效果
	 */
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "c_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "c_user_department", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "department_id"))
	private Set<Department> departments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Attachment getAvatar() {
		return avatar;
	}

	public void setAvatar(Attachment avatar) {
		this.avatar = avatar;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<>();
		Set<Role> roles = this.getRoles();
		for (Role sysRole : roles) {
			auths.add(new SimpleGrantedAuthority(sysRole.getName()));
		}
		return auths;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		return user;
	}

	/**
	 * 以下是权限操作
	 */
	@Transient
	private boolean insert;
	@Transient
	private boolean update;
	@Transient
	private boolean delete;
	@Transient
	private boolean other;

	public void setAuthority() {
		Set<Role> roles = this.getRoles();
		for (Role sysRole : roles) {
			Set<RoleFunction> roleFunctions = sysRole.getRoleFunctions();
			for (RoleFunction roleFunction : roleFunctions) {
				if (roleFunction.getInsertAuthority() != null && roleFunction.getInsertAuthority() == 1) {
					this.insert = true;
				}
				if (roleFunction.getUpdateAuthority() != null && roleFunction.getUpdateAuthority() == 1) {
					this.update = true;
				}
				if (roleFunction.getDeleteAuthority() != null && roleFunction.getDeleteAuthority() == 1) {
					this.delete = true;
				}
				if (roleFunction.getOtherAuthority() != null && roleFunction.getOtherAuthority() == 1) {
					this.other = true;
				}
			}
		}
	}

	/**
	 * 判断用户是否有insert权限
	 */
	public boolean hasInsertAuthority() {
		return insert;
	}

	/**
	 * 判断用户是否有update权限
	 */
	public boolean hasUpdateAuthority() {
		return update;
	}

	/**
	 * 判断用户是否有delete权限
	 */
	public boolean hasDeleteAuthority() {
		return delete;
	}

	/**
	 * 判断用户是否有other权限
	 */
	public boolean hasOtherAuthority() {
		return other;
	}
}
