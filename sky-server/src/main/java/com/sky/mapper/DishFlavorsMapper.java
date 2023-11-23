package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorsMapper {
    /*
     * 新增菜品口味
     * */
    void insertBatch(List<DishFlavor> flavors);

    /*
     * 根据菜品id删除口味
     * */
    @Delete("delete from sky_take_out.dish_flavor where dish_id =#{id}")
    void deleteByDishID(Long id);

    /*
     * 根据菜品id查询口味
     * */
    @Select("select * from sky_take_out.dish_flavor where dish_id =#{id}")
    List<DishFlavor> getByDishId(Long id);
}
