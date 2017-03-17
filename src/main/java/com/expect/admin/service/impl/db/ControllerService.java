package com.expect.admin.service.impl.db;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.db.ControllerRepository;
import com.expect.admin.data.dao.db.PojoRepository;
import com.expect.admin.data.dataobject.db.Controller;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.service.convertor.db.ControllerConvertor;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ControllerVo;
import com.expect.custom.service.vo.component.ResultVo;

/**
 * 控制层Service
 */
@Service
public class ControllerService {

	@Autowired
	private ControllerRepository controllerRepository;
	@Autowired
	private PojoRepository pojoRepository;

	/**
	 * 根据id获取控制层
	 * 
	 * @param id
	 *            控制层 id
	 * @return ControllerVo 控制层vo
	 */
	public ControllerVo getControllerById(String id) {
		Controller controller = null;
		if (!StringUtils.isBlank(id)) {
			controller = controllerRepository.findOne(id);
		}
		List<Pojo> pojos = pojoRepository.findAll();
		return ControllerConvertor.doToVo(controller, pojos);
	}

	/**
	 * 根据实体id获取控制层
	 * 
	 * @param id
	 *            实体 id
	 * @return ControllerVo 控制层vo
	 */
	public ControllerVo getControllerByPojoId(String pojoId) {
		Controller controller = controllerRepository.findByPojoId(pojoId);
		List<Pojo> pojos = pojoRepository.findAll();
		return ControllerConvertor.doToVo(controller, pojos);
	}

	/**
	 * 获取所有的控制层，封装成控制层vos
	 * 
	 * @return 控制层 list
	 */
	public List<ControllerVo> getControllers() {
		List<Controller> controllers = controllerRepository.findAll();
		return ControllerConvertor.dosToVos(controllers);
	}

	/**
	 * 获取所有的控制层，封装成控制层DataTableVos
	 * 
	 * @return 控制层DataTableVo list
	 */
	public List<DataTableRowVo> getControllerDtrvs() {
		List<Controller> controllers = controllerRepository.findAll();
		return ControllerConvertor.dosToDtrvs(controllers);
	}

	/**
	 * 保存控制层
	 * 
	 * @param controllerVo
	 *            控制层vo
	 * 
	 * @return 控制层DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo save(ControllerVo controllerVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		Controller controller = new Controller();
		ControllerConvertor.voToDo(controllerVo, controller);
		// 如果pojoId存在，就设置Pojo
		String pojoId = controllerVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			controller.setPojo(pojo);
		}

		Controller result = controllerRepository.save(controller);
		if (result != null) {
			dtrv.setMessage("保存成功");
			dtrv.setResult(true);
			ControllerConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 更新控制层
	 * 
	 * @param controllerVo
	 *            控制层vo
	 * 
	 * @return 控制层DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo update(ControllerVo controllerVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		if (StringUtils.isBlank(controllerVo.getId())) {
			return dtrv;
		}
		Controller check = controllerRepository.findOne(controllerVo.getId());
		if (check == null) {
			return dtrv;
		}
		ControllerConvertor.voToDo(controllerVo, check);
		// 如果pojoId存在，就设置Pojo
		String pojoId = controllerVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			check.setPojo(pojo);
		}

		dtrv.setMessage("更新成功");
		dtrv.setResult(true);
		ControllerConvertor.doToDtrv(dtrv, check);
		return dtrv;
	}

	/**
	 * 删除控制层
	 * 
	 * @param id
	 *            控制层id
	 * @return ResultVo 结果vo
	 */
	@Transactional
	public ResultVo delete(String id) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(id)) {
			return rv;
		}
		Controller controller = controllerRepository.findOne(id);
		if (controller == null) {
			rv.setMessage("删除失败");
			return rv;
		}
		controllerRepository.delete(controller);
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
			controllerRepository.delete(id);
		}
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

	/**
	 * 保存或者更新实体
	 * 
	 * @param controllerVo
	 *            控制层vo
	 * 
	 * @return ResultVo
	 */
	@Transactional
	public ResultVo saveOrUpdate(ControllerVo controllerVo) {
		ResultVo rv = new ResultVo();
		rv.setMessage("保存失败");

		Controller controller = null;
		// 如果id不存在，就保存
		if (StringUtils.isBlank(controllerVo.getId())) {
			controller = new Controller();
		} else {
			// 如果id存在，就更新
			controller = controllerRepository.findOne(controllerVo.getId());
			if (controller == null) {
				return rv;
			}
		}
		ControllerConvertor.voToDo(controllerVo, controller);
		// 如果实体id存在，就设置实体
		String pojoId = controllerVo.getPojoId();
		if (!StringUtils.isBlank(pojoId)) {
			Pojo pojo = pojoRepository.findOne(pojoId);
			controller.setPojo(pojo);
		}

		if (StringUtils.isBlank(controllerVo.getId())) {
			Controller result = controllerRepository.save(controller);
			if (result == null) {
				return rv;
			}
		}
		rv.setMessage("保存成功");
		rv.setResult(true);
		return rv;
	}

}
