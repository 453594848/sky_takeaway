package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DishServiceImpl implements DishService {
    private final DishMapper dishMapper;
    private final DishFlavorsMapper dishFlavorsMapper;
    private final SetmealDishMapper setmealDishMapper;


    public DishServiceImpl(DishMapper dishMapper, DishFlavorsMapper dishFlavorsMapper, SetmealDishMapper setmealDishMapper) {
        this.dishMapper = dishMapper;
        this.dishFlavorsMapper = dishFlavorsMapper;
        this.setmealDishMapper = setmealDishMapper;
    }

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
        if (flavors != null && !flavors.isEmpty()) {
            for (DishFlavor dishFlavor : flavors) {
                dishFlavor.setDishId(id);
            }
            dishFlavorsMapper.insertBatch(flavors);
        }

    }
    /*
     * 菜品分页查询
     * */

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        try (Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO)) {
            return new PageResult(page.getTotal(), page.getResult());
        }
    }

    /*
     * 菜品删除
     * */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //判断菜品是否是起售状态
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE))
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }
        //判断菜品被套餐关联
        List<Long> setmealIdByDishID = setmealDishMapper.getSetmealIdByDishID(ids);
        if (setmealIdByDishID != null && !setmealIdByDishID.isEmpty())
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        //删除菜品数据
        for (Long id : ids) {
            dishMapper.deleteByID(id);
            //删除菜品管理口味数据
            dishFlavorsMapper.deleteByDishID(id);
        }
    }

    /*
     *根据ID查询员工
     * */
    @Override
    public DishVO getById(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> dishFlavors = dishFlavorsMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /*
     * 修改菜品
     * */
    @Transactional
    @Override
    public void updateAndFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        dishFlavorsMapper.deleteByDishID(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            for (DishFlavor dishFlavor : flavors) {
                dishFlavor.setDishId(dishDTO.getId());
            }
            dishFlavorsMapper.insertBatch(flavors);
        }

    }

    /*
     * 菜品起售、停售
     * */
    @Override
    public void startOrStop(Integer status, Long id) {
        Dish build = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(build);
    }

    /*
     * 根据分类id查询菜品
     * */
    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }


    /**
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d, dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorsMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }
}
