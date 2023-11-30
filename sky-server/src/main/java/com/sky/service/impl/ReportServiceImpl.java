package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.vo.TurnoverReportVO;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service

public class ReportServiceImpl implements com.sky.service.ReportService {
    private final OrderMapper orderMapper;

    public ReportServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /**
     * 获取营业额
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        List<Double> turnoverList = new ArrayList<>();
        if (begin.isAfter(end)) {
            throw new RuntimeException(MessageConstant.UNKNOWN_ERROR);
        } else {
            dateList.add(begin);
            while (!begin.equals(end)) {
                begin = begin.plusDays(1);
                dateList.add(begin);
            }
            for (LocalDate localDate : dateList) {
                Double CountPrice = orderMapper.getByStatusAndDateTime(LocalDateTime.of(localDate, LocalTime.MIN),
                        LocalDateTime.of(localDate, LocalTime.MAX), 5);
                CountPrice= CountPrice==null ? 0.0 : CountPrice;
                turnoverList.add(CountPrice);
            }
            return TurnoverReportVO.builder()
                    .dateList(StringUtil.join(",", dateList))
                    .turnoverList(StringUtil.join(",", turnoverList))
                    .build();
        }
    }
}
