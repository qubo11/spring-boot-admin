<#if business.packageName??>
package ${business.packageName}.convertor;
</#if>

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.utils.DateUtil;
import ${pojo.packageName};
import ${valueObject.packageName};


/**
 * 实体和值对象的转换器
 */
public class ${pojo.name}Convertor {

	/**
	 * do to vo
	 * 
	 * @param ${pojo.nameLowerCase}
	 *            实体
	 * @return ${valueObject.name} 值对象
	 */
	public static ${valueObject.name} doToVo(${pojo.name} ${pojo.nameLowerCase}) {
		${valueObject.name} ${valueObject.nameLowerCase} = new ${valueObject.name}();
		if (${valueObject.nameLowerCase} != null) {
			BeanUtils.copyProperties(${pojo.nameLowerCase}, ${valueObject.nameLowerCase});
		}
		return ${valueObject.nameLowerCase};
	}

	/**
	 * dos to vos
	 * 
	 * @param ${pojo.nameLowerCase}
	 *            实体 list
	 * @return ${valueObject.name} 值对象 list
	 */
	public static List<${valueObject.name}> dosToVos(List<${pojo.name}> ${pojo.nameLowerCase}s) {
		if (!CollectionUtils.isEmpty(${pojo.nameLowerCase})) {
			List<${valueObject.name}> ${valueObject.nameLowerCase}s = new ArrayList<>(${pojo.nameLowerCase}s.size());
			for (${pojo.name} ${pojo.nameLowerCase} : ${pojo.nameLowerCase}s) {
				${valueObject.name} ${valueObject.nameLowerCase} = doToVo(${pojo.nameLowerCase});
				${valueObject.nameLowerCase}s.add(${valueObject.nameLowerCase});
			}
			return ${valueObject.nameLowerCase}s;
		}
		return null;
	}

	/**
	 * vo to do
	 * 
	 * @param ${valueObject.nameLowerCase}
	 *            值对象
	 * @param ${pojo.nameLowerCase}
	 *            实体
	 */
	public static void voToDo(${valueObject.name} ${valueObject.nameLowerCase}, ${pojo.name} ${pojo.nameLowerCase}) {
		BeanUtils.copyProperties(${valueObject.nameLowerCase}, ${pojo.nameLowerCase});
	}

	/**
	 * do to dtrv
	 * 
	 * @param dtrv
	 *            实体 DataTableRowVo
	 * @param ${pojo.nameLowerCase}
	 *            实体
	 */
	public static void doToDtrv(DataTableRowVo dtrv, ${pojo.name} ${pojo.nameLowerCase}) {
		${valueObject.name} ${valueObject.nameLowerCase} = doToVo(${pojo.nameLowerCase});
		dtrv.setObj(${pojo.nameLowerCase}.getId());
		dtrv.setCheckbox(true);
		<#list valueObject.valueObjectPropertyVos as property>
		<#if property.isDataTableShow == '是'>
		<#if property.type == 'String'>
		dtrv.addData(${valueObject.nameLowerCase}.get${property.name});
		</#if>
		<#if property.type == 'Integer' || property.type == 'Double'>
		dtrv.addData(${valueObject.nameLowerCase}.get${property.name}+"");
		</#if>
		<#if property.type == 'Date'>
		Date ${property.name} = ${valueObject.nameLowerCase}.get${property.name};
		String ${property.name}Str=DateUtil.format(${property.name},property.dateFormat);
		dtrv.addData(${property.name}Str);
		</#if>
		</#if>
		</#list>
	}

	/**
	 * dos to dtrvs
	 * 
	 * @param ${pojo.nameLowerCase}s
	 *            实体 list
	 * @return DataTableRowVos
	 */
	public static List<DataTableRowVo> dosToDtrvs(List<${pojo.name}> ${pojo.nameLowerCase}s) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(${pojo.nameLowerCase}s)) {
			for (${pojo.name} ${pojo.nameLowerCase} : ${pojo.nameLowerCase}s) {
				DataTableRowVo dtrv = new DataTableRowVo();
				doToDtrv(dtrv, ${pojo.nameLowerCase});
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}
	
	/**
	 * dos to dtsrv
	 
	 * @param ${pojo.nameLowerCase}s
	 *            实体 list
	 * @return DataTableServerArrayVo
	 */
	public static DataTableServerArrayVo convertDtsrv(List<${pojo.name}> ${pojo.nameLowerCase}s) {
		DataTableServerArrayVo dtsrv = new DataTableServerArrayVo();
		dtsrv.setCheckbox(true);
		if (!CollectionUtils.isEmpty(${pojo.nameLowerCase}s)) {
			for (${pojo.name} ${pojo.nameLowerCase} : ${pojo.nameLowerCase}s) {
				dtsrv.reset();
				<#list valueObject.valueObjectPropertyVos as property>
				<#if property.isDataTableShow == '是'>
				<#if property.type == 'String'>
				dtsrv.addData(${valueObject.nameLowerCase}.get${property.name});
				</#if>
				<#if property.type == 'Integer' || property.type == 'Double'>
				dtsrv.addData(${valueObject.nameLowerCase}.get${property.name}+"");
				</#if>
				<#if property.type == 'Date'>
				Date ${property.name} = ${valueObject.nameLowerCase}.get${property.name};
				String ${property.name}Str=DateUtil.format(${property.name},property.dateFormat);
				dtsrv.addData(${property.name}Str);
				</#if>
				</#if>
				</#list>
				dtsrv.setId(${pojo.nameLowerCase}.getId());
			}
		}
		return dtsrv;
	}

}