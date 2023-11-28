package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShopCartMapper {
    /*
     * 动态查找购物车
     * */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /*
     * 修改数量
     * */
    @Update("update sky_take_out.shopping_cart set number =#{number} where id=#{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /*
     * 插入商品
     * */
    @Insert("insert into sky_take_out.shopping_cart" +
            " (name, image, user_id, dish_id, setmeal_id, " +
            "dish_flavor,amount, create_time) values " +
            "(#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{amount},#{createTime})")
    void insert(ShoppingCart shoppingCart);

    /*
     * 清空购物车
     * */
    @Delete("delete from sky_take_out.shopping_cart where user_id=#{userId}")
    void deleteByUserId(Long userId);
/*
* 减少商品
* */
    void deleteByDishIdOrSetmealId(ShoppingCart shoppingCart);
    /**
     * 批量插入购物车数据
     *
     * @param shoppingCartList
     */
    void insertBatch(List<ShoppingCart> shoppingCartList);
}
