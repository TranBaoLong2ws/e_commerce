package com.TranBaoLong2ws.springboot.E_commerce.Service;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.Cart;
import com.TranBaoLong2ws.springboot.E_commerce.Entity.CartItem;
import com.TranBaoLong2ws.springboot.E_commerce.Entity.Product;
import com.TranBaoLong2ws.springboot.E_commerce.Entity.User;
import com.TranBaoLong2ws.springboot.E_commerce.Reponse.CartItemReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.CartItemRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.CartRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.ProductRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Utils.FinAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    private final FinAuthenticatedUser finAuthenticatedUser;

    private final CartItemRepository cartItemRepository;

    private  final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, FinAuthenticatedUser finAuthenticatedUser, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.finAuthenticatedUser = finAuthenticatedUser;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addToCart(Long id, int quantity) {
        User user = finAuthenticatedUser.getAuthenticatedUser();

        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseGet(()->{
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCart_IdAndProduct_Id(cart.getId(), id)
                .orElse(null);

        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemReponse> getCart() {
        User user = finAuthenticatedUser.getAuthenticatedUser();

        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cart.getItems().stream()
                .map(this::convertCartItem)
                .toList();
    }

    @Override
    @Transactional
    public void deleteCart(){
        User user = finAuthenticatedUser.getAuthenticatedUser();

        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(()-> new RuntimeException("Cart not found"));

        cart.getItems().clear();

        cartRepository.save(cart);
    }

    private CartItemReponse convertCartItem(CartItem cartItem){
        return new CartItemReponse(
                cartItem.getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity()
        );
    }

}
