package com.greglturnquist.hackingspringboot.reactive.controller;

import com.greglturnquist.hackingspringboot.reactive.domain.commerce.Cart;
import com.greglturnquist.hackingspringboot.reactive.repository.commerce.CartRepository;
import com.greglturnquist.hackingspringboot.reactive.repository.commerce.ItemRepository;
import com.greglturnquist.hackingspringboot.reactive.service.CartService;
import com.greglturnquist.hackingspringboot.reactive.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    private final InventoryService inventoryService;

    private final CartService cartService;

    public HomeController(ItemRepository itemRepository, CartRepository cartRepository, CartService cartService, InventoryService inventoryService) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.inventoryService = inventoryService;
    }

    @GetMapping
    Mono<String> home() {
        return Mono.just("home");
    }

    @GetMapping(value = "/home")
    Mono<Rendering> renderingHome() {
        return Mono.just(Rendering.view("cart_home.html")
                .modelAttribute("items",this.itemRepository.findAll()
                .doOnNext(System.out::println))
                .modelAttribute("cart",this.cartRepository.findById("My Cart")
                        .defaultIfEmpty(new Cart("My cart")))
                .build()
        );
    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart(@PathVariable String id) {
        return inventoryService.addItemToCart("My Cart",id)
                .thenReturn("redirect:/home");
    }

    @GetMapping("/search")
    Mono<Rendering> search(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String description,
                           @RequestParam boolean useAnd) {
        return Mono.just(Rendering.view("cart_home.html")
        .modelAttribute("items",inventoryService.searchByExample(name, description, useAnd))
        .modelAttribute("cart",this.cartRepository.findById("My Cart").defaultIfEmpty(new Cart("My Cart"))).build());
    }
}
