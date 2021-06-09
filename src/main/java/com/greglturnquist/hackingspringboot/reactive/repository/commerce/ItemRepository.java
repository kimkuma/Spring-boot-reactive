package com.greglturnquist.hackingspringboot.reactive.repository.commerce;

import com.greglturnquist.hackingspringboot.reactive.domain.commerce.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item,String> {
}
