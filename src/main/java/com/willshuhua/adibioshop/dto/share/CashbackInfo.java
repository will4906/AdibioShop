package com.willshuhua.adibioshop.dto.share;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashbackInfo {

    private String zhifubao_account;
    private String bank_card_number;
}
