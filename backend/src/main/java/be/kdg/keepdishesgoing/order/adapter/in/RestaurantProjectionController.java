package be.kdg.keepdishesgoing.order.adapter.in;

import be.kdg.keepdishesgoing.order.adapter.in.response.dish.DishDto;
import be.kdg.keepdishesgoing.order.adapter.in.response.dish.DishDtoMapper;
import be.kdg.keepdishesgoing.order.adapter.in.response.restaurant.RestaurantDto;
import be.kdg.keepdishesgoing.order.adapter.in.response.restaurant.RestaurantDtoMapper;
import be.kdg.keepdishesgoing.order.adapter.in.response.restaurant.RestaurantWithMenuDto;
import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;
import be.kdg.keepdishesgoing.order.domain.RestaurantProjection;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.port.in.dish.FindDishesProjectionUseCase;
import be.kdg.keepdishesgoing.order.port.in.restaurant.FindRestaurantProjectionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantProjectionController {

    private final FindRestaurantProjectionUseCase findRestaurantProjectionUseCase;
    private final FindDishesProjectionUseCase findDishesProjectionUseCase;

    public RestaurantProjectionController(FindRestaurantProjectionUseCase findRestaurantProjectionUseCase,
                                          FindDishesProjectionUseCase findDishesProjectionUseCase) {
        this.findRestaurantProjectionUseCase = findRestaurantProjectionUseCase;
        this.findDishesProjectionUseCase = findDishesProjectionUseCase;
    }

    @GetMapping
    public List<RestaurantDto> findAllRestaurants() {
        return findRestaurantProjectionUseCase.findAll()
                .stream()
                .map(RestaurantDtoMapper::toDto)
                .toList();
    }

    @GetMapping("/by-type")
    public ResponseEntity<List<RestaurantDto>> findRestaurantsByType(@RequestParam String restaurantType) {
        var restaurantDtos = findRestaurantProjectionUseCase.findAllByType(
                        CuisineTypeProjection.valueOf(restaurantType.toUpperCase()))
                .stream()
                .map(RestaurantDtoMapper::toDto)
                .toList();

        return restaurantDtos.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(restaurantDtos);
    }

    @GetMapping("/by-price")
    public ResponseEntity<List<RestaurantDto>> findRestaurantsByPrice(@RequestParam String priceRange) {
        var restaurantDtos = findRestaurantProjectionUseCase.findAllByPriceRange(priceRange)
                .stream()
                .map(RestaurantDtoMapper::toDto)
                .toList();

        return restaurantDtos.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(restaurantDtos);
    }

    @GetMapping("/{restaurantId}/dishes")
    public ResponseEntity<List<DishDto>> getDishesForRestaurant(
            @PathVariable UUID restaurantId,
            @RequestParam(required = false, defaultValue = "name") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String foodTag
    ) {
        List<DishDto> dishes = findDishesProjectionUseCase.findAllDishesSortedAndFilteredByRestaurant(
                        RestaurantId.of(restaurantId), 
                        sort,
                        direction,
                        type,
                        foodTag
                )
                .stream()
                .map(DishDtoMapper::toDto)
                .toList();

        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<RestaurantWithMenuDto> findRestaurantWithDishes(@PathVariable UUID restaurantId) {
        RestaurantProjection restaurant = findRestaurantProjectionUseCase.findById(RestaurantId.of(restaurantId));

        // Assuming restaurant has a foodMenuId associated
        var dishes = findDishesProjectionUseCase.findDishesByRestaurant(restaurant.getRestaurantId());
        var dishDtos = dishes.stream()
                .map(dish -> new DishDto(
                        dish.getId().dishId(), 
                        dish.getName(),
                        dish.getTypeOfDish(),
                        dish.getFoodTagList(),
                        dish.getDescription(),
                        dish.getPrice(),
                        dish.getPicture(),
                        dish.getDishStatus()))
                .toList();

        var dto = new RestaurantWithMenuDto(
                restaurant.getRestaurantId().restaurantId(), 
                restaurant.getName(),
                dishDtos,
                restaurant.getEmail().emailAddress(),
                restaurant.getAddress().street(),
                restaurant.getAddress().number(),
                restaurant.getAddress().postalCode(),
                restaurant.getAddress().city(),
                restaurant.getAddress().country(),
                restaurant.getCuisineType(),
                restaurant.getDefaultTimePreparation(),
                restaurant.getPicture().url()
        );

        return ResponseEntity.ok(dto);
    }
}
