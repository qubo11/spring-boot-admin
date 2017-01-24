package com.expect.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.FunctionRepository;
import com.expect.admin.data.dao.RoleFunctionRepository;
import com.expect.admin.data.dao.RoleRepository;
import com.expect.admin.data.dao.UserRepository;
import com.expect.admin.data.dataobject.Function;
import com.expect.admin.data.dataobject.Role;
import com.expect.admin.data.dataobject.RoleFunction;
import com.expect.admin.data.dataobject.User;
import com.expect.admin.service.convertor.RoleConvertor;
import com.expect.admin.service.vo.RoleVo;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.JsTreeVo;

/**
 * 角色Service
 */
@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private FunctionRepository functionRepository;
	@Autowired
	private RoleFunctionRepository roleFunctionRepository;
	@Autowired
	private UserRepository userRepository;

	/**
	 * 获取所有的角色
	 */
	public List<RoleVo> getRoles() {
		List<Role> roles = roleRepository.findAll();
		List<RoleVo> roleVos = RoleVo.convert(roles);
		return roleVos;
	}

	/**
	 * 根据id获取角色
	 */
	public RoleVo getRoleById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		Role role = roleRepository.findOne(id);
		return RoleConvertor.convert(role);
	}

	/**
	 * 根据userId获取该用户的所有部门
	 */
	public List<RoleVo> getRolesByUserId(String userId) {
		User user = userRepository.findOne(userId);
		if (user == null) {
			return new ArrayList<>();
		}
		return RoleConvertor.convert(user.getRoles());
	}

	/**
	 * 保存角色
	 */
	@Transactional
	public ResultVo save(String name) {
		ResultVo resultVo = new ResultVo();
		resultVo.setMessage("增加失败");
		if (StringUtils.isBlank(name)) {
			resultVo.setMessage("角色名不能为空");
			return resultVo;
		}
		Role role = new Role();
		role.setName(name);

		Role result = roleRepository.save(role);
		if (result != null) {
			resultVo.setResult(true);
			resultVo.setMessage("增加成功");
			resultVo.setObj(result);
		}
		return resultVo;
	}

	/**
	 * 修改角色
	 */
	@Transactional
	public ResultVo update(String id, String name) {
		ResultVo resultVo = new ResultVo();
		resultVo.setMessage("修改失败");
		if (StringUtils.isBlank(name)) {
			return resultVo;
		}
		Role role = roleRepository.findOne(id);

		if (role == null) {
			return resultVo;
		}
		role.setName(name);

		resultVo.setResult(true);
		resultVo.setMessage("修改成功");
		resultVo.setObj(role);
		return resultVo;
	}

	/**
	 * 删除角色
	 */
	public ResultVo delete(String id) {
		ResultVo resultVo = new ResultVo();
		resultVo.setMessage("删除失败");
		Role role = roleRepository.findOne(id);
		if (role == null) {
			return resultVo;
		}

		roleRepository.delete(id);
		resultVo.setMessage("删除成功");
		resultVo.setResult(true);
		return resultVo;
	}

	/**
	 * 根据roleId获取functions
	 */
	public List<JsTreeVo> getFunctionTreeByRoleId(String roleId) {
		if (StringUtils.isBlank(roleId)) {
			return new ArrayList<>();
		}
		Role role = roleRepository.findOne(roleId);
		if (role == null) {
			return new ArrayList<>();
		}
		Set<Function> functions = new HashSet<>();
		Set<RoleFunction> roleFunctions = role.getRoleFunctions();
		if (!CollectionUtils.isEmpty(roleFunctions)) {
			for (RoleFunction roleFunction : roleFunctions) {
				functions.add(roleFunction.getFunction());
			}
		}
		List<JsTreeVo> resultJsTreeVos = new ArrayList<>();
		List<Function> parentFunctions = functionRepository.findByParentFunctionIsNull();
		// 设置第一级功能
		for (Function firFunction : parentFunctions) {
			JsTreeVo firJsTree = new JsTreeVo();
			setFunctionTree(firFunction, firJsTree);
			// 设置第二级功能
			Set<Function> secFunctions = firFunction.getChildFunctions();
			if (!CollectionUtils.isEmpty(secFunctions)) {
				List<JsTreeVo> secJsTreeVos = new ArrayList<>(secFunctions.size());
				for (Function secFunction : secFunctions) {
					JsTreeVo secJsTree = new JsTreeVo();
					setFunctionTree(secFunction, secJsTree);
					// 设置第三级功能
					Set<Function> thiFunctions = secFunction.getChildFunctions();
					if (!CollectionUtils.isEmpty(thiFunctions)) {
						List<JsTreeVo> thiTreeVos = new ArrayList<>(thiFunctions.size());
						for (Function thiFunction : thiFunctions) {
							JsTreeVo thiJsTree = new JsTreeVo();
							setFunctionTree(thiFunction, thiJsTree);
							if (setFunctionTreeSelected(functions, thiFunction)) {
								thiJsTree.setSelected(true);
							}
							thiTreeVos.add(thiJsTree);
						}
						secJsTree.setChildren(thiTreeVos);
					} else {
						if (setFunctionTreeSelected(functions, secFunction)) {
							secJsTree.setSelected(true);
						}
					}
					secJsTreeVos.add(secJsTree);
				}
				firJsTree.setChildren(secJsTreeVos);
			} else {
				if (setFunctionTreeSelected(functions, firFunction)) {
					firJsTree.setSelected(true);
				}
			}
			resultJsTreeVos.add(firJsTree);
		}
		// 排序
		Collections.sort(resultJsTreeVos);
		for (JsTreeVo firJsTreeVo : resultJsTreeVos) {
			List<JsTreeVo> secJsTreeVos = firJsTreeVo.getChildren();
			if (!CollectionUtils.isEmpty(secJsTreeVos)) {
				Collections.sort(secJsTreeVos);
				for (JsTreeVo secTreeVo : secJsTreeVos) {
					List<JsTreeVo> thiJsTreeVos = secTreeVo.getChildren();
					Collections.sort(thiJsTreeVos);
				}
			}
		}
		return resultJsTreeVos;
	}

	private void setFunctionTree(Function function, JsTreeVo jsTreeVo) {
		jsTreeVo.setId(function.getId());
		jsTreeVo.setText(function.getName());
		jsTreeVo.setSequence(function.getSequence());
		jsTreeVo.setIcon(function.getIcon());
	}

	private boolean setFunctionTreeSelected(Set<Function> functions, Function currentFunction) {
		for (Function function : functions) {
			if (function.getId().equals(currentFunction.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 修改角色的功能
	 */
	@Transactional
	public ResultVo updateRoleFunctions(String roleId, String functionIds) {
		ResultVo resultVo = new ResultVo();
		resultVo.setMessage("修改失败");
		if (StringUtils.isBlank(roleId)) {
			return resultVo;
		}
		Role role = roleRepository.getOne(roleId);
		if (role == null) {
			return resultVo;
		}
		Set<RoleFunction> roleFunctions = roleFunctionRepository.findByRoleId(roleId);
		// 修改角色的功能
		if (!StringUtils.isBlank(functionIds)) {
			String[] functionIdArr = functionIds.split(",");
			Set<RoleFunction> deleteRoleFunctions = new HashSet<>();
			// 删除修改后不存在的角色功能
			for (RoleFunction roleFunction : roleFunctions) {
				boolean isExists = false;
				for (String functionId : functionIdArr) {
					if (functionId.equals(roleFunction.getFunction().getId())) {
						isExists = true;
						break;
					}
				}
				if (!isExists) {
					deleteRoleFunctions.add(roleFunction);
				}
			}
			for (RoleFunction deleteRoleFunction : deleteRoleFunctions) {
				roleFunctionRepository.delete(deleteRoleFunction);
			}
			// 增加新的角色功能
			for (String functionId : functionIdArr) {
				boolean isExists = false;
				for (RoleFunction roleFunction : roleFunctions) {
					if (functionId.equals(roleFunction.getFunction().getId())) {
						isExists = true;
						break;
					}
				}
				if (!isExists) {
					RoleFunction roleFunction = new RoleFunction();
					Function function = functionRepository.findOne(functionId);
					roleFunction.setFunction(function);
					roleFunction.setRole(role);
					roleFunctionRepository.save(roleFunction);
				}
			}
		}

		resultVo.setMessage("修改成功");
		resultVo.setResult(true);
		return resultVo;
	}
}
