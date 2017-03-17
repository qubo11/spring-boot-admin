package com.expect.admin.data.dao.custom;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.log.LogLogin;

/**
 * 登录日志JPA
 */
public interface LogLoginRepository extends JpaRepository<LogLogin, String> {

}
