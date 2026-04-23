package com.TranBaoLong2ws.springboot.E_commerce.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "tbl_order")
@Entity
@Setter
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private  String status;

    @Column(nullable = false)
    private String  shippingAddress;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createAt;

    public Order(){}

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    private List<OrderItem> items;

    public void addOrderItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
