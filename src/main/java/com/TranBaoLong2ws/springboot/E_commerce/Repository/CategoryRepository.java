package com.TranBaoLong2ws.springboot.E_commerce.Repository;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findById(Long id);

    Page<Category> findByNameContaining(String name, Pageable pageable);

    Optional<Category> findByName(String categoryName);
}
