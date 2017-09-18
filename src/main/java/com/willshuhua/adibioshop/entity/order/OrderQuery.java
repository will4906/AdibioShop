package com.willshuhua.adibioshop.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderQuery {

    private long row_id;
    private int limit;
    private String customer_id;
    private String order_id;
    private String status;
}
