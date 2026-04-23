package com.TranBaoLong2ws.springboot.E_commerce.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CategoryCreateResquest {

    private String categoryName;

    private String categoryDescription;


}
