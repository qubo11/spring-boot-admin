<#if valueObject.packageName??>
package ${valueObject.packageName};
</#if>

import java.util.*;

/**
 * ${valueObject.comment}
 */
public interface ${valueObject.name}{

<#list valueObject.valueObjectPropertyVos as property>
	private ${property.type} ${property.name}; //${property.comment}
</#list>

<#list valueObject.valueObjectPropertyVos as property>
	public ${property.type} get${property.nameUpperCase}() {
		return ${property.name};
	}

	public void set${property.nameUpperCase}(${property.type} ${property.name}) {
		this.${property.name} = ${property.name};
	}
</#list>
}