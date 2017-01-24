package com.expect.admin.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.log.LogDbParam;

/**
 * 日志JPA
 */
public interface LogDbParamRepository extends JpaRepository<LogDbParam, String> {

}
