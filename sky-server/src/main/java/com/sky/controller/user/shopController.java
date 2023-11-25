package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags = "店铺相关接口")
public class shopController {
    private final RedisTemplate redisTemplate;

    public shopController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @ApiOperation("获取店铺状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取店铺状态:{}", shopStatus == 1 ? "营业中" : "打烊中");
        return Result.success(shopStatus);
    }

}
