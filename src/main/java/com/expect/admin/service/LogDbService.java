package com.expect.admin.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.expect.admin.data.dao.LogDbRepository;
import com.expect.admin.data.dataobject.LogDb;
import com.expect.admin.service.convertor.LogDbConvertor;
import com.expect.admin.service.vo.LogDbVo;

@Service
public class LogDbService {

	@Autowired
	private LogDbRepository logDbRepository;

	/**
	 * 根据条件分页获取日志记录
	 */
	public Page<LogDb> getLogs(LogDbVo logDbVo, String start, String length) {
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

		LogDb logDb = LogDbConvertor.convert(logDbVo);
		Map<String, Object> betweenMap1 = new HashMap<>();
		Map<String, Object> betweenMap2 = new HashMap<>();
		betweenMap1.put("dateTime", logDbVo.getDateTime1());
		betweenMap2.put("dateTime", logDbVo.getDateTime2());
		betweenMap1.put("executeTime", logDbVo.getExecuteTime1());
		betweenMap2.put("executeTime", logDbVo.getExecuteTime2());

		Page<LogDb> logDbPage = logDbRepository.findByCondition(logDb, betweenMap1, betweenMap2,
				new PageRequest(page, pageSize, new Sort(Direction.DESC, "dateTime")));
		return logDbPage;
	}

	/**
	 * 根据id获取LogDb
	 */
	public LogDbVo getLogDbById(String id) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		LogDb logDb = logDbRepository.findOne(id);
		return LogDbConvertor.convert(logDb);
	}

}
