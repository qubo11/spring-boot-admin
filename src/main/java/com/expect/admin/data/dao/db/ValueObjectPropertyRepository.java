package com.expect.admin.data.dao.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.ValueObjectProperty;

/**
 * 值对象属性 JPA
 */
public interface ValueObjectPropertyRepository extends JpaRepository<ValueObjectProperty, String> {

	/**
	 * 根据值对象id或者属性不为空删除值对象属性
	 * 
	 * @param valueObjectId
	 *            值对象id
	 * @return 删除的结果条数
	 */
	public int deleteByValueObjectIdAndPropertyNotNull(String valueObjectId);

	/**
	 * 根据值对象id获取值对象属性
	 * 
	 * @param valueObjectId
	 *            值对象id
	 * @return 值对象属性
	 */
	public List<ValueObjectProperty> findByValueObjectId(String valueObjectId);

}
