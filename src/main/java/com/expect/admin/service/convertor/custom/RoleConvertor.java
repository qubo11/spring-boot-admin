package com.expect.admin.service.convertor.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.custom.Role;
import com.expect.admin.service.vo.component.html.CheckboxsVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.RoleVo;

public class RoleConvertor {

	/**
	 * dos to vos
	 */
	public static List<RoleVo> dosToVos(Collection<Role> roles) {
		List<RoleVo> roleVos = new ArrayList<>();
		if (CollectionUtils.isEmpty(roles)) {
			return roleVos;
		}
		for (Role role : roles) {
			RoleVo roleVo = doToVo(role);
			roleVos.add(roleVo);
		}
		return roleVos;
	}

	/**
	 * do to vo
	 */
	public static RoleVo doToVo(Role role) {
		RoleVo roleVo = new RoleVo();
		if (role == null) {
			return roleVo;
		}
		BeanUtils.copyProperties(role, roleVo);
		return roleVo;
	}

	/**
	 * vo to do
	 */
	public static Role voToDo(RoleVo roleVo, Role role) {
		BeanUtils.copyProperties(roleVo, role);
		return role;
	}

	/**
	 * dos to dtrvs
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<Role> roles) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(roles)) {
			for (Role role : roles) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, role);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

	/**
	 * do to dtrv
	 */
	public static void doToDtrv(DataTableRowVo dtrv, Role role) {
		dtrv.setObj(role.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(role.getCode());
		dtrv.addData(role.getName());
		dtrv.addData(role.getDescription());
		// 设置操作的button
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getDefaultButton(role.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * vo to cbv
	 */
	public static CheckboxsVo convertCbv(Collection<RoleVo> roles, List<String> ids) {
		CheckboxsVo checkboxsVo = new CheckboxsVo("role");
		if (!CollectionUtils.isEmpty(roles)) {
			for (RoleVo role : roles) {
				if (CollectionUtils.isEmpty(ids)) {
					checkboxsVo.addCheckbox(role.getName(), role.getId());
					continue;
				}
				boolean flag = false;
				for (String id : ids) {
					if (id.equals(role.getId())) {
						flag = true;
						break;
					}
				}
				if (flag) {
					checkboxsVo.addCheckbox(role.getName(), role.getId(), true);
				} else {
					checkboxsVo.addCheckbox(role.getName(), role.getId());
				}
			}
		}
		return checkboxsVo;
	}

}
