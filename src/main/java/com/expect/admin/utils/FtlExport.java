package com.expect.admin.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FtlExport {

	/**
	 * 导出ftl
	 * 
	 * @param dataMap
	 *            数据集
	 * @param templatePath
	 *            模版路径，相对于类路径
	 * @param templateName
	 *            模版名称
	 * @param filePath
	 *            文件输出路径
	 * @param fileName
	 *            文件输出名称
	 */
	public static void create(Map<String, Object> dataMap, String templatePath, String templateName, String filePath,
			String fileName) {
		try {
			// 创建配置实例
			@SuppressWarnings("deprecation")
			Configuration configuration = new Configuration();
			// 设置编码
			configuration.setDefaultEncoding("UTF-8");
			// ftl模板文件统一放至
			configuration.setClassForTemplateLoading(FtlExport.class, "/" + templatePath);
			// 获取模板
			Template template = configuration.getTemplate(templateName);
			// 输出文件
			File outFile = new File(filePath + File.separator + fileName);
			// 如果输出目标文件夹不存在，则创建
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			// 将模板和数据模型合并生成文件
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
			// 生成文件
			template.process(dataMap, out);
			// 关闭流
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
