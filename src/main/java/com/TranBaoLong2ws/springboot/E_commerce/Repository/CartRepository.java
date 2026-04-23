package com.TranBaoLong2ws.springboot.E_commerce.Repository;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser_Id(Long userId);

}
