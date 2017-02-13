package com.expect.admin.service.convertor.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.db.Project;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ProjectVo;

/**
 * 项目转换器
 */
public class ProjectConvertor {

	/**
	 * do to vo
	 * 
	 * @param project
	 *            项目do
	 * @return ProjectVo 项目vo
	 */
	public static ProjectVo doToVo(Project project) {
		ProjectVo projectVo = new ProjectVo();
		if (project != null) {
			BeanUtils.copyProperties(project, projectVo);
		}
		return projectVo;
	}

	/**
	 * dos to vos
	 * 
	 * @param projects
	 *            项目dos
	 * @return ProjectVos 项目vo list
	 */
	public static List<ProjectVo> dosToVos(List<Project> projects) {
		if (!CollectionUtils.isEmpty(projects)) {
			List<ProjectVo> projectVos = new ArrayList<>(projects.size());
			for (Project project : projects) {
				ProjectVo projectVo = doToVo(project);
				projectVos.add(projectVo);
			}
			return projectVos;
		}
		return null;
	}

	/**
	 * vo to do
	 * 
	 * @param project
	 *            项目vo
	 * @param project
	 *            项目do
	 */
	public static void voToDo(ProjectVo projectVo, Project project) {
		BeanUtils.copyProperties(projectVo, project);
	}

	/**
	 * do to dtrv
	 * 
	 * @param dtrv
	 *            项目DataTableRowVo
	 * @param project
	 *            项目do
	 */
	public static void doToDtrv(DataTableRowVo dtrv, Project project) {
		dtrv.setObj(project.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(project.getPackageName());
		dtrv.addData(project.getName());
		dtrv.addData(project.getRemark());
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getPurpleButton("生成代码",
				"data-url='admin/db/project/generate?projectId=" + project.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getYellowButton("配置向导",
				"data-url='admin/db/guide/managePage?projectId=" + project.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getYellowButton("方法",
				"data-url='admin/db/daoMethod/managePage?daoId=" + project.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getBaseButton(project.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * dos to dtrvs
	 * 
	 * @param projects
	 *            项目dos
	 * @return 项目DataTableRowVos
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<Project> projects) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(projects)) {
			for (Project project : projects) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, project);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

}
