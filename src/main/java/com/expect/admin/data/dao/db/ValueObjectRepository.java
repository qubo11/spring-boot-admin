package com.expect.admin.data.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.ValueObject;

/**
 * 值对象 JPA
 */
public interface ValueObjectRepository extends JpaRepository<ValueObject, String> {

	/**
	 * 根据实体id获取值对象
	 * 
	 * @param pojoId
	 *            实体id
	 * @return 值对象
	 */
	public ValueObject findByPojoId(String pojoId);

}
