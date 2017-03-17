package com.expect.admin.service.convertor.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.custom.Company;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.CompanyVo;

/**
 * 公司Convertor
 */
public class CompanyConvertor {

	/**
	 * vo to do
	 */
	public static void voToDo(CompanyVo companyVo, Company company) {
		BeanUtils.copyProperties(companyVo, company);
	}

	/**
	 * do to vo
	 */
	public static CompanyVo doToVo(Company company) {
		CompanyVo companyVo = new CompanyVo();
		if (company == null) {
			return companyVo;
		}
		BeanUtils.copyProperties(company, companyVo);
		Company parentCompany = company.getParentCompany();
		if (parentCompany != null) {
			companyVo.setParentCompanyId(parentCompany.getId());
			companyVo.setParentCompanyName(parentCompany.getName());
		}
		return companyVo;
	}

	/**
	 * dos to vos
	 */
	public static List<CompanyVo> dosToVos(Collection<Company> companys) {
		List<CompanyVo> companyVos = new ArrayList<>();
		for (Company company : companys) {
			CompanyVo companyVo = doToVo(company);
			companyVos.add(companyVo);
		}
		return companyVos;
	}

	/**
	 * dos to dtrvs
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<Company> companys) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(companys)) {
			for (Company company : companys) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, company, null);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

	/**
	 * do to dtrv
	 */
	public static void doToDtrv(DataTableRowVo dtrv, Company company, Company parentCompany) {
		dtrv.setObj(company.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(company.getCode());
		dtrv.addData(company.getName());
		dtrv.addData(company.getAddress());
		if (parentCompany != null) {
			dtrv.addData(parentCompany.getName());
		} else {
			dtrv.addData("");
		}
		dtrv.addData(company.getDescription());
		// 设置操作的button
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getDefaultButton(company.getId()));
		dtrv.addData(buttonSb.toString());
	}
}
