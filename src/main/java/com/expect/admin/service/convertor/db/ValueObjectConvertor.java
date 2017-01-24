package com.expect.admin.service.convertor.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.data.dataobject.db.ValueObject;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ValueObjectVo;

/**
 * 值对象 转换器
 */
public class ValueObjectConvertor {

	/**
	 * do to vo
	 * 
	 * @param valueObject
	 *            值对象 do
	 * @return ValueObjectVo 值对象属性 vo
	 */
	public static ValueObjectVo doToVo(ValueObject valueObject, List<Pojo> pojos) {
		ValueObjectVo valueObjectVo = new ValueObjectVo();
		if (valueObject != null) {
			BeanUtils.copyProperties(valueObject, valueObjectVo);
		}
		// 设置实体
		if (!CollectionUtils.isEmpty(pojos)) {
			SelectOptionVo pojoSov = new SelectOptionVo();
			pojoSov.addOption("", "无");
			for (Pojo pojo : pojos) {
				if (valueObject != null && valueObject.getPojo() != null && valueObject.getPojo().getId() != null) {
					if (valueObject.getPojo().getId().equals(pojo.getId())) {
						pojoSov.addOption(pojo.getId(), pojo.getName(), true);
					} else {
						pojoSov.addOption(pojo.getId(), pojo.getName());
					}
				} else {
					pojoSov.addOption(pojo.getId(), pojo.getName());
				}
			}
			valueObjectVo.setPojoSov(pojoSov);
		}
		return valueObjectVo;
	}

	/**
	 * dos to vos
	 * 
	 * @param valueObjects
	 *            值对象 dos
	 * @return ValueObjectVos 值对象 vo list
	 */
	public static List<ValueObjectVo> dosToVos(List<ValueObject> valueObjects) {
		if (!CollectionUtils.isEmpty(valueObjects)) {
			List<ValueObjectVo> valueObjectVos = new ArrayList<>(valueObjects.size());
			for (ValueObject valueObject : valueObjects) {
				ValueObjectVo valueObjectVo = doToVo(valueObject, null);
				valueObjectVos.add(valueObjectVo);
			}
			return valueObjectVos;
		}
		return null;
	}

	/**
	 * vo to do
	 * 
	 * @param valueObject
	 *            值对象 vo
	 * @param valueObject
	 *            值对象 do
	 */
	public static void voToDo(ValueObjectVo valueObjectVo, ValueObject valueObject) {
		BeanUtils.copyProperties(valueObjectVo, valueObject);
	}

	/**
	 * do to dtrv
	 * 
	 * @param dtrv
	 *            值对象 DataTableRowVo
	 * @param valueObject
	 *            值对象 do
	 */
	public static void doToDtrv(DataTableRowVo dtrv, ValueObject valueObject) {
		dtrv.setObj(valueObject.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(valueObject.getPackageName());
		dtrv.addData(valueObject.getName());
		dtrv.addData(valueObject.getComment());
		Pojo pojo = valueObject.getPojo();
		if (pojo != null) {
			dtrv.addData(pojo.getName());
		} else {
			dtrv.addData("");
		}
		// 设置操作的button
		StringBuilder sb = new StringBuilder();
		sb.append(DataTableButtonFactory.getYellowButton("属性",
				"data-url='admin/db/valueObject/property/managePage?valueObjectId=" + valueObject.getId() + "'"));
		sb.append(DataTableButtonFactory.getDetailButton("data-id='" + valueObject.getId() + "'"));
		sb.append(DataTableButtonFactory.getUpdateButton("data-id='" + valueObject.getId() + "'"));
		sb.append(DataTableButtonFactory.getDeleteButton("data-id='" + valueObject.getId() + "'"));
		dtrv.addData(sb.toString());
	}

	/**
	 * dos to dtrvs
	 * 
	 * @param valueObjects
	 *            值对象 dos
	 * @return 值对象 DataTableRowVos
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<ValueObject> valueObjects) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(valueObjects)) {
			for (ValueObject valueObject : valueObjects) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, valueObject);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

}
