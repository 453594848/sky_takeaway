package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /*
     * 新增菜品和口味
     * */
    void saveDishAndFlavors(DishDTO dishDTO);

    /*
     * 菜品分页查询
     * */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /*
     * 菜品删除
     * */
    void deleteBatch(List<Long> ids);

    /*
     * 根据ID查询员工
     * */
    DishVO getById(Long id);

    /*
     * 修改菜品
     * */
    void updateAndFlavor(DishDTO dishDTO);

    /*
     * 菜品起售、停售
     * */
    void startOrStop(Integer status, Long id);
    /*
     * 根据分类id查询菜品
     * */
    List<Dish> list(Long categoryId);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
