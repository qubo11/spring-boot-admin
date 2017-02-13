package com.expect.admin.service.convertor.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.db.Dao;
import com.expect.admin.data.dataobject.db.DaoMethod;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.DaoMethodVo;
import com.expect.admin.service.vo.db.DaoVo;

/**
 * dao 转换器
 */
public class DaoConvertor {

	/**
	 * do to vo
	 * 
	 * @param dao
	 *            dao do
	 * @return DaoVo dao vo
	 */
	public static DaoVo doToVo(Dao dao, List<String> inherits, List<Pojo> pojos) {
		DaoVo daoVo = new DaoVo();
		if (dao != null) {
			BeanUtils.copyProperties(dao, daoVo);
			// 设置实体
			Pojo pojo = dao.getPojo();
			if (pojo != null) {
				daoVo.setPojoId(pojo.getId());
				daoVo.setPojoName(pojo.getName());
			}
			// 设置dao方法
			List<DaoMethod> daoMethods = dao.getDaoMethods();
			if (!CollectionUtils.isEmpty(daoMethods)) {
				List<DaoMethodVo> daoMethodVos = DaoMethodConvertor.dosToVos(daoMethods);
				daoVo.setDaoMethods(daoMethodVos);
			}
		}
		if (!CollectionUtils.isEmpty(inherits)) {
			SelectOptionVo inheritSov = new SelectOptionVo();
			for (String inherit : inherits) {
				if (dao != null && dao.getInherit() != null && dao.getInherit().equals(inherit)) {
					inheritSov.addOption(inherit, inherit, true);
				} else {
					inheritSov.addOption(inherit, inherit);
				}
			}
			daoVo.setInheritSov(inheritSov);
		}
		// 设置实体
		if (!CollectionUtils.isEmpty(pojos)) {
			SelectOptionVo pojoSov = new SelectOptionVo();
			for (Pojo pojo : pojos) {
				if (dao != null && dao.getPojo() != null && dao.getPojo().getId() != null) {
					if (dao.getPojo().getId().equals(pojo.getId())) {
						pojoSov.addOption(pojo.getId(), pojo.getName(), true);
					} else {
						pojoSov.addOption(pojo.getId(), pojo.getName());
					}
				} else {
					pojoSov.addOption(pojo.getId(), pojo.getName());
				}
			}
			daoVo.setPojoSov(pojoSov);
		}
		return daoVo;
	}

	/**
	 * dos to vos
	 * 
	 * @param daos
	 *            dao dos
	 * @return DaoVos dao vo list
	 */
	public static List<DaoVo> dosToVos(List<Dao> daos) {
		if (!CollectionUtils.isEmpty(daos)) {
			List<DaoVo> daoVos = new ArrayList<>(daos.size());
			for (Dao dao : daos) {
				DaoVo daoVo = doToVo(dao, null, null);
				daoVos.add(daoVo);
			}
			return daoVos;
		}
		return null;
	}

	/**
	 * vo to do
	 * 
	 * @param dao
	 *            dao vo
	 * @param dao
	 *            dao do
	 */
	public static void voToDo(DaoVo daoVo, Dao dao) {
		BeanUtils.copyProperties(daoVo, dao);
	}

	/**
	 * do to dtrv
	 * 
	 * @param dtrv
	 *            dao DataTableRowVo
	 * @param dao
	 *            dao do
	 */
	public static void doToDtrv(DataTableRowVo dtrv, Dao dao) {
		dtrv.setObj(dao.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(dao.getPackageName());
		dtrv.addData(dao.getName());
		dtrv.addData(dao.getInherit());
		dtrv.addData(dao.getComment());
		if (dao.getPojo() != null) {
			dtrv.addData(dao.getPojo().getName());
		} else {
			dtrv.addData("");
		}
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getYellowButton("方法",
				"data-url='admin/db/daoMethod/managePage?daoId=" + dao.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getBaseButton(dao.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * dos to dtrvs
	 * 
	 * @param daos
	 *            dao dos
	 * @return dao DataTableRowVos
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<Dao> daos) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(daos)) {
			for (Dao dao : daos) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, dao);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

}
