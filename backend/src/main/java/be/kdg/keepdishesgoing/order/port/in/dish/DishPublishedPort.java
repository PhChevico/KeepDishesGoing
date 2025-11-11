package be.kdg.keepdishesgoing.order.port.in.dish;

public interface DishPublishedPort {
    
    void project(DishPublishedCommand dishPublishedCommand);
    void project(DishUnpublishedCommand dishUnpublishedCommand);
    void project(MarkDishOutOfStockCommand markDishOutOfStockCommand);
    void project(MarkDishInStockCommand markDishInStockCommand);
    
}
