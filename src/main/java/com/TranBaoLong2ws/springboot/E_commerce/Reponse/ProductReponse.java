package com.TranBaoLong2ws.springboot.E_commerce.Reponse;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class ProductReponse {

    private long id;

    private String name;

    private BigDecimal Price;

    private int stock;

    private String description;

    private Date createDate;


    public ProductReponse(long id, String name, BigDecimal price, int stock, String description, Date createDate) {
        this.id = id;
        this.name = name;
        this.Price = price;
        this.stock = stock;
        this.description = description;
        this.createDate = createDate;
    }

}
