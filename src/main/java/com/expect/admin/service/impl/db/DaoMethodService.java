package com.expect.admin.service.impl.db;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.contants.PojoConstants;
import com.expect.admin.data.dao.db.DaoMethodRepository;
import com.expect.admin.data.dao.db.DaoRepository;
import com.expect.admin.data.dataobject.db.Dao;
import com.expect.admin.data.dataobject.db.DaoMethod;
import com.expect.admin.service.convertor.db.DaoMethodConvertor;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.DaoMethodVo;
import com.expect.admin.utils.JacksonJsonUtil;

@Service
public class DaoMethodService {

	@Autowired
	private DaoMethodRepository daoMethodRepository;
	@Autowired
	private DaoRepository daoRepository;

	/**
	 * 根据id获取Dao方法
	 * 
	 * @param id
	 *            Dao方法id
	 * @return DaoMethodVo Dao方法vo
	 */
	public DaoMethodVo getDaoMethodById(String id, String daoId) {
		DaoMethod daoMethod = null;
		if (!StringUtils.isBlank(id)) {
			daoMethod = daoMethodRepository.findOne(id);
		}
		Dao dao = null;
		if (!StringUtils.isBlank(daoId)) {
			dao = daoRepository.findOne(daoId);
		}
		return DaoMethodConvertor.doToVo(daoMethod, PojoConstants.getOperations(),
				PojoConstants.getMethodNames(), dao);
	}

	/**
	 * 获取所有的Dao方法，封装成Dao方法vos
	 * 
	 * @return Dao方法 list
	 */
	public List<DaoMethodVo> getDaoMethods() {
		List<DaoMethod> daoMethods = daoMethodRepository.findAll();
		return DaoMethodConvertor.dosToVos(daoMethods);
	}

	/**
	 * 获取所有的Dao方法，封装成Dao方法DataTableVos
	 * 
	 * @return Dao方法DataTableVo list
	 */
	public List<DataTableRowVo> getDaoMethodDtrvs(String daoId) {
		List<DaoMethod> daoMethods = daoMethodRepository.findByDaoId(daoId);
		return DaoMethodConvertor.dosToDtrvs(daoMethods);
	}

