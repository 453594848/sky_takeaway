package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShopCartMapper;
import com.sky.service.ShopCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ShopCartServiceImpl implements ShopCartService {
    private final ShopCartMapper shopCartMapper;
    private final DishMapper dishMapper;
    private final SetmealMapper setmealMapper;

    public ShopCartServiceImpl(ShopCartMapper shopCartMapper, DishMapper dishMapper, SetmealMapper setmealMapper) {
        this.shopCartMapper = shopCartMapper;
        this.dishMapper = dishMapper;
        this.setmealMapper = setmealMapper;
    }

    /*
     * 购物车添加商品
     * */
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //判断商品是否存在
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        //通过threadLocal获取userID
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shopCartMapper.list(shoppingCart);
        //存在，数量加一
        if (!list.isEmpty()) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shopCartMapper.updateNumberById(cart);
        } else {
            //不存在，插入数据
            Long dishId = shoppingCartDTO.getDishId();
            //判断是菜品还是套餐
            if (dishId != null) {
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
            }
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setNumber(1);
            shopCartMapper.insert(shoppingCart);
        }


    }

    /*
     * 查看购物车
     * */
    @Override
    public List<ShoppingCart> showShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();

        return shopCartMapper.list(shoppingCart);
    }

    /*
     * 清空购物车
     * */
    @Override
    public void deleteByUserId() {

        Long userId = BaseContext.getCurrentId();
        shopCartMapper.deleteByUserId(userId);
    }

    /*
     * 删除购物车中的一个商品
     * */
    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shopCartMapper.list(shoppingCart);
        if (!list.isEmpty()) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() - 1);
            shopCartMapper.updateNumberById(cart);
            if (cart.getNumber() == 0) {
                shopCartMapper.deleteByDishIdOrSetmealId(cart);
            }
        }

    }
}
