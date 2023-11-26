package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShopCartService {
    /*
     * 购物车添加商品
     * */
    void add(ShoppingCartDTO shoppingCartDTO);
/*
* 查看购物车
* */
    List<ShoppingCart> showShoppingCart();
/*
* 清空购物车
* */
    void deleteByUserId();
/*
* 删除购物车中的一个商品
* */
    void sub(ShoppingCartDTO shoppingCartDTO);
}
