package com.TranBaoLong2ws.springboot.E_commerce.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "Products")
@Entity
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal Price;

    @Column(nullable = false)
    private int stock;


    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "catogory_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImages> productImages;


    public Product(long id, String name, String description, BigDecimal price, int stock, Date createdAt, Category category, List<ProductImages> productImages) {
        this.id = id;
        this.name = name;
        this.description = description;
        Price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.category = category;
        this.productImages = productImages;
    }


    public Product() {
    }
}
