package com.expect.admin.data.dao.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.Business;

/**
 * Business JPA
 */
public interface BusinessRepository extends JpaRepository<Business, String> {

	/**
	 * 根据实体id获取业务实体
	 * 
	 * @param pojoId
	 *            实体id
	 * @return Business列表
	 */
	public List<Business> findByPojoId(String pojoId);

}
