package com.expect.admin.service.impl.db;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.db.ProjectRepository;
import com.expect.admin.data.dataobject.db.Project;
import com.expect.admin.service.convertor.db.ProjectConvertor;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ProjectVo;

/**
 * 项目Service
 */
@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	/**
	 * 根据id获取项目
	 * 
	 * @param id
	 *            项目id
	 * @return ProjectVo 项目vo
	 */
	public ProjectVo getProjectById(String id) {
		Project project = null;
		if (!StringUtils.isBlank(id)) {
			project = projectRepository.findOne(id);
		}
		return ProjectConvertor.doToVo(project);
	}

	/**
	 * 获取所有的项目，封装成项目vos
	 * 
	 * @return 项目 list
	 */
	public List<ProjectVo> getProjects() {
		List<Project> projects = projectRepository.findAll();
		return ProjectConvertor.dosToVos(projects);
	}

	/**
	 * 获取所有的项目，封装成项目DataTableVos
	 * 
	 * @return 项目DataTableVo list
	 */
	public List<DataTableRowVo> getProjectDtrvs() {
		List<Project> projects = projectRepository.findAll();
		return ProjectConvertor.dosToDtrvs(projects);
	}

	/**
	 * 保存项目
	 * 
	 * @param projectVo
	 *            项目vo
	 * 
	 * @return 项目DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo save(ProjectVo projectVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(projectVo.getName())) {
			dtrv.setMessage("项目名称不能为空");
			return dtrv;
		}
		Project project = new Project();
		ProjectConvertor.voToDo(projectVo, project);

		Project result = projectRepository.save(project);
		if (result != null) {
			dtrv.setMessage("保存成功");
			dtrv.setResult(true);
			ProjectConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 更新项目
	 * 
	 * @param projectVo
	 *            项目vo
	 * 
	 * @return 项目DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo update(ProjectVo projectVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(projectVo.getId())) {
			return dtrv;
		}
		if (StringUtils.isBlank(projectVo.getName())) {
			dtrv.setMessage("项目名称不能为空");
			return dtrv;
		}
		Project check = projectRepository.findOne(projectVo.getId());
		if (check == null) {
			return dtrv;
		}
		ProjectConvertor.voToDo(projectVo, check);

		dtrv.setMessage("更新成功");
		dtrv.setResult(true);
		ProjectConvertor.doToDtrv(dtrv, check);
		return dtrv;
	}

	/**
	 * 删除项目
	 * 
	 * @param id
	 *            项目id
	 * @return ResultVo 结果vo
	 */
	@Transactional
	public ResultVo delete(String id) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(id)) {
			return rv;
		}
		Project project = projectRepository.findOne(id);
		if (project == null) {
			rv.setMessage("删除失败");
			return rv;
		}
		projectRepository.delete(project);
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
			projectRepository.delete(id);
		}
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

}
