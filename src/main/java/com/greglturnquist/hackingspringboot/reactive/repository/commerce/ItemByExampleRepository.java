package com.greglturnquist.hackingspringboot.reactive.repository.commerce;

import com.greglturnquist.hackingspringboot.reactive.domain.commerce.Item;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface ItemByExampleRepository extends ReactiveQueryByExampleExecutor<Item> {
}
