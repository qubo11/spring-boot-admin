package com.expect.admin.data.dao.custom;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expect.admin.data.dataobject.custom.Company;

public interface CompanyRepository extends JpaRepository<Company, String> {

}
