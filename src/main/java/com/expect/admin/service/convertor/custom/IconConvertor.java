package com.expect.admin.service.convertor.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.custom.Icon;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.IconVo;
import com.expect.custom.data.dataobject.Attachment;
import com.expect.custom.utils.DocumentGenernate;

/**
 * 图标转换器
 */
public class IconConvertor {

	/**
	 * vo to do
	 */
	public static void voToDo(IconVo iconVo, Icon icon) {
		BeanUtils.copyProperties(iconVo, icon);
	}

	/**
	 * do to vo
	 */
	public static IconVo doToVo(Icon icon) {
		IconVo iconVo = new IconVo();
		if (icon == null) {
			return iconVo;
		}
		BeanUtils.copyProperties(icon, iconVo);
		Attachment attachment = icon.getAttachment();
		if (attachment != null) {
			iconVo.setAttachmentId(attachment.getId());
		}
		return iconVo;
	}

	/**
	 * dos to vos
	 */
	public static List<IconVo> dosToVos(Collection<Icon> icons) {
		List<IconVo> iconVos = new ArrayList<>();
		for (Icon icon : icons) {
			IconVo iconVo = doToVo(icon);
			iconVos.add(iconVo);
		}
		return iconVos;
	}

	/**
	 * dos to dtrvs
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<Icon> icons) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(icons)) {
			for (Icon icon : icons) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, icon);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

	/**
	 * do to dtrv
	 */
	public static void doToDtrv(DataTableRowVo dtrv, Icon icon) {
		dtrv.setObj(icon.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(icon.getName());
		Integer type = icon.getType();
		if (type != null) {
			if (type == 1) {
				dtrv.addData("字体图标");
			} else {
				dtrv.addData("上传图标");
			}
		} else {
			dtrv.addData("");
		}
		dtrv.addData(icon.getIconClass());
		Attachment attachment = icon.getAttachment();
		if (attachment != null) {
			DocumentGenernate dg = new DocumentGenernate();
			Map<String, String> attributes = new HashMap<>();
			attributes.put("href", "attachment/show?id=" + attachment.getId());
			attributes.put("class", "prettyPhoto");
			dg.createOpenElementTag("a", attributes);
			dg.createText("查看");
			dg.createCloseElementTag("a");
			dtrv.addData(dg.getHtml());
		} else {
			dtrv.addData("");
		}
		dtrv.addData(icon.getDescription());
		// 设置操作的button
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getDefaultButton(icon.getId()));
		dtrv.addData(buttonSb.toString());
	}

}
