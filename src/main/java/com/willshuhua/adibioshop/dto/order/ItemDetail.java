package com.willshuhua.adibioshop.dto.order;

import com.willshuhua.adibioshop.entity.order.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetail {

    private Map<String, Object> itemMap;
    private List<Map<String, Object>> infoMapList;
}
