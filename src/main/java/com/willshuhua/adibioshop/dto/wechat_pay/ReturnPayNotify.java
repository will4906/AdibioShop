package com.willshuhua.adibioshop.dto.wechat_pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Root(name = "xml")
public class ReturnPayNotify {

    @Element(name = "return_code", data = true)
    private String return_code;
    @Element(name = "return_msg", data = true)
    private String return_msg;

}
