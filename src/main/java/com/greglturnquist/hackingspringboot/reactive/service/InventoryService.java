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
public class InventoryService {

    private final ItemRepository itemRepository;

    private final CartRepository cartRepository;

    public InventoryService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    // 검색 요구조건을 Example 쿼리로 구현
    public Flux<Item> searchByExample(String name, String description, boolean useAnd) {
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd ? ExampleMatcher.matchingAll() : ExampleMatcher.matchingAny())
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("price");

        Example<Item> probe = Example.of(item, matcher);

        return itemRepository.findAll(probe);
    }

    public Mono<Cart> addItemToCart(String cartId, String itemId) {
        return this.cartRepository.findById(cartId)
                .log("foundCart")
                .defaultIfEmpty(new Cart(cartId))
                .log("emptyCart")
                .flatMap(cart->cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem()
                            .getId().equals(itemId))
                        .findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart).log("newCartItem");
                        })
                        .orElseGet(()->{
                            return this.itemRepository.findById(itemId)
                                    .log("fetchedItem")
                                    .map(item -> new CartItem(item))
                                    .log("cartItem")
                                    .map(cartItem -> {
                                        cart.getCartItems().add(cartItem);
                                        return cart;
                                    }).log("addedCartItem");
                        }))
                .log("cartWithAnotherItem")
                .flatMap(cart -> this.cartRepository.save(cart))
                .log("savedCart");
    }
}
