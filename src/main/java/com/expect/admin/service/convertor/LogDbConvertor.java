package com.expect.admin.service.convertor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.LogDb;
import com.expect.admin.data.dataobject.LogDbParam;
import com.expect.admin.service.vo.LogDbVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableServerArrayVo;
import com.expect.admin.utils.DateUtil;

/**
 * 日志的Contertor
 */
public class LogDbConvertor {

	/**
	 * do to vo
	 */
	public static LogDbVo convert(LogDb logDb) {
		LogDbVo logDbVo = new LogDbVo();
		if (logDb != null) {
			BeanUtils.copyProperties(logDb, logDbVo);
			if (logDb.getDateTime() != null) {
				logDbVo.setDateTimeStr(DateUtil.format(logDb.getDateTime(), DateUtil.noSecondFormat));
			}
			if (logDb.getExecuteTime() != null) {
				logDbVo.setExecuteTimeStr(logDb.getExecuteTime() + "");
			}
			// 设置参数
			Set<LogDbParam> params = logDb.getParams();
			if (!CollectionUtils.isEmpty(params)) {
				for (LogDbParam param : params) {
					logDbVo.addParam(param.getId(), param.getParam());
				}
			}
		}
		return logDbVo;
	}

	/**
	 * dos to vos
	 */
	public static List<LogDbVo> convert(List<LogDb> logDbs) {
		List<LogDbVo> logDbVos = new ArrayList<>();
		if (!CollectionUtils.isEmpty(logDbs)) {
			for (LogDb logDb : logDbs) {
				LogDbVo logDbVo = convert(logDb);
				logDbVos.add(logDbVo);
			}
		}
		return logDbVos;
	}

	/**
	 * vo to do
	 */
	public static LogDb convert(LogDbVo logDbVo) {
		LogDb logDb=new LogDb();
		BeanUtils.copyProperties(logDbVo, logDb);
		if (!StringUtils.isBlank(logDbVo.getDateTimeStr())) {
			logDb.setDateTime(DateUtil.parse(logDbVo.getDateTimeStr(), DateUtil.noSecondFormat));
		}
		if (!StringUtils.isBlank(logDbVo.getExecuteTimeStr())) {
			logDb.setExecuteTime(Long.valueOf(logDbVo.getExecuteTimeStr()));
		}
		return logDb;
	}

	/**
	 * do to dtrv
	 */
	public static void convertDtrv(DataTableRowVo dtrv, LogDb logDb) {
		LogDbVo logDbVo = convert(logDb);
		dtrv.setObj(logDbVo);
		dtrv.addData(logDb.getMethodName());
		dtrv.addData(logDbVo.getDateTimeStr());
		dtrv.addData(logDb.getUsername());
		dtrv.addData(logDb.getOperationType());
		dtrv.addData(logDb.getDescription());
		dtrv.addData(logDb.getExecuteTime() + "");
		// 设置操作的button
		StringBuilder sb = new StringBuilder();
		sb.append(DataTableButtonFactory.getGreenButton("详情", "data-id='" + logDb.getId() + "'"));
		dtrv.addData(sb.toString());
	}

	/**
	 * dos to dtsrv
	 */
	public static DataTableServerArrayVo convertDtsrv(List<LogDb> logDbs) {
		DataTableServerArrayVo dtsrv = new DataTableServerArrayVo();
		if (!CollectionUtils.isEmpty(logDbs)) {
			for (int i = 0; i < logDbs.size(); i++) {
				LogDb logDb = logDbs.get(i);
				dtsrv.reset();
				dtsrv.addData(logDb.getMethodName());
				if (logDb.getDateTime() != null) {
					dtsrv.addData(DateUtil.format(logDb.getDateTime(), DateUtil.noSecondFormat));
				} else {
					dtsrv.addData("");
				}
				dtsrv.addData(logDb.getUsername());
				dtsrv.addData(logDb.getOperationType());
				dtsrv.addData(logDb.getDescription());
				dtsrv.addData(logDb.getExecuteTime() + "");
				// 设置操作的button
				StringBuilder sb = new StringBuilder();
				sb.append(DataTableButtonFactory.getGreenButton("详情", "data-id='" + logDb.getId() + "'"));
				dtsrv.addData(sb.toString());
				dtsrv.setId(logDb.getId());
			}
		}
		return dtsrv;
	}

}
