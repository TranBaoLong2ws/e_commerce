package com.TranBaoLong2ws.springboot.E_commerce.Reponse;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CartItemReponse {

    private long id;

    private String productName;

    private BigDecimal price;

    private int quantity;


    public CartItemReponse(long id, String productName, BigDecimal price, int quantity) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}
