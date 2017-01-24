package com.expect.admin.service.impl.db;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.db.PropertyRepository;
import com.expect.admin.data.dao.db.ValueObjectPropertyRepository;
import com.expect.admin.data.dao.db.ValueObjectRepository;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.data.dataobject.db.PojoConstants;
import com.expect.admin.data.dataobject.db.Property;
import com.expect.admin.data.dataobject.db.ValueObject;
import com.expect.admin.data.dataobject.db.ValueObjectProperty;
import com.expect.admin.service.convertor.db.ValueObjectPropertyConvertor;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ValueObjectPropertyVo;

/**
 * 值对象属性Service
 */
@Service
public class ValueObjectPropertyService {

	@Autowired
	private ValueObjectPropertyRepository valueObjectPropertyRepository;
	@Autowired
	private ValueObjectRepository valueObjectRepository;
	@Autowired
	private PropertyRepository propertyRepository;

	/**
	 * 根据id获取值对象属性
	 * 
	 * @param id
	 *            值对象属性 id
	 * @return ValueObjectPropertyVo 值对象属性vo
	 */
	public ValueObjectPropertyVo getValueObjectPropertyById(String id) {
		ValueObjectProperty valueObjectProperty = null;
		List<Property> properties = null;
		if (!StringUtils.isBlank(id)) {
			valueObjectProperty = valueObjectPropertyRepository.findOne(id);
			if(valueObjectProperty!=null){
				ValueObject valueObject = valueObjectProperty.getValueObject();
				if (valueObject != null) {
					Pojo pojo = valueObject.getPojo();
					if (pojo != null) {
						properties = pojo.getProperties();
					}
				}
			}
		}
		return ValueObjectPropertyConvertor.doToVo(valueObjectProperty, properties);
	}

	/**
	 * 获取所有的值对象属性，封装成值对象属性vos
	 * 
	 * @return 值对象属性 list
	 */
	public List<ValueObjectPropertyVo> getValueObjectPropertys() {
		List<ValueObjectProperty> valueObjectPropertys = valueObjectPropertyRepository.findAll();
		return ValueObjectPropertyConvertor.dosToVos(valueObjectPropertys);
	}

	/**
	 * 获取所有的值对象属性，封装成值对象属性DataTableVos
	 * 
	 * @return 值对象属性DataTableVo list
	 */
	public List<DataTableRowVo> getValueObjectPropertyDtrvs() {
		List<ValueObjectProperty> valueObjectPropertys = valueObjectPropertyRepository.findAll();
		return ValueObjectPropertyConvertor.dosToDtrvs(valueObjectPropertys);
	}

	/**
	 * 获取所有的值对象属性，封装成DataTableVos
	 * 
	 * @return 值对象属性DataTableVo list
	 */
	public List<DataTableRowVo> getValueObjectPropertyDtrvs(String valueObjectId) {
		List<ValueObjectProperty> valueObjectPropertys = null;
		if (StringUtils.isBlank(valueObjectId)) {
			valueObjectPropertys = valueObjectPropertyRepository.findAll();
		} else {
			valueObjectPropertys = valueObjectPropertyRepository.findByValueObjectId(valueObjectId);
		}
		return ValueObjectPropertyConvertor.dosToDtrvs(valueObjectPropertys);
	}

	/**
	 * 保存值对象属性
	 * 
	 * @param valueObjectPropertyVo
	 *            值对象属性vo
	 * 
	 * @return 值对象属性DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo save(ValueObjectPropertyVo valueObjectPropertyVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(valueObjectPropertyVo.getName())) {
			dtrv.setMessage("ValueObjectProperty名称不能为空");
			return dtrv;
		}
		ValueObjectProperty valueObjectProperty = new ValueObjectProperty();
		ValueObjectPropertyConvertor.voToDo(valueObjectPropertyVo, valueObjectProperty);
		// 如果propertyId存在，就设置对应的属性
		String propertyId = valueObjectPropertyVo.getPropertyId();
		if (!StringUtils.isBlank(propertyId)) {
			Property property = propertyRepository.findOne(propertyId);
			valueObjectProperty.setProperty(property);
		}

		ValueObjectProperty result = valueObjectPropertyRepository.save(valueObjectProperty);
		if (result != null) {
			dtrv.setMessage("保存成功");
			dtrv.setResult(true);
			ValueObjectPropertyConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 更新值对象属性
	 * 
	 * @param valueObjectPropertyVo
	 *            值对象属性vo
	 * 
	 * @return 值对象属性DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo update(ValueObjectPropertyVo valueObjectPropertyVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(valueObjectPropertyVo.getId())) {
			return dtrv;
		}
		if (StringUtils.isBlank(valueObjectPropertyVo.getName())) {
			dtrv.setMessage("ValueObjectProperty名称不能为空");
			return dtrv;
		}
		ValueObjectProperty check = valueObjectPropertyRepository.findOne(valueObjectPropertyVo.getId());
		if (check == null) {
			return dtrv;
		}
		ValueObjectPropertyConvertor.voToDo(valueObjectPropertyVo, check);
		// 如果propertyId存在，就设置对应的属性
		String propertyId = valueObjectPropertyVo.getPropertyId();
		if (!StringUtils.isBlank(propertyId)) {
			Property property = propertyRepository.findOne(propertyId);
			check.setProperty(property);
		}

		dtrv.setMessage("更新成功");
		dtrv.setResult(true);
		ValueObjectPropertyConvertor.doToDtrv(dtrv, check);
		return dtrv;
	}

	/**
	 * 删除值对象属性
	 * 
	 * @param id
	 *            值对象属性id
	 * @return ResultVo 结果vo
	 */
	@Transactional
	public ResultVo delete(String id) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(id)) {
			return rv;
		}
		ValueObjectProperty valueObjectProperty = valueObjectPropertyRepository.findOne(id);
		if (valueObjectProperty == null) {
			rv.setMessage("删除失败");
			return rv;
		}
		valueObjectPropertyRepository.delete(valueObjectProperty);
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            用,号隔开
	 * @return 结果vo
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
			valueObjectPropertyRepository.delete(id);
		}
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

	/**
	 * 同步属性
	 * 
	 * @param pojoId
	 *            实体id
	 * @return 结果vo
	 */
	@Transactional
	public ResultVo sync(String valueObjectId) {
		ResultVo rv = new ResultVo();
		rv.setMessage("同步失败");

		if (StringUtils.isBlank(valueObjectId)) {
			return rv;
		}
		ValueObject valueObject = valueObjectRepository.findOne(valueObjectId);
		if (valueObject == null) {
			return rv;
		}
		Pojo pojo = valueObject.getPojo();
		if (pojo == null) {
			return rv;
		}
		List<Property> properties = pojo.getProperties();
		if (CollectionUtils.isEmpty(properties)) {
			return rv;
		}

		// 删除原来的属性转为值对象属性的数据
		valueObjectPropertyRepository.deleteByValueObjectIdAndPropertyNotNull(valueObjectId);

		// 复制属性到vo中
		for (Property property : properties) {
			ValueObjectProperty valueObjectProperty = new ValueObjectProperty();
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
			valueObjectProperty.setName(property.getName());
			valueObjectProperty.setType(property.getType());
			valueObjectProperty.setComment(property.getComment());
			valueObjectProperty.setValueObject(valueObject);
			valueObjectProperty.setProperty(property);
			valueObjectPropertyRepository.save(valueObjectProperty);
		}
		rv.setMessage("同步成功");
		rv.setResult(true);
		List<DataTableRowVo> dtrvs = getValueObjectPropertyDtrvs();
		rv.setObj(dtrvs);
		return rv;
	}

}
