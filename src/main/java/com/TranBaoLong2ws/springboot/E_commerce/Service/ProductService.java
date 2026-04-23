package com.TranBaoLong2ws.springboot.E_commerce.Service;


import com.TranBaoLong2ws.springboot.E_commerce.Reponse.ProductReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;


public interface ProductService {

    public Page<ProductReponse> getAll(int page, int size);

    public ProductReponse getById(long id);

    public void createProduct(ProductRequest productRequest) ;

    public void deleteProduct(long id);

    public Page<ProductReponse> searchProduct(String name, BigDecimal minPrice, BigDecimal maxPrice, int page, int size);

    public void updateProduct(long id,ProductRequest productRequest);
}
