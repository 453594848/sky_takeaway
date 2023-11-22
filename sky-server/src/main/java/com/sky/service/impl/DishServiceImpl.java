package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorsMapper dishFlavorsMapper;

    /*
     * 新增菜品和口味
     * */
    //标记需要事务管理的方法或类
    @Transactional
    @Override
    public void saveDishAndFlavors(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //插入菜品
        dishMapper.insert(dish);
        //插入口味
        //获取Insert生成的主键值
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()){
            for (DishFlavor dishFlavor : flavors) {
                dishFlavor.setDishId(id);
            }
            dishFlavorsMapper.insertBatch(flavors);
        }

    }
}
