package com.expect.admin.service.convertor.custom;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.custom.Authority;
import com.expect.admin.service.vo.component.html.CheckboxsVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.AuthorityVo;

/**
 * 权限Convertor
 */
public class AuthorityConvertor {

	/**
	 * do to vo
	 */
	public static AuthorityVo doToVo(Authority authority) {
		AuthorityVo authorityVo = new AuthorityVo();
		if (authority == null) {
			return authorityVo;
		}
		BeanUtils.copyProperties(authority, authorityVo);
		return authorityVo;
	}

	/**
	 * dos to vos
	 */
	public static List<AuthorityVo> dosToVos(List<Authority> authorities) {
		List<AuthorityVo> authorityVos = new ArrayList<>();
		for (Authority authority : authorities) {
			AuthorityVo authorityVo = doToVo(authority);
			authorityVos.add(authorityVo);
		}
		return authorityVos;
	}

	/**
	 * vo to do
	 */
	public static AuthorityVo voToDo(AuthorityVo authorityVo, Authority authority) {
		BeanUtils.copyProperties(authorityVo, authority);
		return authorityVo;
	}

	/**
	 * dos to dtrvs
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<Authority> authoritys) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(authoritys)) {
			for (Authority authority : authoritys) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, authority);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

	/**
	 * do to dtrv
	 */
	public static void doToDtrv(DataTableRowVo dtrv, Authority authority) {
		dtrv.setObj(authority.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(authority.getCode());
		dtrv.addData(authority.getName());
		dtrv.addData(authority.getIcon());
		dtrv.addData(authority.getSequence() + "");
		dtrv.addData(authority.getDescription());
		// 设置操作的button
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getDefaultButton(authority.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * do to cbsv
	 */
	public static CheckboxsVo doVoCbv(List<Authority> authorities, List<Authority> ownAuthorities) {
		CheckboxsVo checkboxsVo = new CheckboxsVo("authority");
		if (!CollectionUtils.isEmpty(authorities)) {
			for (Authority authority : authorities) {
				if (CollectionUtils.isEmpty(ownAuthorities)) {
					checkboxsVo.addCheckbox(authority.getName(), authority.getId());
					continue;
				}
				boolean flag = false;
				for (Authority ownAuthority : ownAuthorities) {
					if (ownAuthority.getId().equals(authority.getId())) {
						flag = true;
						break;
					}
				}
				if (flag) {
					checkboxsVo.addCheckbox(authority.getName(), authority.getId(), true);
				} else {
					checkboxsVo.addCheckbox(authority.getName(), authority.getId());
				}
			}
		}
		return checkboxsVo;
	}
}
