package com.expect.admin.service.impl.db;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.db.DaoRepository;
import com.expect.admin.data.dao.db.PojoRepository;
import com.expect.admin.data.dataobject.db.Dao;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.service.convertor.db.DaoConvertor;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.DaoVo;

@Service
public class DaoService {

	@Autowired
	private DaoRepository daoRepository;
	@Autowired
	private PojoRepository pojoRepository;

	/**
	 * 根据id获取Dao
	 * 
	 * @param id
	 *            Daoid
	 * @return DaoVo Daovo
	 */
	public DaoVo getDaoById(String id) {
		Dao dao = null;
		if (!StringUtils.isBlank(id)) {
			dao = daoRepository.findOne(id);
		}
		List<Pojo> pojos = pojoRepository.findAll();
		return DaoConvertor.doToVo(dao, Dao.getInherits(), pojos);
	}

	/**
	 * 获取所有的Dao，封装成Daovos
	 * 
	 * @return Dao list
	 */
	public List<DaoVo> getDaos() {
		List<Dao> daos = daoRepository.findAll();
		return DaoConvertor.dosToVos(daos);
	}

	/**
	 * 获取所有的Dao，封装成DaoDataTableVos
	 * 
	 * @return DaoDataTableVo list
	 */
	public List<DataTableRowVo> getDaoDtrvs() {
		List<Dao> daos = daoRepository.findAll();
		return DaoConvertor.dosToDtrvs(daos);
	}

	/**
	 * 获取所有的Dao，封装成DataTableVos
	 * 
	 * @return 属性DataTableVo list
	 */
	public List<DataTableRowVo> getDaoDtrvs(String pojoId) {
		List<Dao> daos = null;
		if (StringUtils.isBlank(pojoId)) {
			daos = daoRepository.findAll();
		} else {
			daos = daoRepository.findByPojoId(pojoId);
		}
		return DaoConvertor.dosToDtrvs(daos);
	}

	/**
	 * 保存Dao
	 * 
	 * @param daoVo
	 *            Daovo
	 * 
	 * @return DaoDataTableRowVo
	 */
	@Transactional
	public DataTableRowVo save(DaoVo daoVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(daoVo.getName())) {
			dtrv.setMessage("Dao名称不能为空");
			return dtrv;
		}
		Dao dao = new Dao();
		DaoConvertor.voToDo(daoVo, dao);
		// 如果pojoId存在，就设置Pojo
		String pojoId = daoVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			dao.setPojo(pojo);
		}

		Dao result = daoRepository.save(dao);
		if (result != null) {
			dtrv.setMessage("保存成功");
			dtrv.setResult(true);
			DaoConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 更新Dao
	 * 
	 * @param daoVo
	 *            Daovo
	 * 
	 * @return DaoDataTableRowVo
	 */
	@Transactional
	public DataTableRowVo update(DaoVo daoVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(daoVo.getId())) {
			return dtrv;
		}
		if (StringUtils.isBlank(daoVo.getName())) {
			dtrv.setMessage("Dao名称不能为空");
			return dtrv;
		}
		Dao check = daoRepository.findOne(daoVo.getId());
		if (check == null) {
			return dtrv;
		}
		DaoConvertor.voToDo(daoVo, check);
		// 如果pojoId存在，就设置Pojo
		String pojoId = daoVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			check.setPojo(pojo);
		}

		dtrv.setMessage("更新成功");
		dtrv.setResult(true);
		DaoConvertor.doToDtrv(dtrv, check);
		return dtrv;
	}

	/**
	 * 删除Dao
	 * 
	 * @param id
	 *            Daoid
	 * @return ResultVo 结果vo
	 */
	@Transactional
	public ResultVo delete(String id) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(id)) {
			return rv;
		}
		Dao dao = daoRepository.findOne(id);
		if (dao == null) {
			rv.setMessage("删除失败");
			return rv;
		}
		daoRepository.delete(dao);
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
			daoRepository.delete(id);
		}
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

}
