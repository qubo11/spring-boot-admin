package com.expect.admin.data.support.other;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface ConditionSearch {

	/**
	 * 等于
	 */
	public final String Operator_Equal = "Equal";
	/**
	 * 不等于
	 */
	public final String Operator_NotEqual = "NotEqual";
	/**
	 * 空
	 */
	public final String Operator_IsNull = "IsNull";
	/**
	 * 非空
	 */
	public final String Operator_IsNotNull = "IsNotNull";
	/**
	 * like
	 */
	public final String Operator_Like = "Like";
	/**
	 * not like
	 */
	public final String Operator_NotLike = "NotLike";
	/**
	 * in
	 */
	public final String Operator_In = "In";
	/**
	 * not in
	 */
	public final String Operator_NotIn = "NotIn";
	/**
	 * 范围区间between<br/>
	 * 如果是单值String类型,那么参数值用逗号(,)分开:<br/>
	 * 1.如果参数值两边都存在(不为null或者"")，则使用between;<br/>
	 * 2.如果参数值前面存在，则使用大于等于;<br/>
	 * 3.如果参数值后面存在，则使用小于等于.<br/>
	 * 如果是双值String/Date类型:<br/>
	 * 1.如果参数值都存在(不为null)，则使用between;<br/>
	 * 2.如果参数值前面存在，则使用大于等于;<br/>
	 * 3.如果参数值后面存在，则使用小于等于.<br/>
	 */
	public final String Operator_Between = "Between";
	/**
	 * 小于
	 */
	public final String Operator_LessThan = "LessThan";
	/**
	 * 小于或等于
	 */
	public final String Operator_LessThanOrEqualTo = "LessThanOrEqualTo";
	/**
	 * 大于
	 */
	public final String Operator_GreaterThan = "GreaterThan";
	/**
	 * 大于或等于
	 */
	public final String Operator_GreaterThanOrEqualTo = "GreaterThanOrEqualTo";
	/**
	 * 小于或等于
	 */
	public final String Operator_Le = "Le";
	/**
	 * 小于
	 */
	public final String Operator_Lt = "Lt";
	/**
	 * 大于或等于
	 */
	public final String Operator_Ge = "Ge";
	/**
	 * 大于
	 */
	public final String Operator_Gt = "Gt";

	/**
	 * 条件的运算符
	 */
	public String value() default Operator_Equal;

	/**
	 * 用来标记Between双值参数。<br/>
	 */
	public String betweenDescriptor() default "";

}
