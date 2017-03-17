package com.expect.admin.service.impl.custom;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.custom.IconRepository;
import com.expect.admin.data.dataobject.custom.Icon;
import com.expect.admin.service.convertor.custom.IconConvertor;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.IconVo;
import com.expect.custom.data.dao.AttachmentRepository;
import com.expect.custom.data.dataobject.Attachment;
import com.expect.custom.service.vo.component.ResultVo;

/**
 * 图标Service
 */
@Service
public class IconService {

	@Autowired
	private IconRepository iconRepository;
	@Autowired
	private AttachmentRepository attachmentRepository;

	/**
	 * 获取所有的图标
	 */
	public List<IconVo> getIcons() {
		List<Icon> icons = iconRepository.findAll();
		List<IconVo> iconVos = IconConvertor.dosToVos(icons);
		return iconVos;
	}

	/**
	 * 获取所有的图标信息，封装成dtrsv
	 */
	public List<DataTableRowVo> getIconDtrsv() {
		List<Icon> icons = iconRepository.findAll();
		List<DataTableRowVo> dtrvs = IconConvertor.dosToDtrvs(icons);
		return dtrvs;
	}

	/**
	 * 根据id获取图标
	 */
	public IconVo getIconById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		Icon icon = iconRepository.findOne(id);
		return IconConvertor.doToVo(icon);
	}

	/**
	 * 保存图标
	 */
	@Transactional
	public DataTableRowVo save(IconVo iconVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("增加失败");
		if (iconVo.getType() == null) {
			dtrv.setMessage("图标类型不能为空");
			return dtrv;
		}
		Icon icon = new Icon();
		IconConvertor.voToDo(iconVo, icon);
		// 判断是否是上传图标
		Integer type = icon.getType();
		if (type != null && type == 2) {
			String attachmentId = iconVo.getAttachmentId();
			if (!StringUtils.isBlank(attachmentId)) {
				Attachment attachment = attachmentRepository.findOne(attachmentId);
				icon.setAttachment(attachment);
			}
		}

		Icon result = iconRepository.save(icon);
		if (result != null) {
			dtrv.setResult(true);
			dtrv.setMessage("增加成功");
			IconConvertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 修改图标
	 */
	@Transactional
	public DataTableRowVo update(IconVo iconVo) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("修改失败");
		if (StringUtils.isBlank(iconVo.getId())) {
			return dtrv;
		}
		if (iconVo.getType() == null) {
			dtrv.setMessage("图标类型不能为空");
			return dtrv;
		}
		Icon checkIcon = iconRepository.findOne(iconVo.getId());
		if (checkIcon == null) {
			return dtrv;
		}
		IconConvertor.voToDo(iconVo, checkIcon);
		// 判断是否是上传图标
		Integer type = checkIcon.getType();
		if (type != null && type == 2) {
			String attachmentId = iconVo.getAttachmentId();
			if (!StringUtils.isBlank(attachmentId)) {
				Attachment attachment = attachmentRepository.findOne(attachmentId);
				checkIcon.setAttachment(attachment);
			}
		}

		dtrv.setResult(true);
		dtrv.setMessage("修改成功");
		IconConvertor.doToDtrv(dtrv, checkIcon);
		return dtrv;
	}

	/**
	 * 删除图标
	 */
	public ResultVo delete(String id) {
		ResultVo resultVo = new ResultVo();
		resultVo.setMessage("删除失败");
		Icon icon = iconRepository.findOne(id);
		if (icon == null) {
			return resultVo;
		}

		iconRepository.delete(id);
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
			iconRepository.delete(id);
		}
		resultVo.setResult(true);
		resultVo.setMessage("删除成功");
		return resultVo;
	}

}
