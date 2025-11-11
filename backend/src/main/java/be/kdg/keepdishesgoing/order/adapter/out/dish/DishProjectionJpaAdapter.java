package be.kdg.keepdishesgoing.order.adapter.out.dish;

import be.kdg.keepdishesgoing.order.domain.DishProjection;
import be.kdg.keepdishesgoing.order.domain.FoodTagProjection;
import be.kdg.keepdishesgoing.order.domain.TypeOfDishProjection;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.port.out.dish.LoadDishesPort;
import be.kdg.keepdishesgoing.order.port.out.dish.PersistDishPort;
import be.kdg.keepdishesgoing.order.port.out.dish.RemoveDishPort;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.SetJoin;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Repository
public class DishProjectionJpaAdapter implements PersistDishPort, LoadDishesPort, RemoveDishPort {

    private static final Logger log = LoggerFactory.getLogger(DishProjectionJpaAdapter.class);
    private final DishProjectionJpaRepository dishProjectionJpaRepository;

    public DishProjectionJpaAdapter(DishProjectionJpaRepository dishProjectionJpaRepository) {
        this.dishProjectionJpaRepository = dishProjectionJpaRepository;
    }

    @Override
    @Transactional
    public DishProjection save(DishProjection dishProjection) {
        DishProjectionJpaEntity entity = DishProjectionJpaMapper.fromDomain(dishProjection);
        DishProjectionJpaEntity saved = dishProjectionJpaRepository.save(entity);
        log.info("DishProjectionJpaAdapter saved {}", saved);
        return DishProjectionJpaMapper.toDomain(saved);
    }

    @Override
    public DishProjection loadDishById(UUID id) {
        return DishProjectionJpaMapper.toDomain(dishProjectionJpaRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void removeDishById(UUID id) {
        dishProjectionJpaRepository.removeById(id);
    }

    @Override
    public List<DishProjection> loadDishesByRestaurantId(UUID restaurantId) {
        return dishProjectionJpaRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(DishProjectionJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<DishProjection> loadAll() {
        return dishProjectionJpaRepository.findAll()
                .stream()
                .map(DishProjectionJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<DishProjection> loadAllByType(TypeOfDishProjection type) {
        return dishProjectionJpaRepository.findAllByTypeOfDish(type)
                .stream()
                .map(DishProjectionJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<DishProjection> loadAllByFoodTag(FoodTagProjection foodTag) {
        return dishProjectionJpaRepository.findAllByFoodTag(foodTag.name())
                .stream()
                .map(DishProjectionJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<DishProjection> loadAllDishesSorted(String sortField, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        return dishProjectionJpaRepository.findAll(sort).stream()
                .map(DishProjectionJpaMapper::toDomain)
                .toList();
    }
    @Override
    public List<DishProjection> loadAllDishesSortedAndFiltered(String sortField, String direction, String type, String foodTag) {
        // 1. Build the Sort object
        Sort sort = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        // 2. Retrieve ALL dishes from the database, sorted, but NOT filtered.
        // This requires the DishProjectionJpaRepository to implement findAll(Sort).
        List<DishProjection> allDishesSorted = dishProjectionJpaRepository.findAll(sort).stream()
                .map(DishProjectionJpaMapper::toDomain)
                .toList();

        // 3. Start a stream for IN-MEMORY filtering
        Stream<DishProjection> filteredStream = allDishesSorted.stream();

        // 4. Apply Type filter (In-Memory)
        if (type != null && !type.isBlank()) {
            try {
                TypeOfDishProjection typeEnum = TypeOfDishProjection.valueOf(type.toUpperCase());

                filteredStream = filteredStream.filter(dish -> dish.getTypeOfDish().equals(typeEnum));

            } catch (IllegalArgumentException e) {
                log.warn("Invalid dish type filter provided: {}", type);
            }
        }

        // 5. Apply Food Tag filter (In-Memory)
        if (foodTag != null && !foodTag.isBlank()) {
            try {
                // Convert the String filter to the actual Enum type for comparison
                FoodTagProjection tagEnum = FoodTagProjection.valueOf(foodTag.toUpperCase());

                // Filter dishes where the foodTags collection CONTAINS the required tag
                filteredStream = filteredStream.filter(dish -> dish.getFoodTagList().contains(tagEnum));

            } catch (IllegalArgumentException e) {
                log.warn("Invalid food tag filter provided: {}", foodTag);
            }
        }

        // 6. Return the final list
        return filteredStream.toList();
    }

    @Override
    public List<DishProjection> loadAllDishesSortedAndFilteredByRestaurant(RestaurantId restaurantId, String sortField, String direction, String type, String foodTag) {
        List<DishProjection> dishes = loadDishesByRestaurantId(restaurantId.restaurantId());

        Stream<DishProjection> filteredStream = dishes.stream();

        if (type != null && !type.isBlank()) {
            try {
                TypeOfDishProjection typeEnum = TypeOfDishProjection.valueOf(type.toUpperCase());
                filteredStream = filteredStream.filter(dish -> dish.getTypeOfDish().equals(typeEnum));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid dish type filter provided: {}", type);
            }
        }

        if (foodTag != null && !foodTag.isBlank()) {
            try {
                FoodTagProjection tagEnum = FoodTagProjection.valueOf(foodTag.toUpperCase());
                filteredStream = filteredStream.filter(dish -> dish.getFoodTagList().contains(tagEnum));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid food tag filter provided: {}", foodTag);
            }
        }

        Comparator<DishProjection> comparator = createAndApplyComparator(sortField, direction);
        filteredStream = filteredStream.sorted(comparator);

        return filteredStream.toList();
    }

    private Comparator<DishProjection> createAndApplyComparator(String sortField, String direction) {
        Comparator<DishProjection> comparator = switch (sortField.toLowerCase()) {
            case "price" -> Comparator.comparing(DishProjection::getPrice);
            case "type" -> Comparator.comparing(DishProjection::getTypeOfDish);
            case "description" -> Comparator.comparing(DishProjection::getDescription);
            default -> Comparator.comparing(DishProjection::getName);
        };

        if ("desc".equalsIgnoreCase(direction)) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

}
