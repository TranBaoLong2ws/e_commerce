package com.TranBaoLong2ws.springboot.E_commerce.Reponse;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OrderItemResponse {

    private String productName;

    private BigDecimal price;

    private int quantity;

    public OrderItemResponse(OrderItem orderItem){
        this.productName = orderItem.getProduct().getName();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();

    }
}
