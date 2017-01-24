package com.expect.admin.data.dao.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.DaoMethod;

/**
 * dao method JPA
 */
public interface DaoMethodRepository extends JpaRepository<DaoMethod, String> {

	/**
	 * 根据daoId获取dao方法
	 * 
	 * @param daoId
	 * @return dao方法
	 */
	public List<DaoMethod> findByDaoId(String daoId);

}
