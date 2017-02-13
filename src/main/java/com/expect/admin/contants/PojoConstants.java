package com.expect.admin.contants;

import java.util.Arrays;
import java.util.List;

import com.expect.admin.utils.DateUtil;

public class PojoConstants {

	/**
	 * 以下是属性类型
	 */
	public static final String PROPERTY_STRING = "String";
	public static final String PROPERTY_INTEGER = "Integer";
	public static final String PROPERTY_DOUBLE = "Double";
	public static final String PROPERTY_BYTE = "Byte[]";
	public static final String PROPERTY_TEXT = "Text";
	public static final String PROPERTY_DATE = "Date";
	public static final String PROPERTY_DATETIME = "DateTime";
	public static final String PROPERTY_OTHER = "Other";

	private static List<String> propertyTypes = Arrays.asList(PROPERTY_OTHER, PROPERTY_STRING, PROPERTY_INTEGER,
			PROPERTY_DOUBLE, PROPERTY_TEXT, PROPERTY_DATE, PROPERTY_DATETIME);

	public static List<String> getPropertyTypes() {
		return propertyTypes;
	}

	/**
	 * 以下是值对象属性
	 */
	public static final String VALUEOBJECT_STRING = "String";
	public static final String VALUEOBJECT_INTEGER = "Integer";
	public static final String VALUEOBJECT_DOUBLE = "Double";
	public static final String VALUEOBJECT_DATE = "Date";
	public static final String VALUEOBJECT_OTHER = "Other";

	private static List<String> valueObjectPropertyTypes = Arrays.asList(VALUEOBJECT_OTHER, VALUEOBJECT_STRING,
			VALUEOBJECT_INTEGER, VALUEOBJECT_DOUBLE, PROPERTY_DATE);

	public static List<String> getValueObjectPropertyTypes() {
		return valueObjectPropertyTypes;
	}

	/**
	 * 以下是操作类型
	 */
	public final static String OPERATION_BLANK = "Blank";
	public final static String OPERATION_EQUALS = "Equals";
	public final static String OPERATION_BETWEEN = "Between";
	public final static String OPERATION_LESSTHAN = "LessThan";
	public final static String OPERATION_LESSTHANEQUAL = "LessThanEqual";
	public final static String OPERATION_GREATERTHAN = "GreaterThan";
	public final static String OPERATION_GREATERTHANEQUAL = "GreaterThanEqual";
	public final static String OPERATION_AFTER = "After";
	public final static String OPERATION_BEFORE = "Before";
	public final static String OPERATION_ISNULL = "IsNull";
	public final static String OPERATION_ISNOTNULL = "IsNotNull";
	public final static String OPERATION_LIKE = "Like";
	public final static String OPERATION_NOTLIKE = "NotLike";
	public final static String OPERATION_STARTINGWITH = "StartingWith";
	public final static String OPERATION_ENDINGWITH = "EndingWith";
	public final static String OPERATION_CONTAINING = "Containing";
	public final static String OPERATION_NOT = "Not";
	public final static String OPERATION_IN = "In";
	public final static String OPERATION_NOTIN = "NotIn";
	public final static String OPERATION_TRUE = "True";
	public final static String OPERATION_FALSE = "False";
	public final static String OPERATION_IGNORECASE = "IgnoreCase";

	public final static String OPERATION_AND = "And";
	public final static String OPERATION_OR = "Or";
	public final static String OPERATION_ORDERBYASC = "OrderByAsc";
	public final static String OPERATION_ORDERBYDESC = "OrderByDesc";

