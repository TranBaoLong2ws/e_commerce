package com.TranBaoLong2ws.springboot.E_commerce.Service;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.*;
import com.TranBaoLong2ws.springboot.E_commerce.Reponse.OrderItemResponse;
import com.TranBaoLong2ws.springboot.E_commerce.Reponse.OrderResponse;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.CartRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.OrderRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Utils.FinAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private final FinAuthenticatedUser finAuthenticatedUser;

    private final CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, FinAuthenticatedUser finAuthenticatedUser, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.finAuthenticatedUser = finAuthenticatedUser;
        this.cartRepository = cartRepository;
    }


    @Override
    @Transactional
    public void checkOut(String shippingAddress) {
        User user = finAuthenticatedUser.getAuthenticatedUser();

        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("Pending");
        order.setShippingAddress(shippingAddress);

        // Khởi tạo list rỗng để tránh NullPointerException nếu chưa khởi tạo ở Entity
        order.setItems(new ArrayList<>());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            // Sử dụng helper method để liên kết orderItem với order
            order.addOrderItem(orderItem);

            total = total.add(
                    cartItem.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );
        }

        order.setTotal(total);

        // Lưu Order (CascadeType.ALL sẽ tự động lưu các OrderItem)
        orderRepository.save(order);

        // Xóa giỏ hàng sau khi đặt hàng thành công
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public List<OrderResponse> getMyOrders() {
        User user = finAuthenticatedUser.getAuthenticatedUser();

        return orderRepository.findByUser_Id(user.getId())
                .stream().map(this::convertoOrder)
                .toList();
    }

    @Override
    public OrderResponse getOrderId(Long id) {

        User user = finAuthenticatedUser.getAuthenticatedUser();

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getUser() == null || user == null) {
            throw new RuntimeException("Invalid user");
        }

        return convertoOrder(order);
    }

    private OrderResponse convertoOrder( Order order) {

        List<OrderItemResponse> itemResponses = order.getItems()
                .stream()
                .map(OrderItemResponse::new)
                .toList();

        return  new OrderResponse(
                order.getId(),
                order.getTotal(),
                order.getStatus(),
                itemResponses
        );
    }
}
