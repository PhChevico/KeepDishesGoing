import type {DishStatus} from "./RestaurantOwner/Dish.ts";

export type Dish = {
    id: string;
    name: string;
    typeOfDish: string;
    foodTagList: string[];
    description: string;
    price: number;
    picture?: string;

    dishStatus: DishStatus;
};