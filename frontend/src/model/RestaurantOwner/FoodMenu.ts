export type FoodMenuDto = {
    id: string;
    restaurantId: string;
};


export type CreateFoodMenuCommand = {
    restaurantId: string;
};
