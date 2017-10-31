package com.willshuhua.adibioshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Share {

    private String share_id;
    private String from_id;
    private String order_id;

}