	public final static List<String> operations = Arrays.asList(OPERATION_BLANK, OPERATION_EQUALS, OPERATION_BETWEEN,
			OPERATION_LESSTHAN, OPERATION_LESSTHANEQUAL, OPERATION_GREATERTHAN, OPERATION_GREATERTHANEQUAL,
			OPERATION_AFTER, OPERATION_BEFORE, OPERATION_ISNULL, OPERATION_ISNOTNULL, OPERATION_LIKE, OPERATION_NOTLIKE,
			OPERATION_STARTINGWITH, OPERATION_ENDINGWITH, OPERATION_CONTAINING, OPERATION_NOT, OPERATION_IN,
			OPERATION_NOTIN, OPERATION_TRUE, OPERATION_FALSE, OPERATION_IGNORECASE, OPERATION_AND, OPERATION_OR,
			OPERATION_ORDERBYASC, OPERATION_ORDERBYDESC);

	public static List<String> getOperations() {
		return operations;
	}

	/**
	 * 以下是方法名
	 */
	public final static String METHODNAME_FINDBY = "findBy";
	public final static String METHODNAME_READBY = "readBy";
	public final static String METHODNAME_QUERYBY = "queryBy";
	public final static String METHODNAME_GETBY = "getBy";
	public final static String METHODNAME_FINDTOP = "findTop*By";
	public final static String METHODNAME_FINDFIRST = "findFirst*By";
	public final static String METHODNAME_COUNTBY = "countBy";
	public final static String METHODNAME_DELETEBY = "deleteBy";
	public final static String METHODNAME_REMOVEBY = "removeBy";

	public final static List<String> methodNames = Arrays.asList(METHODNAME_FINDBY, METHODNAME_READBY,
			METHODNAME_QUERYBY, METHODNAME_GETBY, METHODNAME_FINDTOP, METHODNAME_FINDFIRST, METHODNAME_COUNTBY,
			METHODNAME_DELETEBY, METHODNAME_REMOVEBY);

	public static List<String> getMethodNames() {
		return methodNames;
	}

	/**
	 * 以下是常用的表单域验证
	 */
	public static final String VALIDATETYPE_NULL = "无";
	public static final String VALIDATETYPE_PHONE = "手机号码";
	public static final String VALIDATETYPE_EMAIL = "电子邮箱";
	public static final String VALIDATETYPE_IDCARD = "身份证号码";
	public static final String VALIDATETYPE_URL = "URL";
	public static final String VALIDATETYPE_CUSTOM = "Other";

	public final static List<String> validateTypes = Arrays.asList(VALIDATETYPE_NULL, VALIDATETYPE_PHONE,
			VALIDATETYPE_EMAIL, VALIDATETYPE_IDCARD, VALIDATETYPE_URL, VALIDATETYPE_CUSTOM);

	public static List<String> getValidateTypes() {
		return validateTypes;
	}

	/**
	 * 以下是表单域类型
	 */
	public static final String FORMTYPE_TEXT = "text";
	public static final String FORMTYPE_TEXTAREA = "textarea";
	public static final String FORMTYPE_HIDDEN = "hidden";
	public static final String FORMTYPE_NUMBER = "number";
	public static final String FORMTYPE_PASSWORD = "password";
	public static final String FORMTYPE_SELECT = "select";
	public static final String FORMTYPE_CHECKBOX = "checkbox";
	public static final String FORMTYPE_RICHTEXT = "富文本编辑器";
	public static final String FORMTYPE_FILEUPLOAD = "FileUpload";

	public final static List<String> formTypes = Arrays.asList(FORMTYPE_TEXT, FORMTYPE_TEXTAREA, FORMTYPE_HIDDEN,
			FORMTYPE_NUMBER, FORMTYPE_PASSWORD, FORMTYPE_SELECT, FORMTYPE_CHECKBOX, FORMTYPE_RICHTEXT);

	public static List<String> getFormTypes() {
		return formTypes;
	}

	/**
	 * 日期格式化类型
	 */
	public final static List<String> dateFormats = Arrays.asList(DateUtil.webFormat, DateUtil.fullFormat,
			DateUtil.chineseDtFormat, DateUtil.noSecondFormat);

	public static List<String> getDateFormats() {
		return dateFormats;
	}
}
