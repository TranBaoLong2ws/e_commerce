package com.TranBaoLong2ws.springboot.E_commerce.Reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryReponse {

    private String name;

    private String description;

    public CategoryReponse(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
