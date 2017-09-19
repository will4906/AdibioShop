package com.willshuhua.adibioshop.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOrder {

    private long row_id;
    private String customer_id;
    private String order_id;
    private BigDecimal price;
    private String status;
    private String description;
    private Date create_time;

    private List<OrderPreview> orderPreviewList;
}
