package com.greglturnquist.hackingspringboot.reactive.repository.commerce;

import com.greglturnquist.hackingspringboot.reactive.domain.commerce.Item;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ItemRepository extends ReactiveCrudRepository<Item,String>, ReactiveQueryByExampleExecutor<Item> {
    Flux<Item> findByNameContaining(String partialName);

    @Query("{'name': ?0, 'age': ?1 }")
    Flux<Item> findItemsForCustomerMonthlyReport(String name, int age);

    @Query(" {'age' : -1}")
    Flux<Item> findSortedStuffForWeeklyReport();
}
