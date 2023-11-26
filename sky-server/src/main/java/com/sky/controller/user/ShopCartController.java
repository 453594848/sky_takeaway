package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.result.Result;
import com.sky.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
