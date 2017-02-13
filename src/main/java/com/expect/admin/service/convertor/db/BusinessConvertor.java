package com.expect.admin.service.convertor.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.db.Business;
import com.expect.admin.data.dataobject.db.Pojo;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.BusinessVo;

/**
 * 业务 转换器
 */
public class BusinessConvertor {

	/**
	 * do to vo
	 * 
	 * @param business
	 *            业务 do
	 * @return BusinessVo 业务 vo
	 */
	public static BusinessVo doToVo(Business business, List<Pojo> pojos) {
		BusinessVo businessVo = new BusinessVo();
		if (business != null) {
			BeanUtils.copyProperties(business, businessVo);
			// 设置实体
			Pojo pojo = business.getPojo();
			if (pojo != null) {
				businessVo.setPojoId(pojo.getId());
				businessVo.setPojoName(pojo.getName());
			}
		}
		// 设置实体
		if (!CollectionUtils.isEmpty(pojos)) {
			SelectOptionVo pojoSov = new SelectOptionVo();
			for (Pojo pojo : pojos) {
				if (business != null && business.getPojo() != null && business.getPojo().getId() != null) {
					if (business.getPojo().getId().equals(pojo.getId())) {
						pojoSov.addOption(pojo.getId(), pojo.getName(), true);
					} else {
						pojoSov.addOption(pojo.getId(), pojo.getName());
					}
				} else {
					pojoSov.addOption(pojo.getId(), pojo.getName());
				}
			}
			businessVo.setPojoSov(pojoSov);
		}
		return businessVo;
	}

	/**
	 * dos to vos
	 * 
	 * @param businesss
	 *            业务 dos
	 * @return BusinessVos 业务 vo list
	 */
	public static List<BusinessVo> dosToVos(List<Business> businesss) {
		if (!CollectionUtils.isEmpty(businesss)) {
			List<BusinessVo> businessVos = new ArrayList<>(businesss.size());
			for (Business business : businesss) {
				BusinessVo businessVo = doToVo(business, null);
				businessVos.add(businessVo);
			}
			return businessVos;
		}
		return null;
	}

	/**
	 * vo to do
	 * 
	 * @param business
	 *            业务 vo
	 * @param business
	 *            业务 do
	 */
	public static void voToDo(BusinessVo businessVo, Business business) {
		BeanUtils.copyProperties(businessVo, business);
	}

	/**
	 * do to dtrv
	 * 
	 * @param dtrv
	 *            业务 DataTableRowVo
	 * @param business
	 *            业务 do
	 */
	public static void doToDtrv(DataTableRowVo dtrv, Business business) {
		dtrv.setObj(business.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(business.getPackageName());
		dtrv.addData(business.getName());
		dtrv.addData(business.getPaginate());
		dtrv.addData(business.getComment());
		if (business.getPojo() != null) {
			dtrv.addData(business.getPojo().getName());
		} else {
			dtrv.addData("");
		}
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getBaseButton(business.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * dos to dtrvs
	 * 
	 * @param businesss
	 *            业务 dos
	 * @return 业务 DataTableRowVos
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<Business> businesss) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(businesss)) {
			for (Business business : businesss) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, business);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

}
