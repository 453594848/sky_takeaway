package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

public interface ShopCartService {
    /*
     * 购物车添加商品
     * */
    void add(ShoppingCartDTO shoppingCartDTO);
}
