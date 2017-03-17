package com.expect.admin.service.impl.custom;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.custom.CompanyRepository;
import com.expect.admin.data.dataobject.custom.Company;
import com.expect.admin.service.convertor.custom.CompanyConvertor;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.CompanyVo;
import com.expect.custom.service.vo.component.ResultVo;

/**
 * 公司Service
 */
@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	/**
	 * 获取所有的权限信息，封装成dtrsv
	 */
	public List<DataTableRowVo> getCompanyDtrsv() {
		List<Company> roles = companyRepository.findAll();
		List<DataTableRowVo> dtrvs = CompanyConvertor.dosToDtrvs(roles);
		return dtrvs;
	}

	/**
	 * 根据id获取公司
	 */
	public CompanyVo getCompanyById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		Company company = companyRepository.findOne(id);
		return CompanyConvertor.doToVo(company);
	}

	/**
	 * 保存公司
	 */
	@Transactional
	public DataTableRowVo save(CompanyVo companyVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("增加失败");
		if (StringUtils.isBlank(companyVo.getName())) {
			dtrv.setMessage("公司名不能为空");
			return dtrv;
		}
		Company company = new Company();
		CompanyConvertor.voToDo(companyVo, company);
		// 设置父公司
		Company parentCompany = null;
		if (!StringUtils.isBlank(companyVo.getParentCompanyId())) {
			parentCompany = companyRepository.findOne(companyVo.getParentCompanyId());
		}
		company.setParentCompany(parentCompany);

		Company result = companyRepository.save(company);
		if (result != null) {
			dtrv.setResult(true);
			dtrv.setMessage("增加成功");
			CompanyConvertor.doToDtrv(dtrv, result, parentCompany);
		}
		return dtrv;
	}

	/**
	 * 修改公司
	 */
	@Transactional
	public DataTableRowVo update(CompanyVo companyVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("修改失败");
		if (StringUtils.isBlank(companyVo.getId())) {
			dtrv.setMessage("公司名不能为空");
			return dtrv;
		}
		if (StringUtils.isBlank(companyVo.getName())) {
			dtrv.setMessage("公司名不能为空");
			return dtrv;
		}
		Company checkCompany = companyRepository.findOne(companyVo.getId());
		if (checkCompany == null) {
			return dtrv;
		}
		CompanyConvertor.voToDo(companyVo, checkCompany);
		// 设置父公司
		Company parentCompany = null;
		if (!StringUtils.isBlank(companyVo.getParentCompanyId())) {
			parentCompany = companyRepository.findOne(companyVo.getParentCompanyId());
		}
		checkCompany.setParentCompany(parentCompany);

		dtrv.setResult(true);
		dtrv.setMessage("修改成功");
		CompanyConvertor.doToDtrv(dtrv, checkCompany, parentCompany);
		return dtrv;
	}

	/**
	 * 删除公司
	 */
	public ResultVo delete(String id) {
		ResultVo resultVo = new ResultVo();
		resultVo.setMessage("删除失败");
		Company company = companyRepository.findOne(id);
		if (company == null) {
			return resultVo;
		}

		companyRepository.delete(id);
		resultVo.setMessage("删除成功");
		resultVo.setResult(true);
		return resultVo;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            用,号隔开
	 */
	@Transactional
	public ResultVo deleteBatch(String ids) {
		ResultVo resultVo = new ResultVo();
		resultVo.setMessage("删除失败");
		if (StringUtils.isBlank(ids)) {
			return resultVo;
		}
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			companyRepository.delete(id);
		}
		resultVo.setResult(true);
		resultVo.setMessage("删除成功");
		return resultVo;
	}
}
