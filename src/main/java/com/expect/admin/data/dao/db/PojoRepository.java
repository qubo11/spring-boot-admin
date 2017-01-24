package com.expect.admin.data.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.Pojo;

/**
 * 实体JPA
 */
public interface PojoRepository extends JpaRepository<Pojo, String> {

}
