package com.expect.admin.service.impl.custom;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.custom.AuthorityRepository;
import com.expect.admin.data.dao.custom.RoleFunctionRepository;
import com.expect.admin.data.dataobject.custom.Authority;
import com.expect.admin.data.dataobject.custom.RoleFunction;
import com.expect.admin.service.convertor.custom.AuthorityConvertor;
import com.expect.admin.service.vo.component.html.CheckboxsVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.AuthorityVo;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.web.exception.AjaxRequestException;

/**
 * 权限Service
 */
@Service
public class AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private RoleFunctionRepository roleFunctionRepository;

	/**
	 * 获取所有的权限信息，封装成dtrsv
	 */
	public List<DataTableRowVo> getAuthorityDtrsv() {
		List<Authority> roles = authorityRepository.findAll();
		List<DataTableRowVo> dtrvs = AuthorityConvertor.dosToDtrvs(roles);
		return dtrvs;
	}

	/**
	 * 获取权限，根据角色id和功能id
	 * 
	 * @param roleId
	 *            角色id
	 * @param functionId
	 *            功能id
	 * @return 权限vo list
	 */
	public List<AuthorityVo> getAuthorityByRoleAndFunctionId(String roleId, String functionId) {
		RoleFunction roleFunction = roleFunctionRepository.findByRoleIdAndFunctionId(roleId, functionId);
		List<Authority> authorities = roleFunction.getAuthorities();
		return AuthorityConvertor.dosToVos(authorities);
	}

	/**
	 * 获取权限cbsv模型，根据角色id和功能id
	 * 
	 * @param roleId
	 *            角色id
	 * @param functionId
	 *            功能id
	 * @return 权限cbsv
	 */
	public CheckboxsVo getAuthorityCbsvByRoleAndFunctionId(String roleId, String functionId) {
		RoleFunction roleFunction = roleFunctionRepository.findByRoleIdAndFunctionId(roleId, functionId);
		List<Authority> ownAuthorities = roleFunction.getAuthorities();
		List<Authority> authorities = authorityRepository.findAll();
		return AuthorityConvertor.doVoCbv(authorities, ownAuthorities);
	}

	/**
	 * 配置权限
	 * 
	 * @param roleId
	 *            角色id
	 * @param functionId
	 *            功能id
	 * @param authorityIds
	 *            权限id(使用,分隔)
	 * @return 结果vo
	 */
	@Transactional
	public ResultVo configureAuthority(String roleId, String functionId, String authorityIds)
			throws AjaxRequestException {
		ResultVo rv = new ResultVo();
		rv.setMessage("配置失败");
		if (StringUtils.isBlank(roleId)) {
			rv.setMessage("请选择角色");
			return rv;
		}
		if (StringUtils.isBlank(functionId)) {
			rv.setMessage("请选择功能");
			return rv;
		}
		if (authorityIds == null) {
			return rv;
		}
		RoleFunction roleFunction = roleFunctionRepository.findByRoleIdAndFunctionId(roleId, functionId);
		List<Authority> authorities = new ArrayList<>();
		String[] authorityIdArr = authorityIds.split(",");
		for (String authorityId : authorityIdArr) {
			Authority authority = authorityRepository.findOne(authorityId);
			if (authority != null) {
				authorities.add(authority);
			}
		}
		roleFunction.setAuthorities(authorities);

		rv.setResult(true);
		rv.setMessage("配置成功");
		return rv;
	}

	/**
	 * 根据id获取角色
	 */
	public AuthorityVo getAuthorityById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		Authority authority = authorityRepository.findOne(id);
		return AuthorityConvertor.doToVo(authority);
	}

	/**
	 * 保存角色
	 */
	@Transactional
	public DataTableRowVo save(AuthorityVo authorityVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("增加失败");
		if (StringUtils.isBlank(authorityVo.getName())) {
			dtrv.setMessage("角色名不能为空");
			return dtrv;
		}
		Authority authority = new Authority();
		AuthorityConvertor.voToDo(authorityVo, authority);

		Authority result = authorityRepository.save(authority);
		if (result != null) {
			dtrv.setResult(true);
			dtrv.setMessage("增加成功");
			AuthorityConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 修改角色
	 */
	@Transactional
	public DataTableRowVo update(AuthorityVo authorityVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("修改失败");
		if (StringUtils.isBlank(authorityVo.getId())) {
			dtrv.setMessage("角色名不能为空");
			return dtrv;
		}
		if (StringUtils.isBlank(authorityVo.getName())) {
			dtrv.setMessage("角色名不能为空");
			return dtrv;
		}
		Authority checkAuthority = authorityRepository.findOne(authorityVo.getId());
		if (checkAuthority == null) {
			return dtrv;
		}
		AuthorityConvertor.voToDo(authorityVo, checkAuthority);

		dtrv.setResult(true);
		dtrv.setMessage("修改成功");
		AuthorityConvertor.doToDtrv(dtrv, checkAuthority);
		return dtrv;
	}

	/**
	 * 删除角色
	 */
	public ResultVo delete(String id) {
		ResultVo resultVo = new ResultVo();
		resultVo.setMessage("删除失败");
		Authority authority = authorityRepository.findOne(id);
		if (authority == null) {
			return resultVo;
		}

		authorityRepository.delete(id);
		resultVo.setMessage("删除成功");
		resultVo.setResult(true);
		return resultVo;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            用,号隔开
	 */
	@Transactional
	public ResultVo deleteBatch(String ids) {
		ResultVo resultVo = new ResultVo();
		resultVo.setMessage("删除失败");
		if (StringUtils.isBlank(ids)) {
			return resultVo;
		}
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			authorityRepository.delete(id);
		}
		resultVo.setResult(true);
		resultVo.setMessage("删除成功");
		return resultVo;
	}

}
