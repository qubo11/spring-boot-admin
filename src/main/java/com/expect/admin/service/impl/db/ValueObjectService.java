package com.expect.admin.service.impl.db;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.db.PojoRepository;
import com.expect.admin.data.dao.db.ValueObjectRepository;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.data.dataobject.db.ValueObject;
import com.expect.admin.service.convertor.db.ValueObjectConvertor;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ValueObjectVo;

/**
 * 值对象Service
 */
@Service
public class ValueObjectService {

	@Autowired
	private ValueObjectRepository valueObjectRepository;
	@Autowired
	private PojoRepository pojoRepository;

	/**
	 * 根据id获取值对象
	 * 
	 * @param id
	 *            值对象 id
	 * @return ValueObjectVo 值对象vo
	 */
	public ValueObjectVo getValueObjectById(String id) {
		ValueObject valueObject = null;
		if (!StringUtils.isBlank(id)) {
			valueObject = valueObjectRepository.findOne(id);
		}
		List<Pojo> pojos = pojoRepository.findAll();
		return ValueObjectConvertor.doToVo(valueObject, pojos);
	}

	/**
	 * 根据实体id获取值对象
	 * 
	 * @param pojoId
	 *            实体id
	 * @return 值对象vo
	 */
	public ValueObjectVo getValueObjectByPojoId(String pojoId) {
		ValueObject valueObject = valueObjectRepository.findByPojoId(pojoId);
		return ValueObjectConvertor.doToVo(valueObject, null);
	}

	/**
	 * 获取所有的值对象，封装成值对象vos
	 * 
	 * @return 值对象 list
	 */
	public List<ValueObjectVo> getValueObjects() {
		List<ValueObject> valueObjects = valueObjectRepository.findAll();
		return ValueObjectConvertor.dosToVos(valueObjects);
	}

	/**
	 * 获取所有的值对象，封装成值对象DataTableVos
	 * 
	 * @return 值对象DataTableVo list
	 */
	public List<DataTableRowVo> getValueObjectDtrvs() {
		List<ValueObject> valueObjects = valueObjectRepository.findAll();
		return ValueObjectConvertor.dosToDtrvs(valueObjects);
	}

	/**
	 * 保存值对象
	 * 
	 * @param valueObjectVo
	 *            值对象vo
	 * 
	 * @return 值对象DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo save(ValueObjectVo valueObjectVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		ValueObject valueObject = new ValueObject();
		ValueObjectConvertor.voToDo(valueObjectVo, valueObject);
		// 设置所属实体
		if (!StringUtils.isBlank(valueObjectVo.getPojoId())) {
			Pojo pojo = pojoRepository.findOne(valueObjectVo.getPojoId());
			valueObject.setPojo(pojo);
		}

		ValueObject result = valueObjectRepository.save(valueObject);
		if (result != null) {
			dtrv.setMessage("保存成功");
			dtrv.setResult(true);
			ValueObjectConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 更新值对象
	 * 
	 * @param valueObjectVo
	 *            值对象vo
	 * 
	 * @return 值对象DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo update(ValueObjectVo valueObjectVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(valueObjectVo.getId())) {
			return dtrv;
		}
		ValueObject check = valueObjectRepository.findOne(valueObjectVo.getId());
		if (check == null) {
			return dtrv;
		}
		ValueObjectConvertor.voToDo(valueObjectVo, check);
		// 设置所属实体
		if (!StringUtils.isBlank(valueObjectVo.getPojoId())) {
			Pojo pojo = pojoRepository.findOne(valueObjectVo.getPojoId());
			check.setPojo(pojo);
		}

		dtrv.setMessage("更新成功");
		dtrv.setResult(true);
		ValueObjectConvertor.doToDtrv(dtrv, check);
		return dtrv;
	}

	/**
	 * 删除值对象
	 * 
	 * @param id
	 *            值对象id
	 * @return ResultVo 结果vo
	 */
	@Transactional
	public ResultVo delete(String id) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(id)) {
			return rv;
		}
		ValueObject valueObject = valueObjectRepository.findOne(id);
		if (valueObject == null) {
			rv.setMessage("删除失败");
			return rv;
		}
		valueObjectRepository.delete(valueObject);
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
			valueObjectRepository.delete(id);
		}
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

	/**
	 * 保存或者更新实体
	 * 
	 * @param valueObjectVo
	 *            值对象vo
	 * 
	 * @return ResultVo
	 */
	@Transactional
	public ResultVo saveOrUpdate(ValueObjectVo valueObjectVo) {
		ResultVo rv = new ResultVo();
		rv.setMessage("保存失败");

		ValueObject valueObject = null;
		// 如果id不存在，就保存
		if (StringUtils.isBlank(valueObjectVo.getId())) {
			valueObject = new ValueObject();
		} else {
			// 如果id存在，就更新
			valueObject = valueObjectRepository.findOne(valueObjectVo.getId());
			if (valueObject == null) {
				return rv;
			}
		}
		ValueObjectConvertor.voToDo(valueObjectVo, valueObject);
		// 如果实体id存在，就设置实体
		String pojoId = valueObjectVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			valueObject.setPojo(pojo);
		}

		if (StringUtils.isBlank(valueObjectVo.getId())) {
			ValueObject result = valueObjectRepository.save(valueObject);
			if (result == null) {
				return rv;
			}
		}
		rv.setMessage("保存成功");
		rv.setResult(true);
		return rv;
	}

}
