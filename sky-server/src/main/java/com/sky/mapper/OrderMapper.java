package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    /*
     * 插入数据
     * */
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber
     */
    @Select("select * from sky_take_out.orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);



    /*
     * 根据用户ID查询订单
     * */

 /*   Page<Orders> getByUserId(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderDetail getByOrderId(Long id);*/

    /**
     * 分页条件查询并按下单时间排序
     *
     * @param ordersPageQueryDTO
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单
     *
     * @param id
     */
    @Select("select * from sky_take_out.orders where id=#{id}")
    Orders getById(Long id);

    /*
     * 取消订单
     * */
    @Delete("delete from sky_take_out.orders where id=#{id}")
    void deleteByOrderId(Long id);

    /**
     * 根据状态统计订单数量
     *
     * @param status
     */
    @Select("select count(id) from sky_take_out.orders where status = #{status}")
    Integer countStatus(Integer status);

    /*
     * 查询超时未付款账单
     * */
    @Select("select * from sky_take_out.orders where status= #{pendingPayment} and order_time< #{localDateTime}")
    List<Orders> getBYStatusAndOrderTimeLT(Integer pendingPayment, LocalDateTime localDateTime);


    /*
     * 查询已完成订单营业额
     * */
    @Select("select sum(amount) from sky_take_out.orders where status =5 and order_time between #{begin} and #{end}")
    Double getByStatusAndDateTime(LocalDateTime begin, LocalDateTime end, int i);

    /*
     * 根据日期查找总客人数
     * */
    @Select("select COUNT(user_id) from sky_take_out.orders where status =5 and order_time between #{begin} and #{end}")
    Integer UserSumGetByDateTime(LocalDate begin, LocalDate end, int i);
    /**
     * 根据动态条件统计订单数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * 统计指定时间区间内的销量排名前10
     * @param begin
     * @param end
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
