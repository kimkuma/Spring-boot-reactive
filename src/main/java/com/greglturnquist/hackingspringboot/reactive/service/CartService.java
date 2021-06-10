package com.greglturnquist.hackingspringboot.reactive.service;

import com.greglturnquist.hackingspringboot.reactive.domain.commerce.Cart;
import com.greglturnquist.hackingspringboot.reactive.domain.commerce.CartItem;
import com.greglturnquist.hackingspringboot.reactive.domain.commerce.Item;
import com.greglturnquist.hackingspringboot.reactive.repository.commerce.CartRepository;
import com.greglturnquist.hackingspringboot.reactive.repository.commerce.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;


    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Mono<Cart> addToCart(String cartId, String id) {
        return this.cartRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem()
                                .getId().equals(id))
                        .findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        })
                        .orElseGet(()->
                            this.itemRepository.findById(id)
                                .map(CartItem::new)
                                .doOnNext(cartItem ->
                                        cart.getCartItems().add(cartItem))
                                .map(cartItem -> cart)))
                .flatMap(this.cartRepository::save);
    }

}
