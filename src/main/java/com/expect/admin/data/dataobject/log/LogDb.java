package com.expect.admin.data.dataobject.log;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.expect.admin.data.support.other.ConditionSearch;

/**
 * 数据库日志
 */
@Entity
@Table(name = "c_log_db")
public class LogDb {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", nullable = false, unique = true, length = 32)
	private String id;
	@ConditionSearch(value = ConditionSearch.Operator_Like)
	private String methodName;// 方法名（类名+方法名）
	@ConditionSearch(value = ConditionSearch.Operator_Equal)
	private String operationType;// 操作类型
	@ConditionSearch(value = ConditionSearch.Operator_Like)
	private String description;// 方法描述
	@ConditionSearch(value = ConditionSearch.Operator_Between, betweenDescriptor = "executeTime")
	private Long executeTime;// 执行一次操作时间
	@ConditionSearch(value = ConditionSearch.Operator_Between, betweenDescriptor = "dateTime")
	private Date dateTime;// 执行的时间
	@ConditionSearch(value = ConditionSearch.Operator_Like)
	private String result;// 返回的结果
	private String userId;// 用户id
	@ConditionSearch(value = ConditionSearch.Operator_Like)
	private String username;// 用户名
	@OneToMany(mappedBy = "logDb")
	private Set<LogDbParam> params;// 传入的参数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "method_name", length = 255)
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Column(name = "operation_type", length = 15)
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	@Column(name = "description", length = 1023)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "execute_time", length = 255)
	public Long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}

	@Column(name = "date_time")
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	@Type(type = "text")
	@Column(name = "result")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<LogDbParam> getParams() {
		return params;
	}

	public void setParams(Set<LogDbParam> params) {
		this.params = params;
	}

}
