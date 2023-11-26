package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "c端购物车接口相关")
public class ShopCartController {

    private final ShopCartService shopCartService;

    public ShopCartController(ShopCartService shopCartService) {
        this.shopCartService = shopCartService;
    }

    @ApiOperation("添加购物车")
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("该商品添加购物车：{}", shoppingCartDTO);
        shopCartService.add(shoppingCartDTO);

        return Result.success();
    }

    @ApiOperation(value = "查看购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        List<ShoppingCart> list = shopCartService.showShoppingCart();
        return Result.success(list);
    }

    @ApiOperation("清空购物车")
    @DeleteMapping("/clean")
    public Result delete() {
        log.info("用户清空购物车");
        shopCartService.deleteByUserId();
        return Result.success();
    }

    @ApiOperation("删除购物车中的一个商品")
    @PostMapping("/sub")
    public Result sub(ShoppingCartDTO shoppingCartDTO) {
        shopCartService.sub(shoppingCartDTO);
        return Result.success();
    }

}
