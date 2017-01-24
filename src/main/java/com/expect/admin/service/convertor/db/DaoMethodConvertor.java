package com.expect.admin.service.convertor.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.db.Dao;
import com.expect.admin.data.dataobject.db.DaoMethod;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.data.dataobject.db.Property;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.DaoMethodVo;

/**
 * DaoMethod 转换器
 */
public class DaoMethodConvertor {

	/**
	 * do to vo
	 * 
	 * @param daoMethod
	 *            dao方法 do
	 * @return DaoMethodVo dao方法 vo
	 */
	public static DaoMethodVo doToVo(DaoMethod daoMethod, List<String> operations, List<String> methodNames, Dao dao) {
		DaoMethodVo daoMethodVo = new DaoMethodVo();
		if (daoMethod != null) {
			BeanUtils.copyProperties(daoMethod, daoMethodVo);
		}
		// 设置操作类型
		if (!CollectionUtils.isEmpty(operations)) {
			SelectOptionVo operationSov = new SelectOptionVo();
			for (String operation : operations) {
				operationSov.addOption(operation, operation);
			}
			daoMethodVo.setOperationSov(operationSov);
		}
		// 设置方法名
		if (!CollectionUtils.isEmpty(methodNames)) {
			SelectOptionVo methodNameSov = new SelectOptionVo();
			for (String methodName : methodNames) {
				methodNameSov.addOption(methodName, methodName);
			}
			daoMethodVo.setMethodNameSov(methodNameSov);
		}
		if (dao != null) {
			Pojo pojo = dao.getPojo();
			if (pojo != null) {
				List<Property> properties = pojo.getProperties();
				if (!CollectionUtils.isEmpty(properties)) {
					SelectOptionVo propertySov = new SelectOptionVo();
					for (Property property : properties) {
						String type = property.getType();
						if (type.contains("Set") || type.contains("List")) {
							continue;
						}
						propertySov.addOption(property.getName() + "," + property.getType(), property.getName());
					}
					daoMethodVo.setPropertySov(propertySov);
				}
			}
		}
		return daoMethodVo;
	}

	/**
	 * dos to vos
	 * 
	 * @param daoMethods
	 *            dao方法 dos
	 * @return DaoMethodVos dao方法 vo list
	 */
	public static List<DaoMethodVo> dosToVos(List<DaoMethod> daoMethods) {
		if (!CollectionUtils.isEmpty(daoMethods)) {
			List<DaoMethodVo> daoMethodVos = new ArrayList<>(daoMethods.size());
			for (DaoMethod daoMethod : daoMethods) {
				DaoMethodVo daoMethodVo = doToVo(daoMethod, null, null, null);
				daoMethodVos.add(daoMethodVo);
			}
			return daoMethodVos;
		}
		return null;
	}

	/**
	 * vo to do
	 * 
	 * @param daoMethod
	 *            dao方法 vo
	 * @param daoMethod
	 *            dao方法 do
	 */
	public static void voToDo(DaoMethodVo daoMethodVo, DaoMethod daoMethod) {
		BeanUtils.copyProperties(daoMethodVo, daoMethod);
	}

	/**
	 * do to dtrv
	 * 
	 * @param dtrv
	 *            dao方法 DataTableRowVo
	 * @param daoMethod
	 *            dao方法 do
	 */
	public static void doToDtrv(DataTableRowVo dtrv, DaoMethod daoMethod) {
		dtrv.setObj(daoMethod.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(daoMethod.getName());
		dtrv.addData(daoMethod.getReturnType());
		dtrv.addData(daoMethod.getQuery());
		dtrv.addData(daoMethod.getModifying());
		dtrv.addData(daoMethod.getComment());
		// 设置操作的button
		StringBuilder sb = new StringBuilder();
		sb.append(DataTableButtonFactory.getDetailButton("data-id='" + daoMethod.getId() + "'"));
		sb.append(DataTableButtonFactory.getUpdateButton("data-id='" + daoMethod.getId() + "'"));
		sb.append(DataTableButtonFactory.getDeleteButton("data-id='" + daoMethod.getId() + "'"));
		dtrv.addData(sb.toString());
	}

	/**
	 * dos to dtrvs
	 * 
	 * @param daoMethods
	 *            dao方法 dos
	 * @return dao方法 DataTableRowVos
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<DaoMethod> daoMethods) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(daoMethods)) {
			for (DaoMethod daoMethod : daoMethods) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, daoMethod);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

}
