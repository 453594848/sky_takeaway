package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
class ShopController {
    private final RedisTemplate redisTemplate;
    public ShopController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PutMapping("/{status}")
    @ApiOperation(value = "设置店铺的营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺的营业状态为:{}",status == 1 ?"营业中":"打烊中");
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        return Result.success();
    }
@ApiOperation("获取店铺状态")
@GetMapping("/status")
    public Result<Integer> getStatus(){
    Integer shopStatus = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
    log.info("获取店铺状态:{}",shopStatus == 1 ?"营业中":"打烊中");
    return Result.success(shopStatus);
    }











}