package com.expect.admin.service.impl.custom;

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

import com.expect.admin.data.dao.custom.FunctionRepository;
import com.expect.admin.data.dao.custom.RoleFunctionRepository;
import com.expect.admin.data.dao.custom.RoleRepository;
import com.expect.admin.data.dao.custom.UserRepository;
import com.expect.admin.data.dataobject.custom.Function;
import com.expect.admin.data.dataobject.custom.Role;
import com.expect.admin.data.dataobject.custom.RoleFunction;
import com.expect.admin.data.dataobject.custom.User;
import com.expect.admin.service.convertor.custom.RoleConvertor;
import com.expect.admin.service.vo.component.html.JsTreeVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.RoleVo;
import com.expect.custom.service.vo.component.ResultVo;

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
		List<RoleVo> roleVos = RoleConvertor.dosToVos(roles);
		return roleVos;
	}

	/**
	 * 获取所有的角色信息，封装成dtrsv
	 */
	public List<DataTableRowVo> getRoleDtrsv() {
		List<Role> roles = roleRepository.findAll();
		List<DataTableRowVo> dtrvs = RoleConvertor.dosToDtrvs(roles);
		return dtrvs;
	}

	/**
	 * 根据id获取角色
	 */
	public RoleVo getRoleById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		Role role = roleRepository.findOne(id);
		return RoleConvertor.doToVo(role);
	}

	/**
	 * 根据userId获取该用户的所有部门
	 */
	public List<RoleVo> getRolesByUserId(String userId) {
		User user = userRepository.findOne(userId);
		if (user == null) {
			return new ArrayList<>();
		}
		return RoleConvertor.dosToVos(user.getRoles());
	}

	/**
	 * 保存角色
	 */
	@Transactional
	public DataTableRowVo save(RoleVo roleVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("增加失败");
		if (StringUtils.isBlank(roleVo.getName())) {
			dtrv.setMessage("角色名不能为空");
			return dtrv;
		}
		Role role = new Role();
		RoleConvertor.voToDo(roleVo, role);

		Role result = roleRepository.save(role);
		if (result != null) {
			dtrv.setResult(true);
			dtrv.setMessage("增加成功");
			RoleConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 修改角色
	 */
	@Transactional
	public DataTableRowVo update(RoleVo roleVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("修改失败");
		if (StringUtils.isBlank(roleVo.getId())) {
			return dtrv;
		}
		if (StringUtils.isBlank(roleVo.getName())) {
			dtrv.setMessage("角色名不能为空");
			return dtrv;
		}
		Role checkRole = roleRepository.findOne(roleVo.getId());
		if (checkRole == null) {
			return dtrv;
		}
		RoleConvertor.voToDo(roleVo, checkRole);

		dtrv.setResult(true);
		dtrv.setMessage("修改成功");
		RoleConvertor.doToDtrv(dtrv, checkRole);
		return dtrv;
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
			roleRepository.delete(id);
		}
		resultVo.setResult(true);
		resultVo.setMessage("删除成功");
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
							if (checkFunctionTreeSelected(functions, thiFunction)) {
								thiJsTree.setSelected(true);
							}
							thiTreeVos.add(thiJsTree);
						}
						secJsTree.setChildren(thiTreeVos);
					} else {
						if (checkFunctionTreeSelected(functions, secFunction)) {
							secJsTree.setSelected(true);
						}
					}
					secJsTreeVos.add(secJsTree);
				}
				firJsTree.setChildren(secJsTreeVos);
			} else {
				if (checkFunctionTreeSelected(functions, firFunction)) {
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

	/**
	 * 根据roleId获取该觉得拥有的functions
	 */
	public List<JsTreeVo> getOwnFunctionTreeByRoleId(String roleId) {
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
			// 设置第二级功能
			Set<Function> secFunctions = firFunction.getChildFunctions();
			if (!CollectionUtils.isEmpty(secFunctions)) {
				List<JsTreeVo> secJsTreeVos = new ArrayList<>(secFunctions.size());
				for (Function secFunction : secFunctions) {
					// 设置第三级功能
					Set<Function> thiFunctions = secFunction.getChildFunctions();
					if (!CollectionUtils.isEmpty(thiFunctions)) {
						List<JsTreeVo> thiTreeVos = new ArrayList<>(thiFunctions.size());
						for (Function thiFunction : thiFunctions) {
							JsTreeVo thiJsTree = new JsTreeVo();
							setFunctionTree(thiFunction, thiJsTree);
							if (checkFunctionTreeSelected(functions, thiFunction)) {
								thiTreeVos.add(thiJsTree);
							}
						}
						if (thiTreeVos.size() > 0) {
							JsTreeVo secJsTree = new JsTreeVo();
							setFunctionTree(secFunction, secJsTree);
							secJsTree.setChildren(thiTreeVos);
							secJsTreeVos.add(secJsTree);
						}
					} else {
						if (checkFunctionTreeSelected(functions, secFunction)) {
							JsTreeVo secJsTree = new JsTreeVo();
							setFunctionTree(secFunction, secJsTree);
							secJsTreeVos.add(secJsTree);
						}
					}
				}
				if (secJsTreeVos.size() > 0) {
					JsTreeVo firJsTree = new JsTreeVo();
					setFunctionTree(firFunction, firJsTree);
					firJsTree.setChildren(secJsTreeVos);
					resultJsTreeVos.add(firJsTree);
				}
			} else {
				if (checkFunctionTreeSelected(functions, firFunction)) {
					JsTreeVo firJsTree = new JsTreeVo();
					setFunctionTree(firFunction, firJsTree);
					resultJsTreeVos.add(firJsTree);
				}
			}
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

	private boolean checkFunctionTreeSelected(Set<Function> functions, Function currentFunction) {
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
