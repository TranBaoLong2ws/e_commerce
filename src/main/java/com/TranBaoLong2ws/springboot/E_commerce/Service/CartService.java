package com.TranBaoLong2ws.springboot.E_commerce.Service;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.CartItem;
import com.TranBaoLong2ws.springboot.E_commerce.Reponse.CartItemReponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {

    public void addToCart(Long id, int quantity);

    public List<CartItemReponse> getCart();

    public void deleteCart();
}
