package com.expect.admin.service.convertor.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.data.dataobject.db.Project;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.PojoVo;

/**
 * 实体转换器
 */
public class PojoConvertor {

	/**
	 * do to vo
	 * 
	 * @param pojo
	 *            实体do
	 * @return PojoVo 实体vo
	 */
	public static PojoVo doToVo(Pojo pojo) {
		PojoVo pojoVo = new PojoVo();
		if (pojo != null) {
			BeanUtils.copyProperties(pojo, pojoVo);
			Project project = pojo.getProject();
			if (project != null) {
				pojoVo.setProjectId(project.getId());
				pojoVo.setProjectName(project.getName());
			}

		}
		return pojoVo;
	}

	/**
	 * dos to vos
	 * 
	 * @param pojos
	 *            实体dos
	 * @return PojoVos 实体vo list
	 */
	public static List<PojoVo> dosToVos(List<Pojo> pojos) {
		if (!CollectionUtils.isEmpty(pojos)) {
			List<PojoVo> pojoVos = new ArrayList<>(pojos.size());
			for (Pojo pojo : pojos) {
				PojoVo pojoVo = doToVo(pojo);
				pojoVos.add(pojoVo);
			}
			return pojoVos;
		}
		return null;
	}

	/**
	 * vo to do
	 * 
	 * @param pojo
	 *            实体vo
	 * @param pojo
	 *            实体do
	 */
	public static void voToDo(PojoVo pojoVo, Pojo pojo) {
		BeanUtils.copyProperties(pojoVo, pojo);
	}

	/**
	 * do to dtrv
	 * 
	 * @param dtrv
	 *            实体DataTableRowVo
	 * @param pojo
	 *            实体do
	 */
	public static void doToDtrv(DataTableRowVo dtrv, Pojo pojo) {
		dtrv.setObj(pojo.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(pojo.getPackageName());
		dtrv.addData(pojo.getName());
		dtrv.addData(pojo.getTableName());
		dtrv.addData(pojo.getComment());
		// 设置操作的button
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getGreenJungleButton("页面",
				"data-url='admin/db/page/managePage?pojoId=" + pojo.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getBlueSharpButton("控制层",
				"data-url='admin/db/controller/managePage?pojoId=" + pojo.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getPurpleSharpButton("VO",
				"data-url='admin/db/valueObject?pojoId=" + pojo.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getGreenSharpButton("业务层",
				"data-url='admin/db/business/managePage?pojoId=" + pojo.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getPurpleButton("Dao",
				"data-url='admin/db/dao/managePage?pojoId=" + pojo.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getYellowButton("属性",
				"data-url='admin/db/property/managePage?pojoId=" + pojo.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getDefaultButton(pojo.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * dos to dtrvs
	 * 
	 * @param pojos
	 *            实体dos
	 * @return 实体DataTableRowVos
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<Pojo> pojos) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(pojos)) {
			for (Pojo pojo : pojos) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, pojo);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

}
