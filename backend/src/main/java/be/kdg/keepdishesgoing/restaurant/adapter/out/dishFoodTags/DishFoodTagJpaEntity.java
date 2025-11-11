package be.kdg.keepdishesgoing.restaurant.adapter.out.dishFoodTags;


import be.kdg.keepdishesgoing.restaurant.adapter.out.dish.DishJpaEntity;
import be.kdg.keepdishesgoing.restaurant.domain.FoodTag;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "dish_food_tags", schema = "restaurant")
public class DishFoodTagJpaEntity {

    @Id
    @GeneratedValue
    @Column(name = "food_tag_id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag", nullable = false, length = 50)
    private FoodTag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", nullable = false)
    private DishJpaEntity dish;

    public DishFoodTagJpaEntity() {}

    public DishFoodTagJpaEntity(DishJpaEntity dish, FoodTag tag) {
        this.dish = dish;
        this.tag = tag;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public FoodTag getTag() { return tag; }
    public void setTag(FoodTag tag) { this.tag = tag; }

    public DishJpaEntity getDish() { return dish; }
    public void setDish(DishJpaEntity dish) { this.dish = dish; }
}
