import {Alert, Box, Skeleton, Typography} from "@mui/material";
import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {useRestaurantWithMenu} from "../hooks/useRestaurantWithMenu";
import {useUpdateBasket} from "../hooks/useBasket";
import {DishCard, RestaurantHero} from "../components/RestaurantCard";
import type {Dish} from "../model/Dish.ts";
import {
    createBasket,
    getBasketForRestaurant,
    getOrCreateAnonymousId,
    setAnonymousHeader
} from "../services/clientService.ts";

export function RestaurantDetail() {
    const {id} = useParams<{ id: string }>();
    const {data: restaurant, isLoading, isError} = useRestaurantWithMenu(id!);

    const [basketId, setBasketId] = useState<string | undefined>();

    const {updateBasket, status: updatingBasketStatus} = useUpdateBasket();

    // Initialize Anonymous ID and Header once on component mount
    useEffect(() => {
        const anonymousId = getOrCreateAnonymousId();
        setAnonymousHeader(anonymousId);
    }, []);

    // Initialize basket on restaurant load
    useEffect(() => {
        if (!restaurant?.id) return;

        localStorage.setItem("current_restaurant_id", restaurant.id);

        const initializeBasket = async () => {
            const storedBasketId = localStorage.getItem(`basket_${restaurant.id}`);

            if (storedBasketId) {
                setBasketId(storedBasketId);
                return;
            }

            try {
                // Call the service function directly which handles the anonymousId
                const newBasket = await createBasket(restaurant.id);

                setBasketId(newBasket.basketId);
                localStorage.setItem(`basket_${restaurant.id}`, newBasket.basketId);
            } catch (error) {
                // Log the full error to inspect the 401 response details
                console.error("Basket initialization failed:", error);
                console.log("Basket initialization skipped (already exists or failed)");
            }
        };

        initializeBasket();
    }, [restaurant?.id]);

    const handleAddToCart = async (dish: Dish) => {
        // 🛑 Prevent adding if not published
        if (dish.dishStatus !== 'PUBLISHED') {
            console.warn(`Attempted to add dish '${dish.name}' which is currently ${dish.dishStatus}`);
            return;
        }

        let currentBasketId = basketId;

        if (!currentBasketId) {
            const existingBasket = await getBasketForRestaurant(restaurant!.id);
            if (existingBasket) {
                currentBasketId = existingBasket.basketId;
            } else {
                const newBasket = await createBasket(restaurant!.id);
                currentBasketId = newBasket.basketId;
            }

            setBasketId(currentBasketId);
            localStorage.setItem(`basket_${restaurant!.id}`, currentBasketId);
        }

        await updateBasket({
            basketId: currentBasketId,
            dishId: dish.id,
            dishName: dish.name,
            quantity: 1,
            type: "add",
        });
    };


    const isAdding = updatingBasketStatus.isPending;

    // Loading & error states
    if (isLoading) {
        return (
            <Box sx={{p: 4}}>
                <Skeleton variant="rectangular" height={200}/>
                <Skeleton variant="text" sx={{mt: 2, width: "40%"}}/>
                <Skeleton variant="rectangular" height={400} sx={{mt: 2}}/>
            </Box>
        );
    }

    if (isError || !restaurant) {
        return <Alert severity="error">Failed to load restaurant details!</Alert>;
    }

    const menu = restaurant.menu || [];

    return (
        <Box>
            <RestaurantHero restaurant={restaurant}/>

            <Box sx={{p: 4}}>
                <Typography variant="h5" textAlign="center" mb={2}>
                    Menu
                </Typography>

                <Box sx={{display: "flex", flexWrap: "wrap", gap: 3, justifyContent: "center"}}>
                    {menu.map((dish) => {

                        const isDishAvailable = dish.dishStatus === 'PUBLISHED';

                        // The button is disabled if we are updating, if we don't have a basket ID,
                        // OR if the dish is not available (not 'PUBLISHED').
                        const isDisabled = isAdding || !basketId || !isDishAvailable;

                        return (
                            <DishCard
                                key={dish.id}
                                dish={dish}
                                onAddToCart={handleAddToCart}
                                disabled={isDisabled}
                                dishStatus={dish.dishStatus}
                            />
                        );
                    })}
                </Box>
            </Box>
        </Box>
    );
}