package be.kdg.keepdishesgoing.order.adapter.in;

import be.kdg.keepdishesgoing.order.adapter.in.response.dish.DishDto;
import be.kdg.keepdishesgoing.order.adapter.in.response.dish.DishDtoMapper;
import be.kdg.keepdishesgoing.order.domain.DishProjection;
import be.kdg.keepdishesgoing.order.port.in.dish.FindDishesProjectionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishProjectionController {
    
    private final FindDishesProjectionUseCase findDishesProjectionUseCase;
    
    public DishProjectionController(FindDishesProjectionUseCase findDishesProjectionUseCase) {
        this.findDishesProjectionUseCase = findDishesProjectionUseCase;
    }
    
    @GetMapping("/by-type/{type}")
    public List<DishProjection> loadAllByType(
            @PathVariable String type) {
        return findDishesProjectionUseCase.findAllByType(type);
    }
    
    @GetMapping("/by-food-tag/{foodTag}")
    public List<DishProjection> loadAllByFoodTag(
            @PathVariable String foodTag){
        return findDishesProjectionUseCase.findAllByFoodTag(foodTag);
    }

    @GetMapping
    public ResponseEntity<List<DishDto>> getDishes(
            @RequestParam(required = false, defaultValue = "name") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String type, 
            @RequestParam(required = false) String foodTag 
    ) {
        List<DishDto> dishes = findDishesProjectionUseCase.findAllDishesSortedAndFiltered(
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
    
}
