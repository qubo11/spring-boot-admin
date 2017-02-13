<#if business.packageName??>
package ${business.packageName};
</#if>

import org.apache.commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.transaction.Transactional;

/**
 * ${business.comment}
 */
@Service
public interface ${business.name}{

	<#if dao??>
		@Autowired
		private ${dao.name} ${dao.nameLowerCase};
	
	<#if valueObject?? && pojo??>
	/**
	 * 根据id获取实体
	 * 
	 * @param id
	 *            实体 id
	 * @return ${valueObject.name}
	 */
	public ${valueObject.name} get${valueObject.name}ById(String id) {
		${pojo.name} ${pojo.nameLowerCase} = null;
		if (!StringUtils.isBlank(id)) {
			${pojo.nameLowerCase} = ${dao.nameLowerCase}.findOne(id);
		}
		return ${business.name}Convertor.doToVo(${pojo.nameLowerCase});
	}
	</#if>
	
	/**
	 * 获取所有的实体，封装成实体vo list
	 * 
	 * @return 实体vo list
	 */
	public List<${valueObject.name}> get${valueObject.name}() {
		List<${pojo.name}> ${pojo.nameLowerCase}s = ${dao.nameLowerCase}.findAll();
		return ${business.name}Convertor.dosToVos(${valueObject.name}s);
	}

	/**
	 * 获取所有的实体，封装成DataTableVos
	 * 
	 * @return 实体DataTableVo list
	 */
	public List<DataTableRowVo> get${valueObject.name}Dtrvs() {
		List<${pojo.name}> ${pojo.nameLowerCase}s = ${dao.nameLowerCase}.findAll();
		return ${business.name}Convertor.dosToDtrvs(${valueObject.name}s);
	}
	
	/**
	 * 分页获取实体，封装成实体DataTableServerArrayVo
	 *
	 * @return 实体DataTableServerArrayVo
	 */
	public DataTableServerArrayVo get${valueObject.name}Dtsrv(String start, String length) {
		int pageStart = 0;
		int pageSize = 0;
		if (NumberUtils.isDigits(start)) {
			pageStart = Integer.valueOf(start);
		}
		if (NumberUtils.isDigits(length)) {
			pageSize = Integer.valueOf(length);
		}
		int page = pageStart / pageSize;
		page = pageStart % pageSize == 0 ? page : page + 1;
		Page<${pojo.name}> ${pojo.nameLowerCase}Page = ${dao.nameLowerCase}.findAll(new PageRequest(page, pageSize));
		
		List<${pojo.name}> ${pojo.nameLowerCase}s = ${pojo.nameLowerCase}Page.getContent();
		DataTableServerArrayVo dtsrv = ${business.name}Convertor.convertDtsrv(${pojo.nameLowerCase}s);
		dtsrv.setRecordsFiltered(${pojo.nameLowerCase}Page.getTotalElements());
		dtsrv.setRecordsTotal(${pojo.nameLowerCase}Page.getTotalElements());
		return dtsrv;
	}
	
	/**
	 * 保存实体
	 * 
	 * @param ${valueObject.nameLowerCase}
	 *            实体vo
	 * 
	 * @return 实体DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo save(${valueObject.name} ${valueObject.nameLowerCase}) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("保存失败");
		
		${pojo.name} ${pojo.nameLowerCase} = new ${pojo.name}();
		${business.name}Convertor.voToDo(${valueObject.nameLowerCase}, ${pojo.nameLowerCase});

		${pojo.name} result = ${dao.nameLowerCase}.save(${pojo.nameLowerCase});
		if (result != null) {
			dtrv.setMessage("保存成功");
			dtrv.setResult(true);
			${business.name}Convertor.doToDtrv(dtrv, result);
		}
		return dtrv;
	}

	/**
	 * 更新实体
	 * 
	 * @param ${valueObject.nameLowerCase}
	 *            实体vo
	 * 
	 * @return 实体DataTableRowVo
	 */
	@Transactional
	public DataTableRowVo update(${valueObject.name} ${valueObject.nameLowerCase}) {
		DataTableRowVo dtrv = new DataTableRowVo();
		dtrv.setMessage("更新失败");
		
		if (StringUtils.isBlank(businessVo.getId())) {
			return dtrv;
		}
		${pojo.name} ${pojo.nameLowerCase} = ${dao.nameLowerCase}.findOne(${valueObject.nameLowerCase}.getId());
		if (${pojo.nameLowerCase} == null) {
			return dtrv;
		}
		${business.name}Convertor.voToDo(${valueObject.nameLowerCase}, ${pojo.nameLowerCase});

		dtrv.setMessage("更新成功");
		dtrv.setResult(true);
		${business.name}Convertor.doToDtrv(dtrv, ${pojo.nameLowerCase});
		return dtrv;
	}

	/**
	 * 删除实体
	 * 
	 * @param id
	 *            实体id
	 * @return ResultVo 结果vo
	 */
	@Transactional
	public ResultVo delete(String id) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(id)) {
			return rv;
		}
		${pojo.name} ${pojo.nameLowerCase} = ${dao.nameLowerCase}.findOne(id);
		if (${pojo.nameLowerCase} == null) {
			return rv;
		}
		
		${dao.nameLowerCase}.delete(${pojo.nameLowerCase});
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            用,号隔开
	 */
	@Transactional
	public ResultVo deleteBatch(String ids) {
		ResultVo rv = new ResultVo();
		rv.setMessage("删除失败");
		if (StringUtils.isBlank(ids)) {
			return rv;
		}
		
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			${dao.nameLowerCase}.delete(id);
		}
		rv.setResult(true);
		rv.setMessage("删除成功");
		return rv;
	}
	</#if>
}