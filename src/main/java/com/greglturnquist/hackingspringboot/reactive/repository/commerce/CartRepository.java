package com.greglturnquist.hackingspringboot.reactive.repository.commerce;

import com.greglturnquist.hackingspringboot.reactive.domain.commerce.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartRepository extends ReactiveCrudRepository<Cart, String> {
}
