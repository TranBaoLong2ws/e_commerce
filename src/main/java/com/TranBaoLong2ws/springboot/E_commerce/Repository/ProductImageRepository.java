package com.TranBaoLong2ws.springboot.E_commerce.Repository;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {
}
