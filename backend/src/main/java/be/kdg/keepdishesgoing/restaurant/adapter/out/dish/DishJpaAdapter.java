package be.kdg.keepdishesgoing.restaurant.adapter.out.dish;

import be.kdg.keepdishesgoing.restaurant.domain.DishStatus;
import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.domain.FoodTag;
import be.kdg.keepdishesgoing.restaurant.domain.TypeOfDish;
import be.kdg.keepdishesgoing.restaurant.domain.vo.DishId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.LoadDishesPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.UpdateDishPort;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DishJpaAdapter implements LoadDishesPort, UpdateDishPort{

    private static final Logger log = LoggerFactory.getLogger(DishJpaAdapter.class);
    private final DishJpaRepository dishJpaRepository;

    public DishJpaAdapter(DishJpaRepository dishJpaRepository) {
        this.dishJpaRepository = dishJpaRepository;
    }

    @Override
    public List<Dish> loadDishesByMenu(UUID foodMenuId) {
        return dishJpaRepository.findByFoodMenuId(foodMenuId)
                .stream()
                .map(entity -> Dish.rehydrate(
                        DishId.of(entity.getId()),
                        entity.getName(),
                        TypeOfDish.valueOf(entity.getTypeOfDish().name()),
                        entity.getFoodTags()
                                .stream()
                                .map(tag -> FoodTag.valueOf(tag.getTag().toString()))                                
                                .toList(),
                        entity.getDescription(),
                        entity.getPrice(),
                        entity.getPicture(),
                        entity.getDishStatus(),
                        RestaurantId.of(entity.getRestaurantId())
                ))
                .toList();
    }

    @Override
    public Dish loadDishById(UUID dishId) {
        return dishJpaRepository.findById(dishId)
                .map(DishJpaMapper::toDomain)
                .orElseThrow(() -> new EntityNotFoundException("DishProjection not found: " + dishId));
    }

    @Override
    public Dish saveDish(Dish dish) {
        DishJpaEntity entity = DishJpaMapper.fromDomain(dish);
        log.info(entity.toString());
        DishJpaEntity savedEntity = dishJpaRepository.save(entity);

        return DishJpaMapper.toDomain(savedEntity);
    }

    @Override
    public List<Dish> loadAllDishesDraft() {
        return dishJpaRepository.findDishJpaEntitiesByDishStatus(DishStatus.DRAFT)
                .stream()
                .map(DishJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Dish> loadAllDishesByRestaurantId(UUID restaurantId) {
        return dishJpaRepository.findAllByRestaurantIdAndFetchTags(restaurantId)
                .stream()
                .map(DishJpaMapper::toDomain)
                .toList();
    }
}
