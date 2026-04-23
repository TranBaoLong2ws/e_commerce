package com.TranBaoLong2ws.springboot.E_commerce.Repository;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.Category;
import com.TranBaoLong2ws.springboot.E_commerce.Entity.Product;
import com.TranBaoLong2ws.springboot.E_commerce.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long aLong);

    @Query("""
    SELECT p FROM Product p
    WHERE (:name IS NULL OR :name = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
    AND (:minPrice IS NULL OR p.Price >= :minPrice)
    AND (:maxPrice IS NULL OR p.Price <= :maxPrice)
    """)
    Page<Product> searchProducts(
            @Param("name") String name,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );
}
