package be.kdg.keepdishesgoing.restaurant.adapter.in;


import be.kdg.keepdishesgoing.restaurant.adapter.in.response.DishDto;
import be.kdg.keepdishesgoing.restaurant.adapter.in.response.DishDtoMapper;
import be.kdg.keepdishesgoing.restaurant.adapter.in.response.FoodMenuDto;
import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.FindFoodMenuUseCase;
import be.kdg.keepdishesgoing.restaurant.port.in.foodMenu.CreateFoodMenuCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.foodMenu.CreateFoodMenuUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/restaurant_owner/food_menu")
public class FoodMenuController {
    
    private final CreateFoodMenuUseCase createFoodMenuUseCase;
    private final FindFoodMenuUseCase findFoodMenuUseCase;

    public FoodMenuController(CreateFoodMenuUseCase createFoodMenuUseCase,
                              FindFoodMenuUseCase findFoodMenuUseCase
                              ) {
        this.createFoodMenuUseCase = createFoodMenuUseCase;
        this.findFoodMenuUseCase = findFoodMenuUseCase;
    }

    @GetMapping
    public ResponseEntity<List<DishDto>> getFoodMenuDishes(@RequestParam UUID restaurantId) {
        FoodMenu foodMenu = findFoodMenuUseCase.findFoodMenuByRestaurant(RestaurantId.of(restaurantId));
        List<Dish> dishesInMenu = findFoodMenuUseCase.findDishesByFoodMenu(foodMenu.getFoodMenuId());

        return ResponseEntity.ok(
                dishesInMenu.stream()
                        .map(DishDtoMapper::toDto)
                        .toList()
        );    
    }
    
    @GetMapping("{restaurantId}")
    public ResponseEntity<FoodMenuDto> getFoodMenu(@PathVariable UUID restaurantId){
        FoodMenu foodMenu = findFoodMenuUseCase.findFoodMenuByRestaurant(RestaurantId.of(restaurantId));
        FoodMenuDto foodMenuDto = new FoodMenuDto(foodMenu.getFoodMenuId().foodMenuId(), restaurantId);
        log.info("foodMenuDto: {}", foodMenuDto.id());
        return ResponseEntity.ok(foodMenuDto);
    }

    @PostMapping
    public ResponseEntity<FoodMenuDto> createFoodMenu(@RequestBody CreateFoodMenuCommand createFoodMenuCommand) {
        FoodMenu createdFoodMenu = createFoodMenuUseCase.createFoodMenu(createFoodMenuCommand);
        log.info("foodMenu CREATED: {}", createdFoodMenu.getFoodMenuId());

        FoodMenuDto responseDto = new FoodMenuDto(
                createdFoodMenu.getFoodMenuId().foodMenuId(),
                createdFoodMenu.getRestaurantId().restaurantId() 
        );

        return ResponseEntity.ok(responseDto); 
    }
    
}
