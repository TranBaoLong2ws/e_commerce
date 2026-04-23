package com.TranBaoLong2ws.springboot.E_commerce.Controller;


import com.TranBaoLong2ws.springboot.E_commerce.Reponse.CartItemReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart REST API", description = "Cart manager")
@RestController
@RequestMapping("api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Add to cart", description = "Add product into cart")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam int quantity
    ){
        cartService.addToCart(productId,quantity);
        return "Add success";
    }

    @Operation(summary = "Get all cart",description = "Get all cart item")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CartItemReponse> getCartItems(){
        return cartService.getCart();
    }

    @Operation(summary = "Delete cart", description = "Delete items in cart")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteCart(){
        cartService.deleteCart();
    }
}
