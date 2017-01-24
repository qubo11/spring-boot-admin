<#if dao.packageName??>
package ${dao.packageName};
<#/if>

import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.*;

/**
 * ${pojo.comment}
 */
@Entity
@Table(name = "${pojo.tableName}")
public class ${pojo.name} {

<#list pojo.propertyVos as property>
	private ${property.type} ${property.name}; //${property.comment}
</#list>
<#list pojo.propertyVos as property>

	<#if property.isId == '是'>
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	</#if>
	<#if property.type=='Text' || property.type=='Date'>@Type(name="${property.type}")</#if>
	<#if !property.relation?? || property.relation=="">@Column(name = "${property.columnName}"<#if property.nullable=='否'>, nullable = false</#if><#if property.unique=='是'>, unique = true</#if><#if property.length??>, length=${property.length}</#if><#if property.precision??>, precision=${property.precision}</#if><#if property.scale??>, scale=${property.scale}</#if>)</#if>
	<#if property.relation?? && property.relation!="">
	<#if property.relation=='OneToOne'>
	@OneToOne(cascade=CascadeType.${property.cascade},fetch=FetchType.${property.fetch},<#if property.mappedBy?? && property.mappedBy!="">mappedBy="${property.mappedBy}"</#if>)	
	<#if property.JoinColumn?? && property.JoinColumn!="">@JoinColumn(name="${property.JoinColumn}")</#if>
	</#if>
	<#if property.relation=='OneToMany'>
	@OneToMany(cascade=CascadeType.${property.cascade},fetch=FetchType.${property.fetch},<#if property.mappedBy?? && property.mappedBy!="">mappedBy="${property.mappedBy}"</#if>)	
	</#if>
	<#if property.relation=='ManyToOne'>
	@ManyToOne(cascade=CascadeType.${property.cascade},fetch=FetchType.${property.fetch})	
	@JoinColumn(name="${property.JoinColumn}")
	</#if>
	<#if property.relation=='ManyToMany'>
	@ManyToMany(cascade=CascadeType.${property.cascade},fetch=FetchType.${property.fetch})	
	@JoinTable(name = "${property.joinTable}", joinColumns = @JoinColumn(name = "${property.JoinColumn}"), inverseJoinColumns = @JoinColumn(name = "${property.inverseJoinColumns}"))
	</#if>
	</#if>
	public ${property.type} get${property.nameUpperCase}() {
		return ${property.name};
	}

	public void set${property.nameUpperCase}(${property.type} ${property.name}) {
		this.${property.name} = ${property.name};
	}			
</#list>		

}