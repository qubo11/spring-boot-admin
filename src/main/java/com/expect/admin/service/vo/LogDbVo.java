package com.expect.admin.service.vo;

import java.util.ArrayList;
import java.util.List;

import com.expect.admin.service.vo.component.SimpleVo;

public class LogDbVo {

	private String id;
	private String methodName;// 方法名（类名+方法名）
	private String operationType;// 操作类型
	private String description;// 方法描述
	private String executeTimeStr;// 执行一次操作时间
	private String dateTimeStr;// 执行的时间
	private String result;// 返回的结果
	private String userId;// 用户id
	private String username;// 用户名
	private List<SimpleVo> params;// 参数

	// 以下是查询需要的附加字段
	private String dateTime1;
	private String dateTime2;
	private String executeTime1;
	private String executeTime2;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExecuteTimeStr() {
		return executeTimeStr;
	}

	public void setExecuteTimeStr(String executeTimeStr) {
		this.executeTimeStr = executeTimeStr;
	}

	public String getDateTimeStr() {
		return dateTimeStr;
	}

	public void setDateTimeStr(String dateTimeStr) {
		this.dateTimeStr = dateTimeStr;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<SimpleVo> getParams() {
		return params;
	}

	public void setParams(List<SimpleVo> params) {
		this.params = params;
	}

	public void addParam(String id, String value) {
		if (params == null) {
			params = new ArrayList<>();
		}
		SimpleVo sv = new SimpleVo();
		sv.setId(id);
		sv.setName(value);
		params.add(sv);
	}

	public String getDateTime1() {
		return dateTime1;
	}

	public void setDateTime1(String dateTime1) {
		this.dateTime1 = dateTime1;
	}

	public String getDateTime2() {
		return dateTime2;
	}

	public void setDateTime2(String dateTime2) {
		this.dateTime2 = dateTime2;
	}

	public String getExecuteTime1() {
		return executeTime1;
	}

	public void setExecuteTime1(String executeTime1) {
		this.executeTime1 = executeTime1;
	}

	public String getExecuteTime2() {
		return executeTime2;
	}

	public void setExecuteTime2(String executeTime2) {
		this.executeTime2 = executeTime2;
	}

}
