package com.greglturnquist.hackingspringboot.reactive;

import com.greglturnquist.hackingspringboot.reactive.domain.commerce.Cart;
import com.greglturnquist.hackingspringboot.reactive.domain.commerce.CartItem;
import com.greglturnquist.hackingspringboot.reactive.domain.commerce.Item;
import com.greglturnquist.hackingspringboot.reactive.repository.commerce.CartRepository;
import com.greglturnquist.hackingspringboot.reactive.repository.commerce.ItemRepository;
import com.greglturnquist.hackingspringboot.reactive.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.assertj.core.api.Assertions.assertThat;
import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
class InventoryServiceUnitTest {
    InventoryService inventoryService; // 테스트 대상클래스

    @MockBean private ItemRepository itemRepository; // 협력자 (테스트대상이 아님)

    @MockBean private CartRepository cartRepository; // 협려자 (테스트대상이 아님)

    @BeforeEach
    void setUp() {
        // 테스트 데이터 정의
        Item sampleItem = new Item("item1","TV tray","Alf TV tray",19.99);
        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

        // 협력자와의 상호작용 정의
        when(cartRepository.findById(anyString())).thenReturn(Mono.empty());
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

        inventoryService = new InventoryService(itemRepository, cartRepository);
    }

    @Test
    void addItemToEMptyCartShouldProduceOneCartItem() {
        inventoryService.addItemToCart("My Cart","item1")
                .as(StepVerifier::create)
                .expectNextMatches(cart->{
                    assertThat(cart.getCartItems()).extracting(CartItem::getQuantity)
                            .containsExactlyInAnyOrder(1);

                    assertThat(cart.getCartItems()).extracting(CartItem::getItem)
                            .containsExactly(new Item("item1","TV tray", "Alf TV tray", 19.99));

                    return true;
                })
                .verifyComplete();
    }
}
