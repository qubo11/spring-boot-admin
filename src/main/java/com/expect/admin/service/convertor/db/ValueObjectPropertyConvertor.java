package com.expect.admin.service.convertor.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.contants.PojoConstants;
import com.expect.admin.data.dataobject.db.Property;
import com.expect.admin.data.dataobject.db.ValueObjectProperty;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ValueObjectPropertyVo;
import com.expect.admin.utils.RegexUtil;

/**
 * 值对象 转换器
 */
public class ValueObjectPropertyConvertor {

	/**
	 * do to vo
	 * 
	 * @param valueObjectProperty
	 *            值对象 do
	 * @return ValueObjectPropertyVo 值对象属性 vo
	 */
	public static ValueObjectPropertyVo doToVo(ValueObjectProperty valueObjectProperty, List<Property> properties) {
		ValueObjectPropertyVo valueObjectPropertyVo = new ValueObjectPropertyVo();
		if (valueObjectProperty != null) {
			BeanUtils.copyProperties(valueObjectProperty, valueObjectPropertyVo);
		}
		// 设置属性sov
		SelectOptionVo propertySov = new SelectOptionVo();
		propertySov.addOption("", "无");
		if (!CollectionUtils.isEmpty(properties)) {
			for (Property property : properties) {
				String type = property.getType();
				// 如果property属性类型不是基本类型，就不保存
				List<String> baseTypes = PojoConstants.getPropertyTypes();
				boolean isBaseType = false;
				for (String baseType : baseTypes) {
					if (type.equals(baseType)) {
						isBaseType = true;
						break;
					}
				}
				if (!isBaseType) {
					continue;
				}
				if (valueObjectProperty != null && valueObjectProperty.getProperty() != null) {
					if (valueObjectProperty.getProperty().getId().equals(property.getId())) {
						propertySov.addOption(property.getId() + "," + property.getType(), property.getName(), true);
					} else {
						propertySov.addOption(property.getId() + "," + property.getType(), property.getName());
					}
				} else {
					propertySov.addOption(property.getId() + "," + property.getType(), property.getName());
				}
			}
		}
		valueObjectPropertyVo.setPropertySov(propertySov);
		// 设置类型
		List<String> types = PojoConstants.getValueObjectPropertyTypes();
		valueObjectPropertyVo.setTypes(types);
		// 设置表单域类型
		List<String> formTypes = PojoConstants.getFormTypes();
		valueObjectPropertyVo.setFormTypes(formTypes);
		// 设置验证类型
		List<String> validateTypes = PojoConstants.getValidateTypes();
		valueObjectPropertyVo.setValidateTypes(validateTypes);
		// 日期格式化类型
		List<String> dateFormats = PojoConstants.getDateFormats();
		valueObjectPropertyVo.setDateFormats(dateFormats);
		return valueObjectPropertyVo;
	}

	/**
	 * dos to vos
	 * 
	 * @param valueObjectPropertys
	 *            值对象 dos
	 * @return ValueObjectPropertyVos 值对象 vo list
	 */
	public static List<ValueObjectPropertyVo> dosToVos(List<ValueObjectProperty> valueObjectPropertys) {
		if (!CollectionUtils.isEmpty(valueObjectPropertys)) {
			List<ValueObjectPropertyVo> valueObjectPropertyVos = new ArrayList<>(valueObjectPropertys.size());
			for (ValueObjectProperty valueObjectProperty : valueObjectPropertys) {
				ValueObjectPropertyVo valueObjectPropertyVo = doToVo(valueObjectProperty, null);
				valueObjectPropertyVos.add(valueObjectPropertyVo);
			}
			return valueObjectPropertyVos;
		}
		return null;
	}

	/**
	 * vo to do
	 * 
	 * @param valueObjectProperty
	 *            值对象 vo
	 * @param valueObjectProperty
	 *            值对象 do
	 */
	public static void voToDo(ValueObjectPropertyVo valueObjectPropertyVo, ValueObjectProperty valueObjectProperty) {
		BeanUtils.copyProperties(valueObjectPropertyVo, valueObjectProperty);
		// 设置验证类型对象的正则表达式
		String validateType = valueObjectPropertyVo.getValidateType();
		switch (validateType) {
		case PojoConstants.VALIDATETYPE_NULL:
			valueObjectProperty.setRegex(null);
			break;
		case PojoConstants.VALIDATETYPE_CUSTOM:
			if (StringUtils.isBlank(validateType)) {
				valueObjectProperty.setRegex(null);
			}
			break;
		case PojoConstants.VALIDATETYPE_EMAIL:
			valueObjectProperty.setRegex(RegexUtil.emailRegex);
			break;
		case PojoConstants.VALIDATETYPE_IDCARD:
			valueObjectProperty.setRegex(RegexUtil.idCardRegex);
			break;
		case PojoConstants.VALIDATETYPE_URL:
			valueObjectProperty.setRegex(RegexUtil.urlRegex);
			break;
		case PojoConstants.VALIDATETYPE_PHONE:
			valueObjectProperty.setRegex(RegexUtil.phoneRegex);
			break;
		}
	}

	/**
	 * do to dtrv
	 * 
	 * @param dtrv
	 *            值对象 DataTableRowVo
	 * @param valueObjectProperty
	 *            值对象 do
	 */
	public static void doToDtrv(DataTableRowVo dtrv, ValueObjectProperty valueObjectProperty) {
		dtrv.setObj(valueObjectProperty.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(valueObjectProperty.getName());
		dtrv.addData(valueObjectProperty.getType());
		dtrv.addData(valueObjectProperty.getComment());
		Property property = valueObjectProperty.getProperty();
		if (property != null) {
			dtrv.addData(valueObjectProperty.getName());
		} else {
			dtrv.addData("");
		}
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getBaseButton(valueObjectProperty.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * dos to dtrvs
	 * 
	 * @param valueObjectPropertys
	 *            值对象 dos
	 * @return 值对象 DataTableRowVos
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<ValueObjectProperty> valueObjectPropertys) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(valueObjectPropertys)) {
			for (ValueObjectProperty valueObjectProperty : valueObjectPropertys) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, valueObjectProperty);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

}
