package be.kdg.keepdishesgoing.order.adapter.out.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.domain.vo.BasketId;
import be.kdg.keepdishesgoing.order.port.out.basket.LoadBasketPort;
import be.kdg.keepdishesgoing.order.port.out.basket.UpdateBasketPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Slf4j
@Repository
public class BasketJpaAdapter implements UpdateBasketPort, LoadBasketPort {

    private final BasketJpaRepository basketJpaRepository;

    public BasketJpaAdapter(BasketJpaRepository basketJpaRepository) {
        this.basketJpaRepository = basketJpaRepository;
    }

    @Override
    @Transactional
    public Basket saveBasket(Basket basket) {
        BasketJpaEntity entity = basketJpaRepository.findById(basket.getBasketId().basketId())
                .orElseGet(() -> BasketJpaMapper.toJpaEntity(basket));

        BasketJpaMapper.updateFromDomain(entity, basket);

        log.info("Saving basket: {} {}", basket.getFirstName(), basket.getLastName());

        basketJpaRepository.save(entity);
        return basket;
    }

    @Override
    @Transactional
    public void deleteBasketById(BasketId basketId) {
        basketJpaRepository.deleteById(basketId.basketId());
    }

    @Override
    public Basket findBasketById(UUID id) {
        return basketJpaRepository.findById(id)
                .map(BasketJpaMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Basket not found: " + id));
    }

//    @Override
//    public Basket findBasketByRestaurantId(UUID restaurantId) {
//        return basketJpaRepository.findByRestaurantId(restaurantId)
//                .map(BasketJpaMapper::toDomain)
//                .orElse(null);
//    }

    @Override
    public Basket findBasketByOrderId(UUID orderId) {
        return basketJpaRepository.findByOrderId(orderId)
                .map(BasketJpaMapper::toDomain)
                .orElse(null);
    }

    @Override
    public Basket findBasketByRestaurantIdAndAnonymousId(UUID restaurantId, UUID anonymousId) {
        return basketJpaRepository.findByRestaurantIdAndAnonymousId(restaurantId, anonymousId)
                .map(BasketJpaMapper::toDomain)
                .orElse(null);
    }
}
