package org.whz.consumer.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: hong-zhi
 * @date: 2023/5/10 16:31
 * @Description 借助SpringAOP 实现打印调用方法的名称
 * 结合 @LogMethodName 实现方法名称打印
 */
@Slf4j
@Component
@Aspect //定义切面
public class MessageInfoLog {

//    @Before("@annotation(LogMethodName)")
//    public void logMethodName(JoinPoint joinPoint) {
//        // 打印方法签名
//        log.info("当前方法名称: {}", joinPoint.getSignature().getName());
//    }

    /**
     * 定义切点 org.whz.consumer.service所有公共方法
     */
    @Pointcut("execution(public * org.whz.consumer.service..*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logServiceMethodName(JoinPoint joinPoint) {
        // 打印方法签名
        log.info("\r\n当前方法名称: {}", joinPoint.getSignature().getName());
    }
}
