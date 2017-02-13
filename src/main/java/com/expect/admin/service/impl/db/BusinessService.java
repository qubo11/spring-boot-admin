package com.expect.admin.service.impl.db;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.db.BusinessRepository;
import com.expect.admin.data.dao.db.PojoRepository;
import com.expect.admin.data.dataobject.db.Business;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.service.convertor.db.BusinessConvertor;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.BusinessVo;

/**
 * 业务Service
 */
@Service
public class BusinessService {

	@Autowired
	private BusinessRepository businessRepository;
	@Autowired
	private PojoRepository pojoRepository;

	/**
	 * 根据id获取业务
	 * 
	 * @param id
	 *            业务 id
	 * @return BusinessVo 业务vo
	 */
	public BusinessVo getBusinessById(String id) {
		Business business = null;
		if (!StringUtils.isBlank(id)) {
			business = businessRepository.findOne(id);
		}
		List<Pojo> pojos = pojoRepository.findAll();
		return BusinessConvertor.doToVo(business, pojos);
	}

	/**
	 * 根据实体id获取业务
	 * 
	 * @param id
	 *            实体 id
	 * @return BusinessVo 业务vo
	 */
	public BusinessVo getBusinessByPojoId(String pojoId) {
		Business business = businessRepository.findByPojoId(pojoId);
		List<Pojo> pojos = pojoRepository.findAll();
		return BusinessConvertor.doToVo(business, pojos);
	}

	/**
	 * 获取所有的业务，封装成业务vos
	 * 
	 * @return 业务 list
	 */
	public List<BusinessVo> getBusinesss() {
		List<Business> businesss = businessRepository.findAll();
		return BusinessConvertor.dosToVos(businesss);
	}

	/**
	 * 获取所有的业务，封装成业务DataTableVos
	 * 
	 * @return 业务DataTableVo list
	 */
	public List<DataTableRowVo> getBusinessDtrvs() {
		List<Business> businesss = businessRepository.findAll();
		return BusinessConvertor.dosToDtrvs(businesss);
	}

	/**
	 * 保存业务
	 * 
	 * @param businessVo
	 *            业务vo
	 * 
	 * @return 业务DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo save(BusinessVo businessVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		Business business = new Business();
		BusinessConvertor.voToDo(businessVo, business);
		// 如果pojoId存在，就设置Pojo
		String pojoId = businessVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			business.setPojo(pojo);
		}

		Business result = businessRepository.save(business);
		if (result != null) {
			dtrv.setMessage("保存成功");
			dtrv.setResult(true);
			BusinessConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 更新业务
	 * 
	 * @param businessVo
	 *            业务vo
	 * 
	 * @return 业务DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo update(BusinessVo businessVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("更新失败");
		if (StringUtils.isBlank(businessVo.getId())) {
			return dtrv;
		}
		Business check = businessRepository.findOne(businessVo.getId());
		if (check == null) {
			return dtrv;
		}
		BusinessConvertor.voToDo(businessVo, check);
		// 如果pojoId存在，就设置Pojo
		String pojoId = businessVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			check.setPojo(pojo);
		}

		dtrv.setMessage("更新成功");
		dtrv.setResult(true);
		BusinessConvertor.doToDtrv(dtrv, check);
		return dtrv;
	}

	/**
	 * 删除业务
	 * 
	 * @param id
	 *            业务id
	 * @return ResultVo 结果vo
	 */
	@Transactional
	public ResultVo delete(String id) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(id)) {
			return rv;
		}
		Business business = businessRepository.findOne(id);
		if (business == null) {
			rv.setMessage("删除失败");
			return rv;
		}
		businessRepository.delete(business);
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            用,号隔开
	 */
	@Transactional
	public ResultVo deleteBatch(String ids) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(ids)) {
			return rv;
		}
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			businessRepository.delete(id);
		}
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

	/**
	 * 保存或者更新实体
	 * 
	 * @param businessVo
	 *            业务层vo
	 * 
	 * @return ResultVo
	 */
	@Transactional
	public ResultVo saveOrUpdate(BusinessVo businessVo) {
		ResultVo rv = new ResultVo();
		rv.setMessage("保存失败");
		Business business = null;
		// 如果id不存在，就保存
		if (StringUtils.isBlank(businessVo.getId())) {
			business = new Business();
		} else {
			// 如果id存在，就更新
			business = businessRepository.findOne(businessVo.getId());
			if (business == null) {
				return rv;
			}
		}
		BusinessConvertor.voToDo(businessVo, business);
		// 如果实体id存在，就设置实体
		String pojoId = businessVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			business.setPojo(pojo);
		}

		if (StringUtils.isBlank(businessVo.getId())) {
			Business result = businessRepository.save(business);
			if (result == null) {
				return rv;
			}
		}
		rv.setMessage("保存成功");
		rv.setResult(true);
		return rv;
	}

}
