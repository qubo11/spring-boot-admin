package com.expect.admin.service.impl.db;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.db.PojoRepository;
import com.expect.admin.data.dao.db.PropertyRepository;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.data.dataobject.db.PojoConstants;
import com.expect.admin.data.dataobject.db.Property;
import com.expect.admin.service.convertor.db.PropertyConvertor;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.PropertyVo;

@Service
public class PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private PojoRepository pojoRepository;

	/**
	 * 根据id获取属性
	 * 
	 * @param id
	 *            属性 id
	 * @return 属性vo
	 */
	public PropertyVo getPropertyById(String id) {
		Property property = null;
		if (!StringUtils.isBlank(id)) {
			property = propertyRepository.findOne(id);
		}
		List<Pojo> pojos = pojoRepository.findAll();
		return PropertyConvertor.doToVo(property, pojos);
	}

	/**
	 * 获取所有的属性，封装成属性vo列表
	 * 
	 * @return 属性vo list
	 */
	public List<PropertyVo> getPropertys() {
		List<Property> propertys = propertyRepository.findAll();
		return PropertyConvertor.dosToVos(propertys);
	}

	/**
	 * 获取所有的属性，封装成DataTableVos
	 * 
	 * @return 属性DataTableVo list
	 */
	public List<DataTableRowVo> getPropertyDtrvs(String pojoId) {
		List<Property> propertys = null;
		if (StringUtils.isBlank(pojoId)) {
			propertys = propertyRepository.findAll();
		} else {
			propertys = propertyRepository.findByPojoId(pojoId);
		}
		return PropertyConvertor.dosToDtrvs(propertys);
	}

	/**
	 * 保存属性
	 * 
	 * @param propertyVo
	 *            属性vo
	 * 
	 * @return 属性DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo save(PropertyVo propertyVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(propertyVo.getName())) {
			dtrv.setMessage("实体名称不能为空");
			return dtrv;
		}
		Property property = new Property();
		PropertyConvertor.voToDo(propertyVo, property);
		if (!StringUtils.isBlank(property.getType()) && PojoConstants.PROPERTY_OTHER.equals(property.getType())) {
			property.setType(propertyVo.getOtherType());
		}
		// 如果pojoId存在，就设置Pojo
		String pojoId = propertyVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			property.setPojo(pojo);
		}
		Property result = propertyRepository.save(property);
		if (result != null) {
			dtrv.setMessage("保存成功");
			dtrv.setResult(true);
			PropertyConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 更新属性
	 * 
	 * @param propertyVo
	 *            属性vo
	 * 
	 * @return 属性DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo update(PropertyVo propertyVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(propertyVo.getId())) {
			return dtrv;
		}
		if (StringUtils.isBlank(propertyVo.getName())) {
			dtrv.setMessage("实体名称不能为空");
			return dtrv;
		}
		Property check = propertyRepository.findOne(propertyVo.getId());
		if (check == null) {
			return dtrv;
		}
		PropertyConvertor.voToDo(propertyVo, check);
		if (!StringUtils.isBlank(check.getType()) && PojoConstants.PROPERTY_OTHER.equals(check.getType())) {
			check.setType(propertyVo.getOtherType());
		}
		// 如果pojoId存在，就设置Pojo
		String pojoId = propertyVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			check.setPojo(pojo);
		}

		dtrv.setMessage("更新成功");
		dtrv.setResult(true);
		PropertyConvertor.doToDtrv(dtrv, check);
		return dtrv;
	}

	/**
	 * 删除属性
	 * 
	 * @param id
	 *            属性id
	 * @return ResultVo 结果vo
	 */
	@Transactional
	public ResultVo delete(String id) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(id)) {
			return rv;
		}
		Property property = propertyRepository.findOne(id);
		if (property == null) {
			rv.setMessage("删除失败");
			return rv;
		}
		propertyRepository.delete(property);
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
			propertyRepository.delete(id);
		}
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

}
