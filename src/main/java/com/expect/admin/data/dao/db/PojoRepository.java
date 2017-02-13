package com.expect.admin.data.dao.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.Pojo;

/**
 * 实体JPA
 */
public interface PojoRepository extends JpaRepository<Pojo, String> {

	/**
	 * 根据项目id获取实体
	 * 
	 * @param projectId
	 *            项目id
	 * @return 实体
	 */
	public List<Pojo> findByProjectId(String projectId);

}
