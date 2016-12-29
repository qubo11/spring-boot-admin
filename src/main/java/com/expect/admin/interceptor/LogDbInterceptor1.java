package com.expect.admin.interceptor;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.expect.admin.data.dao.LogDbParamRepository;
import com.expect.admin.data.dao.LogDbRepository;
import com.expect.admin.data.dataobject.LogDb;
import com.expect.admin.data.dataobject.LogDbParam;
import com.expect.admin.data.dataobject.User;
import com.expect.admin.service.log.LogDescriptor;
import com.expect.admin.service.log.LogUtils;

/**
 * 增删改的数据库操作记录
 * 
 * 两种方法：
 * 
 * 1.方法名开头是save,update,delete都会进行日志记录
 * 
 * 2.只要方法上有LogDescriptor的注解，都会进行日志记录
 */
@Component
@Aspect
public class LogDbInterceptor1 {

	private Logger logger = LoggerFactory.getLogger(LogDbInterceptor1.class);

	@Autowired
	private LogDbRepository logDbRepository;
	@Autowired
	private LogDbParamRepository logDbParamRepository;
//	@Autowired
//	private LogDescriptorI logDescriptorI;

//	@Pointcut(value = "(execution(* *..service.*.save*(..)) && !execution(* com.expect.admin.service.*.save*(..))) || (execution(* *..service.*.update*(..)) && !execution(* com.expect.admin.service.*.update*(..))) || execution(* *..service.*.delete*(..)) && !execution(* com.expect.admin.service.*.delete*(..)))")
//	public void pointCutMethod() {
//	}

	// @Around("pointCutMethod()")
//	public Object executeMethod(ProceedingJoinPoint pjp) {
//		logger.info("增删改开始");
//		try {
//			LogDb logDb = new LogDb();
//			// 用户信息
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			User user = (User) auth.getPrincipal();
//			String userId = user.getId();
//			String username = user.getUsername();
//			// 执行时间
//			long executeTime = System.currentTimeMillis();
//			Object object = pjp.proceed();
//			executeTime = System.currentTimeMillis() - executeTime;
//			// 类名
//			String className = pjp.getSignature().getDeclaringTypeName();
//			// 方法名
//			String methodName = pjp.getSignature().getName();
//			logger.info("签名：" + className + "." + methodName);
//			// 日志的注解
//			if (logDescriptorI != null) {
//				logDb.setDescription(logDescriptorI.description(className, methodName));
//				logDb.setOperationType(logDescriptorI.operationType(className, methodName));
//			}
//			logDb.setMethodName(className + "." + methodName);
//			logDb.setExecuteTime(executeTime);
//			logDb.setDateTime(new Date());
//			logDb.setResult(object + "");
//			logDb.setUserId(userId);
//			logDb.setUsername(username);
//			LogDb result = logDbRepository.save(logDb);
//
//			if (result != null) {
//				// 参数
//				Object[] args = pjp.getArgs();
//				for (Object arg : args) {
//					LogDbParam logDbParam = new LogDbParam();
//					logDbParam.setParam(arg + "");
//					logDbParam.setLogDb(logDb);
//					logDbParamRepository.save(logDbParam);
//				}
//			}
//			logger.info("增删改成功");
//			return object;
//		} catch (Throwable e) {
//			e.printStackTrace();
//			logger.info("增删改失败：" + e.getMessage());
//		}
//		return null;
//	}

//	@Pointcut(value = "@annotation(com.expect.admin.utils.log.utils.LogDescriptor)")
//	public void pointAnnotation() {
//	}
//
//	@Around("pointAnnotation()")
//	public Object executeAnnotation(ProceedingJoinPoint pjp) {
//		logger.info("增删改开始");
//		try {
//			LogDb logDb = new LogDb();
//			// 用户信息
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			User user = (User) auth.getPrincipal();
//			String userId = user.getId();
//			String username = user.getUsername();
//			// 执行时间
//			long executeTime = System.currentTimeMillis();
//			Object object = pjp.proceed();
//			executeTime = System.currentTimeMillis() - executeTime;
//			// 类名
//			String className = pjp.getSignature().getDeclaringTypeName();
//			// 方法名
//			String methodName = pjp.getSignature().getName();
//			logger.info("签名：" + className + "." + methodName);
//			// 参数
//			Object[] args = pjp.getArgs();
//			int length = 0;
//			if (args != null) {
//				length = args.length;
//			}
//			// 日志的注解
//			LogDescriptor logDescriptor = LogUtils.getLogDescriptor(className, methodName, length);
//			if (logDescriptor != null) {
//				logDb.setDescription(logDescriptor.value());
//				logDb.setOperationType(logDescriptor.operationType());
//			}
//			logDb.setMethodName(className + "." + methodName);
//			logDb.setExecuteTime(executeTime);
//			logDb.setDateTime(new Date());
//			logDb.setResult(object + "");
//			logDb.setUserId(userId);
//			logDb.setUsername(username);
//			LogDb result = logDbRepository.save(logDb);
//
//			if (result != null) {
//				for (Object arg : args) {
//					LogDbParam logDbParam = new LogDbParam();
//					logDbParam.setParam(arg + "");
//					logDbParam.setLogDb(logDb);
//					logDbParamRepository.save(logDbParam);
//				}
//			}
//			logger.info("增删改成功");
//			return object;
//		} catch (Throwable e) {
//			e.printStackTrace();
//			logger.info("增删改失败：" + e.getMessage());
//		}
//		return null;
//	}
}
