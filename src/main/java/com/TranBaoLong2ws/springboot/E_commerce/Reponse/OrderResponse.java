package com.TranBaoLong2ws.springboot.E_commerce.Reponse;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class OrderResponse {

    private long id;

    private BigDecimal priceTotal;

    private String status;

    private List<OrderItemResponse> orderItems;

    public OrderResponse(long id, BigDecimal priceTotal, String status, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.priceTotal = priceTotal;
        this.status = status;
        this.orderItems = orderItems;
    }
}
