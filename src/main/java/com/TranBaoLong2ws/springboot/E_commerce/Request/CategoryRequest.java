package com.TranBaoLong2ws.springboot.E_commerce.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequest {

    private long id;

    private String categoryName;

    private String description;

    public CategoryRequest(long id, String categoryName, String description) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
    }
}
