package com.expect.admin.data.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.db.Project;

/**
 * 项目JPA
 */
public interface ProjectRepository extends JpaRepository<Project, String> {

}
