package com.expect.admin.data.support.specs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;

import com.expect.admin.data.support.other.ConditionSearch;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CustomerSpesc {

	public <T> Specification<T> conditionSearch(final EntityManager entityManager, final T entity,
			Map<String, Object> betweenParams1, Map<String, Object> betweenParams2) {
		final Class<T> type = (Class<T>) entity.getClass();
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				EntityType<T> entityType = entityManager.getMetamodel().entity(type);
				List<BetweenEntity> bes = new ArrayList<>();
				for (Attribute<T, ?> attr : entityType.getDeclaredAttributes()) {
					String attrName = attr.getName();
					Object attrValue = ReflectionUtils.getField((Field) attr.getJavaMember(), entity);
					try {
						Field field = type.getDeclaredField(attrName);
						if (field == null) {
							continue;
						}
						ConditionSearch conditionSearch = field.getAnnotation(ConditionSearch.class);
						if (conditionSearch == null) {
							continue;
						}
						String operator = conditionSearch.value();
						if (!operator.equals(ConditionSearch.Operator_Between)) {
							if (attrValue == null) {
								continue;
							}
							Predicate predicate = getPredicate(conditionSearch, cb, root.get(attrName), attrValue);
							if (predicate != null) {
								predicates.add(predicate);
							}
						} else {
							BetweenEntity be = new BetweenEntity();
							be.descritor = conditionSearch.betweenDescriptor();
							be.attrName = attrName;
							be.attrValue = attrValue;
							bes.add(be);
						}
					} catch (IllegalArgumentException | NoSuchFieldException | SecurityException e) {
						e.printStackTrace();
					}
				}
				// 处理between
				for (int i = bes.size() - 1; i >= 0; i--) {
					BetweenEntity be = bes.get(i);
					// 单值between
					if ("".equals(be.descritor)) {
						if (be.attrValue == null) {
							continue;
						}
						if (be.attrValue instanceof String) {
							Predicate predicate = between(cb, root.get(be.attrName), (String) be.attrValue);
							if (predicate != null) {
								predicates.add(predicate);
							}
						}
					} else {
						// 双值between
						String descripor = be.descritor;
						Object attrValue1 = betweenParams1.get(descripor);
						Object attrValue2 = betweenParams2.get(descripor);
						if (attrValue1 == null && attrValue2 == null) {
							continue;
						}
						Predicate predicate = between(cb, root.get(be.attrName), attrValue1, attrValue2);
						if (predicate != null) {
							predicates.add(predicate);
						}
					}
				}
				Predicate[] predicatesArr = new Predicate[predicates.size()];
				for (int i = 0; i < predicates.size(); i++) {
					predicatesArr[i] = predicates.get(i);
				}
				return predicates.isEmpty() ? cb.conjunction() : cb.and(predicatesArr);
			}
		};
	}

	public Predicate getPredicate(ConditionSearch conditionSearch, CriteriaBuilder cb, Expression expression,
			Object attrValue) {
		String operator = conditionSearch.value();
		switch (operator) {
		case ConditionSearch.Operator_Equal:
			return equal(cb, expression, attrValue);
		case ConditionSearch.Operator_NotEqual:
			return notEqual(cb, expression, attrValue);
		case ConditionSearch.Operator_IsNull:
			return isNull(cb, expression);
		case ConditionSearch.Operator_IsNotNull:
			return isNotNull(cb, expression);
		case ConditionSearch.Operator_Like:
			return like(cb, expression, attrValue);
		case ConditionSearch.Operator_NotLike:
			return notLike(cb, expression, attrValue);
		case ConditionSearch.Operator_In:
			if (attrValue instanceof Collection) {
				return in(cb, expression, (Collection) attrValue);
			}
		case ConditionSearch.Operator_NotIn:
			if (attrValue instanceof Collection) {
				return notIn(cb, expression, (Collection) attrValue);
			}
		case ConditionSearch.Operator_LessThan:
			return lessThan(cb, expression, attrValue);
		case ConditionSearch.Operator_LessThanOrEqualTo:
			return lessThanOrEqualTo(cb, expression, attrValue);
		case ConditionSearch.Operator_GreaterThan:
			return greaterThan(cb, expression, attrValue);
		case ConditionSearch.Operator_GreaterThanOrEqualTo:
			return greaterThanOrEqualTo(cb, expression, attrValue);
		case ConditionSearch.Operator_Le:
			if (attrValue instanceof Number) {
				return le(cb, expression, (Number) attrValue);
			}
		case ConditionSearch.Operator_Lt:
			if (attrValue instanceof Number) {
				return lt(cb, expression, (Number) attrValue);
			}
		case ConditionSearch.Operator_Ge:
			if (attrValue instanceof Number) {
				return ge(cb, expression, (Number) attrValue);
			}
		case ConditionSearch.Operator_Gt:
			if (attrValue instanceof Number) {
				return gt(cb, expression, (Number) attrValue);
			}
		}
		return null;
	}

	/**
	 * 相等 equal
	 */
	public Predicate equal(CriteriaBuilder cb, Expression expression, Object value) {
		return cb.equal(expression, value);
	}

	/**
	 * 不相等 notEqual
	 */
	public Predicate notEqual(CriteriaBuilder cb, Expression expression, Object value) {
		return cb.notEqual(expression, value);
	}

	/**
	 * 空 isNull
	 */
	public Predicate isNull(CriteriaBuilder cb, Expression expression) {
		return cb.isNull(expression);
	}

	/**
	 * 非空 isNotNull
	 */
	public Predicate isNotNull(CriteriaBuilder cb, Expression expression) {
		return cb.isNotNull(expression);
	}

	/**
	 * not in
	 */

	public Predicate notIn(CriteriaBuilder cb, Expression expression, Collection values) {
		Iterator iterator = values.iterator();
		In in = cb.in(expression);
		while (iterator.hasNext()) {
			in.value(iterator.next());
		}
		return cb.not(in);
	}

	/**
	 * in
	 */
	public Predicate in(CriteriaBuilder cb, Expression expression, Collection values) {
		Iterator iterator = values.iterator();
		In in = cb.in(expression);
		while (iterator.hasNext()) {
			in.value(iterator.next());
		}
		return cb.in(in);
	}

	/**
	 * 模糊匹配 like
	 */
	public Predicate like(CriteriaBuilder cb, Expression expression, Object value) {
		return cb.like(expression, "%" + value + "%");
	}

	/**
	 * 模糊匹配 not like
	 */
	public Predicate notLike(CriteriaBuilder cb, Expression expression, Object value) {
		return cb.notLike(expression, "%" + value + "%");
	}

	/**
	 * 范围区间 between<br/>
	 * 如果是单值String类型,那么参数值用逗号(,)分开:<br/>
	 * 1.如果参数值两边都存在(不为null或者"")，则使用between;<br/>
	 * 2.如果参数值前面存在，则使用大于等于;<br/>
	 * 3.如果参数值后面存在，则使用小于等于.<br/>
	 */
	public Predicate between(CriteriaBuilder cb, Expression expression, String value) {
		String[] values = value.split(",");
		if (values.length == 0) {
			return null;
		} else if (values.length == 1) {
			return greaterThanOrEqualTo(cb, expression, values[0]);
		} else {
			if (!StringUtils.isBlank(values[0]) && !StringUtils.isBlank(values[0])) {
				return cb.between(expression, values[0], values[1]);
			} else if (!StringUtils.isBlank(values[0]) && StringUtils.isBlank(values[0])) {
				return greaterThanOrEqualTo(cb, expression, values[0]);
			} else if (StringUtils.isBlank(values[0]) && !StringUtils.isBlank(values[0])) {
				return lessThanOrEqualTo(cb, expression, values[1]);
			} else {
				return null;
			}
		}
	}

	/**
	 * 范围区间 between<br/>
	 * 如果是双值String/Integer/Long/Date类型:<br/>
	 * 1.如果参数值都存在(不为null)，则使用between;<br/>
	 * 2.如果参数值前面存在，则使用大于等于;<br/>
	 * 3.如果参数值后面存在，则使用小于等于.<br/>
	 */
	public Predicate between(CriteriaBuilder cb, Expression expression, Object obj1, Object obj2) {
		if (obj1 != null && obj2 != null) {
			if (obj1 instanceof Date && obj2 instanceof Date) {
				return cb.between(expression, (Date) obj1, (Date) obj2);
			} else {
				if (obj1 instanceof Integer && obj2 instanceof Integer) {
					return cb.between(expression, (Integer) obj1, (Integer) obj2);
				} else if (obj1 instanceof Long && obj2 instanceof Long) {
					return cb.between(expression, (Long) obj1, (Long) obj2);
				} else {
					return cb.between(expression, (String) obj1, (String) obj2);
				}
			}
		} else if (obj1 != null && obj2 == null) {
			return greaterThan(cb, expression, obj1);
		} else if (obj1 == null && obj2 != null) {
			return lessThan(cb, expression, obj2);
		} else {
			return null;
		}
	}

	/**
	 * 数字-小于等于 le
	 */
	public Predicate le(CriteriaBuilder cb, Expression expression, Number value) {
		return cb.le(expression, value);
	}

	/**
	 * 数字-小于
	 */
	public Predicate lt(CriteriaBuilder cb, Expression expression, Number value) {
		return cb.lt(expression, value);
	}

	/**
	 * 数字-大于等于
	 */
	public Predicate ge(CriteriaBuilder cb, Expression expression, Number value) {
		return cb.ge(expression, value);
	}

	/**
	 * 数字-大于
	 */
	public Predicate gt(CriteriaBuilder cb, Expression expression, Number value) {
		return cb.gt(expression, value);
	}

	/**
	 * 小于 lessThan(用于字符串/日期的比较)
	 */
	public Predicate lessThan(CriteriaBuilder cb, Expression expression, Object value) {
		if (value instanceof Date) {
			return cb.lessThan(expression, (Date) value);
		} else {
			if (value instanceof Integer) {
				return cb.lessThan(expression, (Integer) value);
			} else if (value instanceof Long) {
				return cb.lessThan(expression, (Long) value);
			} else {
				return cb.lessThan(expression, (String) value);
			}
		}
	}

	/**
	 * 小于或等于 lessThanOrEqualTo(用于字符串/日期的比较)
	 */
	public Predicate lessThanOrEqualTo(CriteriaBuilder cb, Expression expression, Object value) {
		if (value instanceof Date) {
			return cb.lessThanOrEqualTo(expression, (Date) value);
		} else {
			if (value instanceof Integer) {
				return cb.lessThanOrEqualTo(expression, (Integer) value);
			} else if (value instanceof Long) {
				return cb.lessThanOrEqualTo(expression, (Long) value);
			} else {
				return cb.lessThanOrEqualTo(expression, (String) value);
			}
		}
	}

	/**
	 * 大于 greaterThan(用于数字/字符串/日期的比较)
	 */
	public Predicate greaterThan(CriteriaBuilder cb, Expression expression, Object value) {
		if (value instanceof Date) {
			return cb.greaterThan(expression, (Date) value);
		} else {
			if (value instanceof Integer) {
				return cb.greaterThan(expression, (Integer) value);
			} else if (value instanceof Long) {
				return cb.greaterThan(expression, (Long) value);
			} else {
				return cb.greaterThan(expression, (String) value);
			}
		}
	}

	/**
	 * 大于或等于 greaterThanOrEqualTo(用于字符串/日期的比较)
	 */
	public Predicate greaterThanOrEqualTo(CriteriaBuilder cb, Expression expression, Object value) {
		if (value instanceof Date) {
			return cb.greaterThanOrEqualTo(expression, (Date) value);
		} else {
			if (value instanceof Integer) {
				return cb.greaterThanOrEqualTo(expression, (Integer) value);
			} else if (value instanceof Long) {
				return cb.greaterThanOrEqualTo(expression, (Long) value);
			} else {
				return cb.greaterThanOrEqualTo(expression, (String) value);
			}
		}
	}

	class BetweenEntity {
		public String attrName;
		public Object attrValue;
		public String descritor;
		public Integer index;
	}

}
