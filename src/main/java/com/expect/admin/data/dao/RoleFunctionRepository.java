package com.expect.admin.data.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.expect.admin.data.dataobject.RoleFunction;

public interface RoleFunctionRepository extends JpaRepository<RoleFunction, String> {

	/**
	 * 根据角色id，删除角色功能
	 * 
	 * @param roleId
	 *            角色id
	 * @return 删除的条数
	 */
	@Modifying
	@Query("delete from RoleFunction where role.id=?")
	public int deleteByRoleId(String roleId);

	/**
	 * 根据角色id，获取角色功能
	 * 
	 * @param roleId
	 *            角色id
	 * @return 角色功能列表
	 */
	public Set<RoleFunction> findByRoleId(String roleId);

}
