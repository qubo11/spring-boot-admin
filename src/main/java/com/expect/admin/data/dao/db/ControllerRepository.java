package com.expect.admin.data.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.Controller;

/**
 * 控制层 JPA
 */
public interface ControllerRepository extends JpaRepository<Controller, String> {

	/**
	 * 根据实体id获取控制层
	 * 
	 * @param pojoId
	 *            实体id
	 * @return 控制层
	 */
	public Controller findByPojoId(String pojoId);

}
