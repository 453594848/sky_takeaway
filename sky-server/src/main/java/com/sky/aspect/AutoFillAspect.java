package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

//切面类
@Aspect

@Component
@Slf4j
public class AutoFillAspect {
    /*
     * 切入点
     * */
    @Pointcut("@annotation(com.sky.annotation.AutoFill)")
    private void autoFillPointCut() {
    }

    /*
     * 连接点
     * */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开启自动填充");
        //获取被拦截方法的操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//获取方法签名对象
        OperationType value = signature
                .getMethod()
                .getAnnotation(AutoFill.class)//获取方法的注解对象
                .value();//获取注解的操作类型
        //获取被拦截方法的参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        //进行的操作
        if (value == OperationType.INSERT) {
            try {
                //为四个公共字段赋值
                args[0].getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class)
                        .invoke(args[0], LocalDateTime.now());
                args[0].getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class)
                        .invoke(args[0], LocalDateTime.now());//通过反射进行赋值

                Method setUpdateUser = args[0].getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                Method setCreatUser = args[0].getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                setCreatUser.invoke(args[0], BaseContext.getCurrentId());
                setUpdateUser.invoke(args[0], BaseContext.getCurrentId());

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else if (value == OperationType.UPDATE) {
            try {
                args[0].getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class)
                        .invoke(args[0], LocalDateTime.now());
                Method setUpdateUser = args[0].getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateUser.invoke(args[0], BaseContext.getCurrentId());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
