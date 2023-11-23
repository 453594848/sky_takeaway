package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /*
     * 判断菜品被套餐关联
     * */
    List<Long> getSetmealIdByDishID(List<Long> ids);
}
