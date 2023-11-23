package com.sky.annotation;
/*
 * 自定义注解，标识字段自动填充
 * */

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//注解在代码运行时的生命周期
@Retention(RetentionPolicy.RUNTIME)
//注解的作用目标
@Target(ElementType.METHOD)

public @interface AutoFill {
    //数据库操作类型
    OperationType value();
}
