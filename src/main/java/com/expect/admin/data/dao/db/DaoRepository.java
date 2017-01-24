package com.expect.admin.data.dao.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.Dao;

/**
 * dao JPA
 */
public interface DaoRepository extends JpaRepository<Dao, String> {

	/**
	 * 根据实体id获取dao
	 * 
	 * @param pojoId
	 *            实体id
	 * @return dao列表
	 */
	public List<Dao> findByPojoId(String pojoId);

}
