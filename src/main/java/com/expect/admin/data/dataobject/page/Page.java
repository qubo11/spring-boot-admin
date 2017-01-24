package com.expect.admin.data.dataobject.page;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.expect.admin.data.dataobject.Function;

/**
 * 子页面DO
 */
@Entity
@Table(name = "c_html_page")
public class Page {

	private String id;
	private String title;// 页面标题
	private String subTitle;// 子标题
	private String templateName;// 模版名称
	private Function function;// 页面对应的功能
	private List<Portlet> portlets;// 面板

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", nullable = false, unique = true, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "title", length = 255)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "subTitle", length = 511)
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	@Column(name = "templateName", length = 255)
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@OneToOne
	@JoinColumn(name = "function_id")
	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	@OneToMany(mappedBy = "page")
	public List<Portlet> getPortlets() {
		return portlets;
	}

	public void setPortlets(List<Portlet> portlets) {
		this.portlets = portlets;
	}

}
