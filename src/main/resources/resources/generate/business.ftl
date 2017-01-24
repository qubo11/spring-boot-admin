<#if dao.packageName??>
package ${dao.packageName};
</#if>

import java.util.*;
import org.springframework.data.jpa.repository.*;
import com.expect.admin.data.support.repository.CustomRepository;

/**
 * ${dao.comment}
 */
public interface ${dao.name} extends ${dao.inherit}{

<#list dao.daoMethods as daoMethod>
	<#if daoMethod.query?? && daoMethod.query=='是'>
	@Query("<#if daoMethod.querySentence??>${daoMethod.querySentence}</#if>")
	</#if>
	<#if daoMethod.modifying?? && daoMethod.modifying=='是'>
	@Query
	</#if>
	public ${daoMethod.returnType} ${daoMethod.name}; //<#if daoMethod.comment??>${daoMethod.comment}</#if>
	
</#list>

}