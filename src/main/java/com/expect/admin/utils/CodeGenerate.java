package com.expect.admin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.expect.admin.service.vo.db.BusinessVo;
import com.expect.admin.service.vo.db.ControllerVo;
import com.expect.admin.service.vo.db.DaoMethodVo;
import com.expect.admin.service.vo.db.DaoVo;
import com.expect.admin.service.vo.db.PojoVo;
import com.expect.admin.service.vo.db.ValueObjectPropertyVo;
import com.expect.custom.utils.FtlExport;

public class CodeGenerate {

	public static void main(String[] args) {
		// List<PojoVo> pojos = new ArrayList<>();
		// PojoVo pojo = new PojoVo();
		// pojo.setName("Test");
		// pojo.setPackageName("com.expect.data.dataobject");
		// pojo.setTableName("c_test");
		// pojo.setComment("测试实体类");
		// pojos.add(pojo);
		//
		// List<PropertyVo> properties = new ArrayList<>();
		// PropertyVo property = new PropertyVo();
		// property.setName("id");
		// property.setType("String");
		// property.setComment("id");
		// property.setIsId("是");
		// property.setColumnName("id");
		// property.setLength(32);
		// property.setUnique("是");
		// property.setNullable("否");
		// properties.add(property);
		//
		// PropertyVo property1 = new PropertyVo();
		// property1.setName("time");
		// property1.setType("Date");
		// property1.setComment("time");
		// property1.setIsId("否");
		// property1.setColumnName("id");
		// property1.setUnique("否");
		// property1.setNullable("是");
		// properties.add(property1);
		//
		// pojo.setPropertyVos(properties);
		// pojo(pojos, "C:\\fjglpt");

		List<DaoVo> daos = new ArrayList<>();
		DaoVo dao = new DaoVo();
		dao.setName("TestRepository");
		dao.setComment("测试dao");
		dao.setInherit("JPARepository");

		List<DaoMethodVo> daoMethods = new ArrayList<>();
		DaoMethodVo daoMethod = new DaoMethodVo();
		daoMethod.setName("findByName");
		daoMethod.setReturnType("int");
		daoMethod.setComment("测试");
		daoMethods.add(daoMethod);
		
		DaoMethodVo daoMethod1 = new DaoMethodVo();
		daoMethod1.setName("findByName");
		daoMethod1.setReturnType("int");
		daoMethod1.setComment("测试");
		daoMethod1.setModifying("是");
		daoMethod1.setQuery("是");
		daoMethod1.setQuerySentence("select *");
		daoMethods.add(daoMethod1);
		dao.setDaoMethods(daoMethods);
		daos.add(dao);
		dao(daos, "C:\\fjglpt");
	}

	/**
	 * 生成pojo类
	 * 
	 * @param pojos
	 *            实体类 list
	 * @param filePath
	 *            输出的文件路径
	 */
	public static void pojo(List<PojoVo> pojos, String filePath) {
		if (!CollectionUtils.isEmpty(pojos)) {
			for (PojoVo pojo : pojos) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("pojo", pojo);
				FtlExport.create(dataMap, "resources/generate", "pojo.ftl", filePath, pojo.getName() + ".java");
			}
		}
	}

	/**
	 * 生成dao类
	 * 
	 * @param daos
	 *            dao类 list
	 * @param filePath
	 *            输出的文件路径
	 */
	public static void dao(List<DaoVo> daos, String filePath) {
		if (!CollectionUtils.isEmpty(daos)) {
			for (DaoVo dao : daos) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("dao", dao);
				FtlExport.create(dataMap, "resources/generate", "dao.ftl", filePath, dao.getName() + ".java");
			}
		}
	}

	/**
	 * 生成business类
	 * 
	 * @param businesses
	 *            业务类 list
	 * @param filePath
	 *            输出的文件路径
	 */
	public static void business(List<BusinessVo> businesses, String filePath) {
		if (!CollectionUtils.isEmpty(businesses)) {
			for (BusinessVo business : businesses) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("business", business);
				FtlExport.create(dataMap, "resources/generate", "business.ftl", filePath, business.getName() + ".java");
			}
		}
	}

	/**
	 * 生成valueObjectProperty类
	 * 
	 * @param valueObjectPropertys
	 *            值对象 list
	 * @param filePath
	 *            输出的文件路径
	 */
	public static void valueObject(List<ValueObjectPropertyVo> valueObjectPropertys, String filePath) {
		if (!CollectionUtils.isEmpty(valueObjectPropertys)) {
			for (ValueObjectPropertyVo valueObjectProperty : valueObjectPropertys) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("valueObjectProperty", valueObjectProperty);
				FtlExport.create(dataMap, "resources/generate", "valueObjectProperty.ftl", filePath,
						valueObjectProperty.getName() + ".java");
			}
		}
	}

	/**
	 * 生成controller类
	 * 
	 * @param controllers
	 *            控制层 list
	 * @param filePath
	 *            输出的文件路径
	 */
	public static void controller(List<ControllerVo> controllers, String filePath) {
		if (!CollectionUtils.isEmpty(controllers)) {
			for (ControllerVo controller : controllers) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("controller", controller);
				FtlExport.create(dataMap, "resources/generate", "controller.ftl", filePath,
						controller.getName() + ".java");
			}
		}
	}

	/**
	 * 生成页面类
	 * 
	 * @param valueObjectPropertys
	 *            值对象 list
	 * @param filePath
	 *            输出的文件路径
	 */
	public static void page(List<ValueObjectPropertyVo> valueObjectPropertys, String filePath) {
		if (!CollectionUtils.isEmpty(valueObjectPropertys)) {
			for (ValueObjectPropertyVo valueObjectProperty : valueObjectPropertys) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("valueObjectProperty", valueObjectProperty);
				FtlExport.create(dataMap, "resources/generate", "manage.ftl", filePath, "manage.html");
				FtlExport.create(dataMap, "resources/generate", "form.ftl", filePath, "form.html");
			}
		}
	}
}
