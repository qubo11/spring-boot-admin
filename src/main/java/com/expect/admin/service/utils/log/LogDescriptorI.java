package com.expect.admin.service.utils.log;

/**
 * 日志记录描述接口，用户日志方法的AOP，统一处理日志描述和操作类型
 */
public interface LogDescriptorI {

	public final String OperationType_Insert = "Insert";
	public final String OperationType_Update = "Update";
	public final String OperationType_Delete = "Delete";

	/**
	 * 日志描述
	 */
	public String description(String className,String methodName);

	/**
	 * 日志操作类型
	 */
	public String operationType(String className,String methodName);

}
