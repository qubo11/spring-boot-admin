package com.expect.admin.service.convertor.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.contants.PojoConstants;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.data.dataobject.db.Property;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.PropertyVo;
import com.expect.admin.utils.HtmlUtil;

/**
 * 属性转换器
 */
public class PropertyConvertor {

	/**
	 * do to vo
	 * 
	 * @param property
	 *            实体do
	 * @return PropertyVo 实体vo
	 */
	public static PropertyVo doToVo(Property property, List<Pojo> pojos) {
		PropertyVo propertyVo = new PropertyVo();
		List<String> types = PojoConstants.getPropertyTypes();
		if (property != null) {
			BeanUtils.copyProperties(property, propertyVo);
			String name = propertyVo.getName();
			// 设置属性名开头大写
			if (StringUtils.isBlank(name)) {
				String first = name.substring(0, 1);
				String end = name.substring(1, name.length());
				propertyVo.setNameUpperCase(first.toUpperCase() + end);
			}
			// 设置其他类型
			if (!StringUtils.isBlank(property.getType())) {
				boolean isExist = false;
				for (String type : types) {
					if (type.equals(property.getType())) {
						isExist = true;
					}
				}
				if (!isExist) {
					propertyVo.setOtherType(property.getType());
					property.setType(PojoConstants.PROPERTY_OTHER);
				}
			}
			// 设置实体
			Pojo pojo = property.getPojo();
			if (pojo != null) {
				propertyVo.setPojoId(pojo.getId());
				propertyVo.setPojoName(pojo.getName());
			}
		}
		// 设置所有类型
		propertyVo.setTypes(types);
		// 设置实体
		if (!CollectionUtils.isEmpty(pojos)) {
			SelectOptionVo pojoSov = new SelectOptionVo();
			for (Pojo pojo : pojos) {
				if (property != null && property.getPojo() != null && property.getPojo().getId() != null) {
					if (property.getPojo().getId().equals(pojo.getId())) {
						pojoSov.addOption(pojo.getId(), pojo.getName(), true);
					} else {
						pojoSov.addOption(pojo.getId(), pojo.getName());
					}
				} else {
					pojoSov.addOption(pojo.getId(), pojo.getName());
				}
			}
			propertyVo.setPojoSov(pojoSov);
		}
		return propertyVo;
	}

	/**
	 * dos to vos
	 * 
	 * @param propertys
	 *            实体dos
	 * @return PropertyVos 实体vo list
	 */
	public static List<PropertyVo> dosToVos(List<Property> propertys) {
		if (!CollectionUtils.isEmpty(propertys)) {
			List<PropertyVo> propertyVos = new ArrayList<>(propertys.size());
			for (Property property : propertys) {
				PropertyVo propertyVo = doToVo(property, null);
				propertyVos.add(propertyVo);
			}
			return propertyVos;
		}
		return null;
	}

	/**
	 * vo to do
	 * 
	 * @param property
	 *            实体vo
	 * @param property
	 *            实体do
	 */
	public static void voToDo(PropertyVo propertyVo, Property property) {
		BeanUtils.copyProperties(propertyVo, property);
	}

	/**
	 * do to dtrv
	 * 
	 * @param dtrv
	 *            实体DataTableRowVo
	 * @param property
	 *            实体do
	 */
	public static void doToDtrv(DataTableRowVo dtrv, Property property) {
		dtrv.setObj(property.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(property.getName());
		dtrv.addData(HtmlUtil.escapeCharacter(property.getType()));
		dtrv.addData(property.getColumnName());
		dtrv.addData(property.getComment());
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getBaseButton(property.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * dos to dtrvs
	 * 
	 * @param propertys
	 *            实体dos
	 * @return 实体DataTableRowVos
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<Property> propertys) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(propertys)) {
			for (Property property : propertys) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, property);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

}
