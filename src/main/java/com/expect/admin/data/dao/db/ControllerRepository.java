package com.expect.admin.data.dao.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.Controller;

/**
 * 控制层 JPA
 */
public interface ControllerRepository extends JpaRepository<Controller, String> {

	/**
	 * 根据实体id获取控制层列表
	 * 
	 * @param pojoId
	 *            实体id
	 * @return 控制层 list
	 */
	public List<Controller> findByPojoId(String pojoId);

}
