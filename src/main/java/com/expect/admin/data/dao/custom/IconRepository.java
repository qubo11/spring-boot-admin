package com.expect.admin.data.dao.custom;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.custom.Icon;

/**
 * 图标JPA
 */
public interface IconRepository extends JpaRepository<Icon, String> {

}
