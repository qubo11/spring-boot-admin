package com.expect.admin.service.convertor.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.db.Controller;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ControllerVo;

/**
 * 控制层 转换器
 */
public class ControllerConvertor {

	/**
	 * do to vo
	 * 
	 * @param controller
	 *            控制层 do
	 * @return ControllerVo 控制层 vo
	 */
	public static ControllerVo doToVo(Controller controller, List<Pojo> pojos) {
		ControllerVo controllerVo = new ControllerVo();
		if (controller != null) {
			BeanUtils.copyProperties(controller, controllerVo);
			// 设置实体
			Pojo pojo = controller.getPojo();
			if (pojo != null) {
				controllerVo.setPojoId(pojo.getId());
				controllerVo.setPojoName(pojo.getName());
			}
		}
		// 设置实体
		if (!CollectionUtils.isEmpty(pojos)) {
			SelectOptionVo pojoSov = new SelectOptionVo();
			for (Pojo pojo : pojos) {
				if (controller != null && controller.getPojo() != null && controller.getPojo().getId() != null) {
					if (controller.getPojo().getId().equals(pojo.getId())) {
						pojoSov.addOption(pojo.getId(), pojo.getName(), true);
					} else {
						pojoSov.addOption(pojo.getId(), pojo.getName());
					}
				} else {
					pojoSov.addOption(pojo.getId(), pojo.getName());
				}
			}
			controllerVo.setPojoSov(pojoSov);
		}
		return controllerVo;
	}

	/**
	 * dos to vos
	 * 
	 * @param controllers
	 *            控制层 dos
	 * @return ControllerVos 控制层 vo list
	 */
	public static List<ControllerVo> dosToVos(List<Controller> controllers) {
		if (!CollectionUtils.isEmpty(controllers)) {
			List<ControllerVo> controllerVos = new ArrayList<>(controllers.size());
			for (Controller controller : controllers) {
				ControllerVo controllerVo = doToVo(controller, null);
				controllerVos.add(controllerVo);
			}
			return controllerVos;
		}
		return null;
	}

	/**
	 * vo to do
	 * 
	 * @param controller
	 *            控制层 vo
	 * @param controller
	 *            控制层 do
	 */
	public static void voToDo(ControllerVo controllerVo, Controller controller) {
		BeanUtils.copyProperties(controllerVo, controller);
	}

	/**
	 * do to dtrv
	 * 
	 * @param dtrv
	 *            控制层 DataTableRowVo
	 * @param controller
	 *            控制层 do
	 */
	public static void doToDtrv(DataTableRowVo dtrv, Controller controller) {
		dtrv.setObj(controller.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(controller.getPackageName());
		dtrv.addData(controller.getName());
		dtrv.addData(controller.getRequestMapping());
		dtrv.addData(controller.getComment());
		if (controller.getPojo() != null) {
			dtrv.addData(controller.getPojo().getName());
		} else {
			dtrv.addData("");
		}
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getBaseButton(controller.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * dos to dtrvs
	 * 
	 * @param controllers
	 *            控制层 dos
	 * @return 控制层 DataTableRowVos
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<Controller> controllers) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(controllers)) {
			for (Controller controller : controllers) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, controller);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

}
