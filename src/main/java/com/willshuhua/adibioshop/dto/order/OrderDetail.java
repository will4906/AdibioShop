package com.willshuhua.adibioshop.dto.order;

import com.willshuhua.adibioshop.entity.order.Order;
import com.willshuhua.adibioshop.entity.order.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    private Order order;
    private List<OrderEvent> orderEventList;
    private List<ItemDetail> itemDetailList;
}
