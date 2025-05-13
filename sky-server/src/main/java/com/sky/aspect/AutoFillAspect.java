package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
@Component
@Aspect
@Slf4j
public class AutoFillAspect {

    //切点类
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }
    //前置增强
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
         log.info("公共字段填充.....");
         // 获得方法的类型
       MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType type = annotation.value();
        // 读取参数
        Object[] args = joinPoint.getArgs();
        if(args == null && args.length == 0){
            return;
        }
        Object arg = args[0];
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        if(type ==OperationType.INSERT){
            try {
                Method setCreateTime = arg.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = arg.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = arg.getClass().getMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = arg.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateUser.invoke(arg,currentId);
                setCreateUser.invoke(arg,currentId);
                setCreateTime.invoke(arg,now);
                setUpdateTime.invoke(arg,now);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (type == OperationType.UPDATE) {
            try {
                Method setUpdateTime = arg.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = arg.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateUser.invoke(arg,currentId);
                setUpdateTime.invoke(arg,now);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
