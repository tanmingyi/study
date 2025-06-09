package com.sky.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.sky.enumeration.OperationType;

@Target(ElementType.METHOD) // 定义注解可以应用在方法上
@Retention(RetentionPolicy.RUNTIME) // 定义注解在运行时可见
public @interface AutoFill {
    // 定义一个枚举类型，表示操作类型
    OperationType value();
}