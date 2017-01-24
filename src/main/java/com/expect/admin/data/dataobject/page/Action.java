package com.expect.admin.data.dataobject.page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * portlet头部的action按钮
 */
@Entity
@Table(name = "c_html_portlet_action")
public class Action {

	private String id;
	private String button;// button样式

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

	@Column(name = "button", length = 511)
	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

}
