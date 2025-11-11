package be.kdg.keepdishesgoing.restaurant.adapter.in;

import be.kdg.keepdishesgoing.restaurant.adapter.in.request.CreateDishRequest;
import be.kdg.keepdishesgoing.restaurant.adapter.in.request.UpdateDishRequest;
import be.kdg.keepdishesgoing.restaurant.adapter.in.response.DishDto;
import be.kdg.keepdishesgoing.restaurant.adapter.in.response.DishDtoMapper;
import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/restaurant_owner/dishes")
public class DishController {
    
    private static final Logger log = LoggerFactory.getLogger(DishController.class);
    
    private final CreateDishUseCase createDishUseCase;
    private final PublishDishToMenuUseCase publishDishToMenuUseCase;
    private final UnpublishDishUseCase unpublishDishUseCase;
    private final ChangeDishStatusUseCase changeDishStatusUseCase;
    private final UpdateDishUseCase updateDishUseCase;
    private final FindDishesUseCase findDishesUseCase;

    public DishController(CreateDishUseCase createDishUseCase, PublishDishToMenuUseCase publishDishToMenuUseCase,
                          UnpublishDishUseCase unpublishDishUseCase,
                          ChangeDishStatusUseCase changeDishStatusUseCase,
                          UpdateDishUseCase updateDishUseCase,
                          FindDishesUseCase findDishesUseCase
    ) {
        this.createDishUseCase = createDishUseCase;
        this.publishDishToMenuUseCase = publishDishToMenuUseCase;
        this.unpublishDishUseCase = unpublishDishUseCase;
        this.changeDishStatusUseCase = changeDishStatusUseCase;
        this.updateDishUseCase = updateDishUseCase;
        this.findDishesUseCase = findDishesUseCase;
    }

    @GetMapping
    public ResponseEntity<List<DishDto>> getDishesByRestaurantId(@RequestParam UUID restaurantId) {
        List<Dish> dishes = findDishesUseCase.findDishesByRestaurantId(restaurantId);

        return ResponseEntity.ok(
                dishes
                        .stream()
                        .map(DishDtoMapper::toDto).toList()
        );
    }
    
    @PostMapping
    public ResponseEntity<DishDto> createDish(@RequestBody CreateDishRequest createDishRequest) {
        CreateDishCommand createDishCommand = new CreateDishCommand(
                createDishRequest.name(),
                createDishRequest.typeOfDish(),
                createDishRequest.foodTags(),
                createDishRequest.description(),
                createDishRequest.price(),
                createDishRequest.picture(),
                createDishRequest.dishStatus(),
                createDishRequest.restaurantId()
        );
        
        log.info(createDishCommand.foodTags().toString());
        Dish dish = createDishUseCase.createDish(createDishCommand);
        return ResponseEntity.ok(DishDtoMapper.toDto(dish));
    }
    
    
    @PatchMapping
    public ResponseEntity<DishDto> updateDish(@RequestBody UpdateDishRequest updateDishRequest) {
        UpdateDishCommand updateDishCommand = new UpdateDishCommand(
                updateDishRequest.dishId(),
                updateDishRequest.name(), 
                updateDishRequest.typeOfDish(), 
                updateDishRequest.foodTags(), 
                updateDishRequest.description(), 
                updateDishRequest.price(), 
                updateDishRequest.picture(), 
                updateDishRequest.dishStatus(), 
                updateDishRequest.restaurantId() 
        );
        Dish dish = updateDishUseCase.updateDish(updateDishCommand);
        
        return ResponseEntity.ok(DishDtoMapper.toDto(dish));
        
    }

    @PostMapping("{dishId}/publish-to-menu/{menuId}")
    public ResponseEntity<DishDto> publishDishToMenu(
            @PathVariable UUID dishId,
            @PathVariable UUID menuId) {

        PublishDishToMenuCommand command = new PublishDishToMenuCommand(dishId, menuId);
        Dish publishedDish = publishDishToMenuUseCase.publishDishToFoodMenu(command);

        return ResponseEntity.ok(DishDtoMapper.toDto(publishedDish));
    }
    
    @PatchMapping("{dishId}/unpublish-from-menu")
    public ResponseEntity<DishDto> unpublishDishToMenu(
            @PathVariable UUID dishId) {

        UnpublishDishCommand command = new UnpublishDishCommand(dishId);
        Dish unpublishedDish = unpublishDishUseCase.unpublishDish(command);

        return ResponseEntity.ok(DishDtoMapper.toDto(unpublishedDish));
    }
    
    @PatchMapping("{dishId}/mark-dish-out-of-stock")
    public ResponseEntity<DishDto> markDishOutOfStock(
            @PathVariable UUID dishId
    ){
        ChangeDishStatusCommand command = new ChangeDishStatusCommand(dishId);
        Dish dishOutOfStock  = changeDishStatusUseCase.markDishOutOfStock(command);
        
        return ResponseEntity.ok(DishDtoMapper.toDto(dishOutOfStock));
    }
    
    @PatchMapping("{dishId}/mark-dish-in-stock")
    public ResponseEntity<DishDto> markDishInStock(
            @PathVariable UUID dishId
    ){
        ChangeDishStatusCommand command = new ChangeDishStatusCommand(dishId);
        Dish dishInStock  = changeDishStatusUseCase.markDishInStock(command);
        
        return ResponseEntity.ok(DishDtoMapper.toDto(dishInStock));
    }
    
    @PostMapping("apply-pending-changes")
    public ResponseEntity<List<DishDto>> acceptAllChanges(){
        List<Dish> dishes = updateDishUseCase.acceptAllChanges();
        
        return ResponseEntity.ok(
                        dishes
                        .stream()
                        .map(DishDtoMapper::toDto).toList()
        );
        
    }

}
