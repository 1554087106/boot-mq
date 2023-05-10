package org.whz.consumer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: hong-zhi
 * @date: 2023/5/10 16:42
 * @Description 创建一个注解
 * 标记位置:方法
 * 作用时期:Runtime
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMethodName {
}
