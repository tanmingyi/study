package com.sky.aspect;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义切面
 */
@Aspect
@Component
@Slf4j
public class AutoFill {
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws Exception{
        log.info("开始进行公共字段自动填充...");
        //获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        com.sky.annotation.AutoFill autoFill = signature.getMethod().getAnnotation(com.sky.annotation.AutoFill.class);
        //获取到当前被拦截的方法的数据库操作类型
        OperationType operationType = autoFill.value();
        //获取到当前被拦截的方法的参数
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0) {
            return;
        }
        //获取被拦截方法的第一个参数
        Object entity = args[0];
        //判断当前数据库操作类型是否为插入操作
        if(operationType == OperationType.INSERT) {
            //为实体对象的四个公共字段赋值
            entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class).invoke(entity, LocalDateTime.now());
            entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(entity, LocalDateTime.now());
            entity.getClass().getDeclaredMethod("setCreateUser", Long.class).invoke(entity, BaseContext.getCurrentId());
            entity.getClass().getDeclaredMethod("setUpdateUser", Long.class).invoke(entity, BaseContext.getCurrentId());
        } else if(operationType == OperationType.UPDATE) {
            //为实体对象的四个公共字段赋值
            entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(entity, LocalDateTime.now());
            entity.getClass().getDeclaredMethod("setUpdateUser", Long.class).invoke(entity, BaseContext.getCurrentId());
        }
    }
}
