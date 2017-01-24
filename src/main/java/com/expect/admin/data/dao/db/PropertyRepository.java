package com.expect.admin.data.dao.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.Property;

/**
 * 实体的属性JPA
 */
public interface PropertyRepository extends JpaRepository<Property, String> {

	/**
	 * 根据实体id，获取所有属性
	 * 
	 * @param pojoId
	 *            实体id
	 * @return 属性列表
	 */
	public List<Property> findByPojoId(String pojoId);

}
