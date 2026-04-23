package com.TranBaoLong2ws.springboot.E_commerce.Controller;

import com.TranBaoLong2ws.springboot.E_commerce.Reponse.OrderResponse;
import com.TranBaoLong2ws.springboot.E_commerce.Service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order REST API", description = "Order manager")
@RestController
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Checkout cart", description = "Add order")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/checkout")
    public String checkout (@RequestParam String address){
        orderService.checkOut(address);
        return "Order placed successfully!";
    }

    @Operation(summary = "Get all order", description = "List order")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<OrderResponse> getMyOrders(){
        return orderService.getMyOrders();
    }


    @Operation(summary = "Get order by id", description = "Detail order")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public OrderResponse getOrderId(@PathVariable Long id){
        return orderService.getOrderId(id);
    }


}
