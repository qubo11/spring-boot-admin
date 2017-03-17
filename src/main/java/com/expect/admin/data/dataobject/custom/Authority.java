package com.expect.admin.data.dataobject.custom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 权限表，默认权限：base(查看),detail(详情),insert(增加),save(保存),delete(删除),update(修改),query(查询),export(导出),import(导入)
 */
@Entity
@Table(name = "c_authority")
public class Authority implements Comparable<Authority> {

	public final static String AUTHORITY_BASE = "base";
	public final static String AUTHORITY_QUERY = "query";
	public final static String AUTHORITY_DETAIL = "detail";
	public final static String AUTHORITY_INSERT = "insert";
	public final static String AUTHORITY_SAVE = "save";
	public final static String AUTHORITY_DELETE = "delete";
	public final static String AUTHORITY_BATCHDELETE = "batchDelete";
	public final static String AUTHORITY_UPDATE = "update";
	public final static String AUTHORITY_EXPORT = "export";
	public final static String AUTHORITY_IMPORT = "import";

	private String id;// 权限id
	private String code;// 权限代码
	private String name;// 权限名称
	private String icon;// 如果对应按钮，可以加上按钮的图标
	private Integer sequence;// 顺序
	private String description;// 权限描述

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

	@Column(name = "code", length = 63)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", length = 63)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "icon", length = 63)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "sequence", precision = 11)
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Column(name = "description", length = 511)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(Authority o) {
		if (this.sequence == null) {
			this.sequence = 0;
		}
		if (o.sequence == null) {
			o.sequence = 0;
		}
		if (this.sequence > o.getSequence()) {
			return 1;
		} else if (this.sequence < o.getSequence()) {
			return -1;
		}
		return 0;
	}

}
