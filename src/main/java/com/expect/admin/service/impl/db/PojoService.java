package com.expect.admin.service.impl.db;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.db.PojoRepository;
import com.expect.admin.data.dao.db.ProjectRepository;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.data.dataobject.db.Project;
import com.expect.admin.service.convertor.db.PojoConvertor;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.PojoVo;

@Service
public class PojoService {

	@Autowired
	private PojoRepository pojoRepository;
	@Autowired
	private ProjectRepository projectRepository;

	/**
	 * 根据id获取实体
	 * 
	 * @param id
	 *            实体id
	 * @return PojoVo 实体vo
	 */
	public PojoVo getPojoById(String id) {
		Pojo pojo = null;
		if (!StringUtils.isBlank(id)) {
			pojo = pojoRepository.findOne(id);
		}
		return PojoConvertor.doToVo(pojo);
	}

	/**
	 * 根据项目id获取实体
	 * 
	 * @param projectId
	 *            项目id
	 * @return 实体vo 列表
	 */
	public List<PojoVo> getPojoByProjectId(String projectId) {
		List<Pojo> pojos = pojoRepository.findByProjectId(projectId);
		return PojoConvertor.dosToVos(pojos);
	}

	/**
	 * 获取所有的实体，封装成实体vos
	 * 
	 * @return 实体 list
	 */
	public List<PojoVo> getPojos() {
		List<Pojo> pojos = pojoRepository.findAll();
		return PojoConvertor.dosToVos(pojos);
	}

	/**
	 * 获取所有的实体，封装成实体DataTableVos
	 * 
	 * @return 实体DataTableVo list
	 */
	public List<DataTableRowVo> getPojoDtrvs() {
		List<Pojo> pojos = pojoRepository.findAll();
		return PojoConvertor.dosToDtrvs(pojos);
	}

	/**
	 * 保存实体
	 * 
	 * @param pojoVo
	 *            实体vo
	 * 
	 * @return 实体DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo save(PojoVo pojoVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(pojoVo.getName())) {
			dtrv.setMessage("实体名称不能为空");
			return dtrv;
		}
		Pojo pojo = new Pojo();
		PojoConvertor.voToDo(pojoVo, pojo);
		// 如果projectId存在，就设置Project
		String projectId = pojoVo.getProjectId();
		if (!StringUtils.isBlank(projectId)) {
			Project project = projectRepository.findOne(projectId);
			pojo.setProject(project);
		}

		Pojo result = pojoRepository.save(pojo);
		if (result != null) {
			dtrv.setMessage("保存成功");
			dtrv.setResult(true);
			PojoConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 更新实体
	 * 
	 * @param pojoVo
	 *            实体vo
	 * 
	 * @return 实体DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo update(PojoVo pojoVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(pojoVo.getId())) {
			return dtrv;
		}
		if (StringUtils.isBlank(pojoVo.getName())) {
			dtrv.setMessage("实体名称不能为空");
			return dtrv;
		}
		Pojo check = pojoRepository.findOne(pojoVo.getId());
		if (check == null) {
			return dtrv;
		}
		PojoConvertor.voToDo(pojoVo, check);
		// 如果projectId存在，就设置Project
		String projectId = pojoVo.getProjectId();
		if (!StringUtils.isBlank(projectId)) {
			Project project = projectRepository.findOne(projectId);
			check.setProject(project);
		}

		dtrv.setMessage("更新成功");
		dtrv.setResult(true);
		PojoConvertor.doToDtrv(dtrv, check);
		return dtrv;
	}

	/**
	 * 删除实体
	 * 
	 * @param id
	 *            实体id
	 * @return ResultVo 结果vo
	 */
	@Transactional
	public ResultVo delete(String id) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(id)) {
			return rv;
		}
		Pojo pojo = pojoRepository.findOne(id);
		if (pojo == null) {
			rv.setMessage("删除失败");
			return rv;
		}
		pojoRepository.delete(pojo);
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
			pojoRepository.delete(id);
		}
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

	/**
	 * 保存或者更新实体
	 * 
	 * @param projectVo
	 *            实体vo
	 * 
	 * @return ResultVo
	 */
	@Transactional
	public ResultVo saveOrUpdate(PojoVo pojoVo) {
		ResultVo rv = new ResultVo();
		rv.setMessage("保存失败");
		if (StringUtils.isBlank(pojoVo.getName())) {
			rv.setMessage("实体名称不能为空");
			return rv;
		}
		Pojo pojo = null;
		// 如果id不存在，就保存
		if (StringUtils.isBlank(pojoVo.getId())) {
			pojo = new Pojo();
		} else {
			// 如果id存在，就更新
			pojo = pojoRepository.findOne(pojoVo.getId());
			if (pojo == null) {
				return rv;
			}
		}
		PojoConvertor.voToDo(pojoVo, pojo);
		// 如果projectId存在，就设置Project
		String projectId = pojoVo.getProjectId();
		if (!StringUtils.isBlank(projectId)) {
			Project project = projectRepository.findOne(projectId);
			pojo.setProject(project);
		}

		if (StringUtils.isBlank(pojoVo.getId())) {
			Pojo result = pojoRepository.save(pojo);
			if (result == null) {
				return rv;
			}
			rv.setObj(result.getId());
		} else {
			rv.setObj(pojo.getId());
		}
		rv.setMessage("保存成功");
		rv.setResult(true);
		return rv;
	}

	/**
	 * 生成代码
	 * 
	 * @param ids
	 *            实体id(用,分隔)
	 * @return 代码压缩包的byte[]
	 */
	// public byte[] generate(String ids) {
	//
	// }

}
