package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/*
 * 定时任务类
 * */
@Component
@Slf4j
public class OrderTask {

    /*    @Scheduled(cron = "0/5 * * * * ?")
        public void testTask() {
            log.info("定时任务测试执行：{}", new Date());
        }*/
    private final OrderMapper orderMapper;

    public OrderTask(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Scheduled(cron = "0 * * * * ?")//每分钟执行一次
    public void processTimeoutOrder() {
        log.info("定时清除过时订单:{}", LocalDateTime.now());
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(15);
        List<Orders> list = orderMapper.getBYStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, localDateTime);

        if (list != null && !list.isEmpty()) {
            for (Orders orders : list) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }


    }

    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨一点执行
    public void processDeliverOrder() {
        log.info("定时处理过时派送中订单:{}", LocalDateTime.now());
        LocalDateTime localDateTime = LocalDateTime.now().minusHours(1);
        List<Orders> list = orderMapper.getBYStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, localDateTime);
        if (list != null && !list.isEmpty()) {
            for (Orders orders : list) {
                orders.setStatus(Orders.COMPLETED);
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }
}
