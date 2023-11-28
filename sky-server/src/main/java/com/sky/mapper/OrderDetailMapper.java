package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    /*
     * 批量插入订单明细
     * */
    void insertBatch(List<OrderDetail> orderDetails);
    /*
     * 根据订单ID查询
     * */
/*@Select("select * from sky_take_out.order_detail where order_id=#{id}")

    List<OrderDetail> getByOrderId(Long id);*/

    /**
     * 根据订单id查询订单明细
     *
     * @param orderId
     * @return
     */
    @Select("select * from sky_take_out.order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);

    /*
     * 取消订单
     * */
    @Delete("delete from sky_take_out.order_detail where order_id=#{id}")
    void deleteByOrderId(Long id);
}
