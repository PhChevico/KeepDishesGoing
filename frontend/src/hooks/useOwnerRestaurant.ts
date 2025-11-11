import {useEffect, useState} from 'react';
import {
    acceptAllChanges,
    acceptOrder,
    createDish,
    createFoodMenu,
    createRestaurant,
    fetchAllDishes,
    fetchOwnerRestaurant,
    fetchRestaurantMenu,
    getRestaurantOrders,
    markDishInStock,
    markDishOutOfStock,
    markOrderReadyForPickup,
    publishDish,
    refuseOrder,
    unpublishDish
} from '../services/restaurantService';
import type {RestaurantDto} from '../model/RestaurantOwner/Restaurant';
import type {CreateDishCommand, DishDto} from '../model/RestaurantOwner/Dish';
import type {CreateFoodMenuCommand} from '../model/RestaurantOwner/FoodMenu';
import {AxiosError} from 'axios';
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import type {OrderDto} from "../model/RestaurantOwner/OrderDetails.ts";

type CreateRestaurantCommand = RestaurantDto;

export type DishStatusAction = 'PUBLISH' | 'UNPUBLISH' | 'OUT_OF_STOCK' | 'IN_STOCK';

const getErrorMessage = (err: unknown, defaultMessage: string = "An unknown error occurred."): string => {
    if (err instanceof AxiosError && err.response) {
        if (typeof err.response.data === 'object' && err.response.data && 'message' in err.response.data) {
            return (err.response.data as { message: string }).message;
        }
        return `Server error (${err.response.status}): ${err.response.statusText}`;
    }
    if (err instanceof Error) {
        return err.message;
    }
    return defaultMessage;
};

