package com.TranBaoLong2ws.springboot.E_commerce.Service;

import com.TranBaoLong2ws.springboot.E_commerce.Reponse.OrderResponse;

import java.util.List;

public interface OrderService {

    public void checkOut(String shippingAddress);

    public List<OrderResponse> getMyOrders();

    public OrderResponse getOrderId(Long id);
}
