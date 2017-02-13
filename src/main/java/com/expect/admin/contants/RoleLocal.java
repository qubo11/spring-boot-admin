package com.expect.admin.contants;

public class RoleLocal {

	private static ThreadLocal<Boolean[]> local = new ThreadLocal<>();

	/**
	 * 赋值
	 */
	public static void set(Boolean[] authorities) {
		local.set(authorities);
	}

	/**
	 * 获取所有的权限值
	 */
	public static Boolean[] get() {
		return local.get();
	}

	/**
	 * 获取增加的权限
	 */
	public static Boolean getInsert() {
		Boolean[] roles = local.get();
		if (roles == null || roles.length != 4) {
			return false;
		}
		return roles[0];
	}

	/**
	 * 获取修改的权限
	 */
	public static Boolean getUpdate() {
		Boolean[] roles = local.get();
		if (roles == null || roles.length != 4) {
			return false;
		}
		return roles[1];
	}

	/**
	 * 获取删除的权限
	 */
	public static Boolean getDelete() {
		Boolean[] roles = local.get();
		if (roles == null || roles.length != 4) {
			return false;
		}
		return roles[2];
	}

	/**
	 * 获取其他的权限
	 */
	public static Boolean getOther() {
		Boolean[] roles = local.get();
		if (roles == null || roles.length != 4) {
			return false;
		}
		return roles[3];
	}

	/**
	 * 移除
	 */
	public static void remove() {
		local.remove();
	}

}
