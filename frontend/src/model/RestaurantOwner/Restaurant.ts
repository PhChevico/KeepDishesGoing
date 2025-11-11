import type {CuisineType, PriceRange} from "./Enums";

export interface RestaurantDto {
    id: string;
    name: string;
    email: string;
    street: string;
    number: number;
    postalCode: number;
    city: string;
    country: string;
    cuisineType: CuisineType;
    defaultTimePreparation: number;
    picture: string;
    priceRange: PriceRange;
}