	/**
	 * 保存Dao方法
	 * 
	 * @param daoMethodVo
	 *            Dao方法vo
	 * 
	 * @return Dao方法DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo save(DaoMethodVo daoMethodVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(daoMethodVo.getName())) {
			dtrv.setMessage("Dao方法名称不能为空");
			return dtrv;
		}
		DaoMethod daoMethod = new DaoMethod();
		DaoMethodConvertor.voToDo(daoMethodVo, daoMethod);

		// 如果daoId存在，就设置dao
		String daoId = daoMethodVo.getDaoId();
		if (!StringUtils.isBlank(daoId)) {
			Dao dao = daoRepository.findOne(daoId);
			daoMethod.setDao(dao);
		}
		DaoMethod result = daoMethodRepository.save(daoMethod);
		if (result != null) {
			dtrv.setMessage("保存成功");
			dtrv.setResult(true);
			DaoMethodConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 更新Dao方法
	 * 
	 * @param daoMethodVo
	 *            Dao方法vo
	 * 
	 * @return Dao方法DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo update(DaoMethodVo daoMethodVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(daoMethodVo.getId())) {
			return dtrv;
		}
		if (StringUtils.isBlank(daoMethodVo.getName())) {
			dtrv.setMessage("Dao方法名称不能为空");
			return dtrv;
		}
		DaoMethod check = daoMethodRepository.findOne(daoMethodVo.getId());
		if (check == null) {
			return dtrv;
		}
		DaoMethodConvertor.voToDo(daoMethodVo, check);
		// 如果daoId存在，就设置dao
		String daoId = daoMethodVo.getDaoId();
		if (!StringUtils.isBlank(daoId)) {
			Dao dao = daoRepository.findOne(daoId);
			check.setDao(dao);
		}

		dtrv.setMessage("更新成功");
		dtrv.setResult(true);
		DaoMethodConvertor.doToDtrv(dtrv, check);
		return dtrv;
	}

	/**
	 * 删除Dao方法
	 * 
	 * @param id
	 *            Dao方法id
	 * @return ResultVo 结果vo
	 */
	@Transactional
	public ResultVo delete(String id) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(id)) {
			return rv;
		}
		DaoMethod daoMethod = daoMethodRepository.findOne(id);
		if (daoMethod == null) {
			rv.setMessage("删除失败");
			return rv;
		}
		daoMethodRepository.delete(daoMethod);
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            用,号隔开
	 */
	@Transactional
	public ResultVo deleteBatch(String ids) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(ids)) {
			return rv;
		}
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			daoMethodRepository.delete(id);
		}
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

	/**
	 * 生成方法名称
	 * 
	 * @param methodName
	 *            方法名(方法开头的名称,如:findBy,find10Top,find10First等)
	 * @param operations
	 *            操作json数据
	 * @param properties
	 *            属性json数据
	 * @return 结果rv
	 */
	public ResultVo generate(String methodName, String operations, String properties, String addProperties,
			String addPropertyTypes) {
		ResultVo rv = new ResultVo();
		rv.setMessage("生成失败");

		List<String> operationList = JacksonJsonUtil.getInstance().readListAsText(operations);
		List<String> propertyList = JacksonJsonUtil.getInstance().readListAsText(properties);
		List<String> addPropertyList = JacksonJsonUtil.getInstance().readListAsText(addProperties);
		List<String> addPropertyTypeList = JacksonJsonUtil.getInstance().readListAsText(addPropertyTypes);

		if (CollectionUtils.isEmpty(operationList) || CollectionUtils.isEmpty(propertyList)
				|| CollectionUtils.isEmpty(addPropertyList)) {
			return rv;
		}
		if (operationList.size() != propertyList.size() || operationList.size() != addPropertyList.size()) {
			return rv;
		}
		List<String> propertyNameList = new ArrayList<>();
		List<String> propertyTypeList = new ArrayList<>();
		for (String property : propertyList) {
			String[] propertyArr = property.split(",");
			propertyNameList.add(propertyArr[0]);
			propertyTypeList.add(propertyArr[1]);
		}

		// 设置方法名称
		if (StringUtils.isBlank(methodName)) {
			methodName = "findBy";
		}
		String method = methodName;
		String preOperation = "";
		for (int i = 0; i < operationList.size(); i++) {
			String operation = operationList.get(i);
			String propertyName = propertyNameList.get(i);
			String addProperty = addPropertyList.get(i);
			// 首字母大写
			if (!"-1".equals(propertyName)) {
				String first = propertyName.substring(0, 1);
				String last = propertyName.substring(1, propertyName.length());
				propertyName = first.toUpperCase() + last;
			}
			if (!StringUtils.isBlank(addProperty)) {
				// 首字母大写
				if (!StringUtils.isBlank(addProperty)) {
					String first = addProperty.substring(0, 1);
					String last = addProperty.substring(1, addProperty.length());
					addProperty = first.toUpperCase() + last;
				}
				propertyName += addProperty;
			}
			switch (operation) {
			case PojoConstants.OPERATION_BLANK:
				// 上一次操作不是And/Or,并且这次不是第一次操作,那就默认加上And
				if ((!preOperation.equals(PojoConstants.OPERATION_OR)
						|| !preOperation.equals(PojoConstants.OPERATION_AND)) && i != 0) {
					method += "And" + propertyName;
				} else {
					method += propertyName;
				}
				break;
			case PojoConstants.OPERATION_ORDERBYASC:
				method += "OrderBy" + propertyName + "Asc";
				break;
			case PojoConstants.OPERATION_ORDERBYDESC:
				method += "OrderBy" + propertyName + "Desc";
				break;
			case PojoConstants.OPERATION_AND:
				method += "And";
				break;
			case PojoConstants.OPERATION_OR:
				method += "Or";
				break;
			default:
				// 上一次操作不是And/Or,并且这次不是第一次操作,那就默认加上And
				if ((!preOperation.equals(PojoConstants.OPERATION_OR)
						|| !preOperation.equals(PojoConstants.OPERATION_AND)) && i != 0) {
					method += "And" + propertyName + operation;
				} else {
					method += propertyName + operation;
				}
				break;
			}
			preOperation = operation;
		}
		method += "(";
		// 设置方法参数
		boolean isHas = false;
		for (int i = 0; i < operationList.size(); i++) {
			String operation = operationList.get(i);
			String propertyName = propertyNameList.get(i);
			String propertyType = propertyTypeList.get(i);
			String addProperty = addPropertyList.get(i);
			String addPropertyType = addPropertyTypeList.get(i);
			if (!StringUtils.isBlank(addProperty)) {
				// 首字母大写
				if (!StringUtils.isBlank(addProperty)) {
					String first = addProperty.substring(0, 1);
					String last = addProperty.substring(1, addProperty.length());
					addProperty = first.toUpperCase() + last;
				}
				propertyName += addProperty;
				// 如果属性是附加属性，就填写附加属性类型
				propertyType = addPropertyType;
			}

			switch (operation) {
			case PojoConstants.OPERATION_ORDERBYASC:
			case PojoConstants.OPERATION_ORDERBYDESC:
			case PojoConstants.OPERATION_AND:
			case PojoConstants.OPERATION_OR:
				break;
			case PojoConstants.OPERATION_BETWEEN:
				if (isHas) {
					method += ",";
				}
				method += propertyType + " " + propertyName + "1," + propertyType + " " + propertyName;
				isHas = true;
				break;
			default:
				if (isHas) {
					method += ",";
				}
				method += propertyType + " " + propertyName;
				isHas = true;
				break;
			}
		}
		method += ");";
		rv.setMessage("生成成功");
		rv.setResult(true);
		rv.setObj(method);
		return rv;
	}

}
