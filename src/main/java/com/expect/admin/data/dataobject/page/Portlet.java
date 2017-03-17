//package com.expect.admin.data.dataobject.page;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import com.expect.admin.data.dataobject.Icon;
//
///**
// * 面板DO
// */
//@Entity
//@Table(name = "c_html_portlet")
//public class Portlet {
//
//	private String id;
//	private String title;// 标题
//	private String subTitle;// 子标题
//	private Integer style;// 样式,1:box;2:light;3.solid
//	private Integer type;// 类型,1:普通;2:tab
//	private String templateName;// 模版名称
//	private Icon icon;// 图标
//	private List<Action> actions;// action按钮
//	private Page page;// 页面
//
//	@Id
//	@GeneratedValue(generator = "uuid")
//	@GenericGenerator(name = "uuid", strategy = "uuid")
//	@Column(name = "id", nullable = false, unique = true, length = 32)
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	@Column(name = "title", length = 255)
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	@Column(name = "subTitle", length = 211)
//	public String getSubTitle() {
//		return subTitle;
//	}
//
//	public void setSubTitle(String subTitle) {
//		this.subTitle = subTitle;
//	}
//
//	@Column(name = "style", precision = 1)
//	public Integer getStyle() {
//		return style;
//	}
//
//	public void setStyle(Integer style) {
//		this.style = style;
//	}
//
//	@Column(name = "type", precision = 1)
//	public Integer getType() {
//		return type;
//	}
//
//	public void setType(Integer type) {
//		this.type = type;
//	}
//
//	@Column(name = "templateName", length = 255)
//	public String getTemplateName() {
//		return templateName;
//	}
//
//	public void setTemplateName(String templateName) {
//		this.templateName = templateName;
//	}
//
//	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//	@JoinTable(name = "c_portlet_action", joinColumns = @JoinColumn(name = "portlet_id"), inverseJoinColumns = @JoinColumn(name = "action_id"))
//	public List<Action> getActions() {
//		return actions;
//	}
//
//	public void setActions(List<Action> actions) {
//		this.actions = actions;
//	}
//
//	@ManyToOne
//	@JoinColumn(name = "icon_id")
//	public Icon getIcon() {
//		return icon;
//	}
//
//	public void setIcon(Icon icon) {
//		this.icon = icon;
//	}
//
//	@ManyToOne
//	@JoinColumn(name = "page_id")
//	public Page getPage() {
//		return page;
//	}
//
//	public void setPage(Page page) {
//		this.page = page;
//	}
//
//}
