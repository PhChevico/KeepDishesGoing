import type {Dish} from "./Dish";

export type RestaurantWithMenu = {
    id: string;                 // maps from restaurantId (UUID)
    name: string;
    menu: Dish[];             // maps from menu (DishDto[])
    email: string;
    address: string;            // maps from street
    number: number;
    postalCode: number;
    city: string;
    country: string;
    cuisineType: string;        // map CuisineTypeProjection to string
    defaultTimePreparation: number;
    picture?: string;           // optional
};
