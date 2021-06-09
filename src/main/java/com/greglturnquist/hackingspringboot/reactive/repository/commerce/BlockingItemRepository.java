package com.greglturnquist.hackingspringboot.reactive.repository.commerce;

import com.greglturnquist.hackingspringboot.reactive.domain.commerce.Item;
import org.springframework.data.repository.CrudRepository;

public interface BlockingItemRepository extends CrudRepository<Item, String> {
}