export const useOwnerRestaurant = () => {
    const [restaurant, setRestaurant] = useState<RestaurantDto | null>(null);
    const [menuId, setMenuId] = useState<string | null>(null);
    const [dishes, setDishes] = useState<DishDto[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    // Function to load the restaurant data AND its dishes
    const loadRestaurantAndDishes = async () => {
        setIsLoading(true);
        setError(null);
        try {
            const restaurantData = await fetchOwnerRestaurant();
            setRestaurant(restaurantData);

            if (restaurantData?.id) {
                const restaurantId = restaurantData.id;

                let loadedMenuId: string | null = null;
                try {
                    const menuData = await fetchRestaurantMenu(restaurantId);
                    if (menuData?.id) {
                        loadedMenuId = menuData.id;
                    }
                } catch (menuErr) {
                    // Handled gracefully
                }

                setMenuId(loadedMenuId);

                const loadedDishes = await fetchAllDishes(restaurantId);
                setDishes(loadedDishes);
            } else {
                setDishes([]);
                setMenuId(null);
            }

        } catch (err) {
            setError(getErrorMessage(err, "Failed to fetch restaurant, menu, or dishes."));
            setRestaurant(null);
            setDishes([]);
            setMenuId(null);
        } finally {
            setIsLoading(false);
        }
    };

    // Initial fetch on component mount
    useEffect(() => {
        loadRestaurantAndDishes();
    }, [loadRestaurantAndDishes]);

    // Function to handle restaurant creation
    const handleCreateRestaurant = async (newRestaurantData: CreateRestaurantCommand): Promise<RestaurantDto | null> => {
        setIsSubmitting(true);
        setError(null);
        try {
            const createdRestaurant = await createRestaurant(newRestaurantData);

            if (!createdRestaurant.id) {
                throw new Error("Restaurant creation failed: ID missing from response.");
            }

            const createMenuCommand: CreateFoodMenuCommand = {
                restaurantId: createdRestaurant.id
            };

            const createdMenu = await createFoodMenu(createMenuCommand);

            if (!createdMenu || !createdMenu.id) {
                throw new Error("Menu creation failed: ID missing from response.");
            }

            setRestaurant(createdRestaurant);
            setMenuId(createdMenu.id);
            setDishes([]);
            return createdRestaurant;
        } catch (err) {
            setError(getErrorMessage(err, "Failed to create restaurant or food menu."));
            return null;
        } finally {
            setIsSubmitting(false);
        }
    };

    // Function to handle dish creation
    const handleCreateDish = async (newDishData: CreateDishCommand): Promise<DishDto | null> => {
        setIsSubmitting(true);
        setError(null);

        if (!restaurant?.id) {
            setError("Cannot add dish: Restaurant is not registered. Please create your restaurant first.");
            setIsSubmitting(false);
            return null;
        }

        try {
            const payload = {
                ...newDishData,
                restaurantId: restaurant.id,
                price: Number(newDishData.price),
                typeOfDish: newDishData.typeOfDish.trim() !== '' ? newDishData.typeOfDish : 'MAIN',
                foodTags: Array.isArray(newDishData.foodTags) ? newDishData.foodTags : [],
            };

            const createdDish = await createDish(payload);
            await loadRestaurantAndDishes(); // AWAIT the full refetch
            return createdDish;
        } catch (err) {
            setError(getErrorMessage(err, "Failed to create dish."));
            return null;
        } finally {
            setIsSubmitting(false);
        }
    };

    // Function to handle dish updates (pessimistic update using refetch)
    const handleUpdateDish = (updatedDish: DishDto) => {
        console.log(`Update successful for dish ${updatedDish.id}. Triggering full refetch.`);
        loadRestaurantAndDishes();
    };

    // Function to handle dish status changes
    const handleChangeDishStatus = async (dishId: string, action: DishStatusAction) => {
        const menuIdToUse = menuId;

        if (!dishId) {
            setError("Cannot change status: Missing dish ID.");
            return;
        }

        if (!menuIdToUse && action === 'PUBLISH') {
            setError("Cannot publish dish: Menu ID is missing. Please ensure the restaurant has a menu created.");
            return;
        }

        setIsSubmitting(true);
        setError(null);

        try {
            let serviceCall: Promise<DishDto>;

            switch (action) {
                case 'PUBLISH':
                    serviceCall = publishDish(dishId, menuIdToUse!);
                    break;
                case 'UNPUBLISH':
                    serviceCall = unpublishDish(dishId);
                    break;
                case 'OUT_OF_STOCK':
                    serviceCall = markDishOutOfStock(dishId);
                    break;
                case 'IN_STOCK':
                    serviceCall = markDishInStock(dishId);
                    break;
                default:
                    return;
            }

            await serviceCall;
            await loadRestaurantAndDishes();
        } catch (err) {
            setError(getErrorMessage(err, "Failed to update dish status."));
        } finally {
            setIsSubmitting(false);
        }
    };


    // Function to accept all pending changes
    const handleAcceptAllChanges = async (): Promise<boolean> => {
        setIsSubmitting(true);
        setError(null);
        try {
            await acceptAllChanges();
            await loadRestaurantAndDishes();
            return true;
        } catch (err) {
            setError(getErrorMessage(err, "Failed to accept all changes."));
            return false;
        } finally {
            setIsSubmitting(false);
        }
    };

    return {
        restaurant,
        menuId,
        dishes,
        isLoading,
        error,
        isSubmitting,
        handleCreateRestaurant,
        handleCreateDish,
        handleUpdateDish,
        handleChangeDishStatus,
        handleAcceptAllChanges,
        refetch: loadRestaurantAndDishes
    };
};

export function useRestaurantOrders(restaurantId: string | undefined) {
    return useQuery<OrderDto[], Error>({
        queryKey: ["restaurantOrders", restaurantId],
        queryFn: () => getRestaurantOrders(restaurantId!),
        enabled: !!restaurantId,
        refetchInterval: 15000,
    });
}

export function useAcceptOrder() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: acceptOrder,
        onSuccess: () => {
            queryClient.invalidateQueries({
                predicate: (query) =>
                    query.queryKey[0] === "restaurantOrders",
            });


        },
    });
}

export function useRefuseOrder() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (variables: { orderId: string, message: string }) =>
            refuseOrder(variables.orderId, variables.message),
        onSuccess: () => {
            queryClient.invalidateQueries({
                predicate: (query) =>
                    query.queryKey[0] === "restaurantOrders",
            });


        },
    });
}

export function useMarkOrderReadyForPickup() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: markOrderReadyForPickup,
        onSuccess: () => {
            queryClient.invalidateQueries({
                predicate: (query) =>
                    query.queryKey[0] === "restaurantOrders",
            });
        },
    });
}