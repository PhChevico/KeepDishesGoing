// src/hooks/useRestaurantWithMenu.ts
import {useQuery} from "@tanstack/react-query";
import {getRestaurantWithMenu} from "../services/clientService.ts";
import type {RestaurantWithMenu} from "../model/RestaurantWithMenu";
import type {Dish} from "../model/Dish";

export function useRestaurantWithMenu(id: string) {
    return useQuery<RestaurantWithMenu, Error>({
        queryKey: ["restaurantWithMenu", id],
        queryFn: async () => {
            const data = await getRestaurantWithMenu(id);

            console.log("Raw backend data:", data);

            return {
                id: data.restaurantId, // Changed from data.id to data.restaurantId
                name: data.name,
                email: data.email,
                address: data.street || data.address, // Backend uses 'street'
                number: Number(data.number),
                postalCode: Number(data.postalCode),
                city: data.city,
                country: data.country,
                cuisineType: data.cuisineType?.toString() || "",
                defaultTimePreparation: data.defaultTimePreparation,
                picture: data.picture,
                menu: data.menu?.map((d) => ({
                    id: (d.dish)?.toString() || "",
                    name: d.name,
                    typeOfDish: d.typeOfDish?.toString() || "",
                    foodTagList: d.foodTagList?.map(tag => tag.toString()) || [],
                    description: d.description,
                    price: Number(d.price),
                    picture: d.picture,
                    dishStatus: d.dishStatus?.toString() || "",
                } as Dish)) || [],
            };
        },
        enabled: !!id,
    });
}